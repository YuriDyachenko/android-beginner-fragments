package dyachenko.androidbeginnerfragments;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Note implements Serializable {
    private String title;
    private String body;
    private Date created;

    public Note() {
    }

    public String getTitle() {
        return title;
    }

    public String getNumberedTitle(int index) {
        return (index + 1) + ". " + getTitle();
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
        for (String str : strings) {
            stringBuilder.append(str.trim())
                    .append("\n");
        }
        this.body = stringBuilder.toString().trim();
    }

    public Date getCreated() {
        return created;
    }

    public String getCreatedString() {
        return "created: " + new SimpleDateFormat("dd.MM.yyyy", Locale.US).format(getCreated());
    }

    public void setCreatedFromString(String stringDate) {
        try {
            this.created = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
