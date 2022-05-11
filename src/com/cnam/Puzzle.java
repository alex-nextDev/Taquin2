package com.cnam;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
public class Puzzle extends JPanel implements ActionListener
{
    /**
	 * Définition des objects inclus dans l'application du graphe : A*
	 */
	
	private JLabel[][] part = new JLabel[3][3];
    private Node nodeDepart, nodeMineur, nodeBranche;
    private ListLien listeOuvert = new ListLien();
    private ListLien listeFerme = new ListLien();
    private ListLien cheminFinal = new ListLien();
    private ListeNode listeCourrent;
    //private int[][] matriceDeparts = { { 4, 1, 3 }, { 9, 2, 5 }, { 7, 8, 6 } };
    private int[][] matriceDeparts = { { 1, 2, 3 }, { 9, 4, 6 }, { 7, 5, 8 } };
    public int[][] etatFinal = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
    private Icon imagen = new ImageIcon();
    private int idCont;
    private int tempEnt;
    private Random random = new Random();
    private int compterDesordre;
    private Timer time, time1;
    private TimerCheminFinal tempsCheminFinal = new TimerCheminFinal();
    private int iVide = 2, jVide = 2;

    Puzzle()
    {
	setLayout(new GridLayout(3, 3, 5, 5));
        setBorder(new EtchedBorder());

        time = new Timer(0, this);
        time1 = new Timer(400, tempsCheminFinal);
        
        for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                        part[i][j] = createJLabel(String.format("/Imagenes/%d.jpg",
                                        matriceDeparts[i][j]));

        ajouterParts();
    }

    public JLabel createJLabel(String ruta)
    {
		JLabel newLabel = new JLabel();
		newLabel.setIcon(new ImageIcon(getClass().getResource(ruta)));
		newLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
	
		return newLabel;
    }

    public void verifierMouvement(int tecla)
    {
		switch (tecla) {
		case KeyEvent.VK_UP:
	            if (iVide == 2)
	            {
	            break;
	            } 
	            else
	            {
	            bougerPartVideBas();
	            iVide += 1;
	            }
		break;
		case KeyEvent.VK_DOWN:
	            if (iVide == 0)
	            {
	            break;
	            }
	            else
	            {
	            bougerPartVideHaut();
	            iVide -= 1;
	            }
	            break;
	
	            case KeyEvent.VK_LEFT:
				if (jVide == 2) {
					break;
				} else {
					bougerPartVideDroite();
					jVide += 1;
				}
				break;
		case KeyEvent.VK_RIGHT:

			if (jVide == 0) {
				break;
			} else {
				bougerPartVideGauche();
				jVide -= 1;
			}
			break;
		}

	}

    private void bougerPartVideGauche()
    {
        imagen = part[iVide][jVide].getIcon();
        tempEnt = matriceDeparts[iVide][jVide];
        System.out.println("Variable tempEnt : " + tempEnt);
        part[iVide][jVide].setIcon(part[iVide][jVide - 1].getIcon());
        matriceDeparts[iVide][jVide] = matriceDeparts[iVide][jVide - 1];

        part[iVide][jVide - 1].setIcon(imagen);
        matriceDeparts[iVide][jVide - 1] = tempEnt;
    }

    private void bougerPartVideDroite() {
            imagen = part[iVide][jVide].getIcon();
            tempEnt = matriceDeparts[iVide][jVide];

            part[iVide][jVide].setIcon(part[iVide][jVide + 1].getIcon());
            matriceDeparts[iVide][jVide] = matriceDeparts[iVide][jVide + 1];

            part[iVide][jVide + 1].setIcon(imagen);
            matriceDeparts[iVide][jVide + 1] = tempEnt;
	}

    private void bougerPartVideHaut()
    {
        imagen = part[iVide][jVide].getIcon();
        tempEnt = matriceDeparts[iVide][jVide];

        part[iVide][jVide].setIcon(part[iVide - 1][jVide].getIcon());
        matriceDeparts[iVide][jVide] = matriceDeparts[iVide - 1][jVide];

        part[iVide - 1][jVide].setIcon(imagen);
        matriceDeparts[iVide - 1][jVide] = tempEnt;
    }

    private void bougerPartVideBas() 
    {
        imagen = part[iVide][jVide].getIcon();
        tempEnt = matriceDeparts[iVide][jVide];

        part[iVide][jVide].setIcon(part[iVide + 1][jVide].getIcon());
        matriceDeparts[iVide][jVide] = matriceDeparts[iVide + 1][jVide];

        part[iVide + 1][jVide].setIcon(imagen);
        matriceDeparts[iVide + 1][jVide] = tempEnt;
    }

    public void resoudrePuzzle(int flag)
    {
        																																								// Ramasser les d�chets en m�moire
		time1.start();
		System.gc();
		listeOuvert.viderListe();
		cheminFinal.viderListe();
		listeFerme.viderListe();
		idCont = 0;
		System.out.println("nouveau noeud idCont : " + idCont);
		nodeDepart = new Node(0, matriceDeparts, idCont, -1, flag);
		listeOuvert.insererFin(nodeDepart);
		while (!listeOuvert.estVide())
        {
            nodeMineur = listeOuvert.getNodeMineur();
            if (memeEtat(nodeMineur.getEtat(), etatFinal))
            { 													// sto stoxo
                listeFerme.insererFin(nodeMineur);
                break;
            } 
            else 
            {
			
				listeOuvert.supprimerNode(nodeMineur);
				listeFerme.insererFin(nodeMineur);
				
				if (nodeMineur.getEmptyX() == 0
						&& nodeMineur.getEmptyY() == 0) {
					
					nodeBranche = bougerVideDroit(nodeMineur, flag);
					checkBranchNodeInLists();
					
					nodeBranche = bougerVideBas(nodeMineur, flag);
					checkBranchNodeInLists();
				}
				
				else if (nodeMineur.getEmptyX() == 0
						&& nodeMineur.getEmptyY() == 1) {
					nodeBranche = bougerVideGouche(nodeMineur, flag);
					checkBranchNodeInLists();

					nodeBranche = bougerVideBas(nodeMineur, flag);
					checkBranchNodeInLists();

					nodeBranche = bougerVideDroit(nodeMineur,
							flag);
					checkBranchNodeInLists();
				}

				
				else if (nodeMineur.getEmptyX() == 0
						&& nodeMineur.getEmptyY() == 2) {
					nodeBranche = bougerVideGouche(nodeMineur, flag);

					checkBranchNodeInLists();

					nodeBranche = bougerVideBas(nodeMineur, flag);

					checkBranchNodeInLists();

				}
				else if (nodeMineur.getEmptyX() == 1
						&& nodeMineur.getEmptyY() == 0) {
					nodeBranche = bougerVideHaut(nodeMineur, flag);
					checkBranchNodeInLists();

					nodeBranche = bougerVideDroit(nodeMineur,
							flag);
					checkBranchNodeInLists();

					nodeBranche = bougerVideBas(nodeMineur, flag);
					checkBranchNodeInLists();

				}
				
				else if (nodeMineur.getEmptyX() == 1
						&& nodeMineur.getEmptyY() == 1) {
					nodeBranche = bougerVideHaut(nodeMineur, flag);
					checkBranchNodeInLists();

					nodeBranche = bougerVideDroit(nodeMineur,
							flag);
					checkBranchNodeInLists();

					nodeBranche = bougerVideBas(nodeMineur, flag);

					checkBranchNodeInLists();

					nodeBranche = bougerVideGouche(nodeMineur, flag);
					checkBranchNodeInLists();

				}
				else if (nodeMineur.getEmptyX() == 1
						&& nodeMineur.getEmptyY() == 2) {
					nodeBranche = bougerVideHaut(nodeMineur, flag);

					checkBranchNodeInLists();

					nodeBranche = bougerVideGouche(nodeMineur, flag);
					checkBranchNodeInLists();

					nodeBranche = bougerVideBas(nodeMineur, flag);
					checkBranchNodeInLists();

				}
	
				else if (nodeMineur.getEmptyX() == 2
						&& nodeMineur.getEmptyY() == 0) {
					nodeBranche = bougerVideHaut(nodeMineur, flag);
					checkBranchNodeInLists();

					nodeBranche = bougerVideDroit(nodeMineur,
							flag);
					checkBranchNodeInLists();

				}
	
				else if (nodeMineur.getEmptyX() == 2
						&& nodeMineur.getEmptyY() == 1) {
					nodeBranche = bougerVideGouche(nodeMineur, flag);
					checkBranchNodeInLists();

					nodeBranche = bougerVideHaut(nodeMineur, flag);
					checkBranchNodeInLists();

					nodeBranche = bougerVideDroit(nodeMineur,
							flag);

					checkBranchNodeInLists();
				}
		
				else if (nodeMineur.getEmptyX() == 2
						&& nodeMineur.getEmptyY() == 2) {
					nodeBranche = bougerVideHaut(nodeMineur, flag);

					checkBranchNodeInLists();
					nodeBranche = bougerVideGouche(nodeMineur, flag);
					checkBranchNodeInLists();

				}

			}
            System.out.println("listeOuvert : ");
            listeOuvert.imprimerListe();
            System.out.println("listeFerme : ");
            listeFerme.imprimerListe();
            
            
        } 
		System.out.println("cheminFinal : ");
        cheminFinal.imprimerListe();

        backTrackNodes();
        listeCourrent = cheminFinal.getPremierNode();
        resetMatricePuzzle();
    }

	private void resetMatricePuzzle() {
		iVide = jVide = 2;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				matriceDeparts[i][j] = etatFinal[i][j];

			}
		}

	}

	public void checkBranchNodeInLists() {
		System.out.println("checkBranchNodeInLists : ");
		if (listeFerme.prouverExistence(nodeBranche))
			; 
		else 
		{
			if (!listeOuvert.prouverExistence(nodeBranche)) {
				listeOuvert.insererFin(nodeBranche);
			}
		}
	}

	public void backTrackNodes() {

		Node nodeAInserer = listeFerme.getDernierNode().getNode();

		cheminFinal.insererDebut(nodeAInserer);

		ListeNode actual = listeFerme.getDernierNode().dernierNode;

		while (actual != null) {
			if (nodeAInserer.getParentID() == actual.getNode().getID()) {
				nodeAInserer = actual.getNode();
				cheminFinal.insererDebut(nodeAInserer);
			}

			actual = actual.dernierNode;
		}

	}

	public boolean memeEtat(int[][] A, int[][] B) {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (A[i][j] != B[i][j])
					return false;

		return true;
	}

	public void ajouterParts() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				add(part[i][j]);
	}

	public Node bougerVideGouche(Node currentNode, int flag) {
		int[][] tmpState = new int[3][3];
		int i, j;
		for (i = 0; i < 3; i++)
			for (j = 0; j < 3; j++)
				tmpState[i][j] = currentNode.getEtat()[i][j];
		int x = currentNode.getEmptyX();
		int y = currentNode.getEmptyY();

		int tmp = tmpState[x][y];
		tmpState[x][y] = tmpState[x][y - 1];
		tmpState[x][y - 1] = tmp;
		System.out.println("bouger Vide Gauche nouveau noeud idCont : " + idCont);
		return new Node(currentNode.getG() + 1, tmpState, ++idCont,
				currentNode.getID(), flag);
	}

	public Node bougerVideDroit(Node currentNode, int flag) {
		int[][] tmpState = new int[3][3];
		int i, j;
		for (i = 0; i < 3; i++)
			for (j = 0; j < 3; j++)
				tmpState[i][j] = currentNode.getEtat()[i][j];

		int x = currentNode.getEmptyX();
		int y = currentNode.getEmptyY();

		int tmp = tmpState[x][y];
		tmpState[x][y] = tmpState[x][y + 1];
		tmpState[x][y + 1] = tmp;
		System.out.println("bouger Vide Droit nouveau noeud idCont : " + idCont);
		return new Node(currentNode.getG() + 1, tmpState, ++idCont,
				currentNode.getID(), flag);
	}

	public Node bougerVideHaut(Node currentNode, int flag) {
		int[][] tmpState = new int[3][3];
		int i, j;
		for (i = 0; i < 3; i++)
			for (j = 0; j < 3; j++)
				tmpState[i][j] = currentNode.getEtat()[i][j];

		int x = currentNode.getEmptyX();
		int y = currentNode.getEmptyY();

		int tmp = tmpState[x][y];
		tmpState[x][y] = tmpState[x - 1][y];
		tmpState[x - 1][y] = tmp;
		System.out.println("bouger Vide Haut nouveau noeud idCont : " + idCont);
		return new Node(currentNode.getG() + 1, tmpState, ++idCont,
				currentNode.getID(), flag);
	}
	
	public Node bougerVideBas(Node currentNode, int flag)
        {
		int[][] tmpState = new int[3][3];
		int i, j;
		for (i = 0; i < 3; i++)
			for (j = 0; j < 3; j++)
				tmpState[i][j] = currentNode.getEtat()[i][j];

		int x = currentNode.getEmptyX();
		int y = currentNode.getEmptyY();

		int tmp = tmpState[x][y];
		tmpState[x][y] = tmpState[x + 1][y];
		tmpState[x + 1][y] = tmp;
		System.out.println("bouger Vide Bas nouveau noeud idCont : " + idCont);
		return new Node(currentNode.getG() + 1, tmpState, ++idCont,
				currentNode.getID(), flag);
	}

	public void random() {

		compterDesordre = 0;
		time.start();

	} 
        
	public boolean verifierFinJeu() {
		if (matriceDeparts[0][0] == 1 && matriceDeparts[0][1] == 2
				&& matriceDeparts[0][2] == 3 && matriceDeparts[1][0] == 4
				&& matriceDeparts[1][1] == 5 && matriceDeparts[1][2] == 6
				&& matriceDeparts[2][0] == 7 && matriceDeparts[2][1] == 8
				&& matriceDeparts[2][2] == 9) {
			JOptionPane.showMessageDialog(Puzzle.this,
					"Commencez le jeu!!",
					"Avertissement", JOptionPane.INFORMATION_MESSAGE);
			return true;
		}

		return false;
	}
	
    public void actionPerformed(ActionEvent event) 
    {
        int randNum;
        compterDesordre++;
        if (compterDesordre < 200)
        {
            if (iVide == 0 && jVide == 0)							// tile[1][0]
            {
                randNum = random.nextInt(2); 
				if (randNum == 0) 
				{
		                    tempEnt = matriceDeparts[0][0];
		                    imagen = part[0][0].getIcon();
		                    matriceDeparts[0][0] = matriceDeparts[0][1];
		                    part[0][0].setIcon(part[0][1].getIcon());
		                    matriceDeparts[0][1] = tempEnt;
		                    part[0][1].setIcon(imagen);
		                    jVide = 1;
				}
                else 
                {
                    tempEnt = matriceDeparts[0][0];
                    imagen = part[0][0].getIcon();
                    matriceDeparts[0][0] = matriceDeparts[1][0];
                    part[0][0].setIcon(part[1][0].getIcon());
                    matriceDeparts[1][0] = tempEnt;
                    part[1][0].setIcon(imagen);
                    iVide = 1;
                }
            }
            else 
            if (iVide == 0 && jVide == 1) 
            {
            	randNum = random.nextInt(3); 
                if(randNum == 0) 
                {
                    tempEnt = matriceDeparts[0][1];
                    imagen = part[0][1].getIcon();
                    matriceDeparts[0][1] = matriceDeparts[0][0];
                    part[0][1].setIcon(part[0][0].getIcon());
                    matriceDeparts[0][0] = tempEnt;
                    part[0][0].setIcon(imagen);
                    jVide = 0;
                } 
                else 
                if(randNum == 1) 
                {
                    tempEnt = matriceDeparts[0][1];
                    imagen = part[0][1].getIcon();
                    matriceDeparts[0][1] = matriceDeparts[0][2];
                    part[0][1].setIcon(part[0][2].getIcon());
                    matriceDeparts[0][2] = tempEnt;
                    part[0][2].setIcon(imagen);
                    jVide = 2;
                }
                else 
                {
                    tempEnt = matriceDeparts[0][1];
                    imagen = part[0][1].getIcon();
                    matriceDeparts[0][1] = matriceDeparts[1][1];
                    part[0][1].setIcon(part[1][1].getIcon());
                    matriceDeparts[1][1] = tempEnt;
                    part[1][1].setIcon(imagen);
                    iVide = 1;
                }
            }
            else
            if (iVide == 0 && jVide == 2)
            {
            	randNum = random.nextInt(2); 
                if (randNum == 0) 
                {
                    tempEnt = matriceDeparts[0][2];
                    imagen = part[0][2].getIcon();
                    matriceDeparts[0][2] = matriceDeparts[0][1];
                    part[0][2].setIcon(part[0][1].getIcon());
                    matriceDeparts[0][1] = tempEnt;
                    part[0][1].setIcon(imagen);
                    jVide = 1;
                }
				else 
				{
		                    tempEnt = matriceDeparts[0][2];
		                    imagen = part[0][2].getIcon();
		                    matriceDeparts[0][2] = matriceDeparts[1][2];
		                    part[0][2].setIcon(part[1][2].getIcon());
		                    matriceDeparts[1][2] = tempEnt;
		                    part[1][2].setIcon(imagen);
		                    iVide = 1;
				}
            }
            else
            if (iVide == 1 && jVide == 0)																				// plakidio[2][0]
            {
                randNum = random.nextInt(3);
                if (randNum == 0) 
                {
                    tempEnt = matriceDeparts[1][0];
                    imagen = part[1][0].getIcon();
                    matriceDeparts[1][0] = matriceDeparts[0][0];
                    part[1][0].setIcon(part[0][0].getIcon());
                    matriceDeparts[0][0] = tempEnt;
                    part[0][0].setIcon(imagen);
                    iVide = 0;
                }	
            else
            if (randNum == 1)
            {
                tempEnt = matriceDeparts[1][0];
                imagen = part[1][0].getIcon();
				matriceDeparts[1][0] = matriceDeparts[1][1];
				part[1][0].setIcon(part[1][1].getIcon());
				matriceDeparts[1][1] = tempEnt;
				part[1][1].setIcon(imagen);
				jVide = 1;
            }
            else 
            {
                tempEnt = matriceDeparts[1][0];
				imagen = part[1][0].getIcon();
				matriceDeparts[1][0] = matriceDeparts[2][0];
				part[1][0].setIcon(part[2][0].getIcon());
				matriceDeparts[2][0] = tempEnt;
				part[2][0].setIcon(imagen);
				iVide = 2;
            }
        }
        else
        if (iVide == 1 && jVide == 1) 									
	{
            randNum = random.nextInt(4); 
            if (randNum == 0)
            {
					tempEnt = matriceDeparts[1][1];
					imagen = part[1][1].getIcon();
					matriceDeparts[1][1] = matriceDeparts[0][1];
					part[1][1].setIcon(part[0][1].getIcon());
					matriceDeparts[0][1] = tempEnt;
					part[0][1].setIcon(imagen);

					iVide = 0;
				}

				else if (randNum == 1) 
				{

					tempEnt = matriceDeparts[1][1];
					imagen = part[1][1].getIcon();
					matriceDeparts[1][1] = matriceDeparts[1][0];
					part[1][1].setIcon(part[1][0].getIcon());
					matriceDeparts[1][0] = tempEnt;
					part[1][0].setIcon(imagen);

					jVide = 0;
				}

				else if (randNum == 2) 
				{

					tempEnt = matriceDeparts[1][1];
					imagen = part[1][1].getIcon();
					matriceDeparts[1][1] = matriceDeparts[1][2];
					part[1][1].setIcon(part[1][2].getIcon());
					matriceDeparts[1][2] = tempEnt;
					part[1][2].setIcon(imagen);

					jVide = 2;
				}

				else 
				{

					tempEnt = matriceDeparts[1][1];
					imagen = part[1][1].getIcon();
					matriceDeparts[1][1] = matriceDeparts[2][1];
					part[1][1].setIcon(part[2][1].getIcon());
					matriceDeparts[2][1] = tempEnt;
					part[2][1].setIcon(imagen);

					iVide = 2;
				}

			}

			else if (iVide == 1 && jVide == 2)
			{
				randNum = random.nextInt(3); 

				if (randNum == 0) 
				{

					tempEnt = matriceDeparts[1][2];
					imagen = part[1][2].getIcon();
					matriceDeparts[1][2] = matriceDeparts[0][2];
					part[1][2].setIcon(part[0][2].getIcon());
					matriceDeparts[0][2] = tempEnt;
					part[0][2].setIcon(imagen);

					iVide = 0;
				}

				else if (randNum == 1)
				{

					tempEnt = matriceDeparts[1][2];
					imagen = part[1][2].getIcon();
					matriceDeparts[1][2] = matriceDeparts[1][1];
					part[1][2].setIcon(part[1][1].getIcon());
					matriceDeparts[1][1] = tempEnt;
					part[1][1].setIcon(imagen);

					jVide = 1;
				}

				else 
				{

					tempEnt = matriceDeparts[1][2];
					imagen = part[1][2].getIcon();
					matriceDeparts[1][2] = matriceDeparts[2][2];
					part[1][2].setIcon(part[2][2].getIcon());
					matriceDeparts[2][2] = tempEnt;
					part[2][2].setIcon(imagen);

					iVide = 2;
				}
			}

			else if (iVide == 2 && jVide == 0)
												
			{
				randNum = random.nextInt(2);

				if (randNum == 0) 
				{

					tempEnt = matriceDeparts[2][0];
					imagen = part[2][0].getIcon();
					matriceDeparts[2][0] = matriceDeparts[1][0];
					part[2][0].setIcon(part[1][0].getIcon());
					matriceDeparts[1][0] = tempEnt;
					part[1][0].setIcon(imagen);

					iVide = 1;
				}
				else 
				{

					tempEnt = matriceDeparts[2][0];
					imagen = part[2][0].getIcon();
					matriceDeparts[2][0] = matriceDeparts[2][1];
					part[2][0].setIcon(part[2][1].getIcon());
					matriceDeparts[2][1] = tempEnt;
					part[2][1].setIcon(imagen);

					jVide = 1;
				}

			}

			else if (iVide == 2 && jVide == 1)
													
			{
				randNum = random.nextInt(3); 

				if (randNum == 0) 
				{

					tempEnt = matriceDeparts[2][1];
					imagen = part[2][1].getIcon();
					matriceDeparts[2][1] = matriceDeparts[1][1];
					part[2][1].setIcon(part[1][1].getIcon());
					matriceDeparts[1][1] = tempEnt;
					part[1][1].setIcon(imagen);

					iVide = 1;

				} else if (randNum == 1) 
				{

					tempEnt = matriceDeparts[2][1];
					imagen = part[2][1].getIcon();
					matriceDeparts[2][1] = matriceDeparts[2][0];
					part[2][1].setIcon(part[2][0].getIcon());
					matriceDeparts[2][0] = tempEnt;
					part[2][0].setIcon(imagen);

					jVide = 0;
				}

				else 
				{

					tempEnt = matriceDeparts[2][1];
					imagen = part[2][1].getIcon();
					matriceDeparts[2][1] = matriceDeparts[2][2];
					part[2][1].setIcon(part[2][2].getIcon());
					matriceDeparts[2][2] = tempEnt;
					part[2][2].setIcon(imagen);

					jVide = 2;
				}
			}

			else if (iVide == 2 && jVide == 2) 
													
			{
				randNum = random.nextInt(2);

				if (randNum == 0) 
				{

					tempEnt = matriceDeparts[2][2];
					imagen = part[2][2].getIcon();
					matriceDeparts[2][2] = matriceDeparts[1][2];
					part[2][2].setIcon(part[1][2].getIcon());
					matriceDeparts[1][2] = tempEnt;
					part[1][2].setIcon(imagen);

					iVide = 1;
				}
				else 
				{

					tempEnt = matriceDeparts[2][2];
					imagen = part[2][2].getIcon();
					matriceDeparts[2][2] = matriceDeparts[2][1];
					part[2][2].setIcon(part[2][1].getIcon());
					matriceDeparts[2][1] = tempEnt;
					part[2][1].setIcon(imagen);

					jVide = 1;
				}
			}
		} else 
		{
			stopTimer();
		}

	}
    
    public void stopTimer()
    {
        time.stop();
    }

    class TimerCheminFinal implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            if (listeCourrent != null)
            {
                for (int i = 0; i < 3; i++)
                    for (int j = 0; j < 3; j++)
                    {
                        part[i][j].setIcon(new ImageIcon(getClass().getResource(String.format("/Imagenes/%d.jpg",listeCourrent.getNode().getEtat()[i][j]))));
                    }
                listeCourrent = listeCourrent.getSiguiente();
            } 
            else
		stopTimer1();
        } 
        public void stopTimer1()
        {
            time1.stop();
        }
    }
}
