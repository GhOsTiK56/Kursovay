package com.example.pendulum;

public class PendulumModel {
    private volatile boolean isRunning = true;
    private float gravity = 9.81f;  // Ускорение свободного падения (м/с²)
    private float length = 1.0f;    // Длина маятника (м)
    private float dt = 0.016f;      // Шаг времени (секунды)
    private float initialAngle = (float) Math.PI / 4;  // Начальный угол (радианы)
    private float angle = initialAngle;  // Текущий угол (радианы)
    private float angularVelocity = 0.0f;  // Угловая скорость (радианы/секунду)

    public void update() {
        if (isRunning) {
            rungeKuttaStep();
        }
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public void setInitialAngle(float angle) {
        this.initialAngle = angle;
        reset();
    }

    public void reset() {
        angle = initialAngle;
        angularVelocity = 0.0f;
    }

    public float getAngle() {
        return angle;
    }

    private void rungeKuttaStep() {
        float k1_v = -gravity / length * (float) Math.sin(angle) * dt;
        float k1_x = angularVelocity * dt;

        float k2_v = -gravity / length * (float) Math.sin(angle + 0.5f * k1_x) * dt;
        float k2_x = (angularVelocity + 0.5f * k1_v) * dt;

        float k3_v = -gravity / length * (float) Math.sin(angle + 0.5f * k2_x) * dt;
        float k3_x = (angularVelocity + 0.5f * k2_v) * dt;

        float k4_v = -gravity / length * (float) Math.sin(angle + k3_x) * dt;
        float k4_x = (angularVelocity + k3_v) * dt;

        angularVelocity += (k1_v + 2*k2_v + 2*k3_v + k4_v) / 6.0f;
        angle += (k1_x + 2*k2_x + 2*k3_x + k4_x) / 6.0f;
    }
}
