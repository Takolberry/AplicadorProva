package br.npc.prova.gerador.tela.principal;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyVetoException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import br.npc.nucleo.prova.Aplicacao;
import br.npc.nucleo.prova.Duracao;
import br.npc.nucleo.prova.Prova;
import br.npc.nucleo.prova.TipoAplicacao;
import br.npc.prova.gerador.editor.treemodel.ProvaTreeModel;

public class TelaProva extends JInternalFrame implements ActionListener {

	private Prova prova;
	private ProvaTreeModel provaTreeModel;
	private JTextField txtDescricao;
	private JTextField txtTurma;
	private JTextField txtDisciplina;
	private JTextField txtProfessor;
	private JTextField txtTipo;
	private JTextField txtEscola;
	private JTextField txtDuracao;
	private JButton btnSalvar;
	private JButton btnCancelar;
	private JPanel aplicacaoPanel;
	private JComboBox<TipoAplicacao> cboAplic;
	private JSpinner numMaxQuestoes;
	private JSpinner numMaxAlt;

	public TelaProva(
			JDesktopPane desktopPane, 
			Prova p,
			ProvaTreeModel provaTreeModel) {
		desktopPane.add(this);
		init();
		setProva(p);
		this.provaTreeModel = provaTreeModel;
	}

	private void setProva(Prova p) {
		this.prova = p;
		this.txtDescricao.setText(this.prova.getDesc());
		this.txtTurma.setText(this.prova.getTurma());
		this.txtTurma.setText(this.prova.getTurma());
		this.txtDisciplina.setText(this.prova.getDisciplina());
		this.txtProfessor.setText(this.prova.getProfessor());
		this.txtTipo.setText(this.prova.getTipo());
		this.txtEscola.setText(this.prova.getEscola());
		this.txtDuracao.setText(this.prova.getDuracao().toString());
		
		Aplicacao aplicacao = this.prova.getAplicacao();
		this.cboAplic.setSelectedItem(aplicacao.getTipo());
		if (aplicacao.getTipo() == TipoAplicacao.RANDOMICA) {
			this.numMaxAlt.setValue(aplicacao.getNumMaxAlterntativas());
			this.numMaxQuestoes.setValue(aplicacao.getNumMaxQuestoes());
		}
	}

	private void init() {
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this);
		panel_1.add(btnCancelar);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(this);
		panel_1.add(btnSalvar);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o:");
		lblDescrio.setBounds(10, 14, 106, 14);
		panel.add(lblDescrio);
		
		txtDescricao = new JTextField();
		txtDescricao.setBounds(126, 11, 260, 20);
		panel.add(txtDescricao);
		txtDescricao.setColumns(50);
		
		JLabel lblTurma = new JLabel("Turma:");
		lblTurma.setBounds(10, 39, 46, 14);
		panel.add(lblTurma);
		
		txtTurma = new JTextField();
		txtTurma.setBounds(126, 36, 86, 20);
		panel.add(txtTurma);
		txtTurma.setColumns(10);
		
		JLabel lblDisciplina = new JLabel("Disciplina:");
		lblDisciplina.setBounds(10, 64, 77, 14);
		panel.add(lblDisciplina);
		
		txtDisciplina = new JTextField();
		txtDisciplina.setBounds(126, 61, 260, 20);
		panel.add(txtDisciplina);
		txtDisciplina.setColumns(20);
		
		JLabel lblProfessor = new JLabel("Professor:");
		lblProfessor.setBounds(10, 89, 77, 14);
		panel.add(lblProfessor);
		
		txtProfessor = new JTextField();
		txtProfessor.setBounds(126, 86, 260, 20);
		panel.add(txtProfessor);
		txtProfessor.setColumns(10);
		
		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setBounds(10, 114, 46, 14);
		panel.add(lblTipo);
		
		txtTipo = new JTextField();
		txtTipo.setBounds(126, 111, 86, 20);
		panel.add(txtTipo);
		txtTipo.setColumns(10);
		
		JLabel lblEscola = new JLabel("Escola:");
		lblEscola.setBounds(10, 139, 46, 14);
		panel.add(lblEscola);
		
		txtEscola = new JTextField();
		txtEscola.setBounds(126, 136, 260, 20);
		panel.add(txtEscola);
		txtEscola.setColumns(50);
		
		JLabel lblDuracao = new JLabel("Dura\u00E7\u00E3o:");
		lblDuracao.setBounds(10, 164, 77, 14);
		panel.add(lblDuracao);
		
		txtDuracao = new JTextField();
		txtDuracao.setBounds(126, 161, 260, 20);
		panel.add(txtDuracao);
		txtDuracao.setColumns(10);
		
		JLabel lblAplicao = new JLabel("Aplica\u00E7\u00E3o:");
		lblAplicao.setBounds(10, 189, 77, 14);
		panel.add(lblAplicao);
		
		cboAplic = new JComboBox<TipoAplicacao>();
		cboAplic.setModel(new DefaultComboBoxModel<TipoAplicacao>(new TipoAplicacao[] {TipoAplicacao.NORMAL, TipoAplicacao.RANDOMICA}));
		cboAplic.setBounds(126, 186, 142, 20);
		cboAplic.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getItem() == TipoAplicacao.RANDOMICA) {
					aplicacaoPanel.setVisible(true);
				} else {
					aplicacaoPanel.setVisible(false);
				}
			}
			
		});
		panel.add(cboAplic);
		
		aplicacaoPanel = new JPanel();
		aplicacaoPanel.setVisible(false);
		aplicacaoPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Aplica\u00E7\u00E3o Rand\u00F4mica", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		aplicacaoPanel.setBounds(10, 227, 376, 142);
		panel.add(aplicacaoPanel);
		aplicacaoPanel.setLayout(null);
		
		JLabel lblNmeroMximoDe = new JLabel("N\u00FAmero m\u00E1ximo de quest\u00F5es:");
		lblNmeroMximoDe.setBounds(10, 25, 204, 14);
		aplicacaoPanel.add(lblNmeroMximoDe);
		
		JLabel lblNmeroMximoDe_1 = new JLabel("N\u00FAmero m\u00E1ximo de alternativas:");
		lblNmeroMximoDe_1.setBounds(10, 50, 204, 14);
		aplicacaoPanel.add(lblNmeroMximoDe_1);
		
		numMaxQuestoes = new JSpinner();
		numMaxQuestoes.setModel(new SpinnerNumberModel(new Integer(5), new Integer(2), null, new Integer(1)));
		numMaxQuestoes.setBounds(226, 23, 48, 20);
		aplicacaoPanel.add(numMaxQuestoes);
		
		numMaxAlt = new JSpinner();
		numMaxAlt.setModel(new SpinnerNumberModel(5, 2, 10, 1));
		numMaxAlt.setBounds(226, 48, 48, 20);
		aplicacaoPanel.add(numMaxAlt);
		
		try {
			setMaximum(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		setClosable(true);
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		try {
			if (event.getSource() == this.btnSalvar) {
				salvar();
			} else if (event.getSource() == this.btnCancelar) {
				this.dispose();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro");
			e.printStackTrace();
		}
	}

	private void salvar() {
		this.prova.setDesc(this.txtDescricao.getText());
		this.prova.setTurma(this.txtTurma.getText());
		this.prova.setDisciplina(this.txtDisciplina.getText());
		this.prova.setProfessor(this.txtProfessor.getText());
		this.prova.setTipo(this.txtTipo.getText());
		this.prova.setEscola(this.txtEscola.getText());
		this.prova.setDuracao(new Duracao(this.txtDuracao.getText()));
		if (this.cboAplic.getSelectedItem() == TipoAplicacao.NORMAL) {
			this.prova.setAplicacao(Aplicacao.getAplicacaoNormal());
		} else {
			this.prova.setAplicacao(Aplicacao.getAplicacaoRandomica((Integer)this.numMaxQuestoes.getValue(), (Integer)this.numMaxAlt.getValue()));
		}
		this.provaTreeModel.fireTreeStructureChanged(this.prova);
		this.dispose();
	}
}
