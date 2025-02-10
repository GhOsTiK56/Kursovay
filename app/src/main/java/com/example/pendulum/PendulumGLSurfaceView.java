package com.example.pendulum;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class PendulumGLSurfaceView extends GLSurfaceView {
    private final PendulumRenderer renderer;

    public PendulumGLSurfaceView(Context context) {
        this(context, null);
    }

    public PendulumGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(2);
        renderer = new PendulumRenderer(context);
        setRenderer(renderer);
        setRenderMode(RENDERMODE_CONTINUOUSLY);
    }

    public void resetPendulum() {
        renderer.resetPendulum();
    }

    public void startAnimation() {
        renderer.startAnimation();
    }

    public void stopAnimation() {
        renderer.stopAnimation();
    }

    public void setPendulumLength(float length) {
        renderer.setPendulumLength(length);
    }

    public void setGravity(float gravity) {
        renderer.setGravity(gravity);
    }

    public void setInitialAngle(float angle) {
        renderer.setInitialAngle(angle);
    }
}
