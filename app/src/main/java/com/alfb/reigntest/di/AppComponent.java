package com.alfb.reigntest.di;

import com.alfb.reigntest.view.activities.ArticleDetailActivity;
import com.alfb.reigntest.view.activities.MainActivity;
import com.alfb.reigntest.view.adapters.ArticlesAdapter;

import javax.inject.Singleton;

import dagger.Component;


/**
 * @author Alejandro Figueroa
 * @version 1.0
 * @since 30-01-2019
 *
 * Interfaz componente para la inyeccion de dependencia en las diferentes vistas de la APP
 */
@Singleton
@Component (modules = AppModule.class)
public interface AppComponent
{
    void inject (MainActivity mainActivity);
    void inject (ArticleDetailActivity articleDetailActivity);

    void inject (ArticlesAdapter articlesAdapter);
}
