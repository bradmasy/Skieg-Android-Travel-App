package skieg.travel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Represents a recycler view object for a calendar event.
 */
public class RecyclerViewCalendar extends RecyclerView.Adapter<RecyclerViewCalendar.MyViewHolder> {

    // Stores values for each attribute in a calendar event
    ArrayList<String> titles;
    ArrayList<String> descriptions;
    ArrayList<String> locations;
    ArrayList<String> dates;


    /**
     * 3 parameter constructor for a RecyclerViewCalendar object.
     * @param titles: ArrayList of Strings
     * @param descriptions: ArrayList of Strings
     * @param locations: ArrayList of Strings
     * @param dates: ArrayList of Strings
     */
    public RecyclerViewCalendar(ArrayList<String> titles, ArrayList<String> descriptions, ArrayList<String> locations, ArrayList<String> dates) {
        this.titles = titles;
        this.descriptions = descriptions;
        this.locations = locations;
        this.dates = dates;
    }

    /**
     * Override method to create a view of a single calendar event in the XML.
     * @param parent: ViewGroup
     * @param viewType: integer
     * @return MyViewHolder object
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_event_item, parent, false);
        return new MyViewHolder(view);
    }

    /**
     * Binds the current values of each Arraylist at a certain position together.
     * Sets the text values of the XML IDs to each Arraylist value.
     * @param holder: MyViewHolder
     * @param position: integer
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textViewTitle.setText(titles.get(position));
        holder.textViewDescription.setText(descriptions.get(position));
        holder.textViewLocation.setText(locations.get(position));
        holder.textViewDate.setText(dates.get(position));
    }

    /**
     * Getter for the number of elements/items in the Arraylists.
     * @return size of the Arraylists: integer
     */
    @Override
    public int getItemCount() {
        return titles.size();
    }


    // Inner class to initialize variables for a Calendar Event object
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // Store the textview objects in the single calendar event XML file.
        TextView textViewTitle, textViewDescription, textViewLocation, textViewDate;

        /**
         * Calls parent class constructor.
         * Connects each textview to its corresponding ID of the value in the XML page.
         * @param itemView: View
         */
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.title);
            textViewDescription = itemView.findViewById(R.id.description);
            textViewLocation = itemView.findViewById(R.id.location);
            textViewDate = itemView.findViewById(R.id.date);
        }
    }
}
