package br.npc.prova.aplicador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ProgressMonitorInputStream;
import javax.swing.border.EmptyBorder;

import br.npc.nucleo.prova.Resultado;
import br.npc.prova.aplicador.util.FiltroExtensao;

public class TelaResultado extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton btnEncerrar;
	private JTextArea txtRelatorioResultado;
	
	private Resultado resultado;
	private JScrollPane scrollPane;
	
	private File diretorioResultado;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new TelaResultado(null, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaResultado(Resultado resultado, File diretorioResultado) {
		System.out.println(diretorioResultado.getAbsoluteFile());
		this.resultado = resultado;
		this.diretorioResultado = diretorioResultado;
		addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(WindowEvent event) {
			}
		});
		addWindowListener(new WindowAdapter() {
			private int vezes = 1;
			@Override
			public void windowClosed(WindowEvent event) {
			
			}
			@Override
			public void windowClosing(WindowEvent event) {
				System.out.println("Tentou fechar : " + vezes++);
				setState(event.getOldState());
				JOptionPane.showMessageDialog(null, "fechando");
			}
		});
		try {
			setResizable(false);
//			this.setUndecorated(true);

			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(new BorderLayout(0, 0));
			
			JPanel panel = new JPanel();
			panel.setBackground(Color.WHITE);
			contentPane.add(panel, BorderLayout.SOUTH);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{143, 71, 71, 0, 0, 0};
			gbl_panel.rowHeights = new int[]{23, 0};
			gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			
			btnEncerrar = new JButton("Encerrar");
			GridBagConstraints gbc_btnEncerrar = new GridBagConstraints();
			gbc_btnEncerrar.gridx = 4;
			gbc_btnEncerrar.gridy = 0;
			panel.add(btnEncerrar, gbc_btnEncerrar);
			btnEncerrar.addActionListener(this);
			
			
			JPanel panel_1 = new JPanel();
			contentPane.add(panel_1, BorderLayout.CENTER);
			panel_1.setLayout(new BorderLayout(0, 0));
			
			scrollPane = new JScrollPane();
			panel_1.add(scrollPane, BorderLayout.CENTER);
			
			txtRelatorioResultado = new JTextArea();
			txtRelatorioResultado.setFont(new Font("Courier New", Font.PLAIN, 13));
			scrollPane.setViewportView(txtRelatorioResultado);
			txtRelatorioResultado.setAutoscrolls(true);
			String relatorio = this.resultado.getRelatorio();
			txtRelatorioResultado.setText(relatorio);
			
			salvar(new Date(), relatorio);
			
			setVisible(true);
			setExtendedState(JFrame.MAXIMIZED_BOTH);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	private void salvar(Date date, String relatorio) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		File resFile = new File(this.diretorioResultado, "Resultado_" + sdf.format(date) + ".txt");
		resFile.createNewFile();
		PrintWriter writer = new PrintWriter(resFile);
		writer.write(relatorio);

		writer.close();
	}

	private boolean escolherResultado() throws FileNotFoundException, IOException, ClassNotFoundException {
		File diretorioOrigem = new File("C:\\ProvasGerador");
		JFileChooser telaSalvar = new JFileChooser(diretorioOrigem);
		telaSalvar.setFileFilter(new FiltroExtensao("prv"));
		telaSalvar.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int ret = telaSalvar.showOpenDialog(this);
		
		if (ret == JFileChooser.APPROVE_OPTION) {
			File arquivo = telaSalvar.getSelectedFile();

			ObjectInputStream objectIn = 
					new ObjectInputStream(
							new BufferedInputStream(
									new ProgressMonitorInputStream(
											this,
											"Lendo " + arquivo.getName() ,
											new FileInputStream(arquivo))));
			
			this.resultado = (Resultado)objectIn.readObject();
			
			objectIn.close();
			
			
			return true;
		}
		return false;
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
	}
}
