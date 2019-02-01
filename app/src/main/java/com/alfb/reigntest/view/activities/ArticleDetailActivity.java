package com.alfb.reigntest.view.activities;

import android.os.Bundle;
import android.webkit.WebView;

import com.alfb.reigntest.R;
import com.alfb.reigntest.model.models.SessionData;

import javax.inject.Inject;


/**
 * @author Alejandro Figueroa
 * @version 1.0
 * @since 30-01-2019
 *
 * Clase que contiene la informacion para mostrar la pantalla de DETALLE DE ARTICULO
 */
@SuppressWarnings("FieldCanBeLocal")
public class ArticleDetailActivity extends SuperActivity
{
    private WebView webArticleDetail;

    @Inject
    public SessionData mSessionData;


    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_article_detail);

        //Realizamos la inyeccion de dependencia
        doViewInjection();

        //Asignacion de los elementos a las variables
        initializeElements();
    }

    ///////////////////////////////////
    ///                             ///
    ///           METHODS           ///
    ///                             ///
    ///////////////////////////////////

    @Override
    protected void doViewInjection ()
    {
        super.doViewInjection();

        //Hacemos la inyeccion de dependencia para que quede habilitado la clase de servicios
        returnAppComponent().inject(this);
    }

    @Override
    protected void initializeElements ()
    {
        //Asignacion de los elementos a las variables

        webArticleDetail = findViewById(R.id.webArticleDetail);
        String html = "<html><head></head><body><b>" + mSessionData.getSelectedArticle().getCorrectTitle() + "</b><br><br>" +
                mSessionData.getSelectedArticle().getComment() + "</body></html>";

        webArticleDetail.loadDataWithBaseURL("", html, "text/html", "utf-8", "");
    }
}
