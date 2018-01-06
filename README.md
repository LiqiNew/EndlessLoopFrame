[![](https://jitpack.io/v/liqinew/endlessloopframe.svg)](https://jitpack.io/#liqinew/endlessloopframe)
[![](https://img.shields.io/badge/%E4%BD%9C%E8%80%85-%E6%9D%8E%E5%A5%87-orange.svg)](https://github.com/LiqiNew)

# EndlessLoopFrame
支持轮询，无限循环滑动，图片手势缩放等效果。

# 如何使用?

### Gradle远程依赖 ###

**1：在项目根目录build.gradley**	<br>
```gradle
allprojects {
　　repositories {
  　　//依赖仓库
　　　maven { url 'https://jitpack.io' }
　　}
}
```
**2：依赖EndlessLoopFrame**<br>
```gradle
compile 'com.github.liqinew:endlessloopframe:V.1.0.0'
```

### 三种效果案例实现

#### XML布局定义

* **XML定义一（设置为android:name="com.liqi.endlessloop.CycleViewPager"）**
```xml
    <fragment
        android:id="@+id/fragment_cycle_viewpager"
        android:name="com.liqi.endlessloop.CycleViewPager"
        android:layout_width="match_parent"
        android:layout_height="200dp"/>
```
* **XML定义二（设置为class="com.liqi.endlessloop.CycleViewPager"）**
```xml
    <fragment
        android:id="@+id/fragment_cycle_viewpager"
        class="com.liqi.endlessloop.CycleViewPager"
        android:layout_width="match_parent"
        android:layout_height="200dp"/>
```
#### 获取XML中定义的CycleViewPager

* **继承AppCompatActivity对象**
```java
CycleViewPager mCycleViewPager=(CycleViewPager)getSupportFragmentManager().findFragmentById(R.id.fragment_cycle_viewpager);
```
#### 一：轮播案例效果

* **效果图**
<image src="./image/demo_01.gif" width="400px" height="700px"/>

* **"轮播"代码实现**

**参数设置(OnCycleViewPagerListener接口提供设置API)**
```java
/**
* 参阅底部OnCycleViewPagerListener接口API设置
*/
...

/**
* 开启轮播，默认不轮播,轮播一定是循环的
*
* @return OnViewPagerDataListener
*/
openWheel();

/**
* 关闭轮播.并设置是否开启循环
*
* @param isCycle 是否开启循环
*
* @return OnViewPagerDataListener
*/
closeWheel();
```
* **数据添加和替换(OnViewPagerDataListener接口API请查阅底部)
通过OnCycleViewPagerListener接口openWheel()或者closeWheel()方法获取OnViewPagerDataListener接口，<br>
然后通过OnViewPagerDataListener接口API去操作。<br>**

#### 二：手势无限循环滑动+图片手势滑动缩放案例效果

* **效果图**

<image src="./image/demo_02.gif" width="400px" height="700px"/>

* **"手势无限循环滑动"代码实现注意事项**

**参数还是调用OnCycleViewPagerListener接口提供的API去设置**<br>

**1：切勿调用OnCycleViewPagerListener接口中openWheel()方法去开启轮播。**<br>

**2：记得把OnCycleViewPagerListener接口setCycle(true)方法设置为true。如果不无限循环滑动得话，请设置为false。**<br>

* **"图片手势滑动缩放"代码实现**

**把传输给CycleViewPager的ImageView值对象变成TouchImageView对象，TouchImageView是继承ImageView自定扩展对象**

```java
/**
* TouchImageView的API同ImageView一致
*/
TouchImageView touchImageView = new TouchImageView(this);
touchImageView.setBackgroundColor(Color.parseColor("#000000"));
LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
touchImageView.setLayoutParams(lp);
touchImageView.setImageResource(R.drawable.xxxx);
```

#### 三：界面左右偏距效果案例

* **效果图**

<image src="./image/demo_03.gif" width="400px" height="200px"/>

* **"界面左右偏距"代码实现**

**调用OnCycleViewPagerListener接口中leftRightDisplayOffset(50,50)设置左右偏距即可**

### A P I

#### OnCycleViewPagerListener接口操作API(非静态调用)
```java
 /**
     * 设置页面切换监听器
     *
     * @param onPageChangeListener 页面切换监听器
     *
     * @return OnCycleViewPagerListener
     */
    OnCycleViewPagerListener.setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener);

    /**
     * 设置指示器底部居中，默认指示器在右方
     *
     * @return OnCycleViewPagerListener
     */
    OnCycleViewPagerListener.setIndicatorBottomCenter();

    /**
     * 设置指示器顶部居中，默认指示器在右方
     *
     * @return OnCycleViewPagerListener
     */
    OnCycleViewPagerListener.setIndicatorTopCenter();

    /**
     * 设置指示器对齐方式。默认指示器在右方
     *
     * @param relativeAlignOne 对齐位置方式1。使用RelativeLayout的对齐方式去设置“上下左右居中”对齐方式
     * @param relativeAlignTwo 对齐位置方式2。使用RelativeLayout的对齐方式去设置“上下左右居中”对齐方式
     * @return OnCycleViewPagerListener
     */
    OnCycleViewPagerListener.setIndicatorAlignManner(int relativeAlignOne, int relativeAlignTwo);

    /**
     * 是否处于循环状态
     *
     * @return 循环状态。true是循环，false没有循环
     */
    OnCycleViewPagerListener.isCycle();

    /**
     * 是否循环，默认不开启，开启前，请将views的最前面与最后面各加入一个视图，用于循环
     *
     * @param isCycle 是否循环
     * @return OnCycleViewPagerListener
     */
    OnCycleViewPagerListener.setCycle(boolean isCycle);

    /**
     * 是否处于轮播状态
     *
     * @return 轮播状态。true是轮播，false没有轮播
     */
    OnCycleViewPagerListener.isWheel();

    /**
     * 设置轮播暂停时间，即每多少秒切换到下一张视图.默认5000ms
     *
     * @param time 秒为单位
     *
     * @return OnCycleViewPagerListener
     */
    OnCycleViewPagerListener.setTime(int time);

    /**
     * 隐藏CycleViewPager
     *
     * @return OnCycleViewPagerListener
     */
    OnCycleViewPagerListener.hide();

    /**
     * 返回内置的viewpager
     *
     * @return BaseViewPager
     */
     OnCycleViewPagerListener.getViewPager();

    /**
     * 设置viewpager是否可以滚动
     *
     * @param enable
     *
     * @return OnCycleViewPagerListener
     */
    OnCycleViewPagerListener.setScrollable(boolean enable);

    /**
     * 返回当前位置,循环时需要注意返回的position包含之前在views最前方与最后方加入的视图，即当前页面试图在views集合的位置
     *
     * @return 当前界面显示位置
     */
    OnCycleViewPagerListener.getCurrentPostion();

    /**
     * 设置指示器图片
     *
     * @param selectedIndicatorId 指示器选择图片
     * @param defaultIndicatorId  指示器默认图片
     *
     * @return OnCycleViewPagerListener
     */
    OnCycleViewPagerListener.setIndicatorImageId(@DrawableRes int selectedIndicatorId, @DrawableRes int defaultIndicatorId);

    /**
     * 如果当前页面嵌套在另一个viewPager中，为了在进行滚动时阻断父ViewPager滚动，可以 阻止父ViewPager滑动事件
     * 父ViewPager需要实现BaseViewPager中的setScrollable方法
     *
     * @param V <V extends BaseViewPager>
     *
     * @return OnCycleViewPagerListener
     */
     OnCycleViewPagerListener.disableOutsideViewPagerTouchEvent(V parentViewPager);

    /**
     * 设置viewpager过度动画渐出渐入动画
     *
     * @return OnCycleViewPagerListener
     */
    OnCycleViewPagerListener.viewPagerGradientDynamic();

    /**
     * viewpager切换速度时间设置
     *
     * @param duration 切换过度时间 单位：秒
     *
     * @return OnCycleViewPagerListener
     */
    OnCycleViewPagerListener.viewPagerSwitchingSpeed(int duration);

    /**
     * 左右显示偏距设置
     *
     * @param left  左显示偏距
     * @param right 右显示偏距
     *
     * @return OnCycleViewPagerListener
     */
    OnCycleViewPagerListener.leftRightDisplayOffset(int left, int right);

    /**
     * 设置背景颜色
     *
     * @param colorId 颜色值ID
     *
     * @return OnCycleViewPagerListener
     */
    OnCycleViewPagerListener.setBackgroundColorId(@ColorRes int colorId);

    /**
     * 开始装载数据
     *
     * @param views    装载数据imageView集合
     * @param listener 监听器
     *
     * @return OnCycleViewPagerListener
     */
    OnCycleViewPagerListener.startLoadData(List<ImageView> views,
                                           OnImageCycleViewListener listener);

    /**
     * 开始装载数据
     *
     * @param imageViews   要显示的views
     * @param showPosition 默认显示位置
     *
     * @return OnCycleViewPagerListener
     */
    OnCycleViewPagerListener.startLoadData(@NonNull List<ImageView> imageViews,
                                           OnImageCycleViewListener listener, int showPosition);

    /**
     * 开启轮播，默认不轮播,轮播一定是循环的
     *
     * @return OnViewPagerDataListener
     */
    OnCycleViewPagerListener.openWheel();

    /**
     * 关闭轮播.并设置是否开启循环
     *
     * @param isCycle 是否开启循环
     *
     * @return OnViewPagerDataListener
     */
    OnCycleViewPagerListener.closeWheel(boolean isCycle);
```

#### OnViewPagerDataListener接口操作API(非静态调用)

```java
    /**
     *数据赋值。并指定当前第几个界面展示
     * @param imageViews ImageView数据集合
     * @param showPosition 当前第几个界面展示
     */
    OnViewPagerDataListener.initAdapterListRefresh(List<ImageView> imageViews, int showPosition);

    /**
     *数据赋值。
     * @param imageViews ImageView数据集合
     */
    OnViewPagerDataListener.initAdapterListRefresh(List<ImageView> imageViews);

    /**
     * 数据添加
     * @param imageViews ImageView数据集合
     */
    OnViewPagerDataListener.addAdapterListRefresh(List<ImageView> imageViews);

    /**
     * 数据添加
     * @param imageView ImageView数据
     */
   OnViewPagerDataListener.addAdapterListRefresh(ImageView imageView);
```
