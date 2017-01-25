package com.android.alex.fppg.network;

import com.android.alex.fppg.model.Players;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Alex on 25-Jan-17.
 */

public interface ApiManager {

    @GET("/liamjdouglas/bb40ee8721f1a9313c22c6ea0851a105/raw/6b6fc89d55ebe4d9b05c1469349af33651d7e7f1/Player.json")
    Call<Players> getPlayersList();
}
