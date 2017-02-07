package hm.hm_stripviewpager;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/9/14.
 * 1.编写layout文件
 * 2.
 */

public class Hm_StripViewPager extends LinearLayout {
    Context context;
    ViewPager_ pager;
    String tabTitle[];
    int tabTitleColor = Color.parseColor("#333333");
    int tabTitleSelectedColor = Color.parseColor("#c83c3c");
    int tabBackgroundColor = Color.parseColor("#f1f1f1");
    int dividerColorBetweenTabAndViewPager = Color.parseColor("#efefef");
    private boolean noScroll = false;
    private TextView[] tabTitleTextViews;
    private TextView tv_strip;
    private int screenWidth = 0;
    private HorizontalScrollView horizontalScrollView;
    private int lastPageIndex = 0;
    private float stripXPosition = 0;

    public Hm_StripViewPager(Context context) {
        super(context);
        this.context = context;
        setOrientation(LinearLayout.VERTICAL);
        initView_prepare();

    }

    public Hm_StripViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setOrientation(LinearLayout.VERTICAL);
        initView_prepare();

    }

    public int getTabTitleSelectedColor() {
        return tabTitleSelectedColor;
    }

    public void setTabTitleSelectedColor(int tabTitleSelectedColor) {
        this.tabTitleSelectedColor = tabTitleSelectedColor;
    }

    public int getDividerColorBetweenTabAndViewPager() {
        return dividerColorBetweenTabAndViewPager;
    }

    public void setDividerColorBetweenTabAndViewPager(int dividerColorBetweenTabAndViewPager) {
        this.dividerColorBetweenTabAndViewPager = dividerColorBetweenTabAndViewPager;
    }

    public boolean isNoScroll() {
        return noScroll;
    }

    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }

    public int getTabBackgroundColor() {
        return tabBackgroundColor;
    }

    public void setTabBackgroundColor(int tabBackgroundColor) {
        this.tabBackgroundColor = tabBackgroundColor;
    }

    public int getTabTitleColor() {
        return tabTitleColor;
    }

    public void setTabTitleColor(int tabTitleColor) {
        this.tabTitleColor = tabTitleColor;
    }

    public String[] getTabTitle() {
        return tabTitle;
    }

    public void setTabTitle(String[] tabTitle) {
        this.tabTitle = tabTitle;
    }


    void initView_prepare() {
        pager = new ViewPager_(context);
        pager.setId(123);//required a view id to avoid crash.


        final View rootView = getRootView();
        ViewTreeObserver vto2 = rootView.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    rootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                initView(getMeasuredWidth(), getMeasuredHeight());
            }
        });
    }

    private void initView(int parentWidth, int parentHeight) {

        screenWidth = parentWidth;
         /*
        添加头部的Strip
         */
        LinearLayout tabParent = new LinearLayout(context);
        tabParent.setOrientation(LinearLayout.HORIZONTAL);
        tabParent.setBackgroundColor(tabBackgroundColor);
        int tabHeight = (int) (parentHeight * 0.08);
        tabTitleTextViews = new TextView[tabTitle.length];
        TextView tv_temp = new TextView(context);
        final int titlePaddingSize = (int) tv_temp.getTextSize();
        for (int i = 0; i < tabTitle.length; i++) {
            TextView tv = new TextView(context);
            tabTitleTextViews[i] = tv;
            if (i == 0) {
                tv.setTextColor(tabTitleSelectedColor);
            } else {
                tv.setTextColor(tabTitleColor);
            }
            tv.setGravity(Gravity.CENTER);
            tv.setText(tabTitle[i]);
//            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (tv.getTextSize() * 0.8));
            tv.setPadding(titlePaddingSize, 0, titlePaddingSize, 0);
            tabParent.addView(tv, ViewGroup.LayoutParams.WRAP_CONTENT, tabHeight);

            final int finalI = i;
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    pager.setCurrentItem(finalI);

                }
            });
        }
        /*
        滑动条
         */
        LinearLayout stripParent = new LinearLayout(context);
        stripParent.setOrientation(LinearLayout.VERTICAL);

        tv_strip = new TextView(context);
        tv_strip.setBackgroundColor(tabTitleSelectedColor);
        tv_strip.setMovementMethod(ScrollingMovementMethod.getInstance());
        stripParent.addView(tabParent, ViewGroup.LayoutParams.MATCH_PARENT, tabHeight);

        LinearLayout linear1 = new LinearLayout(context);
        linear1.setOrientation(LinearLayout.HORIZONTAL);
        linear1.addView(tv_strip, 10, (int) (tabHeight * 0.05));

        stripParent.addView(linear1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tabTitleTextViews[0].post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams params = tv_strip.getLayoutParams();
                params.width = tabTitleTextViews[0].getWidth();
                tv_strip.setLayoutParams(params);
            }
        });

        horizontalScrollView = new HorizontalScrollView(context);
        horizontalScrollView.setBackgroundColor(tabBackgroundColor);
        horizontalScrollView.setHorizontalScrollBarEnabled(false);
        horizontalScrollView.addView(stripParent, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(horizontalScrollView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);



        /*
        分割线
         */
        TextView tv_divider = new TextView(context);
        tv_divider.setBackgroundColor(dividerColorBetweenTabAndViewPager);
        addView(tv_divider, ViewGroup.LayoutParams.MATCH_PARENT, 2);


         /*
        添加pager
         */
        int pagerHeight = (int) (parentHeight * 0.87);
        addView(pager, LayoutParams.MATCH_PARENT, pagerHeight);
    }

    public ViewPager_ getViewPager() {
        return pager;
    }


    public class ViewPager_ extends ViewPager {
        ViewPager_ pager = this;
        float lastPositionOffset = 0;
        OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset == 0.0) {
                    lastPageIndex = getCurrentItem();
                    lastPositionOffset = 0;
                    return;
                }

                if (lastPositionOffset != 0) {

                    float movePixel = (positionOffset - lastPositionOffset) * 100;
                    if (Math.abs(movePixel) < 25) {
                        float a = 0;
                        a = tv_strip.getX() + movePixel;
//                        System.err.println("----------" + lastPositionOffset + "   " + positionOffset + "    " + (positionOffset - lastPositionOffset) * 100);
                        tv_strip.setX(a);
                    }

                }


                lastPositionOffset = positionOffset;
            }

            @Override
            public void onPageSelected(int position) {
                System.err.println("-----------------------------on page selected:" + position);
                for (int i = 0; i < tabTitle.length; i++) {
                    if (i == position) {
                        tabTitleTextViews[i].setTextColor(tabTitleSelectedColor);
                    } else {
                        tabTitleTextViews[i].setTextColor(tabTitleColor);
                    }
                }
                int xPos = (int) (tabTitleTextViews[position].getX());
                ObjectAnimator.ofFloat(tv_strip, "translationX", xPos).start();
                //修改strip的width
                ViewGroup.LayoutParams params = tv_strip.getLayoutParams();
                params.width = tabTitleTextViews[position].getWidth();
                tv_strip.setLayoutParams(params);

                //滑动处理
                if (screenWidth != 0) {
                    if (position > lastPageIndex && screenWidth / 2 < xPos) {
                        //已经超过中间，需要滑动
                        stripXPosition = tv_strip.getX() + tabTitleTextViews[position].getWidth();
                        horizontalScrollView.smoothScrollBy(tabTitleTextViews[position].getWidth(), 0);
                    } else if (lastPageIndex > position && xPos < screenWidth / 2 + tabTitleTextViews[position].getWidth()) {
                        stripXPosition = tv_strip.getX() - tabTitleTextViews[position].getWidth();
                        horizontalScrollView.smoothScrollBy(-tabTitleTextViews[position].getWidth(), 0);
                    }

                }

                //这句在最后
//                lastPageIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };


        public ViewPager_(Context context) {
            super(context);
            //重写addOnPageChangeListener
            addOnPageChangeListener(onPageChangeListener);
        }

        //从xml引用，会调用此方法
        public ViewPager_(Context context, AttributeSet attrs) {
            super(context, attrs);
            //重写addOnPageChangeListener
            addOnPageChangeListener(onPageChangeListener);

        }

        @Override
        public void setCurrentItem(int item) {
            if (noScroll) {
                super.setCurrentItem(item, false);
            } else {
                super.setCurrentItem(item, true);
            }
        }

        /**
         * 禁止滑动
         *
         * @param arg0
         * @return
         */
        @Override
        public boolean onTouchEvent(MotionEvent arg0) {
            if (noScroll)
                return false;
            else
                return super.onTouchEvent(arg0);
        }

        /**
         * 禁止滑动
         *
         * @param arg0
         * @return
         */
        @Override
        public boolean onInterceptTouchEvent(MotionEvent arg0) {
            if (noScroll)
                return false;
            else
                return super.onInterceptTouchEvent(arg0);
        }

    }

}





