package com.alfb.reigntest.view.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alfb.reigntest.di.AppComponent;
import com.alfb.reigntest.di.SessionApp;
import com.alfb.reigntest.model.models.SessionData;

import javax.inject.Inject;


/**
 * @author Alejandro Figueroa
 * @version 1.0
 * @since 30-01-2019
 *
 * Clase abstracta para la herencia de las ACTIVITIES
 */
public abstract class SuperActivity extends AppCompatActivity
{
    @Inject
    public SessionData mSessionData;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    ///////////////////////////////////
    ///                             ///
    ///           METHODS           ///
    ///                             ///
    ///////////////////////////////////

    /**
     * Retorna el COMPONENTE DAGGER
     *
     * @return AppComponent
     */
    protected AppComponent returnAppComponent ()
    {
        return ((SessionApp) getApplication()).getAppComponent();
    }

    /**
     * En las clases hijos, realiza la inyeccion de dependencia
     */
    protected void doViewInjection ()
    {
        //Utilizado por los hijos para realizar la inyeccion de dependencia
    }

    /**
     * En la clases hijos, se encarga de setear todos los componentes de la vista con las variables
     * de la misma
     */
    protected void initializeElements ()
    {
        //Utilizado por los hijos para realizar la inyeccion de dependencia
    }
}
