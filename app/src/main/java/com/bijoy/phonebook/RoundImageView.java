package com.bijoy.phonebook;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

public class RoundImageView {

    public static Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }


    public static int[] imageAll = {R.drawable.nasir, R.drawable.parvaz, R.drawable.shanto, R.drawable.norus, R.drawable.mejanur, R.drawable.sonia, R.drawable.umme, R.drawable.adnan, R.drawable.nasir, R.drawable.rajib, R.drawable.sayed, R.drawable.alamin, R.drawable.porag, R.drawable.shahadat, R.drawable.msshakila, R.drawable.sultan, R.drawable.maidul, R.drawable.emon, R.drawable.ataur, R.drawable.syed};

}
