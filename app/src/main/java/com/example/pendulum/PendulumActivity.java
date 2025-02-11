package com.example.pendulum;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PendulumActivity extends AppCompatActivity {
    private PendulumGLSurfaceView glSurfaceView;
    private PendulumGraphView graphView;
    private SeekBar lengthSeekBar, gravitySeekBar, angleSeekBar;
    private TextView lengthValue, gravityValue, angleValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendulum);

        // Инициализация GraphView
        graphView = findViewById(R.id.graph_view);

        // Инициализация GLSurfaceView
        glSurfaceView = findViewById(R.id.gl_surface_view);
        glSurfaceView.setGraphView(graphView);

        // Настройка элементов управления
        setupControls();
    }

    private void setupControls() {
        // Инициализация кнопок и ползунков
        Button startButton = findViewById(R.id.start_button);
        Button stopButton = findViewById(R.id.stop_button);
        Button resetButton = findViewById(R.id.reset_button);

        lengthSeekBar = findViewById(R.id.length_seekbar);
        gravitySeekBar = findViewById(R.id.gravity_seekbar);
        angleSeekBar = findViewById(R.id.angle_seekbar);

        lengthValue = findViewById(R.id.length_value);
        gravityValue = findViewById(R.id.gravity_value);
        angleValue = findViewById(R.id.angle_value);

        // Настройка обработчиков для кнопок
        startButton.setOnClickListener(v -> {
            glSurfaceView.startAnimation();
            graphView.clearGraph();
        });
        stopButton.setOnClickListener(v -> glSurfaceView.stopAnimation());
        resetButton.setOnClickListener(v -> {
            glSurfaceView.resetPendulum();
            graphView.clearGraph();
        });

        // Настройка ползунков
        setupSeekBar(lengthSeekBar, lengthValue, 10, 200, 100, value -> {
            float length = value / 100f;
            glSurfaceView.setPendulumLength(length);
            lengthValue.setText(String.format("Длина: %.2f м", length));
        });

        setupSeekBar(gravitySeekBar, gravityValue, 10, 300, 98, value -> {
            float gravity = value / 10f;
            glSurfaceView.setGravity(gravity);
            gravityValue.setText(String.format("G: %.1f м/с²", gravity));
        });

        setupSeekBar(angleSeekBar, angleValue, 0, 90, 45, value -> {
            float angle = (float) Math.toRadians(value);
            glSurfaceView.setInitialAngle(angle);
            angleValue.setText(String.format("Угол: %d°", value));
        });
    }

    private void setupSeekBar(SeekBar seekBar, TextView textView, int min, int max, int initial, SeekBarCallback callback) {
        seekBar.setMax(max - min);
        seekBar.setProgress(initial - min);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int value = progress + min;
                callback.onValueChanged(value);
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        callback.onValueChanged(initial);
    }

    interface SeekBarCallback {
        void onValueChanged(int value);
    }
}