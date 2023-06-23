package com.example.oday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.oday.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.iammert.library.readablebottombar.ReadableBottomBar;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth auth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding=ActivityMainBinding.inflate( getLayoutInflater() );
        setContentView( binding.getRoot() );

        setSupportActionBar( binding.toolbar );
        MainActivity.this.setTitle( "My Profile" );

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        binding.toolbar.setVisibility( View.GONE );
        transaction.replace( R.id.container,new Fragment() );
        transaction.commit();

        binding.readableBottomBar.setOnItemSelectListener( new ReadableBottomBar.ItemSelectListener() {
            @Override
            public void onItemSelected(int i) {

                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();


                switch (i){
                    case 0:
                        binding.toolbar.setVisibility( View.GONE );
                        transaction.replace( R.id.container,new HomeFragment() );
                        break;

                        case 1:
                            binding.toolbar.setVisibility( View.GONE );
                            transaction.replace( R.id.container,new NotificationFragment() );
                        break;

                        case 2:
                            binding.toolbar.setVisibility( View.GONE );
                            transaction.replace( R.id.container,new AddPostFragment() );
                        break;

                        case 3:
                            binding.toolbar.setVisibility( View.GONE );
                            transaction.replace( R.id.container,new SearchFragment() );
                        break;

                    case 4:
                        binding.toolbar.setVisibility( View.VISIBLE );
                        transaction.replace( R.id.container,new ProfileFragment() );
                        break;
                }
                transaction.commit();

            }
        } );
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate( R.menu.menu_iteam,menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (item.getItemId() == R.id.setting) {
                auth.signOut();
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity( intent );
        }
        return  super.onOptionsItemSelected( item );
    }
}