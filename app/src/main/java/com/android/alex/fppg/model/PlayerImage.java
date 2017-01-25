package com.android.alex.fppg.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex on 25-Jan-17.
 */
public class PlayerImage {

    @SerializedName("default")
    private DefaultImage defaultImage;

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public DefaultImage getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(DefaultImage defaultImage) {
        this.defaultImage = defaultImage;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public class DefaultImage {

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
