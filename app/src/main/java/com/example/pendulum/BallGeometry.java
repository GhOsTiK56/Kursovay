package com.example.pendulum;

import android.opengl.GLES20;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class BallGeometry {
    private final float radius;
    private final int circlePoints;
    private FloatBuffer vertexBuffer;

    public BallGeometry(float radius, int circlePoints) {
        this.radius = radius;
        this.circlePoints = circlePoints;
    }

    public void init() {
        ByteBuffer bb = ByteBuffer.allocateDirect((circlePoints + 2) * 3 * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
    }

    public void draw(ShaderProgram shader, float angle) {
        updateVertices(angle);

        GLES20.glEnableVertexAttribArray(shader.getPositionHandle());
        GLES20.glVertexAttribPointer(
                shader.getPositionHandle(),
                3,
                GLES20.GL_FLOAT,
                false,
                0,
                vertexBuffer
        );

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, circlePoints + 2);
        GLES20.glDisableVertexAttribArray(shader.getPositionHandle());
    }

    private void updateVertices(float angle) {
        float cx = (float) Math.sin(angle) * 0.5f;
        float cy = -(float) Math.cos(angle) * 0.5f;

        float[] vertices = new float[(circlePoints + 2) * 3];
        vertices[0] = cx;
        vertices[1] = cy;
        vertices[2] = 0.0f;

        for (int i = 1; i <= circlePoints + 1; i++) {
            float theta = (float) (2 * Math.PI * (i - 1) / circlePoints);
            vertices[i * 3] = cx + radius * (float) Math.cos(theta);
            vertices[i * 3 + 1] = cy + radius * (float) Math.sin(theta);
            vertices[i * 3 + 2] = 0.0f;
        }

        vertexBuffer.clear();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
    }
}
