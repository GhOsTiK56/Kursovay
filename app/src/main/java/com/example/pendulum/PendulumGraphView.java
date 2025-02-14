package com.example.pendulum;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class PendulumGraphView extends View {
    private final Paint gridPaint = new Paint();
    private final Paint graphPaint = new Paint();
    private final Paint textPaint = new Paint();
    private final Path graphPath = new Path();
    private final List<Float> angles = new ArrayList<>();
    private final List<Long> times = new ArrayList<>();
    private long startTime = 0;
    private float maxAngle = (float) Math.PI / 2; // Максимальный угол для масштабирования

    public PendulumGraphView(Context context) {
        super(context);
        init();
    }

    public PendulumGraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PendulumGraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // Настройка сетки
        gridPaint.setColor(Color.LTGRAY);
        gridPaint.setStrokeWidth(1);
        gridPaint.setStyle(Paint.Style.STROKE);

        // Настройка графика
        graphPaint.setColor(Color.BLUE);
        graphPaint.setStrokeWidth(3);
        graphPaint.setStyle(Paint.Style.STROKE);
        graphPaint.setAntiAlias(true);

        // Настройка текста
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(24);
        textPaint.setAntiAlias(true);
    }

    public void addPoint(float angle, long time) {
        if (startTime == 0) {
            startTime = time; // Фиксируем начальное время
        }

        angles.add(angle);
        times.add(time - startTime); // Время относительно начала

        // Обновляем максимальный угол для масштабирования
        if (Math.abs(angle) > maxAngle) {
            maxAngle = Math.abs(angle);
        }

        invalidate(); // Перерисовываем View
    }

    public void clearGraph() {
        angles.clear();
        times.clear();
        startTime = 0;
        maxAngle = (float) Math.PI / 2;
        graphPath.reset();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (angles.isEmpty() || times.isEmpty()) {
            return; // Нет данных для отрисовки
        }

        int width = getWidth();
        int height = getHeight();

        // Рисуем сетку
        drawGrid(canvas, width, height);

        // Рисуем график
        drawGraph(canvas, width, height);

        // Рисуем метрики
        drawMetrics(canvas, width, height);
    }

    private void drawGrid(Canvas canvas, int width, int height) {
        // Вертикальные линии (время)
        for (int i = 0; i <= 10; i++) {
            float x = i * width / 10f;
            canvas.drawLine(x, 0, x, height, gridPaint);
        }

        // Горизонтальные линии (угол)
        for (int i = 0; i <= 10; i++) {
            float y = i * height / 10f;
            canvas.drawLine(0, y, width, y, gridPaint);
        }
    }

    private void drawGraph(Canvas canvas, int width, int height) {
        graphPath.reset();

        // Масштабируем время и угол
        float scaleX = width / (float) times.get(times.size() - 1); // Масштаб по X (время)
        float scaleY = height / (2 * maxAngle); // Масштаб по Y (угол)

        // Рисуем график
        for (int i = 0; i < angles.size(); i++) {
            float x = times.get(i) * scaleX;
            float y = height / 2 - angles.get(i) * scaleY; // Центрируем по Y

            if (i == 0) {
                graphPath.moveTo(x, y);
            } else {
                graphPath.lineTo(x, y);
            }
        }

        // Рисуем путь на Canvas
        canvas.drawPath(graphPath, graphPaint);
    }

    private void drawMetrics(Canvas canvas, int width, int height) {
        // Максимальный угол
        float maxAngleDegrees = (float) Math.toDegrees(maxAngle);
        canvas.drawText("Макс. угол: " + String.format("%.1f°", maxAngleDegrees), 20, 30, textPaint);

        // Период колебаний
        if (angles.size() >= 2) {
            long period = times.get(times.size() - 1) - times.get(0);
            canvas.drawText("Период: " + period + " мс", 20, 60, textPaint);
        }

        // Скорость изменения угла
        if (angles.size() >= 2) {
            float speed = (angles.get(angles.size() - 1) - angles.get(angles.size() - 2));
            canvas.drawText("Скорость: " + String.format("%.2f°/с", speed), 20, 90, textPaint);
        }
    }
}