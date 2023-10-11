package nl.theexperts.loomworkshop.service

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import nl.theexperts.loomworkshop.repository.DataRecord
import nl.theexperts.loomworkshop.repository.DataRecordRepository
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.web.client.RestTemplate

class PipelineServiceTest {

    private lateinit var restTemplate: RestTemplate
    private lateinit var dataRecordRepository: DataRecordRepository
    private lateinit var pipelineService: PipelineService

    @BeforeEach
    fun setUp() {
        restTemplate = mockk()
        dataRecordRepository = mockk()
        pipelineService = PipelineService(restTemplate, dataRecordRepository)
    }

    @Test
    fun `test processLargeDataSet performance`() {
        // Mock dependencies
        coEvery { restTemplate.getForObject(any<String>(), String::class.java) } coAnswers {
            Thread.sleep(300)  // Simulate a delay
            "externalData"
        }

        every { dataRecordRepository.findByData(any()) } returns DataRecord(data = "dbData")

        val largeInput = List(300) { "input" }

        // Measure time taken
        val startTime = System.currentTimeMillis()
        pipelineService.processLargeDataSet(largeInput)
        val endTime = System.currentTimeMillis()

        val timeTaken = endTime - startTime
        assertTrue(timeTaken < 5000) // Our slow implementation takes so long! :(
    }
}
