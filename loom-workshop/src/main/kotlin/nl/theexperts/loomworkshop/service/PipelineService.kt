package nl.theexperts.loomworkshop.service

import nl.theexperts.loomworkshop.repository.DataRecordRepository
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class PipelineService(
    private val restTemplate: RestTemplate,
    private val dataRecordRepository: DataRecordRepository
) {

    fun processLargeDataSet(inputData: List<String>): List<String> {
        return inputData.parallelStream()
            .map { data ->
                val externalData = fetchExternalData(data)
                val dbData = lookupInDatabase(externalData)
                return@map intensiveComputation("$externalData - $dbData")
//              intensiveComputationPinned("$externalData - $dbData")
            }
            .toList()
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