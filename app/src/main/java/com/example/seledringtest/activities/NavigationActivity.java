package com.example.seledringtest.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.seledringtest.R;
import com.example.seledringtest.fragments.HomeFragment;
import com.example.seledringtest.fragments.MemoryFragment;
import com.example.seledringtest.utilities.GeneralUtilis;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (GeneralUtilis.getSharedPreferences(this).getBoolean("night_mode",false)){
            toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.light_gray));
            toolbar.setBackgroundColor(getResources().getColor(R.color.full_drk));
        }else {
            toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.full_drk));
            toolbar.setBackgroundColor(getResources().getColor(R.color.light_gray));
        }



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

       /* //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            GeneralUtilis.putValueInEditor(this).putBoolean("refresh", true).commit();
            GeneralUtilis.withOutBackStackConnectFragment(this, new HomeFragment());
        } else if (id == R.id.nav_memorie) {
            GeneralUtilis.putValueInEditor(this).putBoolean("refresh", true).commit();
            GeneralUtilis.withOutBackStackConnectFragment(this, new MemoryFragment());
        } else if (id == R.id.nav_informazioni) {
            GeneralUtilis.putValueInEditor(this).putBoolean("refresh", true).commit();

        } else if (id == R.id.nav_impostazioni) {
            GeneralUtilis.putValueInEditor(this).putBoolean("refresh", true).commit();

        } else if (id == R.id.nav_contatti) {
            GeneralUtilis.putValueInEditor(this).putBoolean("refresh", true).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
