package com.android.alex.fppg.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.alex.fppg.activity.MainActivity;
import com.android.alex.fppg.model.Player;
import com.android.alex.fppg.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {
    private List<Player> players;
    private MainActivity activity;
    private boolean showFPPG;

    private int positionSelected;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public @BindView(R.id.card_view) CardView playerCard;
        public @BindView(R.id.player_image) ImageView playerImage;
        public @BindView(R.id.player_name) TextView playerName;
        public @BindView(R.id.player_fppg) TextView playerFPPG;
        public @BindView(R.id.progress_bar) ProgressBar progressBar;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public PlayerAdapter(MainActivity activity) {
        this.activity = activity;
    }

    public void swapData(List<Player> playerList) {

        showFPPG = false;

        if (players == null) {
            players = new ArrayList<>();
        }

        players.clear();
        players.addAll(playerList);

        notifyDataSetChanged();
    }

    @Override
    public PlayerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (players != null) {

            final Player player = players.get(position);
            if (player != null) {

                String urlImage = String.valueOf(player.getPlayerImage().getDefaultImage().getUrl());
                if (!urlImage.isEmpty()) {
                    Picasso.with(activity).load(urlImage).into(holder.playerImage, new ImageLoadedCallback(holder.progressBar, holder.playerImage));
                }

                holder.playerName.setText(player.toString());

                holder.playerCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        positionSelected = position;
                        updateGameResult();
                    }
                });

                if (showFPPG) {
                    holder.playerFPPG.setText(String.valueOf(player.getFppg()));
                    holder.playerCard.setEnabled(false); // disabled click event to avoid to cheat and retry when user has already seen the solution

                } else {
                    // Resume game
                    holder.playerFPPG.setText(activity.getResources().getString(R.string.fppg));
                    holder.playerCard.setEnabled(true);
                }
            }
        }

    }

    private void updateGameResult() {

        boolean result = false;
        showFPPG = true;

        Player playerSelected;
        Player playerUnselected;

        if (positionSelected == players.size() - 1) {
            // Second player selected
            playerSelected = getPlayer(positionSelected);
            playerUnselected = getPlayer(0);
        } else {
            // First player selected
            playerSelected = getPlayer(positionSelected);
            playerUnselected = getPlayer(1);
        }

        if (playerSelected != null && playerUnselected != null) {
            if (playerSelected.getFppg() > playerUnselected.getFppg()) {
                result = true;
            }
        }

        activity.updateMainActivity(result);
        notifyDataSetChanged();
    }

    public List<Player> getPlayers() {
        if (players == null) {
            players = new ArrayList<>();
        }
        return players;
    }

    public int getItemCount() {

        if (players == null) {
            return 0;
        }
        return players.size();
    }

    public Player getPlayer(int index) {

        if (players == null) {
            return null;
        }

        return players.get(index);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////


    private class ImageLoadedCallback implements Callback {

        private ProgressBar progressBar;
        private ImageView imageView;

        public  ImageLoadedCallback(ProgressBar progressBar, ImageView imageView){
            this.progressBar = progressBar;
            this.imageView = imageView;
        }

        @Override
        public void onSuccess() {
            manageVisibility();
        }

        @Override
        public void onError() {
            manageVisibility();
            imageView.setImageResource(android.R.drawable.ic_menu_report_image);
        }

        private void manageVisibility() {
            progressBar.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
        }
    }
}

