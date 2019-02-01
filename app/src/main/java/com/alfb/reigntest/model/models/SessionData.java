package com.alfb.reigntest.model.models;

import com.alfb.reigntest.model.utilities.InfoManager;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * @author Alejandro Figueroa
 * @version 1.0
 * @since 31-01-2019
 *
 * Clase MODELO de almacenamiento GLOBAL de la informacion usada en la APP
 */
public class SessionData
{
    private int hitsPerPage;
    @SerializedName("hits")
    private List<Article> articleList;

    private Article selectedArticle;

    ///////////////////////////////////
    ///                             ///
    ///          GETS y SETS        ///
    ///                             ///
    ///////////////////////////////////

    public int getHitsPerPage ()
    {
        return hitsPerPage;
    }

    public void setHitsPerPage (int hitsPerPage)
    {
        this.hitsPerPage = hitsPerPage;
    }

    public List<Article> getArticleList ()
    {
        if (articleList == null)
        {
            articleList = new ArrayList<>();
        }

        return articleList;
    }

    public List<Article> getArticleListWithoutDeleted ()
    {
        if (articleList == null)
        {
            articleList = new ArrayList<>();
        }

        //Creamos una nueva lista de articulos que devolver comparandolo con los ids de los
        //elementos que se eliminaron

        List<Article> correctArticles = new ArrayList<>();
        List<Integer> allIDsDeleted = InfoManager.instance().returnDeletedArticlesIDList();
        boolean addElement;
        Integer articleId;

        for (Article element: articleList)
        {
            addElement = true;
            articleId = element.getId();

            for (Integer idDeleted: allIDsDeleted)
            {
                if (articleId.equals(idDeleted))
                {
                    addElement = false;

                    break;
                }
            }

            if (addElement)
            {
                correctArticles.add(element);
            }
        }

        return correctArticles;
    }

    public void setArticleList (List<Article> articleList)
    {
        this.articleList = articleList;
    }

    public Article getSelectedArticle ()
    {
        if (selectedArticle == null)
        {
            selectedArticle = new Article();
        }

        return selectedArticle;
    }

    public void setSelectedArticle (Article selectedArticle)
    {
        this.selectedArticle = selectedArticle;
    }

    ///////////////////////////////////
    ///                             ///
    ///        OTHER METHODS        ///
    ///                             ///
    ///////////////////////////////////

    public void copyInfo (SessionData sessionData)
    {
        setHitsPerPage(sessionData.getHitsPerPage());
        setArticleList(sessionData.getArticleList());
    }

    public void copyArticlesList (List<Article> articleList)
    {
        getArticleList().clear();
        setArticleList(articleList);
    }

    public String returnStringJSONArticleList ()
    {
        String valueReturn;

        JSONArray arrayArticles = new JSONArray();

        List<Article> allElements = getArticleList();

        for (Article element: allElements)
        {
            arrayArticles.put(element.toJSONObject());
        }

        valueReturn = arrayArticles.toString();

        return valueReturn;
    }

    /**
     * Ordena por los ARTICULOS en base a la fecha que fueron creados
     */
    public void orderArticlesListByDate ()
    {
        List<Article> allArticles = getArticleList();

        Collections.sort(allArticles, new Comparator<Article>()
        {
            @Override
            public int compare(Article elementOne, Article elementTwo)
            {
                return elementOne.getDateOrder().compareTo(elementTwo.getDateOrder());
            }
        });

        setArticleList(allArticles);
    }
}
