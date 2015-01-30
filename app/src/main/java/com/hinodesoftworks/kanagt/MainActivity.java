package com.hinodesoftworks.kanagt;

import android.app.Fragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.hinodesoftworks.kanagt.util.DatabaseManager;
import com.hinodesoftworks.kanagt.util.NavMenuListAdapter;
import com.hinodesoftworks.kanagt.util.QuizManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class MainActivity extends ActionBarActivity implements
        HomeFragment.OnHomeFragmentInteractionListener, HiraganaListFragment.OnHiraListInteractionListener,
        NavDrawerFragment.OnNavMenuInteractionListener, KatakanaListFragment.OnKataListLoadedListener,
        HiraganaChartFragment.OnHiraganaChartFragmentListener, KatakanaChartFragment.OnKatakanaChartFragmentListener,
        QuizSetupFragment.OnQuizSetupListener
{

    //nav drawer variables
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private FragmentManager mFragmentManager;
    private DatabaseManager mDatabaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getFragmentManager();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //init 3rd party image loader library for easier asset management
        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(this)
                .build();

        ImageLoader.getInstance().init(imageLoaderConfiguration);

        //init database
        mDatabaseManager = new DatabaseManager(this);
        Cursor c = mDatabaseManager.getAllHira();

        HomeFragment testFrag = new HomeFragment();
        changeViewFragment(testFrag);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }


    //navigation methods


    //utility methods
    private void changeViewFragment(Fragment fragToShow){
        mFragmentManager.beginTransaction()
                .replace(R.id.content_view, fragToShow)
                .commit();
    }

    //nav drawer
    @Override
    public void onNavItemSelected(NavMenuListAdapter.NavLocation location) {
        Log.i("Location String", location.toString());
        switch (location){
            case LOC_HOME:
                changeViewFragment(new HomeFragment());
                break;
            case LOC_HIRA_LIST:
                changeViewFragment(new HiraganaListFragment());
                break;
            case LOC_HIRA_CHART:
                HiraganaChartFragment hchart = new HiraganaChartFragment();
                hchart.setContext(this);
                changeViewFragment(hchart);
                break;
            case LOC_HIRA_P_QUIZ:
                QuizSetupFragment hpSetup = new QuizSetupFragment();
                hpSetup.setQuizMode(QuizManager.QuizMode.MODE_HIRA_P_QUIZ);
                changeViewFragment(hpSetup);
                break;
            case LOC_HIRA_R_QUIZ:
                QuizSetupFragment hrSetup = new QuizSetupFragment();
                hrSetup.setQuizMode(QuizManager.QuizMode.MODE_HIRA_R_QUIZ);
                changeViewFragment(hrSetup);
                break;
            case LOC_KATA_LIST:
                changeViewFragment(new KatakanaListFragment());
                break;
            case LOC_KATA_CHART:
                KatakanaChartFragment kchart = new KatakanaChartFragment();
                kchart.setContext(this);
                changeViewFragment(kchart);
                break;
            case LOC_KATA_P_QUIZ:
                QuizSetupFragment kpSetup = new QuizSetupFragment();
                kpSetup.setQuizMode(QuizManager.QuizMode.MODE_KATA_P_QUIZ);
                changeViewFragment(kpSetup);
                break;
            case LOC_KATA_R_QUIZ:
                QuizSetupFragment krSetup = new QuizSetupFragment();
                krSetup.setQuizMode(QuizManager.QuizMode.MODE_KATA_R_QUIZ);
                changeViewFragment(krSetup);
                break;
            case LOC_STATS:
                changeViewFragment(new StatsFragment());
                break;
            case LOC_SETTINGS:
                changeViewFragment(new PrefsFragment());
                break;
        }
        mDrawerLayout.closeDrawers();
    }

    //home fragment
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    //hira list
    @Override
    public void onHiraListFragmentLoaded(HiraganaListFragment sender) {
        sender.displayHiraList(mDatabaseManager.getAllHira());
    }

    //kata list

    @Override
    public void onKataListLoaded(KatakanaListFragment sender) {
        sender.displayKataList(mDatabaseManager.getAllKata());
    }

    //hira chart

    @Override
    public void onHiraganaChartLoaded(HiraganaChartFragment sender) {
        sender.showHiraChart(mDatabaseManager.getAllHira());
    }

    @Override
    public void onHiraganaChartItemSelected() {

    }

    //kata chart

    @Override
    public void onKatakanaChartLoaded(KatakanaChartFragment sender) {
        sender.showKataChart(mDatabaseManager.getAllKata());
    }

    //quiz setup fragment

    @Override
    public void onQuizStartButtonPressed() {

    }
}
