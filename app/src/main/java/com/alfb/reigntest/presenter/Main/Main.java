package com.alfb.reigntest.presenter.Main;

import android.app.Activity;

import com.alfb.reigntest.presenter.Super.SuperPresenter;


/**
 * @author Alejandro Figueroa
 * @version 1.0
 * @since 30-01-2019
 *
 * Interfaz para el manejo de VISTA y PRESENTADOR de la pantalla de MAIN
 */
public interface Main
{
    interface View
    {
        void successAccess (boolean showMessage);
        void rejectAccess (String message);
    }

    interface Presenter extends SuperPresenter
    {
        void setView (Main.View view);
        void setActivity (Activity activity);
        void refreshArticles ();
        void responseSuccess (boolean showMessage);
        void responseFailed (String message);
    }
}
