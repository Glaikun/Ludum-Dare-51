package com.glaikunt.framework.splash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.IntMap;
import com.glaikunt.framework.Display2D;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.CommonActor;
import com.glaikunt.framework.application.TickTimer;
import com.glaikunt.framework.esc.component.animation.AnimationComponent;
import com.glaikunt.framework.esc.component.common.PositionComponent;
import com.glaikunt.framework.esc.component.common.SizeComponent;
import com.glaikunt.framework.esc.component.misc.FadeComponent;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class LogoActor extends CommonActor {

    private final AnimationComponent logo;
    private final FadeComponent.Fade fade;
    private final TickTimer completeTimer = new TickTimer(2);
    private final TickTimer startTimer = new TickTimer(2);

    public LogoActor(ApplicationResources applicationResources) {
        super(applicationResources);

        this.logo = new AnimationComponent();
        IntMap<Texture> textures = new IntMap<>();
        FileHandle[] fileArray = Gdx.files.internal("logo").list();
        List<String> filesDir = new LinkedList<>();
        for (FileHandle fileHandle : fileArray) {
            if (!(fileHandle.isDirectory())) {
                String path = fileHandle.path();
                filesDir.add(path);
            }
        }
        Collections.sort(filesDir);
        for (String fileDir : filesDir) {
            textures.put(filesDir.indexOf(fileDir), new Texture(Gdx.files.internal(fileDir)));
        }
        this.logo.setup(textures);
        this.logo.setPlayMode(Animation.PlayMode.NORMAL);
        this.logo.setPlaying(false);
        this.logo.setFramerate(.03f);

        this.size = new SizeComponent(logo.getCurrentFrame().getRegionWidth(), logo.getCurrentFrame().getRegionHeight());
        this.pos = new PositionComponent((Display2D.WORLD_WIDTH / 2) - (size.x / 2), (Display2D.WORLD_HEIGHT / 2) - (size.y / 2));

        FadeComponent fade = new FadeComponent();
        this.fade = new FadeComponent.Fade();
        this.fade.setMaxFade(1);
        this.fade.setFade(1); // remove if you want fade in
        this.fade.setSpeed(.5f);
//        this.fade.setFadeIn(true);
        fade.addFade(this.fade);

        this.entity.add(fade);
        this.entity.add(logo);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.setColor(.5f, .5f, .5f, .5f * fade.getFade());
        batch.draw(logo.getCurrentFrame(), getX()+3, getY()-3, getWidth(), getHeight());

        batch.setColor(1, 1, 1, 1 * fade.getFade());
        batch.draw(logo.getCurrentFrame(), getX(), getY(), getWidth(), getHeight());
        batch.setColor(1, 1, 1, 1);
    }

    @Override
    public void act(float delta) {

        startTimer.tick(delta);
        if (!logo.isPlaying() && fade.getFade() >= fade.getMaxFade() && startTimer.isTimerPassedTarget()) {
            logo.setPlaying(true);
        }

        if (logo.getCurrentFrameIndex() >= logo.getCurrentAnimation().getKeyFrames().length - 1) {

            completeTimer.tick(delta);
        }
    }

    public boolean isComplete() {
        return (logo.getCurrentFrameIndex() >= logo.getCurrentAnimation().getKeyFrames().length-1) && completeTimer.isTimerEventReady();
    }
}
