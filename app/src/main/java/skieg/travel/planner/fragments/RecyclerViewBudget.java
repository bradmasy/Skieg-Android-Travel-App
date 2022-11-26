package skieg.travel.planner.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import skieg.travel.R;

public class RecyclerViewBudget extends RecyclerView.Adapter<RecyclerViewBudget.MyViewHolder> {

    public ArrayList<String> items;
    public ArrayList<Double> amounts;
    public ArrayList<String> IDs;


    public RecyclerViewBudget(ArrayList<String> items, ArrayList<Double> amounts, ArrayList<String> IDs) {
        this.items = items;
        this.amounts = amounts;
        this.IDs = IDs;
    }


    @NonNull
    @Override
    public RecyclerViewBudget.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.budget_item, parent, false);
        return new RecyclerViewBudget.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.item.setText(items.get(position));
        holder.amount.setText(String.valueOf(amounts.get(position)));
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    // Inner class to initialize variables for a Calendar Event object
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView item, amount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            amount = itemView.findViewById(R.id.amount);
        }
    }
}
