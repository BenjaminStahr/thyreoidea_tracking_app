package com.example.hashimoto_app.ui.main;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.hashimoto_app.R;
/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter
{
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;
    private ThyroidFragment thyroidFragment;
    private SymptomFragment symptomFragment;
    private IntakeFragment intakeFragment;


    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        thyroidFragment = new ThyroidFragment(context);
        symptomFragment = new SymptomFragment(context);
        intakeFragment = new IntakeFragment(context);
    }

    @Override
    public Fragment getItem(int position)
    {
        // getItem is called to instantiate the fragment for the given page.
        if(position == 0) {

            return  thyroidFragment;
            //return new ThyroidFragment(mContext);//PlaceholderFragment.newInstance(position + 1);
        }
        else if(position == 1)
        {
            return symptomFragment;
            //return new SymptomFragment(mContext);
        }
        else
        {
            return intakeFragment;
            //return new IntakeFragment(mContext);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount()
    {
        // Show 3 total pages
        return 3;
    }
    public void adjustDataToTimePeriod(String period)
    {
        thyroidFragment.setAdapterData(period);
        symptomFragment.setAdapterData(period);
        intakeFragment.setAdapterData(period);
    }


}