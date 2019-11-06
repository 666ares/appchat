package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

public class PrincipalView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	public PrincipalView() {
		setTitle("AppChat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel labelAppChat = new JLabel("Bienvenido al Chat");
		labelAppChat.setFont(new Font("Arial", Font.PLAIN, 30));
		labelAppChat.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(labelAppChat, BorderLayout.CENTER);
	}
	
}
