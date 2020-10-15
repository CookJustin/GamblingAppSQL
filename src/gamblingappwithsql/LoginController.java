/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamblingappwithsql;

import static Utils.DBConnection.conn;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author monke
 */
public class LoginController implements Initializable {

    @FXML
    private PasswordField password;
    @FXML
    private TextField username;

    private static int AccountID;
    @FXML
    private Text yourroll;

    public LoginController() {
        this.AccountID = 0;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
    }    

    @FXML
    private void login(ActionEvent event) throws IOException, SQLException {
        try {
            String attempt_userName = username.getText();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Password FROM Account WHERE UserName = " + "'" + attempt_userName + "';");
            while(rs.next()){
                if(rs.getString("Password").equals(password.getText())){
                    setIdWithUser(username.getText());
                    System.out.println("Login Successful");
                    Parent mainScreen = FXMLLoader.load(getClass().getResource("/gamblingappwithsql/Main.fxml"));
                    Scene mainScreenScene = new Scene(mainScreen);
                    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    app_stage.hide();
                    app_stage.setScene(mainScreenScene);
                    app_stage.setTitle("MainScreen");
                    app_stage.show();
                }
                else{
                    System.out.println("Login Failed");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("hi");
        }
        
    }
    public static void setIdWithUser(String User) throws SQLException{
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT AccountID FROM Account WHERE UserName = " + "'" + User + "';");
         while(rs.next()){
             AccountID = rs.getInt("AccountID");
          
         }
        
    }
    public static int getAccountId(){
       return AccountID;
    }
}
