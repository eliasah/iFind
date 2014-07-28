package engine.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class MainUI {

	private JFrame frmIfind;
	private SearchBar searchbar;
	private AdvSearchBar adsearchbar;
	private JToggleButton tglbtnAdvancedSearch;
	private boolean advanced;
	private Container panel_north;
	private Container user_advanced;
	private Container panel;
	private ResultTable tableres;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUI window = new MainUI();
					window.frmIfind.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public MainUI() {
		initialize();
	}

	private void initialize() {
		frmIfind = new JFrame();
		frmIfind.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\Tools\\workspace\\ifind\\config\\search-icon.png"));
		frmIfind.setTitle("iFind");
		frmIfind.setBounds(100, 100, 576, 703);
		frmIfind.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		advanced = false;
		
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
				advanced = abstractButton.getModel().isSelected();
				// System.out.println("Action - advanced=" + advanced + "\n");
				if (!advanced){
					searchbar.setVisible(true);
					adsearchbar.setVisible(false);
					tglbtnAdvancedSearch.setText("Advanced Search");
				}
				else {
					searchbar.setVisible(false);
					adsearchbar.setVisible(true);
					tglbtnAdvancedSearch.setText("Basic Search");
				}
			}

		};
		
		JMenuBar menuBar = new JMenuBar();
		frmIfind.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmQuitter = new JMenuItem("Quitter");
		mntmQuitter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
			}
		});
		mnFile.add(mntmQuitter);
		
		JMenu mnEdition = new JMenu("Edition");
		menuBar.add(mnEdition);
		
		JMenu mnAide = new JMenu("Aide");
		menuBar.add(mnAide);
		
		// Definition de la barre de recherche basique
		searchbar = new SearchBar();
		searchbar.setBorder(new LineBorder(new Color(0, 0, 0)));
		searchbar.setBounds(12, 13, 535, 54);
		
		// Definition de la barre de recherche avancee.
		// Non visible par défaut
		adsearchbar = new AdvSearchBar();
		adsearchbar.setBorder(new LineBorder(new Color(0, 0, 0)));
		adsearchbar.setBounds(12, 13, 535, 54);
		adsearchbar.setVisible(false);
		
		frmIfind.getContentPane().setLayout(null);
		frmIfind.getContentPane().add(searchbar);
		frmIfind.getContentPane().add(adsearchbar);
		
		tglbtnAdvancedSearch = new JToggleButton("Advanced Search");
		tglbtnAdvancedSearch.setBounds(397, 592, 150, 25);
		frmIfind.getContentPane().add(tglbtnAdvancedSearch);
		tglbtnAdvancedSearch.setVerticalAlignment(SwingConstants.BOTTOM);
		
		tableres = new ResultTable();
		FlowLayout flowLayout = (FlowLayout) tableres.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		tableres.setBounds(12, 80, 535, 507);
		frmIfind.getContentPane().add(tableres);
		
		tglbtnAdvancedSearch.addActionListener(actionListener);
	}
}