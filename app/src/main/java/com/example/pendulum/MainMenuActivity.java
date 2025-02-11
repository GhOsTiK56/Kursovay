package com.example.pendulum;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    // Обработчик нажатия на кнопку "Механика"
    public void onMechanicsClick(View view) {
        Intent intent = new Intent(this, PendulumActivity.class);
        startActivity(intent);
    }

    // Обработчик нажатия на кнопку "Электричество"
    public void onElectricityClick(View view) {
        // Пока просто заглушка
        // В будущем можно добавить раздел "Электричество"
    }

    // Обработчик нажатия на кнопку "Оптика"
    public void onOpticsClick(View view) {
        // Пока просто заглушка
        // В будущем можно добавить раздел "Оптика"
    }
}