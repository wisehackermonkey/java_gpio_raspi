/*
 Java Final Project Calculator
 This class creates the java fx interface
 creates the interface for the calculator
 also handles connecting to the Arduino
 and calling the calculator class that
 handles all the annoying math calculations

    by Oran C
    20171204
    oranbusiness@gmail.com
*/
package edu.srjc.Final.Oran.Collins;


//download link
//http://fazecast.github.io/jSerialComm/
//documentation
//http://fazecast.github.io/jSerialComm/javadoc/index.html
import com.fazecast.jSerialComm.SerialPort;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

//LINKS
// Graph Arduino Sensor Data with Java and JFreeChart
// https://www.youtube.com/watch?v=cw31L_OwX3A
// http://www.farrellf.com/arduino/Main.java
// http://www.farrellf.com/arduino/SensorGraph.java
// http://farrellf.com/arduino/Updated_jSerialComm_Demo.java
// http://www.farrellf.com/arduino/SensorGraph.java

//HARDWARE INFO
//https://embedjournal.com/interface-4x4-matrix-keypad-with-microcontroller/
//https://github.com/Fazecast/jSerialComm/wiki/Java-InputStream-and-OutputStream-Interfacing-Usage-Example


// XTODO: 12/19/17 Select serial port
// xTODO: 12/11/2017 Comments
// XTODO: 12/10/2017 change output on repeate enter clear screen

// XTODO: 12/29/2017 docs
//    get help message on what to do
//            error message if not arduino
//            message if not working with arduino
//            clear descripton of what the buttons do
//
//
//            set by set of what to do to do to get started
//            example how to add number
// xTODO: 12/29/2017 install
// xTODO: 12/29/2017 setup
// xTODO: 12/29/2017 photo
// xTODO: 12/29/2017 explain what is + = A etc
// xTODO: 12/29/2017 comments
// xTODO: 12/29/2017 set comment markers

// xTODO: 12/29/2017 cleanup code
// xTODO: 12/29/2017         ans #
// xTODO: 12/29/2017         ans +
// xTODO: 12/29/2017         ans -
// xTODO: 12/29/2017         ans *
// xTODO: 12/29/2017         ans %

public class UIController implements Initializable
{
    private String current_input = "";
    private double result = 0;
    private static SerialPort serialPort;
    ObservableList<String> options = FXCollections.observableArrayList();
    private static Calculator calculator = new Calculator();

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

    /*
    btnConnectPress()
    this function connects to the arduino
    when after the user has selected what PORT to connect the arduino
    on.

    port_selection.setEditable(false);
    https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/ComboBoxBase.html#isEditable--

    * */
    @FXML
    private void btnConnectPress()
    {
            if(btn_connect.getText().equals("Connect"))
            {
                try
                {
                    if(port_selection.getValue() == null)
                    {
                        error("SELECT PORT: EXAMPLE      port > \"COM3\" or '/dev/ttyACM0'");
                    }
                    else
                    {

                        String portName = port_selection.getValue();

                        serialPort = SerialPort.getCommPort(portName);

                        //set port to scanner mode lets reading in characters without quitting early
                        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 1000, 1000);

                        //open port arduino serial port
                        if(serialPort.openPort())
                        {
                            message.setText(String.format("Port: %s %n", portName));
                            alert(String.format("NOTE: If buttons NOT working, TRY other PORT, than port: %s %n", portName));
                            alert(String.format("Port Connected!: %s %n", portName));
                            btn_connect.setText("Disconnect");
                            port_selection.setEditable(false);
                        }




                        /*
                        * create a thread that monitors the characters
                        * that come off the arduino
                        * for each character that comes in
                        * CALL
                        *
                        * calculator.setTextInput(line);
                        *
                        * witch calls the calculator class
                        * sets up the math stuff, then returns the result
                        * sets the javafx insterface to display the result
                        * calculator > "11+33" set interface to "44"
                        * */
                        Thread thread = new Thread()
                        {
                            @Override
                            public void run()
                            {
                                if(serialPort.getInputStream() == null)
                                {
                                    //error("Error occurred when tring to connect to port:" +
                                    //      "Try another port");
                                    error(String.format("Error Occurred when trying to connect to port:%n Is the Arduino Connected? %n or try connecting to a different port%n"));
                                } else
                                {
                                    try
                                    {
                                        Scanner keypressed = null;
                                        if(serialPort.isOpen())
                                        {
                                            keypressed = new Scanner(serialPort.getInputStream());
                                        }

                                        while(keypressed.hasNext())
                                        {
                                            try
                                            {
                                                String line = keypressed.nextLine();
                                                if(line.matches("[ABCD0123456789#*]"))
                                                {

                                                    calculator.setTextInput(line);


                                                    input.setText(calculator.getCurrentInput());
                                                    output.setText(calculator.getResult());
                                                }
                                            }
                                            catch(Exception err)
                                            {
                                                System.err.println("Exception: Reading Arduino serial port");
                                                error("Exception: Reading Arduino serial port");
                                                keypressed.close();
                                            }
                                        }
                                    }
                                    catch(IllegalStateException e)
                                    {
                                        System.err.println("ERROR: Try DISCONNECT  then CONNECT ");
                                        serialPort.closePort();
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
                }catch(Exception err)
                {
                    System.out.print(String.format("Something went wrong%n",err));
                }
            } else
            {

                /*
                * DISCONNECT
                * when the button 'disconnect is pressed it
                * Closes the connection to the arduino
                * sets the desplay to blank
                * allows the user to select a port
                *
                * also clears the display when trying to reconnect
                * sets the current number of the calculator to blank
                * */
                serialPort.closePort();
                btn_connect.setText("Connect");
                port_selection.setEditable(true);

                output.setText("0.0");
                input.setText("");
                calculator.setMathOperator("");
                calculator.setResult(0.0);
                calculator.setCurrentInput("");

            }


    }

    @FXML
    private void setBtnRefresh_handler()
    {
        getSerialPorts();
    }

    /*
    * getSerialPorts()
    * gets the current list of serial ports form
    * the computer
    * EI COM1, COM3
    * or
    * /dev/ttyACM0, /dev/ttyACM4'
    *
    * SerialPort.getCommPorts()
    * returns a iterable list of ports
    *
    *
    * port_selection.setItems(options);
    * sets the dropdown 'PORT'
    * menu to display the currently available ports
    *
    *
    * */
    private void getSerialPorts()
    {

        SerialPort[] portNames = SerialPort.getCommPorts();

        options = FXCollections.observableArrayList();

        for(int i = 0; i < portNames.length; i++)
        {
            String portname = portNames[i].getSystemPortName();
            options.add(portname);
            port_selection.setItems(options);
        }
    }


    /*
    * clearHandler()
    *
    * function is called when the "CLEAR"
    * button is pressed
    *
    * this function resets the display's
    * output field
    * input field
    * The Calculator classes interal current number and input string
    * */
    @FXML
    private void clearHandler()
    {
        calculator.setResult(0);
        calculator.setMathOperator("");
        calculator.setCurrentInput("");
        input.setText("");
        output.setText("0");
        System.out.print(String.format("Res:%s, Input: <%s>%n", calculator.getResult(), calculator.getCurrentInput()));
    }


    //HELPER FUNCTIONS


    /*
    * JavaFX Alerts abstraction
    * alert_set( String message, Alert.AlertType alert_type )
    * function deplays a popup message to the user
    * */
    private void alert_set( String message, Alert.AlertType alert_type )
    {
        try
        {
            //http://stackoverflow.com/questions/28937392/ddg#36938061
            Alert alert = new Alert(alert_type, message, ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);

            alert.setX(0);
            alert.setY(0);

            alert.show();
        }catch(Exception err)
        {
            System.err.println("ERROR: Alert Message Failed!");
        }

    }

    /*
    alert( String message )
    creates a non warning message, with a giving
    input
    EXAMPLE:
    alert("HELLO WORLD");
    */
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

        //Sets the user interface to have updated list of PORTS
        getSerialPorts();
    }

}
