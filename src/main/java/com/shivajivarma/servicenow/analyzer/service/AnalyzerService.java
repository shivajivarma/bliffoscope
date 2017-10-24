package com.shivajivarma.servicenow.analyzer.service;

import com.shivajivarma.servicenow.analyzer.exception.ParseException;
import com.shivajivarma.servicenow.bliffoscope.model.Target;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface AnalyzerService {

    List<Target> findTargets(String testDataFile, List<String>  targetFiles, int threshold) throws ParseException;

    List<Target> findTargets(MultipartFile testDataMultipartFile, MultipartFile  targetFile, int threshold) throws ParseException;

}
