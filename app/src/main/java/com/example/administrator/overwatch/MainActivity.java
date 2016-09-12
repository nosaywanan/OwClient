package com.example.administrator.overwatch;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.overwatch.Adapter.MyRevAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ow.Utils.ImageUtils;
import ow.Utils.StringUtils;
import ow.bean.OwNewsItem;
import ow.bean.OwNewsPageBean;
import ow.biz.NewsItemBiz;
import ow.client.Constaint;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity implements NewsItemBiz.OnPictureLoadedListener {
    ImageView splash;
    TextView splashTv;
    private static final int ANIM_TIME = 3000;
    private static final float SCALE_END = 1.25F;
    private ProgressBar mProgressbar;
    List<OwNewsItem> newsItems;
    NewsItemBiz biz;
    RecyclerView mMyRecycleView;
    MyRevAdapter adapter;
    static final int REFRESH = 0;
    static final int START = 1;
    // SwipeRefreshLayout mSwipeRefreshLayout;
    View mWelcomeView;
    FrameLayout mWindow;
    TextView mTitmTitle;
    TabLayout mTabLayout;
    ViewPager mViewPager;
    View newsPage, yxglPage;
    List<View> mViews=new ArrayList<View>();
    int mCurrentPage=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTransParentStateBar();
        mWelcomeView = getLayoutInflater().inflate(R.layout.activity_splash, null);
        mWindow = (FrameLayout) getWindow().getDecorView();
        mWindow.addView(mWelcomeView);
        lunchWelcome();
        initTab();
        initView();
    }


    private void initTab() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        TabLayout.Tab tab = mTabLayout.newTab();
        tab.setText("新闻");
        mTabLayout.addTab(tab);
        tab = mTabLayout.newTab();
        tab.setText("攻略");
        mTabLayout.addTab(tab);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        newsPage = getLayoutInflater().inflate(R.layout.new_view_pager, null);
        yxglPage = getLayoutInflater().inflate(R.layout.new_view_pager, null);
        mViews.add(newsPage);
        mViews.add(yxglPage);
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mViews==null?0:mViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                mViewPager.addView(mViews.get(position));
                return mViews.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "新闻全搜索";
                    case 1:
                        return "攻略大全";
                    default:
                        return null;
                }
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                mCurrentPage=position;
                mProgressbar.setVisibility(View.GONE);
                if (!hasLoadedPage(position)) {
                    getNewList(position);
                    mProgressbar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private boolean hasLoadedPage(int pageIndex){
        if (mLoadedPage==null)
            return false;
        for (int i=0;i<mLoadedPage.size();i++){
            if (i==pageIndex)
                return true;
        }
        return false;
    }
    //设置全屏幕状态栏
    private void setTransParentStateBar() {
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
    //设置正常状态栏
    private void setNomalStateBar() {
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }

    private void initView() {
        getNewList(Constaint.OW_YXGL);
        biz = new NewsItemBiz();
        biz.setPictureLoadedListener(this);
        initRecycleView(0);
    }
    private  void  initRecycleView(int postion){
        View view=mViews.get(postion);
        mMyRecycleView = (RecyclerView)view.findViewById(R.id.news_listView);
        mMyRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mProgressbar = (ProgressBar) findViewById(R.id.progress);
        mMyRecycleView.setItemAnimator(new DefaultItemAnimator());
    }
    /**
     * 显示欢迎界面
     */
    private void lunchWelcome() {
        splash = (ImageView) mWelcomeView.findViewById(R.id.splash);
        splashTv= (TextView) mWelcomeView.findViewById(R.id.splash_tv);
        ImageView matrixImage= (ImageView) mWelcomeView.findViewById(R.id.matrix_iv);
        matrixImage.setImageBitmap(ImageUtils.getMatrixBitmap(this));
        splashTv.setText(StringUtils.getReplactringWithColor("OverWatch",getResources().getColor(R.color.colorAccent),"O","W"));
        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        startWelcomAnim();
                    }
                });
    }

    private void startWelcomAnim() {
        final Animation fadeOut = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(splash, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(splash, "scaleY", 1f, SCALE_END);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIM_TIME).play(animatorX).with(animatorY);
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mWelcomeView.startAnimation(fadeOut);
                mWindow.removeView(mWelcomeView);
                setNomalStateBar();
            }
        });
    }
    private List<Integer> mLoadedPage=new ArrayList<Integer>();
    private void setAdapter() {
        initRecycleView(mCurrentPage);
        adapter = new MyRevAdapter(this, newsItems);
        // adapter.setList(newsItems);
        mMyRecycleView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyRevAdapter.OnRevItemClickListener() {
            @Override
            public void OnClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, DetailedNewActivity.class);
                intent.putExtra("link", newsItems.get(position).getLink());
                startActivity(intent);
            }
        });
    }

    private void updateUI() {
        adapter.notifyDataSetChanged();
    }

    private void getNewList(int position) {
        mLoadedPage.add(position);
        LoadNewsTask task = new LoadNewsTask();
        task.execute(position);
    }


    class LoadNewsTask extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected Integer doInBackground(Integer... params) {
            String url = Constaint.OW_NEWS_URL;
            int result = REFRESH;
            switch (mCurrentPage) {
                case Constaint.OW_NEWS:
                    url = Constaint.OW_NEWS_URL;
                    break;
                case Constaint.OW_YXGL:
                    url = Constaint.OW_YXGL_URL;
                    break;
            }
            newsItems = biz.getNewsItem(url + OwNewsPageBean.URLExtra);
            return 0;
        }

        @Override
        protected void onPostExecute(Integer param) {
            super.onPostExecute(param);
            mProgressbar.setVisibility(View.GONE);
            setAdapter();
        }
    }

    @Override
    public void OnSuccess() {
        updateUI();
    }

    @Override
    public void OnFailed() {

    }
}
