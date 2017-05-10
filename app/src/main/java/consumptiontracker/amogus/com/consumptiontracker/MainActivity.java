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

    final String SELECTED_ACTION = "ACTION_VALUE";
    final String SELECTED_CATEGORY = "CATEGORY_VALUE";

    //  The set of fragments to display in the view pager
    ListFragment mediaListFragment;
    ListFragment choreListFragment;
    ListFragment selfListFragment;
    ReportFragment reportFragment;

    //  Binding views via butterknife
    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.top_toolbar)
    Toolbar topToolbar;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    //  Controls the number of fragments contained within
    //  the viewpager
    private int NUM_PAGES = 4;

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
        Utils.printToConsole(list);

        // Configure toolbar and bottombar
        topToolbar.setTitle(getResources().getString(R.string.app_name));
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Log.d(Utils.TAG, "Tab ID : " + tabId);
                switch (tabId) {
                    case R.id.tab_media:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.tab_chores:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.tab_self:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.tab_report:
                        viewPager.setCurrentItem(3);
                        break;
                }
            }

        });

        // set viewpager adapter
        myPagerAdapter = new ListPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        viewPager.setCurrentItem(0);
    }

    public void infoFragmentClicked(Uri uri) {
    }

    public void onItemClicked(String category, String action) {
        Intent mIntent = new Intent(getApplicationContext(), CountActivity.class);
        mIntent.putExtra(SELECTED_ACTION, action);
        mIntent.putExtra(SELECTED_CATEGORY, category);
        startActivity(mIntent);
    }

    private class ListPagerAdapter extends FragmentStatePagerAdapter {

        public ListPagerAdapter(FragmentManager fMmanager) {
            super(fMmanager);
        }

        @Override
        public Fragment getItem(int position) {
            Log.d(Utils.TAG, "Tag value = " + bottomBar.getCurrentTab().getTitle());
            switch (position) {
                case 0:
                    // media fragment is first position
                    if (mediaListFragment == null) {
                        mediaListFragment = ListFragment.newInstance(1, "Media");
                    }
                    return mediaListFragment;
                case 1:
                    // chores fragment is second
                    if (choreListFragment == null) {
                        choreListFragment = ListFragment.newInstance(1, "Chores");
                    }
                    return choreListFragment;
                case 3:
                    Log.d(Utils.TAG, "report tab selected");
                    if (reportFragment == null) {
                        reportFragment = ReportFragment.newInstance();
                    }
                    return reportFragment;
                case 2:
                default:
                    // self fragment is set to default for now
                    if (selfListFragment == null) {
                        selfListFragment = ListFragment.newInstance(1, "Self");
                    }
                    return selfListFragment;
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}