package com.example.hashimoto_app.ui.main;

import android.content.Context;
import android.content.Intent;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.example.hashimoto_app.MainActivity;
import com.example.hashimoto_app.R;
import com.example.hashimoto_app.backend.ThyroidElement;
import com.example.hashimoto_app.ui.main.intake.IntakeFragment;
import com.example.hashimoto_app.ui.main.symtoms.SymptomFragment;
import com.example.hashimoto_app.ui.main.thyroid.ThyroidFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends SmartFragmentStatePagerAdapter
{
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;
    private ThyroidFragment thyroidFragment;
    private ListView thyroidListView;
    private SymptomFragment symptomFragment;
    private IntakeFragment intakeFragment;


    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        thyroidFragment = new ThyroidFragment(context);
        //thyroidListView = thyroidFragment.getListView();
        symptomFragment = new SymptomFragment(context);
        intakeFragment = new IntakeFragment(context);
    }

    @Override
    public Fragment getItem(int position)
    {
        // getItem is called to instantiate the fragment for the given page.
        if(position == 0)
        {
            //thyroidFragment = new ThyroidFragment(mContext);
            //return ThyroidFragment.newInstance();
            //return  thyroidFragment;
            //thyroidFragment = ThyroidFragment.newInstance(mContext);//ThyroidFragment) Fragment.instantiate(mContext, ThyroidFragment.class.getName(), null);
            return thyroidFragment;
        }
        else if(position == 1)
        {
            return symptomFragment;
        }
        else
        {
            return intakeFragment;
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


    @Override
    public int getCount()
    {
        // Show 3 total pages
        return 3;
    }
    public void adjustDataToTimePeriod(String period)
    {
        //thyroidListView.setAdapter(thyroidFragment.generatePlotAdapter());
        thyroidFragment.setAdapterData(period);
        symptomFragment.setAdapterData(period);
        intakeFragment.setAdapterData(period);



    }
    public void updateThyroidFragment(MainActivity mainActivity)
    {
        //thyroidFragment = null;
        //thyroidFragment = (ThyroidFragment) mainActivity.getSupportFragmentManager().findFragmentByTag("thyroid_fragment");
        if(thyroidFragment != null)
        {
            /*final FragmentTransaction ft = mainActivity.getSupportFragmentManager().beginTransaction();
            ft.setReorderingAllowed(false);
            ft.detach(thyroidFragment);
            ft.attach(thyroidFragment);
            ft.commit();*/
            mainActivity.getSupportFragmentManager()
                    .beginTransaction()
                    .detach(thyroidFragment)
                    .commitNowAllowingStateLoss();

            mainActivity.getSupportFragmentManager()
                    .beginTransaction()
                    .attach(thyroidFragment)
                    .commitAllowingStateLoss();
    }


    }
}