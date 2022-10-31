package skieg.travel.planner.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.nio.charset.CharacterCodingException;

import skieg.travel.MainActivity;
import skieg.travel.R;
<<<<<<< HEAD
import skieg.travel.planner.Utility.SlideAdapter;
=======
>>>>>>> origin/dev

public class MainPlanner extends FragmentActivity {


<<<<<<< HEAD
    private ViewPager2 pager;
    private FragmentStateAdapter FSA;
    final public static int amountOfFragments = 2;
=======
    private ViewPager2 viewPager2;
    private FragmentStateAdapter FSA;
>>>>>>> origin/dev

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planner_view_pager);

<<<<<<< HEAD
        pager = findViewById(R.id.pager);
        FSA = new SlideAdapter(this);
        pager.setAdapter(FSA);
    }


=======
        viewPager2 = findViewById(R.id.pager);
        FSA = new Slider(this);
    }

    private class Slider extends FragmentStateAdapter{

        public Slider(MainPlanner thisActivity){
            super(thisActivity);
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
>>>>>>> origin/dev
}
