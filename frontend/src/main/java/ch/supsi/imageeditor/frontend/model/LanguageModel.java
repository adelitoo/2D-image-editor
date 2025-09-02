package ch.supsi.imageeditor.frontend.model;

import ch.supsi.imageeditor.backend.application.PreferencesController;
import ch.supsi.imageeditor.backend.application.PreferencesControllerInterface;
import ch.supsi.imageeditor.frontend.observer.InfoBarSubject;

import java.util.ResourceBundle;

public class LanguageModel extends InfoBarSubject implements LanguageModelInterface{
    private static LanguageModel mySelf;
    private final PreferencesControllerInterface preferencesController;

    protected LanguageModel() {
        this.preferencesController = PreferencesController.getInstance();
    }

    public static LanguageModel getInstance() {
        if (mySelf == null) {
            mySelf = new LanguageModel();
        }
        return mySelf;
    }

    @Override
    public ResourceBundle getResourceBundle() {
        return preferencesController.getPreference();
    }

    @Override
    public void setLanguageTagInsidePreferences(String s) {
        preferencesController.setPreference("language-tag", s);
        notifyObservers(preferencesController.getPreference().getString("label.clickLanguageButton"));
    }
}
