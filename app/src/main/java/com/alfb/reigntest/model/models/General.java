package com.alfb.reigntest.model.models;


/**
 * @author Alejandro Figueroa
 * @version 1.0
 * @since 30-01-2019
 *
 * Clase modelo para almacenar la informacion GENERAL
 */
public class General
{
    private String id;
    private String key;
    private String value;

    ///////////////////////////////////
    ///                             ///
    ///        CONSTRUCTORES        ///
    ///                             ///
    ///////////////////////////////////

    public General ()
    {
        this.id = "";
        this.key = "";
        this.value = "";
    }

    ///////////////////////////////////
    ///                             ///
    ///          GETS y SETS        ///
    ///                             ///
    ///////////////////////////////////

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }
}
