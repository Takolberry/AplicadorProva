package br.npc.prova.gerador.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class TextEditorPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Font font;

	/**
	 * Create the panel.
	 */
	public TextEditorPane() {
		setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		add(toolBar, BorderLayout.NORTH);

		JButton btnNegrito = new JButton(" N ");
		btnNegrito.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnNegrito.setBorder(UIManager.getBorder("ToggleButton.border"));
		btnNegrito.setAction(new NegritoAction());
		toolBar.add(btnNegrito);

		JToggleButton btnItalico = new JToggleButton(" I  ");
		btnItalico.setBorder(UIManager.getBorder("ToggleButton.border"));
		btnItalico.setFont(new Font("Tahoma", Font.ITALIC, 18));
		btnItalico.setAction(new ItalicoAction());
		toolBar.add(btnItalico);

		JButton btnCor = new JButton("Cor");
		btnCor.setForeground(Color.RED);
		btnCor.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnCor.setAction(new CorAction());
		toolBar.add(btnCor);

		JButton btnFonte = new JButton(" Fonte ");
		btnFonte.setForeground(Color.BLACK);
		btnFonte.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnFonte.setAction(new FontAction());
		toolBar.add(btnFonte);

		JTextPane textPane = new JTextPane();
		textPane.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JScrollPane scrollPane = new JScrollPane(textPane);
		add(scrollPane, BorderLayout.CENTER);

		add(scrollPane, BorderLayout.CENTER);
	}
}
