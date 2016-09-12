/*
package ow.RxAndroid;

import android.view.View;
import android.widget.Toast;

import com.example.administrator.overwatch.Adapter.NewsListAdapter;

import java.util.List;

import ow.bean.OwNewsItem;
import ow.bean.OwNewsPageBean;
import ow.biz.NewsItemBiz;
import ow.client.Constaint;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

*/
/**
 * Created by Administrator on 2016-07-23.
 *//*


public class AsyncRx {
    public static List<OwNewsItem> getNewsList() {
        List<OwNewsItem> newsItems ;
        final NewsItemBiz biz = new NewsItemBiz();
        Observable.create(new Observable.OnSubscribe<List<OwNewsItem>>() {
            @Override
            public void call(Subscriber<? super List<OwNewsItem>> subscriber) {
                newsItems = biz.getNewsItem(Constaint.OW_NEWS_URL + OwNewsPageBean.URLExtra);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<OwNewsItem>>() {
                    @Override
                    public void onCompleted() {
                        adapter.notifyDataSetChanged();
                        mProgressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getBaseContext(), "Error on NetWork", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(List<OwNewsItem> owNewsItems) {
                        if (newsListView.getAdapter() == null) {
                            adapter = new NewsListAdapter(getApplicationContext(), newsItems);
                            newsListView.setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}
*/
