package ow.bean;

import android.graphics.Bitmap;

import java.io.Serializable;

public class OwNewsItem implements Serializable
{

    private int id;

    /**
     * 标题
     */
    private String title;
    /**
     * 链接
     */
    private String link;
    /**
     * 发布日期
     */
    private String date;
    /**
     * 图片的链接
     */
    private String imgLink;
    /**
     * 内容
     */
    private String content;
    private Bitmap imgBitmap;
    /**
     * 类型
     */
    private int newsType;

    public int getNewsType()
    {
        return newsType;
    }

    public void setNewsType(int newsType)
    {
        this.newsType = newsType;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getImgLink()
    {
        return imgLink;
    }

    public void setImgLink(String imgLink)
    {
        this.imgLink = imgLink;
    }

    public Bitmap getImgBitmap()
    {
        return imgBitmap;
    }

    public void setImgBitmap(Bitmap imgBitmap)
    {
        this.imgBitmap = imgBitmap;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    @Override
    public String toString()
    {
        return "NewsItem [id=" + id + ", title=" + title + ", link=" + link + ", date=" + date +
               ", imgLink=" + imgLink
               + ", content=" + content + ", newsType=" + newsType + "]";
    }

}
