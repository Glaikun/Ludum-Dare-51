package com.glaikunt.framework.credits;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.glaikunt.framework.Display2D;
import com.glaikunt.framework.application.ApplicationResources;

import static com.glaikunt.framework.cache.FontCache.BASIC_FONT;

public class CreditTextActor extends Actor {

    private final BitmapFont baseFont;
    private final GlyphLayout layout;
    private float alpha = 0;
    private final Vector2 pos;
    private boolean toggle;
    private final String txt;

    public CreditTextActor(ApplicationResources applicationResources, String txt) {

        this.baseFont = applicationResources.getCacheRetriever().getFontCache(BASIC_FONT);
        this.layout = new GlyphLayout();
        this.layout.setText(baseFont, txt, new Color(1f, 1f, 1f, alpha), 0, Align.left, false);
        this.txt = txt;
        this.pos = new Vector2(((Display2D.WORLD_WIDTH / 2f) - (layout.width / 2)), ((Display2D.WORLD_HEIGHT / 2) - (layout.height / 2)));

    }

    @Override
    public void act(float delta) {

        pos.y -= 25 * delta;

        if (!toggle && alpha < 1) {
            alpha += .2 * delta;
        } else if (!toggle) {
            toggle = true;
        }

        if (toggle && alpha > 0) {
            alpha -= .2 * delta;
        } else if (toggle) {
            remove();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {


        layout.setText(baseFont, txt, new Color(.2f, .1f, .4f, alpha), 0, Align.left, false);
        baseFont.draw(batch, layout, pos.x, pos.y);
    }
}
