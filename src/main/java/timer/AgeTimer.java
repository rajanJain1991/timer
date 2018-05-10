package timer;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import timer.controller.TimerMainViewController;

import javax.swing.*;
import java.text.DecimalFormat;

import static timer.utils.DialogUtils.makeJFrameMovable;

/**
 * Created by Computer on 24-06-2017.
 */
public class AgeTimer extends JFrame {

    public static final DecimalFormat AGE_FORMAT = new DecimalFormat("00.00000");
    private double xOffset, yOffset;
    private Stage primaryStage;
    private static Scene scene;
    private static TimerMainViewController controller;

    JFXPanel fxContainer;

    public AgeTimer(Scene scene) {
        fxContainer = new JFXPanel();
        fxContainer.setScene(scene);
        getContentPane().add(fxContainer);

        Screen primaryScreen = Screen.getPrimary();
//            int width = (int)primaryScreen.getBounds().getWidth();
//            int height = (int)(primaryScreen.getBounds().getHeight()/2);
        setSize(110, 35);
        setLocation((int) (primaryScreen.getBounds().getWidth() - 150), 70);
        setType(Type.UTILITY);
        setUndecorated(true);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


//    @Override
//    public void start(Stage firstStage) throws Exception {
//        this.primaryStage = firstStage;
//        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/TimerMainView.fxml"));
//
//        Scene scene = new Scene(root, 100,35);
//
//        makeStageMovable(root, primaryStage);
//        primaryStage.setMaxWidth(100);
//        primaryStage.setMinWidth(100);
//        primaryStage.setMaxHeight(35);
//        primaryStage.setMinHeight(35);
//        primaryStage.setResizable(false);
//        primaryStage.getIcons().clear();
//        primaryStage.initStyle(StageStyle.TRANSPARENT);
//
//        primaryStage.setScene(scene);
//        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
//        primaryStage.setX(primScreenBounds.getWidth() - 150);
//        primaryStage.setY(70);
//        primaryStage.show();
//
//    }
//
//
//    @Override
//    public void stop() {
//
//    }


    public static void main(String[] args) {
//        launch(args);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JFXPanel();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        initScene();
                        AgeTimer hostWindow = new AgeTimer(scene);
                        controller.initDetails(hostWindow);
                        makeJFrameMovable(scene.getRoot(),hostWindow);
                        hostWindow.setVisible(true);
                    }
                });

            }
        });
    }

    public static void initScene() {
        try {
            FXMLLoader loader = new FXMLLoader(AgeTimer.class.getClassLoader().getResource("views/TimerMainView.fxml"));
            Parent root = loader.load();
            controller = (TimerMainViewController)loader.getController();
            scene = new Scene(root);
        } catch (Exception e) {

        }
    }
}

