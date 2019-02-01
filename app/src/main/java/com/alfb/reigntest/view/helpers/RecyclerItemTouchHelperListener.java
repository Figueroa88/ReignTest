package com.alfb.reigntest.view.helpers;

import android.support.v7.widget.RecyclerView;


public interface RecyclerItemTouchHelperListener
{
    void onSwipe (RecyclerView.ViewHolder itemHolder, int direction, int position);
}
