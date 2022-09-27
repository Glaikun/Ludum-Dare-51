package com.glaikunt.framework.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.glaikunt.framework.DynamicDisplay;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;

public class HtmlLauncher extends GwtApplication {

    @Override
    public GwtApplicationConfiguration getConfig() {
        // Resizable application, uses available space in browser
        GwtApplicationConfiguration config = new GwtApplicationConfiguration(1280, 960, true);
        Window.addResizeHandler(new ResizeListener());
        return config;
        // Fixed size application:
        //return new GwtApplicationConfiguration(480, 320);
    }

    @Override
    public ApplicationListener createApplicationListener() {
        return new DynamicDisplay();
    }

    class ResizeListener implements ResizeHandler {
        @Override
        public void onResize(ResizeEvent event) {

            int width = event.getWidth();
            int height = event.getHeight();

            // ignore 0, 0
            if (width == 0 || height == 0) {
                return;
            }

            getRootPanel().setWidth("" + width + "px");
            getRootPanel().setHeight("" + height + "px");

            getGraphics().setWindowedMode(width, height);
        }
    }
}