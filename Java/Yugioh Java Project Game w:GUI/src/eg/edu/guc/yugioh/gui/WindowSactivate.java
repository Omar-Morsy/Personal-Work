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
import javax.swing.JPanel;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.board.player.Player;
import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.spells.ChangeOfHeart;
import eg.edu.guc.yugioh.cards.spells.MagePower;
import eg.edu.guc.yugioh.cards.spells.MonsterReborn;
import eg.edu.guc.yugioh.cards.spells.SpellCard;
import eg.edu.guc.yugioh.exceptions.EmptyFieldException;
import eg.edu.guc.yugioh.exceptions.MissingFieldException;
import eg.edu.guc.yugioh.exceptions.NoSpellSpaceException;
import eg.edu.guc.yugioh.exceptions.UnknownCardTypeException;
import eg.edu.guc.yugioh.exceptions.UnknownSpellCardException;
import eg.edu.guc.yugioh.exceptions.WrongPhaseException;

public class WindowSactivate extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Player p;
	SpellCard s;
	Player p1; 
	Player p2;
	Board board;
public WindowSactivate(SpellCard s, Player P, Player p1, Player p2, Board b) {
	this.p = P;
	this.s = s;
	
	this.p1 = p1;
	this.p2 = p2;
	this.board = b;
	
	setTitle("Yu Gi Oh");
	setSize(700, 500);
	setLocation(200, 100);
	
	Container content = getContentPane();
	content.setLayout(new GridLayout(3,1));
	content.setBackground(Color.CYAN);
	
	JButton d = new JButton("Get the Description of this Spell");
	d.setBackground(Color.WHITE);
	d.setActionCommand("des");
	d.addActionListener(this);
	
	JPanel panel = new JPanel();
	panel.setMaximumSize(getMaximumSize());
	panel.setBackground(Color.YELLOW);
	panel.setLayout(new GridLayout(1,2));
	
	
	JLabel j = new JLabel("Do you Want to Activate this Spell");
	content.add(j);
	JButton yes = new JButton("Yes");
	JButton no = new JButton("No");
	yes.setBackground(Color.orange);
	no.setBackground(Color.YELLOW);
	yes.setActionCommand("yes");
	no.setActionCommand("no");
	yes.addActionListener(this);
	no.addActionListener(this);
	panel.add(yes);
	panel.add(no);
	content.add(panel);
	content.add(d);
	setVisible(true);
	this.validate();
}
@Override
public void actionPerformed(ActionEvent e) {
	
	if (e.getActionCommand().equals("yes")) {
		setVisible(false);
		
		if (s instanceof MagePower) {
			new WindowMagePowerMonster(Card.getBoard().getActivePlayer(),s, p1, p2, board);
			return;
		}else if (s instanceof ChangeOfHeart) {
			new WindowChangeOfHeartMonster(Card.getBoard().getActivePlayer(),s, p1, p2, board);
			return;
		}else{
			if (s instanceof MonsterReborn && Card.getBoard().getActivePlayer().getField().getGraveyard().isEmpty() && Card.getBoard().getOpponentPlayer().getField().getGraveyard().isEmpty()) {
				new WindowE("No Monster in Both GraveYards To be Reborn",p1,p2,board);
				return;
			}
		    try{
			Card.getBoard().getActivePlayer().activateSpell(s, null);
		    }catch(NoSpellSpaceException x) {
				new WindowE("No place to Add more Spell to the Spell area (Invalid Action)",p1,p2,board);
				return;
			}catch(WrongPhaseException x) {
				new WindowE("Wrong Phase Exception (Invalid Action)",p1,p2,board);
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
	
	
	if (e.getActionCommand().equals("no")) {
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
		new DescriptionS(s, p1, p2, board);
	}
	
}
}
