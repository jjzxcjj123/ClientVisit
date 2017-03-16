package com.siti.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.graphics.Path;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.siti.clientvisit.R;


public class WaterWaveView extends View {
	/** 默认值 */
	private static final int DEFAULT_WAVE_LENGTH = 100;//波长
	private static final int DEFAULT_WAVE_COLOR = Color.BLUE;//水波颜色
	private static final int DEFAULT_AMPLITUDE = 30;//振幅
	private static final int DEFAULT_DURATION_ANIMATION = 1000;//一次动画的时间

	/** 水的画笔 */
	private Paint mPaint = null;
	/** 水路径 */
	private Path mPath = null;
	/** 水位 */
	private float mWaterLevel;
	/** 振幅 */
	private float mAmplitude;
	/** 水的颜色 */
	private int mWaterColor;
	/** 波长 */
	private float mWaveLength;
	/** 一次水波动画时间 */
	private int durationAnimation;

	/** 控件宽高 */
	private int width;
	private int height;

	/** 动画 */
	private ValueAnimator mAnimator;


	public WaterWaveView(Context context, AttributeSet attrs) {
		super(context, attrs);
		//初始画笔
		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.FILL);
		mPath = new Path();
		//初始化数据
		initData(context, attrs);
		//初始化动画
		initAnimator();
		mAnimator.start();
	}



	/**
	 * 初始化数据
	 * @param context
	 * @param attrs
     */
	private void initData(Context context, AttributeSet attrs) {
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WaterWaveView);
		mWaveLength = ta.getDimensionPixelSize(R.styleable.WaterWaveView_waveLength, DEFAULT_WAVE_LENGTH);
		mAmplitude = ta.getDimension(R.styleable.WaterWaveView_amplitude, DEFAULT_AMPLITUDE);
		mWaterColor = ta.getColor(R.styleable.WaterWaveView_waterColor, DEFAULT_WAVE_COLOR);
		durationAnimation = ta.getInteger(R.styleable.WaterWaveView_duration, DEFAULT_DURATION_ANIMATION);
		ta.recycle();

		mPaint.setColor(mWaterColor);

	}

	/**
	 * 初始化动画
	 */
	private void initAnimator() {
		mAnimator = ValueAnimator.ofFloat(0, 1);
		mAnimator.setDuration(durationAnimation);
		mAnimator.setRepeatCount(ValueAnimator.INFINITE);
		mAnimator.setInterpolator(new LinearInterpolator());
		mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				float percent = (float) valueAnimator.getAnimatedValue();
				updateWavePath(percent);
				invalidate();
			}
		});
	}

	@Override
	protected Parcelable onSaveInstanceState() {

		return super.onSaveInstanceState();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawPath(mPath, mPaint);
	}

	/**
	 * 根据百分比更新path用于绘制水波
	 * @param percent
     */
	private void updateWavePath(float percent) {
		mPath.reset();
		float halfWaveLength = mWaveLength / 2;
		float dx = mWaveLength * percent;
		mPath.moveTo(-mWaveLength + dx, height - mWaterLevel);
		for (int i = (int)-mWaveLength; i < width + mWaveLength; i += mWaveLength) {
			mPath.rQuadTo(halfWaveLength / 2, -mAmplitude, halfWaveLength, 0);
			mPath.rQuadTo(halfWaveLength / 2, mAmplitude, halfWaveLength, 0);
		}
		mPath.lineTo(width, height);
		mPath.lineTo(0, height);
		mPath.close();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		width = w;
		height = h;
		mWaterLevel = h / 2;
	}
}