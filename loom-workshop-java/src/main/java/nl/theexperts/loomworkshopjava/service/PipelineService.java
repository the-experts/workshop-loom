package nl.theexperts.loomworkshopjava.service;

import nl.theexperts.loomworkshopjava.repository.DataRecord;
import nl.theexperts.loomworkshopjava.repository.DataRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PipelineService {

    private final RestTemplate restTemplate;
    private final DataRecordRepository dataRecordRepository;

    public PipelineService(RestTemplate restTemplate, DataRecordRepository dataRecordRepository) {
        this.restTemplate = restTemplate;
        this.dataRecordRepository = dataRecordRepository;
    }

    public List<String> processLargeDataSet(List<String> inputData) {
        return inputData.parallelStream()
                .map(data -> {
                    String externalData = fetchExternalData(data);
                    String dbData = lookupInDatabase(externalData);
                    return intensiveComputation(externalData + " - " + dbData);
                    //return intensiveComputationPinned(externalData + " - " + dbData);
                })
                .toList();
    }

    private String fetchExternalData(String inputData) {
        String apiUrl = "/" + inputData;
        try {
            restTemplate.getForObject(apiUrl, String.class);
            return inputData;
        } catch (RestClientException e) {
            throw new DataFetchException("Failed to fetch external data.", e);
        }
    }

    private String lookupInDatabase(String data) {
        DataRecord record = dataRecordRepository.findByData(data);
        if (record != null) {
            return record.toString();
        } else {
            throw new DataNotFoundException("Data not found in the database.");
        }
    }

    private String intensiveComputation(String data) {
        // Simulate a heavy computation, e.g., a complex algorithm or just a sleep
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new StringBuilder(data).reverse().toString();
    }

    private String intensiveComputationPinned(String data){
        synchronized (this) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return new StringBuilder(data).reverse().toString();
        }
    }

    static class DataFetchException extends RuntimeException {
        public DataFetchException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    static class DataNotFoundException extends RuntimeException {
        public DataNotFoundException(String message) {
            super(message);
        }
    }
}
