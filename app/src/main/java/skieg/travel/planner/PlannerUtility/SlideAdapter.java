package skieg.travel.planner.PlannerUtility;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import skieg.travel.planner.fragments.BudgetFragment;
import skieg.travel.planner.fragments.ChecklistFragment;
import skieg.travel.planner.fragments.MainPlanner;

/**
 * Slide adapter class.
 */
public class SlideAdapter extends FragmentStateAdapter {

    /**
     * Slide adapter constructor.
     *
     * @param thisActivity the activity we are applying the adapter to.
     */
    public SlideAdapter(MainPlanner thisActivity){
        super(thisActivity);
    }

    /**
     * Creates a fragment.
     *
     * @param position the position of the fragment.
     * @return a fragment.
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // PlannerFragment
        Fragment plannerFragment;
        switch(position){
            case 0:
                plannerFragment =  new BudgetFragment();
                break;
            case 1:
                plannerFragment = new ChecklistFragment();
                break;
            default:
                plannerFragment =  null;
                break;
        }
        return plannerFragment;
    }

    /**
     * Gets the item count.
     *
     * @return the amount of fragments.
     */
    @Override
    public int getItemCount() {
        return MainPlanner.amountOfFragments;
    }

}
