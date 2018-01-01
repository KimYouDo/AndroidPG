package kangwon.cse2.kdy.myruns2;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private List<Fragment> frags;
    private Fragment historyFragment;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Fragment settingFragment;
    private Fragment startFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        this.startFragment = new StartFragment();
        this.historyFragment = new HistoryFragment();
        this.settingFragment = new SettingsFragment();
        this.frags = new ArrayList();
        this.frags.add(this.startFragment);
        this.frags.add(this.historyFragment);
        this.frags.add(this.settingFragment);
        this.mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this.frags);
        this.mViewPager = ((ViewPager)findViewById(R.id.container));
        this.mViewPager.setAdapter(this.mSectionsPagerAdapter);
        ((TabLayout)findViewById(R.id.tabs)).setupWithViewPager(this.mViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() ==  R.id.action_settings);
        for (boolean bool = true; ; bool = super.onOptionsItemSelected(item))
            return bool;
    }




    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments;

        public SectionsPagerAdapter(FragmentManager fm, List<Fragment> frags) {
            super(fm);
            this.fragments = frags;
        }


        public Fragment getItem(int paramInt)
        {
            return this.fragments.get(paramInt);
        }

        public int getCount()
        {
            return 3;
        }

        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "시작";

                case 1:
                    return "기록";

                case 2:
                    return "설정";
                default:
                    return null;

            }
        }

    }

}
