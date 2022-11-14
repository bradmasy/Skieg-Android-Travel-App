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

    public ArrayList<String> items;
    public ArrayList<Boolean> checked;
    public ArrayList<String> IDs;


    public RecyclerViewChecklist(ArrayList<String> items, ArrayList<Boolean> checked, ArrayList<String> IDs) {
        this.items = items;
        this.checked = checked;
        this.IDs = IDs;
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
        holder.checkBoxItem.setTag(position);
        holder.checkBoxItem.setId(position);

    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    // Inner class to initialize variables for a Calendar Event object
    public class MyViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBoxItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxItem = itemView.findViewById(R.id.checkBox);

            checkBoxItem.setOnClickListener(view -> {
                CheckBox currCheckbox = (CheckBox) view;
                System.out.println(currCheckbox.getId());
                System.out.println(currCheckbox.getText());
                boolean isChecked = ((CheckBox) view).isChecked();
                // Check if checkbox was clicked
                if (isChecked) {
                    // Set the checkbox clicked to true
                    int index = items.indexOf(currCheckbox.getText().toString());
                    checked.set(index, true);
                } else {
                    // Set the checkbox clicked to false
                    int index = items.indexOf(currCheckbox.getText().toString());
                    checked.set(index, false);
                }
            });
        }
    }
}
