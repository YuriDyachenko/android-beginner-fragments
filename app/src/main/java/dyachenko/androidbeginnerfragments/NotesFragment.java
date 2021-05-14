package dyachenko.androidbeginnerfragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NotesFragment extends Fragment {

    private static final String EXTRA_POSITION = "EXTRA_POSITION";
    private final List<Note> notes = new ArrayList<>();
    private int position;
    private boolean isLandscape;

    public NotesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fillNotes();
        initList(view);
    }

    private void initList(View view) {
        LinearLayout layout = (LinearLayout) view;
        Context context = getContext();
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            TextView textView = new TextView(context);
            textView.setText(note.getNumberedTitle(i));
            textView.setTextSize(24);
            textView.setPadding(20, 20, 20, 20);
            textView.setTextColor(getResources().getColor(R.color.teal_700, Objects.requireNonNull(context).getTheme()));

            final int index = i;
            textView.setOnClickListener(v -> {
                position = index;
                showNoteDetails(note);
            });

            layout.addView(textView);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXTRA_POSITION, position);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            position = savedInstanceState.getInt(EXTRA_POSITION);
        }

        if (isLandscape && notes.size() > position) {
            showLandNoteDetails(notes.get(position));
        }
    }

    private void showLandNoteDetails(Note note) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.note_fragment_container, NoteFragment.newInstance(note))
                .commit();
    }

    private void showPortNoteDetails(Note note) {
        Intent intent = new Intent(getContext(), NoteActivity.class);
        intent.putExtra(NoteFragment.ARG_NOTE, note);
        startActivity(intent);
    }

    private void showNoteDetails(Note note) {
        if (isLandscape) {
            showLandNoteDetails(note);
        } else {
            showPortNoteDetails(note);
        }
    }

    private void fillNotes() {
        XmlPullParser parser = getResources().getXml(R.xml.notes);
        Note note = null;
        boolean inEntry = false;
        String textValue = "";

        try {
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("note".equalsIgnoreCase(tagName)) {
                            inEntry = true;
                            note = new Note();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (inEntry) {
                            if ("note".equalsIgnoreCase(tagName)) {
                                notes.add(note);
                                inEntry = false;
                            } else if ("title".equalsIgnoreCase(tagName)) {
                                note.setTitle(textValue);
                            } else if ("body".equalsIgnoreCase(tagName)) {
                                note.setBody(textValue);
                            } else if ("created".equalsIgnoreCase(tagName)) {
                                note.setCreatedFromString(textValue);
                            }
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }
}