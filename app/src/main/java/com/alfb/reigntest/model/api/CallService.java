package com.alfb.reigntest.model.api;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;

import com.alfb.reigntest.R;
import com.alfb.reigntest.model.models.Article;
import com.alfb.reigntest.model.models.SessionData;
import com.alfb.reigntest.model.utilities.InfoManager;
import com.alfb.reigntest.model.utilities.Utils;
import com.alfb.reigntest.presenter.Main.Main;
import com.alfb.reigntest.presenter.Super.SuperPresenter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * @author Alejandro Figueroa
 * @version 1.0
 * @since 30-01-2019
 *
 * Clase que controla la llamada a los servicios WEB para la consulta o envio de informacion (se usa
 * la libreria de RETROFIT para hacer la operacion)
 */
@SuppressWarnings({"unchecked","FieldCanBeLocal"})
public class CallService
{
    private ApiClient mApiClient;
    private ProgressDialog mLoading;
    private static Call<SessionData> mCall;

    private SessionData mSessionData;

    ///////////////////////////////////
    ///                             ///
    ///         CONSTRUCTOR         ///
    ///                             ///
    ///////////////////////////////////

    public CallService (ApiClient apiClient, SessionData sessionData)
    {
        this.mApiClient = apiClient;
        this.mSessionData = sessionData;
    }

    ///////////////////////////////////
    ///                             ///
    ///       METHODS EXECUTE       ///
    ///                             ///
    ///////////////////////////////////

    /**
     * Metodo que ejecuta la llamada a un servicio WEB usando RETROFIT
     *
     * @param codeService Int con el codigo del servicio a consultar
     * @param parameters Object con los parametros a enviar al servicio en caso de que existan
     * @param currentActivity Activity donde se ubica actualmente
     */
    public void execute (final int codeService, Object parameters, final Activity currentActivity, final SuperPresenter presenter)
    {
        //Verificamos si existe acceso internet

        if (Utils.isHaveInternetConnection(currentActivity))
        {
            //Ejecutamos el cargando que indica que se esta procesando la app
            showLoading(currentActivity);

            //Verificamos a cual servicio se desea acceder para asignar el CALL correspondiente

            mCall = mApiClient.getAllArticles();

            //Realizamos la llamada y consulta de los servicios

            mCall.enqueue(new Callback<SessionData>()
            {
                @Override
                public void onResponse(@NonNull Call<SessionData> call, @NonNull Response<SessionData> response)
                {
                    if (response.isSuccessful())
                    {
                        SessionData result = response.body();

                        if (result != null)
                        {
                            if (result.getArticleList().size() > 0)
                            {
                                //Almacenamos de forma de String JSON el arreglo de los articulos

                                InfoManager.instance(currentActivity).saveArticleList(result.returnStringJSONArticleList());
                            }

                            //Copiamos los datos obtenidos
                            mSessionData.copyInfo(result);

                            doResponseSuccess(codeService, presenter, true);
                        }
                        else
                        {
                            doResponseFailed(codeService, "Sin Respuesta del Servidor", presenter);
                        }
                    }
                    else
                    {
                        //Si hubo algun algun status que no sea 200

                        doResponseFailed(codeService, "Error en conexion con el Servidor", presenter);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SessionData> call, @NonNull Throwable error)
                {
                    //Si hubo algun error al tratar de hacer la conexion

                    doResponseFailed(codeService, error.getMessage(), presenter);
                }
            });
        }
        else
        {
            //Si no possee acceso internet al realizar una consulta se procede a ver cual es el
            //codigo de la operacion que se desea realizar para evaluar la accion

            //Verificamos si se tiene articulos almacenados previamente
            //y en caso de ser verdadero se cargara la informacion

            if (InfoManager.instance(currentActivity).returnArticleListJSON().equals(""))
            {
                //No hay informacion

                doResponseFailed(codeService, "No pose pueden cargar los artículos por falta de conexión", presenter);
            }
            else
            {
                //Si existe informacion

                Gson gson = new Gson();

                String articleListJSON = InfoManager.instance().returnArticleListJSON();

                Type typeListArticles = new TypeToken<List<Article>>(){}.getType();

                List<Article> newListRoom = gson.fromJson(articleListJSON, typeListArticles);

                mSessionData.copyArticlesList(newListRoom);

                doResponseSuccess(codeService, presenter, false);
            }
        }
    }

    ///////////////////////////////////
    ///                             ///
    ///      METHODS RESPONSE       ///
    ///                             ///
    ///////////////////////////////////

    /**
     * Metodo que se ejecuta cuando se recibe una respuesta positiva o exitosa de un servicio web
     *
     * @param codeService Int con el codigo del servicio que se llamo
     * @param presenter SuperPresenter de la pantalla donde se ubica
     * @param showMessage boolean que indica si es necesario mostrar el mensaje de actualizacon o no
     */
    private void doResponseSuccess (int codeService, SuperPresenter presenter, boolean showMessage)
    {
        switch (codeService)
        {
            case ApiClient.ARTICLES_SV_CODE:

                ((Main.Presenter) presenter).responseSuccess(showMessage);

                break;
        }

        dismissLoading();
    }

    /**
     * Metodo que se ejecuta cuando hubo algun error en la consulta del SERVICIO WEB
     *
     * @param codeService Int con el codigo del servicio que se llamo
     * @param message String con el mensaje que indica el error que ocurrio
     * @param presenter SuperPresenter de la pantalla donde se ubica
     */
    private void doResponseFailed (int codeService, String message, SuperPresenter presenter)
    {
        switch (codeService)
        {
            case ApiClient.ARTICLES_SV_CODE:

                ((Main.Presenter) presenter).responseFailed(message);

                break;
        }

        dismissLoading();
    }

    ///////////////////////////////////
    ///                             ///
    ///       METHODS LOADING       ///
    ///                             ///
    ///////////////////////////////////

    /**
     * Muestra un mensaje de cargando en la pantalla para indicar el proceso de la llamada a los
     * servicios web
     */
    private void showLoading (Context context)
    {
        if (mLoading == null)
        {
            mLoading = new ProgressDialog(context, R.style.CuThemeProgressDialogT);

            mLoading.setTitle("");
            mLoading.setMessage(context.getString(R.string.general_progress_bar_text_loading));
            mLoading.setCanceledOnTouchOutside(false);
            mLoading.setCancelable(false);

            mLoading.show();
        }
        else if (!mLoading.isShowing())
        {
            mLoading = new ProgressDialog(context, R.style.CuThemeProgressDialogT);

            mLoading.setTitle("");
            mLoading.setMessage(context.getString(R.string.general_progress_bar_text_loading));
            mLoading.setCanceledOnTouchOutside(false);
            mLoading.setCancelable(false);
            mLoading.show();
        }
    }

    /**
     * Oculta el mensaje de cargando que se mostro cuando se realizaba la ejecucion de alguna
     * llamada a un servicio web
     */
    private void dismissLoading ()
    {
        if (mLoading != null && mLoading.isShowing())
        {
            mLoading.dismiss();
        }
    }
}
