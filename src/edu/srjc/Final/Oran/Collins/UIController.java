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
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

//download link
//http://fazecast.github.io/jSerialComm/
//documentation
//http://fazecast.github.io/jSerialComm/javadoc/index.html
import com.fazecast.jSerialComm.SerialPort;
import edu.srjc.Final.Oran.Collins.Calculator;

//TODO Board
// https://trello.com/b/OJHyCHlC/java-final


// Graph Arduino Sensor Data with Java and JFreeChart
// https://www.youtube.com/watch?v=cw31L_OwX3A
// http://www.farrellf.com/arduino/Main.java
// http://www.farrellf.com/arduino/SensorGraph.java
// http://farrellf.com/arduino/Updated_jSerialComm_Demo.java
// http://www.farrellf.com/arduino/SensorGraph.java


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
// xTODO: 12/11/2017     fix crash when no input
// xTODO: 12/10/2017 Generalize operators
// xTODO: 12/10/2017 Add *
// xTODO: 12/10/2017 Add /
// xTODO: 12/11/2017    fix rounding issues int result => float result
// xTODO: 12/10/2017 Add -
// TODO: 12/10/2017 change output on repeate enter clear screen
// xTODO: 12/10/2017 Clear output upon pressing 'c' - clear
// xTODO: 12/11/2017     fix clear showing the c character
// xTODO: 12/10/2017  fix input leaving the 'c' character in the input!
// xTODO: 12/11/2017 add ANS +,-,*,/
// xTODO: 12/11/2017 add raspberry pi


// TODO: 12/29/2017 docs
// TODO: 12/29/2017 install
// TODO: 12/29/2017 setup
// TODO: 12/29/2017 photo
// TODO: 12/29/2017 comments
// TODO: 12/29/2017 set comment markers

// xTODO: 12/28/2017 if # than get result
// TODO: 12/29/2017         ans #
// xTODO: 12/28/2017 if 0-9 add to number
// xTODO: 12/28/2017 if 'A' parse current input, then set math op to 'PLUS'
// TODO: 12/29/2017         ans +
// TODO: 12/28/2017 if 'B' parse, math = 'Sub'
// TODO: 12/29/2017         ans -
// TODO: 12/28/2017 if 'C' parse, math = 'times'
// TODO: 12/29/2017         ans *
// TODO: 12/28/2017 if 'D' parse, math = 'divide'
// TODO: 12/29/2017         ans %
// TODO: 12/28/2017 if '*' delete last character,
// TODO: 12/29/2017     function delete
// TODO: 12/28/2017 if '#' parse cuurent input, add to 'result' + input = 'result'
// xTODO: 12/28/2017 UI create 'clear button'
// TODO: 12/29/2017     ui clear handler finish
public class UIController implements Initializable
{
    private String current_input = "";
    private double result = 0;
    private static SerialPort serialPort;
    ObservableList<String> options = FXCollections.observableArrayList();
    private static Calculator calculator = new Calculator();

    enum Math_Op
    {
        plus, minus, multiply, divide, None;
    }

    private Math_Op math_op = Math_Op.None;

    @FXML
    private TextField output;

    @FXML
    private Button btn_connect;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnClear;

    @FXML
    private TextField input;

    @FXML
    private Label message;


    //https://docs.oracle.com/javafx/2/ui_controls/combo-box.htm
    @FXML
    private ComboBox<String> port_selection = new ComboBox<>(options);

    @FXML
    private void btn_connect_press()
    {
        System.out.print(String.format("Connect Button Pressed%n"));
        if(btn_connect.getText().equals("Connect"))
        {

            if(port_selection.getValue() == null)
            {
                error("Serial Port Not Selected: Please select a port");
            } else
            {
                String portName = port_selection.getValue();

                serialPort = SerialPort.getCommPort(portName);

                //set port to scanner mode lets reading in characters without quitting early
                serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

                //open port
                // TODO: 12/28/2017 error handling
                if(serialPort.openPort())
                {
                    message.setText(String.format("Port Connected!: %s %n", portName));
                    btn_connect.setText("Disconnect");
//                    https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/ComboBoxBase.html#isEditable--
                    port_selection.setEditable(false);
                }
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        if(serialPort.getInputStream() == null){
                            //error("Error occurred when tring to connect to port:" +
                              //      "Try another port");
                            error(String.format("Error Occurred when trying to connect to port:%n Is the Arduino Connected? %n or try connecting to a different port%n"));
                        }
                        else
                        {
                            Scanner keypressed = new Scanner(serialPort.getInputStream());

                            while(keypressed.hasNext())
                            {
                                try
                                {
                                    String line = keypressed.nextLine();
                                    if(line.matches("[ABCD0123456789#*]"))
                                    {

                                        calculator.setTextInput(line);

                                        input.setText(calculator.getCurrent_input());
                                        output.setText(calculator.getResult());

                                        System.out.print(String.format("In: %s, Text: %s, Result: %s%n",line,calculator.getCurrent_input(), calculator.getResult()));
                                    }
                                }
                                catch(Exception err)
                                {
                                    System.err.println("Exception: Reading Arduino serial port");
                                    keypressed.close();
                                }
                            }
                        }
                    }
                };
                //close text input monitoring thread when closing the program
                //https://stackoverflow.com/questions/14897194/stop-threads-before-close-my-javafx-program#20374691
                thread.setDaemon(true);
                //start a new thread the monitors the serial characters coming in from arduino keypad
                thread.start();
            }

        } else
        {
            serialPort.closePort();
            btn_connect.setText("Connect");
            port_selection.setEditable(true);

        }
    }

    @FXML
    private void setBtnRefresh_handler()
    {
        getSerialPorts();
    }
    private void getSerialPorts()
    {
        options = FXCollections.observableArrayList();
        SerialPort[] portNames = SerialPort.getCommPorts();
        for(int i = 0; i < portNames.length; i++)
        {
            String portname = portNames[i].getSystemPortName();
            options.add(portname);
            port_selection.setItems(options);
        }
    }



    @FXML
    private void clearHandler()
    {
        System.out.print(String.format("TODO CLEAR HANDLER%n"));
    }





    //HELPER FUNCTIONS
    private void alert_set( String message, Alert.AlertType alert_type )
    {
        //http://stackoverflow.com/questions/28937392/ddg#36938061
        Alert alert = new Alert(alert_type, message, ButtonType.OK);
        alert.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);

        alert.setX(0);
        alert.setY(0);

        alert.show();
    }

    public void print( String input )
    {
        System.out.println(input);
    }

    private void alert( String message )
    {
        alert_set(message, Alert.AlertType.INFORMATION);
    }

    private void error( String error_message )
    {
        alert_set(error_message, Alert.AlertType.ERROR);
    }

    @Override
    public void initialize( URL url, ResourceBundle rb )
    {
        System.out.println("Calculator Started");

        getSerialPorts();
    }

}
