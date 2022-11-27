package skieg.travel.planner.fragments;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewClickListen implements RecyclerView.OnItemTouchListener {

    public ClickListener clickListener;
    public GestureDetector gestureDetector;

    public RecyclerViewClickListen(Context context, final RecyclerView recyclerView, final ClickListener clickListener){
        this.clickListener = clickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

        View item = rv.findChildViewUnder(e.getX(),e.getY());
        if (item != null && clickListener != null && gestureDetector.onTouchEvent(e)){
            clickListener.onClick(item, rv.getChildAdapterPosition(item));
        }

        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public interface ClickListener{
        void onClick(View view, int index);
    }

}
