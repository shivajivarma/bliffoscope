package com.shivajivarma.servicenow.analyzer.service;

import com.shivajivarma.servicenow.analyzer.AnalyzerConstants;
import com.shivajivarma.servicenow.analyzer.exception.ParseException;
import com.shivajivarma.servicenow.analyzer.utils.AnalyzerUtility;
import com.shivajivarma.servicenow.bliffoscope.model.Image;
import com.shivajivarma.servicenow.bliffoscope.model.Target;
import com.shivajivarma.servicenow.bliffoscope.service.BliffoScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnalyzerServiceImpl implements AnalyzerService {


    @Autowired
    private BliffoScope bliffoScope;


    /*
     * Convert files to Image objects and pass them bliffoscope service to analyze targets.
     */
    @Override
    public List<Target> findTargets(String testDataFile, List<String> targetFiles, int threshold) throws ParseException {
        Image testData = AnalyzerUtility.parseImage(testDataFile);
        testData.setName(testDataFile);
        System.out.println(testData);

        List<Image> targetImages = new ArrayList<>();
        for (String targetFile : targetFiles) {
            Image targetImage = AnalyzerUtility.parseImage(targetFile);
            targetImage.setName(targetFile);
            System.out.println(targetImage);
            targetImages.add(targetImage);
        }

        return bliffoScope.findTargets(testData, targetImages, threshold);
    }

    /*
     * Convert MultipartFile to Image objects and pass them bliffoscope service to analyze targets.
     */
    public List<Target> findTargets(MultipartFile testDataMultipartFile, MultipartFile[] targetMultipartFiles, int threshold) throws ParseException {
        try {
            File testDataFile = AnalyzerUtility.saveMultipartFile(testDataMultipartFile);
            Image testData = AnalyzerUtility.parseImage(testDataFile);
            testData.setName(testDataMultipartFile.getOriginalFilename());
            System.out.println(testData);
            testDataFile.delete();

            ArrayList<Image> targetImages = new ArrayList<>();
            for(MultipartFile targetMultipartFile: targetMultipartFiles) {
                File targetFile = AnalyzerUtility.saveMultipartFile(targetMultipartFile);
                Image targetImage = AnalyzerUtility.parseImage(targetFile);
                targetImage.setName(targetMultipartFile.getOriginalFilename());
                System.out.println(targetImage);
                targetImages.add(targetImage);
                targetFile.delete();
            }

            return bliffoScope.findTargets(testData, targetImages, threshold);
        } catch (IOException e) {
            throw new ParseException(5, AnalyzerConstants.CONVERSION_FAILED);
        }
    }

}
