package gui.elements;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class BoxChat extends JButton {

	private static final long serialVersionUID = 1L;

	private String contacto;
	
	public BoxChat(String nombreUser, String lastMensaje) {
		
		// Constructor como un horizontalBox
		super();
		
		this.contacto = nombreUser;
		
		Box chat = new Box(BoxLayout.LINE_AXIS);
		
		chat.setPreferredSize(new Dimension(277, 65));
		chat.setMinimumSize(new Dimension(277, 65));
		

		// Imagen por defecto
		ImageIcon userPic = new ImageIcon("icons/profile_picture.png");
		Image newImage = userPic.getImage().getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH);
		ImageIcon userPic2 = new ImageIcon(newImage);
		JLabel imagelabel = new JLabel(userPic2);
		
		/* 
		 * Mover la imagen, el nombre y el último mensaje un poco hacia
		 * la derecha, para que no esté tan pegado al borde 
		 * */
		chat.add(Box.createHorizontalStrut(8));
		chat.add(imagelabel);
		chat.add(Box.createHorizontalStrut(5));
		
		Box userinfo = Box.createVerticalBox();
		
		JLabel label = new JLabel(nombreUser);
		label.setFont(new Font("Arial", Font.BOLD, 13));
		userinfo.add(label);
		
		Component verticalStrut = Box.createVerticalStrut(6);
		userinfo.add(verticalStrut);
		JLabel label2 = new JLabel(lastMensaje);
		label2.setFont(new Font("Arial", Font.PLAIN, 12));
		userinfo.add(label2);
		
		chat.add(userinfo);	
		
		add(chat);
		Border blackline = BorderFactory.createLineBorder(Color.BLACK);
		setBorder(blackline);
		
		setOpaque(false);
		setContentAreaFilled(false);
		setFocusPainted(false);
	}
	
	public BoxChat(ImageIcon userPic, String nombreUser, String lastMensaje) {
		
		// Constructor como un horizontalBox
		super();
		
		Box chat = new Box(BoxLayout.LINE_AXIS);
		
		chat.setPreferredSize(new Dimension(277, 65));
		chat.setMinimumSize(new Dimension(277, 65));
		
		Image newImage = userPic.getImage().getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH);
		ImageIcon userPic2 = new ImageIcon(newImage);
		JLabel imagelabel = new JLabel(userPic2);
		
		chat.add(Box.createHorizontalStrut(8));
		chat.add(imagelabel);
		chat.add(Box.createHorizontalStrut(5));
		
		Box userinfo = Box.createVerticalBox();
		JLabel label = new JLabel(nombreUser);
		label.setFont(new Font("Arial", Font.BOLD, 13));
		userinfo.add(label);
		
		Component verticalStrut = Box.createVerticalStrut(6);
		userinfo.add(verticalStrut);
		JLabel label2 = new JLabel(lastMensaje);
		label2.setFont(new Font("Arial", Font.PLAIN, 12));
		userinfo.add(label2);
		chat.add(userinfo);	
		
		add(chat);
		Border blackline = BorderFactory.createLineBorder(Color.BLACK);
		setBorder(blackline);
		
		setOpaque(false);
		setContentAreaFilled(false);
		setFocusPainted(false);
	}

	public void setResaltar(boolean ver) {
		
		if (ver) {
			setOpaque(true);
			setContentAreaFilled(true);
			setFocusPainted(true);
			setBackground(Color.GRAY);
		} else {
			setOpaque(false);
			setContentAreaFilled(false);
			setFocusPainted(false);
		}
	}
	
	public String getContacto() {
		return this.contacto;
	}
	
}
