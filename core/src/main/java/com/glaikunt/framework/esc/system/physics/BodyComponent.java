package com.glaikunt.framework.esc.system.physics;

import com.badlogic.ashley.core.Component;

import com.badlogic.gdx.math.Rectangle;
import com.glaikunt.framework.esc.component.common.ContactComponent;

import java.util.*;

public class BodyComponent extends Rectangle implements Component {

    private final Map<BodyComponent, ContactComponent> contactsByBody = new LinkedHashMap<>(0);
    private final List<ContactComponent> beforeContacts = new LinkedList<>();
    private final List<ContactComponent> afterContacts = new LinkedList<>();
    private BodyType bodyType;
    private static long idCounter = 0;
    private final long id = idCounter++; // NOSONAR you want a cheap UUID this is it
    private final List<BodyType> includeList = Arrays.asList(BodyType.BLOCK, BodyType.PLAYER_ONLY_BLOCK);

    /**
     * plus the only things it's allowed to jump off from (not a heatsource for example)
     * @return true if in contact with something to jump off
     */
    public boolean isContactedWithFloor() {
        return contactsByBody.values().stream()
                .anyMatch(c -> c.getNormal().y <= -1 && includeList.contains(c.getBodyBType()));
    }

    public boolean isContactedWithPlayer() {
        return contactsByBody.values().stream()
                .anyMatch(c -> BodyType.PLAYER.equals(c.getBodyBType()));
    }

    public ContactComponent getPlayerContact() {
        return contactsByBody.values().stream()
                .filter(c -> c.getNormal().x != 0 && BodyType.PLAYER.equals(c.getBodyBType()))
                .findAny().orElse(null);
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

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BodyComponent that = (BodyComponent) o;
        return id == that.id;//uuid.equals(that.uuid);
    }

    @Override
    public int hashCode () {
//        return Objects.hash(uuid, bodyType.hashCode());
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "BodyComponent{" +
                "bodyType=" + bodyType +
                ", id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
