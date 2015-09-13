package br.npc.prova.aplicador;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class TelaInicial extends JFrame implements ActionListener {

	private JList<String> provaList;
	private JButton btnExecutarProva;
	private File diretorioBase;
	private JLabel lblProcurandoServidor;
	private ConectorServer conector;
	private JLabel label;
	private JComboBox<InetAddress> cboInterfaces;
	private JToggleButton btnProcurarServer;
	
	private String ultimoBroadcast;
	private ServerReader serverReader;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		for (String param : args) {
			System.out.println(param);
		}
		final String path = args[0];
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new TelaInicial(path);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaInicial(String path) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				conector.setContinuar(false);
				lblProcurandoServidor.setText("Encerrando...");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		System.out.println("TelaInicial()");
		montaTela();
		try {
			listarProvas(path);
			setVisible(true);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			this.dispose();
		}
		
	}

	private void ouvirServidor() {
		conector = new ConectorServer("239.0.0.1", 6789, this.lblProcurandoServidor);
		conector.addActionListener(this);
		new Thread(conector).start();
	}

	private void listarProvas(String path) {
		System.out.println(path);
		File dir = new File(path);
		this.diretorioBase = dir;
		dir = new File(dir, "provas");
		if (!dir.isDirectory()) {
			throw new IllegalArgumentException("O caminho '" + dir.getAbsolutePath() + "' não é um diretório");
		}
		
		File[] arquivos = dir.listFiles();
		DefaultListModel<String> provaListModel = new DefaultListModel<String>();
		for (File file : arquivos) {
			if (file.isFile()) {
				provaListModel.addElement(file.getName());
			}
		}
		this.provaList.setModel(provaListModel);
		
		if (provaListModel.isEmpty()) {
			this.btnExecutarProva.setEnabled(false);
		} else {
			this.btnExecutarProva.setEnabled(true);
		}
	}

	private void montaTela() {
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{615, 0};
		gridBagLayout.rowHeights = new int[]{55, 510, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.NORTH;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		getContentPane().add(panel_1, gbc_panel_1);
		
		lblProcurandoServidor = new JLabel("Procurando servidor...");
		panel_1.add(lblProcurandoServidor);
		
		label = new JLabel("Interfaces");
		panel_1.add(label);
		
		cboInterfaces = new JComboBox<InetAddress>();
		cboInterfaces.setPreferredSize(new Dimension(400, 20));
		cboInterfaces.addActionListener(this);
		try {
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
			while ( e.hasMoreElements()) {
				NetworkInterface inter = e.nextElement();
				Enumeration<InetAddress> inets = inter.getInetAddresses();
				while ( inets.hasMoreElements()) {
					InetAddress inet = inets.nextElement();
					cboInterfaces.addItem(inet);
				}
				
			}
		} catch (SocketException e) {
			throw new IllegalStateException("Erro ao procurar interfaces de rede", e);
		}
		panel_1.add(cboInterfaces);
		
		btnProcurarServer = new JToggleButton("Procurar M\u00E1quinas");
		btnProcurarServer.addActionListener(this);
		panel_1.add(btnProcurarServer);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		getContentPane().add(panel, gbc_panel);
		
		
		DefaultListModel<String> provaListModel = new DefaultListModel<String>();
		
		provaList = new JList<String>(provaListModel);
		provaList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		provaList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		provaList.setPreferredSize(new Dimension(400, 500));
		
		panel.add(provaList);
		
		btnExecutarProva = new JButton("Executar");
		btnExecutarProva.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnExecutarProva.setHorizontalTextPosition(SwingConstants.CENTER);
		btnExecutarProva.setIcon(new ImageIcon(TelaInicial.class.getResource("/br/npc/prova/aplicador/images/tests-icon.png")));
		btnExecutarProva.setPreferredSize(new Dimension(200, 200));
		btnExecutarProva.addActionListener(this);
		panel.add(btnExecutarProva);
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == this.btnExecutarProva) {
			File dir = new File(this.diretorioBase, "provas");
			File fileProva = new File(dir, this.provaList.getSelectedValue());
			new TelaAplicador(fileProva, this.serverReader);
		} else if (event.getSource() == this.btnProcurarServer) {
			if (this.btnProcurarServer.isSelected()) {
				ouvirServidor();
			}
			
		} else if (event.getActionCommand().equals("broadcast_recebido")) {
			
			String msg = (String)event.getSource();
			if (!msg.equals(this.ultimoBroadcast)) {
				String[] partesMsg = msg.split(":");
				String ip = partesMsg[0];
				int porta = Integer.parseInt(partesMsg[2]);
				
				try {
					Socket socket = new Socket(ip, porta);
					
					serverReader = new ServerReader(lblProcurandoServidor, socket);
					new Thread(serverReader).start();
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
	}

}
