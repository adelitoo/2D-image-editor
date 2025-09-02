package ch.supsi.imageeditor.frontend.view;

import ch.supsi.imageeditor.frontend.observer.ObserverTransformations;
import javafx.scene.control.ListView;

import java.util.List;

public class DisplayPipelineView implements ObserverTransformations, DisplayPipelineViewInterface{

    private static DisplayPipelineView mySelf;
    private ListView<String> pipelineListView;

    public static DisplayPipelineView getInstance() {
        if(mySelf == null)
           mySelf = new DisplayPipelineView();
        return mySelf;
    }

    @Override
    public void update(List<String> selectedTransformations) {
        pipelineListView.getItems().clear();
        for (String selectedTransformation : selectedTransformations) {
            pipelineListView.getItems().add(selectedTransformation);
        }
    }

    @Override
    public void setListView(ListView<String> lookup) {
        pipelineListView = lookup;
    }
}
