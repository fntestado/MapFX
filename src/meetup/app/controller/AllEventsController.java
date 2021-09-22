package meetup.app.controller;

import datastructures.graph.Graph;
import datastructures.stack.Stack;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import meetup.app.entities.Event;
import meetup.app.entities.Location;

import javax.swing.*;
import java.io.IOException;

public class AllEventsController {
    @FXML
    public ListView<String> event_listview;
    @FXML
    private Label edit_event_label;
    @FXML
    private Label delete_event_label;
    @FXML
    private Label event_name_label;
    @FXML
    private Label location_name_label;
    @FXML
    private Label detail_label;
    @FXML
    private Label undo_label;
    @FXML
    private Label redo_label;
    @FXML
    private TitledPane titledpane;

    public static String am_pm;
    public static int counter;
    public static Stack<Event> eventUndoStack = new Stack<>();
    public static Stack<Event> eventRedoStack = new Stack<>();
    public static Graph<Location> locationGraph = new Graph<>();
    public static Location location = new Location();

    @FXML
    private void initialize() throws IOException {
        if (!eventUndoStack.isEmpty())
            undo_label.setDisable(false);
        else
            undo_label.setDisable(true);
        if (!eventRedoStack.isEmpty())
            redo_label.setDisable(false);
        else
            redo_label.setDisable(true);

        delete_event_label.setDisable(true);
        edit_event_label.setDisable(true);
        ObservableList<String> events = FXCollections.observableArrayList();
        for (int i = 0; i <= EventController.eventList.size() && EventController.eventList.get(i) != null; i++) {
            events.add(EventController.eventList.get(i).getEventName());
        }
        if (EventController.eventList.size() != 0) {
            event_listview.setItems(events);

            event_name_label.setText(EventController.eventList.get(0).getEventName());
            location_name_label.setText(EventController.eventList.get(0).getLocation());
            detail_label.setText(EventController.eventList.get(0).getDetail());
            titledpane.setText(EventController.eventList.get(0).getDate() + ", " + EventController.eventList.get(0).getTime());
        } else {
            Main.primaryStage.setHeight(516);
            Main.primaryStage.setWidth(711);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/events_view.fxml"));
            BorderPane loadSecond = loader.load();
            Main.mainLayout.getChildren().setAll(loadSecond);
        }
    }

    @FXML
    private void listClicked(MouseEvent event) {
        String item = event_listview.getSelectionModel().getSelectedItem();
        EventController.clickedInformation = item;
        try {
            for (int i = 0; i <= EventController.eventList.size(); i++) {
                if (EventController.eventList.get(i).getEventName().trim().toUpperCase().equals(item.trim().toUpperCase())) {
                    event_name_label.setText(item);
                    location_name_label.setText(EventController.eventList.get(i).getLocation());
                    detail_label.setText(EventController.eventList.get(i).getDetail());
                    titledpane.setText(EventController.eventList.get(i).getDate() + ", " + EventController.eventList.get(i).getTime());
                    delete_event_label.setDisable(false);
                    edit_event_label.setDisable(false);
                    return;
                }
            }
        } catch (NullPointerException e) {

        }
    }

    @FXML
    private void titledPaneClicked() throws IOException {
        EventController.clickedInformation = event_name_label.getText();
        Main.primaryStage.setWidth(600);
        Main.primaryStage.setHeight(440);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/searchresult_view.fxml"));
        BorderPane showSearchResult = loader.load();
        Main.mainLayout.getChildren().setAll(showSearchResult);
    }

    @FXML
    private void editEventClicked() throws IOException {
        Main.primaryStage.setHeight(426);
        Main.primaryStage.setWidth(561);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/edit_event_view.fxml"));
        BorderPane load = loader.load();
        Main.mainLayout.getChildren().setAll(load);
    }

    @FXML
    public void createEventClicked() throws IOException {
        Main.primaryStage.setHeight(426);
        Main.primaryStage.setWidth(561);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/create_new_event_view.fxml"));
        BorderPane load = loader.load();
        Main.mainLayout.getChildren().setAll(load);
    }

    @FXML
    private void deleteEventClicked(MouseEvent event) throws IOException {
        String item = event_listview.getSelectionModel().getSelectedItem();
        for (int i = 0; i <= EventController.eventList.size(); i++) {
            if (EventController.eventList.get(i).getEventName().trim().toUpperCase().equals(item.trim().toUpperCase())) {
                String[] buttons = {"Yes", "No"};
                int input = JOptionPane.showOptionDialog(null, "Are you sure you want to delete " + '"' + item + '"'
                                + "?", "Confirmation",
                        JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[1]);
                if (input == 0) {
                    counter = -1;
                    eventRedoStack = new Stack<>();
                    eventUndoStack = new Stack<>();
                    AllEventsController.eventUndoStack.push(EventController.eventList.get(i));
                    EventController.eventList.remove(EventController.eventList.get(i));
/*
                    if (MapController.locationTree.get(new Location(EventController.eventList.get(i).getLocation())).getEvents().toLinkedList().size() == 0) {
                        MapController.locationGraph.removeVertex(MapController.locationTree.get(new Location(MapController.currentLocation)));
                    } else {
                        MapController.locationTree.get(new Location(EventController.eventList.get(i).getLocation())).getEvents().delete(EventController.eventList.get(i));
                    }*/
                    initialize();
                }
                return;
            }
        }
    }

    @FXML
    private void homeClicked() throws IOException {
        Main.primaryStage.setHeight(516);
        Main.primaryStage.setWidth(711);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/events_view.fxml"));
        BorderPane loadSecond = loader.load();
        Main.mainLayout.getChildren().setAll(loadSecond);
    }

    @FXML
    private void undoClicked() throws IOException {
        if (!eventUndoStack.isEmpty()) {
            undo_label.setDisable(false);
            Event popResult = eventUndoStack.pop();
            eventRedoStack.push(popResult);
            if (counter == 0) {
                EventController.eventList.remove(popResult);
            } else {
                EventController.eventList.addFirst(popResult);
            }
            initialize();
        } else {
            undo_label.setDisable(true);
        }
    }

    @FXML
    private void redoClicked() throws IOException {
        if (!eventRedoStack.isEmpty()) {
            redo_label.setDisable(false);
            Event popResult = eventRedoStack.pop();
            eventUndoStack.push(popResult);
            if (counter == 0) {
                EventController.eventList.addFirst(popResult);
            } else {
                EventController.eventList.remove(popResult);
            }
            initialize();
        } else {
            redo_label.setDisable(true);
        }
    }

    @FXML
    private void onMouseEntered() {
        Main.primaryStage.getScene().setCursor(Cursor.HAND);
    }

    @FXML
    private void onMouseExited() {
        Main.primaryStage.getScene().setCursor(Cursor.DEFAULT);
    }

    @FXML
    private void amAction() {
        am_pm = "AM";
    }

    @FXML
    private void pmAction() {
        am_pm = "PM";
    }
}
