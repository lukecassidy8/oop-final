package com.example.oopfinal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The type Register controller.
 */
public class RegisterController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private Button btnRegister;
    @FXML
    private ImageView imageViewLogo;
    @FXML
    private Button btnCancel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image logoImage = new Image(getClass().getResourceAsStream("/images/sidebar/logo.png"));
        imageViewLogo.setImage(logoImage);
        imageViewLogo.setFitHeight(250);
        imageViewLogo.setFitWidth(250);
    }

    /**
     * Register.
     *
     * @param event the event
     * @throws IOException  the io exception
     * @throws SQLException the sql exception
     */
    public void register(ActionEvent event) throws IOException, SQLException {
        Window owner = btnRegister.getScene().getWindow();

        if (usernameTextField.getText().isEmpty()){
            infoBox("Username is empty!", null, "Error");
            return;
        }
        if (passwordTextField.getText().isEmpty()){
            infoBox("Password is empty!", null, "Error");
            return;
        }
        if (emailTextField.getText().isEmpty()){
            infoBox("Email is empty!", null, "Error");
            return;
        }
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String email = emailTextField.getText();
        ConnDatabase connDatabase = new ConnDatabase();
        boolean flag = connDatabase.checkDupeUser(username);
        if (!flag){
            infoBox("This username already exists", null, "Error");
        }else {
            ConnDatabase.registerUser(username, password, email);
            infoBox("Registration successful!", null, "Success");
        }
    }

    /**
     * Load login.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void loadLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
    public void cancel(ActionEvent ignoredEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }


}
