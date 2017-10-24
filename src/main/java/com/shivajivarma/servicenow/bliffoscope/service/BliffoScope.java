package com.shivajivarma.servicenow.bliffoscope.service;

import com.shivajivarma.servicenow.bliffoscope.model.Image;
import com.shivajivarma.servicenow.bliffoscope.model.Target;

import java.util.List;

public interface BliffoScope {

    List<Target> findTargets(Image sourceImage, List<Image> targetImage, int threshold);

    List<Target> findTargets(Image sourceImage, Image targetImage, int threshold);

}
