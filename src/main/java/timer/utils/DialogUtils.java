package timer.utils;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;

/**
 * Created by Computer on 25-06-2017.
 */
public class DialogUtils {

    public static Stage getDialog() {
        Stage dialog = new Stage();
        dialog.setResizable(false);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.initModality(Modality.APPLICATION_MODAL);
//        dialog.showAndWait();
        return dialog;
    }

    public static void makeStageMovable(Parent root, Stage stage) {

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                final double xOffset = stage.getX() - event.getScreenX();
                final double yOffset = stage.getY() - event.getScreenY();
                root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        stage.setX(event.getScreenX() + xOffset);
                        stage.setY(event.getScreenY() + yOffset);
                    }
                });
            }
        });
    }

    public static void makeJFrameMovable(Parent root, JFrame jFrame) {

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                final double xOffset = jFrame.getX() - event.getScreenX();
                final double yOffset = jFrame.getY() - event.getScreenY();
                root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        jFrame.setLocation((int) (event.getScreenX() + xOffset), (int) (event.getScreenY() + yOffset));
                    }
                });
            }
        });
    }
}
