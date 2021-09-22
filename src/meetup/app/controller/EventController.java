package meetup.app.controller;

import datastructures.linkedlist.LinkedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import meetup.app.entities.Event;

import javax.swing.*;
import java.io.IOException;

public class EventController {
    // initialize FXML's ID:
    @FXML
    private BorderPane eventsview_border_pane;
    @FXML
    private TitledPane titledpane1;
    @FXML
    private TitledPane titledpane2;
    @FXML
    private TitledPane titledpane3;
    @FXML
    private Label currentLocationLabel;
    @FXML
    private Label event_name_label1;
    @FXML
    private Label event_name_label2;
    @FXML
    private Label event_name_label3;
    @FXML
    private Label location_name_label1;
    @FXML
    private Label location_name_label2;
    @FXML
    private Label no_events_found_label;
    @FXML
    private Label location_name_label3;
    @FXML
    private Label see_all_events_label;
    @FXML
    private TextField searchbox_text_field;
    @FXML
    private Button search_button;

    // static variable for setting current location:
    public static String searchResult = "";
    public static String clickedInformation = "";

    public static LinkedList<Event> eventList = new LinkedList<>();

    // methods:
    @FXML
    private void initialize() throws IOException {
        currentLocationLabel.setText('"' + MapController.currentLocation + '"');

        // set temporary events' time and date
        if (eventList.size() >= 3) {
            see_all_events_label.setDisable(false);
            no_events_found_label.setVisible(false);
            titledpane3.setVisible(true);
            titledpane2.setVisible(true);
            titledpane1.setVisible(true);
            see_all_events_label.setDisable(false);
            searchbox_text_field.setDisable(false);
            titledpane1.setText(eventList.get(0).getDate() + ", " + eventList.get(0).getTime());
            titledpane2.setText(eventList.get(1).getDate() + ", " + eventList.get(1).getTime());
            titledpane3.setText(eventList.get(2).getDate() + ", " + eventList.get(2).getTime());

            // set temporary events' name of location
            event_name_label1.setText(eventList.get(0).getEventName());
            event_name_label2.setText(eventList.get(1).getEventName());
            event_name_label3.setText(eventList.get(2).getEventName());

            // set temporary events' location
            location_name_label1.setText(eventList.get(0).getLocation());
            location_name_label2.setText(eventList.get(1).getLocation());
            location_name_label3.setText(eventList.get(2).getLocation());
        } else if (eventList.size() == 2) {
            see_all_events_label.setDisable(false);
            no_events_found_label.setVisible(false);
            titledpane3.setVisible(false);
            titledpane2.setVisible(true);
            titledpane1.setVisible(true);
            see_all_events_label.setDisable(false);
            searchbox_text_field.setDisable(false);
            titledpane1.setText(eventList.get(0).getDate() + ", " + eventList.get(0).getTime());
            titledpane2.setText(eventList.get(1).getDate() + ", " + eventList.get(1).getTime());
            // set temporary events' name of location
            event_name_label1.setText(eventList.get(0).getEventName());
            event_name_label2.setText(eventList.get(1).getEventName());
            // set temporary events' location
            location_name_label1.setText(eventList.get(0).getLocation());
            location_name_label2.setText(eventList.get(1).getLocation());
        } else if (eventList.size() == 1) {
            see_all_events_label.setDisable(false);
            no_events_found_label.setVisible(false);
            titledpane3.setVisible(false);
            titledpane2.setVisible(true);
            titledpane1.setVisible(false);
            see_all_events_label.setDisable(false);
            searchbox_text_field.setDisable(false);
            titledpane2.setText(eventList.get(0).getDate() + ", " + eventList.get(0).getTime());
            event_name_label2.setText(eventList.get(0).getEventName());
            location_name_label2.setText(eventList.get(0).getLocation());
        }
        else {
            see_all_events_label.setDisable(true);
            titledpane3.setVisible(false);
            titledpane2.setVisible(false);
            titledpane1.setVisible(false);
            no_events_found_label.setVisible(true);
            searchbox_text_field.setDisable(true);
        }
    }

    @FXML
    private void changeLocationClicked() throws IOException {
        Main m = new Main();
        Main.primaryStage.setWidth(719);
        Main.primaryStage.setHeight(540);
        m.showMapView();
    }

    @FXML
    private void searchButtonOnAction() throws IOException {
        try {
            for (int i = 0; i <= eventList.size(); i++) {
                if (eventList.get(i).getEventName().trim().toUpperCase().equals(searchbox_text_field.getText().trim().toUpperCase())) {
                    searchResult = searchbox_text_field.getText();
                    Main.primaryStage.setWidth(600);
                    Main.primaryStage.setHeight(440);
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/fxml/searchresult_view.fxml"));
                    BorderPane showSearchResult = loader.load();
                    eventsview_border_pane.getChildren().setAll(showSearchResult);
                    return;
                }
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Sorry, there are no events for " + '"'
                    + searchbox_text_field.getText() + '"' + " near you", "No results found", JOptionPane.WARNING_MESSAGE);
        }
    }

    @FXML
    private void onKeyTyped() {
        if (searchbox_text_field.getText().trim().equals(""))
            search_button.setDisable(true);
        else
            search_button.setDisable(false);
    }

    @FXML
    public void seeAllEventClicked() throws IOException {
        Main.primaryStage.setHeight(440);
        Main.primaryStage.setWidth(620);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/all_events_view.fxml"));
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
    private void titledPane1Clicked() throws IOException {
        clickedInformation = event_name_label1.getText();
        Main.primaryStage.setWidth(600);
        Main.primaryStage.setHeight(440);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/searchresult_view.fxml"));
        BorderPane showSearchResult = loader.load();
        eventsview_border_pane.getChildren().setAll(showSearchResult);
    }

    @FXML
    private void titledPane2Clicked() throws IOException {
        clickedInformation = event_name_label2.getText();
        Main.primaryStage.setWidth(600);
        Main.primaryStage.setHeight(440);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/searchresult_view.fxml"));
        BorderPane showSearchResult = loader.load();
        eventsview_border_pane.getChildren().setAll(showSearchResult);
    }

    @FXML
    private void titledPane3Clicked() throws IOException {
        clickedInformation = event_name_label3.getText();
        Main.primaryStage.setWidth(600);
        Main.primaryStage.setHeight(440);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/searchresult_view.fxml"));
        BorderPane showSearchResult = loader.load();
        eventsview_border_pane.getChildren().setAll(showSearchResult);
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
