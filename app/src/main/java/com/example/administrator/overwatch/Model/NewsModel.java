package com.example.administrator.overwatch.Model;

import android.os.AsyncTask;

import com.example.administrator.overwatch.Callback.GetNewsCallBack;

import java.util.ArrayList;
import java.util.List;

import ow.bean.OwNewsItem;
import ow.bean.OwNewsPageBean;
import ow.biz.NewsItemBiz;
import ow.client.Constaint;

/**
 * @author wjf
 * @date 2016-09-19上午 10:09
 */

public class NewsModel implements INewsModel,NewsItemBiz.OnPictureLoadedListener
{
    private List<Integer> mLoadedPage = new ArrayList<Integer>();
    private int mCurrentPage;
    private GetNewsCallBack mCallBack;

    public NewsModel(GetNewsCallBack callBack)
    {
        mCallBack = callBack;
    }

    @Override
    public void getNews(int position)
    {
        getNewList(position);
    }

    @Override
    public void OnPictureLoadedSuccess()
    {
        mCallBack.onPictureLoadSuccess();
    }

    @Override
    public void OnPictureLoadedFailed()
    {
        mCallBack.onPictureLoadFailed("获取图片失败");
    }
    private void getNewList(int position)
    {
        mCurrentPage = position;
        mLoadedPage.add(position);
        LoadNewsTask task = new LoadNewsTask();
        task.execute(position);
    }

    public boolean hasLoadedPage(int pageIndex)
    {
        if (mLoadedPage == null)
            return false;
        for (int i = 0; i < mLoadedPage.size(); i++)
        {
            if (i == pageIndex)
                return true;
        }
        return false;
    }


    class LoadNewsTask extends AsyncTask<Integer, Integer, Integer>
    {
        List<OwNewsItem> newsItems;
        NewsItemBiz mNewsItemBiz =new NewsItemBiz();
        @Override
        protected Integer doInBackground(Integer... params)
        {
            String url = Constaint.OW_NEWS_URL;
            switch (mCurrentPage)
            {
                case Constaint.OW_NEWS:
                    url = Constaint.OW_NEWS_URL;
                    break;
                case Constaint.OW_YXGL:
                    url = Constaint.OW_YXGL_URL;
                    break;
            }
            newsItems = mNewsItemBiz.getNewsItem(
                    url + OwNewsPageBean.URLExtra);
            mNewsItemBiz.setPictureLoadedListener(NewsModel.this);
            if (newsItems==null||newsItems.isEmpty())
                return 0;
            else
                return 1;
        }

        @Override
        protected void onPostExecute(Integer param)
        {
            super.onPostExecute(param);
            if (mCallBack == null)
                return;
            switch (param)
            {
                case 0:
                    //解析出错
                    mCallBack.onFailed("解析网页出错");
                    break;
                case 1:
                    mCallBack.onSucess(newsItems);
                    break;
            }
        }
    }

}
