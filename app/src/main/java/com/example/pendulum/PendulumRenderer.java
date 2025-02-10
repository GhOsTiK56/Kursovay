package com.example.pendulum;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class PendulumRenderer implements GLSurfaceView.Renderer {
    private final PendulumModel pendulumModel;
    private final ShaderProgram shaderProgram;
    private final PendulumGeometry pendulumGeometry;
    private final BallGeometry ballGeometry;

    public PendulumRenderer(Context context) {
        pendulumModel = new PendulumModel();
        shaderProgram = new ShaderProgram();
        pendulumGeometry = new PendulumGeometry();
        ballGeometry = new BallGeometry(0.05f, 20);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        shaderProgram.init();
        pendulumGeometry.init();
        ballGeometry.init();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        pendulumModel.update();

        shaderProgram.use();
        pendulumGeometry.draw(shaderProgram, pendulumModel.getAngle());
        ballGeometry.draw(shaderProgram, pendulumModel.getAngle());
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    public void resetPendulum() {
        pendulumModel.reset();
    }
}
