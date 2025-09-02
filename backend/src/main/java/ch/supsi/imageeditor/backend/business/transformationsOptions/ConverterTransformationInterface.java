package ch.supsi.imageeditor.backend.business.transformationsOptions;

import java.util.List;
import java.util.ResourceBundle;

public interface ConverterTransformationInterface {
    List<Transformation> convertList(List<String> listOfTransformations, ResourceBundle resourceBundle);
}
