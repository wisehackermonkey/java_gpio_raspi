package arduino;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

//public class Main {
//    public static void main(String[] args) {
//        SerialPort serialPort = new SerialPort("/dev/ttyACM0");
//        try {
//            serialPort.openPort();//Open serial port
//            serialPort.setParams(9600, 8, 1, 0);//Set params.
//            byte[] buffer = serialPort.readBytes(10);//Read 10 bytes from serial port
//
//            serialPort.closePort();//Close serial port
//        }
//        catch (SerialPortException ex) {
//            System.out.println(ex);
//        }
//    }
//}
//http://arduino-er.blogspot.com/2014/03/bi-direction-serial-communication-using.html

public class Main extends Application {

    SerialPort serialPort;
    ObservableList<String> portList;

    ComboBox comboBoxPorts;
    TextField textFieldOut, textFieldIn;
    Button btnOpenSerial, btnCloseSerial, btnSend;

    private void detectPort() {

        portList = FXCollections.observableArrayList();

        String[] serialPortNames = SerialPortList.getPortNames();
        for (String name : serialPortNames) {
            System.out.println(name);
            portList.add(name);
        }
    }

    @Override
    public void start(Stage primaryStage) {

        detectPort();

        comboBoxPorts = new ComboBox(portList);
        textFieldOut = new TextField();
        textFieldIn = new TextField();

        btnOpenSerial = new Button("Open Serial Port");
        btnCloseSerial = new Button("Close Serial Port");
        btnSend = new Button("Send");
        btnSend.setDisable(true);   //default disable before serial port open

        btnOpenSerial.setOnAction(t -> {
            closeSerialPort();              //close serial port before open
            if(openSerialPort()){
                btnSend.setDisable(false);
            }else{
                btnSend.setDisable(true);
            }
        });

        btnCloseSerial.setOnAction(t -> {
            closeSerialPort();
            btnSend.setDisable(true);
        });

        btnSend.setOnAction(t -> {

            if(serialPort != null && serialPort.isOpened()){
                try {
                    String stringOut = textFieldOut.getText();
                    serialPort.writeBytes(stringOut.getBytes());
                } catch (SerialPortException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                System.out.println("Something wrong!");
            }
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(
                comboBoxPorts,
                textFieldOut,
                textFieldIn,
                btnOpenSerial,
                btnCloseSerial,
                btnSend);

        StackPane root = new StackPane();
        root.getChildren().add(vBox);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setOnCloseRequest(t -> closeSerialPort());

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private boolean openSerialPort() {
        boolean success = false;

        if (comboBoxPorts.getValue() != null
                && !comboBoxPorts.getValue().toString().isEmpty()) {
            try {
                serialPort = new SerialPort(comboBoxPorts.getValue().toString());

                serialPort.openPort();
                serialPort.setParams(
                        SerialPort.BAUDRATE_9600,
                        SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);

                serialPort.addEventListener(new MySerialPortEventListener());

                success = true;
            } catch (SerialPortException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return success;
    }

    private void closeSerialPort() {
        if (serialPort != null && serialPort.isOpened()) {
            try {
                serialPort.closePort();
            } catch (SerialPortException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        serialPort = null;
    }

    class MySerialPortEventListener implements SerialPortEventListener {

        @Override
        public void serialEvent(SerialPortEvent serialPortEvent) {

            if(serialPortEvent.isRXCHAR()){
                try {
                    int byteCount = serialPortEvent.getEventValue();
                    byte bufferIn[] = serialPort.readBytes(byteCount);

                    String stringIn = "";
                    try {
                        stringIn = new String(bufferIn, "UTF-8");
                    } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println(stringIn);
                    textFieldIn.setText(stringIn);

                } catch (SerialPortException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }

    }
}

