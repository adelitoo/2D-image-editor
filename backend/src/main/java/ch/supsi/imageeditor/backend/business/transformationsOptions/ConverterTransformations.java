package ch.supsi.imageeditor.backend.business.transformationsOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ConverterTransformations implements ConverterTransformationInterface{

    @Override
    public List<Transformation> convertList(List<String> listOfTransformations, ResourceBundle resourceBundle) {
        List<Transformation> transformations = new ArrayList<>();
        for (String transformation : listOfTransformations) {
            if (transformation.equals(resourceBundle.getString("label.rotateClockwise"))) {
                transformations.add(new RotateImgClockwise());
            } else if (transformation.equals(resourceBundle.getString("label.rotateAntiClockwise"))) {
                transformations.add(new RotateImgAntiClockwise());
            } else if (transformation.equals(resourceBundle.getString("label.negative"))) {
                transformations.add(new NegativeImageColors());
            } else if (transformation.equals(resourceBundle.getString("label.flipUpsideDown"))) {
                transformations.add(new FlipImageUpsideDown());
            } else if (transformation.equals(resourceBundle.getString("label.flipSideToSide"))) {
                transformations.add(new FlipImageSideToSide());
            }
        }
        return transformations;
    }

}
