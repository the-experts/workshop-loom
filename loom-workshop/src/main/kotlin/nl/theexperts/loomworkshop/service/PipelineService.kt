package nl.theexperts.loomworkshop.service

import nl.theexperts.loomworkshop.repository.DataRecordRepository
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.concurrent.Executors
import java.util.concurrent.Future

@Service
class PipelineService(
    private val restTemplate: RestTemplate,
    private val dataRecordRepository: DataRecordRepository
) {

    fun processLargeDataSet(inputData: List<String>): List<String> {
        // Create a list to hold futures of each virtual thread's computation
        val futures = mutableListOf<Future<String>>()

        val factory = Thread.ofVirtual().factory()
        // Create an executor for virtual threads
        val executor = Executors.newThreadPerTaskExecutor(factory)

        // For each data, create a virtual thread and submit the task to the executor
        for (data in inputData) {
            val future = executor.submit<String> {
                val externalData = fetchExternalData(data)
                val dbData = lookupInDatabase(externalData)
                intensiveComputation("$externalData - $dbData")
                // intensiveComputationPinned("$externalData - $dbData")
            }
            futures.add(future)
        }

        // Collect the results from each future (i.e., each virtual thread's computation)
        val results = futures.map { it.get() }

        // Shutdown the executor
        executor.shutdown()

        return results
    }

    private fun fetchExternalData(inputData: String): String {
        val apiUrl = "/$inputData"
        restTemplate.getForObject(apiUrl, String::class.java) ?: throw DataFetchException("Failed to fetch external data.")
        return inputData
    }

    private fun lookupInDatabase(data: String): String {
        val record = dataRecordRepository.findByData(data)
        return record?.data ?: throw DataNotFoundException("Data not found in the database.")
    }

    private fun intensiveComputation(data: String): String {
            Thread.sleep(100)  // Simulate a delay
            return data.reversed()  // Just an example transformation
    }

    private fun intensiveComputationPinned(data: String): String {
        synchronized(this) {
            Thread.sleep(100)  // Simulate a delay
            return data.reversed()  // Just an example transformation
        }
    }
}