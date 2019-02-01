package com.alfb.reigntest.view.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alfb.reigntest.R;
import com.alfb.reigntest.model.models.Article;

import java.util.List;


/**
 * @author Alejandro Figueroa
 * @version 1.0
 * @since 30-01-2019
 *
 * Clase ADAPTER para el manejo de los elementos de la lista de ARTICULOS
 */
public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ItemHolder>
{
    private List<Article> mListElements;


    public ArticlesAdapter (Activity context, List<Article> listElements)
    {
        this.mListElements = listElements;
    }


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.o_row_article, parent, false);

        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position)
    {
        final Article item = mListElements.get(position);

        if (item != null)
        {
            holder.lblTitle.setText(item.getCorrectTitle());

            String authorTime = item.getAuthor() + " - " + item.getDateCreated();

            holder.lblAuthorTime.setText(authorTime);
        }
    }

    @Override
    public int getItemCount()
    {
        return mListElements.size();
    }

    public void removeItem (int position)
    {
        mListElements.remove(position);

        notifyItemRemoved(position);
    }

    public String returnTitleElementDeleted (int index)
    {
        String title = "";

        if (mListElements != null)
        {
            if (mListElements.size() > index)
            {
                title = mListElements.get(index).getCorrectTitle();
            }
        }

        return title;
    }

    public String returnIDElementDeleted (int index)
    {
        String title = "";

        if (mListElements != null)
        {
            if (mListElements.size() > index)
            {
                title = String.valueOf(mListElements.get(index).getId());
            }
        }

        return title;
    }

    public void clear()
    {
        final int size = mListElements.size();
        mListElements.clear();
        notifyItemRangeRemoved(0, size);
    }


    public class ItemHolder extends RecyclerView.ViewHolder
    {
        private TextView lblTitle;
        private TextView lblAuthorTime;

        public LinearLayout lnlViewBack;
        public RelativeLayout rllViewForeground;

        public LinearLayout lnlViewEdit;
        public LinearLayout lnlViewTrash;

        private ItemHolder(View itemView)
        {
            super(itemView);

            lblTitle = itemView.findViewById(R.id.lblRowTitle);
            lblAuthorTime = itemView.findViewById(R.id.lblRowAuthorTime);

            lnlViewBack = itemView.findViewById(R.id.rllElementTrash);
            rllViewForeground = itemView.findViewById(R.id.rllForeground);

            lnlViewEdit = itemView.findViewById(R.id.lnlElementEdit);
            lnlViewTrash = itemView.findViewById(R.id.lnlElementTrash);
        }
    }
}
