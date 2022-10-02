package com.glaikunt.framework.game.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.glaikunt.framework.Ansi;
import com.glaikunt.framework.esc.system.physics.BodyComponent;

public class PlayerNearbyConditionTask extends AbstractLeafTask {

    private static final float RANGE = 32*6; // tiles?
    private final EasyAccessComponent eac;
    private final BodyComponent playerBC;
    private final BodyComponent bc;
    private Vector2 tmpVector2a = new Vector2();
    private Vector2 tmpVector2b = new Vector2();
    public PlayerNearbyConditionTask(Entity entity) {
        super(entity);
        this.eac = entity.getComponent(EasyAccessComponent.class);
        this.bc = entity.getComponent(BodyComponent.class);
        this.playerBC = eac.getPlayerEntity().getComponent(BodyComponent.class);
    }

    @Override
    public Status execute() {
        Gdx.app.log("DEBUG", Ansi.red("[AI] ")+Ansi.yellow("execute PlayerNearbyConditionTask"));
        bc.getCenter(tmpVector2a);
        playerBC.getCenter(tmpVector2b);
        // TODO not just one sqrt warning but two if you keep this debug!
        Gdx.app.log("DEBUG", Ansi.red("  |- ")+Ansi.purple("P:"+playerBC+" E:"+bc+" dist: "+tmpVector2b.sub(tmpVector2a).len()+" testing against range "+RANGE));
        if (tmpVector2b.sub(tmpVector2a).len() < RANGE) {
            Gdx.app.log("DEBUG", Ansi.red("  |- ")+Ansi.green("Status.SUCCEEDED"));
            return Status.SUCCEEDED;
        } else {
            Gdx.app.log("DEBUG", Ansi.red("  |- ")+Ansi.green("Status.FAILED"));
            return Status.FAILED;
        }
    }
}
