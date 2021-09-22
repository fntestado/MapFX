package meetup.app.controller;

import datastructures.stack.Stack;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import meetup.app.entities.Event;
import meetup.app.entities.Location;

import javax.swing.*;
import java.io.IOException;


public class CreateNewEventController extends AllEventsController {

    @FXML
    private TextField hour_text_field;
    @FXML
    private TextField minute_text_field;
    @FXML
    private TextField location_textfield;
    @FXML
    private TextArea event_detail_textarea;
    @FXML
    private TextField event_name_textfield;
    @FXML
    private DatePicker datepicker;
    @FXML
    private Label asterisk1_label;
    @FXML
    private Label asterisk2_label;
    @FXML
    private Label asterisk3_label;
    @FXML
    Label undo_label;
    @FXML
    Label redo_label;

    private static String am_pm = "";
    private Event event;
    private StringBuilder sb = new StringBuilder();
    private Stack<String> undoStack = new Stack<>();
    private Stack<String> redoStack = new Stack<>();

    @FXML
    private void initialize() {
        location_textfield.setText(MapController.currentLocation);
        int LIMIT = 2;
        setTextProperty(LIMIT, hour_text_field);
        setTextProperty(LIMIT, minute_text_field);
    }

    private void setTextProperty(int LIMIT, TextField minute_text_field) {
        minute_text_field.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                minute_text_field.setText(newValue.replaceAll("[^\\d]", ""));
            }
        }));
        minute_text_field.lengthProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.intValue() > oldValue.intValue()) {
                if (minute_text_field.getText().length() >= LIMIT) {
                    minute_text_field.setText(minute_text_field.getText().substring(0, LIMIT));
                }
            }

        });
    }

    @FXML
    private void onKeyTyped(KeyEvent event) {
        sb.append(event.getCharacter());
        if (!sb.toString().trim().equals("")) {
            Timer timer = new Timer(5000, e -> undoStack.push(sb.toString()));

            timer.setRepeats(false);
            timer.start();
            if (!undoStack.isEmpty()) {
                undo_label.setDisable(false);
            } else {
                undo_label.setDisable(true);
            }
        } else {
            undo_label.setDisable(true);
            redo_label.setDisable(true);
        }
    }

    @FXML
    private void undoClicked() {
        try {
            if (!redoStack.isEmpty())
                redo_label.setDisable(false);
            else
                redo_label.setDisable(true);
            if (!undoStack.isEmpty()) {
                undo_label.setDisable(false);
                String popResult = undoStack.pop();
                sb.deleteCharAt(sb.length() - 1);
                redoStack.push(popResult);
                event_detail_textarea.setText(sb.toString());
            } else {
                undo_label.setDisable(true);
            }
        } catch (Exception e) {

        }
    }

    @FXML
    private void redoClicked() {
        if(!undoStack.isEmpty())
            undo_label.setDisable(false);
        else
            undo_label.setDisable(true);
        if (!redoStack.isEmpty()) {
            redo_label.setDisable(false);
            String popResult = redoStack.pop();
            undoStack.push(popResult);
            redoStack.push(popResult);
            event_detail_textarea.setText(popResult);
        } else {
            redo_label.setDisable(true);
        }
    }

    @FXML
    public void createEventClicked() throws IOException {
        asterisk1_label.setVisible(false);
        asterisk2_label.setVisible(false);
        asterisk3_label.setVisible(false);

        if (!event_name_textfield.getText().trim().equals("") && !event_detail_textarea.getText().trim().equals("") && !datepicker.getValue().toString().equals("")) {
            event = new Event(event_name_textfield.getText());
            event.setLocation(location_textfield.getText());
            event.setDate(datepicker.getValue().toString());
            event.setTime(hour_text_field.getText() + ":" + minute_text_field.getText() + " " + am_pm);
            event.setDetail(event_detail_textarea.getText());
            MapController.locationTree.get(new Location(MapController.currentLocation)).addEvent(event);
            EventController.eventList.addFirst(event);
            AllEventsController.eventUndoStack = new Stack<>();
            AllEventsController.eventRedoStack = new Stack<>();
            AllEventsController.eventUndoStack.push(event);
            AllEventsController.counter = 0;

            Main.primaryStage.setHeight(440);
            Main.primaryStage.setWidth(620);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/all_events_view.fxml"));
            BorderPane loadSecond = loader.load();
            Main.mainLayout.getChildren().setAll(loadSecond);
        } else {
            asterisk1_label.setVisible(true);
            asterisk2_label.setVisible(true);
            asterisk3_label.setVisible(true);
            JOptionPane.showMessageDialog(null, "Please enter event's name, date and detail", "", JOptionPane.WARNING_MESSAGE);
        }
    }

    @FXML
    private void changeLocationClicked() throws IOException {
        if (!event_name_textfield.getText().trim().equals("") || !event_detail_textarea.getText().trim().equals("")) {
            showWarningMessage();
        } else {
            Main m = new Main();
            Main.primaryStage.setWidth(719);
            Main.primaryStage.setHeight(540);
            m.showMapView();
        }
    }

    @FXML
    public void homeClicked() throws IOException {
        if (!event_name_textfield.getText().trim().equals("") || !event_detail_textarea.getText().trim().equals("")) {
            showWarningMessage();
        } else {
            Main.primaryStage.setHeight(516);
            Main.primaryStage.setWidth(711);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/events_view.fxml"));
            BorderPane loadSecond = loader.load();
            Main.mainLayout.getChildren().setAll(loadSecond);
        }
    }

    private void showWarningMessage() throws IOException {
        String[] buttons = {"Yes", "No"};
        int input = JOptionPane.showOptionDialog(null, "Are you sure you want to discard all changes?", "Confirmation",
                JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[1]);
        if (input == 0) {
            Main.primaryStage.setHeight(516);
            Main.primaryStage.setWidth(711);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/events_view.fxml"));
            BorderPane loadSecond = loader.load();
            Main.mainLayout.getChildren().setAll(loadSecond);
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
