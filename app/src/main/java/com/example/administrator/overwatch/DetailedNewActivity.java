package com.example.administrator.overwatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.overwatch.Presenter.DetailNewsPresenter;
import com.example.administrator.overwatch.View.IDetailNewsView;

import java.util.Random;

import ow.Utils.ViewUtils;
import ow.Widget.NewPageLinearLayout;
import ow.bean.OwNewsPageBean;
import ow.biz.NewsItemBiz;

/**
 * Created by Administrator on 2016-07-22.
 */

public class DetailedNewActivity extends Activity implements IDetailNewsView
{
    TextView autor, date, title;
    String urlStr;
    NewsItemBiz mNewsItemBiz;
    OwNewsPageBean mPageBean;
    ProgressBar mProgressBar;
    NewPageLinearLayout mNewPageLinearLayout;
    ViewUtils mViewUtils;
    View mContentView;
    DetailNewsPresenter mPresenter;
    int[] backResouce = {R.drawable.back, R.drawable.back1, R.drawable.back2};

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContentView = getLayoutInflater().inflate(R.layout.activity_detailed_new_v2, null);
        setContentView(mContentView);
        urlStr = getIntent().getStringExtra("link");
        initTitleBackground();
        initView();
        initData();

    }

    private void initTitleBackground()
    {
        ImageView titleBack = (ImageView) findViewById(R.id.title_back);
        Random random = new Random(100);
        int randomPic = random.nextInt((int) SystemClock.currentThreadTimeMillis());
        titleBack.setImageResource(backResouce[randomPic%3]);
    }

    private void initView()
    {
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mNewPageLinearLayout = (NewPageLinearLayout) findViewById(R.id.news_page);
        autor = (TextView) findViewById(R.id.news_autor);
        date = (TextView) findViewById(R.id.news_date);
        title = (TextView) findViewById(R.id.news_title);
    }

    private void initData()
    {
        mPresenter = new DetailNewsPresenter(this);
        mPresenter.loadDetailNews(urlStr);
    }

    public void updateView()
    {
        mNewPageLinearLayout.invalidate();
        mContentView.invalidate();
    }

    @Override
    public void showError(String error)
    {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateBoundary()
    {
        updateView();
    }

    @Override
    public void updateDetailNews(OwNewsPageBean owNewsPageBean)
    {
        mProgressBar.setVisibility(View.GONE);
        title.setText(owNewsPageBean.getTitle());
        autor.setText(owNewsPageBean.getAutor());
        date.setText(owNewsPageBean.getDate());
        ViewUtils.getInstance().getContentView(mNewPageLinearLayout,
                owNewsPageBean.getPageInfoList());
        updateView();
    }
}
