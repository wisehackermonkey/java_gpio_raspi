// TODO: 12/29/2017 header comment
/*
 Java Final Project Calculator
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


// TODO: 12/19/17 Select serial port
// TODO: 12/11/2017 Comments
// TODO: 12/10/2017 change output on repeate enter clear screen

// TODO: 12/29/2017 docs
//    get help message on what to do
//            error message if not arduino
//            message if not working with arduino
//            clear descripton of what the buttons do
//
//
//            set by set of what to do to do to get started
//            example how to add number
// TODO: 12/29/2017 install
// TODO: 12/29/2017 setup
// xTODO: 12/29/2017 photo
// xTODO: 12/29/2017 explain what is + = A etc
// TODO: 12/29/2017 comments
// TODO: 12/29/2017 set comment markers

// TODO: 12/29/2017 cleanup code
// TODO: 12/29/2017         ans #
// TODO: 12/29/2017         ans +
// TODO: 12/29/2017         ans -
// TODO: 12/29/2017         ans *
// TODO: 12/29/2017         ans %

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


    @FXML
    private void btn_connect_press()
    {
            // TODO: 12/29/2017 remove
            if(btn_connect.getText().equals("Connect"))
            {
                try
                {
                    if(port_selection.getValue() == null)
                    {
                        error("SELECT PORT: EXAMPLE      port > \"COM3\" or '/dev/ttyACM0'");
                    } else
                    {

                        String portName = port_selection.getValue();

                        serialPort = SerialPort.getCommPort(portName);

                        //set port to scanner mode lets reading in characters without quitting early
                        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 1000, 1000);

                        //open port
                        // TODO: 12/28/2017 error handling
                        if(serialPort.openPort())
                        {
                            message.setText(String.format("Port: %s %n", portName));
                            alert(String.format("NOTE: If buttons NOT working, TRY other PORT, than port: %s %n", portName));
                            alert(String.format("Port Connected!: %s %n", portName));
                            btn_connect.setText("Disconnect");

                            //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/ComboBoxBase.html#isEditable--
                            port_selection.setEditable(false);
                        }

                        // TODO: 12/29/2017 comment
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
                // TODO: 12/29/2017 comment
                serialPort.closePort();
                btn_connect.setText("Connect");
                port_selection.setEditable(true);

                //clear calculator when disconnecting
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

    // TODO: 12/29/2017 comment
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


    // TODO: 12/29/2017 comment
    //HELPER FUNCTIONS
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

    // TODO: 12/29/2017 comment
    private void alert( String message )
    {
        alert_set(message, Alert.AlertType.INFORMATION);
    }
    // TODO: 12/29/2017 comment
    private void error( String error_message )
    {
        alert_set(error_message, Alert.AlertType.ERROR);
    }
    // TODO: 12/29/2017 comment
    @Override
    public void initialize( URL url, ResourceBundle rb )
    {
        System.out.println("Calculator Started");

        getSerialPorts();
    }

}
