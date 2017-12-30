package edu.srjc.Final.Oran.Collins;

import java.text.NumberFormat;
import java.text.ParsePosition;

public class Calculator
{

    private String current_input = "";
    private String mathOperator = "";
    private double result = 0;

    public Calculator()
    {
        System.out.print(String.format("Calculator Constructor Called: %n"));
    }

    public void setTextInput( String keypress )
    {
        // TODO: 12/28/2017 ANS +,-,*,/
        // TODO: 12/28/2017 Error handel setTextinput when ''
        if(current_input.length() != 0 && isNumeric(current_input) && keypress.equals("#"))
        {
            // TODO: 12/29/2017 try catch block
            // TODO: 12/29/2017     send user error message
            double currentNumber = Double.parseDouble(current_input);
            switch (mathOperator)
            {
                case "A":   result = result + currentNumber;   break;
                case "B":   result = result - currentNumber;   break;
                case "C":   result = result * currentNumber;   break;
                case "D":   result = result / currentNumber;   break;
                default:        System.err.println("Operator Not Found!");
            }

            current_input = "";

        }else if(keypress.matches("[ABCD]"))
        {
            mathOperator = keypress;
            result = Double.parseDouble(current_input);
            current_input = "";
        } else
        {
            current_input += keypress;
        }
        // TODO: 12/28/2017 Error handling


        System.out.print(String.format("%s", keypress));
    }


    public String getCurrent_input()
    {
        return current_input;
    }


    public String getResult()
    {
        return Double.toString(result);
    }

    //Helper functioncheck if string is numeric
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

    public void setCurrent_input( String current_input )
    {
        this.current_input = current_input;
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
