package skieg.travel.planner.fragments;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Recycler view click listener.
 */
public class RecyclerViewClickListen implements RecyclerView.OnItemTouchListener {

    public ClickListener clickListener;
    public GestureDetector gestureDetector;

    /**
     * Constructor for click listener.
     *
     * @param context the context.
     * @param recyclerView a recycler view.
     * @param clickListener a click listener.
     */
    public RecyclerViewClickListen(Context context, final RecyclerView recyclerView, final ClickListener clickListener){
        this.clickListener = clickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    /**
     * Execute on an intercepted touch event.
     *
     * @param rv a recycler view.
     * @param e a motion view.
     * @return a boolean.
     */
    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

        View item = rv.findChildViewUnder(e.getX(),e.getY());
        if (item != null && clickListener != null && gestureDetector.onTouchEvent(e)){
            clickListener.onClick(item, rv.getChildAdapterPosition(item));
        }
        return false;
    }

    /**
     * On Touch Event.
     *
     * @param rv a recycler view.
     * @param e a motion event.
     */
    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

    }

    /**
     * on requested disallowed intercept.
     *
     * @param disallowIntercept boolean.
     */
    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    /**
     * interface for onclick listener.
     */
    public interface ClickListener{
        void onClick(View view, int index);
    }
}
