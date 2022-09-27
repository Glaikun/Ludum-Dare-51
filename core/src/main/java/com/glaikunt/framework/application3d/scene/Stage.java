package com.glaikunt.framework.application3d.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.SnapshotArray;
import com.glaikunt.framework.shaders.WireframeShader;

public class Stage extends InputAdapter implements Disposable {

    private final ModelBatch shadowBatch, batch, wireframeBatch;
    private final Camera camera;
    private final Environment environment;

    private final DirectionalShadowLight shadowLight;

    private final SnapshotArray<Actor> children;

    private boolean enableCameraMovement = false;
    private boolean enableLighting = true;
    private boolean enableWireframe = false;

    private final Vector3 tmp = new Vector3();

    public Stage(float fieldOfViewY, float viewportWidth, float viewportHeight) {
        // camera work
        this.camera = new PerspectiveCamera(fieldOfViewY, viewportWidth, viewportHeight);
        this.camera.position.set(0, 1, 3);
//        this.camera.rotate(Vector3.Y, -1f);
        this.camera.lookAt(this.camera.position);
        this.camera.near = 1;
        this.camera.far = 300;
        this.camera.update();

        this.environment = new Environment();
        this.environment.set(new ColorAttribute(ColorAttribute.AmbientLight, .4f, .4f, .4f, 1f));
        this.environment.set(new ColorAttribute(ColorAttribute.Fog, .8f, .8f, .8f, 1f));
        this.environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));


        if (enableLighting) {
            this.shadowLight = new DirectionalShadowLight(1024, 1024, 60, 60, 1f, 300);
            this.shadowLight.set(0.8f, 0.8f, 0.8f, -1f, -.8f, -.2f);
            this.environment.add(shadowLight);
            this.environment.shadowMap = shadowLight;
            this.shadowBatch = new ModelBatch(new DepthShaderProvider());
        } else {
            this.shadowLight = null;
            this.shadowBatch = null;
        }

        this.batch = new ModelBatch();

        if (enableWireframe) {
            this.wireframeBatch = new ModelBatch(new DefaultShaderProvider() {
                @Override
                protected Shader createShader(Renderable renderable) {
                    return new WireframeShader(renderable, config);
                }
            });
        } else {
            this.wireframeBatch = null;
        }

        this.children = new SnapshotArray<>(true, 4, Actor.class);
    }

    public Stage() {
        this(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void act(float delta) {

        if (isEnableCameraMovement()) {
            cameraMovement(delta);
        }

        camera.update(true);

        Actor[] actors = children.begin();
        for (int i = 0, n = children.size; i < n; i++) {
            actors[i].act(delta);

            if (actors[i].isRemove()) {
                children.removeIndex(i);
            }
        }
        children.end();
    }

    public void draw() {

        Actor[] actors = children.begin();

        if (enableWireframe) {
            wireframeBatch.begin(getCamera());
            for (int i = 0, n = children.size; i < n; i++) {
                if (!actors[i].isVisible()) {
                    continue;
                }
                actors[i].draw(wireframeBatch);
            }
            wireframeBatch.end();
        }

        if (enableLighting) {
            shadowLight.begin(Vector3.Zero, getCamera().direction);
            shadowBatch.begin(shadowLight.getCamera());
            for (int i = 0, n = children.size; i < n; i++) {
                if (!actors[i].isVisible()) {
                    continue;
                }
                actors[i].draw(shadowBatch);
            }
            shadowBatch.end();
            shadowLight.end();
        }

        batch.begin(getCamera());
        for (int i = 0, n = children.size; i < n; i++) {
            if (!actors[i].isVisible()) {
                continue;
            }
            actors[i].draw(batch, environment);
        }
        children.end();
        batch.end();
    }

    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    private void cameraMovement(float delta) {

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            tmp.set(camera.direction).crs(camera.up).nor().scl(delta * -10);
            camera.position.add(tmp);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            tmp.set(camera.direction).crs(camera.up).nor().scl(delta * 10);
            camera.position.add(tmp);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            tmp.set(camera.up).nor().scl(delta * 10);
            camera.position.add(tmp);

        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            tmp.set(camera.up).nor().scl(delta * -10);
            camera.position.add(tmp);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            tmp.set(camera.direction).nor().scl(10 * delta);
            camera.position.add(tmp);
        } else if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            tmp.set(camera.direction).nor().scl(-10 * delta);
            camera.position.add(tmp);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.direction.rotate(camera.up, 45 * delta).nor();
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.direction.rotate(camera.up, -45 * delta).nor();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            tmp.set(camera.direction).crs(camera.up).nor();
            camera.direction.rotate(tmp, 45 * delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            tmp.set(camera.direction).crs(camera.up).nor();
            camera.direction.rotate(tmp, -45 * delta);
        }
    }

    public Camera getCamera() {
        return camera;
    }

    public ModelBatch getBatch() {
        return batch;
    }

    public void addActor(Actor actor) {
        actor.setStage(this);
        children.add(actor);
    }

    public Environment getEnvironment() {
        return environment;
    }

    public DirectionalShadowLight getShadowLight() {
        return shadowLight;
    }

    public SnapshotArray<Actor> getChildren() {
        return children;
    }

    public boolean isEnableCameraMovement() {
        return enableCameraMovement;
    }

    public void enableCameraMovement(boolean enableCameraMovement) {
        this.enableCameraMovement = enableCameraMovement;
    }

    @Override
    public void dispose() {

        batch.dispose();
    }

    public void clear() {
        children.clear();
    }
}
