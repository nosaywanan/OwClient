package ow.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * 网页具体内容Bean
 *
 * @author Administrator
 */
public class OwNewsPageBean implements Serializable
{
    public static final String URLExtra = "?__stay_on_pc";
    public static final int PAGE_TEXT = 0;
    public static final int PAGE_IMG = 1;
    public static final int PAGE_MOVIE = 2;
    public String autor;
    public String date;
    public String title;
    // -------------------type ,----内容-------------------
    public List<HashMap<Integer, String>> pageInfoList;

    public String getAutor()
    {
        return autor;
    }

    public void setAutor(String autor)
    {
        this.autor = autor;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public List<HashMap<Integer, String>> getPageInfoList()
    {
        return pageInfoList;
    }

    public void setPageInfoList(List<HashMap<Integer, String>> pageInfoList)
    {
        this.pageInfoList = pageInfoList;
    }

}
