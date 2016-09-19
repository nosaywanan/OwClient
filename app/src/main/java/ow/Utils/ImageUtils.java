package ow.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Administrator on 2016-07-20.
 */

public class ImageUtils
{
    public static Bitmap getImageBitmapFromUrl(String urlStr)
    {
        Bitmap bitmap = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        try
        {
            URL url = new URL(urlStr);
            is = url.openConnection().getInputStream();
            bis = new BufferedInputStream(is);
            bitmap = BitmapFactory.decodeStream(bis);
            options.inSampleSize = caculateInSampleSize(bitmap.getWidth(), bitmap.getHeight(), 200);
            options.inJustDecodeBounds = false;
            bitmap = getRectBitmap(bitmap);
            bis.close();
            is.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap getRectBitmap(Bitmap bitmap)
    {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int centerx = width / 2;
        int centery = height / 2;
        if (width > height)
            bitmap = Bitmap.createBitmap(bitmap, centerx - height / 2, 0, height, height);
        else
            bitmap = Bitmap.createBitmap(bitmap, 0, centery - width / 2, width, width);
        return bitmap;
    }

    private static int caculateInSampleSize(int reWidth, int reHeight, int size)
    {
        int saleRate;
        int reSize;
        if (reWidth > size || reHeight > size)
        {
            reSize = reWidth > reHeight ? reWidth : reHeight;
            saleRate = reSize / size;
        } else
        {
            saleRate = 1;
        }
        return saleRate;
    }

    public static Bitmap getContentImageFromUrl(String imgLink)
    {
        Bitmap bitmap = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        try
        {
            URL url = new URL(imgLink);
            is = url.openConnection().getInputStream();
            bis = new BufferedInputStream(is);
            bitmap = BitmapFactory.decodeStream(bis);
            options.inSampleSize = caculateInSampleSize(bitmap.getWidth(), bitmap.getHeight(), 200);
            options.inJustDecodeBounds = false;
            //bitmap = getRectBitmap(bitmap);
            bis.close();
            is.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap getMatrixBitmap(Context context)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                android.R.mipmap.sym_def_app_icon);
        Matrix matrix = new Matrix();
        matrix.setRotate(90.f);
        Canvas canvas = new Canvas(bitmap);
        canvas.setMatrix(matrix);
        return bitmap;
    }
}
