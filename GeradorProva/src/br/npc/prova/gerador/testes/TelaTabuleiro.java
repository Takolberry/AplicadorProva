package br.npc.prova.gerador.testes;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TelaTabuleiro extends JFrame {
	public TelaTabuleiro() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(new JButton("A"), gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(new JButton("B"), gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		panel.add(new JButton("C"), gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		panel.add(new JButton("D"), gbc);
		
		getContentPane().add(panel, BorderLayout.CENTER);
		pack();
		setVisible(true);
	}
	public static void main(String[] Args) {
		new TelaTabuleiro();
	}
}


