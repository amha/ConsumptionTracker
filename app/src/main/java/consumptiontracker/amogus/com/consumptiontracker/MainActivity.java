package consumptiontracker.amogus.com.consumptiontracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import consumptiontracker.amogus.com.consumptiontracker.model.Count;

@SuppressWarnings("WeakerAccess")
public class MainActivity extends AppCompatActivity
        implements ListFragment.OnListFragmentInteractionListener {

    //  Binding views via butterknife
    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.top_toolbar)
    Toolbar topToolbar;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;
    //  The set of fragments to display in the view pager
    private ListFragment mediaListFragment;
    private ListFragment choreListFragment;
    private ListFragment selfListFragment;
    private ReportFragment reportFragment;

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
        setSupportActionBar(topToolbar);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
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
        PagerAdapter myPagerAdapter = new ListPagerAdapter(getSupportFragmentManager());
        myPagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(myPagerAdapter);
        viewPager.setCurrentItem(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_info:
                startActivity(new Intent(getApplicationContext(), InfoActivity.class));
                return true;
            case R.id.action_settings:
                startActivity(new Intent(getApplicationContext(), PreferenceActivity.class));
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void onItemClicked(String category, String action) {
        Intent mIntent = new Intent(getApplicationContext(), CountActivity.class);
        String SELECTED_ACTION = "ACTION_VALUE";
        mIntent.putExtra(SELECTED_ACTION, action);
        String SELECTED_CATEGORY = "CATEGORY_VALUE";
        mIntent.putExtra(SELECTED_CATEGORY, category);
        startActivity(mIntent);
    }

    private class ListPagerAdapter extends FragmentPagerAdapter {

        public ListPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
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
            return 4;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}