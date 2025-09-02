package ch.supsi.imageeditor.frontend.model;


import ch.supsi.imageeditor.frontend.observer.AboutSubject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AboutModel extends AboutSubject implements AboutModelInterface{
    private static AboutModel mySelf;
    private final Properties aboutUsFile;

    protected AboutModel() {
        aboutUsFile = getAboutUsFile();
    }

    public static AboutModel getInstance() {
        if (mySelf == null) {
            mySelf = new AboutModel();
        }
        return mySelf;
    }

    Properties getAboutUsFile() {
        Properties properties = new Properties();
        try{
            InputStream inputStream = this.getClass().getResourceAsStream("/pom.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }


    @Override
    public void getAboutData() {

        String actualName = aboutUsFile.getProperty("name");
        String actualDeveloper = aboutUsFile.getProperty("developers");
        String actualVersion = aboutUsFile.getProperty("version");
        String actualDate = aboutUsFile.getProperty("dateBuild");

        notifyObservers(
                actualName,
                actualDeveloper,
                actualVersion,
                actualDate
        );
    }

}
