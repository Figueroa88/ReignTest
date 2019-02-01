package com.alfb.reigntest.di;

import android.app.Application;


/**
 * @author Alejandro Figueroa
 * @version 1.0
 * @since 30-01-2019
 *
 * Clase APPLICATION usada para la configuracion de ciertos elementos usados en la vida de la APP
 */
public class SessionApp extends Application
{
    private AppComponent mAppComponent;


    @Override
    public void onCreate()
    {
        super.onCreate();

        //Inicializamos los componentes que se usaran en la aplicacion
        initializeAppComponent();
    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();
    }

    ///////////////////////////////////
    ///                             ///
    ///       APP COMPONENTS        ///
    ///                             ///
    ///////////////////////////////////

    private void initializeAppComponent ()
    {
        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent ()
    {
        return mAppComponent;
    }
}
