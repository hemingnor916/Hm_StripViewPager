# Hm_StripViewPager
简单几个步骤实现带标题的ViewPager A few steps to make a ViewPager with titles.

![Image text](https://github.com/hemingnor916/Hm_StripViewPager/blob/master/demo/demo.png?raw=true)

step 1:
Copy the file "Hm_StripViewPager.java" to your project .

step 2:

    <hm.hm_stripviewpager.Hm_StripViewPager
        android:id="@+id/pager"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    </hm.hm_stripviewpager.Hm_StripViewPager>
    
step 3:

        Hm_StripViewPager tabViewPager = (Hm_StripViewPager) findViewById(R.id.pager);
        tabViewPager.setTabTitle(new String[]{"page 1", "page 2", "page 3-------", "page 4", "page 5------", "page 6", "page 7"});// required
        tabViewPager.setTabBackgroundColor(Color.parseColor("#f3f3f3"));// default: #f1f1f1
        tabViewPager.setTabTitleColor(Color.parseColor("#333333"));// default: #333333
        tabViewPager.setTabTitleSelectedColor(Color.parseColor("#c83c3c"));
        tabViewPager.setDividerColorBetweenTabAndViewPager(Color.parseColor("#000000"));// default: #efefef
        tabViewPager.setNoScroll(false);// default: false

        Hm_StripViewPager.ViewPager_ pager = tabViewPager.getViewPager();
        Adapter adapter = new Adapter(getSupportFragmentManager());
        pager.setAdapter(yourAdapter);
        
        
#Contact me

Email: he.mingyang@qq.com
