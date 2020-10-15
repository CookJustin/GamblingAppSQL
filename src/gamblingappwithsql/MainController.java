/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamblingappwithsql;

import static Utils.DBConnection.conn;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author monke
 */
public class MainController implements Initializable {

    @FXML
    private Text BalanceText;
    @FXML
    private Button Roll;
    @FXML
    private ToggleButton HighLowToggle;
    @FXML
    private Text currentHigh;
    @FXML
    private Text currentLow;
    @FXML
    private Text LoggedInAsText;
    
    private int AccID;
    private int bal = 0;
    @FXML
    private TextField Bet;
    @FXML
    private Text yourroll;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       AccID = LoginController.getAccountId();
       System.out.println(AccID);
        try {
            updateBal();
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error updating Balance");
        }
       BalanceText.setText("Balance: " + bal);
    }    

    @FXML
    private void Roll(ActionEvent event) throws SQLException {
        Random r = new Random();
        int result = r.nextInt(100) + 1;
        yourroll.setText("Roll: "+ result);
        if(result > 50 && currentHigh.isVisible()){
            BalanceText.setText("Balance: " + (bal + Integer.parseInt(Bet.getText())));
            setBal(bal + Integer.parseInt(Bet.getText()));
            updateBal();
        }
        else if(result < 51 && currentLow.isVisible()){
            BalanceText.setText("Balance: " + (bal + Integer.parseInt(Bet.getText())));
            setBal(bal + Integer.parseInt(Bet.getText()));
            updateBal();
        }
        else{
            BalanceText.setText("Balance: " + (bal - Integer.parseInt(Bet.getText())));
            setBal(bal - Integer.parseInt(Bet.getText()));
            updateBal();
        }
    }

    @FXML
    private void HighLowToggle(ActionEvent event) {
        if(currentHigh.isVisible()){
            currentHigh.setVisible(false);
            currentLow.setVisible(true);
          
        }else{
            currentHigh.setVisible(true);
            currentLow.setVisible(false);
        }
        
    }
    private void updateBal() throws SQLException{
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT Balance FROM Account WHERE AccountID = " + "'" + AccID + "';");
        while(rs.next()){
            bal = rs.getInt("Balance");
        }
    }
    private void setBal(int newBal) throws SQLException{
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("UPDATE Account SET Balance = " + "'" + newBal + "' " + "WHERE AccountID = " + "'" + AccID + "';");
        BalanceText.setText("Balance: " + newBal);
                
    }
}
