package ow.Widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import ow.Utils.DimensionUtils;

/**
 * Created by Administrator on 2016-07-22.
 */

public class NewPageLinearLayout  extends LinearLayout{

    public NewPageLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        int padding= DimensionUtils.dip2px(context,16);
        setPadding(padding,padding,padding,padding);
    }
    public void addTextView(View view){
        LayoutParams mLayoutParams=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        addView(view,mLayoutParams);
    }

    public void addImageView(View view){
        int margin=DimensionUtils.dip2px(getContext(),16);
        LayoutParams mLayoutParams=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        mLayoutParams.setMargins(0,margin,0,margin);
        addView(view,mLayoutParams);
    }
}
