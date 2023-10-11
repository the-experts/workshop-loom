import http from 'k6/http';
import {check, sleep} from "k6";

const isNumeric = (value) => /^\d+$/.test(value);

const default_vus = 10;

const target_vus_env = `${__ENV.TARGET_VUS}`;
const target_vus = isNumeric(target_vus_env) ? Number(target_vus_env) : default_vus;

export let options = {
    stages: [// Ramp-up from 1 to TARGET_VUS virtual users (VUs) in 15s
        {duration: "5s", target: target_vus},

        // Stay at rest on TARGET_VUS VUs for 30s
        {duration: "10s", target: target_vus},

        // Ramp-down from TARGET_VUS to 0 VUs for 15s
        {duration: "10s", target: 0}]
};

export default function () {
    // const response = http.get("https://swapi.dev/api/people/30/", {headers: {Accepts: "application/json"}});

    // const url = "http://host.docker.internal:6969/handle-simple-pipeline";
    const url = "http://host.docker.internal:6969/handle-simple-pipeline";
    const payload = JSON.stringify(["abra", "kadabra", "alakazam", "machop", "machamp", "machoke", "kadabra", "alakazam", "machop", "machamp", "machoke", "kadabra", "alakazam", "machop", "machamp", "machoke", "kadabra", "alakazam", "machop", "machamp", "machoke", "kadabra", "alakazam", "machop", "machamp", "machoke", "kadabra", "alakazam", "machop", "machamp", "machoke", "kadabra", "alakazam", "machop", "machamp", "machoke", "kadabra", "alakazam", "machop"]);

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };


    const response = http.post(url, payload, params);

    check(response, {"status is 200": (r) => r.status === 200});
    check(response, {"status is 50x": (r) => r.status >= 500});


    sleep(.300);
};
