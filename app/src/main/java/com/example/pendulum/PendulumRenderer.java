package com.example.pendulum;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class PendulumRenderer implements GLSurfaceView.Renderer {
    private final PendulumModel pendulumModel;
    private  PendulumGraphView graphView;
    private final ShaderProgram shaderProgram;
    private final PendulumGeometry pendulumGeometry;
    private final BallGeometry ballGeometry;
    private long startTime = 0;
    public PendulumRenderer(Context context, PendulumGraphView graphView) {
        this.pendulumModel = new PendulumModel();
        this.graphView = graphView;
        shaderProgram = new ShaderProgram();
        pendulumGeometry = new PendulumGeometry();
        ballGeometry = new BallGeometry(0.05f, 20);
    }
    public void setGraphView(PendulumGraphView graphView) {
        this.graphView = graphView;
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

        // Получаем текущий угол маятника
        float angle = pendulumModel.getAngle();

        // Получаем текущее время
        long currentTime = System.currentTimeMillis();

        // Передаем угол и время в график
        if (graphView != null) {
            graphView.addPoint(angle, currentTime);
        }

        // Отрисовка маятника
        shaderProgram.use();
        pendulumGeometry.draw(shaderProgram, angle, pendulumModel.getLength());
        ballGeometry.draw(shaderProgram, angle, pendulumModel.getLength());
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }
    public void startAnimation() {
        pendulumModel.setRunning(true);
    }

    public void stopAnimation() {
        pendulumModel.setRunning(false);
    }

    public void setPendulumLength(float length) {
        pendulumModel.setLength(length);
    }

    public void setGravity(float gravity) {
        pendulumModel.setGravity(gravity);
    }

    public void setInitialAngle(float angle) {
        pendulumModel.setInitialAngle(angle);
    }
    public void resetPendulum() {
        pendulumModel.reset();
    }
}
