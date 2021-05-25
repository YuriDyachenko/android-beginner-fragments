package dyachenko.androidbeginnerfragments;

import android.view.Menu;

import androidx.fragment.app.Fragment;

public class CommonFragment extends Fragment {

    protected void hideAllMenuItems(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setVisible(false);
        }
    }
}
