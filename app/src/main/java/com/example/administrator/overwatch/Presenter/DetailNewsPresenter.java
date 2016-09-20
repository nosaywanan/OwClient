package com.example.administrator.overwatch.Presenter;

import com.example.administrator.overwatch.Callback.GetDetailNewsCallBack;
import com.example.administrator.overwatch.Model.DetailNewsModel;
import com.example.administrator.overwatch.View.IDetailNewsView;

import ow.bean.OwNewsPageBean;

/**
 * @author wjf
 * @date 2016-09-19下午 5:03
 */

public class DetailNewsPresenter implements GetDetailNewsCallBack
{
    IDetailNewsView mDetailNewsView;
    DetailNewsModel mDetailNewsModel;

    public DetailNewsPresenter(IDetailNewsView view)
    {
        mDetailNewsView = view;
        mDetailNewsModel = new DetailNewsModel(this);
    }

    public void loadDetailNews(String urlStr)
    {
        mDetailNewsModel.loadDetailNews(urlStr);
    }


    @Override
    public void onFailed(String errorText)
    {
        mDetailNewsView.showError(errorText);
    }

    @Override
    public void onSucess(OwNewsPageBean owNewsPageBean)
    {
        mDetailNewsView.updateDetailNews(owNewsPageBean);
    }
}
