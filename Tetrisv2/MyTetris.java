package Tetris;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.CardLayout;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.io.*;
import java.net.*;
import java.util.*;

public class MyTetris extends JFrame {	
	final static char gjr = '헉', huk = '헉', 헉 = '헉';
	
	private static TetrisCanvas tetrisCanvas;
	private static MultiTetrisCanvas multiTetrisCanvas;
	private static JMenuItem mntmStartMenuItem;
	private static JLabel lblScoreLabel;
	private static JLabel lblLineLabel;
	private static JLabel lblLevelLabel;
	

	private static JCheckBox lobbyServerConnectCheckBox;
	private static JCheckBox lobbyClientConnectCheckBox;
	private static JCheckBox lobbyServerReadyCheckBox;
	private static JCheckBox lobbyClientReadyCheckBox;
	private static JCheckBox lobbyAttackCheckBox;
	private static JCheckBox lobbyItemCheckBox;
	
	private JLabel nextPieceLabel;
	private JLabel holdPieceLabel;
	
	protected static boolean connect = false;
	protected static boolean serverOpen = false;
	protected static boolean canChange = true;

	private final OpenServer openServer = new OpenServer(); {
		openServer.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		openServer.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				doOpen();
			}
		});
	}
	
	private final ConnectServer connectServer = new ConnectServer(); {
		connectServer.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		connectServer.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				doConnect();
			}
		});
	}
	
	private final EnemyScore enemyScore = new EnemyScore();
	private static final LeaderBoard leaderBoard = new LeaderBoard();
	private static final GameOver gameOver = new GameOver();
	
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

	public MyTetris() {
		setBounds(100, 100, 350, 500);
		renderUIBase();
	}

	public static TetrisCanvas getTetrisCanvas() {
		return tetrisCanvas;
	}
	public static JLabel getLblScoreLabel() {
		return lblScoreLabel;
	}
	public static JLabel getLblLineLabel() {
		return lblLineLabel;
	}
	public static JLabel getLblLevelLabel() {
		return lblLevelLabel;
	}
	public static JMenuItem getMntmMenuItem() {
		return mntmStartMenuItem;
	}
	public static LeaderBoard getLeaderBoard() {
		return leaderBoard;
	}
	public static GameOver getGameOver() {
		return gameOver;
	}
	public static JCheckBox getUConnectCheckBox() {
		if(!serverOpen) return lobbyServerConnectCheckBox;
		else return lobbyClientConnectCheckBox;
	}
	public static JCheckBox getReadyCheckBox() {
		if(serverOpen) return lobbyServerReadyCheckBox;
		else return lobbyClientReadyCheckBox;
	}
	public static JCheckBox getUReadyCheckBox() {
		if(!serverOpen) return lobbyServerReadyCheckBox;
		else return lobbyClientReadyCheckBox;
	}
	public static JCheckBox getAttackCheckBox() {
		return lobbyAttackCheckBox;
	}
	public static JCheckBox getItemCheckBox() {
		return lobbyItemCheckBox;
	}
	
	/*
	
	UI 시작 
	
	*/
	
	private void renderUIBase() {
		this.getContentPane().removeAll();

		setTitle("테트리스");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(getBounds().x, getBounds().y, 350, 500);
		
		JPanel baseCardContentPane = new JPanel();
		baseCardContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		this.setContentPane(baseCardContentPane);
		this.revalidate();
		baseCardContentPane.setLayout(new CardLayout(0, 0));
		
		CardLayout baseCardLayout =	(CardLayout)baseCardContentPane.getLayout();
		
		//
		// baseContentPane 시작
		//
		
		JPanel baseContentPane = new JPanel();
		baseContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		baseCardContentPane.add(baseContentPane, "Base");
		
		GridBagLayout gbl_baseContentPane = new GridBagLayout();
		GridBagConstraints gbc_baseTetrisText = new GridBagConstraints();
		gbc_baseTetrisText.gridwidth = 3;
		
		gbl_baseContentPane.columnWidths = new int[] {30, 70, 40, 70, 30};
		gbl_baseContentPane.rowHeights = new int[] {30, 0, 30, 0, 0, 0, 0, 30};
		gbl_baseContentPane.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl_baseContentPane.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		baseContentPane.setLayout(gbl_baseContentPane);
	
		JTextField baseTetrisText = new JTextField();
		baseTetrisText.setHorizontalAlignment(SwingConstants.CENTER);
		baseTetrisText.setText("TETRIS");

		gbc_baseTetrisText.insets = new Insets(0, 0, 5, 5);
		gbc_baseTetrisText.fill = GridBagConstraints.BOTH;
		gbc_baseTetrisText.gridx = 1;
		gbc_baseTetrisText.gridy = 1;
		baseContentPane.add(baseTetrisText, gbc_baseTetrisText);
		baseTetrisText.setColumns(10);
		GridBagConstraints gbc_baseSinglePlayButton = new GridBagConstraints();
		
		JButton baseSinglePlayButton = new JButton("혼자 놀기");
		baseSinglePlayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				renderUISingle();
			}
		});
		
		gbc_baseSinglePlayButton.fill = GridBagConstraints.BOTH;
		gbc_baseSinglePlayButton.insets = new Insets(0, 0, 5, 5);
		gbc_baseSinglePlayButton.gridx = 2;
		gbc_baseSinglePlayButton.gridy = 3;
		
		baseContentPane.add(baseSinglePlayButton, gbc_baseSinglePlayButton);
		GridBagConstraints gbc_baseMultiPlayButton = new GridBagConstraints();
		
				JButton baseMultiPlayButton = new JButton("같이 놀기");
				baseMultiPlayButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
	            baseCardLayout.show(baseCardContentPane, "Server");
						setTitle("테트리스 같이 놀기");
					}
				});
				
						gbc_baseMultiPlayButton.fill = GridBagConstraints.BOTH;
						gbc_baseMultiPlayButton.insets = new Insets(0, 0, 5, 5);
						gbc_baseMultiPlayButton.gridx = 2;
						gbc_baseMultiPlayButton.gridy = 4;
						baseContentPane.add(baseMultiPlayButton, gbc_baseMultiPlayButton);
		GridBagConstraints gbc_baseExitButton = new GridBagConstraints();
		gbc_baseExitButton.insets = new Insets(0, 0, 5, 5);
		
				JButton baseExitButton = new JButton("나가기");
				baseExitButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});
						
						JButton baseLeaderBoardButton = new JButton("점수판 보기");
						baseLeaderBoardButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								function.refreshLeaderBoard();
							}
						});
						GridBagConstraints gbc_baseLeaderBoardButton = new GridBagConstraints();
						gbc_baseLeaderBoardButton.fill = GridBagConstraints.HORIZONTAL;
						gbc_baseLeaderBoardButton.insets = new Insets(0, 0, 5, 5);
						gbc_baseLeaderBoardButton.gridx = 2;
						gbc_baseLeaderBoardButton.gridy = 5;
						baseContentPane.add(baseLeaderBoardButton, gbc_baseLeaderBoardButton);
				
						gbc_baseExitButton.fill = GridBagConstraints.BOTH;
						gbc_baseExitButton.gridx = 2;
						gbc_baseExitButton.gridy = 6;
						baseContentPane.add(baseExitButton, gbc_baseExitButton);

		//
		// baseContentPane 끝
		//
		// baseMultiContentPane 시작
		//
		
		JPanel baseMultiContentPane = new JPanel();
		baseMultiContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		baseCardContentPane.add(baseMultiContentPane, "Server");
		
		GridBagLayout gbl_baseServerContentPane = new GridBagLayout();
		GridBagConstraints gbc_baseServerSinglePlayButton = new GridBagConstraints();
		GridBagConstraints gbc_baseServerMultiPlayButton = new GridBagConstraints();
		
		gbl_baseServerContentPane.columnWidths = new int[] {30, 70, 40, 70, 30};
		gbl_baseServerContentPane.rowHeights = new int[] {30, 0, 30, 0, 0, 30, 0, 30};
		gbl_baseServerContentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
		gbl_baseServerContentPane.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		baseMultiContentPane.setLayout(gbl_baseServerContentPane);
		
		JButton baseServerSinglePlayButton = new JButton("서버 열기");
		baseServerSinglePlayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openServer.setVisible(true);
			}
		});

		JButton baseServerMultiPlayButton = new JButton("서버 접속하기");
		baseServerMultiPlayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connectServer.setVisible(true);
			}
		});
		
		gbc_baseServerSinglePlayButton.fill = GridBagConstraints.BOTH;
		gbc_baseServerSinglePlayButton.insets = new Insets(0, 0, 5, 5);
		gbc_baseServerSinglePlayButton.gridx = 2;
		gbc_baseServerSinglePlayButton.gridy = 3;

		gbc_baseServerMultiPlayButton.fill = GridBagConstraints.BOTH;
		gbc_baseServerMultiPlayButton.insets = new Insets(0, 0, 5, 5);
		gbc_baseServerMultiPlayButton.gridx = 2;
		gbc_baseServerMultiPlayButton.gridy = 4;
		GridBagConstraints gbc_baseServerTetrisText = new GridBagConstraints();
		gbc_baseServerTetrisText.gridwidth = 3;
		
			JTextField baseServerTetrisText = new JTextField();
			baseServerTetrisText.setHorizontalAlignment(SwingConstants.CENTER);
			baseServerTetrisText.setText("TETRIS");
			
					gbc_baseServerTetrisText.insets = new Insets(0, 0, 5, 5);
					gbc_baseServerTetrisText.fill = GridBagConstraints.BOTH;
					gbc_baseServerTetrisText.gridx = 1;
					gbc_baseServerTetrisText.gridy = 1;
					baseMultiContentPane.add(baseServerTetrisText, gbc_baseServerTetrisText);
					baseServerTetrisText.setColumns(10);
		
		baseMultiContentPane.add(baseServerSinglePlayButton, gbc_baseServerSinglePlayButton);
		baseMultiContentPane.add(baseServerMultiPlayButton, gbc_baseServerMultiPlayButton);
		GridBagConstraints gbc_baseServerBackButton = new GridBagConstraints();
		
				JButton baseServerBackButton = new JButton("뒤로가기");
				baseServerBackButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
	            baseCardLayout.show(baseCardContentPane, "Base");
						setTitle("테트리스");
					}
				});
				
						gbc_baseServerBackButton.fill = GridBagConstraints.BOTH;
						gbc_baseServerBackButton.insets = new Insets(0, 0, 0, 5);
						gbc_baseServerBackButton.gridx = 2;
						gbc_baseServerBackButton.gridy = 6;
						baseMultiContentPane.add(baseServerBackButton, gbc_baseServerBackButton);
	}
	
	private void renderUIBaseServerTab() {
		CardLayout baseCardLayout =	(CardLayout)this.getContentPane().getLayout();
		baseCardLayout.show(this.getContentPane(), "Server");
	}
	
	private void renderUISingle() {
		this.getContentPane().removeAll();
		
		setTitle("테트리스 혼자 놀기");
		setBounds(getBounds().x, getBounds().y, 380, 620);
		
		JPanel singleContentPane = new JPanel();
		singleContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		this.setContentPane(singleContentPane);
		this.revalidate();	
		singleContentPane.setLayout(new BorderLayout(0, 0));
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu gameMenu = new JMenu("게임");
		menuBar.add(gameMenu);
		
		mntmStartMenuItem = new JMenuItem("시작");
		mntmStartMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tetrisCanvas.start();
				mntmStartMenuItem.setEnabled(false);
			}
		});
		gameMenu.add(mntmStartMenuItem);
		
		JMenuItem mntmExitMenuItem = new JMenuItem("종료");
		mntmExitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		gameMenu.add(mntmExitMenuItem);
		
		tetrisCanvas = new TetrisCanvas();
		singleContentPane.add(tetrisCanvas, BorderLayout.CENTER);
		tetrisCanvas.setLayout(null);
		
		nextPieceLabel = new JLabel("-NEXT-");
		nextPieceLabel.setBounds(285, 40, 45, 15);
		nextPieceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tetrisCanvas.add(nextPieceLabel);
		
		holdPieceLabel = new JLabel("-HOLD-");
		holdPieceLabel.setBounds(285, 130, 44, 15);
		holdPieceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tetrisCanvas.add(holdPieceLabel);
		
		lblScoreLabel = new JLabel("Score");
		lblScoreLabel.setBounds(270, 475, 95, 15);
		lblScoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
		tetrisCanvas.add(lblScoreLabel);
		
		lblLineLabel = new JLabel("Line");
		lblLineLabel.setBounds(270, 495, 60, 15);
		lblLineLabel.setHorizontalAlignment(SwingConstants.LEFT);
		tetrisCanvas.add(lblLineLabel);
		
		lblLevelLabel = new JLabel("Level");
		lblLevelLabel.setBounds(270, 515, 60, 15);
		lblLevelLabel.setHorizontalAlignment(SwingConstants.LEFT);
		tetrisCanvas.add(lblLevelLabel);
	}
	
	
	private void renderUIMulti() {
		this.getContentPane().removeAll();
		
		setTitle("테트리스 같이 놀기");
		setBounds(getBounds().x, getBounds().y, 650, 600);
		
		JPanel multiContentPane = new JPanel();
		multiContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(multiContentPane);
		this.revalidate();	
		multiContentPane.setLayout(new BorderLayout(0, 0));
		
		tetrisCanvas = new TetrisCanvas();
		multiContentPane.add(tetrisCanvas, BorderLayout.CENTER);
		tetrisCanvas.setLayout(null);
		
		nextPieceLabel = new JLabel("-NEXT-");
		nextPieceLabel.setBounds(285, 40, 45, 15);
		nextPieceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tetrisCanvas.add(nextPieceLabel);
		
		holdPieceLabel = new JLabel("-HOLD-");
		holdPieceLabel.setBounds(285, 130, 44, 15);
		holdPieceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tetrisCanvas.add(holdPieceLabel);
		
		lblScoreLabel = new JLabel("Score");
		lblScoreLabel.setBounds(270, 475, 95, 15);
		lblScoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
		tetrisCanvas.add(lblScoreLabel);
		
		lblLineLabel = new JLabel("Line");
		lblLineLabel.setBounds(270, 495, 60, 15);
		lblLineLabel.setHorizontalAlignment(SwingConstants.LEFT);
		tetrisCanvas.add(lblLineLabel);
		
		lblLevelLabel = new JLabel("Level");
		lblLevelLabel.setBounds(270, 515, 60, 15);
		lblLevelLabel.setHorizontalAlignment(SwingConstants.LEFT);
		tetrisCanvas.add(lblLevelLabel);
		
		multiTetrisCanvas = new MultiTetrisCanvas();
		multiContentPane.add(multiTetrisCanvas, BorderLayout.EAST);
		multiTetrisCanvas.setLayout(null);
	}
	
	private void renderUILobby() {
		this.getContentPane().removeAll();
		
		JPanel lobbyContentPane;
		JTextField txtTextfieldIsGood;
		JLabel lobbyServerLabel;
		JLabel lobbyConnectLabel;
		JLabel lobbyReadyLabel;
		JCheckBox lobbyMiriCheckBox;
		JButton lobbyReadyButton;
		
		setTitle("서버 로비");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(getBounds().x, getBounds().y, 400, 250);
		lobbyContentPane = new JPanel();
		lobbyContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		this.setContentPane(lobbyContentPane);
		this.revalidate();
		GridBagLayout gbl_lobbyContentPane = new GridBagLayout();
		gbl_lobbyContentPane.columnWidths = new int[] {100, 120, 120};
		gbl_lobbyContentPane.rowHeights = new int[] {25, 25, 40, 40, 0, 0};
		gbl_lobbyContentPane.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl_lobbyContentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		lobbyContentPane.setLayout(gbl_lobbyContentPane);
		
		txtTextfieldIsGood = new JTextField();
		txtTextfieldIsGood.setHorizontalAlignment(SwingConstants.CENTER);
		txtTextfieldIsGood.setEditable(false);
		txtTextfieldIsGood.setText("textField is Good, But I Don't Like Design");
		GridBagConstraints gbc_txtTextfieldIsGood = new GridBagConstraints();
		gbc_txtTextfieldIsGood.fill = GridBagConstraints.BOTH;
		gbc_txtTextfieldIsGood.gridwidth = 3;
		gbc_txtTextfieldIsGood.insets = new Insets(0, 0, 5, 0);
		gbc_txtTextfieldIsGood.gridx = 0;
		gbc_txtTextfieldIsGood.gridy = 0;
		lobbyContentPane.add(txtTextfieldIsGood, gbc_txtTextfieldIsGood);
		txtTextfieldIsGood.setColumns(10);
		
		lobbyServerLabel = new JLabel("서버 라벨");
		GridBagConstraints gbc_lobbyServerLabel = new GridBagConstraints();
		gbc_lobbyServerLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lobbyServerLabel.gridx = 1;
		gbc_lobbyServerLabel.gridy = 1;
		lobbyContentPane.add(lobbyServerLabel, gbc_lobbyServerLabel);
		
		JLabel lobbyClientLabel = new JLabel("클라이언트 라벨");
		GridBagConstraints gbc_lobbyClientLabel = new GridBagConstraints();
		gbc_lobbyClientLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lobbyClientLabel.gridx = 2;
		gbc_lobbyClientLabel.gridy = 1;
		lobbyContentPane.add(lobbyClientLabel, gbc_lobbyClientLabel);
		
		lobbyConnectLabel = new JLabel("접속 라벨");
		GridBagConstraints gbc_lobbyConnectLabel = new GridBagConstraints();
		gbc_lobbyConnectLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lobbyConnectLabel.gridx = 0;
		gbc_lobbyConnectLabel.gridy = 2;
		lobbyContentPane.add(lobbyConnectLabel, gbc_lobbyConnectLabel);
		
		lobbyServerConnectCheckBox = new JCheckBox("");
		lobbyServerConnectCheckBox.setEnabled(false);
		GridBagConstraints gbc_lobbyServerConnectCheckBox = new GridBagConstraints();
		gbc_lobbyServerConnectCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_lobbyServerConnectCheckBox.gridx = 1;
		gbc_lobbyServerConnectCheckBox.gridy = 2;
		lobbyContentPane.add(lobbyServerConnectCheckBox, gbc_lobbyServerConnectCheckBox);
		
		lobbyClientConnectCheckBox = new JCheckBox("");
		lobbyClientConnectCheckBox.setEnabled(false);
		GridBagConstraints gbc_lobbyClientConnectCheckBox = new GridBagConstraints();
		gbc_lobbyClientConnectCheckBox.insets = new Insets(0, 0, 5, 0);
		gbc_lobbyClientConnectCheckBox.gridx = 2;
		gbc_lobbyClientConnectCheckBox.gridy = 2;
		lobbyContentPane.add(lobbyClientConnectCheckBox, gbc_lobbyClientConnectCheckBox);
		
		lobbyReadyLabel = new JLabel("준비 라벨");
		GridBagConstraints gbc_lobbyReadyLabel = new GridBagConstraints();
		gbc_lobbyReadyLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lobbyReadyLabel.gridx = 0;
		gbc_lobbyReadyLabel.gridy = 3;
		lobbyContentPane.add(lobbyReadyLabel, gbc_lobbyReadyLabel);
		
		lobbyServerReadyCheckBox = new JCheckBox("");
		lobbyServerReadyCheckBox.setEnabled(false);
		GridBagConstraints gbc_lobbyServerReadyCheckBox = new GridBagConstraints();
		gbc_lobbyServerReadyCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_lobbyServerReadyCheckBox.gridx = 1;
		gbc_lobbyServerReadyCheckBox.gridy = 3;
		lobbyContentPane.add(lobbyServerReadyCheckBox, gbc_lobbyServerReadyCheckBox);
		
		lobbyClientReadyCheckBox = new JCheckBox("");
		lobbyClientReadyCheckBox.setEnabled(false);
		GridBagConstraints gbc_lobbyClientReadyCheckBox = new GridBagConstraints();
		gbc_lobbyClientReadyCheckBox.insets = new Insets(0, 0, 5, 0);
		gbc_lobbyClientReadyCheckBox.gridx = 2;
		gbc_lobbyClientReadyCheckBox.gridy = 3;
		lobbyContentPane.add(lobbyClientReadyCheckBox, gbc_lobbyClientReadyCheckBox);
		
		lobbyReadyButton = new JButton("Ready");
		lobbyReadyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(serverOpen) {
					if(lobbyServerReadyCheckBox.isSelected() ? true : false) lobbyServerReadyCheckBox.setSelected(false);
					else lobbyServerReadyCheckBox.setSelected(true);
				} else {
					if(lobbyClientReadyCheckBox.isSelected() ? true : false) lobbyClientReadyCheckBox.setSelected(false);
					else lobbyClientReadyCheckBox.setSelected(true);
				}
			}
		});
		GridBagConstraints gbc_lobbyReadyButton = new GridBagConstraints();
		gbc_lobbyReadyButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_lobbyReadyButton.gridwidth = 3;
		gbc_lobbyReadyButton.insets = new Insets(0, 0, 5, 5);
		gbc_lobbyReadyButton.gridx = 0;
		gbc_lobbyReadyButton.gridy = 4;
		lobbyContentPane.add(lobbyReadyButton, gbc_lobbyReadyButton);
		
		lobbyAttackCheckBox = new JCheckBox("공격");
		GridBagConstraints gbc_lobbyAttackCheckBox = new GridBagConstraints();
		gbc_lobbyAttackCheckBox.insets = new Insets(0, 0, 0, 5);
		gbc_lobbyAttackCheckBox.gridx = 0;
		gbc_lobbyAttackCheckBox.gridy = 5;
		lobbyContentPane.add(lobbyAttackCheckBox, gbc_lobbyAttackCheckBox);
		
		lobbyItemCheckBox = new JCheckBox("아이템");
		GridBagConstraints gbc_lobbyItemCheckBox = new GridBagConstraints();
		gbc_lobbyItemCheckBox.insets = new Insets(0, 0, 0, 5);
		gbc_lobbyItemCheckBox.gridx = 1;
		gbc_lobbyItemCheckBox.gridy = 5;
		lobbyContentPane.add(lobbyItemCheckBox, gbc_lobbyItemCheckBox);
		
		lobbyMiriCheckBox = new JCheckBox("미리 보기");
		lobbyMiriCheckBox.setEnabled(false);
		GridBagConstraints gbc_lobbyMiriCheckBox = new GridBagConstraints();
		gbc_lobbyMiriCheckBox.gridx = 2;
		gbc_lobbyMiriCheckBox.gridy = 5;
		lobbyContentPane.add(lobbyMiriCheckBox, gbc_lobbyMiriCheckBox);
		
		
		lobbyServerConnectCheckBox.setSelected(serverOpen);
		lobbyClientConnectCheckBox.setSelected(!serverOpen);
		lobbyAttackCheckBox.setEnabled(serverOpen);
		lobbyItemCheckBox.setEnabled(serverOpen);
	}
	
	
	/*
	 
	서버 시작
	 
	 */
	
	public void doOpen() {
		if(serverOpen) {
			renderUILobby();
			serverHandler sh = new serverHandler(openServer.port);
			sh.start();
		}
	}
	
	public void doConnect() {
		if(connect) {
			renderUILobby();
			clientHandler ch = new clientHandler(connectServer.ip, connectServer.port);
			if(connect) ch.start();
		}
	}
	
	private class serverHandler extends Thread {
		private ServerSocket socket = null;
		private Socket client = null;
		private BufferedReader in = null;
		private PrintWriter out = null;
		
		public serverHandler(int port){
			try {
				socket = new ServerSocket(port);
			} catch (IOException e) {JOptionPane.showMessageDialog(null, e);}
		}
		
		public void run() {
			try {
				while(true) {
					System.out.println("헉runserver");
					
					client = socket.accept();
					MyTetris.getUConnectCheckBox().setSelected(true);
					
					in = new BufferedReader(new InputStreamReader(client.getInputStream()));
					out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
		
					String outStr;
		
					while(true) {
						outStr = (MyTetris.getReadyCheckBox().isSelected() ? 1 : 0) + "p" +
								 (MyTetris.getAttackCheckBox().isSelected() ? 1 : 0) + "p" +
								 (MyTetris.getItemCheckBox().isSelected() ? 1 : 0);
						out.println(outStr);
						out.flush();
			
						if(in.read() == 1) MyTetris.getUReadyCheckBox().setSelected(true);
						else MyTetris.getUReadyCheckBox().setSelected(false);
			
						if((MyTetris.getReadyCheckBox().isSelected() ? true : false) && (MyTetris.getUReadyCheckBox().isSelected() ? true : false)) break;
					}
					
					renderUIMulti();
					tetrisCanvas.start();
					
					outStr = "1p" + (MyTetris.getAttackCheckBox().isSelected() ? 1 : 0) + "p" + (MyTetris.getItemCheckBox().isSelected() ? 1 : 0);
					out.println(outStr);
					System.out.println(in.read());
					
					MyTetris.tetrisCanvas.data.setAttack(MyTetris.getAttackCheckBox().isSelected());
					
					try {function.handlerRun(in, out, enemyScore);} catch(Exception e) {System.out.println(e);}
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
				MyTetris.getUConnectCheckBox().setSelected(false);
				try {socket.close();} catch (IOException e1) {JOptionPane.showMessageDialog(null, e1);}
				renderUIBase();
				renderUIBaseServerTab();
			}
		}
	}
	
	private class clientHandler extends Thread {
		private Socket socket = null;
		private BufferedReader in = null;
		private PrintWriter out = null;
		
		public clientHandler(String IP, int port) {
			try {
				socket = new Socket(IP, port);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
				MyTetris.connect = false;
				renderUIBase();
				renderUIBaseServerTab();
			}
		}
		
		public void run() {
			System.out.println("헉run");
			try {
				MyTetris.getUConnectCheckBox().setSelected(connect);
				
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
				
				String inStr;
				String[] inStrFix = {""};
				
				while(true) {
					out.write(MyTetris.getReadyCheckBox().isSelected() ? 1 : 0);
					out.flush();
					
					inStr = in.readLine();
					
					if(inStr != null) {
						inStrFix = function.convertStringDiv(inStr);
						
						if(Integer.parseInt(inStrFix[0]) == 1) MyTetris.getUReadyCheckBox().setSelected(true);
						else MyTetris.getUReadyCheckBox().setSelected(false);
						MyTetris.getAttackCheckBox().setSelected(Integer.parseInt(inStrFix[1]) == 1 ? true : false);
						MyTetris.getItemCheckBox().setSelected(Integer.parseInt(inStrFix[2]) == 1 ? true : false);
					}
					
					if((MyTetris.getReadyCheckBox().isSelected() ? true : false) && (MyTetris.getUReadyCheckBox().isSelected() ? true : false)) 
						break;
				}
				
				renderUIMulti();
				tetrisCanvas.start();
				
				out.write(1);
				System.out.println(in.readLine());
				
				MyTetris.tetrisCanvas.data.setAttack(Integer.parseInt(inStrFix[1]) == 1 ? true : false);
				
				try {function.handlerRun(in, out, enemyScore);} catch(Exception e) {System.out.println(e);}
			} catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);
				MyTetris.connect = false;
				renderUIBase();
				renderUIBaseServerTab();
			}
		}
	}
	
	/*
	
	함수들 모음집
	 
	*/
	
	public class function {
		public static void handlerRun(BufferedReader in, PrintWriter out, EnemyScore enemyScore) throws IOException {
			boolean check = true;
			int tempALine = 0;
			
			//output 변수들
			int[][] data;
			int[] r, c;
			int death, score = 0, x, y, CPType, outLine;
			
			String outStr;
			
			//input 변수들
			int inDeath = 0;
			int[][] inData;
			int[] inCPr, inCPc;

			String inStr;
			String[] inStrFix = {""};
			
			while(true) {
				if(tetrisCanvas.stop) death = 1;
				else death = 0;
				score = tetrisCanvas.data.getScore();
				data = tetrisCanvas.data.getData();
				outLine = tetrisCanvas.data.getLine();
				
				if(tetrisCanvas.current != null) {
					r = tetrisCanvas.current.r;
					c = tetrisCanvas.current.c;
					x = tetrisCanvas.current.center.x;
					y = tetrisCanvas.current.center.y;
					CPType = tetrisCanvas.current.getType();
				}
				else {
					r = new int[] {0, 0, 0, 0};
					c = new int[] {0, 0, 0, 0};
					x = 0;
					y = 0;
					CPType = 8;
				}
				
				outStr = death + "p"									//tetrisCanvas.stop					Boolean 자료형 -> convert int 자료형
						+ score + "p"									//tetrisCanvas.data.getScore()		int 자료형
						+ function.convertIntArrayToString(data) + "p"	//tetrisCanvas.data.getData()		int[][] 자료형 즉, data의 data임.
						+ function.convertIntArrayToString_1(r) + "p"	//tetrisCanvas.current.r			int[] 자료형
						+ function.convertIntArrayToString_1(c) + "p"	//tetrisCanvas.current.c			int[] 자료형
						+ x + "p"										//tetrisCanvas.current.center.x		int 자료형
						+ y + "p"										//tetrisCanvas.current.center.y		int 자료형
						+ CPType + "p"									//tetrisCanvas.current.getType()	int 자료형
						+ outLine;										//tetrisCanvas.data.getLine()		int 자료형
				
				out.println(outStr);
				out.flush();
				
				if(check) {
					System.out.println(in.readLine());
					check = false;
					continue;
				}
				
				inStr = in.readLine();
				
				if(inStr != null) {
					inStrFix = function.convertStringDiv(inStr); 	//inStrFix[0, 1, 2, 3, 4, 5, 6, 7, 8]
																	//0 = 죽음			1 = 점수				2 = data.data 테이블임.
																	//3 = 현재조각 r[]		4 = 현재조각 c[]
																	//5 = 현재조각 센터 x	6 = 현재조각 센터 y		7 = 현재조각 타입
																	//8 = 데이터 라인
					inDeath = Integer.parseInt(inStrFix[0]);
					inData = function.convertStringToIntArray(inStrFix[2]);
					if(inData.length == TetrisData.ROW && inData[0].length == TetrisData.COL) {
						multiTetrisCanvas.data.setData(function.convertStringToIntArray(inStrFix[2]));
						multiTetrisCanvas.repaint();
					}
					inCPr = function.convertStringToIntArray_1(inStrFix[3]);
					inCPc = function.convertStringToIntArray_1(inStrFix[4]);
					if(inCPr.length == 4 && inCPc.length == 4) {
						multiTetrisCanvas.current.r = inCPr;
						multiTetrisCanvas.current.c = inCPc;
						multiTetrisCanvas.current.center.x = Integer.parseInt(inStrFix[5]);
						multiTetrisCanvas.current.center.y = Integer.parseInt(inStrFix[6]);
						multiTetrisCanvas.current.setType(Integer.parseInt(inStrFix[7]));
					}
					if(tempALine != Integer.parseInt(inStrFix[8]) / 4) {
						tetrisCanvas.aLine += (Integer.parseInt(inStrFix[8]) / 4 - tempALine > 0 ? Integer.parseInt(inStrFix[8]) / 4 - tempALine : 0);
						tempALine = Integer.parseInt(inStrFix[8]) / 4;
					}
				}

				if(inDeath == 1 && tetrisCanvas.stop) break;
			}
			
			outStr = 1 + "p"
					+ score + "p"
					+ function.convertIntArrayToString(data) + "p"
					+ function.convertIntArrayToString_1(r) + "p"
					+ function.convertIntArrayToString_1(c) + "p"
					+ 0 + "p"
					+ 0 + "p"
					+ 8 + "p"
					+ -1;
			
			out.println(outStr);
			out.flush();
			
			if(inStr != null) {
				inStrFix = function.convertStringDiv(inStr);
				
				inDeath = Integer.parseInt(inStrFix[0]);
				inData = function.convertStringToIntArray(inStrFix[2]);
				if(inData.length == TetrisData.ROW && inData[0].length == TetrisData.COL) {
					multiTetrisCanvas.data.setData(function.convertStringToIntArray(inStrFix[2]));
					multiTetrisCanvas.repaint();
				}
				inCPr = function.convertStringToIntArray_1(inStrFix[3]);
				inCPc = function.convertStringToIntArray_1(inStrFix[4]);
				if(inCPr.length == 4 && inCPc.length == 4) {
					multiTetrisCanvas.current.r = inCPr;
					multiTetrisCanvas.current.c = inCPc;
					multiTetrisCanvas.current.center.x = Integer.parseInt(inStrFix[5]);
					multiTetrisCanvas.current.center.y = Integer.parseInt(inStrFix[6]);
					multiTetrisCanvas.current.setType(Integer.parseInt(inStrFix[7]));
				}
			}
			
			enemyScore.getMeLabel().setText("Me");
			enemyScore.getMeScoreLabel().setText(score+"");
			enemyScore.getEnemyLabel().setText("Foe");
			enemyScore.getEnemyScoreLabel().setText(inStrFix[1]);
			String win;
			if(score > Integer.parseInt(inStrFix[1])) win = "<< Win";
			else if(score < Integer.parseInt(inStrFix[1])) win = "Win >>";
			else win = "Draw";
			enemyScore.getWinLabel().setText(win);
			enemyScore.setVisible(true);
		}
		
		public static String convertIntArrayToString(int[][] array) {
	        StringBuilder sb = new StringBuilder();
	        
	        for (int[] row : array) {
	            for (int num : row) {
	                sb.append(num).append("s");
	            }
	            sb.append("n");
	        }
	        return sb.toString();
	    }
	    
	    public static int[][] convertStringToIntArray(String str) {
	        String[] rows = str.trim().split("n");
	        int[][] array = new int[TetrisData.ROW][TetrisData.COL];
	        
	        for (int i = 0; i < TetrisData.ROW; i++) {
	            String[] nums = rows[i].trim().split("s");
	            for (int j = 0; j < TetrisData.COL; j++) {
	                array[i][j] = Integer.parseInt(nums[j]);
	            }
	        }
	        return array;
	    }
	    
	    public static String convertIntArrayToString_1 (int[] array) {
	    	StringBuilder sb = new StringBuilder();
	    	
	    	for(int num : array) {
	    		sb.append(num).append("s");
	    	}
	    	return sb.toString();
	    }
	    
	    public static int[] convertStringToIntArray_1(String str) {
	    	String[] num = str.trim().split("s");
	    	int[] array = new int[4];
	    	
	    	for(int i = 0; i < 4; i++) {
	    		array[i] = Integer.parseInt(num[i]);
	    	}
	    	return array;
	    }
	    
	    public static String[] convertStringDiv(String str) {
	    	String[] reStr = str.trim().split("p");
	    	return reStr;
	    }
	    
	    public static String readLeaderBoard() {
	    	String str = "";
	    	try {
		    	File file = new File(System.getProperty("java.io.tmpdir") + "/tlb.cTeam");
		    	
		    	FileReader file_reader = new FileReader(file);
		    	int cur = 0;
		    	while((cur = file_reader.read()) != -1) {
		    		str = str + (char)cur;
		    	}
		    	file_reader.close();
	    	} catch(Exception e) {
	    		try {
					str = makeLeaderBoard();
				} catch (Exception e1) {
					System.out.println(e);
					System.out.println(e1);
					return "-1";
				}
			}
	    	return str;
	    }
	    
	    public static void saveLeaderBoard(String str) {
	    	try {
				OutputStream output = new FileOutputStream(System.getProperty("java.io.tmpdir") + "/tlb.cTeam");
				byte[] by = str.getBytes();
				output.write(by);
				output.close();
			} catch (Exception e) {
				System.out.println(e);
			}
	    }
	    
	    public static String makeLeaderBoard() throws Exception {
	    	OutputStream output = new FileOutputStream(System.getProperty("java.io.tmpdir") + "/tlb.cTeam");
	    	String str = "10000, AAA"
	    			+ ", 9000, BBB"
	    			+ ", 8000, CCC"
	    			+ ", 7000, DDD"
	    			+ ", 6000, EEE"
	    			+ ", 5000, FFF"
	    			+ ", 4000, GGG"
	    			+ ", 3000, HHH"
	    			+ ", 2000, III"
	    			+ ", 1000, JJJ";
			byte[] by = str.getBytes();
			output.write(by);
			output.close();
			return str;
	    }
	    
	    public static void refreshLeaderBoard() {
	    	int i;
	    	String leaderBoardRawData = readLeaderBoard();
	    	
			String[] leaderBRFix = leaderBoardRawData.split(",\\s*");

			String[] strScorelist = new String[leaderBRFix.length / 2];
			String[] strNamelist = new String[leaderBRFix.length / 2];
			for (i = 0; i < leaderBRFix.length; i += 2) {
			    strScorelist[i / 2] = leaderBRFix[i].replaceAll("\\s+", "");
			    strNamelist[i / 2] = leaderBRFix[i + 1].replaceAll("\\s+", "");
			}

			i = 0;
			for (String score : strScorelist) {
				getLeaderBoard().getScoreLabel()[i].setText(score);
				i++;
			}
			
			i = 0;
			for (String name : strNamelist) {
				getLeaderBoard().getNameLabel()[i].setText(name);
				i++;
			}
			getLeaderBoard().setVisible(true);
	    }
	    
	    public static void recordLeaderBoard(int score, String name) throws Exception {
	    	String leaderBoardRawData = readLeaderBoard();
	    	
	    	String[] rawData = leaderBoardRawData.split(", ");
	        List<String> leaderBoardList = new ArrayList<>(Arrays.asList(rawData));
	    	
	    	for (int i = 0; i < leaderBoardList.size(); i += 2) {
	            int currentScore = Integer.parseInt(leaderBoardList.get(i));
	            if (score > currentScore) {
	                leaderBoardList.add(i, name);
	                leaderBoardList.add(i, String.valueOf(score));
	                leaderBoardList.remove(20);
	                leaderBoardList.remove(20);
	                break;
	            }
	        }
	    	
	        StringBuilder newLeaderBoard = new StringBuilder();
	        for (String item : leaderBoardList) {
	            newLeaderBoard.append(item).append(", ");
	        }
	        newLeaderBoard.setLength(newLeaderBoard.length() - 2);

	    	OutputStream output = new FileOutputStream(System.getProperty("java.io.tmpdir") + "/tlb.cTeam");
			byte[] by = newLeaderBoard.toString().getBytes();
			output.write(by);
			output.close();
	    }
	}
}