package skieg.travel.planner.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import skieg.travel.R;

public class RecyclerViewChecklist extends RecyclerView.Adapter<RecyclerViewChecklist.MyViewHolder> {

    ArrayList<String> items;
    ArrayList<Boolean> checked;


    public RecyclerViewChecklist(ArrayList<String> items, ArrayList<Boolean> checked) {
        this.items = items;
        this.checked = checked;
    }

    @NonNull
    @Override
    public RecyclerViewChecklist.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checklist_item, parent, false);
        return new RecyclerViewChecklist.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.checkBoxItem.setText(items.get(position));

        // If it's checked value is true
        if (checked.get(position)) {
            holder.checkBoxItem.setChecked(true);
        }

    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    // Inner class to initialize variables for a Calendar Event object
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBoxItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxItem = itemView.findViewById(R.id.checkBox);
        }

    }
}
