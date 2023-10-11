package nl.theexperts.loomworkshopjava.api;

import nl.theexperts.loomworkshopjava.service.PipelineService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PipelineController {

    private final PipelineService pipelineService;

    public PipelineController(PipelineService pipelineService) {
        this.pipelineService = pipelineService;
    }

    @PostMapping("/handle-simple-pipeline")
    public List<String> simplePipeline(@RequestBody List<String> list) {
       return pipelineService.processLargeDataSet(list);
    }
}
