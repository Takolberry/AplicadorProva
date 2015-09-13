package br.npc.prova.gerador.tela.principal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.ProgressMonitorInputStream;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

import br.npc.nucleo.prova.Prova;
import br.npc.nucleo.prova.Questao;
import br.npc.nucleo.prova.Teste;
import br.npc.prova.gerador.editor.treemodel.ProvaTree;
import br.npc.prova.gerador.editor.treemodel.ProvaTreeModel;
import br.npc.prova.gerador.tela.rede.TelaRede;
import br.npc.prova.gerador.util.FiltroExtensao;

public class TelaPrincipal extends JFrame implements MouseListener, ActionListener, TreeModelListener {
	private JDesktopPane desktopPane;
	private JTree arvoreProva;
	private Prova prova = new Prova();
	private boolean sinc = true;
	private File arquivoAberto;
	private ProvaTreeModel provaTreeModel;
	private JButton btnSalvar;
	private JButton btnNovoTesteQuestion;
	private JButton btnDelQuestion;
	private JButton btnDownQuestion;
	private JButton btnUpQuestion;
	private JButton btnAbrir;
	private JButton btnCopiarQuestao;
	private JList<String> problemList;
	private JButton btnRede;
	public JLabel lblInfo;


	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the application.
	 */
	public TelaPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		final TelaPrincipal tela = this;
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent event) {
				if (!tela.isSinc()) {
					int opc = JOptionPane.showConfirmDialog(tela, "Foram feitas alteração nessa prova, deseja salvar estas alterações?", "Encerrando..." , JOptionPane.YES_NO_OPTION);
					if (opc == JOptionPane.YES_OPTION) {
						try {
							if (tela.salvar()) {
								tela.dispose();
							}
						} catch (Exception e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(tela, e.getMessage());
						}
					} else {
						tela.dispose();
					}
				} else {
					tela.dispose();
				}
			}
		});
		
		setBounds(50, 50, 900, 800);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE );
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setTitle("SGP - Sistema Gerador de Prova");
		setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 0, 1, 0, (Color) new Color(0, 0, 0)));
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		btnNovoTesteQuestion = new JButton("Teste");
		
		btnNovoTesteQuestion.addActionListener(this);
		
		btnNovoTesteQuestion.setHorizontalTextPosition(SwingConstants.CENTER);
		btnNovoTesteQuestion.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnNovoTesteQuestion.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnNovoTesteQuestion.setHorizontalAlignment(SwingConstants.TRAILING);
		btnNovoTesteQuestion.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/br/npc/prova/gerador/images/1362493008_Favorites.png")));
		panel.add(btnNovoTesteQuestion);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(this);
		btnSalvar.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/br/npc/prova/gerador/images/disquete_1736_gnome_dev_floppy1.png")));
		btnSalvar.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnSalvar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnSalvar.setHorizontalAlignment(SwingConstants.TRAILING);
		btnSalvar.setAlignmentX(0.5f);
		panel.add(btnSalvar);
		
		btnAbrir = new JButton("Abrir");
		btnAbrir.addActionListener(this);
		btnAbrir.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/br/npc/prova/gerador/images/file-open-icone-7290-48.png")));
		btnAbrir.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnAbrir.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAbrir.setHorizontalAlignment(SwingConstants.TRAILING);
		btnAbrir.setAlignmentX(0.5f);
		panel.add(btnAbrir);
		
		btnRede = new JButton("Rede");
		btnRede.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/br/npc/prova/gerador/images/Network-48-2.png")));
		btnRede.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnRede.setHorizontalTextPosition(SwingConstants.CENTER);
		btnRede.setHorizontalAlignment(SwingConstants.TRAILING);
		btnRede.setAlignmentX(0.5f);
		btnRede.addActionListener(this);
		panel.add(btnRede);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setDividerSize(10);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		getContentPane().add(splitPane_1, BorderLayout.CENTER);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane_1.setLeftComponent(splitPane);
		splitPane.setLastDividerLocation(10);
		splitPane.setDividerSize(10);
		splitPane.setOneTouchExpandable(true);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 18));
		splitPane.setLeftComponent(tabbedPane);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Prova", null, panel_2, null);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_3.setFocusable(false);
		panel_2.add(panel_3, BorderLayout.WEST);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.Y_AXIS));
		
		btnUpQuestion = new JButton("");
		btnUpQuestion.addActionListener(this);
		btnUpQuestion.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/br/npc/prova/gerador/images/6242_16x16.png")));
		panel_3.add(btnUpQuestion);
		
		btnDownQuestion = new JButton("");
		btnDownQuestion.addActionListener(this);
		btnDownQuestion.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/br/npc/prova/gerador/images/6167_16x16.png")));
		panel_3.add(btnDownQuestion);
		
		btnDelQuestion = new JButton("");
		btnDelQuestion.addActionListener(this);
		btnDelQuestion.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/br/npc/prova/gerador/images/6055_16x16.png")));
		panel_3.add(btnDelQuestion);
		
		btnCopiarQuestao = new JButton("");
		btnCopiarQuestao.addActionListener(this);
		btnCopiarQuestao.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/br/npc/prova/gerador/images/SaveAll16x16.png")));
		panel_3.add(btnCopiarQuestao);
		
		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4, BorderLayout.CENTER);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[]{0, 0};
		gbl_panel_4.rowHeights = new int[]{0, 0};
		gbl_panel_4.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_4.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_4.setLayout(gbl_panel_4);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panel_4.add(scrollPane, gbc_scrollPane);
		
		arvoreProva = new ProvaTree(this.prova);
		scrollPane.setViewportView(arvoreProva);
		arvoreProva.addMouseListener(this);
		provaTreeModel = (ProvaTreeModel)arvoreProva.getModel();
		
		JPanel panel_5 = new JPanel();
		panel_2.add(panel_5, BorderLayout.NORTH);
		panel_5.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton button = new JButton("{...}");
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		panel_5.add(button);
		
		JButton btnN = new JButton("N\u00BA");
		panel_5.add(btnN);
		
		desktopPane = new JDesktopPane();
		splitPane.setRightComponent(desktopPane);
		splitPane.setDividerLocation(400);
		
		JTabbedPane painelInferior = new JTabbedPane(JTabbedPane.TOP);
		painelInferior.setFont(new Font("Tahoma", Font.PLAIN, 18));
		splitPane_1.setRightComponent(painelInferior);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		painelInferior.addTab("Problemas", null, scrollPane_1, null);
		
		problemList = new JList<String>();
		scrollPane_1.setViewportView(problemList);
		problemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		problemList.setModel(new DefaultListModel<String>());
		final Icon warningIcon = new ImageIcon(TelaPrincipal.class.getResource("/br/npc/prova/gerador/images/warning.png"));
		problemList.setCellRenderer(new DefaultListCellRenderer() {
		    @Override
		    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		        label.setIcon(warningIcon);
		        return label;
		    }
		});
		splitPane_1.setDividerLocation(600);
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		lblInfo = new JLabel("Mensagem: Blablabla");
		lblInfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				
			}
		});
		panel_1.add(lblInfo);
		provaTreeModel.addTreeModelListener(this);

	}

	public JDesktopPane getDesktopPane() {
		return desktopPane;
	}
	
	public Prova getProva() {
		return prova;
	}

	public ProvaTreeModel getProvaTreeModel() {
		return provaTreeModel;
	}
	
	public boolean isSinc() {
		return sinc;
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		if (event.getClickCount() >= 2) {
			
			for(Component c : getDesktopPane().getComponents()) {
				if (c instanceof JInternalFrame) {
					((JInternalFrame) c).dispose();
				}
			}
			
			TreePath[] paths = this.arvoreProva.getSelectionPaths();
			for (TreePath treePath : paths) {
				Object[] path = treePath.getPath();
				Object noh = path[path.length - 1];
				if (noh instanceof Teste) {
					Teste teste = (Teste)noh;
					new TelaTesteSimples(this, teste);
					break;
				} else if (noh instanceof Prova) {
					Prova p = (Prova)noh;
					new TelaProva(getDesktopPane(), p, this.provaTreeModel);
					break;
				}
			}
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		try {
			if (event.getSource() == this.btnNovoTesteQuestion) {
				new TelaTesteSimples(this);
			} else if (event.getSource() == this.btnSalvar) {
				salvar();
			} else if (event.getSource() == this.btnDelQuestion) {
				deleteQuestoes();
				this.sinc = false;
			} else if (event.getSource() == this.btnUpQuestion) {
				moverQuestaoParaCima();
				this.sinc = false;
			} else if (event.getSource() == this.btnDownQuestion) {
				moverQuestaoParaBaixo();
				this.sinc = false;
			} else if (event.getSource() == this.btnAbrir) {
				abrirProva();
			} else if (event.getSource() == this.btnCopiarQuestao) {
				copiarQuestao();
			}  else if (event.getSource() == this.btnRede) {
				new TelaRede(this, 7, 8);
			}
			if (this.prova != null) {
				if (!sinc) {
					ajustarTitulo(this.sinc);
				}
			}
			verificarPerguntasSemelhantes();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			e.printStackTrace();
		}
	}

	private void copiarQuestao() throws CloneNotSupportedException {
		TreePath[] paths = this.arvoreProva.getSelectionPaths();
		Teste teste = null;
		for (TreePath treePath : paths) {
			Object[] path = treePath.getPath();
			Object noh = path[path.length - 1];
			if (noh instanceof Teste) {
				teste = (Teste)noh;
				teste = (Teste)teste.clone();
				teste.setId("" + (this.prova.getQuestoes().size() + 1));
				this.prova.getQuestoes().add(teste);
				this.provaTreeModel.fireTreeStructureChanged(this.prova);
				this.ajustarTitulo(false);
			}
		}
		
		Toolkit.getDefaultToolkit().beep();
	}

	public void verificarPerguntasSemelhantes() {
		this.problemList.removeAll();
		DefaultListModel<String> model = (DefaultListModel<String>)this.problemList.getModel();
		model.removeAllElements();
		List<Questao> questoes = new ArrayList<Questao>(this.prova.getQuestoes());
		List<Questao> questoesAux = new ArrayList<Questao>(this.prova.getQuestoes());
		Iterator<Questao> it = questoes.iterator();
		while (it.hasNext()) {
			Teste questao = (Teste) it.next();
			questoesAux.remove(questao);
			Iterator<Questao> it2 = questoesAux.iterator();
			while (it2.hasNext()) {
				Teste questao2 = (Teste) it2.next();
//				if (!questao.equals(questao2)) {
					if (questao.getTexto().equals(questao2.getTexto())) {
						
						model.addElement(String.format("As questões %s e %s são iguais, altere uma delas", questao.getId(), questao2.getId()));
					}
//				}
			}
		}
	}

	private void abrirProva() throws FileNotFoundException, IOException, ClassNotFoundException {
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
			
			this.prova = (Prova)objectIn.readObject();
			this.provaTreeModel = new ProvaTreeModel(this.prova);
			this.arvoreProva.setModel(provaTreeModel);
			this.provaTreeModel.fireTreeStructureChanged(this.prova);
			objectIn.close();
			
			this.sinc = true;
			this.arquivoAberto = arquivo;
			ajustarTitulo(this.sinc);
		}
	}

	public void ajustarTitulo(boolean sinc) {
		this.sinc = sinc;
		if (this.arquivoAberto != null) {
			this.setTitle("SGP - Sistema Gerador de Prova : " + this.arquivoAberto.getAbsoluteFile() + (sinc?"":"*"));
		} else {
			this.setTitle("SGP - Sistema Gerador de Prova");
		}
		verificarPerguntasSemelhantes();
	}

	private void moverQuestaoSelecionada(int pos) {
		TreePath[] paths = this.arvoreProva.getSelectionPaths();
		Teste teste = null;
		for (TreePath treePath : paths) {
			Object[] path = treePath.getPath();
			Object noh = path[path.length - 1];
			if (noh instanceof Teste) {
				teste = (Teste)noh;
				break;
			}
		}
		if (teste != null) {
			int indexOf = this.prova.getQuestoes().indexOf(teste);
			if (pos < 0 && indexOf > 0 
					|| pos > 0 && indexOf < this.prova.getQuestoes().size()) {
				this.prova.getQuestoes().remove(indexOf);
				indexOf += pos;
				this.prova.getQuestoes().add(indexOf, teste);
			}
			//ajusta id's
			int indice = 1;
			for (Questao q : this.prova.getQuestoes()) {
				q.setId((indice++) + "");
			}
			
			Object[] arrayPath = new Object[]{this.prova, teste};
			TreePath path = new TreePath(arrayPath);
//			TreePath path = new TreePath(teste);
			this.provaTreeModel.fireTreeStructureChanged(this.prova);	
			this.arvoreProva.setSelectionPath(path);
		}
			
	}

	private void moverQuestaoParaBaixo() {
		moverQuestaoSelecionada(1);
	}
	
	private void moverQuestaoParaCima() {
		moverQuestaoSelecionada(-1);
	}

	private void deleteQuestoes() {
		TreePath[] paths = this.arvoreProva.getSelectionPaths();
		for (TreePath treePath : paths) {
			Object[] path = treePath.getPath();
			Object noh = path[path.length - 1];
			if (noh instanceof Teste) {
				Teste teste = (Teste)noh;
				this.prova.getQuestoes().remove(teste);
			}
		}
		int indice = 1;
		for (Questao q : this.prova.getQuestoes()) {
			q.setId((indice++) + "");
		}
		this.provaTreeModel.fireTreeStructureChanged(this.prova);
	}

	private boolean salvar() throws IOException, FileNotFoundException {
		File diretorioOrigem = new File("C:\\ProvasGerador");
		JFileChooser telaSalvar = new JFileChooser();
		telaSalvar.setCurrentDirectory(diretorioOrigem);
		telaSalvar.setFileFilter(new FiltroExtensao("prv"));
		telaSalvar.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int ret = telaSalvar.showSaveDialog(this);
		
		if (ret == JFileChooser.APPROVE_OPTION) {
			File arquivo = telaSalvar.getSelectedFile();
			boolean cont = true;
			if (arquivo.exists()) {
				int opc = JOptionPane.showConfirmDialog(
						this, 
						"Arquivo '" + arquivo.getName() + "' já existe, deseja sobrescrevê-lo?", 
						"Salvando...", 
						JOptionPane.YES_NO_OPTION);
				
				if (opc == JOptionPane.YES_OPTION) {
					arquivo.delete();
				} else {
					cont = false;
				}
			}
			if (cont) {
				ObjectOutputStream objectOut = 
						new ObjectOutputStream(
								new BufferedOutputStream(
										new FileOutputStream(arquivo)));			
				objectOut.writeObject(this.prova);
				objectOut.close();
				this.arquivoAberto = arquivo;
				ajustarTitulo(true);
				
				return true;
			}
		}
		return false;
	}

	@Override
	public void treeNodesChanged(TreeModelEvent e) {
		ajustarTitulo(false);
	}

	@Override
	public void treeNodesInserted(TreeModelEvent e) {
		ajustarTitulo(false);
	}

	@Override
	public void treeNodesRemoved(TreeModelEvent e) {
		ajustarTitulo(false);
	}

	@Override
	public void treeStructureChanged(TreeModelEvent e) {
		ajustarTitulo(false);
	}
}
