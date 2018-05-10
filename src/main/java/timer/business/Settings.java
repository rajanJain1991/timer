package timer.business;


import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import org.joda.time.DateTime;

import java.io.Serializable;

import static timer.controller.TimerSettingsView.TimerSettingsViewController.formatter;

/**
 * Created by Computer on 26-06-2017.
 */
public class Settings implements Observable, Serializable{

    private static final long serialVersionUID = 1L;

    private static Settings instance = null;

    protected Settings() {

    }

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    private String name = "Kaal";
    private String birthDate = "01-01-0000";
    private String birthTime = "00:00";
    private Boolean classicConsole = true;
    private Boolean standardConsole = false;



    @Override
    public void addListener(InvalidationListener listener) {

    }

    @Override
    public void removeListener(InvalidationListener listener) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public DateTime getBirthDateTime() {
        String dateTime = birthDate+" "+birthTime+":00";
        DateTime dt = formatter.parseDateTime(dateTime);
        return dt;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthTime() {
        return birthTime;
    }

    public void setBirthTime(String birthTime) {
        this.birthTime = birthTime;
    }

    public Boolean getClassicConsole() {
        return classicConsole;
    }

    public void setClassicConsole(Boolean classicConsole) {
        this.classicConsole = classicConsole;
    }

    public Boolean getStandardConsole() {
        return standardConsole;
    }

    public void setStandardConsole(Boolean standardConsole) {
        this.standardConsole = standardConsole;
    }

    public Settings writeFromDesearializedSettings(Settings settings){
        this.name = settings.getName();
        this.birthTime = settings.getBirthTime();
        this.birthDate = settings.getBirthDate();
        this.classicConsole = settings.getClassicConsole();
        this.standardConsole = settings.getStandardConsole();
        return this;
    }
}
