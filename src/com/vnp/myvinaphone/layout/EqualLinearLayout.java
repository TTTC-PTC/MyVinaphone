package com.vnp.myvinaphone.layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;

/*
 * Custom layout that arranges children in a grid-like manner, optimizing for even horizontal and
 * vertical whitespace.
 */
@SuppressLint("NewApi")
public class EqualLinearLayout extends LinearLayout {
	private int mMaxChildWidth = 0;
	private int mMaxChildHeight = 0;
	private static final int UNEVEN_GRID_PENALTY_MULTIPLIER = 10;

	public EqualLinearLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public EqualLinearLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		mMaxChildWidth = 0;
		mMaxChildHeight = 0;

		// Measure once to find the maximum child size.

		int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
				MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.AT_MOST);
		int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
				MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.AT_MOST);

		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			if (child.getVisibility() == GONE) {
				continue;
			}

			child.measure(childWidthMeasureSpec, childHeightMeasureSpec);

			mMaxChildWidth = Math.max(mMaxChildWidth, child.getMeasuredWidth());
			mMaxChildHeight = Math.max(mMaxChildHeight,
					child.getMeasuredHeight());
		}

		// Measure again for each child to be exactly the same size.

		childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxChildWidth,
				MeasureSpec.EXACTLY);
		childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxChildHeight,
				MeasureSpec.EXACTLY);

		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			if (child.getVisibility() == GONE) {
				continue;
			}

			child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
		}

		setMeasuredDimension(resolveSize(mMaxChildWidth, widthMeasureSpec),
				resolveSize(mMaxChildHeight, heightMeasureSpec));
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int width = r - l;
		int height = b - t;
		
		final int count = getChildCount();

		// Calculate the number of visible children.
		int visibleCount = 0;
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			if (child.getVisibility() == GONE) {
				continue;
			}
			++visibleCount;
		}

		if (visibleCount == 0) {
			return;
		}

		// Calculate what number of rows and columns will optimize for even
		// horizontal and
		// vertical whitespace between items. Start with a 1 x N grid, then try
		// 2 x N, and so on.
		
		// Horizontal and vertical space between items
		
		int childWidth;
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			if (child.getVisibility() == GONE) {
				continue;
			}

			childWidth = width/4 * i;

			child.layout(childWidth, 0, childWidth + width/4, height);

		}
	}

}
