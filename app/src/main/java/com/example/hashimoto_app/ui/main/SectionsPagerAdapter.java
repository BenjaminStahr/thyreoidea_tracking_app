package com.example.hashimoto_app.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.hashimoto_app.R;
import com.example.hashimoto_app.ui.main.intake.IntakeFragment;
import com.example.hashimoto_app.ui.main.symtoms.SymptomFragment;
import com.example.hashimoto_app.ui.main.thyroid.ThyroidFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages. It organizes the different pages of the app
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter
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
        //thyroidListView = thyroidFragment.getListView();
        symptomFragment = new SymptomFragment(context);
        intakeFragment = new IntakeFragment(context);
    }

    /**
     *
     * @param position
     * @return returns the different items by index in this sectionspageradapter
     */
    @Override
    public Fragment getItem(int position)
    {
        // getItem is called to instantiate the fragment for the given page.
        if(position == 0)
        {
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

    /**
     *
     * @param position
     * @return returns the name of a page
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    /**
     *
     * @param object
     * @return gets the index of an fragment, but is not implemented
     */
    @Override
    public int getItemPosition(Object object)
    {
        return POSITION_NONE;
    }

    /**
     *
     * @return returns the number of pages in this adapter, in our case three
     */
    @Override
    public int getCount()
    {
        return 3;
    }

    /**
     * This method updates the fragments, if the user adjusted the time frame, he wants to see
     * @param period
     */
    public void adjustDataToTimePeriod(String period)
    {
        thyroidFragment.setAdapterData(period);
        symptomFragment.setAdapterData(period);
        intakeFragment.setAdapterData(period);
    }
}