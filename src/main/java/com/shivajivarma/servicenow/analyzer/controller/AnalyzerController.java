package com.shivajivarma.servicenow.analyzer.controller;

import com.shivajivarma.servicenow.analyzer.service.AnalyzerService;
import com.shivajivarma.servicenow.bliffoscope.model.Target;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("analyser")
public class AnalyzerController {

    @Autowired
    private AnalyzerService analyzerService;

    @RequestMapping(value = "/findTargets", method = RequestMethod.POST)
    @ResponseBody
    public List<Target> findTargets(@RequestParam("testDataFile") MultipartFile testDataFile, @RequestParam("targetFiles") MultipartFile[] targetFiles, @RequestParam(value = "threshold", required = false, defaultValue = "70") Integer threshold) {
       return analyzerService.findTargets(testDataFile, targetFiles, threshold);
    }

}
