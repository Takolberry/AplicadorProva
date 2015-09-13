package br.npc.prova.gerador.editor.treemodel;

import javax.swing.JTree;

import br.npc.nucleo.prova.Prova;

public class ProvaTree extends JTree {

	public ProvaTree(Prova prova) {
		super(new ProvaTreeModel(prova));
		setCellRenderer(new ProvaCellRederer(getCellRenderer()));
	}

}
