package Tetris;

import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.io.*;
import java.net.*;

public class MyTetris extends JFrame {

	private JPanel contentPane;
	private static TetrisCanvas tetrisCanvas;
	private static MultiTetrisCanvas multiTetrisCanvas;
	private static JLabel lblScoreLabel;
	private static JLabel lblLineLabel;
	
	private JLabel nextPieceLabel;
	private JLabel savePieceLabel;
	
	private static JMenuItem mntmStartMenuItem;
	protected static boolean connect = false;
	protected static boolean canChange = true;
	
	private JMenuItem largeMenuItem;
	private JMenuItem serverMenuItem;
	private JMenuItem clientMenuItem;
	private JMenuItem smallMenuItem;

	private final ConnectServer connectServer = new ConnectServer();
	private final EnemyScore enemyScore = new EnemyScore();

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
		renderUI();
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
	public static JMenuItem getMntmNewMenuItem() {
		return mntmStartMenuItem;
	}
	
	private void renderUI() {
		setResizable(false);
		setTitle("테트리스");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 600);
		
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
		
		JMenu extraMenu = new JMenu("Extra");
		menuBar.add(extraMenu);
		
		connectServer.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		connectServer.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				doConnect();
			}
		});
		
		largeMenuItem = new JMenuItem("확장");
		smallMenuItem = new JMenuItem("축소");
		serverMenuItem = new JMenuItem("Server");
		serverMenuItem.setEnabled(false);
		clientMenuItem = new JMenuItem("Client");
		clientMenuItem.setEnabled(false);
		
		largeMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				renderUIMulti();
			}
		});
		
		smallMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				renderUISingle();
			}
		});
		
		serverMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					smallMenuItem.setEnabled(false);
					serverMenuItem.setEnabled(false);
					clientMenuItem.setEnabled(false);
					serverHandler sh = new serverHandler(30000);
					sh.start();
				} catch (Exception e1) { }
			}
		});
		
		clientMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					smallMenuItem.setEnabled(false);
					serverMenuItem.setEnabled(false);
					clientMenuItem.setEnabled(false);
					connectServer.setVisible(true);
				} catch (Exception e1) { }
			}
		});
		
		extraMenu.add(largeMenuItem);
		extraMenu.add(smallMenuItem);
		extraMenu.add(serverMenuItem);
		extraMenu.add(clientMenuItem);
		
		smallMenuItem.setEnabled(false);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		renderCanvas();
	}
	
	private void renderCanvas() {
		tetrisCanvas = new TetrisCanvas();
		contentPane.add(tetrisCanvas, BorderLayout.CENTER);
		tetrisCanvas.setLayout(null);
		
		nextPieceLabel = new JLabel("-NEXT-");
		nextPieceLabel.setBounds(300, 15, 45, 15);
		nextPieceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tetrisCanvas.add(nextPieceLabel);
		
		savePieceLabel = new JLabel("-SAVE-");
		savePieceLabel.setBounds(300, 140, 44, 15);
		savePieceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tetrisCanvas.add(savePieceLabel);
		
		lblScoreLabel = new JLabel("Score");
		lblScoreLabel.setBounds(270, 475, 95, 15);
		lblScoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
		tetrisCanvas.add(lblScoreLabel);
		
		lblLineLabel = new JLabel("Line");
		lblLineLabel.setBounds(270, 495, 60, 15);
		lblLineLabel.setHorizontalAlignment(SwingConstants.LEFT);
		tetrisCanvas.add(lblLineLabel);
	}
	
	private void renderUISingle() {//확장 이후에 다시 축소할 때 사용
		setBounds(getBounds().x, getBounds().y, 400, 600);
		contentPane.remove(multiTetrisCanvas);
		renderUIMenuItem(false);
	}
	
	private void renderUIMulti() {
		tetrisCanvas.stop();
		setBounds(getBounds().x, getBounds().y, 700, 600);
		multiTetrisCanvas = new MultiTetrisCanvas();
		contentPane.add(multiTetrisCanvas, BorderLayout.EAST);
		multiTetrisCanvas.setLayout(null);
		renderUIMenuItem(true);
	}
	
	private void renderUIMenuItem(boolean b) {
		largeMenuItem.setEnabled(!b);
		smallMenuItem.setEnabled(b);
		clientMenuItem.setEnabled(b);
		serverMenuItem.setEnabled(b);
		mntmStartMenuItem.setEnabled(!b);
		canChange = !b;
	}
	
	public void doConnect() {
		if(connect) {
			clientHandler ch = new clientHandler(connectServer.ip, connectServer.port);
			ch.start();
		} else {
			smallMenuItem.setEnabled(true);
			serverMenuItem.setEnabled(true);
			clientMenuItem.setEnabled(true);
		}
	}
	
	private class serverHandler extends Thread {
		private ServerSocket socket = null;
		private Socket client = null;
		private BufferedReader in = null;
		private PrintWriter out = null;
//		private ObjectInputStream inObj = null;
//		private ObjectOutputStream outObj = null;
		
		public serverHandler(int port){
			try {
				socket = new ServerSocket(port);
			} catch (IOException e) {System.out.println(e);}
		}
		
		public void run() {
			while(true) {
				System.out.println("헉runserver");
				try {
					client = socket.accept();
					in = new BufferedReader(new InputStreamReader(client.getInputStream()));
					out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
					
					while(true) {
						if(in.read()==1) {
							out.write(1);
							out.flush();
							break;
						}
					}
					
					tetrisCanvas.start();
					
					function.handlerRun(in, out, enemyScore);

//					inObj = new ObjectInputStream(client.getInputStream());
//					
//					outObj = new ObjectOutputStream(client.getOutputStream());
//					outObj.writeObject(null);
//					outObj.flush();
//					
//					TetrisData data;
//					Object receivedObject;
//					
//					while(true) {
//						data = tetrisCanvas.data;
//						outObj.writeObject(data);
//						outObj.flush();
//						
//						receivedObject = inObj.readObject();
//						
//						if (receivedObject instanceof TetrisData) {
//							multiTetrisCanvas.data = ((TetrisData)receivedObject);
//							multiTetrisCanvas.repaint();
//						}
//					}
				} catch (Exception e) {System.out.println(e);}	
			}
		}
	}
	
	private class clientHandler extends Thread {
		private Socket socket = null;
		private BufferedReader in = null;
		private PrintWriter out = null;
//		private ObjectInputStream inObj = null;
//		private ObjectOutputStream outObj = null;
		
		public clientHandler(String IP, int port) {
			try {
				socket = new Socket(IP, port);
			} catch (Exception e) {
				System.out.println(e);
				smallMenuItem.setEnabled(true);
				serverMenuItem.setEnabled(true);
				clientMenuItem.setEnabled(true);
			}
		}
		
		public void run() {
			System.out.println("헉run");
			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

				while(true) {
					if(true) {
						out.write(1);
						out.flush();
					}
					if(in.read()==1) break;
				}
				
				tetrisCanvas.start();
				
				function.handlerRun(in, out, enemyScore);
				
//				outObj = new ObjectOutputStream(socket.getOutputStream());
//				outObj.writeObject(null);
//				outObj.flush();
//				
//				inObj = new ObjectInputStream(socket.getInputStream());
//				
//				TetrisData data;
//				Object receivedObject;
//				
//				while(true) {
//					data = tetrisCanvas.data;
//					outObj.writeObject(data);
//					outObj.flush();
//
//					receivedObject = inObj.readObject();
//					
//					if (receivedObject instanceof TetrisData) {
//						multiTetrisCanvas.data = ((TetrisData)receivedObject);
//						multiTetrisCanvas.repaint();
//					}
//				}
			} catch(Exception e) {System.out.println(e);}
			renderUIMenuItem(true);
		}
	}
	
	private class function {
		public static void handlerRun(BufferedReader in, PrintWriter out, EnemyScore enemyScore) throws IOException {
			//output 변수들
			int[][] data;
			int[] r, c;
			int death, score = 0, x, y, CPType;
			
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
				score = tetrisCanvas.data.getLine() * 175 * tetrisCanvas.level;
				data = tetrisCanvas.data.getData();
				
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
						+ score + "p"									//tetrisCanvas.data.getLine() * 175 * tetrisCanvas.level int 자료형
						+ function.convertIntArrayToString(data) + "p"	//tetrisCanvas.data.getData()		int[][] 자료형 즉, data의 data임.
						+ function.convertIntArrayToString_1(r) + "p"	//tetrisCanvas.current.r			int[] 자료형
						+ function.convertIntArrayToString_1(c) + "p"	//tetrisCanvas.current.c			int[] 자료형
						+ x + "p"										//tetrisCanvas.current.center.x		int 자료형
						+ y + "p"										//tetrisCanvas.current.center.y		int 자료형
						+ CPType;										//tetrisCanvas.current.getType()	int 자료형
				
				out.println(outStr);
				out.flush();
				
				inStr = in.readLine();
				
				if(inStr != null) {
					inStrFix = function.convertStringDiv(inStr); 	//inStrFix[0, 1, 2, 3, 4, 5, 6, 7]
																	//0 = 죽음			1 = 점수				2 = data.data 테이블임.
																	//3 = 현재조각 r[]		4 = 현재조각 c[]
																	//5 = 현재조각 센터 x	6 = 현재조각 센터 y		7 = 현재조각 타입
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

				if(inDeath == 1 && tetrisCanvas.stop) break;
			}
			
			outStr = 1 + "p"
					+ score + "p"
					+ function.convertIntArrayToString(data) + "p"
					+ function.convertIntArrayToString_1(r) + "p"
					+ function.convertIntArrayToString_1(c) + "p"
					+ 0 + "p"
					+ 0 + "p"
					+ 8;
			
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
	}
}