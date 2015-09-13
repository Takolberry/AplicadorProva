package br.npc.prova.gerador.tela.rede;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

public class ConectorAplicador implements Runnable {

	private JLabel lbInfo;
	
	private ActionListener ouvinte;
	
	private boolean continuar = true;
	
	private int porta;
	
	public ConectorAplicador(int porta, JLabel lbInfo) {
		this.porta = porta;
		this.lbInfo = lbInfo;
	}

	public synchronized void setContinuar(boolean continuar) {
		this.continuar = continuar;
	}
	
	public void addActionListener(ActionListener a) {
		this.ouvinte = a;
	}
	
	@Override
	public void run() {
		//Declaro o ServerSocket
		ServerSocket serv = null; 
		
		//Declaro o Socket de comunicação
		Socket socketCliente = null;
		
		//Declaro o leitor para a entrada de dados
		BufferedReader entrada = null;
				
		try{
			
			//Cria o ServerSocket na porta 7000 se estiver disponível
			serv = new ServerSocket(porta);
			this.lbInfo.setText("Aguardando conexoes");
			while(continuar) {
				socketCliente = serv.accept();
				
				ClienteReader clienteReader = new ClienteReader(this.lbInfo, socketCliente);
				new Thread(clienteReader).start();
				
				ActionEvent event = new ActionEvent(clienteReader, 0, "cliente_conectado");
				this.ouvinte.actionPerformed(event);
				
				this.lbInfo.setText("Conexao estabelecida com " + socketCliente.getInetAddress().getHostAddress());
			}

			this.lbInfo.setText("Encerrando audicao");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				serv.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void interromper() {
		setContinuar(false);
	}

}
