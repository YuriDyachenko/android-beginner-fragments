package dyachenko.androidbeginnerfragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;

public class SettingsFragment extends CommonFragment {

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        CheckBox checkBox = (CheckBox) view.findViewById(R.id.edit_note_via_popup_checkbox);
        checkBox.setChecked(Settings.editNoteViaPopupMenu);

        view.findViewById(R.id.settings_apply_button).setOnClickListener(v -> {
            Settings.editNoteViaPopupMenu = checkBox.isChecked();
            writeSettings();
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }

    private void writeSettings() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(Settings.PREFERENCE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putBoolean(Settings.EDIT_NOTE_VIA_POPUP_MENU, Settings.editNoteViaPopupMenu)
                .apply();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        hideAllMenuItems(menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}