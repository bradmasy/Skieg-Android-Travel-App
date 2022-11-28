package skieg.travel.planner.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import skieg.travel.Utility.DatabaseParse;
import skieg.travel.Utility.InputValidation;
import skieg.travel.MainActivity;
import skieg.travel.R;

/**
 * Checklist Fragment.
 */
public class ChecklistFragment extends PlannerFragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    EditText checklistEditText;
    RecyclerView recyclerView;
    RecyclerViewChecklist recyclerViewChecklist;
    ArrayList<String> itemsList = new ArrayList<>();
    ArrayList<Boolean> checkedList = new ArrayList<>();
    ArrayList<String> idList = new ArrayList<>();


    /**
     * Checklist fragment constructor.
     */
    public ChecklistFragment(){
        super(R.layout.activity_checklist);
    }

    /**
     * On Create View.
     *
     * @param inflater a layout inflater.
     * @param container a container.
     * @param savedInstanceState a bundle of saved instance data from the previous activity.
     * @return an inflated view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_checklist, container, false);

        checklistEditText = view.findViewById(R.id.checklistEditText);

        // Add a checkbox item to the checklist
        Button addItemBtn = view.findViewById(R.id.addItemBtn);
        addItemBtn.setOnClickListener(viewAdd -> {
            String checklistItemStr = checklistEditText.getText().toString();
            if (InputValidation.invalidStringInput(checklistItemStr)) {
                Toast toast = Toast.makeText(getActivity(), "Checklist item cannot be empty.", Toast.LENGTH_SHORT);
                toast.show();

            } else {
                // Clear arraylist values (all database items will be added in the firebase method)
                itemsList = new ArrayList<>();
                checkedList = new ArrayList<>();
                idList = new ArrayList<>();

                addItemToFirebase(checklistItemStr);
            }
        });

        // Clear edit text field
        Button clearItemBtn = view.findViewById(R.id.clearItemBtn);
        clearItemBtn.setOnClickListener(viewClear -> {
            checklistEditText.setText("");
        });

        // Save all checklist checked/unchecked values
        Button saveBtn = view.findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(viewClear -> {
            ArrayList<String> tempItemsList = itemsList;
            ArrayList<Boolean> tempCheckedList = checkedList;
            ArrayList<String> tempIdList = idList;

            saveChecklistToFirebase(tempItemsList, tempCheckedList, tempIdList);
        });

        // Redirect back to main page
        Button backBtn = view.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(viewBack -> {
            backBtnClicked();
        });


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        recyclerViewChecklist = new RecyclerViewChecklist(itemsList, checkedList, idList);

        // Display all checkbox items from the database on the page in the recyclerview
        getDataFromFirebase();

        return view;
    }


    // Sets a RecyclerViewProduct adapter to this fragment's recycler view
    public void initializeAdapter(RecyclerView.Adapter<RecyclerViewChecklist.MyViewHolder> adapter) {
        recyclerView.setAdapter(adapter);
    }


    /**
     * adds an item to firebase.
     *
     * @param checklistItem a string representing a checklist item.
     */
    public void addItemToFirebase(String checklistItem) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance("https://skieg-364814-default-rtdb.firebaseio.com/").getReference();

        String userID = MainActivity.USER.getId();
        String itemID = databaseReference.push().getKey();

        Checklist checklist = new Checklist(checklistItem, itemID, false);

        Task setValueTask = databaseReference.child("Users").child(userID).child("Planner").child("Checklist").child(itemID).setValue(checklist);
        setValueTask.addOnSuccessListener(new OnSuccessListener() {

            /**
             * On a successful write.
             *
             * @param o an object.
             */
            @Override
            public void onSuccess(Object o) {
                Toast toast = Toast.makeText(getActivity().getBaseContext(), "Checklist item added!", Toast.LENGTH_SHORT);
                toast.show();
                checklistEditText.setText("");
            }
        });
    }


    /**
     * Gets the data from the database.
     */
    public void getDataFromFirebase() {
        String id = MainActivity.USER.getId();
        databaseReference = FirebaseDatabase.getInstance("https://skieg-364814-default-rtdb.firebaseio.com/").getReference().child("Users").child(id).child("Planner").child("Checklist");

        databaseReference.addValueEventListener(new ValueEventListener() {

            /**
             * On data change in the database.
             *
             * @param snapshot a database snapshot.
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemsList = new ArrayList<>();
                checkedList = new ArrayList<>();
                idList = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String currSnapshot = String.valueOf(dataSnapshot.getValue());
                    String[] dataValues = currSnapshot.split(",");
                    String item = DatabaseParse.parseDataValue(dataValues[0]);
                    String checked = DatabaseParse.parseDataValue(dataValues[1]);
                    String id = DatabaseParse.parseLastDataValue(dataValues[2]);

                    itemsList.add(item);
                    Boolean isChecked = Boolean.parseBoolean(checked);
                    checkedList.add(isChecked);
                    idList.add(id);
                }

                recyclerViewChecklist = new RecyclerViewChecklist(itemsList, checkedList, idList);
                initializeAdapter(recyclerViewChecklist);
            }

            /**
             * On cancelled write.
             *
             * @param error a database error.
             */
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("LOG", "DB ERROR");
            }
        });
    }

    /**
     * Initiates going back to the main activity.
     */
    public void backBtnClicked() {
        Intent mainIntent = new Intent(getActivity(), MainActivity.class);
        startActivity(mainIntent);
    }

    /**
     * Saves the checklist item to firebase.
     *
     * @param tempItemsList a list of the temp items.
     * @param tempCheckedList a list of the temp checked.
     * @param tempIdList a list of the temp ids.
     */
    public void saveChecklistToFirebase(ArrayList<String> tempItemsList, ArrayList<Boolean> tempCheckedList, ArrayList<String> tempIdList) {
        String id = MainActivity.USER.getId();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance("https://skieg-364814-default-rtdb.firebaseio.com/").getReference().child("Users").child(id).child("Planner").child("Checklist");

        for (int index = 0; index < tempCheckedList.size(); index++) {
            String currID = tempIdList.get(index);
            Checklist checklist = new Checklist(tempItemsList.get(index), currID, tempCheckedList.get(index));
            databaseReference.child(currID).setValue(checklist);
        }
    }
}
