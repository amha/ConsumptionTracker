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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import consumptiontracker.amogus.com.consumptiontracker.model.Count;

public class MainActivity extends AppCompatActivity
        implements InfoFragment.OnFragmentInteractionListener,
        ListFragment.OnListFragmentInteractionListener {

    final String SELECTED_PARAM = "ID_NUMBER";
    //  The set of fragments to display in the view pager
    CountFragment mediaList;
    CountFragment houseList;
    CountFragment bodyList;
    //  Binding views for better performance
    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.top_toolbar)
    Toolbar topToolbar;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;
    //  Controls the number of fragments contained within
    //  the viewpager
    private int NUM_PAGES = 3;
    //  Object that maps layouts with data models for the
    //  viewpager
    private PagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Check contents of table
        List<Count> list = Count.listAll(Count.class);

        // Print contents of the database
        Utils.printToConsole(list);

        // Configure toolbar
        topToolbar.setTitle(getResources().getString(R.string.app_name));

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Log.d(Utils.TAG, "Bottom Bar, selected value = " + bottomBar.getCurrentTab().getTitle());

                if (tabId == R.id.tab_media) {
                    viewPager.setCurrentItem(0);
                }

                if (tabId == R.id.tab_favorites) {
                    viewPager.setCurrentItem(1);
                }

                if (tabId == R.id.tab_friends) {
                    viewPager.setCurrentItem(2);
                }
            }

        });

        myPagerAdapter = new ListPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        viewPager.setCurrentItem(0);
    }

    public void infoFragmentClicked(Uri uri) {
    }

    public void onMediaClicked(String item) {
        Intent mIntent = new Intent(getApplicationContext(), CountActivity.class);
        mIntent.putExtra(SELECTED_PARAM, item);
        startActivity(mIntent);
    }

    private class ListPagerAdapter extends FragmentStatePagerAdapter {

        public ListPagerAdapter(FragmentManager f_manager) {
            super(f_manager);
        }

        @Override
        public Fragment getItem(int position) {
            Log.d(Utils.TAG, "Tag value = " + bottomBar.getCurrentTab().getTitle());
            switch (position) {
                case 0:
                    //return new ListFragment();
                    return ListFragment.newInstance(1, "Media");
                case 1:
                    return ListFragment.newInstance(1, "Chores");
                default:
                    return ListFragment.newInstance(1, "Self");
            }

        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}