package com.apps.edgarhz.witcomregisters;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.edgarhz.witcomregisters.local.LocaldatabaseActivity;
import com.apps.edgarhz.witcomregisters.remote.RemotedatabaseActivity;

import java.util.Calendar;

public class NavigationActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private static ViewPager mViewPager;
    public static String primary = "#22232A";
    public static String dark = "#1C1D26";
    public static String blue = "#0F5DBE";
    public static String accent = "#FF9A22";
    public static String textWhite = "#EFEFEF";
    public static String textAccent = "#0F5DB";
    public static final String HOST = "http://192.168.43.214/witcom/PutData.php";



    private AppCompatActivity appCompatActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        swipeToast();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_navigation, container, false);

            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            ImageView imgV = (ImageView)rootView.findViewById(R.id.imageView);

            if(getArguments().getInt(ARG_SECTION_NUMBER)==1) {
                textView.setText(Html.fromHtml("<font color=" + accent + ">{ </font><font color=" + textWhite + ">Datos Locales</font><font color=" + accent + "> }</font>"));
                imgV.setImageDrawable(getResources().getDrawable(R.drawable.sqlite));
            }
            else if(getArguments().getInt(ARG_SECTION_NUMBER)==2) {
                textView.setText(Html.fromHtml("<font color="+accent+">{ </font><font color="+textWhite+">Datos Remotos</font><font color="+accent+"> }</font>"));
                imgV.setImageDrawable(getResources().getDrawable(R.drawable.mysql));
            }

            imgV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mViewPager.getCurrentItem() == 0) {
                        startActivity(new Intent(rootView.getContext(), LocaldatabaseActivity.class));
                    }

                    else if(mViewPager.getCurrentItem() == 1) {
                        startActivity(new Intent(rootView.getContext(), RemotedatabaseActivity.class));
                    }

                }
            });

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
            }
            return null;
        }
    }

    public void swipeToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.swipe_toast, (ViewGroup) findViewById(R.id.swipe_layout));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

}
