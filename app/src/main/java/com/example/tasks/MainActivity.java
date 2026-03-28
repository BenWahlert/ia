package com.example.tasks;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.tasks.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        DrawerLayout drawerLayout = binding.drawerLayout;
        drawerToggle = new ActionBarDrawerToggle(
            this,
            drawerLayout,
            binding.toolbar,
            R.string.nav_open,
            R.string.nav_close
        );
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_content_main);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }

        binding.navView.setNavigationItemSelectedListener(item -> {
            handleDrawerItem(item);
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        binding.fab.setOnClickListener(v ->
                navController.navigate(R.id.action_FirstFragment_to_SecondFragment)
        );

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.SecondFragment
                    || destination.getId() == R.id.AddSubjectFragment) {
                binding.fab.hide();
            } else {
                binding.fab.show();
            }
        });
    }

    private void handleDrawerItem(MenuItem item) {
        if (navController == null) {
            return;
        }

        int id = item.getItemId();
        if (id == R.id.nav_home) {
            if (navController.getCurrentDestination() == null
                    || navController.getCurrentDestination().getId() != R.id.FirstFragment) {
                navController.popBackStack(R.id.FirstFragment, false);
            }
        } else if (id == R.id.nav_archive) {
            if (navController.getCurrentDestination() == null
                    || navController.getCurrentDestination().getId() != R.id.ArchiveFragment) {
                navController.navigate(R.id.ArchiveFragment);
            }
        } else if (id == R.id.nav_add_subject) {
            if (navController.getCurrentDestination() == null
                    || navController.getCurrentDestination().getId() != R.id.AddSubjectFragment) {
                navController.navigate(R.id.AddSubjectFragment);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
