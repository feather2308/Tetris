package Tetris;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EnemyScore extends JDialog { //상대방점수 다이얼로그

	private final JPanel contentPanel = new JPanel();
	private JLabel meLabel;
	private JLabel enemyLabel;
	private JLabel meScoreLabel;
	private JLabel winLabel;
	private JLabel enemyScoreLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			EnemyScore dialog = new EnemyScore();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public EnemyScore() {
		setResizable(false);
		setTitle("Who is the Winner?");
		setBounds(100, 100, 280, 120);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(2, 3, 0, 0));
		{
			meLabel = new JLabel("Me");
			meLabel.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(meLabel);
		}
		{
			JLabel vsLabel = new JLabel("vs");
			vsLabel.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(vsLabel);
		}
		{
			enemyLabel = new JLabel("Enemy");
			enemyLabel.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(enemyLabel);
		}
		{
			meScoreLabel = new JLabel("Me Score");
			meScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(meScoreLabel);
		}
		{
			winLabel = new JLabel("<< Win >>");
			winLabel.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(winLabel);
		}
		{
			enemyScoreLabel = new JLabel("Enemy Score");
			enemyScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(enemyScoreLabel);
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
		}
	}

	public JLabel getMeLabel() {
		return meLabel;
	}
	public JLabel getEnemyLabel() {
		return enemyLabel;
	}
	public JLabel getMeScoreLabel() {
		return meScoreLabel;
	}
	public JLabel getWinLabel() {
		return winLabel;
	}
	public JLabel getEnemyScoreLabel() {
		return enemyScoreLabel;
	}
}
