package Tetris;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GameOver extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblScoreLabel;
	
	private final RecordScore recordScore = new RecordScore();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			GameOver dialog = new GameOver();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public GameOver() {
		setResizable(false);
		setTitle("GameOver");
		setBounds(100, 100, 210, 120);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel lblTextLabel = new JLabel("게임 끝");
			lblTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblTextLabel, BorderLayout.CENTER);
		}
		{
			lblScoreLabel = new JLabel("점수");
			lblScoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
			contentPanel.add(lblScoreLabel, BorderLayout.SOUTH);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("점수 기록하기");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						recordScore.setVisible(true);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public JLabel getScoreLabel() {
		return lblScoreLabel;
	}
	
	public RecordScore getRecordScore() {
		return recordScore;
	}
}
