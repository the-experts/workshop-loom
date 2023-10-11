package nl.theexperts.loomworkshopjava.service;

import nl.theexperts.loomworkshopjava.repository.DataRecord;
import nl.theexperts.loomworkshopjava.repository.DataRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(MockitoExtension.class)
class PipelineServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private DataRecordRepository dataRecordRepository;

    @InjectMocks
    private PipelineService pipelineService;

    @Test
    @DisplayName("Test processLargeDataSet performance")
    void testProcessLargeDataSetPerformance() throws ExecutionException, InterruptedException {
        // Mock dependencies
        when(restTemplate.getForObject(any(String.class), eq(String.class))).thenAnswer(request -> {
            try {
                Thread.sleep(300); // Delay
            } catch (InterruptedException ignored) {}
            return withStatus(OK).body("responseBody").contentType(MediaType.APPLICATION_JSON);
        });
        when(dataRecordRepository.findByData(any())).thenReturn(new DataRecord("dbData"));

        List<String> largeInput = new ArrayList<>(Collections.nCopies(300, "test"));

        // Measure time taken
        long startTime = System.currentTimeMillis();
        pipelineService.processLargeDataSet(largeInput);
        long endTime = System.currentTimeMillis();

        long timeTaken = endTime - startTime;
        assertThat(timeTaken).isLessThan(2000L);  // Our slow implementation takes so long! :(
    }
}
