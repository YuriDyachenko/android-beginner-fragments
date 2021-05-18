package dyachenko.androidbeginnerfragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class NoteFragment extends Fragment {

    public static final String ARG_NOTE_INDEX = "ARG_NOTE_INDEX";
    public static final String ARG_IS_LANDSCAPE = "ARG_IS_LANDSCAPE";
    private int noteIndex;
    private TextView createdTextView;
    private final Calendar calendar = Calendar.getInstance();
    private boolean isLandscape;

    public NoteFragment() {
    }

    public static NoteFragment newInstance(int noteIndex, boolean isLandscape) {
        NoteFragment noteFragment = new NoteFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_NOTE_INDEX, noteIndex);
        bundle.putBoolean(ARG_IS_LANDSCAPE, isLandscape);
        noteFragment.setArguments(bundle);
        return noteFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            noteIndex = bundle.getInt(ARG_NOTE_INDEX);
            isLandscape = bundle.getBoolean(ARG_IS_LANDSCAPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*
            Для портретной ориентации подключем меню, чтобы спрятать его пункты.
            О программе и настройки будут только в списке вызываться.
         */
        setHasOptionsMenu(!isLandscape);

        View view = inflater.inflate(R.layout.fragment_note, container, false);

        if (noteIndex < Notes.NOTE_STORAGE.size()) {
            Note note = Notes.NOTE_STORAGE.get(noteIndex);

            ((TextView) view.findViewById(R.id.title_text_view)).setText(note.getTitle());
            ((TextView) view.findViewById(R.id.body_text_view)).setText(note.getBody());

            createdTextView = view.findViewById(R.id.created_text_view);
            createdTextView.setText(note.getCreatedString());

            view.findViewById(R.id.created_change_button)
                    .setOnClickListener(v -> changeNoteCreated());
        }

        return view;
    }

    private void changeNoteCreated() {
        Note note = Notes.NOTE_STORAGE.get(noteIndex);

        DatePickerDialog.OnDateSetListener listener = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            note.setCreated(calendar.getTime());
            createdTextView.setText(note.getCreatedString());
        };

        calendar.setTime(note.getCreated());
        new DatePickerDialog(requireContext(), listener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.findItem(R.id.action_about).setVisible(false);
        menu.findItem(R.id.action_settings).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }
}