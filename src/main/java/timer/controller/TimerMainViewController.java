package timer.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import timer.AgeTimer;
import timer.business.Settings;
import timer.controller.ClassicAgeView.ClassicAgeViewController;
import timer.controller.StandardAgeView.StandardAgeViewController;
import timer.controller.TimerSettingsView.TimerSettingsViewController;
import timer.utils.DialogUtils;
import timer.utils.Effects;
import timer.utils.SaveObjects;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import static timer.utils.DialogUtils.makeStageMovable;

/**
 * Created by Computer on 25-06-2017.
 */
public class TimerMainViewController implements Initializable{

    @FXML
    private Button openSettingsButton;

    @FXML
    private VBox mainVBox;

    private FXMLLoader fxmlLoader;
    private VBox childVBox;
    private ClassicAgeViewController classicController;
    private StandardAgeViewController standardController;

    private Settings settings;
    private AgeTimer ageTimer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        settings = SaveObjects.deserializeSettings();
        if (null != settings) {
            settings = Settings.getInstance().writeFromDesearializedSettings(settings);
        } else {
            settings = Settings.getInstance();
        }

    }

    public void initDetails(AgeTimer ageTimer){
        this.ageTimer = ageTimer;
        initEvents();
        paintClassicAgeTimer();
        Effects.addShadowEffect(Arrays.asList(openSettingsButton));
    }

    private void paintClassicAgeTimer() {
        mainVBox.getChildren().clear();
        if(settings.getClassicConsole()) {
            fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/ClassicAgeView.fxml"));
            try {
                childVBox = (VBox) fxmlLoader.load();
                ageTimer.setSize(110,35);
                classicController = (ClassicAgeViewController) fxmlLoader.getController();
                mainVBox.getChildren().add(0, childVBox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/StandardAgeView.fxml"));
            try {
                childVBox = (VBox) fxmlLoader.load();
                ageTimer.setSize(150,50);
                standardController = (StandardAgeViewController) fxmlLoader.getController();
                mainVBox.getChildren().add(0, childVBox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initEvents() {
        openSettingsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (event.getClickCount() > 0 ) {
                        TimerSettingsViewController settingsController = getTimerSettingsView();
                    }
                }
            }
        });
    }


    private TimerSettingsViewController getTimerSettingsView() {
        Stage dialog = DialogUtils.getDialog();

        fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/TimerSettingsView.fxml"));
        try {
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            dialog.setScene(scene);
            Image icon = new Image(getClass().getResourceAsStream("/icons/settingIcon.png"));
            dialog.getIcons().add(icon);
            dialog.setTitle("Settings");
            makeStageMovable(root, dialog);
        } catch (Exception e) {

        }
        dialog.setOnHiding(event -> {
            paintClassicAgeTimer();});
        dialog.setX(openSettingsButton.getScene().getWindow().getX() - 185);
        dialog.setY(openSettingsButton.getScene().getWindow().getY());
        dialog.show();
        return (TimerSettingsViewController) fxmlLoader.getController();
    }
}
