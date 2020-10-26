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
import com.bca.bakingapp.callback.StepCallback;
import com.bca.bakingapp.model.Step;

import java.util.List;

import static android.content.ContentValues.TAG;

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.StepViewHolder> {

    private final LayoutInflater mInflater;
    private List<Step> mSteps;
    private StepCallback callback = null;

    public StepListAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.step_item, parent, false);
        return new StepViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        if (mSteps != null){
            Step current = mSteps.get(position);
            holder.nameItemView.setText(current.getIdStep() + ". " + current.getDescription());
            holder.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null){
                        Log.d(TAG, "onClick: ");
                        callback.stepPressed(current);
                    }
                }
            });
        } else {
            holder.nameItemView.setText("No Task");
        }
    }

    public void setmSteps(List<Step> steps){
        mSteps = steps;
        notifyDataSetChanged();
    }

    public void setCallback(StepCallback mCallback) {
        callback = mCallback;
    }
    @Override
    public int getItemCount() {
        if (mSteps != null)
            return mSteps.size();
        else return 0;
    }

    class StepViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameItemView;
        private final View parent;


        private StepViewHolder(View itemView){
            super(itemView);
            parent = itemView;
            nameItemView = itemView.findViewById(R.id.textViewStepName);
        }

        private View getView(){
            return parent;
        }
    }

}
