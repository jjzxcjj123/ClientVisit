package com.siti.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.siti.clientvisit.R;


/**
 * Created by chenji on 17/3/9.
 *
 * 水波纹特效,中心默认添加文字
 */
public class CircleWaveView extends View {
    private static final int DEFAULT_WAVE_COUNT = 4;//默认波纹数
    private static final float CENTER_CIRCLE_RADIO = 0.6f;//中心圆所占比例

    private int maxWaveRadius;//最大半径
    private int centerCircleRadius;//中心圆半径
    private int waveCount;//波纹数
    private int waveColor;//波纹颜色
    private int textColor;//文字颜色
    private int centerX;//X中心点
    private int centerY;//Y中心点
    private float[] radioList;//从内层到外层各圆比例

    private static final int ANIMATION_DURATION = 3000;//一次动画持续时间
    private ValueAnimator animator;//动画

    private Paint paint;//画笔
    private TextPaint textPaint;//字画笔

    public CircleWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        initData(context, attrs);
        initAnimator();
        //开始动画
        animator.start();
    }

    /**
     * 初始化数据
     * @param context
     * @param attrs
     */
    private void initData(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.CircleWaveView);
        waveCount = ta.getInteger(R.styleable.CircleWaveView_waveCount, DEFAULT_WAVE_COUNT);
        textColor = ta.getColor(R.styleable.CircleWaveView_textColor, Color.WHITE);
        waveColor = ta.getColor(R.styleable.CircleWaveView_waveColor, Color.BLUE);
        ta.recycle();

        radioList = new float[waveCount];
        paint.setColor(waveColor);
        textPaint.setColor(textColor);
        textPaint.setTextSize(centerCircleRadius / 1.4f * 0.8f);
    }

    /**
     * 初始化动画
     */
    private void initAnimator() {
        animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setDuration(ANIMATION_DURATION);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float percent = (float) valueAnimator.getAnimatedValue();

                for (int i = 0; i < radioList.length; i++) {
                    float v = percent - i * 1.0f / waveCount;
                    if(v < 0 && radioList[i] > 0) {
                        v += 1;
                    }
                    radioList[i] = v;
                }
                invalidate();
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
        maxWaveRadius = w > h ? h / 2 : w / 2;
        centerCircleRadius = (int) (w > h ? h * CENTER_CIRCLE_RADIO / 2: w * CENTER_CIRCLE_RADIO / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画波纹
        for (int i = 0; i < radioList.length; i++) {
            int alpha = (int) (255.0f * (1 - radioList[i]));
            paint.setAlpha(alpha);
            canvas.drawCircle(centerX, centerY, centerCircleRadius + radioList[i] * (maxWaveRadius - centerCircleRadius), paint);
        }
        //画中心圆
        paint.setAlpha(255);
        canvas.drawCircle(centerX, centerY, centerCircleRadius, paint);

        //写字
        drawText(new String[]{"客","户","拜","访"}, canvas);
    }

    //写字,暂时只做了4个字的排版
    private void drawText(String[] content, Canvas canvas) {
        if(content.length == 4) {
            int x = (int) (centerCircleRadius / 1.4 / 2);
            textPaint.setTextSize((float) (x * 1.6));
            drawTextByCenterPosition(content[0], canvas, centerX - x, centerY - x, textPaint);
            drawTextByCenterPosition(content[1], canvas, centerX + x, centerY - x, textPaint);
            drawTextByCenterPosition(content[2], canvas, centerX - x, centerY + x, textPaint);
            drawTextByCenterPosition(content[3], canvas, centerX + x, centerY + x, textPaint);
        }
    }

    private void drawTextByCenterPosition(String content, Canvas canvas, int centerX, int centerY, Paint p) {
        Rect bounds = new Rect();
        p.getTextBounds(content,0, content.length(),bounds);
        canvas.drawText(content, centerX - bounds.width() / 2 , centerY + bounds.height() / 2, p);
    }


}
