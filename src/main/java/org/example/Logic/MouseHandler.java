package org.example.Logic;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class MouseHandler implements EventHandler<MouseEvent> {

    public boolean attack;

    @Override
    public void handle(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY) {
            if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
                attack = true;
            } else if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
                attack = false;
            }
        }
    }
}
