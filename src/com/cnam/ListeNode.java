package com.cnam;

public class ListeNode
{
    private Node node;
    public ListeNode suivantNode;
    public ListeNode dernierNode;
    
    public ListeNode() 
    {
        node = new Node();
    }

    public ListeNode(Node node)
    {
    	this(node, null, null);
    }

    public ListeNode(Node node, ListeNode previous, ListeNode next)
    {
		this.node = node;
		System.out.println(node.getID());
		dernierNode = previous;
		if(previous != null && previous.getNode() != null)
			System.out.println(previous.getNode().getID());
		suivantNode = next;
		if(next != null && next.getNode() != null)
			System.out.println(next.getNode().getID());
    }

    public Node getNode() 
    {
        return node;
    }

    public ListeNode getSiguiente() 
    {
    	return suivantNode;
    }

    public ListeNode getAnterior() 
    {
    	return dernierNode;
    }
}
