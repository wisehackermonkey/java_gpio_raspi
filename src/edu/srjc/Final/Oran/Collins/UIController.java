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

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * wisemonkey
 */
// TODO: 12/10/2017 fix ui
// TODO: 12/10/2017  text box select
// TODO: 12/10/2017 fix crash upon '++' instead of +
// TODO: 12/10/2017  crash "+" + <enter>
// TODO: 12/10/2017 Add Delete
// TODO: 12/10/2017 Add *
// TODO: 12/10/2017 Add /
// TODO: 12/10/2017 Add -

public class UIController implements Initializable
{
	private String current_input = "";
	private int result = 0;


	@FXML
	private TextField output;

	@FXML
	private TextField input;

	@FXML
	private void handleInput(KeyEvent e)
	{

		System.out.println("Can't you follow directions?" + e.getCharacter());

	}

	private boolean sanitize_input(KeyEvent key)
	{
		return false;
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
		String buffer = "";

		input.setOnKeyPressed((KeyEvent keypress) ->
		{

			String key = keypress.getText();


			if (key.equals(""))
			{
				System.out.println("T-input => ''");
			} else
			{
				System.out.println("F-input => '" + key + "'");
			}


			if (key.equals("+"))
			{
				System.out.println("T");
				key = "";
				if (isNumeric(current_input))
				{
					System.out.println("----T-isnumeric current input : " + current_input);
					result = Integer.parseInt(current_input);
					current_input = "";
				} else
				{
					System.err.println("----F-isnumeric current input : " + current_input);
				}


			}

			if (keypress.getCode().getName().equals("Enter"))
			{
				System.out.println("EXIT");
				if (isNumeric(current_input))
				{

					result = result + Integer.parseInt(current_input);
					System.out.println("T- Is Enterkey input :" + result);
				} else
				{
					System.out.println("F- Is Enterkey input  numeric:fail!");
				}
			}
			if (key.matches("[0-9+\\-*/]"))
			{
				System.out.println("-T");
				current_input += key;
			} else
			{
				System.out.println("F: matches FAIL regex not number or +,-,/,* " + current_input);
			}
			if (keypress.getText().equals("c"))
			{
				System.out.println("T-c");
				key = "";
				current_input = "";
				result = 0;

			}
			//System.out.println("Key Pressed: " + key + ", Keycode: " + keypress.getCode().getName() + ", Keystream: " + current_input);
		});
	}

}
