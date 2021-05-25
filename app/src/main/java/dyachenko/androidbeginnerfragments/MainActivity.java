package dyachenko.androidbeginnerfragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        readSettings();
        fillNotes();

        FragmentManager fragmentManager = getSupportFragmentManager();

        /* Создаем и размещаем в контейнере фрагмент списка только
        при начальном запуске. */
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.notes_fragment_container, new NotesFragment())
                    .commit();
        }

        /* Если в портретной ориентации мы открыли конкретную заметку, то она поместилась в стек.
        Когда в этот момент мы переключаемся в альбомную ориентацию, где стек не нужен -
        нижеследующий код позволяет его почистить, и в альбомной ориентации кнопка бэк
        будет тогда закрывать приложение, как положено!
        Но!!! С некоторых пор может быть переключение в тот момент, когда выведен
        фрагмент о программе или настройки, их хотелось бы оставить!
        Поэтому после очистки стека добавляем сверху их, если они были до очистки. */
        if (fragmentManager.getBackStackEntryCount() != 0) {

            boolean hasAbout = false;
            boolean hasSettings = false;

            List<Fragment> fragments = fragmentManager.getFragments();
            for (Fragment fragment : fragments) {
                if (fragment instanceof AboutFragment) {
                    hasAbout = true;
                }
                if (fragment instanceof SettingsFragment) {
                    hasSettings = true;
                }
            }

            fragmentManager.popBackStackImmediate();

            if (hasAbout) {
                addAboutFragment(fragmentManager);
            }
            if (hasSettings) {
                addSettingsFragment(fragmentManager);
            }
        }

    }

    private void fillNotes() {
        Notes.fillFromXml(getResources().getXml(R.xml.notes));
    }

    private void readSettings() {
        SharedPreferences sharedPreferences = getSharedPreferences(Settings.PREFERENCE_NAME, Context.MODE_PRIVATE);
        Settings.editNoteViaPopupMenu = sharedPreferences.getBoolean(Settings.EDIT_NOTE_VIA_POPUP_MENU, false);
    }

    private void initView() {
        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);
    }

    private void initDrawer(Toolbar toolbar) {
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            if (navigateFragment(item.getItemId())) {
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            return false;
        });
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    private void addSettingsFragment(FragmentManager fragmentManager) {
        fragmentManager.beginTransaction()
                .add(R.id.global_fragment_container, new SettingsFragment())
                .addToBackStack(null)
                .commit();
    }

    private void addAboutFragment(FragmentManager fragmentManager) {
        fragmentManager.beginTransaction()
                .add(R.id.global_fragment_container, new AboutFragment())
                .addToBackStack(null)
                .commit();
    }

    private boolean navigateFragment(int id) {
        if (id == R.id.action_about) {
            addAboutFragment(getSupportFragmentManager());
            return true;
        }
        if (id == R.id.action_settings) {
            addSettingsFragment(getSupportFragmentManager());
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (navigateFragment(item.getItemId())) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void hideDrawer() {
        if (drawer != null) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            if (toggle != null) {
                toggle.setDrawerIndicatorEnabled(false);
            }
        }
    }

    public void showDrawer() {
        if (drawer != null) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            if (toggle != null) {
                toggle.setDrawerIndicatorEnabled(true);
            }
        }
    }

}