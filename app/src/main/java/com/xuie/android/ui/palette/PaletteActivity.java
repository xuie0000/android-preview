package com.xuie.android.ui.palette;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import com.mikepenz.iconics.IconicsDrawable;
import com.xuie.android.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author xuie
 */
public class PaletteActivity extends AppCompatActivity {

    @BindView(R.id.tab_layout) TabLayout tabLayout;
    @BindView(R.id.viewpager) ViewPager viewpager;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.coordinator) CoordinatorLayout coordinator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.palette);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(new IconicsDrawable(this, "gmi_chevron_left").sizeDp(24).color(Color.WHITE));
        toolbar.setNavigationOnClickListener(view -> finish());

        PaletteViewPagerAdapter paletteViewPagerAdapter = new PaletteViewPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(paletteViewPagerAdapter);
        tabLayout.setupWithViewPager(viewpager);
        changeTopBgColor(0);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                changeTopBgColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private class PaletteViewPagerAdapter extends FragmentPagerAdapter {

        final int PAGE_COUNT = 5;
        private String tabTitles[] = new String[]{"主页", "分享", "收藏", "关注", "微博"};

        PaletteViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PaletteFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }


    /**
     * 根据Palette提取的颜色，修改tab和toolbar以及状态栏的颜色
     */
    private void changeTopBgColor(int position) {
        // 用来提取颜色的Bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), PaletteFragment.getBackgroundBitmapPosition(position));
        // Palette的部分
        Palette.Builder builder = Palette.from(bitmap);
        builder.generate(palette -> {
            //获取到充满活力的这种色调
            Palette.Swatch vibrant = palette.getVibrantSwatch();
            if (vibrant == null) {
                return;
            }
            //根据调色板Palette获取到图片中的颜色设置到toolbar和tab中背景，标题等，使整个UI界面颜色统一
            tabLayout.setBackgroundColor(Objects.requireNonNull(vibrant).getRgb());
            tabLayout.setSelectedTabIndicatorColor(colorBurn(vibrant.getRgb()));
            toolbar.setBackgroundColor(vibrant.getRgb());
            coordinator.setBackgroundColor(vibrant.getRgb());

            if (Build.VERSION.SDK_INT >= 21) {
                Window window = getWindow();
                window.setStatusBarColor(colorBurn(vibrant.getRgb()));
                window.setNavigationBarColor(colorBurn(vibrant.getRgb()));
            }
        });
    }

    /**
     * 颜色加深处理
     *
     * @param rgbValues RGB的值，由alpha（透明度）、red（红）、green（绿）、blue（蓝）构成，
     *                  Android中我们一般使用它的16进制，
     *                  例如："#FFAABBCC",最左边到最右每两个字母就是代表alpha（透明度）、
     *                  red（红）、green（绿）、blue（蓝）。每种颜色值占一个字节(8位)，值域0~255
     *                  所以下面使用移位的方法可以得到每种颜色的值，然后每种颜色值减小一下，在合成RGB颜色，颜色就会看起来深一些了
     */
    private int colorBurn(int rgbValues) {
        int alpha = rgbValues >> 24;
        int red = rgbValues >> 16 & 0xFF;
        int green = rgbValues >> 8 & 0xFF;
        int blue = rgbValues & 0xFF;
        red = (int) Math.floor(red * (1 - 0.1));
        green = (int) Math.floor(green * (1 - 0.1));
        blue = (int) Math.floor(blue * (1 - 0.1));
        return Color.argb(alpha, red, green, blue);
    }
}
