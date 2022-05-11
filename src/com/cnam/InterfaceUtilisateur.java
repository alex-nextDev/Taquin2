package com.cnam;

/**
 * @author TuRi
 */
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.border.EtchedBorder;


final class InterfaceUtilisateur extends JFrame implements KeyListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Action action = new Action();
	private Puzzle puzzle = new Puzzle();
	private JMenu menu;
	private JMenuItem nouveauJeu;
	private JMenu algorithme;
	private JMenuItem taille;
	private JMenuItem profondeur;
	private JMenuBar menuBar = new JMenuBar();

	InterfaceUtilisateur() {
	};
        
	public InterfaceUtilisateur(String name){
            setTitle(name);
            this.setLayout(new BorderLayout());
            add(puzzle, BorderLayout.EAST);

            // Cr�ation de premier menu
            menu = createJMenu("Jeu", 'F');            
            nouveauJeu = createJMenuItem("Nouveau Jeu    ", 'N', 'N',
				InputEvent.CTRL_MASK, action);
            menu.add(nouveauJeu);            
            
            // Cr�ation de premier menu
            algorithme = createJMenu("Trier", 'G');
            //Manhattan
            taille = createJMenuItem("Utilisation de recherche par taille (Manhattan) ", 'A', 'A',
				InputEvent.CTRL_MASK, action);
            algorithme.add(taille);
            //Euclidean
            profondeur = createJMenuItem("Utilisation de recherche par profondeur (Euclidian)  ", 'A',
				'A', InputEvent.CTRL_MASK, action);
            algorithme.add(profondeur);

            menuBar.setBorder(new EtchedBorder());
            menuBar.add(menu);
            menuBar.add(algorithme);

            this.setJMenuBar(menuBar);

            addKeylistener();
            this.setFocusable(true);
	}        
        
    //La classe r�agit aux �v�nements
	class Action extends AbstractAction{

        public Action()
        {
        
        }

        @Override
        public void actionPerformed(ActionEvent event) {

			if (event.getSource() == nouveauJeu) {
                removeKeylistener();
                addKeylistener();
                puzzle.random();                
			}

		
			if (event.getSource() == taille) {
                    puzzle.resoudrePuzzle(1);
                    removeKeylistener();
			} else if (event.getSource() == profondeur) {
	                    puzzle.resoudrePuzzle(2);
	                    removeKeylistener();
			} 

        } 
	}         

    //Cr�ateur de menu
	JMenu createJMenu(String JMenuNombre, char mnemonicChar) {
		JMenu menu = new JMenu(JMenuNombre);
		menu.getPopupMenu().setLightWeightPopupEnabled(false);
		menu.setMnemonic(mnemonicChar);

		return menu;
	}
	
    //Cr�ateur d'�l�ments de menu
	JMenuItem createJMenuItem(String jMenuName, char mnemonicChar, int keyChar,
			int modifierInt, AbstractAction action) {
		JMenuItem menuItem = new JMenuItem(jMenuName);
		menuItem.setMnemonic(mnemonicChar);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(keyChar, modifierInt));
		menuItem.addActionListener(action);

		return menuItem;
	}


    @Override
	public void keyTyped(KeyEvent event) {
	}

    @Override
	public void keyPressed(KeyEvent event) {
	}
    
    //Fonction permettant de capturer le mouvement des touches haut, bas, droite, gauche et de d�placer les pi�ces du puzzle.
    @Override
	public void keyReleased(KeyEvent event) {

            if ((event.getKeyCode() == KeyEvent.VK_UP)
				|| (event.getKeyCode() == KeyEvent.VK_LEFT)
				|| (event.getKeyCode() == KeyEvent.VK_RIGHT)
				|| (event.getKeyCode() == KeyEvent.VK_DOWN)) {

			puzzle.verifierMouvement(event.getKeyCode());

			if (puzzle.verifierFinJeu())
				removeKeylistener();
		}

	}

	public void removeKeylistener() {
		this.removeKeyListener(this);
	}

	/**
	 * add keylistener.
	 */
	public void addKeylistener() {
		this.addKeyListener(this);
	}

}

