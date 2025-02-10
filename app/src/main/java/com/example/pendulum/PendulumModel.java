package com.example.pendulum;

public class PendulumModel {
    private static final float G = 9.81f;
    private static final float LENGTH = 1.0f;
    private static final float DT = 0.016f;

    private float angle = (float) Math.PI / 4;
    private float angularVelocity = 0.0f;

    public void update() {
        rungeKuttaStep();
    }

    public void reset() {
        angle = (float) Math.PI / 3;
        angularVelocity = 0.0f;
    }

    public float getAngle() {
        return angle;
    }

    private void rungeKuttaStep() {
        // Реализация метода Рунге-Кутты 4-го порядка
        float k1_v = -G / LENGTH * (float) Math.sin(angle) * DT;
        float k1_x = angularVelocity * DT;

        float k2_v = -G / LENGTH * (float) Math.sin(angle + 0.5f * k1_x) * DT;
        float k2_x = (angularVelocity + 0.5f * k1_v) * DT;

        float k3_v = -G / LENGTH * (float) Math.sin(angle + 0.5f * k2_x) * DT;
        float k3_x = (angularVelocity + 0.5f * k2_v) * DT;

        float k4_v = -G / LENGTH * (float) Math.sin(angle + k3_x) * DT;
        float k4_x = (angularVelocity + k3_v) * DT;

        angularVelocity += (k1_v + 2*k2_v + 2*k3_v + k4_v) / 6.0f;
        angle += (k1_x + 2*k2_x + 2*k3_x + k4_x) / 6.0f;
    }
}
