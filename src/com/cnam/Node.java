package com.cnam;

/**
 * @author NFA010-CNAM
 */

public class Node
{
    private int[][] etat = new int[3][3];
    private int h; 
    private int g;
    private int id;
    private int parentID;
    private int videX;
    private int videY;

    public Node()
    {
        g = -1;
        id = -1;
        parentID = -1;
    }

    public Node(int g, int[][] etat, int id, int parentID, int flag) 
    {
		this.g = g;
		switch (flag)
	        {
	            case 1:
	            setetat_amplitud(etat);
	            break;
	            case 2:
	            setetat_profundidad(etat);
	            break;
		}
		this.id = id;
		this.parentID = parentID;
    }

    public void copierTout(Node n, int flag) 
    {
        switch (flag)
        {
            case 1:
            setetat_amplitud(n.getEtat());
            break;
            case 2:
            setetat_profundidad(n.getEtat());
            break;
		}
		h = n.getH();
		g = n.getG();
		id = n.id;
		parentID = n.getParentID();
		videX = n.getEmptyX();
		videY = n.getEmptyY();
    }

    public int[][] getEtat()
    {
    	return etat;
    }

    public void setetat_profundidad(int[][] mietat)
    {

	for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
		this.etat[i][j] = mietat[i][j];
	calcularXY();
	System.out.println("calculerF_profondeur : ");
	calcularF_profundidad();

    }

    public void setetat_amplitud(int[][] state)
    {
		for (int i = 0; i < 3; i++)
	            for (int j = 0; j < 3; j++)
			this.etat[i][j] = state[i][j];
		calcularXY();
		System.out.println("calculerF_amplitude : ");
		calcularF_amplitud();
    }

    public void calcularXY() 
    {
    	for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
				if (etat[i][j] == 9)
		        {
		            videX = i;
		            videY = j;
		            break;
				}
    }

    public int getID() 
    {
    	return id;
    }

    public int getParentID() 
    {
        return parentID;
    }

    public int getG() 
    {
    	return g;
    }

    public int getH() 
    {
    	return h;
    }

    public int getEmptyX() 
    {
    	return videX;
    }

    public int getEmptyY() 
    {
    	return videY;
    }

    public int getF() 
    {
    	return g + h;
    }

    public void calcularF_amplitud()
    {
        int tempH = 0;
		int etatMetaX = 0, etatMetaY = 0;
		for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++){
            	switch (etat[i][j])
                {
                    case 1:
                    etatMetaX = 0;
                    etatMetaY = 0;
                    break;
                    case 2:
                    etatMetaX = 0;
                    etatMetaY = 1;
                    break;
                    case 3:
                    etatMetaX = 0;
                    etatMetaY = 2;
                    break;
                    case 4:
                    etatMetaX = 1;
                    etatMetaY = 0;
                    break;
                    case 5:
                    etatMetaX = 1;
                    etatMetaY = 1;
                    break;
                    case 6:
                    etatMetaX = 1;
                    etatMetaY = 2;
                    break;
                    case 7:
                    etatMetaX = 2;
                    etatMetaY = 0;
                    break;
                    case 8:
                    etatMetaX = 2;
                    etatMetaY = 1;
                    break;
                    case 9:
                    etatMetaX = 2;
                    etatMetaY = 2;
                    break;
                }
            	tempH += Math.abs(etatMetaX - i) + Math.abs(etatMetaY - j);
            }
			System.out.println("id : " + id);
			System.out.println("parentID : " + parentID);
			System.out.println("h : " + h);
			System.out.println("g : " + g);
			System.out.println("Position * X : " + videX);
			System.out.println("Position * Y : " + videY);
			for (int i = 0; i < 3; i++) {
	            for (int j = 0; j < 3; j++)
	            {
	            	System.out.print(etat[i][j] + " ");
	            }
				System.out.println("");
    		}
			this.h = tempH;
	}

    public void calcularF_profundidad() 
    {
	int tempF = 0;
	int etatMetaX = 0, etatMetaY = 0;
	for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
            {
            	switch (etat[i][j])
                {
                    case 1:
                    etatMetaX = 0;
                    etatMetaY = 0;
                    break;
                    case 2:
                    etatMetaX = 0;
                    etatMetaY = 1;
                    break;
                    case 3:
                    etatMetaX = 0;
                    etatMetaY = 2;
                    break;
                    case 4:
                    etatMetaX = 1;
                    etatMetaY = 0;
                    break;
                    case 5:
                    etatMetaX = 1;
                    etatMetaY = 1;
                    break;
                    case 6:
                    etatMetaX = 1;
                    etatMetaY = 2;
                    break;
                    case 7:
                    etatMetaX = 2;
                    etatMetaY = 0;
                    break;
                    case 8:
                    etatMetaX = 2;
                    etatMetaY = 1;
                    break;
                    case 9:
                    etatMetaX = 2;
                    etatMetaY = 2;
                    break;
		}
		int tempAm = (etatMetaX - i);
		int tempBm = (etatMetaY - j);
        if(tempAm!=0||tempBm!=0)
                tempF++;
        }
		System.out.println("id : " + id);
		System.out.println("parentID : " + parentID);
		System.out.println("h : " + h);
		System.out.println("g : " + g);
		System.out.println("Position vide X : " + videX);
		System.out.println("Position vide Y : " + videY);
		for (int i = 0; i < 3; i++) {
	        for (int j = 0; j < 3; j++)
	        {
	        	System.out.print(etat[i][j] + " ");
	        }
			System.out.println("");
		}
        this.h = tempF;
	}
}