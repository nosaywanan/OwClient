package com.example.administrator.overwatch.View;

/**
 * @author wjf
 * @date 2016-09-19下午 4:55
 */

public interface IBaseView
{
    //显示错误信息
    abstract void showError(String error);

    //更新界面
    abstract void updateBoundary();
}
