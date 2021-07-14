package eg.edu.guc.yugioh.gui;


import java.awt.Color;


import java.awt.Container;

import java.awt.GridLayout;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.board.player.Player;
import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.Mode;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.cards.spells.SpellCard;


public class ShowMeHand extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Player p1; 
	Player p2;
	Board board;
public ShowMeHand(Player p1, Player p2, Board b) {
	this.p1 = p1;
	this.p2 = p2;
	this.board = b;
		@SuppressWarnings("unchecked")
		ArrayList <Card> h = (ArrayList<Card>) Card.getBoard().getActivePlayer().getField().getHand().clone();
		
		Container content = getContentPane();
		content.setLayout(new GridLayout(2,1));
		content.setBackground(Color.CYAN);
		setTitle("Yu Gi Oh");
		setSize(700, 500);
		setLocation(200, 100);
		
		JLabel j = new JLabel("This is the Hand of : "+Card.getBoard().getActivePlayer().getName());
		content.add(j);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,10));
		panel.setBackground(Color.cyan);
		panel.setMaximumSize(getMaximumSize());
		
		for (int i=0; i<20 && i<h.size(); i++) {
			JButton hand1;
		
			if (h.get(i) instanceof MonsterCard){
				MonsterCard m = (MonsterCard)h.get(i);
				hand1 = this.makeMonster(m.getName(), m.getMode(), m.getLevel(), m.getAttackPoints(), m.getDefensePoints());
			} else {
				SpellCard s = (SpellCard)h.get(i);
				hand1 = this.makeSpell(s.getName(), s.getDescription());
		}
			panel.add(hand1);
		}
		
		content.add(panel);
		
		setVisible(true);
		
	}
public JButton makeMonster(String name , Mode mode, int level, int attack, int defense) {
	JButton z = new JButton();
	
	JLabel j1 = new JLabel(name);
	JLabel j2 = new JLabel(mode.toString());
	JLabel j3 = new JLabel("L= " +Integer.toString(level));
	JLabel j4 = new JLabel("A= "+Integer.toString(attack));
	JLabel j5 = new JLabel("D= "+Integer.toString(defense));
	
	z.setLayout(new GridLayout(5,1));
	z.add(j1);
	z.add(j2);
	z.add(j3);
	z.add(j4);
	z.add(j5);
	z.setBackground(Color.orange);
	
	
	return z;
}
public JButton makeSpell(String name , String description) {
	JButton z = new JButton();
	
	JLabel j1 = new JLabel(name);
	
	
	
	z.setLayout(new GridLayout(1,1));
	z.add(j1);
	
	z.setBackground(Color.cyan);
	return z;

}


	
}

