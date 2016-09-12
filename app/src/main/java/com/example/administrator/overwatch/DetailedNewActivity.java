package com.example.administrator.overwatch;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import ow.Utils.ViewUtils;
import ow.Widget.NewPageLinearLayout;
import ow.bean.OwNewsPageBean;
import ow.biz.NewsItemBiz;

/**
 * Created by Administrator on 2016-07-22.
 */

public class DetailedNewActivity extends Activity  {
    TextView autor, date, title;
    String urlStr;
    NewsItemBiz mNewsItemBiz;
    OwNewsPageBean mPageBean;
    ProgressBar mProgressBar;
    NewPageLinearLayout mNewPageLinearLayout;
    ViewUtils mViewUtils;
    View mContentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContentView = getLayoutInflater().inflate(R.layout.activity_detailed_new_v2, null);
        setContentView(mContentView);
        urlStr = getIntent().getStringExtra("link");
        initView();
        initData();

    }

    private void initView() {
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mNewPageLinearLayout = (NewPageLinearLayout) findViewById(R.id.news_page);
        autor = (TextView) findViewById(R.id.news_autor);
        date = (TextView) findViewById(R.id.news_date);
        title = (TextView) findViewById(R.id.news_title);
    }

    private void initData() {
        mNewsItemBiz = new NewsItemBiz();
        mViewUtils = new ViewUtils();
        DetailedNewTask task = new DetailedNewTask();
        task.execute(urlStr);
    }

    public void updateView() {
        mNewPageLinearLayout.invalidate();
        mContentView.invalidate();
    }

    class DetailedNewTask extends AsyncTask<String, Integer, OwNewsPageBean> {
        @Override
        protected OwNewsPageBean doInBackground(String... params) {
            mPageBean = mNewsItemBiz.parseNewsPage(params[0]);
            return mPageBean;
        }

        @Override
        protected void onPostExecute(OwNewsPageBean pageBean) {
            mProgressBar.setVisibility(View.INVISIBLE);
            title.setText(mPageBean.getTitle());
            autor.setText(mPageBean.getAutor());
            date.setText(mPageBean.getDate());
            mViewUtils.getContentView(mNewPageLinearLayout, mPageBean.getPageInfoList());
            updateView();
        }

    }
}
