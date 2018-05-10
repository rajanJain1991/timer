package timer.controller.TimerSettingsView;


import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import timer.business.Settings;
import timer.utils.SaveObjects;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Computer on 25-06-2017.
 */
public class TimerSettingsViewController implements Initializable{
    @FXML
    Button closeButton, saveButton;

    @FXML
    TextField nameTextField;

    @FXML
    TextField bdTextField;

    @FXML
    TextField btTextField;

    @FXML
    RadioButton classicConsoleRB, standardConsoleRB;

    final ToggleGroup group = new ToggleGroup();

    private Settings settings;
    private Stage stage;
    private Robot robot;

    private ReadOnlyBooleanWrapper dialogHiddenProperty = new ReadOnlyBooleanWrapper(false);
    public static DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm:ss");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        settings = Settings.getInstance();

        try {
            robot = new Robot();
        } catch (Exception e) {

        }
        initData();
        initEvents();
    }

    private void initData() {
        nameTextField.setText(settings.getName());
        bdTextField.setText(settings.getBirthDate());
        btTextField.setText(settings.getBirthTime());
        classicConsoleRB.setSelected(settings.getClassicConsole());
        standardConsoleRB.setSelected(settings.getStandardConsole());
        classicConsoleRB.setToggleGroup(group);
        standardConsoleRB.setToggleGroup(group);
    }

    private void initEvents() {
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage = (Stage) saveButton.getScene().getWindow();
                stage.close();
            }
        });

        btTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String candidate = StringUtils.overlay("00:00", newValue, 0, newValue.length()) ;
                if (!candidate.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) {
                    btTextField.setText(oldValue);
                } else if(newValue.length()==2 && oldValue.length() == 1) {
                    btTextField.setText(newValue+":");
                } else if (newValue.length()==5) {
                    robot.keyPress(KeyEvent.VK_TAB);
                }
            }
        });
        bdTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String candidate = StringUtils.overlay("11-11-1111", newValue, 0, newValue.length()) ;
                if(!candidate.matches("(0[1-9]|[12]?[0-9]|3[0-1])-(0[1-9]|1[0-2])-[0-9]{4}")) {
                    bdTextField.setText(oldValue);
                }else if((newValue.length()==2 && oldValue.length() == 1) || (newValue.length()==5 && oldValue.length() == 4)) {
                    bdTextField.setText(newValue+"-");
                } else if (newValue.length()==10) {
                    robot.keyPress(KeyEvent.VK_TAB);
                }
            }
        });
        nameTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length()>=10) {
                    nameTextField.setText(newValue.substring(0,10));
                    robot.keyPress(KeyEvent.VK_TAB);
                }
            }
        });

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(checkTextFieldsValidation()){
                    saveSettings();
                    SaveObjects.serializeSettings(settings);
                    stage = (Stage) saveButton.getScene().getWindow();
                    stage.close();
                } else {
                    System.out.println("validation error");
                }
            }
        });
    }

    private boolean checkTextFieldsValidation() {
        if(StringUtils.isEmpty(nameTextField.getText())){
            return false;
        }
        try {
            String dateTime = bdTextField.getText()+" "+btTextField.getText()+":00";
            DateTime dt = formatter.parseDateTime(dateTime);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void saveSettings() {
        settings.setName(nameTextField.getText());
        settings.setBirthDate(bdTextField.getText());
        settings.setBirthTime(btTextField.getText());
        settings.setClassicConsole(classicConsoleRB.isSelected());
        settings.setStandardConsole(standardConsoleRB.isSelected());
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public void setNameTextField(TextField nameTextField) {
        this.nameTextField = nameTextField;
    }

    public TextField getBdTextField() {
        return bdTextField;
    }

    public void setBdTextField(TextField bdTextField) {
        this.bdTextField = bdTextField;
    }

    public TextField getBtTextField() {
        return btTextField;
    }

    public void setBtTextField(TextField btTextField) {
        this.btTextField = btTextField;
    }

    public ReadOnlyBooleanProperty getDialogHiddenProperty() {
        return dialogHiddenProperty.getReadOnlyProperty();
    }

}
