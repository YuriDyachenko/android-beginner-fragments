package dyachenko.androidbeginnerfragments;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Notes {
    private static final String tagNote = "note";
    public static final List<Note> NOTE_STORAGE = new ArrayList<>();

    public static int searchByPartOfTitle(String query) {
        for (int i = 0; i < NOTE_STORAGE.size(); i++) {
            if (NOTE_STORAGE.get(i).getTitle().toLowerCase().contains(query)) {
                return i;
            }
        }
        return -1;
    }

    public static void fillFromXml(XmlPullParser parser) {
        if (!NOTE_STORAGE.isEmpty()) {
            return;
        }
        try {
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG && parser.getName().equals(tagNote)) {
                    Note note = new Note();
                    note.setBody(parser.getAttributeValue(0));
                    note.setCreatedFromString(parser.getAttributeValue(1));
                    note.setTitle(parser.getAttributeValue(2));
                    NOTE_STORAGE.add(note);
                }
                parser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

}
