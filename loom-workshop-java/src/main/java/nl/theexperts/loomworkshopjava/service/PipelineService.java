package nl.theexperts.loomworkshopjava.service;

import nl.theexperts.loomworkshopjava.repository.DataRecord;
import nl.theexperts.loomworkshopjava.repository.DataRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class PipelineService {

    private final RestTemplate restTemplate;
    private final DataRecordRepository dataRecordRepository;

    public PipelineService(RestTemplate restTemplate, DataRecordRepository dataRecordRepository) {
        this.restTemplate = restTemplate;
        this.dataRecordRepository = dataRecordRepository;
    }

    public List<String> processLargeDataSet(List<String> inputData) {
        List<Future<String>> futures = new ArrayList<>();

        ThreadFactory factory = Thread.ofVirtual().factory();
        try (ExecutorService executor = Executors.newThreadPerTaskExecutor(factory)) {
            for (String data : inputData) {
                Future<String> future = executor.submit(() -> {
                    String externalData = fetchExternalData(data);
                    String dbData = lookupInDatabase(externalData);
                    return intensiveComputationPinned(externalData + " - " + dbData);
                });
                futures.add(future);
            }

            List<String> results = new ArrayList<>();
            for (Future<String> future : futures) {
                results.add(future.get());
            }
            return results;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
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

    private String intensiveComputationPinned(String data) {
        final Lock lock = new ReentrantLock();
        lock.lock();
        try {
            Thread.sleep(100);
            return new StringBuilder(data).reverse().toString();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
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
