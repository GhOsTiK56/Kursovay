package com.example.pendulum;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private PendulumGLSurfaceView glSurfaceView;
    private SeekBar lengthSeekBar, gravitySeekBar, angleSeekBar;
    private TextView lengthValue, gravityValue, angleValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        glSurfaceView = findViewById(R.id.gl_surface_view);
        setupControls();
    }

    private void setupControls() {
        Button startButton = findViewById(R.id.start_button);
        Button stopButton = findViewById(R.id.stop_button);
        Button resetButton = findViewById(R.id.reset_button);

        lengthSeekBar = findViewById(R.id.length_seekbar);
        gravitySeekBar = findViewById(R.id.gravity_seekbar);
        angleSeekBar = findViewById(R.id.angle_seekbar);

        lengthValue = findViewById(R.id.length_value);
        gravityValue = findViewById(R.id.gravity_value);
        angleValue = findViewById(R.id.angle_value);

        startButton.setOnClickListener(v -> glSurfaceView.startAnimation());
        stopButton.setOnClickListener(v -> glSurfaceView.stopAnimation());
        resetButton.setOnClickListener(v -> glSurfaceView.resetPendulum());

        setupSeekBar(lengthSeekBar, lengthValue, 10, 200, 100, value -> {
            glSurfaceView.setPendulumLength(value / 100f);
            lengthValue.setText(String.format("Длина: %.2f м", value / 100f));
        });

        setupSeekBar(gravitySeekBar, gravityValue, 10, 300, 98, value -> {
            glSurfaceView.setGravity(value / 10f);
            gravityValue.setText(String.format("G: %.1f м/с²", value / 10f));
        });

        setupSeekBar(angleSeekBar, angleValue, 0, 90, 45, value -> {
            glSurfaceView.setInitialAngle((float) Math.toRadians(value));
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