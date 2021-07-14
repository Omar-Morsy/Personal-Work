package eg.edu.guc.yugioh.gui;


import java.awt.Color;
import java.awt.Container;







import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.board.player.Player;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.exceptions.EmptyFieldException;
import eg.edu.guc.yugioh.exceptions.MissingFieldException;
import eg.edu.guc.yugioh.exceptions.UnknownCardTypeException;
import eg.edu.guc.yugioh.exceptions.UnknownSpellCardException;

public class WindowE extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MonsterCard m;
	Player p1; 
	Player p2;
	Board board;
	public WindowE(String s,Player p1, Player p2, Board b){
		
		this.p1 = p1;
		this.p2 = p2;
		this.board = b;
		
		setTitle("Yu Gi Oh");
		setSize(500, 500);
		setLocation(200, 100);
		
		Container content = getContentPane();
		content.setLayout(new GridLayout(2,1));
		content.setBackground(Color.CYAN);
		content.add(new JLabel(s));
		JButton b1 = new JButton("Ok return to Board");
		b1.setBackground(Color.YELLOW);
		b1.setActionCommand("ok");
		b1.addActionListener(this);
		content.add(b1);
		
		setVisible(true);
		this.validate();
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		setVisible(false);
		if (e.getActionCommand().equals("ok")){
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
