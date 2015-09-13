package br.npc.prova.aplicador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ProgressMonitorInputStream;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.npc.nucleo.prova.Alternativa;
import br.npc.nucleo.prova.Aplicacao;
import br.npc.nucleo.prova.Prova;
import br.npc.nucleo.prova.Questao;
import br.npc.nucleo.prova.Resultado;
import br.npc.nucleo.prova.Teste;
import br.npc.nucleo.prova.TipoAplicacao;
import br.npc.prova.aplicador.util.FiltroExtensao;
import javax.swing.JProgressBar;

public class TelaAplicador extends JFrame implements ActionListener, ChangeListener {

	private Prova prova;
	private int indexOfTeste;
	private JPanel contentPane;
	private JPanel provaPanel;
	private JTextArea txtAreaQuestao;
	private JButton btnProx;
	private JPanel questionPanel;
	private JScrollPane scrollAltPanel;
	private JPanel dadosProvaPanel;
	private JLabel lblNewLabel;
	private JLabel lblDescProva;
	private JButton btnAnterior;
	private JButton btnEncerrar;
	private JLabel lblTempoRestante;
	private Temporizador temp;
	private Resultado resultado;
	private File diretorioBase;
	private File diretorioResultado;
	private JProgressBar progressBar;
	private ServerReader serverReader;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new TelaAplicador(null, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaAplicador(File fileProva, ServerReader serverReader) {
		this.serverReader = serverReader;
		
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
			gbl_panel.columnWidths = new int[]{71, 71, 71, 0, 0, 0};
			gbl_panel.rowHeights = new int[]{23, 0};
			gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			
			btnAnterior = new JButton("Anterior");
			GridBagConstraints gbc_btnAnterior = new GridBagConstraints();
			gbc_btnAnterior.fill = GridBagConstraints.BOTH;
			gbc_btnAnterior.insets = new Insets(0, 0, 0, 5);
			gbc_btnAnterior.gridx = 0;
			gbc_btnAnterior.gridy = 0;
			panel.add(btnAnterior, gbc_btnAnterior);
			btnAnterior.addActionListener(this);
			
			btnProx = new JButton("Proximo");
			GridBagConstraints gbc_btnProx = new GridBagConstraints();
			gbc_btnProx.fill = GridBagConstraints.BOTH;
			gbc_btnProx.insets = new Insets(0, 0, 0, 5);
			gbc_btnProx.gridx = 2;
			gbc_btnProx.gridy = 0;
			panel.add(btnProx, gbc_btnProx);
			btnProx.addActionListener(this);
			
			progressBar = new JProgressBar();
			progressBar.setStringPainted(true);
			progressBar.setFont(new Font("Courier New", Font.BOLD, 22));
			progressBar.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					JProgressBar jProgressBar = (JProgressBar)e.getSource();
					jProgressBar.setString(jProgressBar.getValue() + " / " + jProgressBar.getMaximum());
				}
			});
			GridBagConstraints gbc_progressBar = new GridBagConstraints();
			gbc_progressBar.fill = GridBagConstraints.BOTH;
			gbc_progressBar.insets = new Insets(0, 0, 0, 5);
			gbc_progressBar.gridx = 3;
			gbc_progressBar.gridy = 0;
			panel.add(progressBar, gbc_progressBar);
			
			btnEncerrar = new JButton("Encerrar");
			GridBagConstraints gbc_btnEncerrar = new GridBagConstraints();
			gbc_btnEncerrar.fill = GridBagConstraints.BOTH;
			gbc_btnEncerrar.gridx = 4;
			gbc_btnEncerrar.gridy = 0;
			panel.add(btnEncerrar, gbc_btnEncerrar);
			btnEncerrar.addActionListener(this);
			
			
			JPanel panel_1 = new JPanel();
			contentPane.add(panel_1, BorderLayout.CENTER);
			panel_1.setLayout(new BorderLayout(0, 0));
			
			JPanel commandPanel = new JPanel();
			commandPanel.setBackground(Color.WHITE);
			commandPanel.setLayout(new FlowLayout());
			panel_1.add(commandPanel, BorderLayout.SOUTH);
			
			provaPanel = new JPanel();
			provaPanel.setFont(new Font("Courier New", Font.PLAIN, 11));
			provaPanel.setBackground(Color.WHITE);
			panel_1.add(provaPanel, BorderLayout.CENTER);
			GridBagLayout gbl_provaPanel = new GridBagLayout();
			gbl_provaPanel.columnWidths = new int[]{0, 128, 0};
			gbl_provaPanel.rowHeights = new int[]{0, 100, 0, 0};
			gbl_provaPanel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
			gbl_provaPanel.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
			provaPanel.setLayout(gbl_provaPanel);
			
			dadosProvaPanel = new JPanel();
			dadosProvaPanel.setBackground(Color.WHITE);
			FlowLayout flowLayout = (FlowLayout) dadosProvaPanel.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			GridBagConstraints gbc_dadosProvaPanel = new GridBagConstraints();
			gbc_dadosProvaPanel.insets = new Insets(0, 0, 5, 5);
			gbc_dadosProvaPanel.fill = GridBagConstraints.HORIZONTAL;
			gbc_dadosProvaPanel.gridx = 0;
			gbc_dadosProvaPanel.gridy = 0;
			provaPanel.add(dadosProvaPanel, gbc_dadosProvaPanel);
			
			lblNewLabel = new JLabel("Prova:");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
			dadosProvaPanel.add(lblNewLabel);
			
			lblDescProva = new JLabel("PAT");
			lblDescProva.setFont(new Font("Tahoma", Font.PLAIN, 18));
			dadosProvaPanel.add(lblDescProva);
			
			lblTempoRestante = new JLabel("00 min 00 s");
			GridBagConstraints gbc_lblTempoRestante = new GridBagConstraints();
			gbc_lblTempoRestante.insets = new Insets(0, 0, 5, 0);
			gbc_lblTempoRestante.gridx = 1;
			gbc_lblTempoRestante.gridy = 0;
			provaPanel.add(lblTempoRestante, gbc_lblTempoRestante);
			lblTempoRestante.setFont(new Font("Tahoma", Font.PLAIN, 18));
			
			questionPanel = new JPanel();
			questionPanel.setFont(new Font("Cordia New", Font.PLAIN, 11));
			GridBagConstraints gbc_questionPanel = new GridBagConstraints();
			gbc_questionPanel.gridwidth = 2;
			gbc_questionPanel.insets = new Insets(0, 0, 5, 5);
			gbc_questionPanel.fill = GridBagConstraints.HORIZONTAL;
			gbc_questionPanel.gridx = 0;
			gbc_questionPanel.gridy = 1;
			provaPanel.add(questionPanel, gbc_questionPanel);
			questionPanel.setLayout(new BorderLayout(0, 0));
			
			scrollAltPanel = new JScrollPane();
			scrollAltPanel.setFont(new Font("Courier New", Font.PLAIN, 11));
			scrollAltPanel.setBackground(Color.WHITE);
			GridBagConstraints gbc_scrollAltPanel = new GridBagConstraints();
			gbc_scrollAltPanel.gridwidth = 2;
			gbc_scrollAltPanel.insets = new Insets(0, 0, 0, 5);
			gbc_scrollAltPanel.fill = GridBagConstraints.BOTH;
			gbc_scrollAltPanel.gridx = 0;
			gbc_scrollAltPanel.gridy = 2;
			provaPanel.add(scrollAltPanel, gbc_scrollAltPanel);
			
			txtAreaQuestao = new JTextArea("Quest");
			txtAreaQuestao.setBackground(Color.WHITE);
			txtAreaQuestao.setFont(new Font("Tahoma", Font.PLAIN, 18));
			txtAreaQuestao.setEditable(false);
			
			this.indexOfTeste = 0;

			if (fileProva != null) {
				
				abrirProva(fileProva);
				
			} else {
				File diretorioOrigem = new File("C:\\ProvasGerador");
				JFileChooser telaSalvar = new JFileChooser(diretorioOrigem);
				telaSalvar.setFileFilter(new FiltroExtensao("prv"));
				telaSalvar.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int ret = telaSalvar.showOpenDialog(this);
				
				if (ret == JFileChooser.APPROVE_OPTION) {
					File arquivo = telaSalvar.getSelectedFile();
					
					abrirProva(arquivo);
				} else {
					System.exit(0);
				}
			}
			iniciarProva();		
	
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void iniciarProva() {
		this.temp = new Temporizador(this.prova.getDuracao().getMinutos()*60, lblTempoRestante, this);
		new Thread(temp).start();
		setTeste();
		verificaAnterior();
		verificaProximo();
		this.btnAnterior.setVisible(false);
	}

	class AlternativaListener implements ChangeListener {
		private Teste teste;
		private Alternativa alt;

		public AlternativaListener(Teste teste, Alternativa alt) {
			this.teste = teste;
			this.alt = alt;
		}

		@Override
		public void stateChanged(ChangeEvent event) {
			if (event.getSource() instanceof JRadioButton) {
				JRadioButton radio = (JRadioButton)event.getSource();
				if (radio.isSelected()) {
					this.teste.getOpcoes().add(this.alt);
				} else {
					this.teste.getOpcoes().remove(this.alt);
				}
			} else if (event.getSource() instanceof JCheckBox) {
				JCheckBox check = (JCheckBox)event.getSource();
				if (check.isSelected()) {
					this.teste.getOpcoes().add(this.alt);
				} else {
					this.teste.getOpcoes().remove(this.alt);
				}
			}
			
						
		}
	}
	
	private void setTeste() {
		Teste teste = (Teste)this.prova.getQuestoes().get(this.indexOfTeste);
		
		
		questionPanel.removeAll();
		questionPanel.setBackground(Color.WHITE);
		String indicacaoNumeroCorretas = "";
		if (teste.getAlternativasCorretas().size() == 1) {
			indicacaoNumeroCorretas = "\n[MARQUE APENAS UMA]";
		} else /*if (teste.getAlternativas().size() - teste.getAlternativasCorretas().size() <= 3)*/ {
			indicacaoNumeroCorretas = String.format("\n[MARQUE %d]", teste.getAlternativasCorretas().size());
		}/* else {
			indicacaoNumeroCorretas = "\n[MARQUE APENAS AS QUE CONSIDERAR CORRETAS]";
		}*/
		txtAreaQuestao.setText(teste.getId() + " - " + teste.getTexto() + indicacaoNumeroCorretas);
		txtAreaQuestao.setBackground(Color.WHITE);
		txtAreaQuestao.setFont(new Font("Courier New", Font.BOLD, 18));
		
		questionPanel.add(txtAreaQuestao, BorderLayout.CENTER);
		
		JPanel altPanel = new JPanel();
		altPanel.setBackground(Color.WHITE);
		scrollAltPanel.setViewportView(altPanel);
		altPanel.setLayout(new BoxLayout(altPanel, BoxLayout.Y_AXIS));
		altPanel.setFont(new Font("Courier New", Font.BOLD, 16));
		ButtonGroup buttonGroup = new ButtonGroup();
		for (Alternativa alt : teste.getAlternativas().values()) {
			if (teste.getAlternativasCorretas().size() == 1) {
				JRadioButton radio = new JRadioButton("<html>" + alt.getValor().replaceAll("\n","<br/>") + "<br/><br/></html>");
				radio.setVerticalTextPosition(SwingConstants.TOP);
				radio.setFont(new Font("Courier New", Font.BOLD, 16));
				radio.addChangeListener(new AlternativaListener(teste, alt));
				radio.addChangeListener(this);
				radio.setBackground(Color.WHITE);
				if (teste.getOpcoes().contains(alt)) {
					radio.setSelected(true);
				}
				altPanel.add(radio);	
				buttonGroup.add(radio);
			} else if (teste.getAlternativasCorretas().size() > 1) {
				JCheckBox check = new JCheckBox("<html>" + alt.getValor().replaceAll("\n","<br/>") + "<br/><br/></html>");
				check.setVerticalTextPosition(SwingConstants.TOP);
				check.setFont(new Font("Courier New", Font.BOLD, 16));
				check.addChangeListener(new AlternativaListener(teste, alt));
				check.addChangeListener(this);
				check.setBackground(Color.WHITE);
				if (teste.getOpcoes().contains(alt)) {
					check.setSelected(true);
				}
				altPanel.add(check);	
			}
			
		}
		repaint();
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	private boolean escolherProva() throws FileNotFoundException, IOException, ClassNotFoundException {
		File diretorioOrigem = new File("C:\\ProvasGerador");
		JFileChooser telaSalvar = new JFileChooser(diretorioOrigem);
		telaSalvar.setFileFilter(new FiltroExtensao("prv"));
		telaSalvar.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int ret = telaSalvar.showOpenDialog(this);
		
		if (ret == JFileChooser.APPROVE_OPTION) {
			File arquivo = telaSalvar.getSelectedFile();
			
			abrirProva(arquivo);
			
			return true;
		}
		return false;
		
	}

	private void abrirProva(File arquivo) throws IOException,
			FileNotFoundException, ClassNotFoundException {
		ObjectInputStream objectIn = 
				new ObjectInputStream(
						new BufferedInputStream(
								new ProgressMonitorInputStream(
										this,
										"Lendo " + arquivo.getName() ,
										new FileInputStream(arquivo))));
		
		this.prova = (Prova)objectIn.readObject();
		
		this.prova = getAplicacaoProva(this.prova);
		
		objectIn.close();
		
		this.diretorioBase =  arquivo.getParentFile();
		File dirResultados = new File(this.diretorioBase, "resultados");
		if (!dirResultados.exists()) {
			if (dirResultados.mkdir()) {
				this.diretorioResultado = dirResultados;
			}
		} else {
			this.diretorioResultado = dirResultados;
		}
		
		this.lblDescProva.setText(this.prova.getDesc());
		this.progressBar.setMaximum(this.prova.getQuestoes().size());
	}

	private Prova getAplicacaoProva(Prova prova) {
		Aplicacao aplicacao = prova.getAplicacao();
		if (aplicacao.getTipo() == TipoAplicacao.RANDOMICA) {
			Prova provaAux = new Prova();
			copiarDePara(prova, provaAux);
			provaAux.setAplicacao(Aplicacao.getAplicacaoNormal());
			List<Questao> questoes = prova.getQuestoes();
			Collections.shuffle(questoes);
			
			for (int contQuest = 0; contQuest < aplicacao.getNumMaxQuestoes(); contQuest++) {
				Questao q = questoes.get(contQuest);
				if (q instanceof Teste) {
					Teste t = (Teste)q;
					
					Set<Alternativa> altsIncorretas = 
						new HashSet<Alternativa>(
								getAlternativas(
										t.getAlternativas()));
					
					Set<Alternativa> altsCorretas = 
						new HashSet<Alternativa>(
								t.getAlternativasCorretas());
					
					altsIncorretas.removeAll(altsCorretas);
					
					List<Alternativa> alternativas = mesclar(altsIncorretas, altsCorretas, aplicacao.getNumMaxAlterntativas());
					
					altsCorretas = new HashSet<Alternativa>();
					
					Map<String, Alternativa> altMap = new TreeMap<String, Alternativa>();
					
					for (Alternativa alternativa : alternativas) {
						if (alternativa.isCorreta()) {
							altsCorretas.add(alternativa);
						}
						altMap.put(alternativa.getId(), alternativa);
					}
					
					Teste novoTeste = new Teste("" + (contQuest + 1), q.getTexto(), q.getValor(), altMap, altsCorretas);
					provaAux.getQuestoes().add(novoTeste);
				} else {
					provaAux.getQuestoes().add(q);
				}
			}
			
			prova = provaAux;
			
		}
		return prova;
	}

	private Set<Alternativa> getAlternativas(Map<String, Alternativa> alternativas) {
		Set<Alternativa> alts = new HashSet<Alternativa>();
		for (Map.Entry<String, Alternativa> entry : alternativas.entrySet()) {
			alts.add(entry.getValue());
		}
		return alts;
	}
	
	private List<Alternativa> mesclar(Set<Alternativa> altsIncorretas,
			Set<Alternativa> altsCorretas, int numMaxAlterntativas) {
		List<Alternativa> altList = new ArrayList<Alternativa>();
		if (altsIncorretas.size() + altsCorretas.size() <= numMaxAlterntativas) {
			//O numero de erradas e corretas nao eh maior
			// que o numero exigido, entao nao tem o fazer de 
			// randomico a nao ser add todas as alternativas e 
			// misturar(metodo shuffle)
			altList.addAll(altsIncorretas);
			altList.addAll(altsCorretas);
			Collections.shuffle(altList);
		} else {
			
			
			List<Alternativa> altListCorretas = new ArrayList<Alternativa>(altsCorretas);
			//Mistura as alternativas corretas
			Collections.shuffle(altListCorretas);
			
			//Adiciona ao menos uma correta as alternativas removendo do pool
			// de corretas
			altList.add(altListCorretas.remove(0));
			
			//Adiciona os dois conjuntos de alternativas, corretas e incorretas, 
			// na lista altListAux
			List<Alternativa> altListAux = new ArrayList<Alternativa>(altsIncorretas);
			altListAux.addAll(altListCorretas);
			
			//Embaralha o cojunto da 'restantes'
			Collections.shuffle(altListAux);
			
			//Vai movendo alternativas do conjunto das 'restantes' para
			// o conjunto onde ja tinha uma correta ate 
			// chegar em numMaxAlterntativas
			for ( int cont = 1; cont < numMaxAlterntativas; cont++) {
				altList.add(altListAux.remove(0));
			}
			
			//Mais uma embaralhada
			Collections.shuffle(altList);
		}
		
		//Ajustando id's para ficar em ordem
		List<Alternativa> altListRet = new ArrayList<Alternativa>();
		int id = 1;
		for (Alternativa alternativa : altList) {
			alternativa.setId("" + (id++));
			altListRet.add(alternativa);
		}
		
		return altListRet;
	}

	private void copiarDePara(Prova prova, Prova provaAux) {
		provaAux.setDesc(prova.getDesc());
		provaAux.setTurma(prova.getTurma());
		provaAux.setDisciplina(prova.getDisciplina());
		provaAux.setProfessor(prova.getProfessor());
		provaAux.setTipo(prova.getTipo());
		provaAux.setEscola(prova.getEscola());
		provaAux.setDuracao(prova.getDuracao());
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == this.btnProx) {
			vaiPraProximaQuestao();
		} else if (event.getSource() == this.btnAnterior) {
			vaiPraQuestaoAnterior();
		} else if (event.getSource() == this.btnEncerrar) {
			encerrar(true);
//			avaliar();
		} else if (event.getSource() == this.temp) {
//			avaliar();
			encerrar(false);
		}
		
	}

	private void encerrar(boolean perguntar) {
		int respondidas = this.prova.getNumeroRespondidas();
		String msg = "";
		if (respondidas == 0) {
			msg = "Você não respodeu nenhuma questao.\n" ;
		} else 	if (respondidas == 1) {
			msg = "Você só respondeu 1 questao.\n";
		} else if (respondidas < this.prova.getQuestoes().size()) {
			msg = "Você só respondeu " + respondidas + " questoes de " + this.prova.getQuestoes().size() + ".\n";
		} else {
			msg = "Você já respondeu todas as questões.\n ";
		}
		msg = msg + "Deseja mesmo encerrar a prova agora?";
		if (perguntar) {
			int opc = JOptionPane.showConfirmDialog(this, msg, "Encerrando..." , JOptionPane.YES_NO_OPTION);
			if (opc == JOptionPane.YES_OPTION) {
				avaliar();
				reverProva();
				this.dispose();
			}
		} else {
			avaliar();
			reverProva();
			this.dispose();
		}

	}

	private void reverProva() {
		System.out.println("Vai chamar TelaResultado()");
//		sendResultado();
		new TelaResultado(this.resultado, this.diretorioResultado);
	}

	private void sendResultado() {
		try {
			OutputStream output = this.serverReader.getSocketCliente().getOutputStream();
			PrintWriter writer = new PrintWriter(output);
			writer.write(this.resultado.getRelatorio());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void avaliar() {
		String tempoRestante = this.lblTempoRestante.getText();
		Resultado resultado = this.prova.getResultado(tempoRestante);
		String msg = resultado.getResumo();
		JOptionPane.showMessageDialog(this, msg);
		
		this.resultado = resultado;
	}

	private void vaiPraQuestaoAnterior() {
		this.indexOfTeste--;
		verificaAnterior();
		setTeste();
	}

	private void verificaAnterior() {
		if (this.indexOfTeste == 0) {
			this.btnAnterior.setVisible(false);
		} else {
			this.btnAnterior.setVisible(true);
		}
		this.btnProx.setVisible(true);
	}

	private void vaiPraProximaQuestao() {
		this.indexOfTeste++;
		verificaProximo();
		setTeste();
	}

	private void verificaProximo() {
		if (this.indexOfTeste >= this.prova.getQuestoes().size() - 1) {
			this.btnProx.setVisible(false);
		} else {
			this.btnProx.setVisible(true);
		}
		this.btnAnterior.setVisible(true);
	}

	@Override
	public void stateChanged(ChangeEvent event) {
		int numRespondidas = this.prova.getNumeroRespondidas();
		this.progressBar.setValue(numRespondidas);
	}

}
