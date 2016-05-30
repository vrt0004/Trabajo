package ui;


import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class BloquePreguntas extends JDialog {

	
	@SuppressWarnings({ "unused" })
	private Main main;
	private JPanel controlesPanel;
	private JButton añadeButton;
	private JPanel mainPanel;
	private Component controlesStrut;


	/**
	 * Create the dialog.
	 */

	public BloquePreguntas(Main main) {
		this.main = main;

		setTitle("Generar bloque de preguntas");
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModal(true);
		setBounds(100, 100, 375, 532);
		getContentPane().setLayout(new BorderLayout(0, 0));

		this.mainPanel = new JPanel();
		this.mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(this.mainPanel);
		this.mainPanel
				.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
		
		this.controlesPanel = new JPanel();
		this.controlesPanel.setBorder(new EmptyBorder(0, 5, 5, 5));
		this.mainPanel.add(this.controlesPanel);
		this.controlesPanel.setLayout(new BoxLayout(this.controlesPanel,
				BoxLayout.Y_AXIS));

		
		this.añadeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.controlesPanel.add(this.añadeButton);
		this.controlesStrut = Box.createVerticalStrut(5);
		this.controlesPanel.add(this.controlesStrut);
		setVisible(true);
	}

}
	
	