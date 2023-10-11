package nl.theexperts.loomworkshop.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DataRecordRepository : JpaRepository<DataRecord, Long> {
    fun findByData(data: String): DataRecord?
}
