package br.npc.prova.gerador.tela.principal;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import br.npc.nucleo.prova.Alternativa;
import br.npc.nucleo.prova.Prova;
import br.npc.nucleo.prova.Teste;
import br.npc.prova.gerador.editor.treemodel.ProvaTreeModel;
import br.npc.prova.gerador.util.MoneyTextField;

public class TelaTesteSimples extends JInternalFrame implements MouseListener, ActionListener {
	private static final String NAO = "Não";
	private static final String SIM = "Sim";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tbAlternativas;
	private JRadioButton rdbtnSim;
	private JRadioButton rdbtnNo;
	private JTextArea textArea;
	private JButton btnAdd;
	private JButton btnSalvarQuestao;
	private JButton btnCancelarQuestao;
	private JButton btnApagarQuestao;
	private MoneyTextField txtValor;
	private JTextArea txtQuestao;
	
	private Prova prova;
	private ProvaTreeModel provaTreeModel;
	private Teste teste;
	private JButton btnApagar;
	
	private TelaPrincipal telaPrincipal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new TelaTesteSimples();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaTesteSimples() {
		init();
		setTitle("Quest\u00E3o : Teste");
	}

	public TelaTesteSimples(TelaPrincipal telaPrincipal) {
		this.telaPrincipal = telaPrincipal;
		telaPrincipal.getDesktopPane().add(this);
		this.prova = telaPrincipal.getProva();
		this.provaTreeModel =  telaPrincipal.getProvaTreeModel();
		init();
	}

	public TelaTesteSimples(
			TelaPrincipal telaPrincipal,
			Teste teste) {
		this.telaPrincipal = telaPrincipal;
		telaPrincipal.getDesktopPane().add(this);
		this.prova = telaPrincipal.getProva();
		this.provaTreeModel = telaPrincipal.getProvaTreeModel();
		init();
		setTeste(teste);
		
	}

	private void init() {
		
		setBounds(100, 100, 934, 610);
		
		JPanel panel_4 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_4.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		getContentPane().add(panel_4, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Valor:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel_4.add(lblNewLabel);
		
		txtValor = new MoneyTextField(8);
		txtValor.setText("1,00");
		panel_4.add(txtValor);
		txtValor.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		getContentPane().add(panel_3, BorderLayout.SOUTH);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{67, 344, 75, 63, 0};
		gbl_panel_3.rowHeights = new int[]{23, 0};
		gbl_panel_3.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		btnApagarQuestao = new JButton("Apagar");
		btnApagarQuestao.addActionListener(this);
		GridBagConstraints gbc_btnApagarQuestao = new GridBagConstraints();
		gbc_btnApagarQuestao.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnApagarQuestao.insets = new Insets(0, 0, 0, 5);
		gbc_btnApagarQuestao.gridx = 0;
		gbc_btnApagarQuestao.gridy = 0;
		panel_3.add(btnApagarQuestao, gbc_btnApagarQuestao);
		
		btnCancelarQuestao = new JButton("Cancelar");
		btnCancelarQuestao.addActionListener(this);
		GridBagConstraints gbc_btnCancelarQuestao = new GridBagConstraints();
		gbc_btnCancelarQuestao.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnCancelarQuestao.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelarQuestao.gridx = 2;
		gbc_btnCancelarQuestao.gridy = 0;
		panel_3.add(btnCancelarQuestao, gbc_btnCancelarQuestao);
		
		btnSalvarQuestao = new JButton("Salvar");
		btnSalvarQuestao.addActionListener(this);
		GridBagConstraints gbc_btnSalvarQuestao = new GridBagConstraints();
		gbc_btnSalvarQuestao.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSalvarQuestao.anchor = GridBagConstraints.NORTH;
		gbc_btnSalvarQuestao.gridx = 3;
		gbc_btnSalvarQuestao.gridy = 0;
		panel_3.add(btnSalvarQuestao, gbc_btnSalvarQuestao);
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 18));
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Enunciado", null, panel, null);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		txtQuestao = new JTextArea();
		panel.add(txtQuestao);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Alternativas", null, panel_1, null);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.NORTH);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{48, 138, 45, 51, 0, 0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblTexto = new JLabel("Texto :");
		GridBagConstraints gbc_lblTexto = new GridBagConstraints();
		gbc_lblTexto.anchor = GridBagConstraints.WEST;
		gbc_lblTexto.insets = new Insets(0, 0, 5, 5);
		gbc_lblTexto.gridx = 0;
		gbc_lblTexto.gridy = 1;
		panel_2.add(lblTexto, gbc_lblTexto);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		
		btnAdd = new JButton("Add");
		btnAdd.addActionListener(this);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridheight = 2;
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 1;
		panel_2.add(scrollPane_1, gbc_scrollPane_1);
		
		textArea = new JTextArea();
		scrollPane_1.setViewportView(textArea);
		textArea.setRows(7);
		textArea.setLineWrap(true);
		textArea.setColumns(35);
		
		JLabel lblCorreta = new JLabel("Correta?");
		GridBagConstraints gbc_lblCorreta = new GridBagConstraints();
		gbc_lblCorreta.anchor = GridBagConstraints.WEST;
		gbc_lblCorreta.insets = new Insets(0, 0, 5, 5);
		gbc_lblCorreta.gridx = 2;
		gbc_lblCorreta.gridy = 1;
		panel_2.add(lblCorreta, gbc_lblCorreta);
		
		rdbtnSim = new JRadioButton(SIM);
		rdbtnSim.setSelected(false);
		buttonGroup.add(rdbtnSim);
		GridBagConstraints gbc_rdbtnSim = new GridBagConstraints();
		gbc_rdbtnSim.anchor = GridBagConstraints.WEST;
		gbc_rdbtnSim.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnSim.gridx = 3;
		gbc_rdbtnSim.gridy = 1;
		panel_2.add(rdbtnSim, gbc_rdbtnSim);
		
		rdbtnNo = new JRadioButton("N\u00E3o");
		rdbtnNo.setSelected(true);
		buttonGroup.add(rdbtnNo);
		GridBagConstraints gbc_rdbtnNo = new GridBagConstraints();
		gbc_rdbtnNo.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNo.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNo.gridx = 4;
		gbc_rdbtnNo.gridy = 1;
		
		panel_2.add(rdbtnNo, gbc_rdbtnNo);
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.insets = new Insets(0, 0, 5, 0);
		gbc_btnAdd.anchor = GridBagConstraints.WEST;
		gbc_btnAdd.gridx = 5;
		gbc_btnAdd.gridy = 1;
		panel_2.add(btnAdd, gbc_btnAdd);
		
		btnApagar = new JButton("Apagar");
		btnApagar.addActionListener(this);
		GridBagConstraints gbc_btnApagar = new GridBagConstraints();
		gbc_btnApagar.insets = new Insets(0, 0, 0, 5);
		gbc_btnApagar.gridx = 4;
		gbc_btnApagar.gridy = 3;
		panel_2.add(btnApagar, gbc_btnApagar);
		
		
		
	
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		tbAlternativas = new JTable();
		tbAlternativas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbAlternativas.setModel(new DefaultTableModel(
			new Object[][] {
			},
			
			new String[] {
				"Texto", "Correta?"
			}
		) {
			Class[] columnTypes = new Class[] {
				Object.class, Boolean.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tbAlternativas.getColumnModel().getColumn(0).setPreferredWidth(190);
		tbAlternativas.addMouseListener(this);
		scrollPane.setViewportView(tbAlternativas);
		
		try {
			setMaximum(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		setClosable(true);
		
		setModoAdicao();
	
		setVisible(true);
	}

	private void setDefaultValues() {
		this.rdbtnSim.setSelected(false);
		this.rdbtnNo.setSelected(true);
		this.textArea.setText("");
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		if (event.getClickCount() == 2) {
			
			JTable table = tbAlternativas;
			int row = table.getSelectedRow();
			if (row >= 0) {
				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				String texto = (String)tableModel.getValueAt(row, 0);
				textArea.setText(texto);
				boolean sim = (Boolean)tableModel.getValueAt(row, 1);
				rdbtnSim.setSelected(sim);
				tableModel.removeRow(row);
				setModoEdicao();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		
	}

	@Override
	public void mouseExited(MouseEvent event) {
		
	}

	@Override
	public void mousePressed(MouseEvent event) {
		
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == this.btnAdd) {
				addAlternativa();
				setModoAdicao();
			} else if (e.getSource() == this.btnSalvarQuestao) {
				Teste t = getTeste();
				if (!t.exatamenteIgual(this.teste)) {
					this.prova.getQuestoes().remove(this.teste);
					this.prova.getQuestoes().add(t);
					Collections.sort(this.prova.getQuestoes());
					this.provaTreeModel.fireTreeStructureChanged(this.prova);
					this.telaPrincipal.ajustarTitulo(false);
				}
				this.dispose();
			} else if (e.getSource() == this.btnCancelarQuestao) {
				this.dispose();
			} else if (e.getSource() == this.btnApagarQuestao) {
				this.prova.getQuestoes().remove(this.teste);
			} else if (e.getSource() == this.btnApagar) {
				setModoAdicao();
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage());
		}
	}

	private void setTeste(Teste teste) {
		this.teste = teste;
		this.txtValor.setText(String.format("%.2f",teste.getValor()));
		this.txtQuestao.setText(teste.getTexto());
		List<String> alts = new ArrayList<String>();
		for (Map.Entry<String, Alternativa> entry : teste.getAlternativas().entrySet()) {
			alts.add(entry.getValue().getId());
			
		}
		Collections.sort(alts);
		for (String id : alts) {
			Alternativa alt = teste.getAlternativas().get(id);
			addAlternativa(alt.getValor(), alt.isCorreta());
		}
		setTitle(String.format("Questão [ Teste ] %s", teste.toStringResumido(100).replaceFirst("\\[.*\\]", " - ")));
	}
	
	private Teste getTeste() {
		Teste teste = new Teste();//this.teste;
		if (this.teste == null) {
			String id = "" + (this.prova.getQuestoes().size() + 1);
			teste.setId(id);
		} else {
			teste.setId(this.teste.getId());
		}
		String strValor = this.txtValor.getText();
		strValor = strValor.replace(".", ""); 
		strValor = strValor.replace(",", "."); 
		double valor = Double.parseDouble(strValor);
		
		String texto = this.txtQuestao.getText();
		Map<String, Alternativa> alternativas = getAlternativas();
		Set<Alternativa>  alternativasCorretas = getAlternativasCorretas(alternativas);
		if (alternativasCorretas.size() == 0) {
			throw new IllegalArgumentException("Adicione ao menos uma alternativa correta");
		}
		teste.setValor(valor);
		teste.setTexto(texto);
		teste.setAlternativas(alternativas);
		teste.getAlternativasCorretas().clear();
		teste.getAlternativasCorretas().addAll(alternativasCorretas);

		return teste; 
	}

	private Set<Alternativa> getAlternativasCorretas(Map<String, Alternativa> alternativas) {
		Set<Alternativa> altCorretas = new HashSet<Alternativa>();
		for (Map.Entry<String, Alternativa> entry : alternativas.entrySet()) {
			if (entry.getValue().isCorreta()) {
				altCorretas.add(entry.getValue());
			}
		}
		return altCorretas;
	}

	private Map<String, Alternativa> getAlternativas() {
		Map<String, Alternativa> alternativas = new HashMap<String, Alternativa>();
		DefaultTableModel model = (DefaultTableModel)this.tbAlternativas.getModel();
		int linhas = model.getRowCount();
		for (int i = 0; i < linhas; i++) {
			Alternativa alt = getAlternativa(model, i);
			alternativas.put(i + "", alt);
		}
		
		return alternativas;
	}

	private Alternativa getAlternativa(DefaultTableModel model, int i) {
		String texto = (String)model.getValueAt(i, 0);
		boolean correta = (Boolean)model.getValueAt(i, 1);
		Alternativa alt = new Alternativa(i + "", texto, correta);
		return alt;
	}

	private void setModoAdicao() {
		setModo(false);
		setDefaultValues();
		this.textArea.requestFocus();
	}

	private void setModoEdicao() {
		setModo(true);
	}
	private void setModo(boolean edicao) {
		this.btnApagar.setEnabled(edicao);
		this.tbAlternativas.setEnabled(!edicao);
	}

	private void addAlternativa(Alternativa alt) {
		addAlternativa(alt.getValor(), alt.isCorreta());
	}
	
	private void addAlternativa() {
		verificaTexto();
		addAlternativa(this.textArea.getText(), this.rdbtnSim.isSelected());
		this.textArea.setText("");
		this.rdbtnNo.setSelected(true);
	}
	
	private void verificaTexto() {
		Set<String> alternativas = new HashSet<String>();
		DefaultTableModel model = (DefaultTableModel)this.tbAlternativas.getModel();
		int linhas = model.getRowCount();
		for (int i = 0; i < linhas; i++) {
			String texto = (String)model.getValueAt(i, 0);
			alternativas.add(texto.trim().toUpperCase());
		}
		if (alternativas.contains(this.textArea.getText().trim().toUpperCase())) {
			throw new IllegalArgumentException("Já existe uma alternativa com o mesmo texto");
		}
	}

	private void addAlternativa(String valor, boolean correta) {
		DefaultTableModel model = (DefaultTableModel)this.tbAlternativas.getModel();
		Object[] row = new Object[2];
		row[0] = valor;
		row[1] = correta;
		model.addRow(row);
	}
}
