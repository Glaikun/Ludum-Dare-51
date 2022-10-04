package com.glaikunt.framework.esc.component.animation;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.utils.IntMap;

import java.util.List;

public class ModelAnimationComponent implements Component {

    private IntMap<Animation<ModelInstance>> entries = new IntMap<>();
    private int currentAnimationId = 0;
    private ModelInstance currentFrame;
    private float stateTime;
    private float framerate;
    private boolean playing;
    private boolean xFlip;
    private boolean yFlip;

    public ModelAnimationComponent(ModelAnimationComponent animationComponent) {

        this.entries = animationComponent.entries;
        this.currentAnimationId = animationComponent.currentAnimationId;
        this.currentFrame = animationComponent.currentFrame;
        this.stateTime = animationComponent.stateTime;
        this.framerate = animationComponent.framerate;
        this.playing = animationComponent.playing;
    }

    public ModelAnimationComponent(List<Model> models) {
        ModelInstance[] modelInstances = new ModelInstance[models.size()];
        for (int i = 0; i < models.size(); i++) {
            modelInstances[i] = new ModelInstance(models.get(i));
        }

        framerate = 0.095f;

        // Initialize the Animation with the frame interval and array of frames
        Animation<ModelInstance> animation = new Animation<>(framerate, modelInstances);
        entries.put(0, animation);
        animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        currentAnimationId = 0;
        setPlaying(true);
        setCurrentFrame(getCurrentAnimation().getKeyFrame(getStateTime()));
    }

    public ModelAnimationComponent(IntMap<Model> textures) {
        setup(textures);
    }

    public ModelAnimationComponent() {

    }

    public void setup(IntMap<Model> models) {
        ModelInstance[] frames = new ModelInstance[models.size];
        for (IntMap.Entry<Model> texture : models) {
            frames[texture.key] = new ModelInstance(texture.value);
            framerate = 0.095f;

            // Initialize the Animation with the frame interval and array of frames
            Animation<ModelInstance> animation = new Animation<>(framerate, frames);
            entries.put(texture.key, animation);
            animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        }

        currentAnimationId = 0;
        setPlaying(true);
        setCurrentFrame(getCurrentAnimation().getKeyFrame(getStateTime()));
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public int getCurrentFrameIndex() {
        return getCurrentAnimation().getKeyFrameIndex(getStateTime());
    }

    public Animation<ModelInstance> getCurrentAnimation() {
        return entries.get(currentAnimationId);
    }

    public Animation.PlayMode getPlayMode() {
        return getCurrentAnimation().getPlayMode();
    }

    public void setPlayMode(Animation.PlayMode playMode) {
        setStateTime(0);
        this.getCurrentAnimation().setPlayMode(playMode);
    }

    public boolean isAnimationFinished() {
        return entries.get(currentAnimationId).isAnimationFinished(getStateTime());
    }

    public float getFramerate() {
        return framerate;
    }

    public void setFramerate(float framerate) {
        for (Animation<ModelInstance> animation : entries.values()) {
            animation.setFrameDuration(framerate);
        }
        this.framerate = framerate;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public IntMap<Animation<ModelInstance>> getEntries() {
        return entries;
    }

    public void setEntries(IntMap<Animation<ModelInstance>> entries) {
        this.entries = entries;
    }

    public int getCurrentAnimationId() {
        return currentAnimationId;
    }

    public void setCurrentAnimationId(int currentAnimationId) {
        this.currentAnimationId = currentAnimationId;
        setCurrentFrame(getCurrentAnimation().getKeyFrame(getStateTime()));
    }

    public void reset() {
        setStateTime(0);
        setCurrentFrame(getCurrentAnimation().getKeyFrame(getStateTime()));
    }

    public ModelInstance getCurrentFrame() {
        return currentFrame;
    }

    public ModelInstance getLastFrame() {
        return getCurrentAnimation().getKeyFrame(getCurrentAnimation().getAnimationDuration());
    }

    public void setCurrentFrame(ModelInstance currentFrame) {
        this.currentFrame = currentFrame;
    }

    public boolean isxFlip() {
        return xFlip;
    }

    public void setxFlip(boolean xFlip) {
        this.xFlip = xFlip;
    }

    public boolean isyFlip() {
        return yFlip;
    }

    public void setyFlip(boolean yFlip) {
        this.yFlip = yFlip;
    }
}
