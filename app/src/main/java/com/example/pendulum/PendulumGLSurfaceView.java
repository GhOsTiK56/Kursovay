package com.example.pendulum;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class PendulumGLSurfaceView extends GLSurfaceView {
    private final PendulumRenderer renderer;

    public PendulumGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        renderer = new PendulumRenderer(context);
        setRenderer(renderer);
        setRenderMode(RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            renderer.resetPendulum();
            return true;
        }
        return super.onTouchEvent(event);
    }
}
