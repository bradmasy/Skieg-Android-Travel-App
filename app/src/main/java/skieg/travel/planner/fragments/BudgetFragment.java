package skieg.travel.planner.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

import skieg.travel.Utility.DatabaseParse;
import skieg.travel.Utility.InputValidation;
import skieg.travel.MainActivity;
import skieg.travel.R;
import skieg.travel.post.RecyclerViewClickListener;

/**
 * Budget Fragment Class.
 */
public class BudgetFragment extends PlannerFragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    TextView budgetItem;
    TextView budgetAmount;
    TextView budgetTitle;

    RecyclerView recyclerView;
    RecyclerViewBudget recyclerViewBudget;
    RecyclerViewClickListener itemListener;

    ArrayList<String> itemsList = new ArrayList<>();
    ArrayList<Double> amountsList = new ArrayList<>();
    ArrayList<String> idList = new ArrayList<>();

    int indexSelectedItem;
    String stringSelectedItem;

    /**
     * Budget Fragment Constructor.
     */
    public BudgetFragment(){
        super(R.layout.activity_budget);
    }

    /**
     * On Create View.
     *
     * @param inflater an inflater.
     * @param container a container.
     * @param savedInstanceState a bundle of previously saved instance data.
     * @return an inflater view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_budget, container, false);

        budgetItem = view.findViewById(R.id.budgetItemET);
        budgetAmount = view.findViewById(R.id.budgetAmountET);
        budgetTitle = view.findViewById(R.id.budgetTitle);

        // Add a checkbox item to the checklist
        Button addItemBtn = view.findViewById(R.id.addItemBtn);
        addItemBtn.setOnClickListener(viewAdd -> {

            String itemStr = budgetItem.getText().toString();
            String amountStr = budgetAmount.getText().toString();

            if (InputValidation.invalidStringInput(itemStr) || InputValidation.invalidStringInput(amountStr)) {
                Toast toast = Toast.makeText(getActivity(), "Please fill out all fields.", Toast.LENGTH_SHORT);
                toast.show();

            } else {
                // Clear arraylist values (all database items will be added in the firebase method)
                itemsList = new ArrayList<>();
                amountsList = new ArrayList<>();
                idList = new ArrayList<>();

                addItemToFirebase(itemStr, Double.parseDouble(amountStr));
            }
        });

        // Clear edit text field
        Button clearItemBtn = view.findViewById(R.id.clearItemBtn);
        clearItemBtn.setOnClickListener(viewClear -> {
            budgetItem.setText("");
            budgetAmount.setText("");
        });

        Button removeItemBtn = view.findViewById(R.id.removeBtn);
        removeItemBtn.setOnClickListener(viewRemove -> {
            // Query Firebase and Remove
            removeItemFromFirebase(stringSelectedItem, indexSelectedItem);
        });


        // Redirect back to main page
        Button backBtn = view.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(viewBack -> {
            backBtnClicked();
        });


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerViewBudget = new RecyclerViewBudget(itemsList, amountsList, idList);

        // Display all budget items from the database on the page in the recyclerview
        getDataFromFirebase();

        return view;
    }


    // Sets a RecyclerViewProduct adapter to this fragment's recycler view
    public void initializeAdapter(RecyclerView.Adapter<RecyclerViewBudget.MyViewHolder> adapter) {
        recyclerView.setAdapter(adapter);
    }


    /**
     * adds an item to firebase database.
     *
     * @param item an item we are adding
     * @param amount the amount of the item.
     */
    public void addItemToFirebase(String item, double amount) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance("https://skieg-364814-default-rtdb.firebaseio.com/").getReference();

        String userID = MainActivity.USER.getId();
        String itemID = databaseReference.push().getKey();

        Budget budget = new Budget(itemID, item, amount);

        Task setValueTask = databaseReference.child("Users").child(userID).child("Planner").child("Budget").child(itemID).setValue(budget);
        setValueTask.addOnSuccessListener(new OnSuccessListener() {

            /**
             * On success of the database write.
             * @param o an object.
             */
            @Override
            public void onSuccess(Object o) {
                Toast toast = Toast.makeText(getActivity().getBaseContext(), "Budget item added!", Toast.LENGTH_SHORT);
                toast.show();
                budgetItem.setText("");
                budgetAmount.setText("");
            }
        });
    }

    /**
     * removes an item from the database.
     *
     * @param item an item from the database.
     * @param index the index of the item.
     */
    public void removeItemFromFirebase(String item, int index){
        String userID = MainActivity.USER.getId();
        databaseReference = FirebaseDatabase.getInstance("https://skieg-364814-default-rtdb.firebaseio.com/").getReference().child("Users").child(userID).child("Planner").child("Budget");
        Query query = databaseReference.orderByChild("id");
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            /**
             * Executes on a change in the database.
             *
             * @param snapshot the database snapshot.
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int iter = 0;
                // Iterate to find the correct index to remove.
                for (DataSnapshot snapshot1 : snapshot.getChildren()){

                    String currentItemName = String.valueOf(snapshot1.child("item").getValue());
                    if (iter == index && currentItemName.equals(item)){
                        Toast.makeText(getActivity(),"Removed: " + item,Toast.LENGTH_SHORT).show();
                        snapshot1.getRef().removeValue();
                    }
                    iter++;
                }
            }

            /**
             * executes on cancelling a database error.
             *
             * @param error the database error.
             */
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("ERROR:",error.toString());
            }
        });
    }


    /**
     * Gets the data from firebase.
     */
    public void getDataFromFirebase() {
        String id = MainActivity.USER.getId();
        databaseReference = FirebaseDatabase.getInstance("https://skieg-364814-default-rtdb.firebaseio.com/").getReference().child("Users").child(id).child("Planner").child("Budget");

        databaseReference.addValueEventListener(new ValueEventListener() {

            /**
             * Executes on a change to the data in firebase.
             *
             * @param snapshot a database snapshot.
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemsList = new ArrayList<>();
                amountsList = new ArrayList<>();
                idList = new ArrayList<>();

                double totalAmount = 0;
                DecimalFormat decimalFormat = new DecimalFormat("0.00");

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String currSnapshot = String.valueOf(dataSnapshot.getValue());

                    String[] dataValues = currSnapshot.split(",");
                    String amount = DatabaseParse.parseDataValue(dataValues[0]);
                    String item = DatabaseParse.parseDataValue(dataValues[1]);
                    String id = DatabaseParse.parseLastDataValue(dataValues[2]);

                    itemsList.add(item);
                    amountsList.add(Double.parseDouble(amount));
                    idList.add(id);

                    totalAmount += Double.parseDouble(amount);
                }

                String budgetAmountString = "Budget\nTotal Amount: $" + decimalFormat.format(totalAmount);
                budgetTitle.setText(budgetAmountString);

                recyclerViewBudget = new RecyclerViewBudget(itemsList, amountsList, idList);
                recyclerView.addOnItemTouchListener(new RecyclerViewClickListen(getContext(), recyclerView, new RecyclerViewClickListen.ClickListener() {

                    /**
                     * on Click for data change.
                     *
                     * @param view the view.
                     * @param index the index.
                     */
                    @Override
                    public void onClick(View view, int index) {
                        indexSelectedItem = index;
                        stringSelectedItem = itemsList.get(indexSelectedItem);

                        Toast.makeText(getActivity(), "Selected Item: " + stringSelectedItem, Toast.LENGTH_SHORT).show();
                        view.setBackgroundResource(R.drawable.drawable_button_ripple_recyclerview);
                    }
                }));

                initializeAdapter(recyclerViewBudget);
            }

            /**
             * For a cancelled write to the database.
             *
             * @param error the database error.
             */
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("LOG", "DB ERROR");
            }
        });
    }


    /**
     * Initiates the main activity upon clicked.
     */
    public void backBtnClicked() {
        Intent mainIntent = new Intent(getActivity(), MainActivity.class);
        startActivity(mainIntent);
    }
}