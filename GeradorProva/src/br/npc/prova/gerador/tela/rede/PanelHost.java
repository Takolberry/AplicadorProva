package br.npc.prova.gerador.tela.rede;

import java.awt.Color;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.DropMode;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;

public class PanelHost extends JPanel implements MouseListener, MouseMotionListener {
	
	private static final String PATH = "/br/npc/prova/gerador/images/Network-48-2.png";
	
	private String idPanel;
	
	private String ip;
	
	private boolean selecionado = false;
	
	private Color defaultColor;
	
	private String path;
	private String pathMissing;
	
	
	
	private MouseEvent firstMouseEvent = null;
	
	
	private boolean dragEnabled;
	private DropMode dropMode;
//	private TransferHandler transferHandler;
	
	public PanelHost(String idPanel, String ip, Color defaultColor, String path, String pathMissing, boolean admin) {
		this.idPanel = idPanel;
		this.path = path;
		this.pathMissing = pathMissing;
		this.defaultColor = defaultColor; 
		setIp(ip);
		setBorder();
		if (!admin) {
			setTransferHandler(new PanelHostTransferHandle());
			addMouseListener(this);
			addMouseMotionListener(this);
		}
	}

	public boolean isSelecionado() {
		return selecionado;
	}
	
	public void clicar() {
		this.selecionado = !this.selecionado;
		setBorder();
		updateUI();
	}

	private void setBorder() {
		if (selecionado) {
			setBorder(BorderFactory.createLineBorder(Color.RED, 3, true));
		} else {
			setBorder(BorderFactory.createLineBorder(this.defaultColor, 1, false));
		}
	}
	
	public void setDragEnabled(boolean b) {
		this.dragEnabled = b;
	}
	
	public String getIp() {
		return ip;
	}
	
	public String getIdPanel() {
		return idPanel;
	}

	public void setIp(String ip) {
		this.ip = ip;
		removeAll();
		JLabel lblIp = new JLabel("IP : " + ip);
		lblIp.setHorizontalTextPosition(SwingConstants.CENTER);
		lblIp.setVerticalTextPosition(SwingConstants.BOTTOM);
		if (ip.equals("XXX.XXX.XXX.XXX")) {
			lblIp.setIcon(new ImageIcon(TelaRede.class.getResource(this.pathMissing)));
		} else {
			lblIp.setIcon(new ImageIcon(TelaRede.class.getResource(this.path)));
		}
		add(lblIp);
	}

	public final void setDropMode(DropMode dropMode) {
		this.dropMode = dropMode;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		clicar();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// Don't bother to drag if there is no image.
		if (ip.equals("XXX.XXX.XXX.XXX")) {
			return;
		}

		firstMouseEvent = e;
		e.consume();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (firstMouseEvent != null) {
			PanelHost dest = (PanelHost)e.getSource();
			PanelHost orig = (PanelHost)firstMouseEvent.getSource();
			String ip = orig.getIp();
			orig.setIp(dest.getIp());
			dest.setIp(ip);
			
			repaint();
		}
		firstMouseEvent = null;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// Don't bother to drag if the component displays no image.
		if (ip.equals("XXX.XXX.XXX.XXX")) {
			return;
		}

		if (firstMouseEvent != null) {
			e.consume();

//			// If they are holding down the control key, COPY rather than MOVE
			int ctrlMask = InputEvent.CTRL_DOWN_MASK;
			int action = ((e.getModifiersEx() & ctrlMask) == ctrlMask) ? TransferHandler.COPY
					: TransferHandler.MOVE;

			int dx = Math.abs(e.getX() - firstMouseEvent.getX());
			int dy = Math.abs(e.getY() - firstMouseEvent.getY());
			// Arbitrarily define a 5-pixel shift as the
			// official beginning of a drag.
			if (dx > 5 || dy > 5) {
				// This is a drag, not a click.
				JComponent c = (JComponent) e.getSource();
				TransferHandler handler = c.getTransferHandler();
				// Tell the transfer handler to initiate the drag.
				handler.exportAsDrag(c, firstMouseEvent, action);
				firstMouseEvent = null;
			}
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	

}
