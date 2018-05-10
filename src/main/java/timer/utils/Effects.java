package timer.utils;

import javafx.event.EventHandler;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

import java.util.List;

/**
 * Created by Computer on 25-06-2017.
 */
public class Effects {

    public static void addShadowEffect(List<? extends Region> regions) {
        for (Region region: regions) {
            //Adding the shadow when the mouse cursor is on
            region.addEventHandler(MouseEvent.MOUSE_ENTERED,
                    new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent e) {
                            ((Region) e.getSource()).setEffect(new DropShadow());
                        }
                    });
            //Removing the shadow when the mouse cursor is off
            region.addEventHandler(MouseEvent.MOUSE_EXITED,
                    new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent e) {
                            ((Region) e.getSource()).setEffect(null);
                        }
                    });
        }
    }
}
