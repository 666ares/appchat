package gui.elements;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class MenuOpciones extends JPopupMenu {

	private static final long serialVersionUID = 1L;
	
	public MenuOpciones() {
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		setBounds(150, 150, 115, 200);
		setPreferredSize(new Dimension(115, 200));
		
		add(new JMenuItem(new AbstractAction("Añadir Contacto") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		}));
	}

}
