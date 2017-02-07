package hm.hm_stripviewpager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Hm_StripViewPager tabViewPager = (Hm_StripViewPager) findViewById(R.id.pager);
        tabViewPager.setTabTitle(new String[]{"page 1", "page 2", "page 3-------", "page 4", "page 5------", "page 6", "page 7"});// required
        tabViewPager.setTabBackgroundColor(Color.parseColor("#f3f3f3"));// default: #f1f1f1
        tabViewPager.setTabTitleColor(Color.parseColor("#333333"));// default: #333333
        tabViewPager.setTabTitleSelectedColor(Color.parseColor("#c83c3c"));
        tabViewPager.setDividerColorBetweenTabAndViewPager(Color.parseColor("#000000"));// default: #efefef
        tabViewPager.setNoScroll(false);// default: false

        Hm_StripViewPager.ViewPager_ pager = tabViewPager.getViewPager();
        Adapter adapter = new Adapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

    }

    class Adapter extends FragmentPagerAdapter {

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new F_1();
            } else {
                return new F_2();
            }
        }

        @Override
        public int getCount() {
            return 7;
        }
    }
}
