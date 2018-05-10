package timer.controller.ClassicAgeView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.Seconds;
import org.joda.time.Years;
import timer.business.Settings;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Computer on 11-08-2017.
 */
public class ClassicAgeViewController implements Initializable {

    @FXML
    private Label nameLabel;
    @FXML
    private Label ageTimerLabel;

    private Settings settings;
    private Timer timeAge ;
    public static final DecimalFormat AGE_FORMAT = new DecimalFormat("00.00000");
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
        TimerTask refreshAge = new TimerTask() {
            @Override
            public void run() {
                javafx.application.Platform.runLater(new Runnable() {
                    public void run() {
                        DateTime currentTime = new DateTime();
                        LocalTime birthTime = new LocalTime(bHour,bMinute);
                        DateTime previousBirthday = new DateTime(currentTime.getYear(), bMonth,bDay,bHour,bMinute,00);
                        DateTime comingBirthday;
                        if (currentTime.isBefore(previousBirthday)) {
                            previousBirthday = new DateTime(currentTime.getYear()-1,bMonth,bDay,bHour,bMinute,00);
                            comingBirthday = new DateTime(currentTime.getYear(), bMonth,bDay,bHour,bMinute,00);
                        }else {
                            previousBirthday = new DateTime(currentTime.getYear(),bMonth,bDay,bHour,bMinute,00);
                            comingBirthday = new DateTime(currentTime.getYear()+1, bMonth,bDay,bHour,bMinute,00);
                        }
                        double ageCurrentFraction = (double) Seconds.secondsBetween(previousBirthday, currentTime).getSeconds() / (double)Seconds.secondsBetween(previousBirthday, comingBirthday).getSeconds();
                        double ageYear = Years.yearsBetween(myAge, currentTime).getYears();
                        double fractionAge = ageYear + ageCurrentFraction;
                        ageTimerLabel.setText(AGE_FORMAT.format(fractionAge));
                    }
                });
            };
        };
        timeAge.schedule(refreshAge,1000,1000);
        ageTimerLabel.setText("00.00000");
    }
}
