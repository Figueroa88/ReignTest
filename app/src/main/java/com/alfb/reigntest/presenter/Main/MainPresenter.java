package com.alfb.reigntest.presenter.Main;

import android.app.Activity;

import com.alfb.reigntest.model.api.ApiClient;
import com.alfb.reigntest.model.api.CallService;


/**
 * @author Alejandro Figueroa
 * @version 1.0
 * @since 30-01-2019
 *
 * Clase PRESENTADOR para el manejo de la LOGICA de la pantalla de MAIN
 */
public class MainPresenter implements Main.Presenter
{
    private Main.View mView;
    private Activity mActivity;
    private CallService mCallService;

    ///////////////////////////////////
    ///                             ///
    ///         CONSTRUCTOR         ///
    ///                             ///
    ///////////////////////////////////

    public MainPresenter (CallService callService)
    {
        this.mCallService = callService;
    }

    ///////////////////////////////////
    ///                             ///
    ///          HERENCIA           ///
    ///                             ///
    ///////////////////////////////////

    @Override
    public void setView(Main.View view)
    {
        this.mView = view;
    }

    @Override
    public void setActivity(Activity activity)
    {
        this.mActivity = activity;
    }

    @Override
    public void refreshArticles()
    {
        //Llamamos al servicio para obtener los articulos

        mCallService.execute(ApiClient.ARTICLES_SV_CODE, null, mActivity, this);
    }

    @Override
    public void responseSuccess(boolean showMessage)
    {
        mView.successAccess(showMessage);
    }

    @Override
    public void responseFailed(String message)
    {
        mView.rejectAccess(message);
    }
}
