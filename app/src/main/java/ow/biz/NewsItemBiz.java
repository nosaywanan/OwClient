package ow.biz;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ow.Utils.HtmlUtils;
import ow.Utils.ImageUtils;
import ow.bean.OwNewsItem;
import ow.bean.OwNewsPageBean;

public class NewsItemBiz
{

    public List<OwNewsItem> getNewsItem(String urlStr)
    {
        List<OwNewsItem> newsList = new ArrayList<OwNewsItem>();
        try
        {
            //String htmlStr = DataUtils.doGet(urlStr);
            //Document document= Jsoup.connect("http://ow.17173.com/news/?__stay_on_pc").get();
            Document document = Jsoup.connect(urlStr).timeout(5000).get();
            //Document document= Jsoup.parse(htmlStr);
            Elements itemsElementsImg = document.getElementsByClass("art-item-c1");
            Elements itemsElementsText = document.getElementsByClass("art-item-c2");
            for (int i = 0; i < itemsElementsImg.size(); i++)
            {
                OwNewsItem item = new OwNewsItem();
                String imgLink = itemsElementsImg.get(i).getElementsByTag("img").attr("src");
                String content = itemsElementsText.get(i).getElementsByTag("p").text();
                String title = itemsElementsText.get(i).getElementsByTag("a").text();
                String htmlLink = itemsElementsText.get(i).getElementsByTag("a").attr("href");
                String date = itemsElementsText.get(i).getElementsByClass("date").text();
                item.setContent(content);
                item.setId(i);
                item.setDate(date);
                item.setImgLink(imgLink);
                item.setTitle(title);
                item.setLink(htmlLink);
                LoadImgTask task = new LoadImgTask();
                task.execute(item);
                if (!htmlLink.equals(""))
                    newsList.add(item);
            }

        } catch (IOException e)
        {
            Log.e("cuowu", "chaoshi");
            e.printStackTrace();
            return null;
        }
        return newsList;
    }

    public OwNewsPageBean parseNewsPage(String urlStr)
    {
        long start = System.currentTimeMillis();
        Log.e("OwNewsPageBean****start", start + "");
        urlStr = urlStr + OwNewsPageBean.URLExtra;
        OwNewsPageBean pageBean = new OwNewsPageBean();

        if (didPageHasMovie(urlStr))
        {
            //如果包含视频插件,则去解析带视频的网页
            pageBean = parseNewsPageWithMovie(urlStr);

        } else
        {
            //否则就按照普通网页解析
            pageBean = parseNewsPageWithOnlyText(urlStr);
        }
        Log.e("OwNewsPageBean**end", System.currentTimeMillis() + "");
        Log.e("OwNewsPageBean**消耗", System.currentTimeMillis() - start + "");
        return pageBean;
    }

    private boolean didPageHasMovie(String urlStr)
    {
        try
        {
            Document document = Jsoup.connect(urlStr).get();
            Elements elements = document.getElementsByClass("p-video");
            return elements.size() == 0 ? false : true;
        } catch (Exception e)
        {
            return false;
        }
    }

    /**
     * 解析带movie 的
     *
     * @param urlStr
     * @return
     */
    private OwNewsPageBean parseNewsPageWithMovie(String urlStr)
    {
        OwNewsPageBean pageBean = new OwNewsPageBean();
        List<HashMap<Integer, String>> pageInfoList = new ArrayList<HashMap<Integer, String>>();

        try
        {
            Document document = Jsoup.connect(urlStr).get();
            //body正文内容
            Element element = document.getElementsByClass("gb-final-mod-article").get(0);
            String autor, date, title, movieLink;
            title = document.getElementsByClass("gb-final-tit-article").text();
            autor = document.getElementsByClass("gb-final-mod-info").get(0).getElementsByClass(
                    "gb-final-author")
                    .text();
            date = document.getElementsByClass("gb-final-mod-info").get(0).getElementsByClass(
                    "gb-final-date").text();
            movieLink = document.getElementsByTag("embed").attr("src");

            pageBean.setAutor(autor);
            pageBean.setDate(date);
            pageBean.setTitle(title);
            //只获取p标签的内容
            Elements contentEl = element.getElementsByTag("p");

            for (int i = 0; i < contentEl.size(); i++)
            {
                HashMap<Integer, String> infoMap = new HashMap<Integer, String>();
                if (contentEl.get(i).hasText())
                {
                    infoMap.put(OwNewsPageBean.PAGE_TEXT, contentEl.get(i).text().toString());
                } else
                {
                    infoMap.put(OwNewsPageBean.PAGE_MOVIE, movieLink);
                }

                pageInfoList.add(infoMap);
            }
            pageBean.setPageInfoList(pageInfoList);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return pageBean;
    }

    /**
     * 解析只有文字的网页
     *
     * @param urlStr
     * @return
     */
    private OwNewsPageBean parseNewsPageWithOnlyText(String urlStr)
    {
        OwNewsPageBean pageBean = new OwNewsPageBean();
        List<HashMap<Integer, String>> pageInfoList = new ArrayList<HashMap<Integer, String>>();

        try
        {
            Document document = Jsoup.connect(urlStr).get();
            if (HtmlUtils.isDocumentHas(document, "gb-final-page-current"))
            {
                document = Jsoup.connect(HtmlUtils.getAllContentHtmlLink(urlStr)).get();
            }
            String autor, date, title;
            title = document.getElementsByClass("gb-final-tit-article").text();
            autor = document.getElementsByClass("gb-final-mod-info").get(0).getElementsByClass(
                    "gb-final-author")
                    .text();
            date = document.getElementsByClass("gb-final-mod-info").get(0).getElementsByClass(
                    "gb-final-date").text();
            pageBean.setAutor(autor);
            pageBean.setDate(date);
            pageBean.setTitle(title);
            //正文
            Element article = document.getElementById("mod_article");
            //带有p标签的内容
            Elements contentEl = article.getElementsByTag("p");
            //判断是否有center标签的image
            Elements center = document.getElementsByTag("center");
            if (center.size() > 0)
            {
                //center.size()>0 有center 标签则根据center解析
                for (int i = 0; i < article.children().size(); i++)
                {
                    HashMap<Integer, String> infoMap = new HashMap<Integer, String>();
                    if (article.child(i).nodeName().toString().equals("div"))
                    {
                        //去除没有用的div
                        continue;
                    }
                    if (article.child(i).nodeName().toString().equals("center"))
                    {

                        //center标签,取出img link
                        infoMap.put(OwNewsPageBean.PAGE_IMG,
                                article.child(i).getElementsByTag("img").attr("src"));
                    } else if (article.child(i).nodeName().toString().equals("p"))
                    {
                        //p标签,取出文本
                        infoMap.put(OwNewsPageBean.PAGE_TEXT, article.child(i).text());
                    }
                    if (!infoMap.isEmpty())
                    {
                        //map若为空 ,则不添加
                        pageInfoList.add(infoMap);
                    }

                }
            } else
            {
                //center.size()=0 没有center 标签则根据p解析
                for (int i = 0; i < contentEl.size(); i++)
                {
                    // if(article.child(i).childNodeSize()<2)
                    HashMap<Integer, String> infoMap = new HashMap<Integer, String>();
                    if (contentEl.get(i).hasText())
                    {
                        //p标签有文本,则直接添加文本
                        infoMap.put(OwNewsPageBean.PAGE_TEXT, contentEl.get(i).text());
                    } else
                    {
                        if (contentEl.get(i).getElementsByTag("img").attr("src").equals(""))
                        {
                            //p标签没有文本,判断是否有src 值
                        } else
                        {
                            infoMap.put(OwNewsPageBean.PAGE_IMG,
                                    contentEl.get(i).getElementsByTag("img").attr("src"));
                        }

                    }
                    if (!infoMap.isEmpty())
                    {
                        pageInfoList.add(infoMap);
                    }
                }

            }

            pageBean.setPageInfoList(pageInfoList);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return pageBean;
    }

    class LoadImgTask extends AsyncTask<OwNewsItem, Integer, String>
    {
        @Override
        protected String doInBackground(OwNewsItem... params)
        {

            for (int i = 0; i < params.length; i++)
                params[i].setImgBitmap(ImageUtils.getImageBitmapFromUrl(params[i].getImgLink()));
            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            if (mPictureLoadedListener != null)
                mPictureLoadedListener.OnPictureLoadedSuccess();
        }
    }

    OnPictureLoadedListener mPictureLoadedListener = null;

    public void setPictureLoadedListener(OnPictureLoadedListener pictureLoadedListener)
    {
        mPictureLoadedListener = pictureLoadedListener;
    }

    public interface OnPictureLoadedListener
    {
        void OnPictureLoadedSuccess();

        void OnPictureLoadedFailed();
    }
}
