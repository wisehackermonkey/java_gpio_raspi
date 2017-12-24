/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.srjc.Final.Oran.Collins;

import java.net.URL;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
//import jssc.SerialPort;
import jssc.*;

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

//https://docs.oracle.com/javafx/2/ui_controls/combo-box.htm
    @FXML
    private TextField input;
    ObservableList<String> options =
            FXCollections.observableArrayList(
                    "Option 1",
                    "Option 2",
                    "Option 3"
            );
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

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        System.out.println("Calculator Started");
//        SerialPort serialPort = new SerialPort();
//
//        SerialPort test = new SerialPort("/dev/ttyACM0");
//
//                    test.addEventListener(event -> {
//                        if(event.isRXCHAR()) {
//                            try {
//                                StringBuilder sb = null;
//                                sb.append(test.readString(event.getEventValue()));
//                                String str = sb.toString();
//
//                                if(str.endsWith("\r\n")) {
//                                    line.set(Long.toString(time.getTime()).concat(":").concat(
//                                            str.substring(0, str.indexOf("\r\n"))));
//                                     sb = new StringBuilder();
//                                }
//                            } catch (SerialPortException ex) {
//                                Logger.getLogger(SerialComm.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                        }
//                    });
//
//            } catch (Exception e) {
//                    System.err.println(e.toString());
//                    }

        }
//  https://stackoverflow.com/questions/29719405/serial-connection-arduino-java#29720451
    public class SerialComm  implements SerialPortEventListener {
        Date time = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("mm");

        boolean connected;
        StringBuilder sb;
        private SerialPort serialPort;

        final StringProperty line = new SimpleStringProperty("");

        //Not sure this is necessary
        private  final String [] PORT_NAMES = {
                "/dev/tty.usbmodem1411", // Mac OS X
                "COM11", // Windows
        };
        //Baud rate of communication transfer with serial device
        public static final int DATA_RATE = 9600;

        //Create a connection with the serial device
        public boolean connect() {
            String [] ports = SerialPortList.getPortNames();
            //First, Find an instance of serial port as set in PORT_NAMES.
            for (String port : ports) {
                System.out.print("Ports: " + port);
                serialPort = new SerialPort(port);
            }
            if (serialPort == null) {
                System.out.println("Could not find device.");
                return false;
            }

            //Operation to perform is port is found
            try {
                // open serial port
                if(serialPort.openPort()) {
                    System.out.println("Connected");
                    // set port parameters
                    serialPort.setParams(DATA_RATE,
                            SerialPort.DATABITS_8,
                            SerialPort.STOPBITS_1,
                            SerialPort.PARITY_NONE);
                    serialPort.setEventsMask(SerialPort.MASK_RXCHAR);

                    serialPort.addEventListener(event -> {
                        if(event.isRXCHAR()) {
                            try {
                                sb.append(serialPort.readString(event.getEventValue()));
                                String str = sb.toString();
                                if(str.endsWith("\r\n")) {
                                    line.set(Long.toString(time.getTime()).concat(":").concat(
                                            str.substring(0, str.indexOf("\r\n"))));
                                    System.out.println("line" + line);

                                    sb = new StringBuilder();
                                }
                            } catch (SerialPortException ex) {
                                Logger.getLogger(SerialComm.class.getName()).log(Level.SEVERE, null, ex);                    }
                        }
                    });
                }
            } catch (Exception e) {
                System.out.println("ErrOr");
                e.printStackTrace();
                System.err.println(e.toString());
            }
            return serialPort != null;
        }

        @Override
        public void serialEvent(SerialPortEvent spe) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public StringProperty getLine() {
            return line;
        }
    }

}
