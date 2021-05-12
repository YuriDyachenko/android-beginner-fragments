package dyachenko.androidbeginnerfragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment {

    private final List<Note> notes = new ArrayList<>();
    private int position;

    public NotesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
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

            final int index = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    position = index;
                }
            });

            layout.addView(textView);
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
                                note.setCreated(textValue);
                            }
                        }
                        break;
                    default:
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }
}