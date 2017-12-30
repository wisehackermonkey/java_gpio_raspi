// TODO: 12/29/2017 Header comment
/*
 Java Final Project Calculator
    by Oran C
    20171204
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

    // TODO: 12/29/2017 comment
    public void setTextInput( String keypress )
    {
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
        else if(currentInput.length() != 0 && isNumeric(currentInput) && keypress.equals("#"))
        {
            // TODO: 12/29/2017 try catch block
            // TODO: 12/29/2017     send user error message
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
        else if(keypress.matches("[ABCD]"))
        {
            mathOperator = keypress;
            result = Double.parseDouble(currentInput);
            currentInput = "";
        }
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
