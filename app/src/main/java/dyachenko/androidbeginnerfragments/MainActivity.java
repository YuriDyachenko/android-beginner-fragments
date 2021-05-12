package dyachenko.androidbeginnerfragments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fillNotes();
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