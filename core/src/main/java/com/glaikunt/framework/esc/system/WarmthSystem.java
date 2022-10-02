package com.glaikunt.framework.esc.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.glaikunt.framework.esc.component.common.WarmthComponent;

public class WarmthSystem extends EntitySystem {

    private static final float DEPLETION_RATE_UNITS_PER_SECOND = 10f; // ergo - 10 seconds to depletion, right?
    private static final float INDOORS_UNITS_PER_SECOND = 5f;
    private static final float HEATSOURCE_UNITS_PER_SECOND = 15f; // obvs indoors with a heatsource would be a+b
    private final ImmutableArray<Entity> warmthEntities;

    private final ComponentMapper<WarmthComponent> wc = ComponentMapper.getFor(WarmthComponent.class);

    public WarmthSystem(Engine engine) {
        warmthEntities = engine.getEntitiesFor(Family.all(WarmthComponent.class).get());
    }

    @Override
    public void update(float delta) {
        for (int i = 0; i < warmthEntities.size(); ++i) {

            Entity entity = warmthEntities.get(i);
            WarmthComponent w = wc.get(entity);

            // if outdoors, deplete
//            if (w.isOutside()) {
//                w.setWarmth(w.getWarmth()-(DEPLETION_RATE_UNITS_PER_SECOND*delta));
//            } else {
//                w.setWarmth(w.getWarmth()+(INDOORS_UNITS_PER_SECOND*delta));
//            }
//
//            if (w.isNearHeatSource()) {
//                w.setWarmth(w.getWarmth() + (HEATSOURCE_UNITS_PER_SECOND * delta));
//            }

//            Gdx.app.log("DEBUG", "warmth: "+w);
        }
    }
}
