package com.example.administrator.overwatch.Callback;

import java.util.List;

import ow.bean.OwNewsItem;

/**
 * @author wjf
 * @date 2016-09-19上午 10:10
 */

public interface GetNewsCallBack extends BaseNewsCallback
{
    void onSucess(List<OwNewsItem> owNewsItemList);
    void onPictureLoadSuccess();
    void onPictureLoadFailed(String errortext);
}
