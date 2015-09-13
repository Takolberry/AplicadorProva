package br.npc.prova.aplicador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JLabel;

public class ServerReader implements Runnable {

	private JLabel lblInfo;
	
	private Socket socketCliente;
	
	private StringBuffer bufferRead = new StringBuffer();
	
	private boolean continuar = true;
	
	public ServerReader(JLabel lblInfo, Socket socketCliente) {
		this.lblInfo = lblInfo;
		this.socketCliente = socketCliente;
	}

	@Override
	public void run() {
		InputStream input = null;
		BufferedReader bis = null;
		try {
			input = socketCliente.getInputStream();

			bis = 
				new BufferedReader(
						new InputStreamReader(input));
			
			while(this.continuar) {
				try {
					append(bis.readLine());
					this.lblInfo.setText("Mensagem recebida de " + this.socketCliente.getInetAddress().getHostAddress());
					System.out.println("Mensagem recebida de " + this.socketCliente.getInetAddress().getHostAddress());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(bis, input, socketCliente);
		}
	}
	
	private void close(
			BufferedReader bis,
			InputStream input, 
			Socket socketCliente) {
		if (bis != null) {
			try {
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (socketCliente != null) {
			try {
				socketCliente.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void append(String msg) {
		this.bufferRead.append(msg);
	}
	
	public void clearBufferRead() {
		this.bufferRead.setLength(0);
	}
	
	public String getBuffer() {
		return this.bufferRead.toString();
	}

	public Socket getSocketCliente() {
		return socketCliente;
	}
	
	public void interromper() {
		this.continuar = false;
	}
	
}
