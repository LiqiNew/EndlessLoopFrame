package com.liqi.endlessloop.interfa;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.liqi.endlessloop.BaseViewPager;

import java.util.List;

/**
 * 轮播fragment对象对外暴露接口
 * Created by LiQi on 2017/12/21.
 */

public interface OnCycleViewPagerListener {
    /**
     * 设置页面切换监听器
     *
     * @param onPageChangeListener 页面切换监听器
     * @return OnCycleViewPagerListener
     */
    OnCycleViewPagerListener setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener);

    /**
     * 设置指示器底部居中，默认指示器在右方
     *
     * @return OnCycleViewPagerListener
     */
    OnCycleViewPagerListener setIndicatorBottomCenter();

    /**
     * 设置指示器顶部居中，默认指示器在右方
     *
     * @return OnCycleViewPagerListener
     */
    OnCycleViewPagerListener setIndicatorTopCenter();

    /**
     * 设置指示器对齐方式。默认指示器在右方
     *
     * @param relativeAlignOne 对齐位置方式1。使用RelativeLayout的对齐方式去设置“上下左右居中”对齐方式
     * @param relativeAlignTwo 对齐位置方式2。使用RelativeLayout的对齐方式去设置“上下左右居中”对齐方式
     * @return OnCycleViewPagerListener
     */
    OnCycleViewPagerListener setIndicatorAlignManner(int relativeAlignOne, int relativeAlignTwo);

    /**
     * 是否处于循环状态
     *
     * @return 循环状态。true是循环，false没有循环
     */
    boolean isCycle();

    /**
     * 是否循环，默认不开启，开启前，请将views的最前面与最后面各加入一个视图，用于循环
     *
     * @param isCycle 是否循环
     * @return OnCycleViewPagerListener
     */
    OnCycleViewPagerListener setCycle(boolean isCycle);

    /**
     * 是否处于轮播状态
     *
     * @return 轮播状态。true是轮播，false没有轮播
     */
    boolean isWheel();

    /**
     * 设置轮播暂停时间，即每多少秒切换到下一张视图.默认5000ms
     *
     * @param time 秒为单位
     *
     * @return OnCycleViewPagerListener
     */
    OnCycleViewPagerListener setTime(int time);

    /**
     * 隐藏CycleViewPager
     *
     * @return OnCycleViewPagerListener
     */
    OnCycleViewPagerListener hide();

    /**
     * 返回内置的viewpager
     *
     * @return viewPager
     */
    BaseViewPager getViewPager();

    /**
     * 设置viewpager是否可以滚动
     *
     * @param enable
     *
     * @return OnCycleViewPagerListener
     */
    OnCycleViewPagerListener setScrollable(boolean enable);

    /**
     * 返回当前位置,循环时需要注意返回的position包含之前在views最前方与最后方加入的视图，即当前页面试图在views集合的位置
     *
     * @return 当前界面显示位置
     */
    int getCurrentPostion();

    /**
     * 设置指示器图片
     *
     * @param selectedIndicatorId 指示器选择图片
     * @param defaultIndicatorId  指示器默认图片
     *
     * @return OnCycleViewPagerListener
     */
    OnCycleViewPagerListener setIndicatorImageId(@DrawableRes int selectedIndicatorId, @DrawableRes int defaultIndicatorId);

    /**
     * 如果当前页面嵌套在另一个viewPager中，为了在进行滚动时阻断父ViewPager滚动，可以 阻止父ViewPager滑动事件
     * 父ViewPager需要实现BaseViewPager中的setScrollable方法
     *
     * @return OnCycleViewPagerListener
     */
    <V extends BaseViewPager> OnCycleViewPagerListener disableOutsideViewPagerTouchEvent(V parentViewPager);

    /**
     * 设置viewpager过度动画渐出渐入动画
     *
     * @return OnCycleViewPagerListener
     */
    OnCycleViewPagerListener viewPagerGradientDynamic();

    /**
     * viewpager切换速度时间设置
     *
     * @param duration 切换过度时间 单位：秒
     *
     * @return OnCycleViewPagerListener
     */
    OnCycleViewPagerListener viewPagerSwitchingSpeed(int duration);

    /**
     * 左右显示偏距设置
     *
     * @param left  左显示偏距
     * @param right 右显示偏距
     *
     * @return OnCycleViewPagerListener
     */
    OnCycleViewPagerListener leftRightDisplayOffset(int left, int right);

    /**
     * 设置背景颜色
     *
     * @param colorId 颜色值ID
     *
     * @return OnCycleViewPagerListener
     */
    OnCycleViewPagerListener setBackgroundColorId(@ColorRes int colorId);

    /**
     * 开始装载数据
     *
     * @param views    装载数据imageView集合
     * @param listener 监听器
     *
     * @return OnCycleViewPagerListener
     */
    OnCycleViewPagerListener startLoadData(List<ImageView> views,
                                           OnImageCycleViewListener listener);

    /**
     * 开始装载数据
     *
     * @param imageViews   要显示的views
     * @param showPosition 默认显示位置
     *
     * @return OnCycleViewPagerListener
     */
    OnCycleViewPagerListener startLoadData(@NonNull List<ImageView> imageViews,
                                           OnImageCycleViewListener listener, int showPosition);

    /**
     * 开启轮播，默认不轮播,轮播一定是循环的
     *
     * @return OnViewPagerDataListener
     */
    OnViewPagerDataListener openWheel();

    /**
     * 关闭轮播.并设置是否开启循环
     *
     * @param isCycle 是否开启循环
     *
     * @return OnViewPagerDataListener
     */
    OnViewPagerDataListener closeWheel(boolean isCycle);
}
