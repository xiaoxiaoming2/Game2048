package com.xiaoxiaoming.game2048;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView scoreTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scoreTV = (TextView) findViewById(R.id.score);
        findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

findViewById(R.id.restart).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        GameView.getGameView().startGame();
    }
});
    }

    public void initScore() {
        scoreTV.setText("");
        socre = 0;
    }

    public void addScore(int s) {
        socre += s;
        showScore();
    }

    private void showScore() {
        scoreTV.setText(socre + "");
    }

    private static MainActivity main = null;
    private int socre = 0;

    public static MainActivity getInstance() {
        return main;
    }

    public MainActivity() {
        main = this;
    }

}
