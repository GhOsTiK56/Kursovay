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
    private final Paint paint = new Paint();
    private final Path path = new Path();
    private final List<Float> angles = new ArrayList<>(); // Список углов
    private final List<Long> times = new ArrayList<>();   // Список временных меток
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
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
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
        path.reset();
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

        // Очищаем путь
        path.reset();

        // Масштабируем время и угол
        float scaleX = width / (float) times.get(times.size() - 1); // Масштаб по X (время)
        float scaleY = height / (2 * maxAngle); // Масштаб по Y (угол)

        // Рисуем график
        for (int i = 0; i < angles.size(); i++) {
            float x = times.get(i) * scaleX;
            float y = height / 2 - angles.get(i) * scaleY; // Центрируем по Y

            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }

        // Рисуем путь на Canvas
        canvas.drawPath(path, paint);
    }
}