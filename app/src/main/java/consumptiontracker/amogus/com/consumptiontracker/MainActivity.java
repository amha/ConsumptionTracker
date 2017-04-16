package consumptiontracker.amogus.com.consumptiontracker;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import consumptiontracker.amogus.com.consumptiontracker.dummy.DummyContent;

public class MainActivity extends AppCompatActivity implements InfoFragment.OnFragmentInteractionListener, MediaItemFragment.OnListFragmentInteractionListener {

    String TAG = "CONSOLE_TEXT";

    private int NUM_PAGES = 2;
    private PagerAdapter myPagerAdapter;

    @BindView(R.id.pager)
    ViewPager viewPager;

    @BindView(R.id.top_toolbar)
    Toolbar topToolbar;

    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        topToolbar.setTitle(getResources().getString(R.string.app_name));
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_media) {
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                    viewPager.setCurrentItem(1);
                }
                if (tabId == R.id.tab_favorites) {
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                    viewPager.setCurrentItem(0);
                }
            }

        });

        myPagerAdapter = new MyPagerAdaper(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);

    }

    // TODO: implement info fragment interactions
    public void infoFragmentClicked(Uri uri) {
    }

    // TODO: implement info media tap interactions
    public void onMediaClicked(DummyContent.DummyItem item) {
        Log.d(TAG, "dummy item clicked: " + item.id);
    }

    // TODO: Update to include remaing fragments
    private class MyPagerAdaper extends FragmentStatePagerAdapter {

        public MyPagerAdaper(FragmentManager f_manager) {
            super(f_manager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new InfoFragment();
                default:
                    return new MediaItemFragment();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}
