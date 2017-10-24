package com.shivajivarma.servicenow.analyzer.service;

import com.shivajivarma.servicenow.analyzer.exception.ParseException;
import com.shivajivarma.servicenow.analyzer.utils.Utility;
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


    @Override
    public List<Target> findTargets(String testDataFile, List<String> targetFiles, int threshold) throws ParseException {
        Image testData = Utility.parseImage(testDataFile);
        testData.setName(testDataFile);
        System.out.println(testData);

        List<Image> targetImages = new ArrayList<Image>();
        for (String targetFile : targetFiles) {
            Image targetImage = Utility.parseImage(targetFile);
            targetImage.setName(targetFile);
            System.out.println(targetImage);
            targetImages.add(targetImage);
        }

        return bliffoScope.findTargets(testData, targetImages, threshold);
    }

    public List<Target> findTargets(MultipartFile testDataMultipartFile, MultipartFile targetMultipartFile, int threshold) throws ParseException {
        try {
            File testDataFile = Utility.saveMultipartFile(testDataMultipartFile);
            Image testData = Utility.parseImage(testDataFile);
            testData.setName(testDataMultipartFile.getOriginalFilename());
            System.out.println(testData);
            testDataFile.delete();

            List<Image> targetImages = new ArrayList<Image>();
            File targetFile = Utility.saveMultipartFile(targetMultipartFile);
            Image targetImage = Utility.parseImage(targetFile);
            targetImage.setName(targetMultipartFile.getOriginalFilename());
            System.out.println(targetImage);
            targetImages.add(targetImage);
            targetFile.delete();

            return bliffoScope.findTargets(testData, targetImages, threshold);
        } catch (IOException e) {
            throw new ParseException(5, "Conversion failed - MultipartFile file to File");
        }
    }

}
