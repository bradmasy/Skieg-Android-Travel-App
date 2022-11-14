package skieg.travel.planner.fragments;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import skieg.travel.DatabaseParse;
import skieg.travel.MainActivity;
import skieg.travel.R;

public class RecyclerViewChecklist extends RecyclerView.Adapter<RecyclerViewChecklist.MyViewHolder> {

    public ArrayList<String> items;
    public ArrayList<Boolean> checked;
    public ArrayList<String> IDs;

    private ChecklistClickListener clickListener;

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


    public void setClickListener(ChecklistClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }



    // Inner class to initialize variables for a Calendar Event object
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        CheckBox checkBoxItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxItem = itemView.findViewById(R.id.checkBox);

            checkBoxItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox currCheckbox = (CheckBox) v;
                    System.out.println(currCheckbox.getId());
                    System.out.println(currCheckbox.getText());
                    boolean isChecked = ((CheckBox) v).isChecked();
                    // Check if checkbox was clicked
                    if (isChecked){
//                        int index = items.indexOf(currCheckbox.getText().toString());
//                        checked.set(index, true);
                        // Do your coding
                        System.out.println("CHECKED");
                        setItemFromFirebase(true, currCheckbox.getText().toString());
                    }
                    else{
                        // Do your coding
                        System.out.println("NOT CHECKED");
                        setItemFromFirebase(false, currCheckbox.getText().toString());
                    }

                }
            });
        }


        public void onClick(View itemView) {
            if (clickListener != null) clickListener.onClick(itemView, getAdapterPosition());
        }





        public void setItemFromFirebase(boolean checkedValue, String checklistItem) {
            String id = MainActivity.USER.getId();
            // .child("Users").child(id).child("Planner").child("Checklist")
            DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://skieg-364814-default-rtdb.firebaseio.com/").getReference().child("Users").child(id).child("Planner").child("Checklist");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d("LOG", "IN DATA SNAPSHOT METHOD");

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String currSnapshot = String.valueOf(dataSnapshot.getValue());



                        // {item=item1, checked=false, id=-NGeksyGKISPKaSYAnuO}
                        String[] dataValues = currSnapshot.split(",");
                        String item = DatabaseParse.parseDataValue(dataValues[0]);
                        String checked = DatabaseParse.parseDataValue(dataValues[1]);
                        String id = DatabaseParse.parseLastDataValue(dataValues[2]);


                        if (item.equals(checklistItem)) {
                            Log.d("ITEM EQUAL:", item);
                            Log.d("ITEM:", item);
                            Log.d("CHECKED:", checked);
                            Log.d("ID:", id);

                            String userID = MainActivity.USER.getId();
                            Checklist checklist = new Checklist(item, id, checkedValue);

                            // child("Users").child(userID).child("Planner").child("Checklist").child(id)
                            Task setValueTask = databaseReference.child(id).setValue(checklist);

                            setValueTask.addOnSuccessListener(new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {

                                }
                            });
                        }



                        Log.d("DATASNAP:", currSnapshot);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("LOG", "DB ERROR");
                }
            });
        }

    }
}
