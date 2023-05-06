package com.example.oopfinal;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.fxml.FXMLLoader;

/**
 * The type Login controller.
 */
public class LoginController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button btnLogin;

    @FXML
    private Button btnCancel;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private ImageView imageViewLogo;
    private String username;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image logoImage = new Image(getClass().getResourceAsStream("/images/sidebar/logo.png"));
        imageViewLogo.setImage(logoImage);
        imageViewLogo.setFitHeight(250);
        imageViewLogo.setFitWidth(250);
    }

    /**
     * Login.
     *
     * @param event the event
     * @throws SQLException the sql exception
     * @throws IOException  the io exception
     */
    public void login(ActionEvent event) throws SQLException, IOException {
        Window owner = btnLogin.getScene().getWindow();
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        if (username.isEmpty()){
            loginMessageLabel.setText("Your username is empty");
            return;
        }
        if (password.isEmpty()) {
            loginMessageLabel.setText("Your password is empty");
            return;
        }

        ConnDatabase connDatabase = new ConnDatabase();
        boolean flag = connDatabase.validateLogin(username, password);
        this.username = username;

        if (!flag){
            infoBox("Please enter correct Email and Password", null, "Failed");
        } else {
            infoBox("Login Successful!", null, "Welcome!");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
            Parent root = loader.load();
            HomePageController homePageController = loader.getController();
            homePageController.setUsernameLabel(username);
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Get username string.
     *
     * @return the string
     */
    public String getUsername(){
        return username;
    }

    /**
     * Info box.
     *
     * @param infoMessage the info message
     * @param headerText  the header text
     * @param title       the title
     */
    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    /**
     * Cancel.
     *
     * @param ignoredEvent the ignored event
     */
    public void cancel(ActionEvent ignoredEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    /**
     * Load register.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void loadRegister(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Show password.
     *
     * @param event the event
     */


}