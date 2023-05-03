package com.example.oopfinal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;

/**
 * The type Register controller.
 */
public class RegisterController {
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
    private Label registerMessageLabel;

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
            registerMessageLabel.setText("Username is empty!");
            return;
        }
        if (passwordTextField.getText().isEmpty()){
            registerMessageLabel.setText("Password is empty!");
        }
        if (emailTextField.getText().isEmpty()){
            registerMessageLabel.setText("Email is empty");
        }
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String email = emailTextField.getText();
        ConnDatabase connDatabase = new ConnDatabase();
        ConnDatabase.registerUser(username, password, email);

        registerMessageLabel.setText("Registration Successful!");
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
}
