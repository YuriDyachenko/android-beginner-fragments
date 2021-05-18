package dyachenko.androidbeginnerfragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}