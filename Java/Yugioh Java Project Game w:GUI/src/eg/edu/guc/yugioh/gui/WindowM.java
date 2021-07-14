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
import eg.edu.guc.yugioh.exceptions.EmptyFieldException;
import eg.edu.guc.yugioh.exceptions.MissingFieldException;
import eg.edu.guc.yugioh.exceptions.MultipleMonsterAdditionException;
import eg.edu.guc.yugioh.exceptions.NoMonsterSpaceException;
import eg.edu.guc.yugioh.exceptions.UnknownCardTypeException;
import eg.edu.guc.yugioh.exceptions.UnknownSpellCardException;
import eg.edu.guc.yugioh.exceptions.WrongPhaseException;

public class WindowM extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Player p;
	MonsterCard m;
	Player p1; 
	Player p2;
	Board board;

public WindowM(MonsterCard m, Player P, Player p1, Player p2, Board b) {
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
	content.setBackground(Color.CYAN);
	JButton set = new JButton("Set this Monster in the Field");
	JButton summon = new JButton("Summon this Monster in the Field");
	JButton d = new JButton("Get the Description of this Monster");
	d.setBackground(Color.WHITE);
	d.setActionCommand("des");
	d.addActionListener(this);
	set.setBackground(Color.orange);
	summon.setBackground(Color.YELLOW);
	JButton c = new JButton("Cancel and return to the Board");
	c.setActionCommand("cancel");
	c.setBackground(Color.orange);
	c.addActionListener(this);
	set.setActionCommand("set");
	summon.setActionCommand("summon");
	set.addActionListener(this);
	summon.addActionListener(this);
	content.add(set);
	content.add(summon);
	content.add(d);
	content.add(c);
	
	setVisible(true);
	this.validate();
}	

public static void main(String[]args) {
	
}

@Override
public void actionPerformed(ActionEvent e) {
	if (e.getActionCommand().equals("set")) {
		setVisible(false);
		
		if (m.getLevel() > 4) {
			if (m.getLevel()<7 && Card.getBoard().getActivePlayer().getField().getMonstersArea().size() < 1) {
				new NoMonsterForSacrifice(p1, p2, board);
				return;
			}
            if (m.getLevel()>=7 && Card.getBoard().getActivePlayer().getField().getMonstersArea().size() < 2) {
            	new NoMonsterForSacrifice(p1, p2, board);
            	return;
			}
			new WindowSummonSacrifice(m, Card.getBoard().getActivePlayer(), p1, p2, board);
			return;
		} else {
		
		try {
		Card.getBoard().getActivePlayer().setMonster(m);
		}catch(NoMonsterSpaceException x) {
			new WindowE("No Space for Adding more Monster in Monsters area (Invalid Action)",p1,p2,board);
			return;
		}catch(WrongPhaseException x) {
			new WindowE("Wrong Phase Exception (Invalid Action)",p1,p2,board);
			return;
		}catch(MultipleMonsterAdditionException x) {
			new WindowE("Monster Multiple Addition Exception (Invalid Action)",p1,p2,board);
			return;
		}
		
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
	if (e.getActionCommand().equals("summon")) {
		setVisible(false);
		if (m.getLevel() > 4) {
			if (m.getLevel()<7 && Card.getBoard().getActivePlayer().getField().getMonstersArea().size() < 1) {
				new NoMonsterForSacrifice(p1, p2, board);
				return;
			}
            if (m.getLevel()>=7 && Card.getBoard().getActivePlayer().getField().getMonstersArea().size() < 2) {
            	new NoMonsterForSacrifice(p1, p2, board);
            	return;
			}
			new WindowSummonSacrifice(m, Card.getBoard().getActivePlayer(), p1, p2, board);
			return;
		} else {
		try{
		Card.getBoard().getActivePlayer().summonMonster(m);
		}catch(NoMonsterSpaceException x) {
			new WindowE("No Space for Adding more Monster in Monsters area (Invalid Action)",p1,p2,board);
			return;
		}catch(WrongPhaseException x) {
			new WindowE("Wrong Phase Exception (Invalid Action)",p1,p2,board);
			return;
		}catch(MultipleMonsterAdditionException x) {
			new WindowE("Monster Multiple Addition Exception (Invalid Action)",p1,p2,board);
			return;
		}
		
		
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
