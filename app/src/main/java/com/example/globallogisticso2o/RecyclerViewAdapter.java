package com.example.globallogisticso2o;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globallogisticso2o.domain.PostData;

import java.util.ArrayList;

/**
 * 리사이클러뷰 어댑터
 * @author 최재훈
 * @version 1.0 테스트 필요
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int ITEM = 0;
    private final int PROGRESS_BAR = 1;

    private ArrayList<PostData> items;

    public RecyclerViewAdapter(ArrayList<PostData> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new ProgressBarViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder) {
            displayItem((ItemViewHolder) holder, position);
        } else if(holder instanceof ProgressBarViewHolder) {
            displayProgressBar((ProgressBarViewHolder) holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (items.get(position) == null ? PROGRESS_BAR : ITEM);
    }

    @Override
    public int getItemCount() {
        return (items == null ? 0 : items.size());
    }

    private void displayItem(ItemViewHolder holder, int position) {
        holder.setItem(items.get(position));
    }

    private void displayProgressBar(ProgressBarViewHolder holder, int position) {

    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private ViewGroup container;

        private TextView title;
        private TextView nickname;
        private TextView date;
        private TextView content;

        private ImageView thumbnail;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            container = (ViewGroup) itemView.findViewById(R.id.containerPost);

            title = (TextView) itemView.findViewById(R.id.textViewPostTitle);
            nickname = (TextView) itemView.findViewById(R.id.textViewNickname);
            date = (TextView) itemView.findViewById(R.id.textViewDate);
            content = (TextView) itemView.findViewById(R.id.textViewPostContent);

            thumbnail = (ImageView) itemView.findViewById(R.id.imageViewPicture);
        }

        public void setItem(PostData item) {
            title.setText(item.getPostTitle());
            nickname.setText(item.getNickname());
            date.setText(item.getPostDate());
            content.setText(item.getPostContent());
        }
    }

    private class ProgressBarViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        public ProgressBarViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBarLoadingPost);
        }
    }

}