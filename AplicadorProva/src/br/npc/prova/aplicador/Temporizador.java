package br.npc.prova.aplicador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

public class Temporizador implements Runnable {

	private int segundosRestantes;
	private JLabel label;
	private ActionListener action;

	public Temporizador(int segundosRestantes, JLabel label, ActionListener action) {
		super();
		this.segundosRestantes = segundosRestantes;
		this.label = label;
		this.action = action;
	}

	@Override
	public void run() {
		try {
			while (segundosRestantes > 0) {
				int min = segundosRestantes / 60;
				int seg = segundosRestantes % 60;
				this.label.setText(String.format("%02d min %02d s", min, seg));

				Thread.sleep(1000);
				this.segundosRestantes--;
			}
			this.label.setText(String.format("00 min 00 s"));
			ActionEvent event = new ActionEvent(this, 0, "");
			
			this.action.actionPerformed(event);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
