package skieg.travel.planner.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.nio.charset.CharacterCodingException;

import skieg.travel.MainActivity;
import skieg.travel.R;
import skieg.travel.planner.Utility.SlideAdapter;
import skieg.travel.planner.transformers.ZoomOutTransformer;


public class MainPlanner extends FragmentActivity {
    private ViewPager2 pager;
    private FragmentStateAdapter FSA;
    final public static int amountOfFragments = 2;
    
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planner_view_pager);

        pager = findViewById(R.id.pager);
        FSA = new Slider(this);
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
                    return new ChecklistFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}
