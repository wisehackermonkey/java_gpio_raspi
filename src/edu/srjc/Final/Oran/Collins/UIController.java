/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.srjc.Final.Oran.Collins;

import java.net.URL;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * wisemonkey
 */
// TODO: 12/11/2017 Comments
// TODO: 12/11/2017 polish this shit
// TODO: 12/10/2017 fix ui
// TODO: 12/15/2017   make pretty change colors
// TODO: 12/10/2017   prompt more descriptive
// TODO: 12/11/2017   change location of history of op's '5+6' 
// xTODO: 12/10/2017  output textbox
// xTODO: 12/10/2017  text box select
// xTODO: 12/10/2017 fix crash upon '++' instead of +
// xTODO: 12/10/2017  crash "+" + <enter>
// TODO: 12/10/2017 Add Delete
// TODO: 12/11/2017     fix crash when no input
// xTODO: 12/10/2017 Generalize operators
// xTODO: 12/10/2017 Add *
// xTODO: 12/10/2017 Add /
// xTODO: 12/11/2017    fix rounding issues int result => float result
// xTODO: 12/10/2017 Add -
// TODO: 12/10/2017 change output on repeate enter clear screen
// xTODO: 12/10/2017 Clear output upon pressing 'c' - clear
// TODO: 12/11/2017     fix clear showing the c character
// TODO: 12/10/2017  fix input leaving the 'c' character in the input!
// TODO: 12/11/2017 add ANS +,-,*,/
// TODO: 12/11/2017 add raspberry pi
public class UIController implements Initializable
{
    private String current_input = "";
    private double result = 0;

    enum Math_Op
    {
        plus,
        minus,
        multiply,
        divide,
        None;
    }

    private Math_Op math_op = Math_Op.None;

    @FXML
    private TextField output;


    @FXML
    private TextField input;

    @FXML
    private void handleInput(KeyEvent e)
    {

        System.out.println("Can't you follow directions?" + e.getCharacter());

    }

    //Helper functioncheck if string is numeric
    //https://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java
    private static boolean isNumeric(String str)
    {
        if (str.isEmpty())
        {
            System.err.println("isNumeric(): is empty!=> " + str);
            return false;
        }

        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

        input.setOnKeyPressed((KeyEvent keypress) ->
        {

            String key = keypress.getText();


            System.out.print(String.format("Key Input => '%s', keyname: %s%n", key, keypress.getCode().getName()));

            System.out.print(String.format(",%n", current_input));
            if (keypress.getCode().getName().equals("Backspace"))
            {
                String temp_for_print = current_input;
                if (current_input.length() != 0)
                {
                    current_input = current_input.substring(0, current_input.length() - 1);
                } else
                {
                    System.err.println("T-Input is empty!!");
                }
                System.out.print(String.format("T-backspace pressed!!, Cur: %s, New: %s, Field: %s,%n", temp_for_print, current_input, input.getText()));
            }
            else
            {
                System.err.println("F-backspace not pressed");
            }

            if (key.matches("[+\\-*/]") && isNumeric(current_input))
            {
                switch (key)
                {
                    case "+":   math_op = Math_Op.plus;     break;
                    case "-":   math_op = Math_Op.minus;    break;
                    case "*":   math_op = Math_Op.multiply; break;
                    case "/":   math_op = Math_Op.divide;   break;
                }
                key = "";
                try
                {
                    result = Long.parseLong(current_input);
                }
                catch (NumberFormatException e)
                {
                    System.err.print(String.format("ERROR DELETE PRESSED: %s%n",e.getMessage()));
                }
                current_input = "";

            }

            if (keypress.getText().equals("c"))
            {
                input.setText("");
                System.out.println("T-c");
                key = "";
                current_input = "";
                result = 0;

                math_op = Math_Op.None;

            }
            if (keypress.getCode().getName().equals("Enter") && isNumeric(current_input))
            {
                System.out.println("Enter");
                long current_number = Integer.parseInt(current_input);

                switch (math_op)
                {
                    case plus:      result = result + current_number;   break;
                    case minus:     result = result - current_number;   break;
                    case multiply:  result = result * current_number;   break;
                    case divide:    result = result / current_number;   break;
                    default:        System.err.println("Operator Not Found!");
                }
                System.out.println("T- Is Enterkey input :" + result + "\n\n\n\n");
                output.setText(String.valueOf(result));
                current_input = "";
                input.setText("");
            }
            else
            {
                System.err.println("F- Is Enterkey input  numeric:fail!");
            }

            if (key.matches("[0-9]") || key.matches("[+\\-*/]"))
            {
                System.out.println("T: matches TRUE regex not number or +,-,/,* " + current_input);
                current_input += key;
            } else
            {
                System.err.println("F: matches FAIL regex not number or +,-,/,* " + current_input);
            }
        });
    }

}
