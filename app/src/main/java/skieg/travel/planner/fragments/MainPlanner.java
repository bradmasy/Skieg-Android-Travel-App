package skieg.travel.planner.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.CharacterCodingException;
import java.util.ArrayList;

import skieg.travel.CalendarFragment;
import skieg.travel.DatabaseParse;
import skieg.travel.MainActivity;
import skieg.travel.R;
import skieg.travel.planner.Utility.SlideAdapter;
import skieg.travel.planner.transformers.ZoomOutTransformer;


public class MainPlanner extends FragmentActivity {
    private ViewPager2 pager;
    private FragmentStateAdapter FSA;
    final public static int amountOfFragments = 2;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    RecyclerViewChecklist recyclerViewChecklist;
    ArrayList<String> itemsList = new ArrayList<>();
    ArrayList<Boolean> checkedList = new ArrayList<>();
    ArrayList<String> idList = new ArrayList<>();
    ChecklistFragment checklistFragment;



    
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planner_view_pager);

        pager = findViewById(R.id.pager);
        FSA = new SlideAdapter(this);
        pager.setAdapter(FSA);
        pager.setPageTransformer(new ZoomOutTransformer());
    }

    private class Slider extends FragmentStateAdapter{

        public Slider(MainPlanner thisActivity){
            super(thisActivity);
            Log.d("SLIDER", "CREATER ");
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch(position){
                case 0:
                    return new BudgetFragment();
                case 1:
                    // DOES SAME THING
//                    checklistFragment = new ChecklistFragment();
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.fragmentContainer, checklistFragment);
//                    fragmentTransaction.commit();
//                    getDataFromFirebase();
//                    return checklistFragment;
                    return new ChecklistFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return amountOfFragments;
        }
    }


    public void getDataFromFirebase() {
        Log.d("LOG", "IN DATA SNAPSHOT METHOD1");
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

                // TEST THIS NEXT
                recyclerViewChecklist = new RecyclerViewChecklist(itemsList, checkedList);
                checklistFragment.initializeAdapter(recyclerViewChecklist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("LOG", "DB ERROR");
            }
        });
    }
}
