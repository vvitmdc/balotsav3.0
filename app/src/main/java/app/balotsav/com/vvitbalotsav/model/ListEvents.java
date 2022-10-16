package app.balotsav.com.vvitbalotsav.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ListEvents implements Parcelable {
    ArrayList<Event> events;

    protected ListEvents(Parcel in) {
        events = in.createTypedArrayList(Event.CREATOR);
    }

    public static final Creator<ListEvents> CREATOR = new Creator<ListEvents>() {
        @Override
        public ListEvents createFromParcel(Parcel in) {
            return new ListEvents(in);
        }

        @Override
        public ListEvents[] newArray(int size) {
            return new ListEvents[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(events);
    }
}