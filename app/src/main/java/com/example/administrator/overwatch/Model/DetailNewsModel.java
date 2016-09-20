package com.example.administrator.overwatch.Model;

import android.os.AsyncTask;

import com.example.administrator.overwatch.Callback.GetDetailNewsCallBack;

import ow.bean.OwNewsPageBean;
import ow.biz.NewsItemBiz;

/**
 * @author wjf
 * @date 2016-09-19上午 10:09
 */

public class DetailNewsModel
{
    private GetDetailNewsCallBack mCallback;

    public DetailNewsModel(GetDetailNewsCallBack callback)
    {
        mCallback = callback;
    }

    public void loadDetailNews(String urlStr)
    {
        DetailedNewTask task = new DetailedNewTask();
        task.execute(urlStr);
    }

    class DetailedNewTask extends AsyncTask<String, Integer, OwNewsPageBean>
    {
        NewsItemBiz mNewsItemBiz = new NewsItemBiz();
        @Override
        protected OwNewsPageBean doInBackground(String... params)
        {
            return mNewsItemBiz.parseNewsPage(params[0]);
        }

        @Override
        protected void onPostExecute(OwNewsPageBean pageBean)
        {
            mCallback.onSucess(pageBean);
        }
    }
}
