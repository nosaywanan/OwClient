package com.example.administrator.overwatch.View;

import java.util.List;

import ow.bean.OwNewsItem;

/**
 * @author wjf
 * @date 2016-09-19上午 10:18
 */

public interface IGetNewsView extends IBaseView
{
    void updateContent(List<OwNewsItem> owNewsItemList);
}
