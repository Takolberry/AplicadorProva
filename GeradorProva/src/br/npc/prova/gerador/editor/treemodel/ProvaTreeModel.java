package br.npc.prova.gerador.editor.treemodel;

import java.util.ArrayList;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import br.npc.nucleo.prova.Prova;
import br.npc.nucleo.prova.Questao;

public class ProvaTreeModel implements TreeModel {

	private Prova prova;

	private ArrayList<TreeModelListener> treeModelListeners = 
			new ArrayList<TreeModelListener>();
	
	public ProvaTreeModel(Prova prova) {
		this.prova = prova;
	}

    public void fireTreeStructureChanged(Prova p) {
        TreeModelEvent e = new TreeModelEvent(this, new Object[] {p});
        for (TreeModelListener tml : treeModelListeners) {
            tml.treeStructureChanged(e);
        }
    }
	
	@Override
	public void addTreeModelListener(TreeModelListener listener) {
		treeModelListeners.add(listener);
	}

	@Override
	public Object getChild(Object parent, int indexChild) {
		if (parent instanceof Prova) {
			Prova p = (Prova)parent;
			return p.getQuestoes().get(indexChild);
//			String childText = p.getQuestoes().get(indexChild).toString();
//			childText = childText.replaceAll("Questao - ", "").trim();
//			String childTextFinal = childText.substring(0, Math.min(45, childText.length()));
//			if (childText.length() > childTextFinal.length()) {
//				childTextFinal += "...";
//			}
//			return childTextFinal;
		} 
		return null;
	}

	@Override
	public int getChildCount(Object parent) {
		if (parent instanceof Prova) {
			Prova p = (Prova)parent;
			return p.getQuestoes().size();
		}
		return 0;
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		if (parent instanceof Prova) {
			Prova p = (Prova)parent;
			if (child instanceof Questao) {
				Questao q = (Questao)child;
				return p.getQuestoes().indexOf(q);
			}
		}
		return -1;
	}

	@Override
	public Object getRoot() {
		return this.prova;
	}

	@Override
	public boolean isLeaf(Object node) {
		if (node instanceof Prova) {
			Prova p = (Prova)node;
			return p.getQuestoes().size() == 0;
		}
		return true;
	}

	@Override
	public void removeTreeModelListener(TreeModelListener listener) {
		treeModelListeners.remove(listener);
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		System.out.println("*** valueForPathChanged : "
                + path + " --> " + newValue);
	}

}
