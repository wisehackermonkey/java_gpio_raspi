/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.srjc.Final.Oran.Collins;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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
    private TextField input;

    @FXML
    private void handleInput(KeyEvent e)
    {

        System.out.println("Can't you follow directions?" + e.getCharacter());

    }



    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

        input.setOnKeyPressed(new EventHandler<KeyEvent>() {
        public void handle(KeyEvent ke) {
            System.out.println("Key Pressed: " + ke.getText());

        }
    });
    }    
    
}
