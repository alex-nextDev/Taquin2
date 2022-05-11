package com.cnam;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {

		InterfaceUtilisateur frame = new InterfaceUtilisateur("Puzzle");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.pack();

		frame.setResizable(false);

		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

	}
}
