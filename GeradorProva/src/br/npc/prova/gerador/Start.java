package br.npc.prova.gerador;

import java.awt.EventQueue;

import br.npc.prova.gerador.tela.principal.TelaPrincipal;

public class Start {


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new TelaPrincipal();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
