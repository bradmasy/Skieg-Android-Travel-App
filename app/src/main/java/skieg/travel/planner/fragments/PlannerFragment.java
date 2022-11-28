package skieg.travel.planner.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Planner fragment class.
 */
public class PlannerFragment extends Fragment {

    /**
     * Planner fragment constructor.
     *
     * @param implementation the implementation id.
     */
    public PlannerFragment(int implementation){
        super(implementation);
    }

    /**
     * On view created.
     * @param view a view
     * @param savedInstanceState a bundle representing the previously saved instance data.
     */

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}
