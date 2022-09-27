package com.glaikunt.framework.esc.component.text;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.glaikunt.framework.application.TickTimer;
import com.glaikunt.framework.esc.component.common.PositionComponent;

public class DelayedTextComponent implements TextComponent {

    private String text, deltaText = "";
    private TickTimer delay, disableTimer = new TickTimer(3f);
    private BitmapFont font;
    private GlyphLayout layout;
    private Color colour;
    private float targetWidth = 0;
    private int align = Align.left;
    private boolean wrap = false;
    private boolean finished, visible, paused;
    private PositionComponent pos;
    private boolean shake;
    private PositionComponent shakePos = new PositionComponent(0, 0);

    public DelayedTextComponent() {

    }

    public DelayedTextComponent(DelayedTextComponent text) {
        setFont(text.getFont());
        setLayout(new GlyphLayout());
        setDelay(new TickTimer(text.getDelay().getTargetTime()));
        setColour(text.getColour());
        setTargetWidth(text.getTargetWidth());
        setWrap(text.isWrap());
        setAlign(text.getAlign());
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDeltaText() {
        return deltaText;
    }

    public void setDeltaText(String deltaText) {
        this.deltaText = deltaText;
    }

    public TickTimer getDelay() {
        return delay;
    }

    public Color getColour() {
        return colour;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }

    public void setDelay(TickTimer delay) {
        this.delay = delay;
    }

    public BitmapFont getFont() {
        return font;
    }

    public void setFont(BitmapFont font) {
        this.font = font;
    }

    public GlyphLayout getLayout() {
        return layout;
    }

    public void setLayout(GlyphLayout layout) {
        this.layout = layout;
    }

    public float getTargetWidth() {
        return targetWidth;
    }

    public void setTargetWidth(float targetWidth) {
        this.targetWidth = targetWidth;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getAlign() {
        return align;
    }

    public void setAlign(int align) {
        this.align = align;
    }

    public boolean isWrap() {
        return wrap;
    }

    public void setWrap(boolean wrap) {
        this.wrap = wrap;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    public TickTimer getDisableTimer() {
        return disableTimer;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setPos(PositionComponent pos) {
        this.pos = pos;
    }

    public PositionComponent getPos() {
        return pos;
    }

    public void setDisableTimer(TickTimer disableTimer) {
        this.disableTimer = disableTimer;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isShake() {
        return shake;
    }

    public void setShake(boolean shake) {
        this.shake = shake;
    }

    public PositionComponent getShakePos() {
        return shakePos;
    }

    public void setShakePos(PositionComponent shakePos) {
        this.shakePos = shakePos;
    }
}
