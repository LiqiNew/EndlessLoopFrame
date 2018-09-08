package com.liqi.endlessloop;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.liqi.endlessloop.adapter.ViewPagerImageViewAdapter;
import com.liqi.endlessloop.interfa.OnCycleViewPagerListener;
import com.liqi.endlessloop.interfa.OnImageCycleViewListener;
import com.liqi.endlessloop.interfa.OnViewPagerDataListener;

import java.util.List;

/**
 * 实现可循环，可轮播的viewpager
 *
 * @author Liqi
 */
public class CycleViewPager extends Fragment implements OnPageChangeListener, OnViewPagerDataListener, OnCycleViewPagerListener {

    private final int WHEEL = 100,// 转动
            WHEEL_WAIT = 101; // 等待
    private List<ImageView> mImageViews;
    private ImageView[] mIndicators;
    private FrameLayout mViewPagerFragmentLayout;
    private LinearLayout mIndicatorLayout; // 指示器
    private BaseViewPager mViewPager;
    private BaseViewPager mParentViewPager;
    private ViewPagerImageViewAdapter mAdapter;
    private Handler mHandler;
    private int mTime = 5000; // 默认轮播时间
    private int mCurrentPosition = 0; // 轮播当前位置
    private boolean mIsScrolling = false; // 滚动框是否滚动着
    private boolean mIsCycle = false; // 是否循环
    private boolean mIsWheel = false; // 是否轮播
    private long mReleaseTime = 0; // 手指松开、页面不滚动时间，防止手机松开后短时间进行切换
    private final Runnable RUNNABLE = new Runnable() {

        @Override
        public void run() {
            if (getActivity() != null && !getActivity().isFinishing()
                    && mIsWheel) {
                long now = System.currentTimeMillis();
                // 检测上一次滑动时间与本次之间是否有触击(手滑动)操作，有的话等待下次轮播
                if (now - mReleaseTime > mTime - 500) {
                    mHandler.sendEmptyMessage(WHEEL);
                } else {
                    mHandler.sendEmptyMessage(WHEEL_WAIT);
                }
            }
        }
    };
    private OnPageChangeListener mOnPageChangeListener;
    //指示器选择图片和默认图片
    private
    @DrawableRes
    int mSelectedIndicatorId = R.drawable.icon_point_pre,
            mDefaultIndicatorId = R.drawable.icon_point;

    @Override
    public OnCycleViewPagerListener setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(
                R.layout.view_cycle_viewpager_contet, null);

        mViewPager = (BaseViewPager) view.findViewById(R.id.viewPager);
        mIndicatorLayout = (LinearLayout) view
                .findViewById(R.id.layout_viewpager_indicator);

        mViewPagerFragmentLayout = (FrameLayout) view
                .findViewById(R.id.layout_viewager_content);

        mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (null != mImageViews) {
                    if (msg.what == WHEEL && mImageViews.size() != 0) {
                        if (!mIsScrolling) {
                            int max = mImageViews.size() + 1;
                            int position = (mCurrentPosition + 1)
                                    % mImageViews.size();
                            mViewPager.setCurrentItem(position, true);
                            if (position == max) { // 最后一页时回到第一页
                                mViewPager.setCurrentItem(1, false);
                            }
                        }

                        mReleaseTime = System.currentTimeMillis();
                        mHandler.removeCallbacks(RUNNABLE);
                        mHandler.postDelayed(RUNNABLE, mTime);
                        return;
                    }
                    if (msg.what == WHEEL_WAIT && mImageViews.size() != 0) {
                        mHandler.removeCallbacks(RUNNABLE);
                        mHandler.postDelayed(RUNNABLE, mTime);
                    }
                }
            }
        };

        return view;
    }

    /**
     * 设置指示器底部居中。默认指示器在右方
     */
    @Override
    public OnCycleViewPagerListener setIndicatorBottomCenter() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mIndicatorLayout.setLayoutParams(params);
        return this;
    }

    /**
     * 设置指示器顶部居中。默认指示器在右方
     */
    @Override
    public OnCycleViewPagerListener setIndicatorTopCenter() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mIndicatorLayout.setLayoutParams(params);
        return this;
    }

    /**
     * 设置指示器对齐方式。默认指示器在右方
     *
     * @param relativeAlignOne 对齐位置方式1。使用RelativeLayout的对齐方式去设置“上下左右居中”对齐方式
     * @param relativeAlignTwo 对齐位置方式2。使用RelativeLayout的对齐方式去设置“上下左右居中”对齐方式
     * @return OnCycleViewPagerListener
     */
    @Override
    public OnCycleViewPagerListener setIndicatorAlignManner(int relativeAlignOne, int relativeAlignTwo) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(relativeAlignOne);
        params.addRule(relativeAlignTwo);
        mIndicatorLayout.setLayoutParams(params);
        return this;
    }

    /**
     * 是否处于循环状态
     *
     * @return
     */
    @Override
    public boolean isCycle() {
        return mIsCycle;
    }

    /**
     * 是否循环，默认不开启，开启前，请将views的最前面与最后面各加入一个视图，用于循环
     *
     * @param isCycle 是否循环
     */
    @Override
    public OnCycleViewPagerListener setCycle(boolean isCycle) {
        this.mIsCycle = isCycle;
        return this;
    }

    /**
     * 是否处于轮播状态
     *
     * @return
     */
    @Override
    public boolean isWheel() {
        return mIsWheel;
    }

    /**
     * 释放指示器高度，可能由于之前指示器被限制了高度，此处释放
     */
//    public void releaseHeight() {
//        getView().getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
//       // refreshData();
//    }

    /**
     * 设置轮播暂停时间，即没多少秒切换到下一张视图.默认5000ms
     *
     * @param time 秒为单位
     */
    @Override
    public OnCycleViewPagerListener setTime(int time) {
        time = time < 0 ? 0 : time;
        this.mTime = time * 1000;
        return this;
    }

    /**
     * 刷新数据，当外部视图更新后，通知刷新数据
     */
//    public void refreshData() {
//        if (adapter != null)
//            adapter.notifyDataSetChanged();
//    }

    /**
     * 隐藏CycleViewPager
     */
    @Override
    public OnCycleViewPagerListener hide() {
        mViewPagerFragmentLayout.setVisibility(View.GONE);
        return this;
    }

    /**
     * 返回内置的viewpager
     *
     * @return viewPager
     */
    @Override
    public BaseViewPager getViewPager() {
        return mViewPager;
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        if (arg0 == 1) { // viewPager在滚动
            mIsScrolling = true;
            //滑动时阻止外层viewpager滑动
            if (null != mParentViewPager) {
                mParentViewPager.setScrollable(false);
            }
            return;
        } else if (arg0 == 0) { // viewPager滚动结束
            //滑动结束时恢复外层viewpager滑动
            if (mParentViewPager != null) {
                mParentViewPager.setScrollable(true);
            }
            mReleaseTime = System.currentTimeMillis();

            mViewPager.setCurrentItem(mCurrentPosition, false);

        }
        mIsScrolling = false;
        if (null != mOnPageChangeListener) {
            mOnPageChangeListener.onPageScrollStateChanged(arg0);
        }
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        if (null != mOnPageChangeListener) {
            mOnPageChangeListener.onPageScrolled(arg0, arg1, arg2);
        }
    }

    @Override
    public void onPageSelected(int arg0) {
        if (null != mImageViews) {
            int max = mImageViews.size() - 1;
            int position = arg0;
            mCurrentPosition = arg0;
            if (mIsCycle) {
                if (arg0 == 0) {
                    mCurrentPosition = max - 1;
                } else if (arg0 == max) {
                    mCurrentPosition = 1;
                }
                position = mCurrentPosition - 1;
            }
            setIndicator(position);
            if (null != mOnPageChangeListener) {
                mOnPageChangeListener.onPageSelected(arg0);
            }
        }
    }

    /**
     * 设置viewpager是否可以滚动
     *
     * @param enable
     */
    @Override
    public OnCycleViewPagerListener setScrollable(boolean enable) {
        mViewPager.setScrollable(enable);
        return this;
    }

    /**
     * 返回当前位置,循环时需要注意返回的position包含之前在views最前方与最后方加入的视图，即当前页面试图在views集合的位置
     *
     * @return
     */
    @Override
    public int getCurrentPostion() {
        return mCurrentPosition;
    }

    /**
     * 设置指示器图片
     *
     * @param selectedIndicatorId 指示器选择图片
     * @param defaultIndicatorId  指示器默认图片
     */
    @Override
    public OnCycleViewPagerListener setIndicatorImageId(@DrawableRes int selectedIndicatorId, @DrawableRes int defaultIndicatorId) {
        mSelectedIndicatorId = selectedIndicatorId;
        mDefaultIndicatorId = defaultIndicatorId;
        return this;
    }

    /**
     * 设置指示器
     *
     * @param selectedPosition 默认指示器位置
     */
    private void setIndicator(int selectedPosition) {
        for (int i = 0; i < mIndicators.length; i++) {
            mIndicators[i].setBackgroundResource(mDefaultIndicatorId);
        }
        if (mIndicators.length > selectedPosition) {
            mIndicators[selectedPosition]
                    .setBackgroundResource(mSelectedIndicatorId);
        }
    }

    /**
     * 如果当前页面嵌套在另一个viewPager中，为了在进行滚动时阻断父ViewPager滚动，可以 阻止父ViewPager滑动事件
     * 父ViewPager需要实现BaseViewPager中的setScrollable方法
     */
    @Override
    public <V extends BaseViewPager> OnCycleViewPagerListener disableOutsideViewPagerTouchEvent(V parentViewPager) {
        this.mParentViewPager = parentViewPager;
        return this;
    }

    /**
     * 设置viewpager过度动画渐出渐入动画
     */
    @Override
    public OnCycleViewPagerListener viewPagerGradientDynamic() {
        if (null != mViewPager) {
            //设置viewpager切换动画：参考资料：http://www.jianshu.com/p/f689e499b3eb
            mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
                @Override
                public void transformPage(View page, float position) {
                    float normalizedposition = Math.abs(Math.abs(position) - 1);
                    page.setAlpha(normalizedposition);
                }
            });
        }
        return this;
    }

    /**
     * viewpager切换速度时间设置
     *
     * @param duration 切换过度时间 单位：秒
     */
    @Override
    public OnCycleViewPagerListener viewPagerSwitchingSpeed(int duration) {
        if (null != mViewPager) {
            //设置viewPager切换过度时间的类
            ViewPagerScroller scroller = new ViewPagerScroller(getContext());
            scroller.setScrollDuration(duration);
            scroller.initViewPagerScroll(mViewPager);  //这个是设置切换过渡时间
        }
        return this;
    }

    @Override
    public OnCycleViewPagerListener leftRightDisplayOffset(int left, int right) {
        //设置每页之间的左右间隔
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mViewPager.getLayoutParams();
        layoutParams.setMargins(left, 0, right, 0);
        mViewPager.setLayoutParams(layoutParams);
        return this;
    }

    @Override
    public OnCycleViewPagerListener setBackgroundColorId(@ColorRes int colorId) {
        if (null != mViewPagerFragmentLayout) {
            mViewPagerFragmentLayout.setBackgroundResource(colorId);
        }
        return this;
    }

    /**
     * 开启轮播，默认不轮播,轮播一定是循环的
     */
    @Override
    public OnViewPagerDataListener openWheel() {
        mIsWheel = true;
//        // 初始化的时候有开启循环才执行。
//        if (!mIsCycle) {
//            // 将最后一个ImageView添加进来
//            mImageViews.add(0, mImageViews.get(mImageViews.size() - 1));
//            // 将第一个ImageView添加进来
//            mImageViews.add(mImageViews.get(0));
//            mAdapter.notifyDataSetChanged();
//        }
        mIsCycle = true;
        mHandler.postDelayed(RUNNABLE, mTime);
        return this;
    }

    /**
     * 关闭轮播.并设置是否开启循环
     *
     * @param isCycle 是否开启循环
     */
    @Override
    public OnViewPagerDataListener closeWheel(boolean isCycle) {
        this.mIsWheel = false;
        this.mIsCycle = isCycle;
        return this;
    }

    /**
     * 开始装载数据
     *
     * @param views    装载数据imageView集合
     * @param listener 监听器
     */
    @Override
    public OnCycleViewPagerListener startLoadData(List<ImageView> views,
                                                  OnImageCycleViewListener listener) {
        return startLoadData(views, listener, 0);
    }

    /**
     * 开始装载数据
     *
     * @param imageViews   要显示的views
     * @param showPosition 默认显示位置
     */
    @Override
    public OnCycleViewPagerListener startLoadData(@NonNull List<ImageView> imageViews,
                                                  OnImageCycleViewListener listener, int showPosition) {
        if (null == imageViews || imageViews.isEmpty()) {
            mViewPagerFragmentLayout.setVisibility(View.GONE);
        } else {
            mViewPagerFragmentLayout.setVisibility(View.VISIBLE);
        }

        initIndicator(imageViews);

        mAdapter = new ViewPagerImageViewAdapter(imageViews);
        mAdapter.setOnImageCycleViewListener(listener);

        //viewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setAdapter(mAdapter);

        this.mImageViews = mAdapter.getImageViews();

        if (null != this.mImageViews && !this.mImageViews.isEmpty()) {
            if (showPosition < 0 || showPosition >= imageViews.size())
                showPosition = 0;
            if (mIsCycle) {
                showPosition = showPosition + 1;
            }
            mViewPager.setCurrentItem(showPosition);
            //传输进来的显示index值如果是当前已经显示的index值执行赋值指示器
            if (mCurrentPosition == showPosition) {
                setIndicator(mCurrentPosition > 0 ? mCurrentPosition - 1 : mCurrentPosition);
            }
        }
        return this;
    }

    private void initIndicator(List<ImageView> imageViews) {
        if (mIndicatorLayout.getChildCount() > 0) {
            mIndicatorLayout.removeAllViews();
        }

        if (null != imageViews && !imageViews.isEmpty()) {
            int ivSize = imageViews.size();
            //设置指示器设置指示器
            mIndicators = new ImageView[ivSize];
            // 初始化的时候有开启循环才执行。
            if (mIsCycle) {
                ImageView imageViewFirst = imageViews.get(ivSize - 1);
                ImageView imageViewLast = imageViews.get(0);
//                // 将最后一个ImageView添加进来
//                imageViews.add(0, imageViews.get(ivSize - 1));
//                // 将第一个ImageView添加进来
//                imageViews.add(imageView);
                // 将最后一个ImageView添加进来
                imageViews.add(0, getImageView(imageViewFirst.getDrawable()));
                // 将第一个ImageView添加进来
                imageViews.add(getImageView(imageViewLast.getDrawable()));
            }

            for (int i = 0; i < mIndicators.length; i++) {
                Activity activity = getActivity();
                if (null != activity) {
                    View view = LayoutInflater.from(activity).inflate(
                            R.layout.view_cycle_viewpager_indicator, null);
                    mIndicators[i] = (ImageView) view.findViewById(R.id.image_indicator);
                    mIndicatorLayout.addView(view);
                }
            }
            // 默认指向第一项，下方viewPager.setCurrentItem将触发重新计算指示器指向
            setIndicator(0);
        }
    }

    @Override
    public void initAdapterListRefresh(@NonNull List<ImageView> imageViews, int showPosition) {
        if (null == imageViews || imageViews.isEmpty()) {
            mViewPagerFragmentLayout.setVisibility(View.GONE);
        } else {
            mViewPagerFragmentLayout.setVisibility(View.VISIBLE);
        }
        //如果已经开启轮播
        if (mIsWheel) {
            mHandler.removeCallbacks(RUNNABLE);
            openWheel();
        }
        initIndicator(imageViews);
        mAdapter.initImageViews(imageViews);
        mAdapter.notifyDataSetChanged();
        this.mImageViews = mAdapter.getImageViews();

        if (null != this.mImageViews && !this.mImageViews.isEmpty()) {
            if (showPosition < 0 || showPosition >= imageViews.size())
                showPosition = 0;
            if (mIsCycle) {
                showPosition = showPosition + 1;
            }
            mViewPager.setCurrentItem(showPosition);
            //传输进来的显示index值如果是当前已经显示的index值执行赋值指示器
            if (mCurrentPosition == showPosition) {
                setIndicator(mCurrentPosition > 0 ? mCurrentPosition - 1 : mCurrentPosition);
            }
        }
    }

    @Override
    public void initAdapterListRefresh(@NonNull List<ImageView> imageViews) {
        initAdapterListRefresh(imageViews, 0);
    }

    @Override
    public void addAdapterListRefresh(List<ImageView> imageViews) {
        if (null != imageViews && !imageViews.isEmpty()) {
            //如果已经开启轮播
            if (mIsCycle) {
                List<ImageView> views = mAdapter.getImageViews();
                views.remove(0);
                views.remove(views.size() - 1);
            }

            mAdapter.addImageViews(imageViews);
            initIndicatorState();

            // 初始化的时候有开启循环才执行。
            if (mIsCycle) {
                List<ImageView> views = mAdapter.getImageViews();
                ImageView imageViewFirst = views.get(views.size() - 1);
                ImageView imageViewLast = views.get(0);
//                // 将最后一个ImageView添加进来
//                imageViews.add(0, imageViews.get(ivSize - 1));
//                // 将第一个ImageView添加进来
//                imageViews.add(imageView);
                // 将最后一个ImageView添加进来
                views.add(0, getImageView(imageViewFirst.getDrawable()));
                // 将第一个ImageView添加进来
                views.add(getImageView(imageViewLast.getDrawable()));
            }

            mAdapter.notifyDataSetChanged();
            this.mImageViews = mAdapter.getImageViews();
        }
    }

    private void initIndicatorState() {
        if (null != mIndicatorLayout && mIndicatorLayout.getChildCount() > 0) {
            mIndicatorLayout.removeAllViews();
        }
        if (null != mImageViews) {
            int ivSize = this.mImageViews.size();
            //设置指示器设置指示器
            mIndicators = new ImageView[ivSize];
            for (int i = 0; i < mIndicators.length; i++) {
                Activity activity = getActivity();
                if (null != activity) {
                    View view = LayoutInflater.from(activity).inflate(
                            R.layout.view_cycle_viewpager_indicator, null);
                    mIndicators[i] = (ImageView) view.findViewById(R.id.image_indicator);
                    mIndicatorLayout.addView(view);
                }
            }
            // 触发重新计算指示器指向
            setIndicator(mCurrentPosition > 0 ? mCurrentPosition - 1 : mCurrentPosition);
        }
    }

    @Override
    public void addAdapterListRefresh(ImageView imageView) {
        if (null != imageView) {
            //如果已经开启轮播
            if (mIsCycle) {
                List<ImageView> views = mAdapter.getImageViews();
                views.remove(0);
                views.remove(views.size() - 1);

            }

            mAdapter.addImageView(imageView);
            initIndicatorState();

            // 初始化的时候有开启循环才执行。
            if (mIsCycle) {
                List<ImageView> views = mAdapter.getImageViews();
                ImageView imageViewFirst = views.get(views.size() - 1);
                ImageView imageViewLast = views.get(0);
//                // 将最后一个ImageView添加进来
//                imageViews.add(0, imageViews.get(ivSize - 1));
//                // 将第一个ImageView添加进来
//                imageViews.add(imageView);
                // 将最后一个ImageView添加进来
                views.add(0, getImageView(imageViewFirst.getDrawable()));
                // 将第一个ImageView添加进来
                views.add(getImageView(imageViewLast.getDrawable()));
            }

            mAdapter.notifyDataSetChanged();
            this.mImageViews = mAdapter.getImageViews();
        }
    }

    private ImageView getImageView(Drawable drawable) {
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageDrawable(drawable);
        return imageView;
    }
}