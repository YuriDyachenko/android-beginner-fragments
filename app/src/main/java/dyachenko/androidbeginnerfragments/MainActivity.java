package dyachenko.androidbeginnerfragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        readSettings();

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
            for (Fragment fragment: fragments) {
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

    private void readSettings() {
        SharedPreferences sharedPreferences = getSharedPreferences(Settings.PREFERENCE_NAME, Context.MODE_PRIVATE);
        Settings.editNoteViaPopupMenu = sharedPreferences.getBoolean(Settings.EDIT_NOTE_VIA_POPUP_MENU, false);
    }

    private void initView() {
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_about) {
            addAboutFragment(getSupportFragmentManager());
            return true;
        }
        if (item.getItemId() == R.id.action_settings) {
            addSettingsFragment(getSupportFragmentManager());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

}