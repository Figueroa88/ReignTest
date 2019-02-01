package com.alfb.reigntest.model.api;

import com.alfb.reigntest.model.models.SessionData;

import retrofit2.Call;
import retrofit2.http.GET;


/**
 * @author Alejandro Figueroa
 * @version 1.0
 * @since 30-01-2019
 *
 * Clase INTERFAZ usada para el realizar las llamada a los servicios web a traves de RETROFIT
 */
public interface ApiClient
{
    //Url Base

    String BASE_URL = "http://hn.algolia.com";

    //Code Call Service
    int ARTICLES_SV_CODE = 100;

    //Sides

    String SIDE_ARTICLES = "/api/v1/search_by_date?query=android";


    @GET(SIDE_ARTICLES)
    public Call<SessionData> getAllArticles ();
}
