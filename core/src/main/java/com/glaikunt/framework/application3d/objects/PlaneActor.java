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

public class PlaneActor extends Actor {

    private ModelInstance plane;

    public PlaneActor(ApplicationResources applicationResources) {
        this(applicationResources, new Vector3(), 1, 1, Color.WHITE);
    }

    public PlaneActor(ApplicationResources applicationResources, Vector3 initPos, float scl, float opacity, Color colour) {

        BlendingAttribute blendingAttribute = new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        blendingAttribute.opacity = opacity;

        ModelBuilder modelBuilder = new ModelBuilder();
        this.plane = new ModelInstance(modelBuilder.createRect(
                1f,0f,-1f,
                -1f,0f,-1f,
                -1f,0f,1f,
                1f,0f,1f,
                0,
                0,
                0,
                new Material(blendingAttribute, ColorAttribute.createDiffuse(colour)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.TextureCoordinates));
        this.plane.transform.scl(scl);
        this.plane.transform.setToTranslation(initPos.x, initPos.y, initPos.z);
    }

    @Override
    public void draw(ModelBatch batch, Environment environment) {

        batch.render(plane, getStage().getEnvironment());
    }

    @Override
    public void act(float delta) {

    }

    public ModelInstance getPlane() {
        return plane;
    }
}
