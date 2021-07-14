package eg.edu.guc.yugioh.gui;

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

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.board.player.Player;
import eg.edu.guc.yugioh.exceptions.EmptyFieldException;
import eg.edu.guc.yugioh.exceptions.MissingFieldException;
import eg.edu.guc.yugioh.exceptions.UnknownCardTypeException;
import eg.edu.guc.yugioh.exceptions.UnknownSpellCardException;


public class WindowA extends JFrame implements ActionListener {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	JButton b1 = new JButton();
	Panel p1 = new Panel();
	Panel p2 = new Panel();
	TextField t1 = new TextField();
	TextField t2 = new TextField();
	String s;
	String z;
	Player a ;
	Player b;
	private Board board = new Board();
	
	public WindowA() {
	setTitle("Yu Gi Oh");
	setSize(800, 768);
	setLocation(200, 100);
	setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
	
	Container content = getContentPane();
	content.setBackground(Color.BLACK);
	content.setLayout(new GridLayout(6, 1));
	
	
	p1.setLayout(new GridLayout(1, 1));
	p1.add(new Label("Player1 Name: "));
	t1.addActionListener(this);
	t1.setBackground(Color.cyan);
	t2.setBackground(Color.cyan);
	p1.add(t1);
	p1.setBackground(Color.cyan);
	this.add(p1);
	
	
	p2.setLayout(new GridLayout(1, 1));
	p2.add(new Label("Player2 Name: "));
	t2.addActionListener(this);
	p2.add(t2);
	p2.setBackground(Color.cyan);
	this.add(p2);
	
	b1 = new JButton("New Game");
	b1.setBackground(Color.yellow);
	b1.setActionCommand("b1");
	b1.addActionListener(this);
	content.add(b1);

	
	setVisible(true);
	this.validate();
}
public static void main(String[]args) {
	new WindowA();
}

@Override
public void actionPerformed(ActionEvent e) {
	setVisible(false);
	if (e.getActionCommand().toString().equals("b1")) {
		s = t1.getText();
		z= t2.getText();
		try {
			a = new Player(s);
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MissingFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnknownCardTypeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnknownSpellCardException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (EmptyFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			b = new Player(z);
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MissingFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnknownCardTypeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnknownSpellCardException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (EmptyFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		getBoard().whoStarts(a, b);
		getBoard().startGame(a, b);
		try {
			new WindowB(a, b, this.getBoard());
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MissingFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnknownCardTypeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnknownSpellCardException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (EmptyFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
}
public  Board getBoard() {
	return board;
}
public  void setBoard(Board b) {
	this.board = b;
}

}
