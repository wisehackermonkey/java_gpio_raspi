/*
 Java Final Project Calculator
 This class handles the math calculations
 for the calculator
 all it does is take "12312" input and do the math for
 it.

    by Oran C
    20171204
    20171229
    oranbusiness@gmail.com
*/
package edu.srjc.Final.Oran.Collins;

import java.text.NumberFormat;
import java.text.ParsePosition;

public class Calculator
{

    private String currentInput = "";
    private String mathOperator = "";
    private double result = 0.0;

    /*
    * setTextInput( String keypress )
    * This function Processes user input IE "1234A1234#"
    * and does the math to calculate the result
    * */
    public void setTextInput( String keypress )
    {
        /*
        * Delete Button
        * Delete = "*" on the hardware keypad
        * deletes the last character to be entered
        *
        * currentInput.substring(0, length - 1);
        * removes the last character in the input
        * and sets the current input
        *
        * EX:
        * "1234" -> "123"
        * */
        if(keypress.equals("*"))
        {
            int length = currentInput.length();

            if(length != 0)
            {
                currentInput = currentInput.substring(0, length - 1);
            }
            else
            {
                System.err.print(String.format("Error Input is empty%n"));
            }
        }
        /*
        * ENTER and return results of calculation
        * ENTER = "#" on the hardware keypad
        * Parses current input
        * */
        else if(currentInput.length() != 0 && isNumeric(currentInput) && keypress.equals("#"))
        {
            double currentNumber = 0.0;
            try
            {
                currentNumber = Double.parseDouble(currentInput);
            }
            catch(NumberFormatException err)
            {
                System.err.print(String.format("Error : input is Not numeric!%n"));
            }
            switch(mathOperator)
            {
                case "A":
                    result = result + currentNumber;
                    break;
                case "B":
                    result = result - currentNumber;
                    break;
                case "C":
                    result = result * currentNumber;
                    break;
                case "D":
                    result = result / currentNumber;
                    break;
                default:
                    System.err.println("Operator Not Found!");
            }

            currentInput = "";

        }
        /*
        returns result of add subtract, *, /
        and parses the current input
        "1234A" = 1234 with math operator = +
        sets the math operator
        + = A
        - = B
        X = C
        / = D
          * */
        else if(keypress.matches("[ABCD]"))
        {
            mathOperator = keypress;
            try
            {
                result = Double.parseDouble(currentInput);
            }
            catch(NumberFormatException err)
            {
                System.err.print(String.format("Error : input is Not numeric!%n"));
            }

            currentInput = "";
        }
        /*
        * stores (saves or accumulates)the current input coming in from
        * the Arduino's keypad
        * */
        else
        {
            currentInput += keypress;
        }
        System.out.print(String.format("%s", keypress));
    }



    //Helper function to check if string is numeric
    //https://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java
    private static boolean isNumeric( String str )
    {
        //if there is no number give to function ie "" empty
        if(str.isEmpty())
        {
            System.err.println("isNumeric(): is empty!=> " + str);
            return false;
        }

        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }

    //Getters and setters
    public String getCurrentInput()
    {
        return currentInput;
    }
    public String getResult()
    {
        return Double.toString(result);
    }
    public void setCurrentInput( String currentInput )
    {
        this.currentInput = currentInput;
    }
    public void setMathOperator( String mathOperator )
    {
        this.mathOperator = mathOperator;
    }
    public void setResult( double result )
    {
        this.result = result;
    }
}
