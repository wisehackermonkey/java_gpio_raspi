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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import com.fazecast.jSerialComm.SerialPort;

/**
 * wisemonkey
 */
// TODO: 12/19/17 Select serial port
// xTODO: 12/19/17   desplay current serial ports
// xTODO: 12/19/17   get click event from each populated
// xTODO: 12/19/17   on click event start main loop
// TODO: 12/19/17 get input from arduino
// TODO: 12/19/17 rect to input from stored input

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
    private static SerialPort serialPort;

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
    private Button btn_connect;
    //https://docs.oracle.com/javafx/2/ui_controls/combo-box.htm
    @FXML
    private TextField input;
    ObservableList<String> options = FXCollections.observableArrayList();
    @FXML
    private ComboBox port_selection = new ComboBox(options);


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

    private void alert_set(String message, Alert.AlertType alert_type)
    {
        //http://stackoverflow.com/questions/28937392/ddg#36938061
        Alert alert = new Alert(alert_type, message, ButtonType.OK);
        alert.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);

        alert.setX(0);
        alert.setY(0);

        alert.show();
    }

    //HELPER FUNCTIONS
    public void print(String input)
    {
        System.out.println(input);
    }

    private void alert(String message)
    {
        alert_set(message, Alert.AlertType.INFORMATION);
    }

    private void error(String error_message)
    {
        alert_set(error_message, Alert.AlertType.ERROR);
    }

    @FXML
    private void btn_connect_press()
    {
        System.out.print(String.format("Connect Button Pressed%n"));
        if (btn_connect.getText().equals("Connect"))
        {

            if (port_selection.getValue() == null)
            {
                error("Serial Port Not Selected: Please select a port");
            } else
            {
                String portName = port_selection.getValue().toString();

                serialPort = SerialPort.getCommPort(portName);

                //open port
                if (serialPort.openPort())
                {
                    alert(String.format("Port Connected!: %s %n", portName));
                    btn_connect.setText("Disconnect");
                    port_selection.setEditable(false);
                }
            }

        }
        else
        {
            serialPort.closePort();
            port_selection.setEditable(true);
            btn_connect.setText("Connect");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        System.out.println("Calculator Started");
        SerialPort[] portNames = SerialPort.getCommPorts();
        for (int i = 0; i < portNames.length; i++)
        {
            String portname = portNames[i].getSystemPortName();
            options.add(portname);
            port_selection.setItems(options);
        }

    }

}
