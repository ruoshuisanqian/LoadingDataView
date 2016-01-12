package com.tangram.loadingdataview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * loading animation view
 * Created by wanglx on 16/1/8.
 */
public class LoadingAnimationView extends View {

    private Paint mPaint;

    private AnimationControl mAnimationControl;
    private boolean mHasAnimation;

    public LoadingAnimationView(Context context) {
        super(context);
        init();
    }

    public LoadingAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!mHasAnimation){
            mHasAnimation=true;
            mAnimationControl.createAnimations();
        }
    }


    @Override
    public void setVisibility(int v) {
        if (getVisibility() != v) {
            super.setVisibility(v);
            if (v == GONE || v == INVISIBLE) {
                mAnimationControl.end();
            } else {
                mAnimationControl.start();
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mAnimationControl.cancel();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setColor(0xFFAAAAAA);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mAnimationControl = new AnimationControl(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mAnimationControl.draw(canvas, mPaint);
    }

    public class AnimationControl {
        private View mTarget;
        private List<Animator> mAnimators = new ArrayList<>();
        public static final float SCALE=1.0f;

        public static final int ALPHA=255;

        float[] scaleFloats=new float[]{SCALE,
                SCALE,
                SCALE,
                SCALE,
                SCALE,
                SCALE,
                SCALE,
                SCALE};

        int[] alphas=new int[]{ALPHA,
                ALPHA,
                ALPHA,
                ALPHA,
                ALPHA,
                ALPHA,
                ALPHA,
                ALPHA};

        public AnimationControl(View target) {
            mTarget = target;
        }

        public void draw(Canvas canvas, Paint paint){
            float radius=getWidth()/10;
            for (int i = 0; i < 8; i++) {
                canvas.save();
                Point point=circleAt(getWidth(),getHeight(),getWidth()/2.5f-radius,i*(Math.PI/4));
                canvas.translate(point.x, point.y);
                canvas.scale(scaleFloats[i], scaleFloats[i]);
                canvas.rotate(i * 45);
                paint.setAlpha(alphas[i]);
                RectF rectF=new RectF(-radius,-radius/1.5f,1.5f*radius,radius/1.5f);
                canvas.drawRoundRect(rectF, 5, 5, paint);
                canvas.restore();
            }
        }

        Point circleAt(int width,int height,float radius,double angle){
            float x= (float) (width/2+radius*(Math.cos(angle)));
            float y= (float) (height/2+radius*(Math.sin(angle)));
            return new Point(x, y);
        }

        public void createAnimations() {
            end();
            mAnimators.clear();
            int[] delays = {0, 120, 240, 360, 480, 600, 720, 780, 840};
            for (int i = 0; i < 8; i++) {
                final int index = i;
                ValueAnimator scaleAnim = ValueAnimator.ofFloat(1, 0.4f, 1);
                scaleAnim.setDuration(1000);
                scaleAnim.setRepeatCount(-1);
                scaleAnim.setStartDelay(delays[i]);
                scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        scaleFloats[index] = (float) animation.getAnimatedValue();
                        mTarget.postInvalidate();
                    }
                });
                scaleAnim.start();

                ValueAnimator alphaAnim = ValueAnimator.ofInt(255, 77, 255);
                alphaAnim.setDuration(1000);
                alphaAnim.setRepeatCount(-1);
                alphaAnim.setStartDelay(delays[i]);
                alphaAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        alphas[index] = (int) animation.getAnimatedValue();
                        mTarget.postInvalidate();
                    }
                });
                alphaAnim.start();
                mAnimators.add(scaleAnim);
                mAnimators.add(alphaAnim);
            }
        }

        public void end(){
            for (Animator animator : mAnimators ){
                if(animator.isRunning()){
                    animator.end();
                }
            }
        }

        public void start(){
            for (Animator animator : mAnimators ){
                if(!animator.isRunning()){
                    animator.start();
                }
            }
        }

        public void cancel(){
            for (Animator animator : mAnimators ){
                if(animator.isRunning()){
                    animator.cancel();
                }
            }
        }

    }

    final class Point{
        public float x;
        public float y;

        public Point(float x, float y){
            this.x=x;
            this.y=y;
        }
    }
}
