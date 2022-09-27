package com.glaikunt.framework.factory;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class BodyFactory {

//    https://github.com/libgdx/libgdx/wiki/box2d#kinematic-bodies
//    https://github.com/GuidebeeGameEngine/Box2D/wiki/Shape-Types
//    https://www.codeandweb.com/physicseditor

//    Vector2 vel = this.player.body.getLinearVelocity();
//    Vector2 pos = this.player.body.getPosition();
//
//// apply left impulse, but only if max velocity is not reached yet
//      if (Gdx.input.isKeyPressed(Keys.A) && vel.x > -MAX_VELOCITY) {
//        this.player.body.applyLinearImpulse(-0.80f, 0, pos.x, pos.y, true);
//    }
//
//// apply right impulse, but only if max velocity is not reached yet
//      if (Gdx.input.isKeyPressed(Keys.D) && vel.x < MAX_VELOCITY) {
//        this.player.body.applyLinearImpulse(0.80f, 0, pos.x, pos.y, true);
//    }

    public static Body createDynamicBody(World world, Shape shape, BodyDef.BodyType bodyType, Vector2 pos, float density, float friction, float restitution, Object userData) {
        // First we create a body definition
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = bodyType;
        // Set our body's starting position in the world
        bodyDef.position.set(pos.x, pos.y);

        // Create our body in the world using our body definition
        Body body = world.createBody(bodyDef);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution; // Make it bounce a little bit

        body.setUserData(userData);

        // Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        shape.dispose();

        return body;
    }

    public static Shape createCircle(float radius) {
        // Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(radius);
        return circle;
    }

    public static Shape createBox(float width, float height) {
        // Create a boxShape shape
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width, height);
        return polygonShape;
    }

    public static Shape createTriangle(float width, float height) {
        // Create a triangleShape shape
        PolygonShape triangleShape = new PolygonShape();
        triangleShape.set(getTriangleVertices());
        return triangleShape;
    }

    public static Shape createHexagon(float width, float height) {
        // Create a hexagonShape shape
        PolygonShape hexagonShape = new PolygonShape();
        hexagonShape.set(getHexagonVertices());
        return hexagonShape;
    }

    private static float[] getHexagonVertices() {
        float[] vertices = new float[12];
        vertices[0] = 0;
        vertices[1] = -16;
        vertices[2] = 14;
        vertices[3] = -8;
        vertices[4] = 14;
        vertices[5] = 8;

        vertices[6] = 0;
        vertices[7] = 16;
        vertices[8] = -14;
        vertices[9] = 8;
        vertices[10] = -14;
        vertices[11] = -8;

        return vertices;

    }

    private static float[] getTriangleVertices() {
        float[] vertices = new float[6];
        vertices[0] = -16;
        vertices[1] = -16;
        vertices[2] = 16;
        vertices[3] = -16;
        vertices[4] = 0;
        vertices[5] = 16;

        return vertices;

    }
}
