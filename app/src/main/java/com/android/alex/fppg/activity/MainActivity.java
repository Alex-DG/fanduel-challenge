package com.android.alex.fppg.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.alex.fppg.R;
import com.android.alex.fppg.adapter.PlayerAdapter;
import com.android.alex.fppg.application.FppgApp;
import com.android.alex.fppg.model.Player;
import com.android.alex.fppg.model.Players;
import com.android.alex.fppg.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private int counterWin, counterLose;

    private PlayerAdapter playerAdapter;

    public AnimatorSet animatorSet = new AnimatorSet();

    private List<Player> players = new ArrayList<>();

    @BindView(R.id.recycler_view_players)
    RecyclerView recyclerView;
    @BindView(R.id.game_result_image)
    ImageView fanDuelLogoResult;
    @BindView(R.id.no_internet)
    TextView noInternet;
    @BindView(R.id.score_win)
    TextView scoreWin;
    @BindView(R.id.score_lose)
    TextView scoreLose;
    @BindView(R.id.exit_btn)
    Button exitBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        playerAdapter = new PlayerAdapter(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(playerAdapter);

        exitBtn.setOnClickListener(clickListener);
        fanDuelLogoResult.setOnClickListener(clickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (AppUtil.isConnected()) {
            getPlayersList();
        } else {
            recyclerView.setVisibility(View.GONE);
            noInternet.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        stopAnimation();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////


    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int id = v.getId();

            switch (id) {

                case R.id.game_result_image:

                    stopAnimation(); // Stop existing animation on screen
                    fanDuelLogoResult.setVisibility(View.GONE); // Hide game result image

                    // When user has correctly guessed 10 times
                    if (counterWin == 10) {
                        Intent intent = new Intent(MainActivity.this, ResultActivity.class);

                        int total = counterWin + counterLose;
                        intent.putExtra(AppUtil.tryCountTAG, total);

                        startActivity(intent);

                    } else {
                        // Resume game
                        updateGridPlayers();
                    }
                    break;

                case R.id.exit_btn:

                    System.exit(0);
                    break;
            }
        }
    };


    ////////////////////////////////////////////////////////////////////////////////////////////////


    public void updateMainActivity(boolean result) {
        updateScore(result);
        showGameResultImage(result);
    }

    /**
     *  Update score counter
     */
    public void updateScore(boolean isWin) {

        if (isWin) {
            counterWin++;
        } else {
            counterLose++;
        }

        scoreWin.setText(String.valueOf(counterWin));
        scoreLose.setText(String.valueOf(counterLose));
    }

    /**
     * Show to the user if it's a Win or a Lose!!
     */
    public void showGameResultImage(boolean isWin) {

        fanDuelLogoResult.setVisibility(View.VISIBLE);

        // FanDuel logo : green logo for a win and red logo for a lose
        if (isWin) {
            fanDuelLogoResult.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fan_duel_win_logo));
        } else {
            fanDuelLogoResult.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fan_duel_lose_logo));
        }

        if (!AppUtil.isRoboUnitTest()) {

            // Fade out - Fade in animations on the FanDuel Logo
            ObjectAnimator fadeOut = ObjectAnimator.ofFloat(fanDuelLogoResult, "alpha", 1f, .3f);
            fadeOut.setDuration(800);

            ObjectAnimator fadeIn = ObjectAnimator.ofFloat(fanDuelLogoResult, "alpha", .3f, 1f);
            fadeIn.setDuration(800);

            animatorSet.play(fadeIn).after(fadeOut);
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    animatorSet.start();
                }
            });
            animatorSet.start();
        }
    }

    private void stopAnimation() {
        if (animatorSet.isRunning()) {
            animatorSet.end();
        }
    }

    /**
     * Update grid players
     */
    private void updateGridPlayers() {
        List<Player> randomList = AppUtil.getRandomList(players, 2);
        playerAdapter.swapData(randomList);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void getPlayersList() {
        Call<Players> call = FppgApp.getApiManager().getPlayersList();
        call.enqueue(new Callback<Players>() {
            @Override
            public void onResponse(Call<Players> call, Response<Players> response) {
                Log.i(TAG, "onResponse");

                Players playersList = null;
                if (response != null) {
                    playersList = response.body();
                }

                if (playersList != null && playersList.getPlayers() != null && playersList.getPlayers().size() > 0) {
                    players = playersList.getPlayers();
                    updateGridPlayers();
                }
            }

            @Override
            public void onFailure(Call<Players> call, Throwable t) {
                Log.i(TAG, "onFailure : " + t.getMessage());
            }
        });
    }
}
