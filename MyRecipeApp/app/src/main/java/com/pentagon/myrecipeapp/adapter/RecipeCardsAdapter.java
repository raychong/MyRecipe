package com.pentagon.myrecipeapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pentagon.myrecipeapp.R;
import com.pentagon.myrecipeapp.model.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RecipeCardsAdapter extends RecyclerView.Adapter<RecipeCardsAdapter.ViewHolder> {
    private final ArrayList<Recipe> recipes;
    private Fragment callbackFragment;
    private onItemClickedListener mCallbackListener;

    public RecipeCardsAdapter(ArrayList<Recipe> recipes, Fragment callbackFragment) {
        this.recipes = recipes;
        this.callbackFragment = callbackFragment;
        try {
            mCallbackListener = (onItemClickedListener) callbackFragment;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void removeItem(int position) {
        recipes.remove(position);
        notifyItemRemoved(position);
    }

    public int getID(int position) {
        return recipes.get(position).getNo();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvRecipeName)
        TextView tvRecipeName;
        @BindView(R.id.tvCategory)
        TextView tvCategory;
        @BindView(R.id.card_view)
        CardView cardView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.card_view)
        public void onItemClicked() {
            if (mCallbackListener != null) {
                mCallbackListener.onItemClicked(getAdapterPosition());
            }

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe_cards, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {

            Recipe recipe = recipes.get(position);
            holder.tvRecipeName.setText("" + recipe.getTitle());
            holder.tvCategory.setText("" + recipe.getCategory());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public interface onItemClickedListener {
        void onItemClicked(int pos);
    }
}
