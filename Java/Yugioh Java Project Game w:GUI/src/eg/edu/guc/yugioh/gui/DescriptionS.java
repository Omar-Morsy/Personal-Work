package eg.edu.guc.yugioh.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.board.player.Player;
import eg.edu.guc.yugioh.cards.spells.SpellCard;
import eg.edu.guc.yugioh.exceptions.EmptyFieldException;
import eg.edu.guc.yugioh.exceptions.MissingFieldException;
import eg.edu.guc.yugioh.exceptions.UnknownCardTypeException;
import eg.edu.guc.yugioh.exceptions.UnknownSpellCardException;

public class DescriptionS extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	SpellCard s;
	Player p1; 
	Player p2;
	Board board;

	public DescriptionS(SpellCard s, Player p1, Player p2, Board b) {
this.s = s;
		
		this.p1 = p1;
		this.p2 = p2;
		this.board = b;
		
		setTitle("Yu Gi Oh");
		setSize(700, 500);
		setLocation(200, 100);
		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		
		GridBagConstraints con = new GridBagConstraints();
		con.fill = GridBagConstraints.HORIZONTAL;
		con.gridx = 1;
		con.gridy = 0;
		con.ipady = 800;
		con.ipadx = 500;
		con.weightx = 2	; 
		
		Container content = getContentPane();
		content.setLayout(new GridLayout(2,1));
		JLabel j = new JLabel(s.getDescription());
		j.setBackground(Color.CYAN);
		content.add(j);
		
		JButton c = new JButton("Return to the Board");
		c.setActionCommand("cancel");
		c.setBackground(Color.cyan);
		c.addActionListener(this);
		content.add(c);
		
		
		setVisible(true);
		this.validate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("cancel")){
			setVisible(false);
			
			try {
				new WindowB(p1,p2,board);
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

}
