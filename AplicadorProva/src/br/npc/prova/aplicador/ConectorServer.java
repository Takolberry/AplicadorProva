package br.npc.prova.aplicador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

import javax.swing.JLabel;

public class ConectorServer implements Runnable {

	private static final int BROADCAST_PORT = 8888;
	private static final int SOCKET_PORT = 8765;
	
	private ActionListener ouvinte;
	
	private boolean continuar = true;
	
	private String ip;
	
	private int porta;
	
	private JLabel lblProcurandoServidor;
	private String filteredMessage = "";
	
	public ConectorServer(String ip, int porta, JLabel lblProcurandoServidor) {
		this.ip = ip;
		this.porta = porta;
		this.lblProcurandoServidor = lblProcurandoServidor;
		this.lblProcurandoServidor.setText("Procurando servidor");
	}

	public synchronized void setContinuar(boolean continuar) {
		this.continuar = continuar;
	}
	
	public void addActionListener(ActionListener a) {
		this.ouvinte = a;
	}
	
	@Override
	public void run() {
		
		try {
			DatagramSocket broadcastReceiver = new DatagramSocket(BROADCAST_PORT);
			
			DatagramPacket datagramPacket = getBroadcastDataGram();
			
			while(this.continuar) {
				broadcastReceiver.receive(datagramPacket);  //(1)
				
				String msg = new String(datagramPacket.getData());
				msg = getFilteredMessage(msg);
				if (!filteredMessage.equals(msg)) {
					filteredMessage = msg;
					msg = datagramPacket.getAddress().getHostAddress() + ":" + msg;
					ActionEvent event = new ActionEvent(msg, 0, "broadcast_recebido");
					this.ouvinte.actionPerformed(event );
					
					msg = "Broadcast recebido : >" + msg + "<";
					System.out.println(msg);
					lblProcurandoServidor.setText(msg);
				}
			}
			
			System.out.println("Interrompendo leitura do broadcast");
			lblProcurandoServidor.setText("Interrompendo leitura do broadcast");
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	private String getFilteredMessage(String msg) {
		if (msg != null) {
			String[] msgs = msg.split(":");
			msg = msgs[0] + ":" + msgs[1] + ":" + msgs[2] + ":";
		}
		return msg;
	}

	private DatagramPacket getBroadcastDataGram() {
		byte[] bufRec = new byte[1000];
		DatagramPacket datagramPacket = new DatagramPacket(bufRec, bufRec.length);
		return datagramPacket;
	}
	
	public synchronized void interromper() {
		this.continuar = false;
	}

	private void doStuff() {
		InetAddress group;
		try {
			group = InetAddress.getByName(this.ip);
			MulticastSocket s = new MulticastSocket(this.porta);
			s.joinGroup(group);
			this.lblProcurandoServidor.setText("Juntou-se ao grupo");

			DatagramPacket recv = getBroadcastDataGram();
			s.receive(recv);

			this.lblProcurandoServidor.setText("Servidor encontrado");
			System.out.println("Servidor encontrado");
			
			String msg = new String(recv.getData());
			String[] partes = msg.split(":");
			if (partes[0].equals("Hello")) {
				String ipServer = partes[1];
				this.lblProcurandoServidor.setText("Servidor encontrado : " + ipServer);
				int porta = Integer.parseInt(partes[2]);
				
				
				Socket socket = new Socket(ipServer, porta);
				BufferedReader in =
			        new BufferedReader(
			            new InputStreamReader(socket.getInputStream()));
				
				String line = null;
				while (this.continuar) {
					while ((line = in.readLine()) != null) {
						this.lblProcurandoServidor.setText("Recebendo do server:" + line);
					}
					Thread.sleep(1000);
				}
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
