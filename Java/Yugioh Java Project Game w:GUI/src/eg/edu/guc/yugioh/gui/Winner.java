package eg.edu.guc.yugioh.gui;


import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import eg.edu.guc.yugioh.board.Board;

public class Winner extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public Winner(Board board) {
	setTitle("Yu Gi Oh");
	setSize(700, 500);
	setLocation(200, 100);
	
	getContentPane().setLayout(new GridLayout(3,1));
	getContentPane().setBackground(Color.CYAN);
	JButton b = new JButton("Play Again");
	JLabel j = new JLabel("The Winner is : "+board.getWinner().getName());
	JLabel j1 = new JLabel("Thank you for playing Yugioh");
	j.setBackground(Color.orange);
	j1.setBackground(Color.YELLOW);
	b.setActionCommand("playAgain");
	b.setBackground(Color.YELLOW);
	b.addActionListener(this);
	getContentPane().add(j);
	getContentPane().add(b);
	getContentPane().add(j1);
	
	setVisible(true);
	this.validate();
}

@Override
public void actionPerformed(ActionEvent e) {
	if (e.getActionCommand().equals("playAgain")) {
		setVisible(false);
		new WindowA();
	}
	
}
}
