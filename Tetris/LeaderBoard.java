package Tetris;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LeaderBoard extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel[] lblNameLabel, lblScoreLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			LeaderBoard dialog = new LeaderBoard();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public LeaderBoard() {
		setTitle("LeaderBoard");
		setResizable(false);
		setBounds(100, 100, 250, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 3, 0, 0));
		
		//왼쪽위부터 (0, 0) ~ (2, 11)
		JLabel[] lblBrankLabel = new JLabel[] {new JLabel(""), new JLabel("LeaderBoard"), new JLabel(""),
											   new JLabel(""), new JLabel(""), new JLabel("")}; int bc = 0;
		
		JLabel[] lblRankLabel = new JLabel[] {new JLabel("1st"), new JLabel("2nd"),
									new JLabel("3rd"), new JLabel("4th"),
									new JLabel("5th"), new JLabel("6th"),
									new JLabel("7th"), new JLabel("8th"),
									new JLabel("9th"), new JLabel("10th")}; int rc = 0;
											   
		lblNameLabel = new JLabel[] {new JLabel("1st Name"), new JLabel("2nd Name"),
									new JLabel("3rd Name"), new JLabel("4th Name"),
									new JLabel("5th Name"), new JLabel("6th Name"),
									new JLabel("7th Name"), new JLabel("8th Name"),
									new JLabel("9th Name"), new JLabel("10th Name")}; int nc = 0;
											  
		lblScoreLabel = new JLabel[] {new JLabel("1st Score"), new JLabel("2nd Score"),
									new JLabel("3rd Score"), new JLabel("4th Score"),
									new JLabel("5th Score"), new JLabel("6th Score"),
									new JLabel("7th Score"), new JLabel("8th Score"),
									new JLabel("9th Score"), new JLabel("10th Score")}; int sc = 0;
											   
									for (int i = 0; i < 10; i++) {
										lblRankLabel[i].setHorizontalTextPosition(SwingConstants.CENTER);
										lblRankLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
										lblNameLabel[i].setHorizontalTextPosition(SwingConstants.CENTER);
										lblNameLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
										lblScoreLabel[i].setHorizontalTextPosition(SwingConstants.CENTER);
										lblScoreLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
									}
		
		for(int r = 0; r < 12; r++){
			for(int c = 0; c < 3; c++) {
				switch(c) {
					case 0:
						if(r == 0 || r == 11) {
							contentPanel.add(lblBrankLabel[bc]);
							bc++;
							break;
							}
						contentPanel.add(lblRankLabel[rc]);
						rc++;
						break;
					case 1:
						if(r == 0 || r == 11) {
							contentPanel.add(lblBrankLabel[bc]);
							bc++;
							break;
							}
						contentPanel.add(lblNameLabel[nc]);
						nc++;
						break;
					case 2:
						if(r == 0 || r == 11) {
							contentPanel.add(lblBrankLabel[bc]);
							bc++;
							break;
							}
						contentPanel.add(lblScoreLabel[sc]);
						sc++;
						break;
					default:
						break;
				}
			}
		}
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton closeButton = new JButton("Close");
				closeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				closeButton.setActionCommand("Close");
				buttonPane.add(closeButton);
			}
		}
	}
	
	public JLabel[] getNameLabel() {
		return lblNameLabel;
	}
	
	public JLabel[] getScoreLabel() {
		return lblScoreLabel;
	}
}
