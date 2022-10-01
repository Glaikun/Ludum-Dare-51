package com.glaikunt.framework.esc.system.physics;

import com.badlogic.ashley.core.Component;
import com.glaikunt.framework.application.Rectangle;
import com.glaikunt.framework.esc.component.common.ContactComponent;

import java.util.LinkedList;
import java.util.List;

public class BodyComponent extends Rectangle implements Component {

    private final List<ContactComponent> beforeContacts = new LinkedList<>();
    private final List<ContactComponent> afterContacts = new LinkedList<>();
    private final List<BodyComponent> bodyContacts = new LinkedList<>();
    private BodyType bodyType;

    public List<ContactComponent> getBeforeContacts() {
        return beforeContacts;
    }

    public List<ContactComponent> getAfterContacts() {
        return afterContacts;
    }

    public List<BodyComponent> getBodyContacts() {
        return bodyContacts;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }
}
