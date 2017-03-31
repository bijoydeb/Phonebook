package com.bijoy.phonebook;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class TimesTextView extends TextView {

    public static Typeface m_typeFace = null;

    public TimesTextView(Context context) {
        super(context);
        if (isInEditMode()) {
            return;
        }
        loadTypeFace(context);
    }

    public TimesTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) {
            return;
        }
        loadTypeFace(context);
    }

    public TimesTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (isInEditMode()) {
            return;
        }
        loadTypeFace(context);
    }

    private void loadTypeFace(Context context) {
        if (m_typeFace == null)
            m_typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/times.ttf");
        this.setTypeface(m_typeFace);
    }
}
