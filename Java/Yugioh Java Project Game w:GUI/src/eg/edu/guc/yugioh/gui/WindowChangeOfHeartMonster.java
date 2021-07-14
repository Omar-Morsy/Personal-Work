package eg.edu.guc.yugioh.gui;



import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
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
import eg.edu.guc.yugioh.exceptions.EmptyFieldException;
import eg.edu.guc.yugioh.exceptions.MissingFieldException;
import eg.edu.guc.yugioh.exceptions.NoSpellSpaceException;
import eg.edu.guc.yugioh.exceptions.UnknownCardTypeException;
import eg.edu.guc.yugioh.exceptions.UnknownSpellCardException;
import eg.edu.guc.yugioh.exceptions.WrongPhaseException;

public class WindowChangeOfHeartMonster extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Player p;
	Player p1;
	Player p2;
	Board board;
	SpellCard s1;
	private ArrayList<MonsterCard> opponentMonstersArea = new ArrayList<MonsterCard>();
	@SuppressWarnings("unchecked")
	public WindowChangeOfHeartMonster(Player p,SpellCard s1 ,Player p1, Player p2,Board b) {
		this.p = p;
		this.p1 = p1;
		this.p2 = p2;
		this.board = b;
		this.s1 = s1;
		
		this.setOpponentMonstersArea((ArrayList<MonsterCard>)Card.getBoard().getOpponentPlayer().getField().getMonstersArea().clone());
		
		setTitle("Yu Gi Oh");
		setSize(700, 500);
		setLocation(200, 100);
		
		Container content = getContentPane();
		content.setLayout(new GridLayout(3,1));
		content.setBackground(Color.CYAN);
		
		JPanel panel = new JPanel();
		panel.setMaximumSize(getMaximumSize());
		panel.setBackground(Color.YELLOW);
		panel.setLayout(new GridLayout(1,5));
		
		JButton c = new JButton("Cancel and return to the Board");
		c.setBackground(Color.green);
		c.setActionCommand("cancel");
		c.addActionListener(this);
		
		JLabel j = new JLabel("Please Choose the Monster you want to Activate the Spell on from your Opponent's Monsters Area");
		content.add(j);
		
		for (int i=0; i<5;i++) {
			JButton z;
			if (!this.getOpponentMonstersArea().isEmpty()) {
				MonsterCard m = this.getOpponentMonstersArea().remove(0);
				if (m.getMode() == Mode.DEFENSE) {
					z = this.makeMonster(m.getName(), m.getMode(), m.getLevel(), m.getAttackPoints(), m.getDefensePoints(),true);

				} else {
				z = this.makeMonster(m.getName(), m.getMode(), m.getLevel(), m.getAttackPoints(), m.getDefensePoints(),false);
				}
				z.setActionCommand("m2no"+i);
			    z.addActionListener(this);
			    z.setBackground(Color.YELLOW);
			    panel.add(z);
			} 
			
		}
		
		
		content.add(panel);
		content.add(c);
		setVisible(true);
		this.validate();
	}
	
	public JButton makeMonster(String name , Mode mode, int level, int attack, int defense, boolean f) {
		
		JButton z = new JButton();
		
		if (f) {
			ImageIcon Cardi = new ImageIcon(getClass().getResource("Cardi.png"));
			z.setIcon(Cardi);
		} else {
		
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
		}
		return z;
	}
	public ArrayList<MonsterCard> getOpponentMonstersArea() {
		return opponentMonstersArea;
	}
	public void setOpponentMonstersArea(ArrayList<MonsterCard> opponentMonstersArea) {
		this.opponentMonstersArea = opponentMonstersArea;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		if (s.charAt(0) == 'm' && s.charAt(1) == '2' && s.charAt(2) == 'n') {
			setVisible(false);
			MonsterCard m = Card.getBoard().getOpponentPlayer().getField().getMonstersArea().get(Integer.parseInt(s.substring(4)));
			try{
			Card.getBoard().getActivePlayer().activateSpell(s1,m);
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
