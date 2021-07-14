
package scientific.calculator;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ScientificCalculator.CalculorThreaded;


/**
 *
 * @author omar
 */
public class NewJFrame extends JFrame implements ActionListener  {
	private static JButton [] button = new JButton[36];
    private static TextField tf;
    private JPanel panel;
    private JPanel panel1;
    private CalculorThreaded c = new CalculorThreaded();
    private double num1 , num2;
    private boolean op = false;
    private boolean pointOp = false;
    private String opCode;
    private String x;
    private boolean on = false;
    private String number = "";
    private String number2 = "";
    private boolean equal = false;
    private int counterD = 0;
    private int counterM = 0;
    private int counterS = 0;
    private int counterP = 0;
 public NewJFrame() {
	 super("Scientific Calculator");
	    panel = new JPanel();
	    panel1 = new JPanel(new GridLayout(0,4));

	    tf = new TextField(20);
	    tf.setEditable(false);
	    panel.add(tf);

	    button[0]=new JButton("x^3");
	    button[1]=new JButton("x!");
	    button[2]=new JButton("sqrt");
	    button[3]=new JButton("sin");
	    button[4]=new JButton("cos");
	    button[5]=new JButton("tan");
	    button[6]=new JButton("ln");
	    button[7]=new JButton("log");
	    button[8]=new JButton("1/x");
	    button[9]=new JButton("e^x");
	    button[10]=new JButton("x^2");
	    button[11]=new JButton("x^y");
	    button[12]=new JButton("|x|");
	    button[13]=new JButton("pi");
	    button[14]=new JButton("e");

	    button[15]=new JButton("C");
	    button[16]=new JButton("(");
	    button[17]=new JButton(")");
	    button[18]=new JButton("%");
	    button[19]=new JButton("/");
	    button[20]=new JButton("7");
	    button[21]=new JButton("8");
	    button[22]=new JButton("9");
	    button[23]=new JButton("*");
	    button[24]=new JButton("4");
	    button[25]=new JButton("5");
	    button[26]=new JButton("6");
	    button[27]=new JButton("-");
	    button[28]=new JButton("1");
	    button[29]=new JButton("2");
	    button[30]=new JButton("3");
	    button[31]=new JButton("+");
	    button[32]=new JButton(".");
	    button[33]=new JButton("0");
	    button[34] =new JButton("On/OFF");
	    button[35] =new JButton("=");



	    for(int i = 0; i<button.length;i++){
	        panel1.add(button[i]);
	        button[i].setActionCommand("b"+Integer.toString(i));
	        //String x = "b"+Integer.toString(i);
	       // System.out.println(x);
	        button[i].addActionListener(this);
	        button[i].setEnabled(false);
	        button[34].setEnabled(true);
	    }


	    panel.add(panel1);
	    add(panel);
	    setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);

	    
	    this.setVisible(true);
	    this.validate();

	}
 public static void main (String[]args) {
	 new NewJFrame();
 }
@Override
public void actionPerformed(ActionEvent e)  {
	
	if (e.getActionCommand().toString().equals("b20")){
		if (pointOp == true) {
			x += "7";
			tf.setText(x);
			number = x;
		} else {
			if (equal == true) {
				num1 = 0;
				num2 = 0;
				counterS = 0;
				counterM = 0;
				counterD = 0;
				equal = false;
				pointOp = false;
			}
			number += "7";
			tf.setText(number);
		
	} 
}
	else if (e.getActionCommand().toString().equals("b21")){
		if (pointOp == true) {
			x += "8";
			tf.setText(x);
			number = x;
		} else {
			if (equal == true) {
				num1 = 0;
				num2 = 0;
				counterS = 0;
				counterM = 0;
				counterD = 0;
				equal = false;
				pointOp = false;
			}
			number += "8";
			tf.setText(number);
			
	} 


	} else if (e.getActionCommand().toString().equals("b22")){
		if (pointOp == true) {
			x += "9";
			tf.setText(x);
			number = x;
		} else {
			if (equal == true) {
				num1 = 0;
				num2 = 0;
				counterS = 0;
				counterM = 0;
				counterD = 0;
				equal = false;
				pointOp = false;
			}
			number += "9";
			tf.setText(number);
			
	} 
}
		
	else if (e.getActionCommand().toString().equals("b24")){
		if (pointOp == true) {
			x += "4";
			tf.setText(x);
			number = x;
		} else {
			if (equal == true) {
				num1 = 0;
				num2 = 0;
				counterS = 0;
				counterM = 0;
				counterD = 0;
				equal = false;
				pointOp = false;
			}
			number += "4";
			tf.setText(number);
			
	} 
	
	}else if (e.getActionCommand().toString().equals("b25")){
		if (pointOp == true) {
			x += "5";
			tf.setText(x);
			number = x;
		} else {
			if (equal == true) {
				num1 = 0;
				num2 = 0;
				counterS = 0;
				counterM = 0;
				counterD = 0;
				equal = false;
				pointOp = false;
			}
			number += "5";
			tf.setText(number);
			
	} 
	
	}else if (e.getActionCommand().toString().equals("b26")){
		if (pointOp == true) {
			x += "6";
			tf.setText(x);
			number = x;
		} else {
			if (equal == true) {
				num1 = 0;
				num2 = 0;
				counterS = 0;
				counterM = 0;
				counterD = 0;
				equal = false;
				pointOp = false;
			}
			number += "6";
			tf.setText(number);
			
	} 
	
	}else if (e.getActionCommand().toString().equals("b28")){
		if (pointOp == true) {
			x += "1";
			tf.setText(x);
			number = x;
		} else {
			if (equal == true) {
				num1 = 0;
				num2 = 0;
				counterS = 0;
				counterM = 0;
				counterD = 0;
				equal = false;
				pointOp = false;
			}
			number += "1";
			tf.setText(number);
			
	} 
	
	}else if (e.getActionCommand().toString().equals("b29")){
		if (pointOp == true) {
			x += "2";
			tf.setText(x);
			number = x;
		} else {
			if (equal == true) {
				num1 = 0;
				num2 = 0;
				counterS = 0;
				counterM = 0;
				counterD = 0;
				equal = false;
				pointOp = false;
			}
			number += "2";
			tf.setText(number);
		
	} 
	
	}else if (e.getActionCommand().toString().equals("b30")){
		if (pointOp == true) {
			x += "3";
			tf.setText(x);
			number = x;
		} else {
			if (equal == true) {
				num1 = 0;
				num2 = 0;
				counterS = 0;
				counterM = 0;
				counterD = 0;
				equal = false;
				pointOp = false;
			}
			number += "3";
			tf.setText(number);
	} 
		
	
	}else if (e.getActionCommand().toString().equals("b33")){
		if (pointOp == true) {
			x += "0";
			tf.setText(x);
			number = x;
		} else {
			if (equal == true) {
				num1 = 0;
				num2 = 0;
				counterS = 0;
				counterM = 0;
				counterD = 0;
				equal = false;
				pointOp = false;
			}
			number += "0";
			tf.setText(number);
			
	} 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	}else if (e.getActionCommand().toString().equals("b31")) {
		if (pointOp == true) {
			pointOp = false;
		}
		if (equal == true) {
			equal = false;
		}
		opCode = "+";
		op = true;
		num2 = Double.parseDouble(number);
		try {
			num1 = c.doOperation(opCode, num2, num1);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tf.setText(Double.toString(num1));
		number = "";
		x = "";
	
	} else if (e.getActionCommand().toString().equals("b27")) {
		if (pointOp == true) {
			pointOp = false;
		}
		if (equal == true) {
			equal = false;
		}
		opCode = "-";
		op = true;
		num2 = Double.parseDouble(number);
		if (counterS == 0) {
			num1 = 2 * num2;
		}
		if (num2 < 0) {
			num2 = -num2;
		}
		try {
			num1 = c.doOperation(opCode, num1, num2);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tf.setText(Double.toString(num1));
		number = "";
		counterS ++;
		x = "";
		
	
	} else if (e.getActionCommand().toString().equals("b19")) {
		if (pointOp == true) {
			pointOp = false;
		}
		if (equal == true) {
			equal = false;
		}
		opCode = "/";
		op = true;
		num2 = Double.parseDouble(number);
		if (counterD == 0) {
			num1 = 1;
		}
		try {
			num1 = c.doOperation(opCode, num2, num1);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tf.setText(Double.toString(num1));
		number = "";
		counterD ++;
		x = "";
	
	} else if (e.getActionCommand().toString().equals("b23")) {
		if (pointOp == true) {
			pointOp = false;
		}
		if (equal == true) {
			equal = false;
		}
		opCode = "*";
		op = true;
		num2 = Double.parseDouble(number);
		if (counterM == 0) {
			num1 = 1;
		}
		try {
			num1 = c.doOperation(opCode, num2, num1);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tf.setText(Double.toString(num1));
		number = "";
		counterM ++;
		x = "";
	
		
		
		
		
		
		
		
	
	} else if (e.getActionCommand().toString().equals("b2")) {
		
		if (pointOp == true) {
			pointOp = false;
		}
		opCode = "sqrt";
		if (number.equals("")) {
			
		} else {
			num2 = Double.parseDouble(number);
			num1 = num2;
		} 
		try {
			num1 = c.doOperation(opCode, num1, num2);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tf.setText(Double.toString(num1));
		number = "";
		x = "";
		if (equal == true) {
			equal = false;
		}
		
	
	} else if (e.getActionCommand().toString().equals("b3")) {
		if (pointOp == true) {
			pointOp = false;
		}
		opCode = "sin";
if (number.equals("")) {
			
		} else {
			num2 = Double.parseDouble(number);
			num1 = num2;
		} 
		try {
			num1 = c.doOperation(opCode, num1, num2);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tf.setText(Double.toString(num1));
		number = "";
		x = "";
		if (equal == true) {
			equal = false;
		}
	
	} else if (e.getActionCommand().toString().equals("b4")) {
		if (pointOp == true) {
			pointOp = false;
			
		}
		opCode = "cos";
if (number.equals("")) {
			
		} else {
			num2 = Double.parseDouble(number);
			num1 = num2;
		} 
		try {
			num1 = c.doOperation(opCode, num1, num2);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tf.setText(Double.toString(num1));
		number = "";
		x = "";
		if (equal == true) {
			equal = false;
		}
	
	} else if (e.getActionCommand().toString().equals("b5")) {
		if (pointOp == true) {
			pointOp = false;
			
		}
		opCode = "tan";
if (number.equals("")) {
			
		} else {
			num2 = Double.parseDouble(number);
			num1 = num2;
		} 
		try {
			num1 = c.doOperation(opCode, num1, num2);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tf.setText(Double.toString(num1));
		number = "";
		x = "";
		if (equal == true) {
			equal = false;
		}
	
	} else if (e.getActionCommand().toString().equals("b6")) {
		if (pointOp == true) {
			pointOp = false;
			
		}
		opCode = "ln";
if (number.equals("")) {
			
		} else {
			num2 = Double.parseDouble(number);
			num1 = num2;
		} 
		try {
			num1 = c.doOperation(opCode, num1, num2);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tf.setText(Double.toString(num1));
		number = "";
		x = "";
		if (equal == true) {
			equal = false;
		}
	
	} else if (e.getActionCommand().toString().equals("b7")) {
		if (pointOp == true) {
			pointOp = false;
			
		}
		opCode = "log";
if (number.equals("")) {
			
		} else {
			num2 = Double.parseDouble(number);
			num1 = num2;
		} 
		try {
			num1 = c.doOperation(opCode, num1, num2);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tf.setText(Double.toString(num1));
		number = "";
		x = "";
		if (equal == true) {
			equal = false;
		}
	
	} else if (e.getActionCommand().toString().equals("b8")) {
		if (pointOp == true) {
			pointOp = false;
			
		}
		opCode = "1/x";
if (number.equals("")) {
			
		} else {
			num2 = Double.parseDouble(number);
			num1 = num2;
		} 
		try {
			num1 = c.doOperation(opCode, num1, num2);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tf.setText(Double.toString(num1));
		number = "";
		x = "";
		if (equal == true) {
			equal = false;
		}
	
	} else if (e.getActionCommand().toString().equals("b9")) {
		if (pointOp == true) {
			pointOp = false;
			
		}
		opCode = "e^x";
if (number.equals("")) {
			
		} else {
			num2 = Double.parseDouble(number);
			num1 = num2;
		} 
		try {
			num1 = c.doOperation(opCode, num1, num2);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tf.setText(Double.toString(num1));
		number = "";
		x = "";
		if (equal == true) {
			equal = false;
		}
		
	
	} else if (e.getActionCommand().toString().equals("b10")) {
		if (pointOp == true) {
			pointOp = false;
			
		}
		opCode = "x^2";
if (number.equals("")) {
			
		} else {
			num2 = Double.parseDouble(number);
			num1 = num2;
		} 
		try {
			num1 = c.doOperation(opCode, num1, num2);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tf.setText(Double.toString(num1));
		number = "";
		x = "";
		if (equal == true) {
			equal = false;
		}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	} else if (e.getActionCommand().toString().equals("b11")) {
		if (pointOp == true) {
			pointOp = false;
			
		}
		opCode = "x^y";
		op = true;
		if (number.equals("") && counterP !=0) {
			num2 = 1;
		} else {
		num2 = Double.parseDouble(number);
		}
		if (counterP == 0) {
			num1 = num2;
			num2 =1;
		}
		try {
			num1 = c.doOperation(opCode, num1, num2);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tf.setText(Double.toString(num1));
		number = "";
		counterP ++;
		x = "";
		if (equal == true) {
			equal = false;
		}
		
	} else if (e.getActionCommand().toString().equals("b12")) {
		if (pointOp == true) {
			pointOp = false;
			
		}
		opCode = "|x|";
		if (number.equals("")){
			num2 = 0;
		}
		num2 = Double.parseDouble(number);
		if (num2 != num1 && (!number.equals(""))) {
			num1 = num2;
		} 
		try {
			num1 = c.doOperation(opCode, num1, num2);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tf.setText(Double.toString(num1));
		number = "";
		x = "";
		if (equal == true) {
			equal = false;
		}
	
	} else if (e.getActionCommand().toString().equals("b13")) {
		if (pointOp == true) {
			pointOp = false;
			
		}
		num1 = 3.141592653589793;
		tf.setText(Double.toString(num1));
		number = "";
		number2 = "";
		x = "";
		
	
	} else if (e.getActionCommand().toString().equals("b14")) {
		if (pointOp == true) {
			pointOp = false;
			
		}
		num1 = 2.718281828459045;
		tf.setText(Double.toString(num1));
		number = "";
		number2 = "";
		x = "";
		if (equal == true) {
			equal = false;
		}
		
	
	}  else if (e.getActionCommand().toString().equals("b15")) {
		button[15].setText("AC");
		num1 = 0;
		num2 = 0;
		op = false;
		opCode = null;
		pointOp = false;
		tf.setText("0");
		number = "";
		number2 = "";
		counterM = 0;
		counterD = 0;
		counterS = 0;
		counterP = 0;
		x = "";
		if (equal == true) {
			equal = false;
		}
	
	} else if (e.getActionCommand().toString().equals("b18")) {
		if (pointOp == true) {
			pointOp = false;
			
		}
		num1 = Double.parseDouble(number);
		num1 /= 100;
		tf.setText(Double.toString(num1));
		number = "";
		number2 = "";
		x = "";
		if (equal == true) {
			equal = false;
		}
	
	} else if (e.getActionCommand().toString().equals("b1")) {
		if (pointOp == true) {
			pointOp = false;
			
		}
		opCode = "x!";
if (number.equals("")) {
			
		} else {
			num2 = Double.parseDouble(number);
			num1 = num2;
		} 
		try {
			num1 = c.doOperation(opCode, num1, num2);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tf.setText(Double.toString(num1));
		number = "";
		x = "";
		if (equal == true) {
			equal = false;
		}
	
	} else if (e.getActionCommand().toString().equals("b35")) {
		if (pointOp == true) {
			pointOp = false;
			
		}
		num2 = Double.parseDouble(number);
		try {
			num1 = c.doOperation(opCode, num1, num2);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tf.setText(Double.toString(num1));
		number = "";
		number2 = "";
		equal = true;
		x = "";
	
	} else if (e.getActionCommand().toString().equals("b0")) {
		if (pointOp == true) {
			pointOp = false;
			
		}
		opCode = "x^3";
if (number.equals("")) {
			
		} else {
			num2 = Double.parseDouble(number);
			num1 = num2;
		} 
		try {
			num1 = c.doOperation(opCode, num1, num2);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tf.setText(Double.toString(num1));
		number = "";
		x = "";
		if (equal == true) {
			equal = false;
		}
	
	} else if (e.getActionCommand().toString().equals("b17")) {
		if (pointOp == true) {
			pointOp = false;
			
		}
		num2 = Double.parseDouble(number);
		try {
			num1 = c.doOperation(opCode, num1, num2);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tf.setText(Double.toString(num1));
		number = "";
		number2 = "";
		equal = true;
		x = "";
	
	} else if (e.getActionCommand().toString().equals("b32")) {
		pointOp = true;
		
		 x = Integer.toString((int)num1) + ".";
		 number = "";
		 number2 = "";
		//System.out.println(x);
	
	} else if (e.getActionCommand().toString().equals("b34")) {
		if (on == false) {
			tf.setText("");
			for(int i = 0; i<button.length;i++){
		        button[i].setEnabled(true);
		        on = true;
		        num1 = 0;
		        num2 = 0;
		        pointOp = false;
		        op = false;
		        number = "";
		        tf.setText("");
		        number2 = "";
		        counterM = 0;
				counterD = 0;
				counterS = 0;
				counterP = 0;
				x = "";
		        
		    }
		} else {
			tf.setText("");
			for(int i = 0; i<button.length;i++){
		        button[i].setEnabled(false);
		        button[34].setEnabled(true);
		        on = false;
		        num1 = 0;
		        num2 = 0;
		        pointOp = false;
		        op = false;
		        number = "";
		        tf.setText("");
		        number2 = "";
		        counterM = 0;
				counterD = 0;
				counterS = 0;
				counterP = 0;
				x = "";
		        
		    }
		}
	
	}
 }
}
	
 



