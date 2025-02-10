package com.example.pendulum;

import android.opengl.GLES20;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class PendulumGeometry {
    private static final int VERTEX_COUNT = 2;
    private FloatBuffer vertexBuffer;

    public void init() {
        ByteBuffer bb = ByteBuffer.allocateDirect(VERTEX_COUNT * 3 * 4);
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

        GLES20.glDrawArrays(GLES20.GL_LINES, 0, VERTEX_COUNT);
        GLES20.glDisableVertexAttribArray(shader.getPositionHandle());
    }

    private void updateVertices(float angle) {
        float x = (float) Math.sin(angle) * 0.5f;
        float y = -(float) Math.cos(angle) * 0.5f;

        float[] vertices = {
                0.0f, 0.0f, 0.0f,
                x, y, 0.0f
        };

        vertexBuffer.clear();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
    }
}
