package com.android.alex.fppg.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import com.android.alex.fppg.application.FppgApp;
import com.android.alex.fppg.model.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alex on 25-Jan-17.
 */

public class AppUtil {


    public final static String tryCountTAG = "TRY_COUNT";

    /**
     * Check internet connection
     */
    public static boolean isConnected() {

        ConnectivityManager cm = (ConnectivityManager) FppgApp.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /**
     * Create a random subList of 'n' players
     */
    public static List<Player> getRandomList(List<Player> players, int n) {

        if (players != null && n < players.size()) {

            List<Player> copy = new LinkedList<Player>(players);
            Collections.shuffle(copy);

            return copy.subList(0, n);

        } else {
            return new ArrayList<Player>(); // return empty list
        }
    }

    /*
     * Detect if execution is by Robolectric
     */
    public static boolean isRoboUnitTest() {
        return "robolectric".equals(Build.FINGERPRINT);
    }
}
