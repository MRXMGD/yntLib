/*
 *
 *
 *    Copyright (c) 2018. mrxmgd.com
 *
 *     @author MRXMGD <mrxmgd@gmail.com>
 *
 *
 */

package com.mrxmgd.baselib.recyclerview.divider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * 在自定义recyclerview分割线，linear，grid通用
 *
 * @author MRXMGD <mrxmgd@gmail.com>
 */
public class RecycleViewDivider extends RecyclerView.ItemDecoration {
    public static final int ORIENTATION_VERTICAL = 1; //竖直方向列表（水平分割线）
    public static final int ORIENTATION_HORIZONTAL = 2;//水平方向的列表（竖直分割线）
    public static final int ORIENTATION_BOTH = 3;//水平和竖直都有分割线(GRID)
    private Paint mPaint;
    private Drawable mDivider;
    private int mDividerHeight = 1;//分割线高度，默认为1px
    private int mOrientation;//列表的方向
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    /**
     * 默认分割线：高度为2px，颜色为灰色
     *
     * @param context
     * @param orientation 列表方向
     */
    public RecycleViewDivider(Context context, int orientation) {
        mOrientation = orientation;
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    /**
     * 自定义分割线
     *
     * @param context
     * @param orientation 列表方向
     * @param drawableId  分割线图片
     */
    public RecycleViewDivider(Context context, int orientation, int drawableId) {
        this(context, orientation);
        mDivider = ContextCompat.getDrawable(context, drawableId);
        mDividerHeight = mDivider.getIntrinsicHeight();
    }

    /**
     * 自定义分割线
     *
     * @param context
     * @param orientation   列表方向
     * @param dividerHeight 分割线高度
     * @param dividerColor  分割线颜色
     */
    public RecycleViewDivider(Context context, int orientation, int dividerHeight, int dividerColor) {
        this(context, orientation);
        mDividerHeight = dividerHeight;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(dividerColor);
        mPaint.setStyle(Paint.Style.FILL);
    }


    //获取分割线尺寸
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, mDividerHeight);
    }

    //绘制分割线
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation == ORIENTATION_VERTICAL) {
            drawHorizontal(c, parent);
        } else if (mOrientation == ORIENTATION_HORIZONTAL) {
            drawVertical(c, parent);
        } else {
            drawVertical(c, parent);
            drawHorizontal(c, parent);
        }
    }

    //绘制横向 item 分割线
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        int childSize = parent.getChildCount();
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        // 去掉最后一行的分割线
        //如果是gridlayout
        if (layoutManager instanceof GridLayoutManager) {
            //列数
            int spanCount = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
            //如果刚刚是整列的话，少返回一列
            if (childSize % spanCount == 0) {
                childSize = childSize - spanCount;
            } else {//如果不是整列，少返回最后那一列的几个
                childSize = childSize - childSize % spanCount;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            //列数
            int spanCount = ((StaggeredGridLayoutManager) parent.getLayoutManager()).getSpanCount();
            //如果刚刚是整列的话，少返回一列
            if (childSize % spanCount == 0) {
                childSize = childSize - spanCount;
            } else {//如果不是整列，少返回最后那一列的几个
                childSize = childSize - childSize % spanCount;
            }
        } else {
            //直接不返回最后一项（如果需要最后一个分割线，还不如直接写到item里面）
            childSize--;
        }
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getLeft() - layoutParams.leftMargin;
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int right = child.getRight() + layoutParams.rightMargin
                    + mDividerHeight;
            final int bottom = top + mDividerHeight;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    //绘制纵向 item 分割线
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getTop() - layoutParams.topMargin;
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + mDividerHeight;
            final int bottom = child.getBottom() + layoutParams.bottomMargin;

            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

}