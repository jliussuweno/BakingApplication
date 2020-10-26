package com.bca.bakingapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bca.bakingapp.R;
import com.bca.bakingapp.model.Ingredient;

import java.util.List;

public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListAdapter.IngredientViewHolder> {

    private final LayoutInflater mInflater;
    private List<Ingredient> mIngredients;

    public IngredientsListAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.ingredient_item, parent, false);
        return new IngredientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        if (mIngredients != null){
            Ingredient current = mIngredients.get(position);
            Log.d("TAG", "onBindViewHolder: " + current.getIngre());
            holder.nameItemView.setText("- " + current.getIngre() + " : " + current.getQuantity() + " " + current.getMeasure());
        } else {
            holder.nameItemView.setText("No Recipe");
        }
    }

    public void setmIngredients(List<Ingredient> ingredients){
        mIngredients = ingredients;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mIngredients != null)
            return mIngredients.size();
        else return 0;
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameItemView;

        private IngredientViewHolder(View itemView){
            super(itemView);
            nameItemView = itemView.findViewById(R.id.textViewNameIngredient);
        }
    }

}
