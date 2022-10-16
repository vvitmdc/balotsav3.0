package app.balotsav.com.vvitbalotsav.model;

public class Announcements {
    private String notice;
    private String date;
    private String time;

    public Announcements() {
    }

    public Announcements(String notice, String date, String time) {
        this.notice = notice;
        this.time = time;
        this.date = date;
    }

    public String getNotice() {
        return notice;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

}
