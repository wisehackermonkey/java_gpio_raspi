/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.srjc.Final.Oran.Collins;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 *
 * @author 
 */
public class UIController implements Initializable
{
    
    @FXML
    private Label label;

    @FXML
    private TextField txtName;

    @FXML
    private void handleButtonAction(ActionEvent event)
    {
        System.out.println("You clicked me!");
        if (txtName.getText().length() > 0)
        {
            label.setText("Hello there " + txtName.getText() + "!");
        }
        else
        {
            label.setText("Can't you follow directions?");
        }

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    
    
}
