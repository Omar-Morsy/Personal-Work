package eg.edu.guc.yugioh.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import eg.edu.guc.yugioh.board.player.Player;
import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.Mode;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.cards.spells.SpellCard;

public class WindowG extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private ArrayList<Card >graveyard = new ArrayList<Card>();
	public WindowG(Player p) {
this.setGraveyard(p.getField().getGraveyard());
		setTitle("Yu Gi Oh");
		setSize(700, 500);
		setLocation(200, 100);
		
		Container content = getContentPane();
		content.setLayout(new GridLayout(2,1));
		content.setBackground(Color.CYAN);
		JLabel j = new JLabel("The last Card in "+p.getName()+"'s Graveyard is : ");
		JButton b;
		if (!this.getGraveyard().isEmpty()){
			if (this.getGraveyard().get(this.getGraveyard().size()-1) instanceof MonsterCard) {
				MonsterCard m = (MonsterCard) this.getGraveyard().get(this.getGraveyard().size()-1);
				b = makeMonster(m.getName(), m.getMode(), m.getLevel(), m.getAttackPoints(), m.getDefensePoints());
			} else {
				SpellCard s = (SpellCard) this.getGraveyard().get(this.getGraveyard().size()-1);
				b = makeSpell(s.getName(),s.getDescription());
			}
		} else {
			b = new JButton();
		}
		content.add(j);
		content.add(b);
		
		setVisible(true);
		this.validate();
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
		z.setBackground(Color.ORANGE);
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
	public ArrayList<Card > getGraveyard() {
		return graveyard;
	}
	public void setGraveyard(ArrayList<Card > graveyard) {
		this.graveyard = graveyard;
	}
}
