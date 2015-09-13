package br.npc.prova.gerador.editor.treemodel;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

import br.npc.nucleo.prova.Teste;

public class ProvaCellRederer implements TreeCellRenderer {

	private TreeCellRenderer renderer;
	
	public ProvaCellRederer(TreeCellRenderer renderer) {
		this.renderer = renderer;
	}
	
	@Override
	public Component getTreeCellRendererComponent(
			JTree jTree, Object value,
			boolean selected, 
			boolean expanded, 
			boolean leaf, 
			int row, 
			boolean hasFocus) {
		String nohText = value.toString();
		if (value instanceof Teste) {
			Teste teste = (Teste)value;
			nohText = teste.toStringResumido(45);
		}
		
		return renderer.getTreeCellRendererComponent(
				jTree, 
				nohText, 
				selected,
				expanded, 
				leaf, 
				row, 
				hasFocus);
	}

}
