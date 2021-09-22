package meetup.app.controller;

import datastructures.stack.Stack;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;

import javax.swing.*;
import java.io.IOException;

public class SearchResultController {

    // initialize FXML's ID:
    @FXML
    private TitledPane titledpane;
    @FXML
    private Label search_result_label;
    @FXML
    private Label location_name_label;
    @FXML
    private Label event_name_label;
    @FXML
    private Label detail_label;

    private static String search_result = "";


    // methods:
    @FXML
    private void initialize() {
        if (!EventController.searchResult.trim().equals("")) {
            search_result_label.setText("Showing results for " + '"' + EventController.searchResult + '"');
            for (int i = 0; i <= EventController.eventList.size() && EventController.eventList.get(i) != null; i++) {
                if (EventController.eventList.get(i).getEventName().trim().toUpperCase().equals(EventController.searchResult.trim().toUpperCase())) {
                    titledpane.setText(EventController.eventList.get(i).getDate() + ", " + EventController.eventList.get(i).getTime());
                    event_name_label.setText((EventController.eventList.get(i).getEventName()));
                    location_name_label.setText((EventController.eventList.get(i).getLocation()));
                    detail_label.setText(EventController.eventList.get(i).getDetail());
                    search_result = EventController.searchResult;
                    EventController.searchResult = "";
                    return;
                }
            }
        } else {
            for (int i = 0; i <= EventController.eventList.size(); i++) {
                if (EventController.eventList.get(i).getEventName().trim().toUpperCase().equals(EventController.clickedInformation.trim().toUpperCase())) {
                    titledpane.setText(EventController.eventList.get(i).getDate() + ", " + EventController.eventList.get(i).getTime());
                    event_name_label.setText((EventController.eventList.get(i).getEventName()));
                    location_name_label.setText((EventController.eventList.get(i).getLocation()));
                    detail_label.setText(EventController.eventList.get(i).getDetail());
                    return;
                }
            }
        }
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
    private void seeAllEvent() throws IOException {
        EventController eventController = new EventController();
        eventController.seeAllEventClicked();
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
    private void createEventClicked() throws IOException {
        Main.primaryStage.setHeight(426);
        Main.primaryStage.setWidth(561);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/create_new_event_view.fxml"));
        BorderPane load = loader.load();
        Main.mainLayout.getChildren().setAll(load);
    }

    @FXML
    private void deleteEventClicked() throws IOException {
        if (!search_result_label.getText().trim().equals("")) {
            for (int i = 0; i <= EventController.eventList.size(); i++) {
                if (EventController.eventList.get(i).getEventName().trim().toUpperCase().equals(search_result.trim().toUpperCase())) {
                    String[] buttons = {"Yes", "No"};
                    int input = JOptionPane.showOptionDialog(null, "Are you sure you want to delete " + '"'
                                    + EventController.clickedInformation.trim() + '"' + "?", "Confirmation",
                            JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[1]);
                    if (input == 0) {
                        AllEventsController.counter = -1;
                        AllEventsController.eventRedoStack =  new Stack<>();
                        AllEventsController.eventUndoStack = new Stack<>();
                        AllEventsController.eventUndoStack.push(EventController.eventList.get(i));
                        EventController.eventList.remove(EventController.eventList.get(i));
                        seeAllEvent();
                    }

                    return;
                }
            }
        } else {
            for (int i = 0; i <= EventController.eventList.size(); i++) {
                if (EventController.eventList.get(i).getEventName().trim().toUpperCase().equals(EventController.clickedInformation.trim().toUpperCase())) {
                    String[] buttons = {"Yes", "No"};
                    int input = JOptionPane.showOptionDialog(null, "Are you sure you want to delete " + '"'
                                    + EventController.clickedInformation.trim() + '"' + "?", "Confirmation",
                            JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[1]);
                    if (input == 0) {
                        AllEventsController.counter = -1;
                        AllEventsController.eventRedoStack =  new Stack<>();
                        AllEventsController.eventUndoStack = new Stack<>();
                        AllEventsController.eventUndoStack.push(EventController.eventList.get(i));
                        EventController.eventList.remove(EventController.eventList.get(i));
                        seeAllEvent();
                    }
                    return;
                }
            }
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
}
