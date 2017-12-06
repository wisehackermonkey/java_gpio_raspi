/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.srjc.Final.Oran.Collins;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author 
 */
public class UIController implements Initializable
{
    private String current_input = "";
    private int result = 0;




    @FXML
    private TextField output;

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
        public void handle(KeyEvent keypress)
        {

            String key = keypress.getText();

            if (key.equals("+"))
            {
                System.out.println("T");
                key = "";
                result = Integer.parseInt(current_input);
                current_input = "";

            } if (keypress.getText().equals("c"))
            {
                System.out.println("T-c");
                key = "";
                current_input = "";
                result = 0;

            } else if (keypress.getCode().getName().equals("Enter"))
            {
                System.out.println("EXIT");
                System.out.println(result + Integer.parseInt(current_input));
            } else
            {
                System.out.println("F");
                if (key.matches("[0-9+\\-*/]")){
                    System.out.println("-T");
                    current_input += key;
                }else{
                    System.out.println("-F: " + current_input);
                }

            }
            System.out.println("Key Pressed: " + key + ", Keycode: " + keypress.getCode().getName() + ", Keystream: " + current_input);
        }
    });
    }    
    
}
