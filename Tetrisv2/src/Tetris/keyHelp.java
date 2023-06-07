package Tetris;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Cursor;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class keyHelp extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			keyHelp dialog = new keyHelp();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public keyHelp() {
		setResizable(false);
		setTitle("도움말");
		setBounds(100, 100, 600, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 4, 0, 0));
		{
			JLabel lblNewLabel = new JLabel("←, → - 좌우 이동");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("↑, 'z' - 회전");
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblNewLabel_1);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("↓ - 아래로 이동");
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblNewLabel_2);
		}
		{
			JLabel lblNewLabel_3 = new JLabel("'c' - 홀드");
			lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblNewLabel_3);
		}
		{
			JLabel lblNewLabel_4 = new JLabel("'a' - 아이템1");
			lblNewLabel_4.setToolTipText("");
			lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblNewLabel_4);
		}
		{
			JTextPane textPane = new JTextPane();
			textPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			textPane.setEditable(false);
			textPane.setFont(new Font("굴림", Font.PLAIN, 11));
			textPane.setText("맨 밑 1줄을 삭제한다.\r\n(5,000점 소모)");
			contentPanel.add(textPane);
		}
		{
			JLabel lblNewLabel_5 = new JLabel("'s' - 아이템2");
			lblNewLabel_5.setToolTipText("");
			lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblNewLabel_5);
		}
		{
			JTextPane textPane = new JTextPane();
			textPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			textPane.setEditable(false);
			textPane.setFont(new Font("굴림", Font.PLAIN, 11));
			textPane.setText("현재 모양을 랜덤하게 바꾼다.\r\n(동일한 모양 출현 가능)\r\n(1,000점 소모)");
			contentPanel.add(textPane);
		}
		{
			JLabel lblNewLabel_6 = new JLabel("'d' - 아이템3");
			lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblNewLabel_6);
		}
		{
			JTextPane textPane = new JTextPane();
			textPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			textPane.setFont(new Font("굴림", Font.PLAIN, 11));
			textPane.setEditable(false);
			textPane.setText("상대방의 다음 모양을\r\n이상한 모양으로 바꾼다.\r\n(멀티한정, 4종 중 랜덤)\r\n(15,000점 소모)");
			contentPanel.add(textPane);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton closeButton = new JButton("닫기");
				closeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				closeButton.setActionCommand("Cancel");
				buttonPane.add(closeButton);
			}
		}
	}

}
