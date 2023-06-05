package Tetris;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

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
		setTitle("점수판");
		setResizable(false);
		setBounds(100, 100, 250, 430);
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
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				{
					GridBagLayout gbl_buttonPane = new GridBagLayout();
					gbl_buttonPane.columnWidths = new int[] {30, 0, 30};
					gbl_buttonPane.rowHeights = new int[] {20, 20, 20, 20, 0};
					gbl_buttonPane.columnWeights = new double[]{0.0, 1.0, 0.0};
					gbl_buttonPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
					buttonPane.setLayout(gbl_buttonPane);
					JButton resetButton = new JButton("기록 초기화");
					resetButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								MyTetris.function.makeLeaderBoard();
								dispose();
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					});
					GridBagConstraints gbc_resetButton = new GridBagConstraints();
					gbc_resetButton.fill = GridBagConstraints.VERTICAL;
					gbc_resetButton.insets = new Insets(0, 0, 5, 5);
					gbc_resetButton.gridx = 1;
					gbc_resetButton.gridy = 0;
					buttonPane.add(resetButton, gbc_resetButton);
					JButton closeButton = new JButton("닫기");
					closeButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							dispose();
						}
					});
					closeButton.setActionCommand("Close");
					GridBagConstraints gbc_closeButton = new GridBagConstraints();
					gbc_closeButton.insets = new Insets(0, 0, 5, 5);
					gbc_closeButton.fill = GridBagConstraints.VERTICAL;
					gbc_closeButton.gridx = 1;
					gbc_closeButton.gridy = 2;
					buttonPane.add(closeButton, gbc_closeButton);
				}
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
