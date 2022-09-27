package com.glaikunt.framework.application3d.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.glaikunt.framework.application3d.ApplicationResources;
import com.glaikunt.framework.application3d.scene.Actor;

public class BoxActor extends Actor {

    private ModelInstance box;

    public BoxActor(ApplicationResources applicationResources) {
        this(applicationResources, new Vector3(), 1, 1, Color.WHITE);
    }

    public BoxActor(ApplicationResources applicationResources, Vector3 initPos, float scl, float opacity, Color colour) {

        BlendingAttribute blendingAttribute = new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        blendingAttribute.opacity = opacity;

        ModelBuilder modelBuilder = new ModelBuilder();
        this.box = new ModelInstance(modelBuilder.createBox(1, 1, 1,  new Material(blendingAttribute, ColorAttribute.createDiffuse(colour)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.TextureCoordinates), 0, 0, 0);
        this.box.transform.scl(scl);
        this.box.transform.setToTranslation(initPos.x, initPos.y, initPos.z);
    }

    @Override
    public void draw(ModelBatch batch, Environment environment) {

        batch.render(box, getStage().getEnvironment());
    }

    @Override
    public void draw(ModelBatch batch) {

        batch.render(box);
    }

    @Override
    public void act(float delta) {

    }

    public ModelInstance getBox() {
        return box;
    }
}
