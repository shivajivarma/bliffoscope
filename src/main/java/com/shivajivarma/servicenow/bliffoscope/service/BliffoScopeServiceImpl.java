package com.shivajivarma.servicenow.bliffoscope.service;

import com.shivajivarma.servicenow.bliffoscope.exception.InvalidDataException;
import com.shivajivarma.servicenow.bliffoscope.exception.NotFoundException;
import com.shivajivarma.servicenow.bliffoscope.model.Coordinates;
import com.shivajivarma.servicenow.bliffoscope.model.Image;
import com.shivajivarma.servicenow.bliffoscope.model.Target;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BliffoScopeServiceImpl implements BliffoScope {

    /**
     * Computes score for each subset of test data against each target image and checks whether the score percentage is more than threshold
     */
    public List<Target> findTargets(Image sourceImage, List<Image> targetImages, int threshold) throws NotFoundException {

        List<Target> targets = new ArrayList<Target>();

        if (sourceImage == null) {
            throw new NotFoundException(100, "Source image not found");
        } else if (targetImages == null || targetImages.size() == 0) {
            throw new NotFoundException(101, "Target images not found");
        } else if (threshold < 0 || threshold > 100) {
            throw new InvalidDataException(102, "Threshold should be between 0 and 100");
        } else {
            for (Image targetImage : targetImages) {
                targets.addAll(findTargets(sourceImage, targetImage, threshold));
            }
        }

        return targets;
    }


    /**
     * Computes score for each subset of test data against target image and checks whether the score percentage is more than threshold
     *
     * @param threshold : Minimum percentage match required to consider a subset of test data as target.
     */
    public List<Target> findTargets(Image sourceImage, Image targetImage, int threshold) {
        List<Target> targets;

        if (sourceImage == null) {
            throw new NotFoundException(100, "Source image not found");
        } else if (targetImage == null) {
            throw new NotFoundException(101, "Target image not found");
        } else if (threshold < 0 || threshold > 100) {
            throw new InvalidDataException(102, "Threshold should be between 0 and 100");
        } else {
            targets = new ArrayList<>();
            float targetArea = targetImage.getHeight() * targetImage.getWidth();
            for (int row = 0; row <= sourceImage.getHeight() - targetImage.getHeight(); row++) {
                for (int col = 0; col <= sourceImage.getWidth() - targetImage.getWidth(); col++) {
                    int score = getScoreForSubSet(sourceImage, targetImage, row, col);
                    float percentageMatch = ((score / targetArea) * 100);
                    if (percentageMatch >= threshold) {
                        Target target = new Target();
                        target.setType(targetImage.getName());
                        target.setCoordinates(new Coordinates(row, col));
                        target.setPercentageMatch(percentageMatch);
                        targets.add(target);
                    }
                }
            }
        }

        return targets;
    }


    /**
     * Computes score for subset of test data against target image (no of matching cells)
     */
    private static int getScoreForSubSet(Image sourceImage, Image targetImage, int row, int col) {
        int score = 0;
        for (int targetRow = 0; targetRow < targetImage.getHeight(); targetRow++) {
            for (int targetCol = 0; targetCol < targetImage.getWidth(); targetCol++) {

                if (targetImage.getMatrix().get(targetRow).get(targetCol) == sourceImage.getMatrix().get(row + targetRow).get(col + targetCol)) {
                    score++;
                }
            }
        }
        return score;
    }

}
