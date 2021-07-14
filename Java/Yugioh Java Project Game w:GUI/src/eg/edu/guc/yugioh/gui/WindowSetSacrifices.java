package eg.edu.guc.yugioh.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
import eg.edu.guc.yugioh.exceptions.EmptyFieldException;
import eg.edu.guc.yugioh.exceptions.MissingFieldException;
import eg.edu.guc.yugioh.exceptions.MultipleMonsterAdditionException;
import eg.edu.guc.yugioh.exceptions.NoMonsterSpaceException;
import eg.edu.guc.yugioh.exceptions.UnknownCardTypeException;
import eg.edu.guc.yugioh.exceptions.UnknownSpellCardException;
import eg.edu.guc.yugioh.exceptions.WrongPhaseException;

public class WindowSetSacrifices extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Player p;
	MonsterCard m1;
	Player p1; 
	Player p2;
	Board board;
	private ArrayList<MonsterCard> activeMonstersArea = new ArrayList<MonsterCard>();
	MonsterCard spare;
	boolean f;
@SuppressWarnings("unchecked")
public WindowSetSacrifices(MonsterCard m1, Player P, Player p1, Player p2, Board b) {
	this.p = P;
	this.m1 = m1;
	
	this.p1 = p1;
	this.p2 = p2;
	this.board = b;
	
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
	c.setActionCommand("cancel");
	c.setBackground(Color.green);
	c.addActionListener(this);
	
	this.setActiveMonstersArea((ArrayList<MonsterCard>)Card.getBoard().getActivePlayer().getField().getMonstersArea().clone());

	
	for (int i=0; i<5;i++) {
		JButton z;
		if (!this.getActiveMonstersArea().isEmpty()) {
			MonsterCard m = this.getActiveMonstersArea().remove(0);
			z = this.makeMonster(m.getName(), m.getMode(), m.getLevel(), m.getAttackPoints(), m.getDefensePoints());
		    z.setActionCommand("m1no"+i);
		    z.addActionListener(this);
		    z.setBackground(Color.YELLOW);
		    panel.add(z);
		} 
		
	}
	
	if (m1.getLevel() < 7 && m1.getLevel() > 4 ) {
		
		content.add(new JLabel("This Monster needs 1 sacrifice from your Monsters area to be Set , Please choose one as a sacrifice"));
		
	} else {
		content.add(new JLabel("This Monster needs 2 sacrifices from your Monsters area to be Set , Please choose two as  sacrifices"));
	}
	content.add(panel);
	content.add(c);
	
	
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
public ArrayList<MonsterCard> getActiveMonstersArea() {
	return activeMonstersArea;
}
public void setActiveMonstersArea(ArrayList<MonsterCard> activeMonstersArea) {
	this.activeMonstersArea = activeMonstersArea;
}

@Override
public void actionPerformed(ActionEvent e) {
	String s = e.getActionCommand();
	if (s.charAt(0) == 'm' && s.charAt(1) == '1' && s.charAt(2) == 'n' && f == false) {
		if (m1.getLevel() < 7 && m1.getLevel() > 4 ) {
			setVisible(false);
			MonsterCard m = Card.getBoard().getActivePlayer().getField().getMonstersArea().get(Integer.parseInt(s.substring(4)));
            ArrayList<MonsterCard> sacrifices = new ArrayList<MonsterCard>();
            sacrifices.add(m);
            try{
            Card.getBoard().getActivePlayer().setMonster(m1, sacrifices);
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
		} else {
			f = true;
			spare = Card.getBoard().getActivePlayer().getField().getMonstersArea().get(Integer.parseInt(s.substring(4)));
            return;
			}
	}
	
	
	if (s.charAt(0) == 'm' && s.charAt(1) == '1' && s.charAt(2) == 'n' && f) {
		setVisible(false);
		MonsterCard m = Card.getBoard().getActivePlayer().getField().getMonstersArea().get(Integer.parseInt(s.substring(4)));
        ArrayList<MonsterCard> sacrifices = new ArrayList<MonsterCard>();
        sacrifices.add(spare);
        sacrifices.add(m);
        spare = null;
        
        try{
        Card.getBoard().getActivePlayer().setMonster(m1, sacrifices);
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
