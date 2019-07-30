/*
 *
 *
 *    Copyright (c) 2018. mrxmgd.com
 *
 *     @author MRXMGD <mrxmgd@gmail.com>
 *
 *
 */

package com.mrxmgd.baselib.util;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

/**
 * 文字工具类
 *
 * @author MRXMGD <mrxmgd@gmail.com>
 */
public class TextUtils {
    /**
     * 设置字体样式（默认主色）
     *
     * @param text
     * @return
     */
    public static SpannableString setTextStyle(String text) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3356")), 0,
                spannableString.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 设置字体颜色大小
     *
     * @param text
     * @param color        颜色
     * @param relativeSize 相对大小
     * @return
     */
    public static SpannableString setTextStyle(String text, int color, float relativeSize) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ForegroundColorSpan(color), 0,
                spannableString.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(relativeSize), 0, spannableString.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 设置字体大小
     *
     * @param text
     * @param relativeSize 相对大小
     * @return
     */
    public static SpannableString setTextStyle(String text, float relativeSize) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new RelativeSizeSpan(relativeSize), 0, spannableString.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 设置字体颜色
     *
     * @param text
     * @param color
     * @return
     */
    public static SpannableString setTextStyle(String text, int color) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ForegroundColorSpan(color), 0,
                spannableString.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 根据文本获取字符宽度
     *
     * @param context
     * @param text
     * @param textSize 字体大小SP
     * @return
     */
    public static float getTextWidth(Context context, String text, int textSize) {
        TextPaint paint = new TextPaint();
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        paint.setTextSize(scaledDensity * textSize);
        return paint.measureText(text);
    }
}
