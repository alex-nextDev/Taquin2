package com.cnam;

public class ListLien
{
    private ListeNode premierNode;
    private ListeNode dernierNode;
    
    public ListLien()
    {
    	premierNode = dernierNode = null;
    }
    
    public void insererFin(Node node)
    {
    	System.out.println("inserer Fin : ");
    	System.out.println("node g : "+node.getG());
    	if (estVide())
            premierNode = dernierNode = new ListeNode(node);
        else 
		{
            ListeNode newNode = new ListeNode(node);
            dernierNode.suivantNode = newNode;
            newNode.dernierNode = dernierNode;
            dernierNode = newNode;
		}
    }
    
    public void insererDebut(Node node)
    {
		System.out.println("inserer Debut : ");
		System.out.println("node g : "+node.getG());
    	if (estVide())
            premierNode = dernierNode = new ListeNode(node);
		else
	    {
            ListeNode newNode = new ListeNode(node);
            newNode.suivantNode = premierNode;
            premierNode.dernierNode = newNode;
            premierNode = newNode;
		}
    }

    public Node getNodeMineur()
    {
		Node minimumNode = premierNode.getNode();
		ListeNode current = premierNode.getSiguiente();
		while (current != null)
        {
            if (current.getNode().getF() < minimumNode.getF())
            minimumNode = current.getNode();
            current = current.getSiguiente();
        }
		return minimumNode;
    }

    public void supprimerNode(Node node) 
    {
		if (estVide())
	            return ;
		else 
            if (memeNode(node, premierNode.getNode())&& premierNode == dernierNode) 
            {
            	premierNode = dernierNode = null;
            	return;
	        }else if (memeNode(node, premierNode.getNode())) 												// komvos
			{
                premierNode = premierNode.getSiguiente();
                premierNode.dernierNode = null;
                return;
			}else
            if (memeNode(node, dernierNode.getNode()))
            {
				dernierNode = dernierNode.getAnterior();
				dernierNode.suivantNode = null;
	        }
	        else 
	        {
				ListeNode current = premierNode;
				while (current.getSiguiente() != null)
                {
                    if (memeNode(current.getSiguiente().getNode(), node))
                    {
                        current.suivantNode.suivantNode.dernierNode = current;
                        current.suivantNode = current.getSiguiente().getSiguiente();
                        return; 
                    }
                    else
                    	current = current.suivantNode;
				}
	        }
    }

    public boolean estVide()
    {
		return premierNode == null; 
    }

    public boolean prouverExistence(Node node)
    {
		if (!estVide())
	        {
	            ListeNode current = premierNode;
	            while (current != null)
	            {
	                if (memeNode(current.getNode(), node))
	                    return true;
	                else
	                    current = current.getSiguiente();
	            }
	            return false;
		}
		return false;
    }

    public boolean memeNode(Node A, Node B) 
    {
		int[][] stateA = A.getEtat();
		int[][] stateB = B.getEtat();
		for (int i = 0; i < 3; i++)
	            for (int j = 0; j < 3; j++)
			if (stateA[i][j] != stateB[i][j])
	                    return false;
		return true;
    }

    public ListeNode getDernierNode() 
    {
    	return dernierNode;
    }

    public ListeNode getPremierNode()
    {
    	return premierNode;
    }

	
    public void viderListe()
    {
    	premierNode = dernierNode = null;
    }
    
    public void imprimerListe()
    {
		if (!estVide())
	        {
	            ListeNode current = premierNode;
	            while (current != null)
	            {
	            	if(current.getNode() != null) {
	            		System.out.print(" g : "+current.getNode().getG());
	            		System.out.print(", f : "+current.getNode().getF());
	            	}
	            	current = current.getSiguiente();
	            }
	            System.out.println("");
		}
    }
}
