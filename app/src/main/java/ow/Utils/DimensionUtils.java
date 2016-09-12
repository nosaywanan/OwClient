package ow.Utils;

import android.content.Context;

/**
 * Created by Administrator on 2016-07-22.
 */

public class DimensionUtils {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
