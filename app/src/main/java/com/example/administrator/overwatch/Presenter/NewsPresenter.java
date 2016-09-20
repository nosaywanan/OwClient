package com.example.administrator.overwatch.Presenter;

import com.example.administrator.overwatch.Callback.GetNewsCallBack;
import com.example.administrator.overwatch.Model.NewsModel;
import com.example.administrator.overwatch.View.IGetNewsView;

import java.util.List;

import ow.bean.OwNewsItem;

/**
 * @author wjf
 * @date 2016-09-19上午 10:14
 */

public class NewsPresenter implements GetNewsCallBack
{
    private NewsModel mNewsModel;
    private IGetNewsView mIGetNewsView;
    public NewsPresenter(IGetNewsView newsView)
    {
        mIGetNewsView = newsView;
        mNewsModel = new NewsModel(this);
    }

    public void getNews(int position)
    {
        mNewsModel.getNews(position);
    }

    public boolean hasLoadedPage(int position)
    {
       return mNewsModel.hasLoadedPage(position);
    }

    @Override
    public void onSucess(List<OwNewsItem> owNewsItemList)
    {
        mIGetNewsView.updateContent(owNewsItemList);
    }

    @Override
    public void onFailed(String errorText)
    {
        mIGetNewsView.showError(errorText);
    }

    @Override
    public void onPictureLoadSuccess()
    {
        mIGetNewsView.updateBoundary();
    }

    @Override
    public void onPictureLoadFailed(String errortext)
    {
        mIGetNewsView.showError(errortext);
    }
}
