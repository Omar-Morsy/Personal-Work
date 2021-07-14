package eg.edu.guc.yugioh.gui;



import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;







import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.board.player.Player;
import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.exceptions.DefenseMonsterAttackException;
import eg.edu.guc.yugioh.exceptions.EmptyFieldException;
import eg.edu.guc.yugioh.exceptions.MissingFieldException;
import eg.edu.guc.yugioh.exceptions.MonsterMultipleAttackException;
import eg.edu.guc.yugioh.exceptions.UnknownCardTypeException;
import eg.edu.guc.yugioh.exceptions.UnknownSpellCardException;
import eg.edu.guc.yugioh.exceptions.WrongPhaseException;


public class WindowMattack extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Player p;
	MonsterCard m;
	Player p1; 
	Player p2;
	Board board;
	
	
public WindowMattack(MonsterCard m, Player P, Player p1, Player p2, Board b) {
	this.p = P;
	this.m = m;
	
	this.p1 = p1;
	this.p2 = p2;
	this.board = b;
	
	setTitle("Yu Gi Oh");
	setSize(700, 500);
	setLocation(200, 100);
	
	
	
	
	Container content = getContentPane();
	content.setLayout(new GridLayout(4,1));
	content.setBackground(Color.CYAN);;
	
	
	JButton d = new JButton("Get the Description of this Monster");
	d.setBackground(Color.WHITE);
	d.setActionCommand("des");
	d.addActionListener(this);
	
	JButton b1 = new JButton("Declare Attack with this Monster");
	JButton b2 = new JButton("Switch this Monster Mode");
	b1.setBackground(Color.ORANGE);
	b2.setBackground(Color.YELLOW);
	JButton c = new JButton("Cancel and return to the Board");
	c.setActionCommand("cancel");
	c.setBackground(Color.blue);
	c.addActionListener(this);
	b1.setActionCommand("declareattack");
	b2.setActionCommand("switchmode");
	b1.addActionListener(this);
	b2.addActionListener(this);
	content.add(b1);
	content.add(b2);
	content.add(d);
	content.add(c);
	
	
	
	setVisible(true);
	this.validate();
}

public static void main(String[]args) {

}
@Override
public void actionPerformed(ActionEvent e) {
	if (e.getActionCommand().equals("declareattack")) {
		setVisible(false);
		
		try {
		if (Card.getBoard().getOpponentPlayer().getField().getMonstersArea().isEmpty()) {
		Card.getBoard().getActivePlayer().declareAttack(m);
		} else {
			new WindowChooseMonstertoAttack(p,m,p1,p2,board);
		return;
		}
		}catch(WrongPhaseException x) {
			new WindowE("Wrong Phase Exception (Invalid Action)",p1,p2,board);
			return;
		}catch(MonsterMultipleAttackException x) {
			new WindowE("Monster Multiple Attack Exception (Invalid Action)",p1,p2,board);
			return;
		}catch(DefenseMonsterAttackException x) {
			new WindowE("Can't Attack with a Monster in Defense Mode (Invalid Action)",p1,p2,board);
			return;
		}
		
		try {
			new WindowB(p1, p2, board);
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
	if (e.getActionCommand().equals("switchmode")) {
		setVisible(false);
		
		try{
		Card.getBoard().getActivePlayer().switchMonsterMode(m);
		}catch(WrongPhaseException x) {
			new WindowE("Wrong Phase Exception (Invalid Action)",p1,p2,board);
			return;
		}
		
		try {
			new WindowB(p1, p2, board);
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
	if (e.getActionCommand().equals("des")) {
		setVisible(false);
		new DescriptionM(m, p1, p2, board);
	}
}
}
