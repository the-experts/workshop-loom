docker compose up -d influxdb grafana wiremock
echo "--------------------------------------------------------------------------------------"
echo "Load testing with Grafana dashboard http://localhost:3000/d/k6/k6-load-testing-results"
echo "--------------------------------------------------------------------------------------"
docker compose run --rm k6 run /scripts/noise.js
