package Tetris;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
					serverHandler sh = new serverHandler(30000);
					sh.start();
				} catch (Exception e1) { }
			}
		});
		
		clientMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
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
					
					int[][] data;
					String receivedData;
					
					while(true) {
						
						data = tetrisCanvas.data.getData();
						out.println(ArrayConverter.convertIntArrayToString(data));
						out.flush();
						
						receivedData = in.readLine();
						
						if(receivedData != null) {
							multiTetrisCanvas.data.setData(ArrayConverter.convertStringToIntArray(receivedData));
							multiTetrisCanvas.repaint();
						}
					}

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
			} catch (Exception e) {System.out.println(e);}
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
				
				int[][] data;
				String receivedData;
				
				while(true) {
					data = tetrisCanvas.data.getData();
					out.println(ArrayConverter.convertIntArrayToString(data));
					out.flush();
					
					receivedData = in.readLine();
					
					if(receivedData != null) {
						multiTetrisCanvas.data.setData(ArrayConverter.convertStringToIntArray(receivedData));
						multiTetrisCanvas.repaint();
					}
				}
				
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
		}
	}
	
	class ArrayConverter {
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
	}
}