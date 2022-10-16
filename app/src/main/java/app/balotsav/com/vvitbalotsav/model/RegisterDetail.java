package app.balotsav.com.vvitbalotsav.model;

public class RegisterDetail {
    public String getEventName() {
        return EventName;
    }

    String EventName;
    int JnCount, SubCount, SrCount, team;

    public RegisterDetail() {
    }

    public int getJnCount() {
        return JnCount;
    }

    public void setJnCount(int jnCount) {
        JnCount = jnCount;
    }

    public int getSubCount() {
        return SubCount;

    }

    public void setSubCount(int subCount) {
        SubCount = subCount;
    }

    public int getSrCount() {
        return SrCount;
    }

    public void setSrCount(int srCount) {
        SrCount = srCount;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public RegisterDetail(String eventName, int subjnrBoyCount, int jnBoyCount, int srBoyCount) {
        EventName = eventName;
        JnCount = jnBoyCount;
        SubCount = subjnrBoyCount;
        SrCount = srBoyCount;
        team = -99;

    }

    public RegisterDetail(String eventName, int j, int sj, int s, int team) {
        EventName = eventName;
        this.team = team;
        JnCount = -99;
        SubCount = -99;
        SrCount = -99;
    }

    public RegisterDetail(String eventName, int jnCount, int srCount) {
        EventName = eventName;
        JnCount = jnCount;
        SrCount = srCount;
        SubCount = -99;
        team = -99;
    }

    public RegisterDetail(String eventName, int srCount) {
        EventName = eventName;
        SrCount = srCount;
        JnCount = -99;
        SubCount = -99;
        team = -99;
    }

    public RegisterDetail(String eventName, int subjCount, int srcount, int x, int y, int z) {
        EventName = eventName;
        SrCount = -99;
        JnCount = -99;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }
}
