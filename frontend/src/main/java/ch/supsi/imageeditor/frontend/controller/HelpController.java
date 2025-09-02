package ch.supsi.imageeditor.frontend.controller;

import ch.supsi.imageeditor.frontend.model.AboutModel;
import ch.supsi.imageeditor.frontend.model.AboutModelInterface;
import ch.supsi.imageeditor.frontend.model.HelpModel;
import ch.supsi.imageeditor.frontend.model.HelpModelInterface;

import java.util.ResourceBundle;

public class HelpController implements HelpControllerInterface {
    private static HelpController mySelf;
    private final AboutModelInterface aboutModel;
    private final HelpModelInterface helpModel;


    protected HelpController() {
        aboutModel = AboutModel.getInstance();
        helpModel = HelpModel.getInstance();
    }


    public static HelpController getInstance() {
        if (mySelf == null) {
            mySelf = new HelpController();
        }
        return mySelf;
    }

    public AboutModel getAboutModel() {
        return (AboutModel) aboutModel;
    }

    public HelpModel getHelpModel() {
        return (HelpModel) helpModel;
    }

    public void setBundle(ResourceBundle bundle){
        helpModel.setBundle(bundle);
    }

    @Override
    public void showAbout() { aboutModel.getAboutData();}

    @Override
    public void showHelp() { helpModel.getHelpData(); }

}
