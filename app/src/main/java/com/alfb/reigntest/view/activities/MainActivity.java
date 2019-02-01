package com.alfb.reigntest.view.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alfb.reigntest.R;
import com.alfb.reigntest.model.models.Article;
import com.alfb.reigntest.model.models.SessionData;
import com.alfb.reigntest.model.utilities.InfoManager;
import com.alfb.reigntest.model.utilities.Utils;
import com.alfb.reigntest.presenter.Main.Main;
import com.alfb.reigntest.view.adapters.ArticlesAdapter;
import com.alfb.reigntest.view.helpers.ArticleItemTouchHelper;
import com.alfb.reigntest.view.helpers.LineDividerItemDecoration;
import com.alfb.reigntest.view.helpers.RecyclerItemClickListener;
import com.alfb.reigntest.view.helpers.RecyclerItemTouchHelperListener;

import java.util.List;

import javax.inject.Inject;


/**
 * @author Alejandro Figueroa
 * @version 1.0
 * @since 30-01-2019
 *
 * Clase que contiene la informacion para mostrar la pantalla de MAIN
 */
@SuppressWarnings ("FieldCanBeLocal")
public class MainActivity extends SuperActivity implements Main.View, RecyclerItemTouchHelperListener
{
    private RecyclerView revListRefresh;
    private SwipeRefreshLayout srlRefresh;
    private TextView lblNotFound;

    private ArticlesAdapter articlesAdapter;
    private boolean isFirstTime = true;

    @Inject
    public SessionData mSessionData;

    @Inject
    public Main.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);

        //Realizamos la inyeccion de dependencia
        doViewInjection();

        //Asignacion de los elementos a las variables
        initializeElements();

        //Asignamos los eventos correspondientes
        eventOnScrollLis();
        eventOnRefreshSwipe();
        eventOnSelectedItem();

        //Refresca la lista de elementos de la lista
        //refreshArticleList();
        presenter.refreshArticles();
    }

    ///////////////////////////////////
    ///                             ///
    ///           METHODS           ///
    ///                             ///
    ///////////////////////////////////

    @Override
    protected void doViewInjection()
    {
        super.doViewInjection();

        //Hacemos la inyeccion de dependencia para que quede habilitado la clase de servicios
        returnAppComponent().inject(this);
    }

    @Override
    protected void initializeElements()
    {
        //Asignacion de los elementos a las variables
        revListRefresh = findViewById(R.id.revListRefresh);
        srlRefresh = findViewById(R.id.srlRefresh);
        lblNotFound = findViewById(R.id.lblDataNotFound);

        presenter.setView(this);
        presenter.setActivity(this);

        InfoManager.instance(this);
    }

    ///////////////////////////////////
    ///                             ///
    ///           REFRESH           ///
    ///                             ///
    ///////////////////////////////////

    /**
     * Refresca los elementos de la lista de la pantalla
     */
    public void refreshList()
    {
        try
        {
            mSessionData.orderArticlesListByDate();

            //Copiamos la direccion del elemento para hacer el manejo mas comodo
            List<Article> auxListElements = mSessionData.getArticleListWithoutDeleted();

            //Verificamos que realmente hayan gestiones cargadas para sino activar el mensaje de no encontradas
            if (auxListElements.size() == 0)
            {
                lblNotFound.setVisibility(View.VISIBLE);

                if (articlesAdapter != null)
                {
                    articlesAdapter.clear();
                }
            }
            else
            {
                lblNotFound.setVisibility(View.GONE);

                //Actualizamos los valores de la lista
                articlesAdapter = new ArticlesAdapter(this, auxListElements);

                revListRefresh.addItemDecoration(new LineDividerItemDecoration(this));

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                revListRefresh.setLayoutManager(layoutManager);

                revListRefresh.setItemAnimator(new DefaultItemAnimator());

                revListRefresh.setAdapter(articlesAdapter);

                ItemTouchHelper.SimpleCallback item = new ArticleItemTouchHelper(0, ItemTouchHelper.LEFT, this);

                new ItemTouchHelper(item).attachToRecyclerView(revListRefresh);

                ItemTouchHelper.SimpleCallback item2 = new ArticleItemTouchHelper(0, ItemTouchHelper.RIGHT, this);

                new ItemTouchHelper(item2).attachToRecyclerView(revListRefresh);
            }
        }
        catch (Exception e)
        {
            //Hacemos visible el elemento de que no hay informacion
            if (lblNotFound != null)
            {
                lblNotFound.setVisibility(View.VISIBLE);
            }

            //Limpiamos los valores del adapter
            if (articlesAdapter != null)
            {
                articlesAdapter.clear();
            }
        }
    }

    ///////////////////////////////////
    ///                             ///
    ///           EVENTOS           ///
    ///                             ///
    ///////////////////////////////////

    /**
     * Evento que se ejecuta en el proceso de SCROLL de la lista
     */
    private void eventOnRefreshSwipe()
    {
        srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                srlRefresh.setRefreshing(false);

                //Llamamos al presentador para que haga la llamada al servico
                presenter.refreshArticles();
            }
        });
    }

    private void eventOnSelectedItem()
    {
        revListRefresh.addOnItemTouchListener(new RecyclerItemClickListener(this, revListRefresh,
                new RecyclerItemClickListener.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(View view, int position)
                    {
                        mSessionData.setSelectedArticle(mSessionData.getArticleList().get(position));

                        Utils.callAppCompatActivity(MainActivity.this, ArticleDetailActivity.class);
                    }

                    @Override
                    public void onLongItemClick(View view, int position)
                    {
                        // do whatever
                    }
                })
        );
    }

    /**
     * Evento que se ejecuta al realizar el SCROLL de la lista
     */
    private void eventOnScrollLis()
    {
        revListRefresh.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);

                int topRowVerticalPosition = (revListRefresh == null || revListRefresh.getChildCount() == 0) ? 0 : revListRefresh.getChildAt(0).getTop();

                srlRefresh.setEnabled(dx == 0 && topRowVerticalPosition >= 0);
            }
        });
    }

    @Override
    public void onSwipe(RecyclerView.ViewHolder itemHolder, int direction, int position)
    {
        if (itemHolder instanceof ArticlesAdapter.ItemHolder)
        {
            final int deletedIndex = itemHolder.getAdapterPosition();

            String idDeleted = articlesAdapter.returnIDElementDeleted(deletedIndex);

            if (!idDeleted.equals(""))
            {
                InfoManager.instance(this).saveDeletedArticleID(idDeleted);
            }

            articlesAdapter.removeItem(deletedIndex);
        }
    }

    ///////////////////////////////////
    ///                             ///
    ///          RESPONSE           ///
    ///                             ///
    ///////////////////////////////////

    @Override
    public void successAccess(boolean showMessage)
    {
        refreshList();

        if (showMessage && !isFirstTime)
        {
            Toast.makeText(this, "Artículos Actualizados", Toast.LENGTH_SHORT).show();
        }
        else
        {
            isFirstTime = false;
        }
    }

    @Override
    public void rejectAccess(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
