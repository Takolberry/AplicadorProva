package br.npc.prova.gerador.tela.rede;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ProcuradorDeMaquinas implements Runnable {

	//Porta para broadcast
	private int broadcastPort;
	//Porta que sera usada para conexao ponto-a-ponto
	private int portaPontoPonto;
	
	private boolean continuar = true;
	
	public ProcuradorDeMaquinas(int broadcastPort, int portaPontoPonto) {
		this.broadcastPort = broadcastPort;
		this.portaPontoPonto = portaPontoPonto;
	}

	public synchronized void setContinuar(boolean continuar) {
		this.continuar = continuar;
	}

	@Override
	public void run() {
		try {
			DatagramSocket brodacast = new DatagramSocket();
			//Mensagem para clientes com a porta para conexao ponto-a-ponto
			String broadcastMsg = "Hello:" + this.portaPontoPonto + ":";
			//Endereco de broadcast
			InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");
			//Monta datagram para broadcast
			DatagramPacket broadcastDataGram = 
				getBroadCastMessage(
						this.broadcastPort,
						broadcastMsg, 
						broadcastAddress);
			while(this.continuar) {
				try {
					//Envia mensagem de broadcast
					brodacast.send(broadcastDataGram);
					
					Thread.sleep(5000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private DatagramPacket getBroadCastMessage(
			int broadcastPort, 
			String msg,
			InetAddress broadcastAddress) {
		
		byte[] bufSend = msg.getBytes();
		DatagramPacket broadcastDataGram = 
			new DatagramPacket(bufSend,
				bufSend.length, 
				broadcastAddress, 
				broadcastPort);
		
		return broadcastDataGram;
	}

}
