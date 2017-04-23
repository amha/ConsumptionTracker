package consumptiontracker.amogus.com.consumptiontracker;

import android.content.Intent;
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

    final String TAG = "CONSOLE_TEXT";
    final String SELECTED_PARAM = "ID_NUMBER";
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
                    viewPager.setCurrentItem(1);
                }
                if (tabId == R.id.tab_favorites) {
                    viewPager.setCurrentItem(0);
                }
            }

        });

        myPagerAdapter = new MyPagerAdaper(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);

    }

    public void infoFragmentClicked(Uri uri) {
    }

    public void onMediaClicked(DummyContent.DummyItem item) {
        Log.d(TAG, "dummy item clicked: " + item.id);
        Intent mIntent = new Intent(getApplicationContext(), CountActivity.class);
        mIntent.putExtra(SELECTED_PARAM, item.id);
        startActivity(mIntent);
    }

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