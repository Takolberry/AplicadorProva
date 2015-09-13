package br.npc.prova.gerador.editor;

import java.awt.event.ActionEvent;

import javax.swing.JEditorPane;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;

public class NegritoAction extends StyledEditorKit.StyledTextAction {
	private static final long serialVersionUID = 9174670038684056758L;

	public NegritoAction() {
		super(" N ");
	}

	public String toString() {
		return "Negrito";
	}

	public void actionPerformed(ActionEvent e) {
		JEditorPane editor = getEditor(e);
		if (editor != null) {
			StyledEditorKit kit = getStyledEditorKit(editor);
			MutableAttributeSet attr = kit.getInputAttributes();
			boolean bold = (StyleConstants.isBold(attr)) ? false : true;
			SimpleAttributeSet sas = new SimpleAttributeSet();
			StyleConstants.setBold(sas, bold);
			setCharacterAttributes(editor, sas, false);

		}
	}
}
