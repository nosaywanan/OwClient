package com.example.administrator.overwatch.Callback;

import ow.bean.OwNewsPageBean;

/**
 * @author wjf
 * @date 2016-09-19下午 5:08
 */

public interface GetDetailNewsCallBack extends BaseNewsCallback
{
    void onSucess(OwNewsPageBean owNewsPageBean);
}
