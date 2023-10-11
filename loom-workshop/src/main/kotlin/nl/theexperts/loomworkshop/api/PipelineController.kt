package nl.theexperts.loomworkshop.api

import nl.theexperts.loomworkshop.service.PipelineService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
    class PipelineController(val pipelineService: PipelineService) {
    //call service
    @PostMapping("/handle-simple-pipeline")
    fun simplePipeline(@RequestBody list: List<String> ) =
        pipelineService.processLargeDataSet(list)
}