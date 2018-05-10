package timer.controller.StandardAgeView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Seconds;
import org.joda.time.Years;
import timer.business.Settings;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Computer on 12-08-2017.
 */
public class StandardAgeViewController implements Initializable {

    @FXML
    Label ageLabel, daysLabel, secondLabel, nameLabel;

    private Settings settings;
    private Timer timeAge ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        settings = Settings.getInstance();
        paintTimer();
    }

    private void paintTimer() {
        timeAge = new Timer();
        nameLabel.setText(settings.getName());
        setUpAgeTimer();
    }

    private void setUpAgeTimer() {
        DateTime myAge = settings.getBirthDateTime();
        int bDay = myAge.getDayOfMonth();
        int bMonth = myAge.getMonthOfYear();
        int bYear = myAge.getYear();
        int bHour = myAge.getHourOfDay();
        int bMinute = myAge.getMinuteOfHour();
        int mSecond = myAge.getSecondOfDay();
        int yDay = myAge.getDayOfYear();

        TimerTask refreshAge = new TimerTask() {
            @Override
            public void run() {
                javafx.application.Platform.runLater(new Runnable() {
                    public void run() {
                        DateTime currentTime = new DateTime();
                        DateTime previousBirthday = new DateTime(currentTime.getYear(), bMonth,bDay,bHour,bMinute,00);
                        if (currentTime.isBefore(previousBirthday)) {
                            previousBirthday = new DateTime(currentTime.getYear()-1,bMonth,bDay,bHour,bMinute,00);
                        }else {
                            previousBirthday = new DateTime(currentTime.getYear(),bMonth,bDay,bHour,bMinute,00);
                        }
                        int ageCurrentSecond =  Seconds.secondsBetween(previousBirthday, currentTime).getSeconds();
                        int ageCurrentDays = Days.daysBetween(previousBirthday, currentTime).getDays();
                        int ageYear = Years.yearsBetween(myAge, currentTime).getYears();
                        ageLabel.setText(String.valueOf(ageYear));
                        secondLabel.setText(String.valueOf(ageCurrentSecond));
                        daysLabel.setText(String.valueOf(ageCurrentDays));
                    }
                });
            };
        };

        timeAge.schedule(refreshAge,1000,1000);
    }
}
