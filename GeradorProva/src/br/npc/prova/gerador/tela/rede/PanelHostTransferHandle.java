package br.npc.prova.gerador.tela.rede;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

public class PanelHostTransferHandle extends TransferHandler {

	public int getSourceActions(JComponent c) {
	    return MOVE;
	}

	public Transferable createTransferable(JComponent c) {
		PanelHost host = (PanelHost)c;
		
	    return new StringSelection(host.getIdPanel());
	}
	
	
	public void exportDone(JComponent c, Transferable t, int action) {
	    if (action == TransferHandler.MOVE) {
	    	PanelHost hostOrigem = (PanelHost)c;

	    	hostOrigem.setIp("XXX.XXX.XXX.XXX");

	    }
	}


	public boolean importData(TransferHandler.TransferSupport info) {
		if (!info.isDrop()) {
            return false;
        }

		PanelHost hostDestino = (PanelHost)info.getComponent();
		
		 // Get the string that is being dropped.
		Transferable t = info.getTransferable();
		String data;
		try {
			data = (String) t.getTransferData(DataFlavor.stringFlavor);
		} catch (Exception e) {
			return false;
		}
		hostDestino.setIp(((PanelHost)TelaRede.labPanels.get(data)).getIp());

	    return true;
	}
	
	
	public boolean canImport(TransferHandler.TransferSupport support) {
	    // for the demo, we will only support drops (not clipboard paste)
	    if (!support.isDrop()) {
	        return false;
	    }

	    // we only import Strings
	    if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
	        return false;
	    }

	    // check if the source actions (a bitwise-OR of supported actions)
	    // contains the MOVE action
	    boolean moveSupported = (MOVE & support.getSourceDropActions()) == MOVE;
	    if (moveSupported) {
	        support.setDropAction(MOVE);
	        return true;
	    }

	    // MOVE is not supported, so reject the transfer
	    return false;
	}
}
