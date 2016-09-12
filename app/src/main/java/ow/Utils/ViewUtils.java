package ow.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import ow.Widget.NewPageLinearLayout;
import ow.bean.OwNewsPageBean;

/**
 * Created by Administrator on 2016-07-22.
 */

public class ViewUtils {


    public void getContentView(NewPageLinearLayout linearLayout, List<HashMap<Integer, String>> pageInfoList) {
        // NewPageLinearLayout linearLayout = new NewPageLinearLayout(context, null);
        long start = System.currentTimeMillis();
        Log.e("getContentView**start", start + "");
        String content;
        String imgLink;
        for (int i = 0; i < pageInfoList.size(); i++) {
            switch (getContentType(pageInfoList.get(i))) {
                case OwNewsPageBean.PAGE_TEXT:
                    content = pageInfoList.get(i).get(OwNewsPageBean.PAGE_TEXT);
                    linearLayout.addTextView(createTextView(linearLayout.getContext(), content));
                    break;
                case OwNewsPageBean.PAGE_IMG:
                    imgLink = pageInfoList.get(i).get(OwNewsPageBean.PAGE_IMG);
                    linearLayout.addImageView(createImageView(linearLayout.getContext(), imgLink));
                    break;
                case OwNewsPageBean.PAGE_MOVIE:
                    break;
            }
        }
        ascyLoadContentImage();
        Log.e("getContentView**end", System.currentTimeMillis() + "");
        Log.e("getContentView**耗时", System.currentTimeMillis() - start + "");
    }


    public int getContentType(HashMap<Integer, String> map) {
        int type = OwNewsPageBean.PAGE_TEXT;
        for (HashMap.Entry<Integer, String> entry : map.entrySet()) {
            type = entry.getKey();
        }
        return type;
    }

    private TextView createTextView(Context context, String content) {
        TextView textView = new TextView(context);
        textView.setTextColor(Color.BLACK);
        textView.setLineSpacing(4, 1.2f);
//        int padding= DimensionUtils.dip2px(context,8);
//        textView.setPadding(padding,padding,padding,padding);
        textView.setText(content);
        return textView;
    }

    HashMap<ImageView, String> imgMap = new HashMap<ImageView, String>();

    private ImageView createImageView(Context context, String imageLink) {

        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setBackgroundColor(Color.WHITE);
        imgMap.put(imageView, imageLink);
        // imageView.setImageBitmap();
        return imageView;
    }

    private void ascyLoadContentImage() {
        LoadContentImageTask task = new LoadContentImageTask();
        task.execute(imgMap);
    }

    class LoadContentImageTask extends AsyncTask<HashMap<ImageView, String>, Integer, HashMap<ImageView, Bitmap>> {

        @Override
        protected HashMap<ImageView, Bitmap> doInBackground(HashMap<ImageView, String>... params) {

            Log.e("LoadImageTask-doInBack", System.currentTimeMillis() + "");
            HashMap<ImageView, Bitmap> map = new HashMap<ImageView, Bitmap>();
            ImageView key;
            String link;
            Bitmap bitmap;
            for (HashMap<ImageView, String> param : params) {
                for (HashMap.Entry<ImageView, String> entry : param.entrySet()) {
                    key = entry.getKey();
                    link = entry.getValue();
                    bitmap = ImageUtils.getContentImageFromUrl(link);
                    map.put(key, bitmap);
                }
            }
            return map;
        }

        @Override
        protected void onPostExecute(HashMap<ImageView, Bitmap> map) {
            Log.e("LoadImageTask-onPostExe", System.currentTimeMillis() + "");
            ImageView key;
            Bitmap bitmap;
            LinearLayout.LayoutParams params;
            super.onPostExecute(map);
            for (HashMap.Entry<ImageView, Bitmap> entry : map.entrySet()) {
                key = entry.getKey();
                bitmap = entry.getValue();
                if (bitmap != null) {
                    params = (LinearLayout.LayoutParams) key.getLayoutParams();
                    params.width = bitmap.getWidth();
                    params.height = bitmap.getHeight();
                    key.setLayoutParams(params);
                    key.setImageBitmap(bitmap);
                    key.invalidate();
                }

            }
        }
    }
}
