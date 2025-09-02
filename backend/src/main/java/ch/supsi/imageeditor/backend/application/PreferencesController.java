package ch.supsi.imageeditor.backend.application;

import ch.supsi.imageeditor.backend.business.PreferencesBusinessInterface;
import ch.supsi.imageeditor.backend.business.PreferencesModel;

import java.util.ResourceBundle;

public class PreferencesController implements PreferencesControllerInterface {

    private static PreferencesController myself;

    private final PreferencesBusinessInterface preferencesModel;

    protected PreferencesController() {
        this.preferencesModel = PreferencesModel.getInstance();
    }

    public static PreferencesController getInstance() {
        if (myself == null) {
            myself = new PreferencesController();
        }

        return myself;
    }


    public ResourceBundle getPreference() {
        return this.preferencesModel.getPreference();
    }

    public void setPreference(String tag, String preferenceTag) {
        this.preferencesModel.setPreference(tag, preferenceTag);
    }
}
