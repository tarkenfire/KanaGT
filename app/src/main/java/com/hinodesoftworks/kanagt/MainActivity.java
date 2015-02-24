package com.hinodesoftworks.kanagt;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hinodesoftworks.kanagt.util.DatabaseManager;
import com.hinodesoftworks.kanagt.util.NavMenuListAdapter;
import com.hinodesoftworks.kanagt.util.QuizManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MainActivity extends ActionBarActivity implements
        HomeFragment.OnHomeFragmentInteractionListener, HiraganaListFragment.OnHiraListInteractionListener,
        NavDrawerFragment.OnNavMenuInteractionListener, KatakanaListFragment.OnKataListLoadedListener,
        HiraganaChartFragment.OnHiraganaChartFragmentListener, KatakanaChartFragment.OnKatakanaChartFragmentListener,
        QuizSetupFragment.OnQuizSetupListener, StatsFragment.OnStatsFragmentLoadedListener,
        PrefsFragment.PreferenceListener
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
        toolbar.setTitleTextColor(Color.WHITE);
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

    //utility methods
    private void changeViewFragment(Fragment fragToShow){
        mFragmentManager.beginTransaction()
                .replace(R.id.content_view, fragToShow)
                .commit();
    }

    private void changeActionBarTitle(int resId){
        getSupportActionBar().setTitle(resId);
    }

    //navigation methods
    //nav drawer
    @Override
    public void onNavItemSelected(NavMenuListAdapter.NavLocation location) {
        switch (location){
            case LOC_HOME:
                changeViewFragment(new HomeFragment());
                changeActionBarTitle(R.string.app_name);
                break;
            case LOC_HIRA_LIST:
                changeViewFragment(new HiraganaListFragment());
                changeActionBarTitle(R.string.title_fragment_hira_list);
                break;
            case LOC_HIRA_CHART:
                HiraganaChartFragment hchart = new HiraganaChartFragment();
                hchart.setContext(this);
                changeViewFragment(hchart);
                changeActionBarTitle(R.string.title_fragment_hira_chart);
                break;
            case LOC_HIRA_P_QUIZ:
                QuizSetupFragment hpSetup = new QuizSetupFragment();
                hpSetup.setQuizMode(QuizManager.QuizMode.MODE_HIRA_P_QUIZ);
                changeViewFragment(hpSetup);
                changeActionBarTitle(R.string.title_fragment_quiz_setup);
                break;
            case LOC_HIRA_R_QUIZ:
                QuizSetupFragment hrSetup = new QuizSetupFragment();
                hrSetup.setQuizMode(QuizManager.QuizMode.MODE_HIRA_R_QUIZ);
                changeViewFragment(hrSetup);
                changeActionBarTitle(R.string.title_fragment_quiz_setup);
                break;
            case LOC_KATA_LIST:
                changeViewFragment(new KatakanaListFragment());
                changeActionBarTitle(R.string.title_fragment_kata_list);
                break;
            case LOC_KATA_CHART:
                KatakanaChartFragment kchart = new KatakanaChartFragment();
                kchart.setContext(this);
                changeViewFragment(kchart);
                changeActionBarTitle(R.string.title_fragment_kata_chart);
                break;
            case LOC_KATA_P_QUIZ:
                QuizSetupFragment kpSetup = new QuizSetupFragment();
                kpSetup.setQuizMode(QuizManager.QuizMode.MODE_KATA_P_QUIZ);
                changeViewFragment(kpSetup);
                changeActionBarTitle(R.string.title_fragment_quiz_setup);
                break;
            case LOC_KATA_R_QUIZ:
                QuizSetupFragment krSetup = new QuizSetupFragment();
                krSetup.setQuizMode(QuizManager.QuizMode.MODE_KATA_R_QUIZ);
                changeViewFragment(krSetup);
                changeActionBarTitle(R.string.title_fragment_quiz_setup);
                break;
            case LOC_STATS:
                changeViewFragment(new StatsFragment());
                changeActionBarTitle(R.string.title_fragment_stats);
                break;
            case LOC_SETTINGS:
                changeViewFragment(new PrefsFragment());
                changeActionBarTitle(R.string.title_settings);
                break;
        }
        mDrawerLayout.closeDrawers();
    }

    //fragment interfaces
    //home fragment
    @Override
    public void onQuickLinkPressed(View v) {
        switch (v.getId()){
            case R.id.welcome_hira_list_button:
                onNavItemSelected(NavMenuListAdapter.NavLocation.LOC_HIRA_LIST);
                break;
            case R.id.welcome_hira_chart_button:
                onNavItemSelected(NavMenuListAdapter.NavLocation.LOC_HIRA_CHART);
                break;
            case R.id.welcome_hira_quiz_button:
                onNavItemSelected(NavMenuListAdapter.NavLocation.LOC_HIRA_P_QUIZ);
                break;
            case R.id.welcome_kata_list_button:
                onNavItemSelected(NavMenuListAdapter.NavLocation.LOC_KATA_LIST);
                break;
            case R.id.welcome_kata_chart_button:
                onNavItemSelected(NavMenuListAdapter.NavLocation.LOC_KATA_CHART);
                break;
            case R.id.welcome_kata_quiz_button:
                onNavItemSelected(NavMenuListAdapter.NavLocation.LOC_KATA_P_QUIZ);
                break;
        }
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
    public void onHiraganaChartItemSelected(HiraganaChartFragment sender, String kana) {
        Cursor data = mDatabaseManager.getKanaDetails("hiragana", kana);
        //todo create constants to represent row numbers
        sender.showDetailDialog(data.getString(0), data.getString(1), data.getInt(2),
                                data.getInt(3), data.getInt(4), data.getString(5));

        data.close();
    }

    //kata chart

    @Override
    public void onKatakanaChartLoaded(KatakanaChartFragment sender) {
        sender.showKataChart(mDatabaseManager.getAllKata());
    }

    @Override
    public void onKatakanaChartItemSelected(KatakanaChartFragment sender, String kana){
        Cursor data = mDatabaseManager.getKanaDetails("katakana", kana);
        //todo create constants to represent row numbers
        sender.showDetailDialog(data.getString(0), data.getString(1), data.getInt(2),
                data.getInt(3), data.getInt(4), data.getString(5));

        data.close();
    }

    //quiz setup fragment

    @Override
    public void onQuizStartButtonPressed(QuizManager.QuizMode mode, int numOfQuestions,
                                         int setChoice) {
        Intent sender = new Intent(this, QuizActivity.class);

        sender.putExtra("mode", mode);
        sender.putExtra("num_of_questions", numOfQuestions);
        sender.putExtra("set_choice", setChoice);
        startActivity(sender);
    }

    //stats fragment

    @Override
    public void onStatsFragmentLoaded(StatsFragment sender) {
        Cursor hira = mDatabaseManager.getCharacterStats("hiragana");
        Cursor kata = mDatabaseManager.getCharacterStats("katakana");
        Cursor quiz = mDatabaseManager.getAllQuizResults();

        sender.updateStats(hira, kata, quiz);
    }

    public void onHistoryButtonPressed(StatsFragment sender){
        sender.showHistoryDialog(mDatabaseManager.getAllQuizResults());
    }

    //prefs fragment
    @Override
    public void onDatabaseDeleteClicked() {
        mDatabaseManager.resetDatabases();
    }

    @Override
    public void showLicense() {
        Intent i = new Intent(this, CreditsActivity.class);
        startActivity(i);
    }
}
