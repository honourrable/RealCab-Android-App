package tr.yildiz.edu.realcab;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Event implements Parcelable {

    private String eventName;
    private String eventType;
    private String eventDate;
    private String eventLocation;
    private ArrayList<Clothe> eventClothes;

    public Event(){
        this.eventClothes = new ArrayList<>();
    }

    public Event(String eventName, String eventType, String eventDate, String eventLocation) {
        this.eventName = eventName;
        this.eventType = eventType;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
        this.eventClothes = new ArrayList<>();
    }

    public static ArrayList<Event> getEvents(){

        ArrayList<Event> events = new ArrayList<>();
        Event event = new Event("2022 Show", "Catwalk", "2022/02/10", "Berlin");
        event.setEventClothes( new ArrayList<>(Clothe.getClothes().subList(0,3)));
        events.add(event);

        events.add(new Event("Zara", "Catwalk", "2021/08/01", "Melbourne"));
        events.add(new Event("Grand S", "Catwalk", "2021/10/06", "Paris"));
        events.add(new Event("Selena", "Dance", "2021/07/25", "Istanbul"));
        events.add(new Event("BeyondBeauty", "Catwalk", "2021/12/15", "Mexico City"));

        return events;
    }

    protected Event(Parcel in) {
        eventName = in.readString();
        eventType = in.readString();
        eventDate = in.readString();
        eventLocation = in.readString();
        eventClothes = in.createTypedArrayList(Clothe.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(eventName);
        dest.writeString(eventType);
        dest.writeString(eventDate);
        dest.writeString(eventLocation);
        dest.writeTypedList(eventClothes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };



    public void setEventClothes(ArrayList<Clothe> eventClothes) {
        this.eventClothes = eventClothes;
    }

    public ArrayList<Clothe> getEventClothes() {
        return eventClothes;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }
}
