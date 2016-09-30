package com.example.administrator.overwatch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import dilidili.DilidiliPlayer;

/**
 * @author wjf
 * @date 2016-09-20下午 1:45
 */

public class Test extends AppCompatActivity implements DilidiliPlayer.OnPageLoadListener
{
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        DilidiliPlayer dilidiliPlayer = (DilidiliPlayer) findViewById(R.id.dilidili);
        //"http://f.v.17173cdn.com/player_f2/MzY0MTg0NjM.swf"
        dilidiliPlayer.setUrl("http://f.v.17173cdn.com/player_f2/MzY0MTg0NjM.swf");
        dilidiliPlayer.loadUrl();
        dilidiliPlayer.setOnPageLoadListener(this);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
    }

    @Override
    public void OnPageLoadSuccess()
    {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void OnPageLoadFailed(String error)
    {
        mProgressBar.setVisibility(View.GONE);
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
