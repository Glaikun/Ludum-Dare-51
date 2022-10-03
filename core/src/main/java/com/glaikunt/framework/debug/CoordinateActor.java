//package com.glaikunt.framework.debug;
//
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.PerspectiveCamera;
//import com.badlogic.gdx.graphics.VertexAttributes;
//import com.badlogic.gdx.graphics.g3d.Material;
//import com.badlogic.gdx.graphics.g3d.ModelBatch;
//import com.badlogic.gdx.graphics.g3d.ModelInstance;
//import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
//import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
//import com.glaikunt.framework.application3d.ApplicationResources;
//import com.glaikunt.framework.application3d.scene.Actor;
//
//public class CoordinateActor extends Actor {
//
//    private ModelInstance coordinates, box;
//    private PerspectiveCamera camera;
//
//    public CoordinateActor(ApplicationResources applicationResources, PerspectiveCamera camera) {
//
//        this.camera = camera;
//        BlendingAttribute blendingAttribute = new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//        blendingAttribute.opacity = .2f;
//        ModelBuilder modelBuilder = new ModelBuilder();
//        this.box = new ModelInstance(modelBuilder.createBox(1, 1, 1, new Material(blendingAttribute), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal), 0, 0, 0);
//        this.coordinates = new ModelInstance(modelBuilder.createXYZCoordinates(1f, new Material(), VertexAttributes.Usage.Position | VertexAttributes.Usage.ColorPacked), box.transform);
//    }
//
//    @Override
//    public void draw(ModelBatch batch) {
//
//        batch.render(coordinates, getStage().getEnvironment());
//        batch.render(box, getStage().getEnvironment());
//    }
//
//    @Override
//    public void act(float delta) {
//
//    }
//}
