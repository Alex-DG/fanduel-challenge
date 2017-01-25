package com.android.alex.fppg.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Alex on 25-Jan-17.
 */

public class Players {

    private List<Player> players;

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
