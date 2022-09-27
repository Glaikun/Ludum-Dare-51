package com.glaikunt.framework.esc.component.input;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Input;

/**
 * This class automatically checks the Left Mouse Click && ui mouse position
 */
public class SelectableComponent implements Component {

    private boolean hoveredOver;
    private boolean justPressed;

    private int input = Input.Buttons.LEFT;
    private boolean uiMousePositionCheck = true;
    private boolean frontMousePositionCheck = false;

    private boolean disabled;

    public boolean isHoveredOver() {
        return hoveredOver;
    }

    public void setHoveredOver(boolean hoveredOver) {
        this.hoveredOver = hoveredOver;
    }

    public boolean isJustPressed() {
        return justPressed;
    }

    public void setJustPressed(boolean justPressed) {
        this.justPressed = justPressed;
    }

    public int getInput() {
        return input;
    }

    public void setInput(int input) {
        this.input = input;
    }

    public boolean isUiMousePositionCheck() {
        return uiMousePositionCheck;
    }

    public void setUiMousePositionCheck(boolean uiMousePositionCheck) {
        this.uiMousePositionCheck = uiMousePositionCheck;
    }

    public boolean isFrontMousePositionCheck() {
        return frontMousePositionCheck;
    }

    public void setFrontMousePositionCheck(boolean frontMousePositionCheck) {
        this.frontMousePositionCheck = frontMousePositionCheck;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isDisabled() {
        return disabled;
    }
}
