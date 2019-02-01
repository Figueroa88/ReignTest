package com.alfb.reigntest.model.models;

import android.support.annotation.NonNull;

import com.alfb.reigntest.model.utilities.Utils;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Date;


/**
 * @author Alejandro Figueroa
 * @version 1.0
 * @since 30-01-2019
 *
 * Clase MODELO para el almacenamiento de la informacion de un ARTICULO
 */
public class Article
{
    @SerializedName("created_at_i")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("story_title")
    private String storyTitle;
    @SerializedName("author")
    private String author;
    @SerializedName("created_at")
    private String dateCreated;
    @SerializedName("comment_text")
    private String comment;

    ///////////////////////////////////
    ///                             ///
    ///         CONSTRUCTOR         ///
    ///                             ///
    ///////////////////////////////////

    public Article ()
    {
        id = 0;
        title = "";
        storyTitle = "";
        author = "";
        dateCreated = "";
        comment = "";
    }

    ///////////////////////////////////
    ///                             ///
    ///          GETS y SETS        ///
    ///                             ///
    ///////////////////////////////////

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getCorrectTitle ()
    {
        String valueReturn;

        if (title == null || title.equals(""))
        {
            valueReturn = storyTitle;
        }
        else
        {
            valueReturn = title;
        }

        return valueReturn;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getStoryTitle()
    {
        return storyTitle;
    }

    public void setStoryTitle(String storyTitle)
    {
        this.storyTitle = storyTitle;
    }

    public String getAuthor ()
    {
        return author;
    }

    public void setAuthor (String author)
    {
        this.author = author;
    }

    public Long getDateOrder ()
    {
        long valueReturn = 0;

        if (!dateCreated.equals(""))
        {
            String[] splitOne = dateCreated.split("T");

            if (splitOne.length > 1)
            {
                String[] splitTwo = splitOne[1].split("\\.");

                if (splitTwo.length > 0)
                {
                    String goodDate = splitOne[0] + " " + splitTwo[0];

                    valueReturn = getTimeBetweenDateTwo(goodDate);
                }
            }
        }

        return valueReturn;
    }

    public String getDateCreated ()
    {
        String valueReturn = dateCreated;

        if (!dateCreated.equals(""))
        {
            String[] splitOne = dateCreated.split("T");

            if (splitOne.length > 1)
            {
                String[] splitTwo = splitOne[1].split("\\.");

                if (splitTwo.length > 0)
                {
                    String goodDate = splitOne[0] + " " + splitTwo[0];

                    valueReturn = getTimeBetweenDate(goodDate, splitOne[0]);
                }
            }
        }

        return valueReturn;
    }

    public void setDateCreated (String dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    @Override
    @NonNull
    public String toString()
    {
        String valueReturn;

        try
        {
            JSONObject articleJSON = new JSONObject();

            articleJSON.put("parent_id", getId());
            articleJSON.put("title", getTitle());
            articleJSON.put("story_title", getStoryTitle());
            articleJSON.put("author", getAuthor());
            articleJSON.put("created_at", getDateCreated());

            valueReturn = articleJSON.toString();
        }
        catch (Exception e)
        {
            valueReturn = "";
        }

        return valueReturn;
    }

    public JSONObject toJSONObject ()
    {
        JSONObject elementReturn = new JSONObject();

        try
        {
            elementReturn.put("parent_id", getId());
            elementReturn.put("title", getTitle());
            elementReturn.put("story_title", getStoryTitle());
            elementReturn.put("author", getAuthor());
            elementReturn.put("created_at", getDateCreated());
        }
        catch (Exception e)
        {
            //Nothing
        }

        return elementReturn;
    }

    private String getTimeBetweenDate (String newDateCreated, String onlyDate)
    {
        String timeReturn = newDateCreated;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strCurrentDate = Utils.getCurrentDate("yyyy-MM-dd HH:mm:ss");

        if (newDateCreated != null && !newDateCreated.equals(""))
        {
            try
            {
                Date createdDate = simpleDateFormat.parse(newDateCreated);
                Date currentDate = simpleDateFormat.parse(strCurrentDate);

                long diff = currentDate.getTime() - createdDate.getTime();
                long seconds = diff / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;

                hours = Math.abs(hours);

                if (hours == 0)
                {
                    minutes = Math.abs(minutes);

                    timeReturn = String.valueOf(minutes) + "m";
                }
                else if (hours >= 24 && hours < 48)
                {
                    timeReturn = "Ayer";
                }
                else if (hours < 24)
                {
                    timeReturn = String.valueOf(hours) + "h";
                }
                else if (hours > 48)
                {
                    timeReturn = onlyDate;
                }
            }
            catch (Exception e)
            {
               timeReturn = onlyDate;
            }
        }

        return timeReturn;
    }

    private long getTimeBetweenDateTwo (String newDateCreated)
    {
        long timeReturn = 0;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strCurrentDate = Utils.getCurrentDate("yyyy-MM-dd HH:mm:ss");

        if (newDateCreated != null && !newDateCreated.equals(""))
        {
            try
            {
                Date createdDate = simpleDateFormat.parse(newDateCreated);
                Date currentDate = simpleDateFormat.parse(strCurrentDate);

                long diff = currentDate.getTime() - createdDate.getTime();
                long seconds = diff / 1000;
                long minutes = seconds / 60;

                minutes = Math.abs(minutes);

                timeReturn = minutes;
            }
            catch (Exception e)
            {
                timeReturn = 0;
            }
        }

        return timeReturn;
    }
}
