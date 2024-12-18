package org.example.Logic;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashSet;
import java.util.Set;

public class KeyHandler implements EventHandler<KeyEvent> {
    final private Set<KeyCode> activeKeys = new HashSet<>();
    public boolean upPressed, downPressed, rightPressed, leftPressed, jump;
    public boolean restart;
    public boolean OptionMenu;
    public boolean inventoryPressed; // Added flag to track inventory key press
    public boolean interationPressed; // Added flag to track inventory key press

    @Override
    public void handle(KeyEvent e) {
        KeyCode code = e.getCode();
        if (e.getEventType() == KeyEvent.KEY_PRESSED) {
            activeKeys.add(code);
            // Check if inventory key is pressed
            if (code == KeyCode.I) {
                inventoryPressed = true;
            }
            if (code == KeyCode.E) {
                interationPressed = true;
            }
            if (code == KeyCode.ESCAPE) {
                // Show option menu
                OptionMenu=true;
            }
        } else if (e.getEventType() == KeyEvent.KEY_RELEASED) {
            activeKeys.remove(code);
        }

        // Update the boolean flags based on active keys
        upPressed = activeKeys.contains(KeyCode.W);
        downPressed = activeKeys.contains(KeyCode.S);
        rightPressed = activeKeys.contains(KeyCode.D);
        leftPressed = activeKeys.contains(KeyCode.A);
        restart = activeKeys.contains(KeyCode.R);
        jump = activeKeys.contains(KeyCode.SPACE);
      //  inventoryPressed = activeKeys.contains(KeyCode.I);
    }
}
