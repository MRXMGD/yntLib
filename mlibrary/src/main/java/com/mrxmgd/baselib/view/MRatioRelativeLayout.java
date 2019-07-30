/*
 *
 *
 *    Copyright (c) 2018. mrxmgd.com
 *
 *     @author MRXMGD <mrxmgd@gmail.com>
 *
 *
 */

package com.mrxmgd.baselib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.mrxmgd.baselib.R;

/**
 * 高度/宽度 =1/1
 *
 * @author MRXMGD <mrxmgd@gmail.com>
 */
public class MRatioRelativeLayout extends RelativeLayout {
	private int heightRatio = 1;
	private int widthRatio = 1;
	private float ratio = 1f;

	public MRatioRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MRatioRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MRatioRelativeLayout);
		heightRatio = typedArray.getInteger(R.styleable.MRatioRelativeLayout_heightRatio, 1);
		widthRatio = typedArray.getInteger(R.styleable.MRatioRelativeLayout_widthRatio, 1);
		ratio = (float) heightRatio / widthRatio;
		typedArray.recycle();
	}

	public MRatioRelativeLayout(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// For simple implementation, or internal size is always 0.
		// We depend on the container to specify the layout size of
		// our view. We can't really know what it is since we will be
		// adding and removing different arbitrary views and do not
		// want the layout to change as this happens.
		setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
				getDefaultSize(0, heightMeasureSpec));
		// Children are just made to fill our space.
		int childWidthSize = getMeasuredWidth();
		int childHeightSize = getMeasuredHeight();
		widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize,
				MeasureSpec.EXACTLY);
		heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (childWidthSize * ratio),
				MeasureSpec.EXACTLY);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}