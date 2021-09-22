package meetup.app.controller;

import datastructures.graph.Graph;
import datastructures.linkedlist.LinkedList;
import datastructures.stack.Stack;
import datastructures.tree.Tree;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import meetup.app.entities.Event;
import meetup.app.entities.Location;

import java.io.IOException;

public class MapController {

    // initialize FXML's ID:
    @FXML
    private GridPane mapGridPane;
    @FXML
    private TextField searchTextField;
    @FXML
    private Label searchResultLabel;
    @FXML
    private Label next_label;
    @FXML
    private Label undo_label;
    @FXML
    private Label redo_label;
    @FXML
    private Button button;

    // static Tree that contains location:
    static Tree<Location> locationTree = new Tree<>();
    public static Graph<Location> locationGraph = new Graph<>();
    private Stack<Button> buttonUndo = new Stack<>();
    private Stack<Button> buttonRedo = new Stack<>();

    // static String that contains current location:
    public static String currentLocation;
    public static Location currLocation = new Location();

    //variables for locating buttons in the map (gridPane):
    public static int index = 0, counter = 0;
    private Location location;
    private Event event;

    @FXML
    private void initialize() {
        int index = 0;
        for (int i = 0; i < 59; i++) {
            for (int j = 0; j < 46; j++) {
                button = new Button();
                location = new Location("Location " + index);
                location.setX_axis(i);
                location.setY_axis(j);

                event = new Event("Sample Event " + index);
                event.setDate("2019-11-04");
                event.setTime("05:00 PM");
                event.setLocation(location.getLocationName());
                event.setDetail(event.getEventName() + " help you host events and support community experts 7 days a week. Charge ticket" +
                        " fees or dues to help cover your costs");
                location.addEvent(event);
                locationTree.add(location);
                locationGraph.addVertex(location);
                button.setId("" + i + " " + j);

                button.setPrefHeight(12);
                button.setPrefWidth(50);
                button.setStyle("-fx-border-color: transparent; -fx-background-color: transparent;");

                button.setMinWidth(Region.USE_PREF_SIZE);
                button.setMaxWidth(Region.USE_PREF_SIZE);
                button.setPrefWidth(13);

                button.setMinHeight(Region.USE_PREF_SIZE);
                button.setMaxHeight(Region.USE_PREF_SIZE);
                button.setPrefHeight(10);

                button.setOnAction(this::loadWhenClicked);
                mapGridPane.add(button, i, j); // i == column j == row


                if (locationTree.get(locationTree.toLinkedList().get(index)).getEvents().toLinkedList().size() == 0) {
                    locationGraph.removeVertex(locationTree.toLinkedList().get(index));
                }
                index++;
            }
        }


    }

    // methods:
    @FXML
    public void next_labelAction() throws IOException {
        EventController.eventList = new LinkedList<>();
        for (int i = 0; i <= locationTree.toLinkedList().size() && locationTree.toLinkedList().get(i) != null; i++) {
            if (locationTree.get(locationTree.toLinkedList().get(i)).getEvents().toLinkedList().size() == 0) {
                locationGraph.removeEdge(new Location(currentLocation), locationTree.toLinkedList().get(i));
            }
            if (locationTree.toLinkedList().get(i).getLocationName().trim().toUpperCase().equals(searchTextField.getText().trim().toUpperCase())) {
                for (int j = 0; j <= locationTree.toLinkedList().size() && locationTree.toLinkedList().get(j) != null; j++) {
                    Location start = locationTree.toLinkedList().get(i);
                    Location destination = locationTree.toLinkedList().get(j);
                    int x = Math.abs(start.getX_axis() - destination.getX_axis());
                    int y = Math.abs(start.getY_axis() - destination.getY_axis());
                    if (x < 10 && y < 10) {
                        if (!locationGraph.isAdjacent(start, destination)) {
                            locationGraph.addEdge(start, destination);
                        }
                    }
                }
                locationGraph.getNeighbors(locationTree.toLinkedList().get(i)).forEach(location1 -> EventController.eventList.addFirst(location1.getEvents().toLinkedList().get(0)));
                break;
            }
        }
        AllEventsController.locationGraph = locationGraph;
        AllEventsController.location = currLocation;
        Main.primaryStage.setHeight(516);
        Main.primaryStage.setWidth(711);
        currentLocation = searchTextField.getText();
        currLocation.setLocationName(currentLocation);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/events_view.fxml"));
        BorderPane loadSecond = loader.load();
        Main.mainLayout.getChildren().setAll(loadSecond);
    }

    @FXML
    private void setSearchLabelAction() {
        try {
            mapGridPane.getChildren().get(index).setStyle("-fx-border-color: transparent; -fx-background-color: transparent;");
            searchResultLabel.setVisible(false);
            if (Character.isDigit(searchTextField.getText().charAt(0))) {
                index = Integer.parseInt(searchTextField.getText());
                mapGridPane.getChildren().get(index).setStyle("-fx-background-color: red; ");
                searchTextField.setText("Location " + index);
            } else if (searchTextField.getText().toUpperCase().startsWith("LOCATION")) {
                index = Integer.parseInt(searchTextField.getText().substring(9).trim());
                mapGridPane.getChildren().get(index).setStyle("-fx-background-color: red; ");
                searchTextField.setText("Location " + index);
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            searchResultLabel.setVisible(true);
        }
    }

    @FXML
    private void loadWhenClicked(ActionEvent event) {
        searchResultLabel.setVisible(false);
        mapGridPane.getChildren().get(index).setStyle("-fx-border-color: transparent; -fx-background-color: transparent;");
        if (counter == 0) { // if button == 0 then show button
            Button button = (Button) event.getSource();
            if (!buttonUndo.isEmpty()) {
                if (!buttonUndo.peek().getId().equals(button.getId())) {
                    buttonUndo.push(button);
                }
            } else {
                buttonUndo.push(button);
            }
            counter = -1;
            int i = 0;
            for (Node node : mapGridPane.getChildren()) {
                if (button.getId() == node.getId())
                    break;
                i++;
            }
            index = i;
            searchTextField.setText("Location " + index);
            next_label.setDisable(false);
            button.setStyle("-fx-background-color: red; "); // button sytle
        } else {    // if button != 0 then set the last clicked button to transparent
            next_label.setDisable(true);
            undo_label.setDisable(false);
            redo_label.setDisable(false);
            searchTextField.setText("");
            counter = 0;
            mapGridPane.getChildren().get(index).setStyle("-fx-border-color: transparent; -fx-background-color: transparent;");
        }
    }

    @FXML
    private void undoClicked() {
        if (!buttonUndo.isEmpty()) {
            mapGridPane.getChildren().get(index).setStyle("-fx-border-color: transparent; -fx-background-color: transparent;");
            button = buttonUndo.pop();
            buttonRedo.push(button);
            if (!buttonRedo.isEmpty()) {
                redo_label.setDisable(false);
            } else {
                redo_label.setDisable(true);
            }
            int i = 0;
            for (Node node : mapGridPane.getChildren()) {
                if (button.getId() == node.getId())
                    break;
                i++;
            }
            index = i;
            searchTextField.setText("Location " + index);
            button.setStyle("-fx-background-color: red; "); // button sytle
        } else {
            undo_label.setDisable(true);
        }
    }

    @FXML
    private void redoClicked() {
        if (!buttonRedo.isEmpty()) {
            mapGridPane.getChildren().get(index).setStyle("-fx-border-color: transparent; -fx-background-color: transparent;");
            button = buttonRedo.pop();
            if (!buttonUndo.isEmpty()) {
                if (!buttonUndo.peek().getId().equals(button.getId())) {
                    buttonUndo.push(button);
                }
            } else {
                buttonUndo.push(button);
            }
            if (!buttonUndo.isEmpty()) {
                undo_label.setDisable(false);
            } else {
                undo_label.setDisable(true);
            }

            int i = 0;
            for (Node node : mapGridPane.getChildren()) {
                if (button.getId() == node.getId())
                    break;
                i++;
            }
            index = i;
            searchTextField.setText("Location " + index);
            button.setStyle("-fx-background-color: red; "); // button sytle
        } else {
            redo_label.setDisable(true);
        }
    }

/*    @FXML
    public void temporaryEvents() {
        location = new Location(searchTextField.getText());
        event = new Event("Spanish Lesson".trim().toUpperCase());
        event.setLocation(location.getLocationName());
        event.setDate("2019-11-03");
        event.setTime("3:30 PM");
        location.addEvent(event);

        event = new Event("English Lesson".trim().toUpperCase());
        event.setLocation(location.getLocationName());
        event.setDate("2019-11-03");
        event.setTime("4:30 PM");
        location.addEvent(event);

        event = new Event("Filipino Lesson".trim().toUpperCase());
        event.setLocation(location.getLocationName());
        event.setDate("2019-11-03");
        event.setTime("5:30 PM");
        location.addEvent(event);
        locationTree.add(location);

        EventController.eventList = locationTree.get(new Location(searchTextField.getText())).getEvents().toLinkedList();
    }*/

    @FXML
    private void onMouseEntered() {
        Main.primaryStage.getScene().setCursor(Cursor.HAND);
    }

    @FXML
    private void onMouseExited() {
        Main.primaryStage.getScene().setCursor(Cursor.DEFAULT);
    }
}

