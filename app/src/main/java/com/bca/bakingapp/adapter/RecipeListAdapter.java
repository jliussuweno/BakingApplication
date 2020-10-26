package com.bca.bakingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bca.bakingapp.R;
import com.bca.bakingapp.callback.RecipeCallback;
import com.bca.bakingapp.model.Recipe;

import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    private final LayoutInflater mInflater;
    private List<Recipe> mRecipes;
    private RecipeCallback callback = null;

    public RecipeListAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        if (mRecipes != null){
            Recipe current = mRecipes.get(position);
            holder.nameItemView.setText(current.getNameRecipe());
            holder.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null){
                        callback.recipePressed(current);
                    }
                }
            });
        } else {
            holder.nameItemView.setText("No Task");
        }
    }

    public void setmRecipes(List<Recipe> recipes){
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    public void setCallback(RecipeCallback mCallback) {
        callback = mCallback;
    }
    @Override
    public int getItemCount() {
        if (mRecipes != null)
            return mRecipes.size();
        else return 0;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameItemView;
        private final View parent;


        private RecipeViewHolder(View itemView){
            super(itemView);
            parent = itemView;
            nameItemView = itemView.findViewById(R.id.textView);
        }

        private View getView(){
            return parent;
        }
    }

}
