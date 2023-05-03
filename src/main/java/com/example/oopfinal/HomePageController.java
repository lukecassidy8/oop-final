package com.example.oopfinal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * The type Home page controller.
 */
public class HomePageController implements Initializable {

    @FXML
    private Label labelUser;
    @FXML
    private Label labelGreeting;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalTime timeNow = LocalTime.now();
        int hour = timeNow.getHour();
        if(hour < 12){
            labelGreeting.setText("Good morning!");
        } else{
            labelGreeting.setText("Good afternoon");
        }
    }

    /**
     * Load loan item.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void loadLoanItem(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("searchitems.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Load print items.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void loadPrintItems(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("printitems.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Load return item.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void loadReturnItem(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("returnitem.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Load faults.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void loadFaults(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("faults.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Set username label.
     *
     * @param username the username
     */
    public void setUsernameLabel(String username){
        labelUser.setText("You are logged in as: "+ username);
    }


}
