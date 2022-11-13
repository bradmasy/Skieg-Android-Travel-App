package skieg.travel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewCalendar extends RecyclerView.Adapter<RecyclerViewCalendar.MyViewHolder> {

    ArrayList<String> titles;
    ArrayList<String> descriptions;
    ArrayList<String> locations;
    ArrayList<String> dates;


    public RecyclerViewCalendar(ArrayList<String> titles, ArrayList<String> descriptions, ArrayList<String> locations, ArrayList<String> dates) {
        this.titles = titles;
        this.descriptions = descriptions;
        this.locations = locations;
        this.dates = dates;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_event_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textViewTitle.setText(titles.get(position));
        holder.textViewDescription.setText(descriptions.get(position));
        holder.textViewLocation.setText(locations.get(position));
        holder.textViewDate.setText(dates.get(position));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }


    // Inner class to initialize variables for a Calendar Event object
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewDescription, textViewLocation, textViewDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.title);
            textViewDescription = itemView.findViewById(R.id.description);
            textViewLocation = itemView.findViewById(R.id.location);
            textViewDate = itemView.findViewById(R.id.date);
        }
    }
}
