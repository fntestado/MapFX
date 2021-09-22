package meetup.app.controller;

import datastructures.stack.Stack;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import meetup.app.entities.Event;
import meetup.app.entities.Location;

import javax.swing.*;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class EditEventController extends AllEventsController {
    @FXML
    private TextField event_name_textfield;
    @FXML
    private DatePicker datepicker;
    @FXML
    TextField hour_text_field;
    @FXML
    TextField minute_text_field;
    @FXML
    TextField location_textfield;
    @FXML
    Label asterisk1_label;
    @FXML
    Label asterisk2_label;
    @FXML
    Label asterisk3_label;
    @FXML
    Label undo_label;
    @FXML
    Label redo_label;
    @FXML
    TextArea event_detail_textarea;

    private Event event;
    private Stack<String> undoStack = new Stack<>();
    private Stack<String> redoStack = new Stack<>();
    private StringBuilder sb = new StringBuilder();

    @FXML
    private void initialize() {
        if (!EventController.searchResult.trim().equals("")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
            for (int i = 0; i <= EventController.eventList.size(); i++) {
                if (EventController.eventList.get(i).getEventName().trim().toUpperCase().equals(EventController.searchResult.trim().toUpperCase())) {
                    event_name_textfield.setText(EventController.eventList.get(i).getEventName());
                    datepicker.setValue(LocalDate.parse(EventController.eventList.get(i).getDate(), formatter));
                    hour_text_field.setText(EventController.eventList.get(i).getTime().substring(0, 2));
                    minute_text_field.setText(EventController.eventList.get(i).getTime().substring(3, 5));
                    event_detail_textarea.setText(EventController.eventList.get(i).getDetail());
                    location_textfield.setText(EventController.eventList.get(i).getLocation());
                    return;
                }
            }
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
            for (int i = 0; i <= EventController.eventList.size(); i++) {
                if (EventController.eventList.get(i).getEventName().trim().toUpperCase().equals(EventController.clickedInformation.trim().toUpperCase())) {
                    event_name_textfield.setText(EventController.eventList.get(i).getEventName());
                    datepicker.setValue(LocalDate.parse(EventController.eventList.get(i).getDate(), formatter));
                    hour_text_field.setText(EventController.eventList.get(i).getTime().substring(0, 2));
                    minute_text_field.setText(EventController.eventList.get(i).getTime().substring(3, 5));
                    event_detail_textarea.setText(EventController.eventList.get(i).getDetail());
                    location_textfield.setText(EventController.eventList.get(i).getLocation());
                    return;
                }
            }
        }

    }

    @FXML
    private void homeClicked() throws IOException {
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

    @FXML
    private void saveEventClicked() throws IOException {
        Date date = new Date();
        Instant instant = date.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

        asterisk1_label.setVisible(false);
        asterisk2_label.setVisible(false);
        asterisk3_label.setVisible(false);
        try {
            if (!event_name_textfield.getText().trim().equals("") && !event_detail_textarea.getText().trim().equals("") && !datepicker.getValue().toString().equals("")) {
                event = new Event(event_name_textfield.getText());
                event.setDate(localDate.toString());
//                String am_pm2 = am_pm == null || am_pm.trim().equals("") ? " " : am_pm;
                event.setTime(hour_text_field.getText() + ":" + minute_text_field.getText() + " " + am_pm);
                event.setDetail(event_detail_textarea.getText());
                event.setLocation(location_textfield.getText());
                for (int i = 0; i <= EventController.eventList.size() && EventController.eventList.get(i) != null; i++) {
                    if (EventController.eventList.get(i).getEventName().trim().toUpperCase().equals(EventController.clickedInformation.trim().toUpperCase())) {
                        EventController.eventList.add(event);
                        MapController.locationTree.get(new Location(MapController.currentLocation)).getEvents().add(event);
                        EventController.eventList.remove(EventController.eventList.get(i));
                        MapController.locationTree.get(new Location(MapController.currentLocation)).getEvents().delete(EventController.eventList.get(i));
                        break;
                    }
                }
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
        } catch (NullPointerException e) {
            asterisk1_label.setVisible(true);
            asterisk2_label.setVisible(true);
            asterisk3_label.setVisible(true);
            JOptionPane.showMessageDialog(null, "Please enter event's name, date and detail", "", JOptionPane.WARNING_MESSAGE);
        }
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


}
