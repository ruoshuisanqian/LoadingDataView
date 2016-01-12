package com.tangram.loadingdataview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * loading data base layout
 * Created by wanglx on 16/1/5.
 */
public class LoadingDataBaseLayout extends RelativeLayout implements OnClickListener{
    private ReloadDataListener mReloadDataListener;
    private View mLoading;
    private View mError;
    private View mEmpty;


    public LoadingDataBaseLayout(Context context) {
        super(context);
        initLayout();
    }

    public LoadingDataBaseLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout();
    }

    public LoadingDataBaseLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    public void setReloadDataListener(ReloadDataListener listener){
        mReloadDataListener = listener;
    }

    private void initLayout(){
        mLoading  = getLoadingLayout();
        mError = getErrorLayout();
        mEmpty = getEmptyLayout();
        mError.setOnClickListener(this);
        addView(mLoading);
        addView(mError);
        addView(mEmpty);
    }

    public void loading(){
        mError.setVisibility(GONE);
        mEmpty.setVisibility(GONE);
        mLoading.setVisibility(VISIBLE);
        mLoading.bringToFront();
    }

    public void error(){
        mError.setVisibility(VISIBLE);
        mLoading.setVisibility(GONE);
        mEmpty.setVisibility(GONE);
        mError.bringToFront();
    }

    public void empty(){
        mEmpty.setVisibility(VISIBLE);
        mLoading.setVisibility(GONE);
        mError.setVisibility(GONE);
        mEmpty.bringToFront();
    }

    public void finish(){
        mError.setVisibility(GONE);
        mLoading.setVisibility(GONE);
        mEmpty.setVisibility(GONE);
    }

    @Override
    public void onClick(View v) {
        if(mReloadDataListener != null){
            loading();
            mReloadDataListener.reloadData();
        }
    }

    /**
     * 加载页 view
     * @return
     */
    public  View getLoadingLayout(){
        // loading layout
        RelativeLayout defaultLoadingLayout = new RelativeLayout(getContext());
        defaultLoadingLayout.setBackgroundColor(Color.WHITE);
        defaultLoadingLayout.setVisibility(GONE);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        defaultLoadingLayout.setLayoutParams(params);
        defaultLoadingLayout.setGravity(Gravity.CENTER);

        //loading animation
        LoadingAnimationView loadingAnimationView = new LoadingAnimationView(getContext());
        loadingAnimationView.setId(generateViewId());
        RelativeLayout.LayoutParams loadingAnimParams = new RelativeLayout.LayoutParams(dp2px(32), dp2px(32));
        loadingAnimParams.addRule(RelativeLayout.CENTER_VERTICAL);
        loadingAnimationView.setLayoutParams(loadingAnimParams);
        defaultLoadingLayout.addView(loadingAnimationView);

        //loading text view
        TextView loadingText = new TextView(getContext());
        loadingText.setId(generateViewId());
        loadingText.setText("加载中...");
        RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.addRule(RelativeLayout.RIGHT_OF, loadingAnimationView.getId());
        textParams.addRule(RelativeLayout.CENTER_VERTICAL);
        textParams.leftMargin = dp2px(5);
        loadingText.setGravity(Gravity.CENTER);
        loadingText.setLayoutParams(textParams);
        defaultLoadingLayout.addView(loadingText);
        return defaultLoadingLayout;
    }

    /**
     * 错误页view
     * @return
     */
    public  View getErrorLayout(){
        RelativeLayout defaultErrorLayout = new RelativeLayout(getContext());
        defaultErrorLayout.setBackgroundColor(Color.WHITE);
        defaultErrorLayout.setGravity(Gravity.CENTER);

        TextView textView = new TextView(getContext());
        textView.setText("网络错误，请重试！");
        defaultErrorLayout.addView(textView);
        defaultErrorLayout.setVisibility(GONE);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        defaultErrorLayout.setLayoutParams(params);
        return defaultErrorLayout;
    }

    /**
     * 空数据 view
     * @return
     */
    public View getEmptyLayout(){
        RelativeLayout defaultEmptyLayout = new RelativeLayout(getContext());
        defaultEmptyLayout.setBackgroundColor(Color.WHITE);
        defaultEmptyLayout.setGravity(Gravity.CENTER);

        TextView textView = new TextView(getContext());
        textView.setText("没有数据");
        defaultEmptyLayout.addView(textView);
        defaultEmptyLayout.setVisibility(GONE);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        defaultEmptyLayout.setLayoutParams(params);
        return defaultEmptyLayout;
    }

    public interface ReloadDataListener{
        void reloadData();
    }

    private int dp2px(int dpValue) {
        return (int) getContext().getResources().getDisplayMetrics().density * dpValue;
    }
}
