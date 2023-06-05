package Tetris;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RecordScore extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textNameField;
	private JTextField textScoreField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RecordScore dialog = new RecordScore();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RecordScore() {
		setResizable(false);
		setTitle("RecordScore");
		setBounds(100, 100, 180, 120);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 2, 0, 0));
		{
			JLabel lblNameLabel = new JLabel("Name");
			lblNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblNameLabel);
		}
		{
			textNameField = new JTextField();
			textNameField.setHorizontalAlignment(SwingConstants.CENTER);
			textNameField.setText("Your Name");
			contentPanel.add(textNameField);
			textNameField.setColumns(10);
		}
		{
			JLabel lblScoreLabel = new JLabel("Your Score");
			lblScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblScoreLabel);
		}
		{
			textScoreField = new JTextField();
			textScoreField.setHorizontalAlignment(SwingConstants.CENTER);
			textScoreField.setEditable(false);
			textScoreField.setEnabled(false);
			contentPanel.add(textScoreField);
			textScoreField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton recordButton = new JButton("Record");
				recordButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							MyTetris.function.recordLeaderBoard(Integer.parseInt(textScoreField.getText()), textNameField.getText());
							dispose();
							MyTetris.getGameOver().dispose();
						} catch (Exception e1) {
							e1.printStackTrace();
							dispose();
							MyTetris.getGameOver().dispose();
						}
					}
				});
				recordButton.setActionCommand("OK");
				buttonPane.add(recordButton);
				getRootPane().setDefaultButton(recordButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public JTextField getScoreField() {
		return textScoreField;
	}
}
