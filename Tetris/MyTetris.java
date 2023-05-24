package Tetris;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MyTetris extends JFrame {

	private JPanel contentPane;
	public static TetrisCanvas tetrisCanvas;
	private static JLabel lblScoreLabel;
	private static JMenuItem mntmNewMenuItem;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyTetris frame = new MyTetris();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MyTetris() {
		setResizable(false);
		setTitle("테트리스");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 600);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("게임");
		menuBar.add(mnNewMenu);
		
		mntmNewMenuItem = new JMenuItem("시작");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tetrisCanvas.start();
				mntmNewMenuItem.setEnabled(false);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("종료");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		tetrisCanvas = new TetrisCanvas();
		FlowLayout flowLayout = (FlowLayout) tetrisCanvas.getLayout();
		flowLayout.setVgap(15);
		flowLayout.setHgap(15);
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(tetrisCanvas, BorderLayout.CENTER);
		
		lblScoreLabel = new JLabel("Score");
		tetrisCanvas.add(lblScoreLabel);
	}

	public TetrisCanvas getTetrisCanvas() {
		return tetrisCanvas;
	}
	public static JLabel getLblScoreLabel() {
		return lblScoreLabel;
	}
	public static JMenuItem getMntmNewMenuItem() {
		return mntmNewMenuItem;
	}
}
