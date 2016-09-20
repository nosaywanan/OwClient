package dilidili;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Administrator on 2016-07-13.
 */

public class DilidiliPlayer extends WebView
{
    private String url;

    public DilidiliPlayer(Context context)
    {
        this(context, null);
    }

    public DilidiliPlayer(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    @Override
    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }


    private void init()
    {
        setBackgroundColor(Color.parseColor("#000000"));
        getSettings().setJavaScriptEnabled(true);
        getSettings().setMediaPlaybackRequiresUserGesture(true);
        setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                if (mOnPageLoadListener!=null)
                    mOnPageLoadListener.OnPageLoadSuccess();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request,
                                        WebResourceError error)
            {
                super.onReceivedError(view, request, error);
                if (mOnPageLoadListener!=null)
                    mOnPageLoadListener.OnPageLoadFailed(error.toString());
            }

        });
    }

    public void reload()
    {
        reload();
    }

    public void loadUrl()
    {
        loadUrl(url);
    }
    private OnPageLoadListener mOnPageLoadListener;

    public void setOnPageLoadListener(OnPageLoadListener onPageLoadListener)
    {
        mOnPageLoadListener = onPageLoadListener;
    }

    public interface OnPageLoadListener
    {
        void OnPageLoadSuccess();

        void OnPageLoadFailed(String error);
    }
}
