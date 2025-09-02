package ch.supsi.imageeditor.frontend.model;

import ch.supsi.imageeditor.frontend.observer.HelpSubject;

import java.util.ResourceBundle;

public class HelpModel extends HelpSubject implements HelpModelInterface{

    private static HelpModel mySelf;
    private ResourceBundle bundle;

    protected HelpModel() {}

    public static HelpModel getInstance() {
        if (mySelf == null) {
            mySelf = new HelpModel();
        }
        return mySelf;
    }

    @Override
    public void setBundle(ResourceBundle bundle){
        this.bundle = bundle;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    @Override
    public void getHelpData() {
        String title = bundle.getString("label.helpTitle");
        String howToUse = bundle.getString("label.howToUse");
        String close =bundle.getString("label.ok");
        notifyObservers(title, howToUse, close);
    }
}
