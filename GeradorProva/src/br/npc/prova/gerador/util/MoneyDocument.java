package br.npc.prova.gerador.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

@SuppressWarnings("serial")
public class MoneyDocument extends PlainDocument {

	private int maxNumeroDigitos;
	
	public MoneyDocument(int maxNumeroDigitos) {
		this.maxNumeroDigitos = maxNumeroDigitos;
	}

	public void insertString(int offs, String str, AttributeSet a)
			throws BadLocationException {

		str = str.replace(".", ""); 
		str = str.replace(",", ""); 
		
		//Retirando caracteres não numericos do valor de entrada
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (!Character.isDigit(c)) {
				return;
			}
		}

		String texto = getText(0, getLength());
		if (texto.length() < this.maxNumeroDigitos) {
			super.remove(0, getLength());
			texto = texto.replace(".", "").replace(",", "");
			StringBuffer s = new StringBuffer(texto + str);

			//Eliminando Zeros à esquerda
			while (s.length() > 0 && s.charAt(0) == '0') {
				s.deleteCharAt(0);
			}

			if (s.length() < 3) {
				if (s.length() < 1) {
					s.insert(0, "000");
				} else if (s.length() < 2) {
					s.insert(0, "00");
				} else {
					s.insert(0, "0");
				}
			}

			s.insert(s.length() - 2, ",");

			if (s.length() > 6) {
				s.insert(s.length() - 6, ".");
			}

			if (s.length() > 10) {
				s.insert(s.length() - 10, ".");
			}

			super.insertString(0, s.toString(), a);
		}
	}

	public void remove(int offset, int length) throws BadLocationException {
		super.remove(offset, length);
		String texto = getText(0, getLength());
		texto = texto.replace(",", "");
		texto = texto.replace(".", "");
		super.remove(0, getLength());
		insertString(0, texto, null);
	}
	
//	@Override
//	protected void insertUpdate(DefaultDocumentEvent chng, AttributeSet attr) {
//		String texto;
//		try {
//			if (chng.getType() == EventType.INSERT) {
//				texto = getText(0, getLength());
//				texto = texto.replace(",", "");
//				texto = texto.replace(".", "");
//				insertString(0, texto, null);
//			} else {
//				super.insertUpdate(chng, attr);
//			}
//		} catch (BadLocationException e) {
//			e.printStackTrace();
//		}
//	}
}
