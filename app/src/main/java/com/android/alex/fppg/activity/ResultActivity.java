package com.android.alex.fppg.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.alex.fppg.R;
import com.android.alex.fppg.util.AppUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alex on 25-Jan-17.
 *
 * Very simple design could be improved with more visual effect or
 * the list of good answers, or some stats about how many times it
 * took to win etc..
 */

public class ResultActivity extends AppCompatActivity {

    @BindView(R.id.play_btn)
    Button playBtn;
    @BindView(R.id.game_result_text)
    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);

        int totalTry = getIntent().getIntExtra(AppUtil.tryCountTAG, 10); // At least 10 try to show the result activity !

        resultText.setText(getResources().getText(R.string.you_won) + " (After " + totalTry + " try ;)");

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
