package skieg.travel.planner.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;
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

import skieg.travel.DatabaseParse;
import skieg.travel.InputValidation;
import skieg.travel.MainActivity;
import skieg.travel.R;

public class ChecklistFragment extends PlannerFragment implements ChecklistClickListener {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    EditText checklistEditText;

    RecyclerView recyclerView;
    RecyclerViewChecklist recyclerViewChecklist;

    ArrayList<String> itemsList = new ArrayList<>();
    ArrayList<Boolean> checkedList = new ArrayList<>();
    ArrayList<String> idList = new ArrayList<>();


    public ChecklistFragment(){
        super(R.layout.activity_checklist);
        Log.d("LOG", "CHECKLIST");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_checklist, container, false);


        checklistEditText = view.findViewById(R.id.checklistEditText);

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

        Button clearItemBtn = view.findViewById(R.id.clearItemBtn);
        clearItemBtn.setOnClickListener(viewClear -> {
            checklistEditText.setText("");
        });

        Button saveBtn = view.findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(viewClear -> {
//            saveChecklistToFirebase();
        });

        Button backBtn = view.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(viewBack -> {
            backBtnClicked();
        });


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        recyclerViewChecklist = new RecyclerViewChecklist(itemsList, checkedList, idList);
        recyclerViewChecklist.setClickListener(this);

        getDataFromFirebase();

        return view;
    }


    // Sets a RecyclerViewProduct adapter to this fragment's recycler view
    public void initializeAdapter(RecyclerView.Adapter<RecyclerViewChecklist.MyViewHolder> adapter) {
        recyclerView.setAdapter(adapter);
    }


    public void addItemToFirebase(String checklistItem) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance("https://skieg-364814-default-rtdb.firebaseio.com/").getReference();

        String userID = MainActivity.USER.getId();
        String itemID = databaseReference.push().getKey();

        Checklist checklist = new Checklist(checklistItem, itemID, false);

        Task setValueTask = databaseReference.child("Users").child(userID).child("Planner").child("Checklist").child(itemID).setValue(checklist);

        setValueTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast toast = Toast.makeText(getActivity().getBaseContext(), "Checklist item added!", Toast.LENGTH_SHORT);
                toast.show();

                checklistEditText.setText("");
            }
        });
    }


    public void getDataFromFirebase() {
        String id = MainActivity.USER.getId();
        databaseReference = FirebaseDatabase.getInstance("https://skieg-364814-default-rtdb.firebaseio.com/").getReference().child("Users").child(id).child("Planner").child("Checklist");

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
                    Log.d("ITEM:", item);
                    Log.d("CHECKED:", checked);
                    Log.d("ID:", id);

                    itemsList.add(item);
                    Boolean isChecked = Boolean.parseBoolean(checked);
                    checkedList.add(isChecked);
                    idList.add(id);


                    Log.d("DATASNAP:", currSnapshot);
                }

                recyclerViewChecklist = new RecyclerViewChecklist(itemsList, checkedList, idList);
                initializeAdapter(recyclerViewChecklist);
//                recyclerView.setAdapter(recyclerViewChecklist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("LOG", "DB ERROR");
            }
        });
    }


    public void backBtnClicked() {
        Intent mainIntent = new Intent(getActivity(), MainActivity.class);
        startActivity(mainIntent);
    }

    @Override
    public void onClick(View view, int position) {
        System.out.println("CLICK METHOD: " + view + " ; " + position);
    }
}
