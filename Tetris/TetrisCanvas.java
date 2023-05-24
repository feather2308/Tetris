package tetris;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class TetrisCanvas extends JPanel implements Runnable, KeyListener {
	protected Thread worker;
	protected Color colors[];
	protected int w = 25;
	protected TetrisData data;
	protected int margin = 20;
	protected boolean stop, makeNew;
	protected Piece current;
	protected Piece next;
	protected Piece save;
	protected int interval = 2000;
	protected int level = 2;
	protected int lineTmp;
	protected String scoreStr = "Score: 0";
	
	public TetrisCanvas() {
		data = new TetrisData();
		addKeyListener(this);
		colors = new Color[8];
		colors[0] = new Color(80, 80, 80);	//검은회색
		colors[1] = new Color(255, 0, 0);	//빨간색
		colors[2] = new Color(0, 255, 0);	//녹색
		colors[3] = new Color(0, 200, 255);	//노란색
		colors[4] = new Color(255, 255, 0);	//하늘색
		colors[5] = new Color(255, 150, 0);	//황토색
		colors[6] = new Color(210, 0, 240);	//보라색
		colors[7] = new Color(40, 0, 240);	//파란색
	}
	
	public void start() {
		data.clear();
		lineTmp = data.getLine();
		pieceMake();
		worker = new Thread(this);
		worker.start();
		makeNew = true;
		stop = false;
		requestFocus();
		MyTetris.getLblScoreLabel().setText("Score: 0");
		repaint();
	}
	
	public void stop() {
		stop = true;
		current = null;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		for(int i = 0; i < TetrisData.ROW; i++) {
			for(int k = 0; k < TetrisData.COL; k++) {
				if(data.getAt(i, k) == 0) {
					g.setColor(colors[data.getAt(i, k)]);
					g.draw3DRect(margin/2 + w * k, margin/2 + w * i, w, w, true);
				} else {
					g.setColor(colors[data.getAt(i, k)]);
					g.fill3DRect(margin/2 + w * k, margin/2 + w * i, w, w, true);
				}
			}
		}
		
		if(current != null) {
			for(int i = 0; i < 4; i++) {
				g.setColor(colors[current.getType()]);
				g.fill3DRect(margin/2 + w * (current.getX() + current.c[i]), margin/2 + w * (current.getY() + current.r[i]), w, w, true);
			}
		}
		
	    if(next != null) {
	    	int nextX = TetrisData.COL + 2; // 오른쪽에 위치할 x 좌표
            int nextY = 2; // 위쪽에 위치할 y 좌표
	        for (int i = 0; i < 4; i++) {
	            g.setColor(colors[next.getType()]);
	            g.fill3DRect(margin / 2 + w * (nextX + next.c[i]), margin / 2 + w * (nextY + next.r[i]), w, w, true);
	        }
	    }
	    
	    if(save != null) {
	    	int saveX = TetrisData.COL + 2;
	    	int saveY = 7;
	    	for(int i = 0; i < 4; i++) {
	    		g.setColor(colors[save.getType()]);
	    		g.fill3DRect(margin / 2 + w * (saveX + save.c[i]), margin / 2 + w * (saveY + save.r[i]), w, w, true);
	    	}
	    }
	}
	
	public Dimension getPreferredSize() {
		int tw = w * TetrisData.COL + margin;
		int th = w * TetrisData.ROW + margin;
		return new Dimension(tw, th);
	}
	
	public void run() {
		while(!stop) {
			try {
				if(makeNew) {
					current = next;
					pieceMake();
					makeNew = false;
					lineTmp = data.getLine();
				} else {
					if(current.moveDown()) {
						makeNew = true;
						gameCheck();
						current = null;
						data.removeLiness();
					}
					try {
						Thread.sleep(interval/level);
					} catch(Exception e) { }
				}
				repaint();
			} catch(Exception e) { }
		}
	}
	
	public void keyPressed(KeyEvent e) {
		if(current == null) return;
		
		switch(e.getKeyCode()) {
			case 37:	//왼쪽 화살표
				current.moveLeft();
				repaint();
				break;  
			case 39:	//오른쪽 화살표
				current.moveRight();
				repaint();
				break;
			case 38:	//윗쪽 화살표
			case 90:	//'z' 키
				if(!current.isOverlap(0)) {
					current.rotate();
				}
				repaint();
				break;
			case 40:	//아랫쪽 화살표
				if(current.moveDown()) {
					makeNew = true;
					gameCheck();
					current = null;
					data.removeLiness();
					worker.interrupt();
				}
				repaint();
				break;
			case 32:	//스페이스바
				while(!current.moveDown()) { }
				makeNew = true;
				gameCheck();
				current = null;
				data.removeLiness();
				repaint();
				worker.interrupt();
				break;
			case 67:	//'c' 키
				if(current.save) {
					if(save == null) {
						save = current;
						current = next;
						pieceMake();
						current.save = false;
					} else {
						Piece temp = save;
						save = current;
						current = temp;
						current.save = false;
						current.resetPosition();
					}
				}
		}
	}
	
	public void keyReleased(KeyEvent e) { }
	public void keyTyped(KeyEvent e) { }
	
	public void pieceMake() {
		int random = (int)(Math.random() * Integer.MAX_VALUE) % 7;
        switch(random) {
            case 0:
                next = new Bar(data);
                break;
            case 1:
                next = new Tee(data);
                break;
            case 2:
                next = new El(data);
                break;
            case 3:
                next = new Square(data);
                break;
            case 4:
                next = new Er(data);
                break;
            case 5:
                next = new Kl(data);
                break;
            case 6:
                next = new Kr(data);
                break;
            default:
                if(random % 2 == 0)
                    next = new Tee(data);
                else next = new El(data);
        }
	}
	
	public void gameCheck() {
		if(current.copy()) {
			stop();
			MyTetris.getMntmNewMenuItem().setEnabled(true);
			int score = data.getLine() * 175 * level;
			JOptionPane.showMessageDialog(this, "게임끝\n점수: " + score);
		}
	}
}
