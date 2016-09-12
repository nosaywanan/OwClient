package ow.Utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

/**
 * Created by Administrator on 2016-09-06.
 */

public class StringUtils {
    public static SpannableString getReplactringWithColor(String originStr,int color,String... keywords){
        SpannableString ss=new SpannableString(originStr);
        for(String key:keywords){
            int length=key.length();
            int start= originStr.indexOf(key);
            if (start>-1){
                ss.setSpan(new ForegroundColorSpan(color),start,start+length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return  ss;
    }

}
