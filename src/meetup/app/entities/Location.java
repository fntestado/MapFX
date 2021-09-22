package meetup.app.entities;

import datastructures.linkedlist.LinkedList;
import datastructures.tree.Tree;

import java.lang.Comparable;

public class Location implements Comparable<Location> {
    private String locationName;
    private Tree<Event> events = new Tree<>();
    private int x_axis;
    private int y_axis;
    private int key;

    public Location() {
        locationName = "";
    }

    public Location(String locationName) {
        this.locationName = locationName;
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void addEvent(Tree<Event> eventTree) {
        events = eventTree;
    }

    public Tree<Event> getEvents() {
        return events;
    }

    public LinkedList<Event> eventList() {
        return events.toLinkedList();
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getX_axis() {
        return x_axis;
    }

    public void setX_axis(int x_axis) {
        this.x_axis = x_axis;
    }

    public int getY_axis() {
        return y_axis;
    }

    public void setY_axis(int y_axis) {
        this.y_axis = y_axis;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    @Override
    public int compareTo(Location o) {
        return this.locationName.compareTo(o.locationName);
    }
}
