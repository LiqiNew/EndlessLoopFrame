package com.liqi.endlessloop.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.liqi.endlessloop.CycleViewPager;
import com.liqi.endlessloop.interfa.OnImageCycleViewListener;
import com.liqi.endlessloop.touch_image.TouchImageView;

import java.util.ArrayList;
import java.util.List;

/**手动滑动+图片缩放测试用例
 * Created by LiQi on 2018/1/4.
 */

public class TestZoomingActivity extends AppCompatActivity implements OnImageCycleViewListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_zooming_activity);
        getCycleViewPager();
    }

    private CycleViewPager getCycleViewPager() {
        List<ImageView> imageViews = new ArrayList<>();
        imageViews.add(getTouchImageView(R.drawable.test_iamge01));
        imageViews.add(getTouchImageView(R.drawable.test_iamge02));
        imageViews.add(getTouchImageView(R.drawable.test_iamge03));

        CycleViewPager cycleViewPager = (CycleViewPager) getSupportFragmentManager().findFragmentById(R.id.fragment);
        cycleViewPager.setCycle(true)
                // .leftRightDisplayOffset(50,50)
                .setIndicatorBottomCenter()
                .setBackgroundColorId(android.R.color.white)
                .startLoadData(imageViews, this);
        return cycleViewPager;
    }

    @NonNull
    private TouchImageView getTouchImageView(int imageId) {
        TouchImageView touchImageView = new TouchImageView(this);
        touchImageView.setBackgroundColor(Color.parseColor("#000000"));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        touchImageView.setLayoutParams(lp);
        touchImageView.setImageResource(imageId);
        //touchImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        //touchImageView.setOnTouchImageViewListener(this);
        return touchImageView;
    }

    @Override
    public void onImageClick(View imageView) {
        Toast.makeText(this, "图片点击了", Toast.LENGTH_SHORT).show();
    }
}
