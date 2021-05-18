package dyachenko.androidbeginnerfragments;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        FragmentManager fragmentManager = getSupportFragmentManager();

        /*
            Если в портретной ориентации мы открыли конкретную заметку, то она поместилась в стек.
            Когда в этот момент мы переключаемся в альбомную ориентацию, где стек не нужен -
            нижеследующий код позволяет его почистить, и в альбомной ориентации кнопка бэк
            будет тогда закрывать приложение, как положено!
         */
        if (fragmentManager.getBackStackEntryCount() != 0) {
            fragmentManager.popBackStackImmediate();
        }

        /*
            Создаем и размещаем в контейнере фрагмент списка только при начальном запуске.
         */
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.notes_fragment_container, new NotesFragment())
                    .commit();
        }
    }

    private void initView() {
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Toast.makeText(this, R.string.action_settings, Toast.LENGTH_SHORT).show();
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

}