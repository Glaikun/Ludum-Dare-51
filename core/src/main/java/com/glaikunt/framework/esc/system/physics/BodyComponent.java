package com.glaikunt.framework.esc.system.physics;

import com.badlogic.ashley.core.Component;
import com.glaikunt.framework.application.Rectangle;
import com.glaikunt.framework.esc.component.common.ContactComponent;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BodyComponent extends Rectangle implements Component {

    private final Map<BodyComponent, ContactComponent> contactsByBody = new LinkedHashMap<>();
    private final List<ContactComponent> beforeContacts = new LinkedList<>();
    private final List<ContactComponent> afterContacts = new LinkedList<>();
    private BodyType bodyType;

    public boolean isContactedWithFloor() {
        return contactsByBody.values().stream()
                .anyMatch(c -> c.getNormal().y <= -1);
    }

    public List<ContactComponent> getBeforeContacts() {
        return beforeContacts;
    }

    public List<ContactComponent> getAfterContacts() {
        return afterContacts;
    }

    public Map<BodyComponent, ContactComponent> getContactsByBody() {
        return contactsByBody;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }
}
