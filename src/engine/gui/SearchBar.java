package engine.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** Cette classe definit une barre de recherche basique
 * 
 * @author Abou Haydar Elias - Univ. Paris Denis Diderot
 *
 */
public class SearchBar extends JPanel {
	public SearchBar() {

		JTextField txtsearch = new JTextField();
		txtsearch.setLocation(12,12);
		txtsearch.setSize(364, 31);
		add(txtsearch);

		JButton btnsearch = new JButton("Rechercher");
		btnsearch.setBounds(388, 15, 131, 25);
		btnsearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Connect Search Bar
			}
		});
		setLayout(null);
		add(btnsearch);
	}
}