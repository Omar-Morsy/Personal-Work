package eg.edu.guc.yugioh.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import eg.edu.guc.yugioh.exceptions.UnknownCardTypeException;
import eg.edu.guc.yugioh.exceptions.UnknownSpellCardException;


public class WindowB extends JFrame implements MouseListener, ActionListener {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JLabel a = new JLabel();
	JLabel b = new JLabel();
	JButton b1;
	JButton b2;
	
	JButton hand1;
	JButton hand2;
	
	boolean draggedhand1;
	boolean draggedhand2;
	
	Player p1;
	Player p2;
	
	private Card draggedfromhand ;
	
	
	private Board board;
	
	private ArrayList <Card> handp1 = new ArrayList<Card>();
	private ArrayList <Card> handp2 = new ArrayList<Card>();
	
	private ArrayList <MonsterCard> monstersAreap1 = new ArrayList<MonsterCard>();
	private ArrayList <MonsterCard> monstersAreap2 = new ArrayList<MonsterCard>();
	
	private ArrayList <SpellCard> spellsAreap1 = new ArrayList<SpellCard>();
	private ArrayList <SpellCard> spellsAreap2 = new ArrayList<SpellCard>();
	
	private ArrayList <Card> graveyard1 = new ArrayList<Card>();
	private ArrayList <Card> graveyard2 = new ArrayList<Card>();

@SuppressWarnings("unchecked")
public WindowB(Player p1, Player p2, Board bo) throws NumberFormatException, MissingFieldException, UnknownCardTypeException, UnknownSpellCardException, EmptyFieldException, IOException {
	board = bo;
	Card.setBoard(board);
	this.p1 = p1;
	this.p2 = p2;
	
	if (board.GameOver() == true) {
		setVisible(false);
		new Winner(board);
	}
	
	
	
	this.setHandp1((ArrayList<Card>)p1.getField().getHand().clone());
	this.setHandp2((ArrayList<Card>)p2.getField().getHand().clone());
	
	this.setMonstersAreap1((ArrayList<MonsterCard>)p1.getField().getMonstersArea().clone());
	this.setMonstersAreap2((ArrayList<MonsterCard>)p2.getField().getMonstersArea().clone());
	
	this.setSpellsAreap1((ArrayList<SpellCard>)p1.getField().getSpellArea().clone());
	this.setSpellsAreap2((ArrayList<SpellCard>)p2.getField().getSpellArea().clone());
	
	
	
	
	
	
	setTitle("Yu Gi Oh");
	setSize(800, 768);
	setLocation(200, 100);
	setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
	
	Container content = getContentPane();
	content.setBackground(Color.BLACK);
	
	JPanel panel = new JPanel();
	panel.setMaximumSize(getMaximumSize());
	panel.setLayout(new BorderLayout());
	
	JPanel panel1 = new JPanel();
	panel1.setMaximumSize(getMaximumSize());
	panel1.setLayout(new BorderLayout());
	
	JPanel panel8 = new JPanel();
	panel8.setMaximumSize(getMaximumSize());
	panel8.setBackground(Color.YELLOW);
	panel8.setLayout(new GridBagLayout());
	
	JPanel panel9 = new JPanel();
	panel9.setMaximumSize(getMaximumSize());
	panel9.setBackground(Color.YELLOW);
	panel9.setLayout(new GridBagLayout());
	
	JPanel panel7 = new JPanel();
	panel7.setMaximumSize(getMaximumSize());
	panel7.setBackground(Color.YELLOW);
	panel7.setLayout(new GridLayout(4,6));
	

	
	JPanel panel2 = new JPanel();
	panel2.setBackground(Color.GREEN);
	panel2.setMaximumSize(getMaximumSize());
	panel2.setLayout(new BorderLayout());
	a = new JLabel("    " + p1.getName());
	JLabel c = new JLabel("Life Points = " + p1.getLifePoints() + "     ");
	panel2.add(a, BorderLayout.WEST);
	panel2.add(c, BorderLayout.EAST);
	
	JPanel panel3 = new JPanel();
	panel3.setBackground(Color.GREEN);
	panel3.setMaximumSize(getMaximumSize());
	panel3.setLayout(new BorderLayout());
	b = new JLabel("    " + p2.getName());
	JLabel d = new JLabel("Life Points = " + p2.getLifePoints() + "     ");
	panel3.add(b, BorderLayout.WEST);
	panel3.add(d, BorderLayout.EAST);
	
	JPanel panel4 = new JPanel();
	panel4.setBackground(Color.lightGray);
	panel4.setLayout(new BorderLayout());
	panel4.setMaximumSize(getMaximumSize());
	JLabel j1 = new JLabel("Player's turn is : " + board.getActivePlayer().getName()+ "    ");
	JLabel j2 = new JLabel("Current Phase is : " + board.getActivePlayer().getField().getPhase() + "    ");
	
	
	panel4.add(j1 , BorderLayout.NORTH);
	panel4.add(j2 , BorderLayout.SOUTH);
	
	JPanel pzz = new JPanel();
	pzz.setBackground(Color.darkGray);
	pzz.setLayout(new BorderLayout());
	pzz.setMaximumSize(getMaximumSize());
	JButton b ;
	JButton b0;
	if (!p1.getField().getGraveyard().isEmpty()){
		if (p1.getField().getGraveyard().get(p1.getField().getGraveyard().size()-1) instanceof MonsterCard){
			MonsterCard m = (MonsterCard) p1.getField().getGraveyard().get(p1.getField().getGraveyard().size()-1);
			if (m.getMode() == Mode.DEFENSE) {
				b = this.makeMonster(m.getName(), m.getMode(), m.getLevel(), m.getAttackPoints(), m.getDefensePoints(),true);

			} else {
			b = this.makeMonster(m.getName(), m.getMode(), m.getLevel(), m.getAttackPoints(), m.getDefensePoints(),false);
			}
			} else {
			SpellCard s = (SpellCard) p1.getField().getGraveyard().get(p1.getField().getGraveyard().size()-1);
			b  = makeSpell(s.getName(), s.getDescription(),false);
		}
		pzz.add(b,BorderLayout.BEFORE_FIRST_LINE);
	}
	if (!p2.getField().getGraveyard().isEmpty()){
		if (p2.getField().getGraveyard().get(p2.getField().getGraveyard().size()-1) instanceof MonsterCard){
			MonsterCard m = (MonsterCard) p2.getField().getGraveyard().get(p2.getField().getGraveyard().size()-1);
			if (m.getMode() == Mode.DEFENSE) {
				b0 = this.makeMonster(m.getName(), m.getMode(), m.getLevel(), m.getAttackPoints(), m.getDefensePoints(),true);

			} else {
			b0 = this.makeMonster(m.getName(), m.getMode(), m.getLevel(), m.getAttackPoints(), m.getDefensePoints(),false);
			}	
			} else {
			SpellCard s = (SpellCard) p2.getField().getGraveyard().get(p2.getField().getGraveyard().size()-1);
			b0  = makeSpell(s.getName(), s.getDescription(),false);
		}
		pzz.add(b0, BorderLayout.AFTER_LAST_LINE);
	}
	
	
	JPanel pz = new JPanel();
	pz.setBackground(Color.darkGray);
	pz.setLayout(new BorderLayout());
	pz.setMaximumSize(getMaximumSize());
	JButton b1g = new JButton("GraveYard of "+p1.getName());
	JButton b2g = new JButton("GraveYard of "+p2.getName());
	b1g.setBackground(Color.RED);
	b2g.setBackground(Color.RED);
	b1g.setActionCommand("graveyard1");
	b2g.setActionCommand("graveyard2");
	b1g.addActionListener(this);
	b2g.addActionListener(this);
	pz.add(b1g , BorderLayout.NORTH);
	pz.add(b2g , BorderLayout.SOUTH);
	panel4.add(pz, BorderLayout.CENTER);
	pz.add(pzz, BorderLayout.CENTER);
	
	
	
	
	ImageIcon Ca = new ImageIcon(getClass().getResource("Card.png"));
	JPanel panel5 = new JPanel();
	panel5.setLayout(new BorderLayout());
	panel5.setBackground(Color.darkGray);
	panel5.setMaximumSize(getMaximumSize());
	JLabel j3 = new JLabel(p1.getName() + "'s" + " Deck "+ "  [" + p1.getField().getDeck().getDeck().size() +" Cards ]");
	JLabel j4 = new JLabel(p2.getName() + "'s" + " Deck "+ "  [" + p2.getField().getDeck().getDeck().size() +" Cards ]");
	j3.setIcon(Ca);
	j4.setIcon(Ca);
	
	panel5.add(j3 , BorderLayout.NORTH);
	panel5.add(j4 , BorderLayout.SOUTH);
	
	JPanel pp = new JPanel();
	pp.setLayout(new BorderLayout());
	pp.setBackground(Color.darkGray);
	
	JPanel p = new JPanel();
	p.setLayout(new BorderLayout());
	p.setBackground(Color.darkGray);
	if (Card.getBoard().getActivePlayer() == p1) {
		//JButton b1 = new JButton("Add Card from Deck");
		JButton ba = new JButton("Show me my Hand");
		ba.setBackground(Color.RED);
		ba.setActionCommand("la3eeb1");
		ba.addActionListener(this);
		//b1.setBackground(Color.RED);
		//b1.setActionCommand("addcard1");
		//b1.addActionListener(this);
		//p.add(b1, BorderLayout.NORTH);
		pp.add(ba,BorderLayout.NORTH);
		
	}
	if (Card.getBoard().getActivePlayer() == p2) {
		//JButton b2 =new JButton("Add Card from Deck");
		JButton ba = new JButton("Show me my Hand");
		ba.setBackground(Color.RED);
		ba.setActionCommand("la3eeb2");
		ba.addActionListener(this);
		//b2.setBackground(Color.RED);
		//b2.setActionCommand("addcard2");
		//b2.addActionListener(this);
		//p.add(b2, BorderLayout.SOUTH);
		pp.add(ba,BorderLayout.SOUTH);
		
	}
	
	
	
	panel5.add(p, BorderLayout.CENTER);
	
	JPanel px = new JPanel();
	px.setLayout(new BorderLayout());
	px.setBackground(Color.darkGray);
	px.setMaximumSize(getMaximumSize());
	
	JPanel panels = new JPanel();
	panels.setLayout(new BorderLayout());
	panels.setBackground(Color.darkGray);
	panels.setMaximumSize(getMaximumSize());
	b1 = new JButton("  END TURN  ");
	b2 = new JButton("  END PHASE  ");
	b1.setBackground(Color.RED);
	b2.setBackground(Color.RED);
	b1.addMouseListener(this);
	b2.addMouseListener(this);
	if (Card.getBoard().getActivePlayer() == p1) {
		panels.add(b1, BorderLayout.BEFORE_FIRST_LINE);
		px.add(b2 , BorderLayout.BEFORE_FIRST_LINE);
	} else {
		panels.add(b1, BorderLayout.AFTER_LAST_LINE);
		px.add(b2 , BorderLayout.SOUTH);
	}
	
	panels.add(px , BorderLayout.CENTER);

	
	
	JPanel panel6 = new JPanel();
	panel6.setLayout(new BorderLayout());
	panel6.setBackground(Color.darkGray);
	panel6.setMaximumSize(getMaximumSize());
    
	panel6.add(panels , BorderLayout.CENTER);
	p.add(pp, BorderLayout.CENTER);
	pp.add(panel6,BorderLayout.CENTER);
	
	
	
if (Card.getBoard().getActivePlayer() == p1) {	
for(int i=0; i<20 && i<this.getHandp1().size(); i++) {
	GridBagConstraints con = new GridBagConstraints();
	con.fill = GridBagConstraints.HORIZONTAL;
	con.gridx = i;
	con.gridy = 0;
	con.ipady = 100; 
	con.weightx = 2	; 
	
	if (i == 0) {
		panel8.add(new JLabel("Hand Of : "+ p1.getName()),con);
	} else {
		if (this.getHandp1().get(i) instanceof SpellCard) {
			SpellCard s = (SpellCard)this.getHandp1().get(i);
			hand1 = this.makeSpell(s.getName(), s.getDescription(),false);
		} else if (this.getHandp1().get(i) instanceof MonsterCard){
			MonsterCard m = (MonsterCard)this.getHandp1().get(i);
			if (m.getMode() == Mode.DEFENSE) {
				hand1 = this.makeMonster(m.getName(), m.getMode(), m.getLevel(), m.getAttackPoints(), m.getDefensePoints(),false);

			} else {
			hand1 = this.makeMonster(m.getName(), m.getMode(), m.getLevel(), m.getAttackPoints(), m.getDefensePoints(),false);
			}		} else {
			Card s = this.getHandp1().get(i);
			hand1 = this.makeSpell(s.getName(), s.getDescription(),false);
		}
		hand1.setActionCommand("hand1no"+i);
		hand1.addActionListener(this);
		panel8.add(hand1,con);
	}
	
	
}

for(int i=0; i<20 && i < this.getHandp2().size(); i++) {
	GridBagConstraints con = new GridBagConstraints();
	con.fill = GridBagConstraints.HORIZONTAL;
	con.gridx = i;
	con.gridy = 0;
	con.ipady = 100; 
	con.weightx = 2;
	ImageIcon Card = new ImageIcon(getClass().getResource("Card.png"));

	JButton bh;
	if (i == 0) {
		panel9.add(new JLabel("Hand Of : "+ p2.getName()),con);
	} else {
		if (this.getHandp2().get(i) instanceof SpellCard) {
			bh = new JButton(Card);
			
		} else if (this.getHandp2().get(i) instanceof MonsterCard) {
			bh = new JButton(Card);
		} else {
			bh = new JButton(Card);
		}
		bh.setBackground(Color.DARK_GRAY);
		panel9.add(bh,con);
	}
}
} else {
	
	for(int i=0; i<20 && i<this.getHandp1().size(); i++) {
		GridBagConstraints con = new GridBagConstraints();
		con.fill = GridBagConstraints.HORIZONTAL;
		con.gridx = i;
		con.gridy = 0;
		con.ipady = 100; 
		con.weightx = 2	; 
		ImageIcon Card = new ImageIcon(getClass().getResource("Card.png"));
	    
		JButton bh;
		
		if (i == 0) {
			panel8.add(new JLabel("Hand Of : "+ p1.getName()),con);
		} else {
			if (this.getHandp1().get(i) instanceof SpellCard) {
				bh = new JButton(Card);
			} else if (this.getHandp1().get(i) instanceof MonsterCard){
				bh = new JButton(Card);
			} else {
				bh = new JButton(Card);
			}
			bh.setBackground(Color.DARK_GRAY);
			panel8.add(bh,con);
		}
		
		
	}

for(int i=0; i<20 && i < this.getHandp2().size(); i++) {
	GridBagConstraints con = new GridBagConstraints();
	con.fill = GridBagConstraints.HORIZONTAL;
	con.gridx = i;
	con.gridy = 0;
	con.ipady = 100; 
	con.weightx = 2;
	if (i == 0) {
		panel9.add(new JLabel("Hand Of : "+ p2.getName()),con);
	} else {
		if (this.getHandp2().get(i) instanceof SpellCard) {
			SpellCard s = (SpellCard)this.getHandp2().get(i);
			hand2 = this.makeSpell(s.getName(), s.getDescription(),false);
		} else if (this.getHandp2().get(i) instanceof MonsterCard) {
			MonsterCard m = (MonsterCard)this.getHandp2().get(i);
			if (m.getMode() == Mode.DEFENSE) {
				hand2 = this.makeMonster(m.getName(), m.getMode(), m.getLevel(), m.getAttackPoints(), m.getDefensePoints(),false);

			} else {
			hand2 = this.makeMonster(m.getName(), m.getMode(), m.getLevel(), m.getAttackPoints(), m.getDefensePoints(),false);
			}	
			} else {
			Card s = this.getHandp2().get(i);
			hand2 = this.makeSpell(s.getName(), s.getDescription(),false);
		}
		hand2.setActionCommand("hand2no"+i);
		hand2.addActionListener(this);
		panel9.add(hand2,con);
	}
}
}
	
for (int i =0; i<4; i++ ){
	JButton z;
	for (int j=0; j<6; j++) {
		if (i==0 && j==0) {
			panel7.add(new JLabel("Monsters Area Of : "+ p1.getName()));
		} else if (i==1 && j==0) {
			panel7.add(new JLabel("Spells Area Of : "+ p1.getName()));
		} else if (i==2 && j==0) {
			panel7.add(new JLabel("Spells Area Of : "+ p2.getName()));
		} else if (i==3 && j==0) {
			panel7.add(new JLabel("Monsters Area Of : "+ p2.getName()));
		} else {
			if (i == 0 && !this.getMonstersAreap1().isEmpty()&& j!=0) {
				MonsterCard m = this.getMonstersAreap1().remove(0);
				if (m.getMode() == Mode.DEFENSE) {
					z = this.makeMonster(m.getName(), m.getMode(), m.getLevel(), m.getAttackPoints(), m.getDefensePoints(),true);

				} else {
				z = this.makeMonster(m.getName(), m.getMode(), m.getLevel(), m.getAttackPoints(), m.getDefensePoints(),false);
				}
				z.setActionCommand("m1no"+j);
			    z.addActionListener(this);
			   
			} else if (i == 1 && !this.getSpellsAreap1().isEmpty()&& j!=0) {
				SpellCard s = this.getSpellsAreap1().remove(0);
				z = this.makeSpell(s.getName(), s.getDescription(),true);
				z.setActionCommand("s1no"+j);
			    z.addActionListener(this);
			    
			} else if (i == 2 && !this.getSpellsAreap2().isEmpty()&& j!=0) {
				SpellCard s = this.getSpellsAreap2().remove(0);
				z = this.makeSpell(s.getName(), s.getDescription(),true);
				z.setActionCommand("s2no"+j);
			    z.addActionListener(this);
			    
			} else if (i == 3 && !this.getMonstersAreap2().isEmpty()&& j!=0){
				MonsterCard m = this.getMonstersAreap2().remove(0);
				if (m.getMode() == Mode.DEFENSE) {
					z = this.makeMonster(m.getName(), m.getMode(), m.getLevel(), m.getAttackPoints(), m.getDefensePoints(),true);

				} else {
				z = this.makeMonster(m.getName(), m.getMode(), m.getLevel(), m.getAttackPoints(), m.getDefensePoints(),false);
				}				z.setActionCommand("m2no"+j);
			    z.addActionListener(this);
			    
			} else {
				z = new JButton();
				if (i == 0) {
					z = new JButton("Monster");
					z.setActionCommand("m1emptyno"+j);
				    z.addActionListener(this);
				    z.setBackground(Color.WHITE);
				}
				if (i == 1) {
					z = new JButton("Spell");
					z.setActionCommand("s1emptyno"+j);
				    z.addActionListener(this);
				    z.setBackground(Color.WHITE);
				}
				if (i == 2) {
					z = new JButton("Spell");
					z.setActionCommand("s2emptyno"+j);
				    z.addActionListener(this);
				    z.setBackground(Color.WHITE);
				}
				if (i == 3) {
					z = new JButton("Monster");
					z.setActionCommand("m2emptyno"+j);
				    z.addActionListener(this);
				    z.setBackground(Color.WHITE);
				}
			}
			panel7.add(z);
			
		}
	}
}

panel1.add(panel8, BorderLayout.NORTH);
panel1.add(panel7, BorderLayout.CENTER);
panel1.add(panel9, BorderLayout.SOUTH);
	
	
	panel.add(panel2, BorderLayout.NORTH);
	panel.add(panel1, BorderLayout.CENTER);
	panel.add(panel3, BorderLayout.SOUTH);
	panel.add(panel4, BorderLayout.EAST);
	panel.add(panel5, BorderLayout.WEST);
	
	
	content.add(panel);
	
	if (!board.GameOver()) {
	setVisible(true);
	this.validate();
	}
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
public JButton makeSpell(String name , String description, boolean f) {
	JButton z = new JButton();
	if (f) {
		ImageIcon Card = new ImageIcon(getClass().getResource("Card.png"));
		z.setIcon(Card);
	} else{
	
	
	JLabel j1 = new JLabel(name);
	
	
	
	z.setLayout(new GridLayout(1,1));
	z.add(j1);
	
	z.setBackground(Color.cyan);
}
	return z;
}

@Override
public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mousePressed(MouseEvent e) {
	setVisible(false);
	if (e.getSource() == b1) {
		board.getActivePlayer().endTurn();
	} else if (e.getSource() == b2) {
		board.getActivePlayer().endPhase();
	}
	try {
		new WindowB(p1,p2, board);
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

@Override
public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

public ArrayList <Card> getHandp1() {
	return handp1;
}

public void setHandp1(ArrayList <Card> handp1) {
	this.handp1 = handp1;
}

public ArrayList <Card> getHandp2() {
	return handp2;
}

public void setHandp2(ArrayList <Card> handp2) {
	this.handp2 = handp2;
}

public ArrayList <MonsterCard> getMonstersAreap1() {
	return monstersAreap1;
}

public void setMonstersAreap1(ArrayList <MonsterCard> monstersAreap1) {
	this.monstersAreap1 = monstersAreap1;
}

public ArrayList <MonsterCard> getMonstersAreap2() {
	return monstersAreap2;
}

public void setMonstersAreap2(ArrayList <MonsterCard> monstersAreap2) {
	this.monstersAreap2 = monstersAreap2;
}

public ArrayList <SpellCard> getSpellsAreap1() {
	return spellsAreap1;
}

public void setSpellsAreap1(ArrayList <SpellCard> spellsAreap1) {
	this.spellsAreap1 = spellsAreap1;
}

public ArrayList <SpellCard> getSpellsAreap2() {
	return spellsAreap2;
}

public void setSpellsAreap2(ArrayList <SpellCard> spellsAreap2) {
	this.spellsAreap2 = spellsAreap2;
}

@Override
public void actionPerformed(ActionEvent e) {
	String s = e.getActionCommand().toString();
	if (s.charAt(0) == 'h' && s.charAt(4) == '1') {
		draggedhand1 = true;
		this.setDraggedfromhand(this.getHandp1().get(Integer.parseInt(s.substring(7))));
		
		return;
	}
	if (s.charAt(0) == 'h' && s.charAt(4) == '2') {
		draggedhand2 = true;
		this.setDraggedfromhand(this.getHandp2().get(Integer.parseInt(s.substring(7))));
		return;
	}
	if (s.charAt(0) == 'm' && s.charAt(1) == '1' && draggedhand1 && this.getDraggedfromhand() instanceof MonsterCard && p1 == board.getActivePlayer() ) {
		MonsterCard m = (MonsterCard)this.getDraggedfromhand();
		setVisible(false);
		
		
		new WindowM(m, p1,p1,p2,board);
		
	}
	if (s.charAt(0) == 'm' && s.charAt(1) == '2' && draggedhand2 && this.getDraggedfromhand() instanceof MonsterCard && p2 == board.getActivePlayer()) {
		MonsterCard m = (MonsterCard)this.getDraggedfromhand();
		setVisible(false);
		new WindowM(m, p2,p1,p2,board);
		
	}
	
	
	if (s.charAt(0) == 's' && s.charAt(1) == '1' && draggedhand1 && this.getDraggedfromhand() instanceof SpellCard && p1 == board.getActivePlayer()) {
		SpellCard ss = (SpellCard)this.getDraggedfromhand();
		setVisible(false);
		new WindowS(ss,p1,p1,p2,board);
	}
	if (s.charAt(0) == 's' && s.charAt(1) == '2' && draggedhand2 && this.getDraggedfromhand() instanceof SpellCard && p2 == board.getActivePlayer()) {
		SpellCard ss = (SpellCard)this.getDraggedfromhand();
		setVisible(false);
		new WindowS(ss,p2,p1,p2,board);
	}
	
	
	if (s.charAt(0) == 'm' && s.charAt(1) == '1' && s.charAt(2) == 'n' && Card.getBoard().getActivePlayer() == p1 ) {
		MonsterCard m = p1.getField().getMonstersArea().get(Integer.parseInt(s.substring(4))-1);
		setVisible(false);
		new WindowMattack(m, p1, p1, p2, board);
	}	
	if (s.charAt(0) == 'm' && s.charAt(1) == '2' && s.charAt(2) == 'n' && Card.getBoard().getActivePlayer() == p2 ) {
		MonsterCard m = p2.getField().getMonstersArea().get(Integer.parseInt(s.substring(4))-1);
		setVisible(false);
		new WindowMattack(m, p2, p1, p2, board);
	}	
	
	if (s.equals("addcard1") && Card.getBoard().getActivePlayer() == p1) {
		p1.addCardToHand();
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
	if (s.equals("addcard2") && Card.getBoard().getActivePlayer() == p2) {
		p2.addCardToHand();
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
	if (s.equals("graveyard1")) {
		new WindowG(p1);
	}
	if (s.equals("graveyard2")) {
		new WindowG(p2);
	}
	
	if (s.charAt(0) == 's' && s.charAt(1) == '1' && s.charAt(2) == 'n' && Card.getBoard().getActivePlayer() == p1 ) {
		SpellCard ss = p1.getField().getSpellArea().get(Integer.parseInt(s.substring(4))-1);
		setVisible(false);
		new WindowSactivate(ss, p1, p1, p2, board);
		
	}
	if (s.charAt(0) == 's' && s.charAt(1) == '1' && s.charAt(2) == 'n' && Card.getBoard().getActivePlayer() == p1 ) {
		SpellCard ss = p1.getField().getSpellArea().get(Integer.parseInt(s.substring(4))-1);
		setVisible(false);
		new WindowSactivate(ss, p1, p1, p2, board);
		
	}
	
	if (s.charAt(0) == 's' && s.charAt(1) == '2' && s.charAt(2) == 'n' && Card.getBoard().getActivePlayer() == p2 ) {
		SpellCard ss = p2.getField().getSpellArea().get(Integer.parseInt(s.substring(4))-1);
		setVisible(false);
		new WindowSactivate(ss, p2, p1, p2, board);
		
	}
	if (s.equals("la3eeb1")) {
		new ShowMeHand(p1, p2, board);
	}
	if (s.equals("la3eeb2")) {
		new ShowMeHand(p1, p2, board);
	}
}

public Card getDraggedfromhand() {
	return draggedfromhand;
}

public void setDraggedfromhand(Card draggedfromhand) {
	this.draggedfromhand = draggedfromhand;
}

public ArrayList <Card> getGraveyard1() {
	return graveyard1;
}

public void setGraveyard1(ArrayList <Card> graveyard1) {
	this.graveyard1 = graveyard1;
}

public ArrayList <Card> getGraveyard2() {
	return graveyard2;
}

public void setGraveyard2(ArrayList <Card> graveyard2) {
	this.graveyard2 = graveyard2;
}

}
