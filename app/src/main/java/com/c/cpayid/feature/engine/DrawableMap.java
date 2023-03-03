package com.c.cpayid.feature.engine;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

/**
 * DICKY <akbar.attijani@gmail.com>
 */

public class DrawableMap {
    
    public static Drawable getApplicationIcon(Context context) {
        String drawableName = ApplicationVariable.applicationId;
        drawableName = drawableName.replaceAll("\\.", "_");

        return ContextCompat.getDrawable(context, context.getResources().getIdentifier(drawableName, "drawable", "com.c.cpayid.feature"));
    }

    public static int getApplicationIconInId(Context context, String applicationId) {
        return context.getResources().getIdentifier(applicationId.replaceAll("\\.", "_"), "drawable", "com.c.cpayid.feature");
    }

    public static Drawable changeColor(Drawable drawable, String color) {
        GradientDrawable bgDrawable = (GradientDrawable) drawable;
        bgDrawable.setColor(ColorMap.getColor(ApplicationVariable.applicationId, color));

        return bgDrawable;
    }

    public static Drawable changeColorStroke(Drawable drawable, String color) {
        GradientDrawable bgDrawable = (GradientDrawable) drawable;
        bgDrawable.setStroke(2, ColorMap.getColor(ApplicationVariable.applicationId, color));

        return bgDrawable;
    }

    public static Drawable changeColorVector(Drawable drawable, String color) {
//        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, ColorMap.getColor(ApplicationVariable.applicationId, color));
        return drawable;
    }
}
