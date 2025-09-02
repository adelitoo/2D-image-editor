package ch.supsi.imageeditor.frontend.controller;

import ch.supsi.imageeditor.frontend.model.LanguageModel;
import ch.supsi.imageeditor.frontend.model.LanguageModelInterface;

import java.util.ResourceBundle;

public class LanguageController implements LanguageControllerInterface {
    private static LanguageController mySelf;
    private final LanguageModelInterface languageModel;
    protected LanguageController() {
        languageModel = LanguageModel.getInstance();
    }

    public static LanguageController getInstance() {
        if (mySelf == null) {
            mySelf = new LanguageController();
        }
        return mySelf;
    }

    @Override
    public LanguageModel getLanguageModel() {
        return (LanguageModel) languageModel;
    }

    @Override
    public ResourceBundle getResourceBundle() {
        return languageModel.getResourceBundle();
    }

    @Override
    public void setLanguageTagInsidePreferences(String s) {
        languageModel.setLanguageTagInsidePreferences(s);
    }

}
