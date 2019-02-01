package com.alfb.reigntest.view.helpers;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.alfb.reigntest.view.adapters.ArticlesAdapter;


/**
 * @author Alejandro Figueroa
 * @version 1.0
 * @since 30-01-2019
 *
 * Clase para la ejecucion del evento de touch en los elementos de la fila de los articulos
 */
public class ArticleItemTouchHelper extends ItemTouchHelper.SimpleCallback
{
    private RecyclerItemTouchHelperListener mListener;


    public ArticleItemTouchHelper (int dragDirs, int swipeDirs, RecyclerItemTouchHelperListener listener)
    {
        super(dragDirs, swipeDirs);

        this.mListener = listener;
    }


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
    {
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
    {
        if (mListener != null)
        {
            mListener.onSwipe(viewHolder, direction, viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder)
    {
        View foregroundView = ((ArticlesAdapter.ItemHolder) viewHolder).rllViewForeground;

        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection)
    {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState)
    {
        if (viewHolder != null)
        {
            View foregroundView = ((ArticlesAdapter.ItemHolder) viewHolder).rllViewForeground;

            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive)
    {
        View foregroundView = ((ArticlesAdapter.ItemHolder) viewHolder).rllViewForeground;

        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull  RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive)
    {
        View foregroundView = ((ArticlesAdapter.ItemHolder) viewHolder).rllViewForeground;

        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
    }
}
