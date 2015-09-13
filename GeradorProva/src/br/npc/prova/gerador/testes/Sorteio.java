package br.npc.prova.gerador.testes;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Sorteio extends JPanel {

	private static final long serialVersionUID = 1L;

	JFrame imageFrame;

	public static void main(String[] args) {
		Sorteio d = new Sorteio();
		d.init();
	}

	public void init() {

		imageFrame = new JFrame();
		imageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		imageFrame.setSize(500, 500);
		imageFrame.getContentPane().add(this);
		imageFrame.setVisible(true);
		System.out.println("largura do painel:" + this.getWidth()
				+ ", altura do painel:" + this.getHeight());
		this.repaint();

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		int panelWidth = this.getWidth();
		int panelHeight = this.getHeight();
		int ovalWidth = 90;
		int ovalHeight = 90;
		g2d.drawOval((panelWidth - ovalWidth) / 2,
				(panelHeight - ovalHeight) / 2, ovalWidth, ovalHeight);

	}

}
