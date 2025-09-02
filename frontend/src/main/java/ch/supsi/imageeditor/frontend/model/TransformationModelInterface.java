package ch.supsi.imageeditor.frontend.model;

import ch.supsi.imageeditor.frontend.view.DisplayPNMImageView;
import ch.supsi.imageeditor.frontend.view.InfoBarView;

import java.util.ResourceBundle;

public interface TransformationModelInterface {
    void addTransformation(String selectedTransformation);

    void clearPipelineListView();

    void applyTransformations();

    void setObserverImageDisplay(DisplayPNMImageView displayPNMImageView);

    void setObserverInfoBar(InfoBarView infoBarView);

    void setResourceBundle(ResourceBundle resourceBundle);
}
