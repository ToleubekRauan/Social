package com.example.rauan.social;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.GraphRequest;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MainActivity extends AppCompatActivity implements ProfileListener {
    private Drawer drawer;
    private GraphRequest graphRequest;
    UserProfile userProfile;
//    private interface ProfileListener{
//        void showProfileInfo(UserProfile userProfile);
//    }
    ProfileListener profileListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileListener = this;





    }

    private void createDrawer(UserProfile userProfile) {
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName(userProfile.getName()).withEmail(userProfile.getEmail()).withIcon(getResources().getDrawable(R.mipmap.ic_launcher_round))
                ).build();

        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("News");
        drawer = new DrawerBuilder().withActivity(this).withAccountHeader(headerResult)
                .addDrawerItems(item1).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if(drawerItem.getIdentifier() == 1){
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new NewsFragment()).commit();
                        }
                        return false;
                    }
                }).build();
    }

    @Override
    public void setProfileInterface(UserProfile userProfile) {
        createDrawer(userProfile);
    }
}
