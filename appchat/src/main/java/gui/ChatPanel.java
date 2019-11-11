package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class ChatPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ChatPanel(String nombreUser, String lastMensaje) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBounds(0, 0, 250, 80);
		setPreferredSize(new Dimension(250, 80));
		Border blackline = BorderFactory.createLineBorder(Color.black);
		setBorder(blackline);
		
		JLayeredPane layeredPane = new JLayeredPane();
		add(layeredPane);
		
		//Imagen del usuario
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 75, 80);
		layeredPane.add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		ImageIcon userPic = new ImageIcon("C:\\Users\\Juanjo\\Desktop\\Apuntes\\TDS\\proyecto\\appchat\\appchat\\icons\\profile_picture.png");
		Image newImage = userPic.getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
		ImageIcon userPic2 = new ImageIcon(newImage);
		JLabel imageLabel = new JLabel(userPic2);
		
		panel.add(imageLabel);
		
		//Información del usuario
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(89, 0, 161, 80);
		layeredPane.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		Component verticalStrut = Box.createVerticalStrut(20);
		panel_1.add(verticalStrut);
		
		JLabel lblUsuario = new JLabel(nombreUser);
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_1.add(lblUsuario);
		
		Component verticalStrut_2 = Box.createVerticalStrut(5);
		panel_1.add(verticalStrut_2);
		
		JLabel lblUltimoMensaje = new JLabel(lastMensaje);
		panel_1.add(lblUltimoMensaje);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		panel_1.add(verticalStrut_1);
	}
	
	public ChatPanel(ImageIcon userImage, String nombreUser, String lastMensaje) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JLayeredPane layeredPane = new JLayeredPane();
		add(layeredPane);
		
		//Imagen del usuario
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 75, 80);
		layeredPane.add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		Image newImage = userImage.getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
		ImageIcon userPic2 = new ImageIcon(newImage);
		JLabel imageLabel = new JLabel(userPic2);
		
		panel.add(imageLabel);
		
		//Información del usuario
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(89, 0, 161, 80);
		layeredPane.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		Component verticalStrut = Box.createVerticalStrut(20);
		panel_1.add(verticalStrut);
		
		JLabel lblUsuario = new JLabel(nombreUser);
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_1.add(lblUsuario);
		
		Component verticalStrut_2 = Box.createVerticalStrut(5);
		panel_1.add(verticalStrut_2);
		
		JLabel lblUltimoMensaje = new JLabel(lastMensaje);
		panel_1.add(lblUltimoMensaje);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		panel_1.add(verticalStrut_1);
	}
	
	
}
