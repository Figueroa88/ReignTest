package com.alfb.reigntest.model.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author Alejandro Figueroa
 * @version 1.0
 * @since 30-12-2019
 *
 * Clase que se encargara de manejar el salvado y recuperacion de la informacion que se encuentre
 * almacenado en el dispositivo
 */
public class InfoManager
{
    //Objeto
    private static InfoManager mInfoManager;

    //Variables de la clase

    private SharedPreferences mSharedPreferences;

    //Nombre de los las keys de los elementos que se almacenaran en la app

    private final String mKeyArticleList = "ArticleListReign";
    private final String mKeyArticlesDeleted = "ArticlesDeleted";
    private final String mKeyArticlesTitleDeleted = "ArticlesDeletedTitle";

    ///////////////////////////////////
    ///                             ///
    ///        CONSTRUCTOR          ///
    ///                             ///
    ///////////////////////////////////

    private InfoManager(Context context)
    {
        this.mSharedPreferences = context.getSharedPreferences("com.alfb.reigntest", Context.MODE_PRIVATE);
    }

    ///////////////////////////////////
    ///                             ///
    ///         INSTANCIA           ///
    ///                             ///
    ///////////////////////////////////

    public static InfoManager instance ()
    {
        return mInfoManager;
    }

    public static InfoManager instance (Context context)
    {
        if (mInfoManager == null)
        {
            mInfoManager = new InfoManager(context);
        }

        return mInfoManager;
    }

    ///////////////////////////////////
    ///                             ///
    ///    ALMACEN DE ELEMENTOS     ///
    ///                             ///
    ///////////////////////////////////

    /**
     * Almacena la informacion de los articulos que se obtuvieron en formato JSON
     *
     * @param jsonData String con los articulos
     */
    public void saveArticleList (String jsonData)
    {
        mSharedPreferences.edit().putString(mKeyArticleList, jsonData).apply();
    }

    /**
     * Almacena el ID de un articulo que se haya eliminado para que al momento de cargar nuevamente
     * los datos, el mismo ya no aparezca
     *
     * @param idDeleted String con el id del articulo que se elimino
     */
    public void saveDeletedArticleID (String idDeleted)
    {
        boolean addNewID = true;

        Set<String> allIds = mSharedPreferences.getStringSet(mKeyArticlesDeleted, new HashSet<String>());

        Set<String> cloneAllIds = new HashSet<>(allIds);

        mSharedPreferences.edit().remove(mKeyArticlesDeleted).apply();

        for (String element: cloneAllIds)
        {
            if (element.equals(idDeleted))
            {
                addNewID = false;
            }
        }

        if (addNewID)
        {
            cloneAllIds.add(idDeleted);
        }

        mSharedPreferences.edit().putStringSet(mKeyArticlesDeleted, cloneAllIds).apply();
    }

    /**
     * Almacena el TITULO de un articulo que se haya eliminado para que al momento de cargar nuevamente
     * los datos, el mismo ya no aparezca
     *
     * @param titleDeleted String con el titulo del articulo que se elimino
     */
    public void saveDeletedArticleTitle (String titleDeleted)
    {
        boolean addNewID = true;

        Set<String> allIds = mSharedPreferences.getStringSet(mKeyArticlesTitleDeleted, new HashSet<String>());

        Set<String> cloneAllIds = new HashSet<>(allIds);

        mSharedPreferences.edit().remove(mKeyArticlesTitleDeleted).apply();

        for (String element: cloneAllIds)
        {
            if (element.equals(titleDeleted))
            {
                addNewID = false;
            }
        }

        if (addNewID)
        {
            cloneAllIds.add(titleDeleted);
        }

        mSharedPreferences.edit().putStringSet(mKeyArticlesTitleDeleted, cloneAllIds).apply();
    }

    ///////////////////////////////////
    ///                             ///
    ///     RETURN DE ELEMENTOS     ///
    ///                             ///
    ///////////////////////////////////

    /**
     * Retorna la lista de articulos obtenidos del servicio que fueron almacenados en formato JSON
     *
     * @return String
     */
    public String returnArticleListJSON ()
    {
        return mSharedPreferences.getString(mKeyArticleList, "");
    }

    /**
     * Retorna la lista de los ID de los articulos que fueron eliminados
     *
     * @return List<Integer>
     */
    public List<Integer> returnDeletedArticlesIDList ()
    {
        List<Integer> elementsReturn = new ArrayList<>();

        Set<String> allIds = mSharedPreferences.getStringSet(mKeyArticlesDeleted, new HashSet<String>());

        if (allIds != null)
        {
            for (String element: allIds)
            {
                elementsReturn.add(Integer.valueOf(element));
            }
        }

        return elementsReturn;
    }

    /**
     * Retorna la lista de los TITULOS de los articulos que fueron eliminados
     *
     * @return List<String>
     */
    public List<String> returnDeletedArticlesTitleList ()
    {
        List<String> elementsReturn = new ArrayList<>();

        Set<String> allIds = mSharedPreferences.getStringSet(mKeyArticlesTitleDeleted, new HashSet<String>());

        if (allIds != null)
        {
            for (String element: allIds)
            {
                elementsReturn.add(element);
            }
        }

        return elementsReturn;
    }
}
