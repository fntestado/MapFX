package meetup.app.entities;

public class Event implements Comparable<Event> {
    private String eventName;
    private String time;
    private String location;
    private String detail;
    private String date;

    public Event() {
        eventName = "";
    }

    public Event(String eventName) {
        this.eventName = eventName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEventName() {
        return eventName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int compareTo(Event event) {
        return eventName.compareTo(event.eventName);
    }

    @Override
    public String toString() {
        return eventName;
    }
}
