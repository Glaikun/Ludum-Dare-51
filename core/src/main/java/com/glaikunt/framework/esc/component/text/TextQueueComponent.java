package com.glaikunt.framework.esc.component.text;

import com.badlogic.ashley.core.Component;
import com.glaikunt.framework.application.TickTimer;

import java.util.LinkedList;

public class TextQueueComponent implements Component {

    private boolean paused = false;
    private final LinkedList<TextComponent> queue = new LinkedList<>();
    private TickTimer swapDelay;
    private boolean enableSkipText = true;

    public LinkedList<TextComponent> getQueue() {
        return queue;
    }

    public TickTimer getSwapDelay() {
        return swapDelay;
    }

    public void setSwapDelay(TickTimer swapDelay) {
        this.swapDelay = swapDelay;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isEnableSkipText() {
        return enableSkipText;
    }

    public void setEnableSkipText(boolean enableSkipText) {
        this.enableSkipText = enableSkipText;
    }
}
