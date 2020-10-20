package com.sashashtmv.myshop;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sashashtmv.myshop.adapter.GoodsListAdapter;
import com.sashashtmv.myshop.fragment.AboutFragment;
import com.sashashtmv.myshop.fragment.FeedbackFragment;
import com.sashashtmv.myshop.fragment.HomeFragment;
import com.sashashtmv.myshop.fragment.ListGoodsFragment;
import com.sashashtmv.myshop.fragment.ProfileFragment;
import com.sashashtmv.myshop.model.ModelGoods;

import java.util.ArrayList;
import java.util.List;


public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.Callbacks {
    private DrawerLayout drawer;
    private List<ModelGoods> list;
    private FragmentManager mFragmentManager;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private GoodsListAdapter mAdapter;
    private String mTitle;

    ListGoodsFragment listGoodsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        list = new ArrayList<>();
        mFragmentManager = getSupportFragmentManager();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                list.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ModelGoods upload = postSnapshot.getValue(ModelGoods.class);
                    upload.setKey(postSnapshot.getKey());
                    list.add(upload);
                }
                if (mAdapter != null) {
                    mAdapter.sortList(mTitle, list);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new HomeFragment()).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new HomeFragment()).commit();
                break;

            case R.id.nav_my_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new ProfileFragment()).addToBackStack(null).commit();
                break;

            case R.id.nav_favorites:
                onCreateAllGoodsFragment("Избранные");
                break;

            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;

            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new AboutFragment()).addToBackStack(null).commit();
                break;

            case R.id.nav_feedback:
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new FeedbackFragment()).commit();
                break;


        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onCreateAllGoodsFragment(String title) {
        mAdapter = new GoodsListAdapter(this, list);
        mTitle = title;
        listGoodsFragment = ListGoodsFragment.newInstance(title, list, mAdapter);
        mFragmentManager.beginTransaction()
                .replace(R.id.frag_container, listGoodsFragment, "allGoods")
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

