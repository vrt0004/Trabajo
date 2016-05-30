package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import analisis.analisisSintactico.LALR1;
import analisis.analisisSintactico.LL1;
import analisis.analisisSintactico.LR1;
import analisis.analisisSintactico.SLR1;

public class Main {

	private JFrame frmPlgram;
	private JPanel controlPanel;
	private JPanel vistaPreviaPanel;
	private JScrollPane vistaPreviaScroll;

	public JEditorPane vistaPreviaText;

	private JPanel añadirPanel;
	private JButton añadirButton;
	private JComboBox<String> añadirBox;
	private JPanel contenedorPanel;
	private Component añadirDerechoStrut;
	private Component añadirIzquierdoStrut;
	private JMenuBar menuBar;


	@SuppressWarnings("unused")
	private Main main = this;
	private JMenuItem menuLL;
	private JMenuItem menuLR;
	private JMenuItem menuSLR;
	private JMenuItem menuLALR;

	private JMenuItem menuExportarMoodleXMLButton;
	private JMenuItem menuExportarLatexButton;
	private JMenu menuArchivo;
	private JMenu menuExportar;
	private JMenu menuAyuda;
	private JMenuItem menuAcercaDe;
	private JMenuItem menuWeb;
	private List<ProblemaPanel<?>> panelesProblema = new ArrayList<>();
	private JFileChooser fileChooser;
	private JScrollPane contenedorScroll;
	private JPanel problemasPanel;
	private boolean scrollContenedor = true;
	private boolean scrollVistaPrevia = true;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmPlgram.setVisible(true);
				} catch (Exception e) {

				}
			}
		});
	}

	public Main() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {

		}
		initialize();
		this.fileChooser = new JFileChooser();
		this.fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.frmPlgram = new JFrame();
		this.frmPlgram.setTitle("PLGRAM");
		this.frmPlgram.setBounds(100, 100, 1150, 900);
		this.frmPlgram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initMenuBar();
		initControlPanel();
		initVistaPreviaPanel();
	}

	/**
	 * Initialize the contents of the MenuBar.
	 */
	private void initMenuBar() {
		this.menuBar = new JMenuBar();
		this.frmPlgram.getContentPane().add(this.menuBar, BorderLayout.NORTH);

		this.menuArchivo = new JMenu("Archivo");
		this.menuBar.add(this.menuArchivo);

		this.menuExportar = new JMenu("Exportar");
		this.menuBar.add(this.menuExportar);

		this.menuAyuda = new JMenu("Ayuda");
		this.menuBar.add(this.menuAyuda);

		this.menuWeb = new JMenuItem("Página web");
		this.menuWeb.addActionListener(new MenuWebActionListener());
		this.menuAyuda.add(this.menuWeb);

		this.menuAcercaDe = new JMenuItem("Acerca de");
		this.menuAcercaDe.addActionListener(new MenuAcercaDeActionListener());
		this.menuAyuda.add(this.menuAcercaDe);

		this.menuArchivo.addSeparator();

		this.menuLL = new JMenuItem("Nueva cuestión - LL");
		this.menuLL.addActionListener(new MenuLLActionListener());
		this.menuLL.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.Event.CTRL_MASK));

		this.menuArchivo.add(this.menuLL);

		this.menuSLR = new JMenuItem("Nueva cuestión - SLR");
		this.menuSLR.addActionListener(new MenuLRActionListener());
		this.menuSLR.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.Event.CTRL_MASK));

		this.menuArchivo.add(this.menuSLR);

		this.menuLR = new JMenuItem("Nueva cuestión - LR");
		this.menuLR.addActionListener(new MenuSLRActionListener());
		this.menuLR.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.Event.CTRL_MASK));

		this.menuArchivo.add(this.menuLR);

		this.menuLALR = new JMenuItem("Nueva cuestión - LALR");
		this.menuLALR.addActionListener(new MenuLALRActionListener());
		this.menuLALR.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.Event.CTRL_MASK));

		this.menuArchivo.add(this.menuLALR);

		this.menuExportarMoodleXMLButton = new JMenuItem("Exportar como Moodle XML");
		this.menuExportarMoodleXMLButton.addActionListener(new MenuExportarButtonActionListener());
		this.menuExportarMoodleXMLButton
				.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.Event.CTRL_MASK));
		this.menuExportar.add(this.menuExportarMoodleXMLButton);

		this.menuExportarLatexButton = new JMenuItem("Exportar como LaTeX");
		this.menuExportarLatexButton.addActionListener(new MenuExportarButtonActionListener());
		this.menuExportarLatexButton
				.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.Event.CTRL_MASK));
		this.menuExportar.add(this.menuExportarLatexButton);

	}

	private void initControlPanel() {
		this.controlPanel = new JPanel();
		this.controlPanel.setBorder(null);
		this.frmPlgram.getContentPane().add(this.controlPanel, BorderLayout.WEST);
		this.controlPanel.setLayout(new BorderLayout(0, 0));

		this.contenedorScroll = new JScrollPane();
		this.contenedorScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.controlPanel.add(this.contenedorScroll, BorderLayout.CENTER);

		this.problemasPanel = new JPanel();
		this.problemasPanel.setBorder(null);
		this.contenedorScroll.add(this.problemasPanel);
		this.contenedorScroll.setViewportView(this.problemasPanel);

		this.contenedorPanel = new JPanel();
		this.contenedorPanel.setBorder(null);
		this.controlPanel.add(this.contenedorPanel, BorderLayout.NORTH);
		this.contenedorPanel.setLayout(new BoxLayout(this.contenedorPanel, BoxLayout.Y_AXIS));

		this.añadirPanel = new JPanel();
		this.controlPanel.add(this.añadirPanel, BorderLayout.SOUTH);

		this.añadirButton = new JButton("+");
		this.añadirButton.addActionListener(new AddButtonActionListener());

		this.añadirIzquierdoStrut = Box.createHorizontalStrut(70);
		this.añadirPanel.add(this.añadirIzquierdoStrut);
		this.añadirPanel.add(this.añadirButton);

		this.añadirBox = new JComboBox<>();
		this.añadirBox
				.setModel(new DefaultComboBoxModel<String>(new String[] { "LL(1)", "SLR(1)", "LR(1)", "LALR(1)" }));
		this.añadirPanel.add(this.añadirBox);

		this.añadirDerechoStrut = Box.createHorizontalStrut(70);
		this.añadirPanel.add(this.añadirDerechoStrut);
	}

	private void initVistaPreviaPanel() {
		this.vistaPreviaPanel = new JPanel();
		this.frmPlgram.getContentPane().add(this.vistaPreviaPanel, BorderLayout.CENTER);
		this.vistaPreviaPanel.setLayout(new BorderLayout(0, 0));

		this.vistaPreviaScroll = new JScrollPane();
		this.vistaPreviaPanel.add(this.vistaPreviaScroll);

		this.vistaPreviaText = new JTextPane();
		this.vistaPreviaText.setEditable(false);
		this.vistaPreviaText.setContentType("text/html;charset=UTF-8");

		this.vistaPreviaScroll.add(this.vistaPreviaText);
		this.vistaPreviaScroll.setViewportView(this.vistaPreviaText);
		contenedorScroll.getVerticalScrollBar().addAdjustmentListener(new ScrollbarContenedorListener());
		vistaPreviaScroll.getVerticalScrollBar().addAdjustmentListener(new ScrollbarVistaPreviaListener());

	}

	private class MenuExportarButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			
			FileDialog dialogoArchivo;
			Frame f = null;
			///TODO
			dialogoArchivo = new FileDialog(f, "Guardar", FileDialog.SAVE);
			dialogoArchivo.setVisible(true);
		
			JMenuItem source = (JMenuItem) event.getSource();
			
			if (source == menuExportarMoodleXMLButton){
				System.out.println("ExportarMoodleXMLButton");
				
			}else{
				System.out.println("ExportarLatexButton");
			}
			      
			
			int valorRetorno = fileChooser.showSaveDialog(frmPlgram);
			if (valorRetorno == JFileChooser.APPROVE_OPTION) {
				//File fichero = fileChooser.getSelectedFile();
				if (source == menuExportarMoodleXMLButton) {
					// .exportaXML(fichero);
				} else if (source == menuExportarLatexButton) {
					// documento().exportaLatex(fichero);
				}
			}
		}
	}



	private class MenuWebActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			try {
				Desktop.getDesktop().browse(new URI("https://github.com/vrt0004/Trabajo"));
			} catch (IOException | URISyntaxException e) {

			}
		}
	}

	private class MenuAcercaDeActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			JOptionPane.showMessageDialog(frmPlgram,
					"PLGRAM\n" + "TFG del Grado en Ingeniería Informática\n"
							+ "Escuela Politécnica Superior, Universidad de Burgos\n"
							+ "Presentado en Julio de 2016\n\n" + "Autor: Victor Renuncio Tobar\n"
							+ "Tutor: Dr. Cesar Ignacio García Osorio",
					"Acerca de", JOptionPane.PLAIN_MESSAGE);
		}
	}

	private class AddButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (añadirBox.getSelectedItem().equals("LL(1)")) {
				añadeLL(null);
			} else if (añadirBox.getSelectedItem().equals("SLR(1)")) {
				añadeSLR(null);
			} else if (añadirBox.getSelectedItem().equals("LR(1)")) {
				añadeLR(null);
			} else if (añadirBox.getSelectedItem().equals("LALR(1)")) {
				añadeLALR(null);
			}
		}
	}

	private class MenuLLActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			añadeLL(null);
		}
	}

	private class MenuLRActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			añadeLR(null);
		}
	}

	private class MenuSLRActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			añadeSLR(null);
		}
	}

	private class MenuLALRActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			añadeLALR(null);
		}
	}

	private class ScrollbarContenedorListener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			if (!scrollContenedor)
				e.getAdjustable().setValue(e.getAdjustable().getMaximum());
			scrollContenedor = true;
		}
	}

	private class ScrollbarVistaPreviaListener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			if (!scrollVistaPrevia)
				e.getAdjustable().setValue(e.getAdjustable().getMaximum());
			scrollVistaPrevia = true;
		}
	}

	void añadeLL(Problema<LL1> problema) {
		LLPanel panel = new LLPanel(this, contenedorPanel, this.panelesProblema.size() + 1);
		scrollContenedor = false;
		if (problema != null)
			panel.problema(problema);
		contenedorPanel.add(panel);
		panelesProblema.add(panel);
		contenedorPanel.revalidate();
	}

	void añadeLALR(Problema<LALR1> problema) {
		LALRPanel panel = new LALRPanel(this, contenedorPanel, this.panelesProblema.size() + 1);
		scrollContenedor = false;
		if (problema != null)
			panel.problema(problema);
		contenedorPanel.add(panel);
		panelesProblema.add(panel);
		contenedorPanel.revalidate();

	}

	void añadeSLR(Problema<SLR1> problema) {
		SLRPanel panel = new SLRPanel(this, contenedorPanel, this.panelesProblema.size() + 1);
		scrollContenedor = false;
		if (problema != null)
			panel.problema(problema);
		contenedorPanel.add(panel);
		panelesProblema.add(panel);
		contenedorPanel.revalidate();

	}

	void añadeLR(Problema<LR1> problema) {
		LRPanel panel = new LRPanel(this, contenedorPanel, this.panelesProblema.size() + 1);
		scrollContenedor = false;
		if (problema != null)
			panel.problema(problema);
		contenedorPanel.add(panel);
		panelesProblema.add(panel);
		contenedorPanel.revalidate();

	}

	void eliminaMarca() {
		for (ProblemaPanel<?> panel : this.panelesProblema)
			panel.eliminarVista();
	}


	public void moverProblemaArriba(ProblemaPanel<?> problema) {
		int index = this.panelesProblema.indexOf(problema);
		if (index > 0) {

			this.panelesProblema.remove(problema);
			this.panelesProblema.add(index - 1, problema);

			contenedorPanel.removeAll();
			int num = 1;
			for (ProblemaPanel<?> panel : this.panelesProblema) {
				contenedorPanel.add(panel);
				if (panel.problemaActual != null)
					panel.problemaActual.setNumero(num++);
			}
			contenedorPanel.revalidate();
		}

	}

	public void moverProblemaAbajo(ProblemaPanel<?> problema) {
		int index = this.panelesProblema.indexOf(problema);
		if (index < this.panelesProblema.size() - 1) {

			this.panelesProblema.remove(problema);
			this.panelesProblema.add(index + 1, problema);

			contenedorPanel.removeAll();
			int num = 1;
			for (ProblemaPanel<?> panel : this.panelesProblema) {
				contenedorPanel.add(panel);
				if (panel.problemaActual != null)
					panel.problemaActual.setNumero(num++);
			}
			contenedorPanel.revalidate();
			actualizaVistaPrevia(problema.problemaActual);
		}
	}

	public void actualizaVistaPrevia(Object object) {
		// TODO Auto-generated method stub
		
	}

}
