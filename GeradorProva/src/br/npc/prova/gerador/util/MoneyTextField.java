package br.npc.prova.gerador.util;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MoneyTextField extends JTextField {

	public MoneyTextField(int tamanho) {
		addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				setSelectionStart(getText().length());
			}

			@Override
			public void focusLost(FocusEvent e) {
				setSelectionStart(getText().length());
			}
			
		});

		setColumns(tamanho + 2);
		setDocument(new MoneyDocument(tamanho));
	}

	@Override
	public boolean requestFocusInWindow() {
		
		return super.requestFocusInWindow();
	}
	
}
