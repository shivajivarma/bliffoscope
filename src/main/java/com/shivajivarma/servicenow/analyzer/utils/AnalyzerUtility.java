package com.shivajivarma.servicenow.analyzer.utils;

import com.shivajivarma.servicenow.analyzer.AnalyzerConstants;
import com.shivajivarma.servicenow.analyzer.exception.ParseException;
import com.shivajivarma.servicenow.bliffoscope.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class AnalyzerUtility {

    public static Image parseImage(String filePath) throws ParseException {
        Image image;
        if (filePath != null) {
            URL relativePath = AnalyzerUtility.class.getClassLoader().getResource(filePath);
            if (relativePath != null) {
                File file = new File(relativePath.getFile());
                image = parseImage(file);
            } else {
                throw new ParseException(2, "File not found - " + filePath);
            }
        } else {
            throw new ParseException(1, AnalyzerConstants.INVALID_FILE_PATH);
        }
        return image;
    }

    /*
    * Reads the file from resources folder and converts it into Image object.
    * 1. Fills boolean matrix
    * 2. Compute height of image (Height equals number of lines read from the file)
    * 3. Computes width of image (Max line width)
    */
    public static Image parseImage(File file) throws ParseException {
        Image image;
        try {
            Scanner sc = new Scanner(file);
            image = new Image();
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                int lineWidth = line.length();
                if (lineWidth > image.getWidth()) {
                    image.setWidth(lineWidth);
                }
                image.setHeight(image.getHeight() + 1);
                image.getMatrix().add(convertStringToBooleanArray(line));
            }
            if(image.getMatrix().size() == 0){
                file.delete();
                throw new ParseException(3, "Invalid file uploaded");
            }
            sc.close();
        } catch (FileNotFoundException e) {
            throw new ParseException(2, "File not found - " + file.getAbsolutePath());
        }
        return image;
    }

    /*
     * Converts string of spaces and pluses to boolean array. (space -> false & plus -> true)
     */
    public static ArrayList<Boolean> convertStringToBooleanArray(String str) throws ParseException {
        ArrayList<Boolean> arrayList = new ArrayList<Boolean>();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (str.charAt(i) == '+') {
                arrayList.add(true);
            } else if (str.charAt(i) == ' ') {
                arrayList.add(false);
            } else {
                throw new ParseException(3, "Parsing failed as invalid character found in the file");
            }
        }
        return arrayList;
    }

    /*
     * Writes multipart file content to file and return the file pointer.
     */
    public static File saveMultipartFile(MultipartFile multipartFile) throws IOException {
        File file = new File(new Date().getTime() + multipartFile.getOriginalFilename()); //Adding time stamp to file name in order to avoid collision with parallel requests.
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();
        return file;
    }

}
