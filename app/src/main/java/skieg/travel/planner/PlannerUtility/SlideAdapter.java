package skieg.travel.planner.PlannerUtility;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import skieg.travel.planner.fragments.BudgetFragment;
import skieg.travel.planner.fragments.ChecklistFragment;
import skieg.travel.planner.fragments.MainPlanner;

public class SlideAdapter extends FragmentStateAdapter {

    public SlideAdapter(MainPlanner thisActivity){
        super(thisActivity);
    }

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

    @Override
    public int getItemCount() {
        return MainPlanner.amountOfFragments;
    }

}
