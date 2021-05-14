package dyachenko.androidbeginnerfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class NoteFragment extends Fragment {

    public static final String ARG_NOTE = "ARG_NOTE";
    private Note note;

    public NoteFragment() {
    }

    public static NoteFragment newInstance(Note note) {
        NoteFragment noteFragment = new NoteFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_NOTE, note);
        noteFragment.setArguments(bundle);
        return noteFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            note = (Note) bundle.getSerializable(ARG_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        ((TextView) view.findViewById(R.id.title_text_view)).setText(note.getTitle());
        ((TextView) view.findViewById(R.id.created_text_view)).setText(note.getCreatedString());
        ((TextView) view.findViewById(R.id.body_text_view)).setText(note.getBody());
        return view;
    }
}