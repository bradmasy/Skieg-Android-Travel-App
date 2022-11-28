package skieg.travel.planner.fragments;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import skieg.travel.R;
import skieg.travel.planner.Utility.SlideAdapter;
import skieg.travel.planner.transformers.ZoomOutTransformer;

/**
 * Main planner activity.
 */
public class MainPlanner extends FragmentActivity {
    private ViewPager2 pager;
    private FragmentStateAdapter FSA;
    final public static int amountOfFragments = 2;

    /**
     * On create method.
     *
     * @param savedInstanceState a bundle of saved instance data from the previous activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planner_view_pager);

        pager = findViewById(R.id.pager);
        FSA = new SlideAdapter(this);
        pager.setAdapter(FSA);
        pager.setPageTransformer(new ZoomOutTransformer());
    }

    /**
     * Slider Class.
     */
    private class Slider extends FragmentStateAdapter{

        /**
         * Constructor for a slider.
         *
         * @param thisActivity the main planner activity.
         */
        public Slider(MainPlanner thisActivity){
            super(thisActivity);
        }

        /**
         * Creates fragments based on the users position in the view pager.
         *
         * @param position the position of the user in the view pager.
         * @return the fragment.
         */
        @NonNull
        @Override
        public PlannerFragment createFragment(int position) {
            switch(position){
                case 0:
                    return new BudgetFragment();
                case 1:
                    return new ChecklistFragment();
                default:
                    return null;
            }
        }

        /**
         * Gets the amount of fragments.
         *
         * @return the amount of fragments.
         */
        @Override
        public int getItemCount() {
            return amountOfFragments;
        }
    }
}
