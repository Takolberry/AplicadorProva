package br.npc.prova.gerador.tela.rede;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import br.npc.prova.gerador.tela.principal.TelaPrincipal;

public class TelaRede extends JInternalFrame implements ActionListener, MouseListener, MouseMotionListener {

	private TelaPrincipal telaPrincipal;
	private int linhas;
	private int colunas;
	private JPanel panelLab;
	private JSpinner spnLinhas;
	private JSpinner spnColunas;
	private JButton btnAtualizarGrade;
	private JScrollPane scrollPane;
	
	private static final String PATH_MISSING = "/br/npc/prova/gerador/images/System-Lock-Screen-48.png";
	
	private static final int BROADCAST_PORT = 8888;
	private static final int SOCKET_PORT = 8765;
	
	private MouseEvent firstMouseEvent = null;
	private JPanel panel_1;
	private JPanel panel_2;
	private ProcuradorDeMaquinas procurador;
	private JToggleButton btnProcurarNaoProcurar;
	
	private Map<String, ClienteReader> maquinas = new HashMap<String, ClienteReader>();
	public static Map<String, JPanel> labPanels = new HashMap<String, JPanel>();
	private ConectorAplicador conector;
	private JComboBox<InetAddress> cboInterfaces;
	private JLabel lblInterfaces;
	
	public static void main(String[] args) {
		new TelaRede(null, 5, 5);
	}
	

	
	public TelaRede(TelaPrincipal telaPrincipal, int linhas, int colunas) {
		this.telaPrincipal = telaPrincipal;
		if (telaPrincipal != null) {
			telaPrincipal.getDesktopPane().add(this);
		}
		
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosing(InternalFrameEvent event) {
				
				for(ClienteReader clienteReader : maquinas.values()) {
					try {
						System.out.println("Fechando " + clienteReader.getSocketCliente().getInetAddress().getHostAddress());
						clienteReader.interromper();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if (conector != null) {
					conector.interromper();
				}
			}
		});
		
		setBounds(100, 100, 934, 610);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{216, 0};
		gridBagLayout.rowHeights = new int[]{0, 47, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		{
			JPanel panel = new JPanel();
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.insets = new Insets(0, 0, 5, 0);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 0;
			getContentPane().add(panel, gbc_panel);
			panel.setLayout(new BorderLayout(0, 0));
			{
				panel_1 = new JPanel();
				panel.add(panel_1, BorderLayout.WEST);
				{
					JLabel lblLinhas = new JLabel("Linhas : ");
					panel_1.add(lblLinhas);
				}
				{
					spnLinhas = new JSpinner();
					panel_1.add(spnLinhas);
					spnLinhas.setPreferredSize(new Dimension(40, 20));
					spnLinhas.setModel(new SpinnerNumberModel(new Integer(1), null, null, new Integer(1)));
					spnLinhas.setValue(linhas);
				}
				{
					JLabel lblNewLabel = new JLabel("Colunas");
					panel_1.add(lblNewLabel);
					lblNewLabel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
				}
				{
					spnColunas = new JSpinner();
					panel_1.add(spnColunas);
					spnColunas.setPreferredSize(new Dimension(40, 20));
					spnColunas.setModel(new SpinnerNumberModel(new Integer(1), null, null, new Integer(1)));
					spnColunas.setValue(colunas);
				}
				{
					btnAtualizarGrade = new JButton("Atualizar Grade");
					panel_1.add(btnAtualizarGrade);
					btnAtualizarGrade.addActionListener(this);
				}
			}
			{
				panel_2 = new JPanel();
				panel.add(panel_2, BorderLayout.EAST);
				{
					btnProcurarNaoProcurar = new JToggleButton("Procurar M\u00E1quinas");
					btnProcurarNaoProcurar.addActionListener(this);
					{
						lblInterfaces = new JLabel("Interfaces");
						panel_2.add(lblInterfaces);
					}
					{
						cboInterfaces = new JComboBox<InetAddress>();
						cboInterfaces.setPreferredSize(new Dimension(400, 20));
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
						panel_2.add(cboInterfaces);
					}
					panel_2.add(btnProcurarNaoProcurar);
				}
			}
		}
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 1;
		getContentPane().add(scrollPane, gbc_scrollPane_1);
		
		panelLab = new JPanel();
		panelLab.setBounds(new Rectangle(0, 0, 100, 100));
		scrollPane.setViewportView(panelLab);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
//		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
//		gbl_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelLab.setLayout(gbl_panel);
		
		montarGrid();
		
		iniciarConector();
		try {
			setMaximum(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		setClosable(true);
		setVisible(true);
	}

	private void montarGrid() {
		this.linhas = (Integer)this.spnLinhas.getValue();
		this.colunas = (Integer)this.spnColunas.getValue();
		JPanel panel = this.panelLab;
		panel.removeAll();
		for(int col = 0 ; col < this.colunas; col++) {
			
			for(int lin = 0 ; lin < this.linhas; lin++) {
				Color color = Color.darkGray;
				String ip = "XXX.XXX.XXX.XXX";
				String path = "/br/npc/prova/gerador/images/Network-48-2.png";
				addHost(panel, col, lin, color, ip, path, false);
			}
			
			
		}
		Color color = Color.GREEN;
		String ip = "127.0.0.0";
		String path = "/br/npc/prova/gerador/images/Monitor_Matrix.png";
		addHost(panel, -this.colunas, this.linhas, color, ip, path, true);
		
		scrollPane.updateUI();
	}

	private void addHost(JPanel panel, int col, int lin, Color color, String ip, String path, boolean admin) {
		int grossura = 1;
		if (col < 0) {
			grossura = 4;
		}
		JPanel panelHost = getPanelHost(col + ":" + lin, color, grossura, ip, path, admin);

		GridBagConstraints gbc_panel = getGridBagConstants(col, lin);
		if (col < 0) {
			gbc_panel.gridx = 0;
			gbc_panel.gridwidth = -col;
			gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		}
		panel.add(panelHost, gbc_panel);
		if (!admin) {
			labPanels.put(col + ":" + lin, panelHost);
			panelHost.addMouseListener(this);
			panelHost.addMouseMotionListener(this);
		}
	}

	private JPanel getPanelHost(String idPanel, Color color, int grossura, String ip, String path, boolean admin) {
		JPanel panelHost = new PanelHost(idPanel, ip, color, path, PATH_MISSING, admin);
		return panelHost;
	}

	private GridBagConstraints getGridBagConstants(int col, int lin) {
		GridBagConstraints gbc_panel = new GridBagConstraints();
		//gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel.fill = GridBagConstraints.NONE;
		gbc_panel.gridx = col;
		gbc_panel.gridy = lin;
		return gbc_panel;
	}


	@Override
	public void actionPerformed(ActionEvent event) {
		try {
			if (event.getSource() == this.btnAtualizarGrade) {
				this.colunas = (Integer)this.spnColunas.getValue();
				this.linhas = (Integer)this.spnLinhas.getValue();
				montarGrid();
			} else if (event.getSource() == this.btnProcurarNaoProcurar) {
				if (this.btnProcurarNaoProcurar.isSelected()) {
					avisarMaquinas();
				} else {
					interromperProcura();
				}
			} else if (event.getSource() instanceof ClienteReader) {
				addNovaMaquina(event);
			}
			scrollPane.updateUI();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}

	private void addNovaMaquina(ActionEvent event) {
		ClienteReader source = (ClienteReader)event.getSource();
		String ip = source.getSocketCliente().getInetAddress().getHostAddress();
		this.maquinas.put(ip, source);
		for(int col = 0 ; col < this.colunas; col++) {
			for(int lin = 0 ; lin < this.linhas; lin++) {
				String key = col + ":" + lin;
				PanelHost panelHost = (PanelHost)this.labPanels.get(key);
				if (panelHost.getIp().equals("XXX.XXX.XXX.XXX")) {
					panelHost.setIp(ip);
					return;
				}
			}
		}
	}

	private void interromperProcura() {
		this.procurador.setContinuar(false);
		this.telaPrincipal.lblInfo.setText("Procura de maquinas interrompida");
	}

	private void avisarMaquinas() throws Exception {
		try {
			//Porta para broadcast
			int broadcastPort = BROADCAST_PORT;
			//Porta que sera usada para conexao ponto-a-ponto
			int portaPontoPonto = SOCKET_PORT;
			
			//Fica mandando mensagens de broadcast de tempos em tempos
			this.procurador = 
				new ProcuradorDeMaquinas(broadcastPort, portaPontoPonto);
			
			new Thread(this.procurador).start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void iniciarConector() {
		if (this.conector == null) {
			this.conector = 
				new ConectorAplicador(
					SOCKET_PORT, 
					this.telaPrincipal.lblInfo);
			this.conector.addActionListener(this);
			new Thread(this.conector).start();
		}
	}
	
	private void interromperConector() {
		if (this.conector == null) {
			this.conector.interromper();
			this.conector = null;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		scrollPane.updateUI();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		scrollPane.updateUI();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		scrollPane.updateUI();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {

		
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}
}
