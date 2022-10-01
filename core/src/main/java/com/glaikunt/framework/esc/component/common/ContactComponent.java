package com.glaikunt.framework.esc.component.common;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.glaikunt.framework.esc.system.physics.BodyType;

import java.util.Objects;

public class ContactComponent implements Component {

    private final Rectangle interaction = new Rectangle();
    private final Vector2 normal = new Vector2();
    private Rectangle bodyA, bodyB;
    private BodyType bodyAType;
    private BodyType bodyBType;

    public Rectangle getInteraction() {
        return interaction;
    }

    public void setInteraction(Rectangle interaction) {
        this.interaction.set(interaction);
    }

    public Vector2 getNormal() {
        return normal;
    }

    public Rectangle getBodyA() {
        return bodyA;
    }

    public void setBodyA(Rectangle bodyA) {
        this.bodyA = bodyA;
    }

    public Rectangle getBodyB() {
        return bodyB;
    }

    public void setBodyB(Rectangle bodyB) {
        this.bodyB = bodyB;
    }

    public BodyType getBodyAType() {
        return bodyAType;
    }

    public BodyType getBodyBType() {
        return bodyBType;
    }

    public void setBodyAType(BodyType bodyAType) {
        this.bodyAType = bodyAType;
    }

    public void setBodyBType(BodyType bodyBType) {
        this.bodyBType = bodyBType;
    }

    @Override
    public String toString() {
        return "ContactComponent{" +
                "interaction=" + interaction +
                ", normal=" + normal +
                ", bodyA=" + bodyA +
                ", bodyB=" + bodyB +
                ", bodyAType=" + bodyAType +
                ", bodyBType=" + bodyBType +
                '}';
    }
}
