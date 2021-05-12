package dyachenko.androidbeginnerfragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Note {
    private String title;
    private String body;
    private Date created;

    public Note() {
    }

    public Note(String title, String body, Date created) {
        this.title = title;
        this.body = body;
        this.created = created;
    }

    public String getTitle() {
        return title;
    }

    public String getNumberedTitle(int index) {
        return String.valueOf(index + 1) + ". " + getTitle();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        String[] strings = body.split("\n");
        StringBuilder stringBuilder = new StringBuilder();
        for (String str: strings) {
            stringBuilder.append(str.trim())
                    .append("\n");
        }
        this.body = stringBuilder.toString().trim();
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setCreated(String stringDate) {
        try {
            this.created = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
