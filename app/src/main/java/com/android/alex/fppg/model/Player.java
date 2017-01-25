package com.android.alex.fppg.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex on 25-Jan-17.
 */

public class Player {

    private String id;
    private float fppg;

    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("images")
    private PlayerImage playerImage;


    ////////////////////////////////////////////////////////////////////////////////////////////////


    public Player() {}

    public Player(String id, String firstName, String lastName, PlayerImage playerImage) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.playerImage = playerImage;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public PlayerImage getPlayerImage() {
        return playerImage;
    }

    public void setPlayerImage(PlayerImage playerImage) {
        this.playerImage = playerImage;
    }

    public float getFppg() {
        return fppg;
    }

    public void setFppg(float fppg) {
        this.fppg = fppg;
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    @Override
    public String toString() {
        return getFullName();
    }
}
