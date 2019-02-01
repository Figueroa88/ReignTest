package com.alfb.reigntest.di;

import android.app.Application;
import android.content.Context;

import com.alfb.reigntest.model.api.ApiClient;
import com.alfb.reigntest.model.api.CallService;
import com.alfb.reigntest.model.models.SessionData;
import com.alfb.reigntest.presenter.Main.Main;
import com.alfb.reigntest.presenter.Main.MainPresenter;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @author Alejandro Figueroa
 * @version 1.0
 * @since 30-01-2019
 *
 * Clase MODULO creada para el manejo de los elementos generales de la aplicacion con DAGGER
 */
@Module
public class AppModule
{
    private final Application application;

    ///////////////////////////////////
    ///                             ///
    ///         CONSTRUCTOR         ///
    ///                             ///
    ///////////////////////////////////

    public AppModule(Application application)
    {
        this.application = application;
    }

    ///////////////////////////////////
    ///                             ///
    ///           CONTEXT           ///
    ///                             ///
    ///////////////////////////////////

    @Provides
    @Singleton
    public Application provideApplication ()
    {
        return application;
    }

    @Provides
    @Singleton
    public Context provideApplicationContext()
    {
        return application;
    }

    ///////////////////////////////////
    ///                             ///
    ///        SESSION DATA         ///
    ///                             ///
    ///////////////////////////////////

    /**
     * Este metodo te permite obtener una sola instancia del modelo que contendra todos los datos de la app,
     * el cual sera el que se utilice en la vida de la aplicacion (Es singleton ya que seria el mismo para
     * toda la vida de la app)
     *
     * @return SessionData
     */
    @Provides
    @Singleton
    public SessionData provideUser()
    {
        return new SessionData();
    }

    ///////////////////////////////////
    ///                             ///
    ///         CONNECTION          ///
    ///                             ///
    ///////////////////////////////////

    @Provides
    @Singleton
    public Gson provideGson ()
    {
        return new Gson();
    }

    @Provides
    @Singleton
    public GsonConverterFactory provideGsonConverterFactory ()
    {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient()
    {
        return new OkHttpClient.Builder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit (OkHttpClient client, GsonConverterFactory converterFactory)
    {
        return new Retrofit.Builder()
                .baseUrl(ApiClient.BASE_URL)
                .addConverterFactory(converterFactory)
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    public ApiClient provideApiClient (Retrofit retrofit)
    {
        return retrofit.create(ApiClient.class);
    }

    @Provides
    @Singleton
    public CallService provideCallService (ApiClient apiClient, SessionData sessionData)
    {
        return new CallService(apiClient, sessionData);
    }

    ///////////////////////////////////
    ///                             ///
    ///         PRESENTERS          ///
    ///                             ///
    ///////////////////////////////////

    //En cada uno de los presentadores para el Dagger se debe retornar la interfaz en el metodo
    //pero el metodo que se le hace NEW es a la clase presentador

    @Provides
    @Singleton
    public Main.Presenter provideLoginPresenter (CallService callService)
    {
        return new MainPresenter(callService);
    }
}
