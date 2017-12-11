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

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
/**
 * wisemonkey
 */
// TODO: 12/10/2017 fix ui
// TODO: 12/10/2017   prompt more descriptive
// xTODO: 12/10/2017  output textbox
// xTODO: 12/10/2017  text box select
// xTODO: 12/10/2017 fix crash upon '++' instead of +
// xTODO: 12/10/2017  crash "+" + <enter>
// TODO: 12/10/2017 Add Delete
// TODO: 12/10/2017 Generalize operators
// TODO: 12/10/2017 Add *
// TODO: 12/10/2017 Add /
// TODO: 12/10/2017 Add -
// TODO: 12/10/2017 change output on repeate enter clear screen
// xTODO: 12/10/2017 Clear output upon pressing 'c' - clear
// TODO: 12/10/2017  fix input leaving the 'c' character in the input!

public class UIController implements Initializable
{
	private String current_input = "";
	private int result = 0;

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

		input.setOnKeyPressed((KeyEvent keypress) ->
		{

			String key = keypress.getText();


			System.out.println("Key Input => '" + key + "'");


			if (key.equals("+") && isNumeric(current_input))
			{
				key = "";
				result = Integer.parseInt(current_input);
				current_input = "";
				math_op = Math_Op.plus;
			}
			if (key.equals("*") && isNumeric(current_input))
			{
				key = "";
				result = Integer.parseInt(current_input);
				current_input = "";
				math_op = Math_Op.multiply;
			}

			if (keypress.getText().equals("c"))
			{
				System.out.println("T-c");
				key = "";
				current_input = "";
				result = 0;
				input.setText("");
				math_op = Math_Op.None;

			}
			if (keypress.getCode().getName().equals("Enter") && isNumeric(current_input))
			{
				System.out.println("Enter");
				int current_number = Integer.parseInt(current_input);

				switch (math_op){
					case plus     : result = result + current_number; break;
					case multiply : result = result * current_number; break;
					default:
						System.err.println("Operator Not Found!");
				}
				System.out.println("T- Is Enterkey input :" + result + "\n\n\n\n");
				output.setText(String.valueOf(result));
			}else{
				System.err.println("F- Is Enterkey input  numeric:fail!");
			}

			if (key.matches("[0-9]") || key.matches("[+\\-*/]"))
			{
				System.out.println("T: matches TRUE regex not number or +,-,/,* " + current_input);
				current_input += key;
			} else
			{
				System.err.println("F: matches FAIL regex not number or +,-,/,* " + current_input);
			}
		});
	}

}
