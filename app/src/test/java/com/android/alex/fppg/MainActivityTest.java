package com.android.alex.fppg;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.alex.fppg.activity.MainActivity;
import com.android.alex.fppg.adapter.PlayerAdapter;
import com.android.alex.fppg.network.ApiManager;
import com.android.alex.fppg.network.FppgService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Alex on 25-Jan-17.
 * Robolectric unitTests
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class MainActivityTest {

    private MainActivity mainActivity;

    private PlayerAdapter playerAdapter;
    private RecyclerView recyclerView;
    private Button exitBtn;
    private ImageView gameResult;
    private TextView scoreWin, scorLose;

    @Before
    public void setUp() throws Exception {


        mainActivity = Robolectric.buildActivity(MainActivity.class)
                .create()
                .resume()
                .get();

        exitBtn = (Button) mainActivity.findViewById(R.id.exit_btn);
        gameResult = (ImageView) mainActivity.findViewById(R.id.game_result_image);
        scoreWin = (TextView) mainActivity.findViewById(R.id.score_win);
        scorLose = (TextView) mainActivity.findViewById(R.id.score_lose);
        recyclerView = (RecyclerView) mainActivity.findViewById(R.id.recycler_view_players);

        playerAdapter = new PlayerAdapter(mainActivity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(mainActivity, 2));
        recyclerView.setAdapter(playerAdapter);
    }

    @Test
    public void mainActivity_not_null() throws Exception {
        assertNotNull(mainActivity);
    }

    @Test
    public void adapter_content_not_null() throws Exception {
        assertNotNull(playerAdapter.getPlayers());
    }

    @Test
    public void updateScore_win() throws Exception {
        mainActivity.updateScore(true);
        assertEquals("1", scoreWin.getText().toString());
        assertEquals("0", scorLose.getText().toString());
    }

    @Test
    public void updateScore_lose() throws Exception {
        mainActivity.updateScore(false);
        assertEquals("0", scoreWin.getText().toString());
        assertEquals("1", scorLose.getText().toString());
    }

    @Test
    public void showGameResultImage_win() throws Exception {
        Drawable d = ContextCompat.getDrawable(mainActivity, R.drawable.fan_duel_win_logo);

        mainActivity.showGameResultImage(true);
        Drawable d2 = gameResult.getDrawable();

        assertEquals(d, d2);
    }

    @Test
    public void showGameResultImage_lose() throws Exception {
        Drawable d = ContextCompat.getDrawable(mainActivity, R.drawable.fan_duel_win_logo);

        mainActivity.showGameResultImage(false);
        Drawable d2 = gameResult.getDrawable();

        assertEquals(d, d2);
    }

    @Test
    public void fan_duel_win_logo_not_null() throws Exception {
        Drawable d = ContextCompat.getDrawable(mainActivity, R.drawable.fan_duel_win_logo);
        assertNotNull(d);
    }

    @Test
    public void fan_duel_lose_logo_not_null() throws Exception {
        Drawable d = ContextCompat.getDrawable(mainActivity, R.drawable.fan_duel_lose_logo);
        assertNotNull(d);
    }
}
