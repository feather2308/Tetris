package Tetris;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class OpenServer extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textIPField;
	private JTextField textPortField;
	int port;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			OpenServer dialog = new OpenServer();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public OpenServer() {
		setResizable(false);
		setTitle("Server Open");
		setBounds(100, 100, 235, 150);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] {0, 30, 60, 30, 0};
		gbl_contentPanel.rowHeights = new int[] {0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblIPLabel = new JLabel("IP");
			GridBagConstraints gbc_lblIPLabel = new GridBagConstraints();
			gbc_lblIPLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblIPLabel.gridx = 0;
			gbc_lblIPLabel.gridy = 0;
			contentPanel.add(lblIPLabel, gbc_lblIPLabel);
		}
		{
			textIPField = new JTextField();
			try {
				textIPField.setText(InetAddress.getLocalHost().getHostAddress());
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			textIPField.setEditable(false);
			GridBagConstraints gbc_textIPField = new GridBagConstraints();
			gbc_textIPField.gridwidth = 3;
			gbc_textIPField.insets = new Insets(0, 0, 5, 5);
			gbc_textIPField.fill = GridBagConstraints.HORIZONTAL;
			gbc_textIPField.gridx = 2;
			gbc_textIPField.gridy = 0;
			contentPanel.add(textIPField, gbc_textIPField);
			textIPField.setColumns(10);
		}
		{
			JLabel lblPortLabel = new JLabel("Port");
			GridBagConstraints gbc_lblPortLabel = new GridBagConstraints();
			gbc_lblPortLabel.insets = new Insets(0, 0, 0, 5);
			gbc_lblPortLabel.gridx = 0;
			gbc_lblPortLabel.gridy = 1;
			contentPanel.add(lblPortLabel, gbc_lblPortLabel);
		}
		{
			textPortField = new JTextField();
			textPortField.setText("30000");
			GridBagConstraints gbc_textPortField = new GridBagConstraints();
			gbc_textPortField.insets = new Insets(0, 0, 0, 5);
			gbc_textPortField.gridwidth = 3;
			gbc_textPortField.fill = GridBagConstraints.HORIZONTAL;
			gbc_textPortField.gridx = 2;
			gbc_textPortField.gridy = 1;
			contentPanel.add(textPortField, gbc_textPortField);
			textPortField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton openButton = new JButton("Open");
				openButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						port = Integer.parseInt(textPortField.getText());
						MyTetris.serverOpen = true;
						dispose();
					}
				});
				buttonPane.add(openButton);
				getRootPane().setDefaultButton(openButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						MyTetris.serverOpen = false;
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
