package br.npc.prova.gerador.tela.principal;

import java.awt.BorderLayout;
import java.awt.EventQueue;
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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import br.npc.prova.gerador.editor.TextEditorPane;

public class TelaTeste extends JInternalFrame implements MouseListener, ActionListener {
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
	private JButton btnNovaCancelar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new TelaTeste();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaTeste() {
		init();
	}

	public TelaTeste(JDesktopPane desktopPane) {
		desktopPane.add(this);
		init();
	}

	private void init() {
		setTitle("Quest\u00E3o : Teste");
		setBounds(100, 100, 634, 449);
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 18));
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Enunciado", null, panel, null);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		TextEditorPane textEditorPane = new TextEditorPane();
		panel.add(textEditorPane);
		
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
		btnAdd.setEnabled(false);
		btnAdd.addActionListener(this);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.anchor = GridBagConstraints.NORTH;
		gbc_scrollPane_1.gridheight = 2;
		gbc_scrollPane_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 1;
		panel_2.add(scrollPane_1, gbc_scrollPane_1);
		
		textArea = new JTextArea();
		textArea.setEnabled(false);
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
		rdbtnSim.setEnabled(false);
		rdbtnSim.setSelected(false);
		buttonGroup.add(rdbtnSim);
		GridBagConstraints gbc_rdbtnSim = new GridBagConstraints();
		gbc_rdbtnSim.anchor = GridBagConstraints.WEST;
		gbc_rdbtnSim.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnSim.gridx = 3;
		gbc_rdbtnSim.gridy = 1;
		panel_2.add(rdbtnSim, gbc_rdbtnSim);
		
		rdbtnNo = new JRadioButton("N\u00E3o");
		rdbtnNo.setEnabled(false);
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
		
		btnNovaCancelar = new JButton("Nova Alternativa");
		btnNovaCancelar.addActionListener(this);
		GridBagConstraints gbc_btnNovaCancelar = new GridBagConstraints();
		gbc_btnNovaCancelar.insets = new Insets(0, 0, 0, 5);
		gbc_btnNovaCancelar.gridx = 2;
		gbc_btnNovaCancelar.gridy = 3;
		panel_2.add(btnNovaCancelar, gbc_btnNovaCancelar);
		
		JButton btnApagar = new JButton("Apagar");
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
		
		setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		if (event.getClickCount() == 2) {
			
			JTable table = tbAlternativas;
			int row = table.getSelectedRow();
			DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
			String texto = (String)tableModel.getValueAt(row, 0);
			textArea.setText(texto);
			String correta = (String)tableModel.getValueAt(row, 1);
			boolean sim = SIM.equals(correta);
			rdbtnSim.setSelected(sim);
			//getRdbtnNo().setSelected(!sim);
			
			tableModel.removeRow(row);
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
		if (e.getSource() == this.btnAdd) {
			addAlternativa();
		} else if (e.getSource() == this.btnNovaCancelar) {
			if (this.btnNovaCancelar.getText().startsWith("Nova")) {
				this.textArea.setEnabled(true);
				this.rdbtnSim.setEnabled(true);
				this.rdbtnNo.setSelected(true);
				this.rdbtnNo.setEnabled(true);
				this.btnNovaCancelar.setText("Cancelar");
			} else {
				this.textArea.setText("");
				this.textArea.setEnabled(false);
				this.rdbtnSim.setSelected(false);
				this.rdbtnSim.setEnabled(false);
				this.rdbtnNo.setSelected(true);
				this.rdbtnNo.setEnabled(false);
				this.btnNovaCancelar.setText("Nova Alternativa");
			}
		}
		
	}

	private void addAlternativa() {
		DefaultTableModel model = (DefaultTableModel)this.tbAlternativas.getModel();
		String[] row = new String[2];
		row[0] = this.textArea.getText();
		row[1] = this.rdbtnSim.isSelected()?SIM:NAO;
		model.addRow(row);
		
		this.textArea.setText("");
		this.rdbtnNo.setSelected(true);
	}
}
