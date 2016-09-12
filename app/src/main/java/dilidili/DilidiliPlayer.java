package dilidili;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by Administrator on 2016-07-13.
 */

public class DilidiliPlayer extends WebView{
    private String url;
    private ProgressBar mProgressBar;

    public DilidiliPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setProgressBar(ProgressBar progressBar) {
        mProgressBar = progressBar;
    }

    private void init() {
        setBackgroundColor(Color.parseColor("#000000"));
        getSettings().setJavaScriptEnabled(true);
        getSettings().setMediaPlaybackRequiresUserGesture(true);
        setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                loadUrl(url);
                return true;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
    public void reload(){
        reload();
    }
    public void loadUrl(){
        loadUrl(url);
    }
}
