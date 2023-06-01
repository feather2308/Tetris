package Tetris;

import java.awt.*;
import javax.swing.*;

public class MultiTetrisCanvas extends JPanel{
	protected Thread worker;
	protected Color colors[];
	protected int w = 25;
	protected TetrisData data = new TetrisData();
	protected int margin = 20;
	protected boolean stop;
	protected Piece current = new Empty(this.data);
	
	public MultiTetrisCanvas() {
		colors = new Color[10];
		colors[0] = new Color(133, 133, 133);//미리보기색
		colors[1] = new Color(255, 0, 0);	//빨간색
		colors[2] = new Color(0, 255, 0);	//녹색
		colors[3] = new Color(0, 200, 255);	//노란색
		colors[4] = new Color(255, 255, 0);	//하늘색
		colors[5] = new Color(255, 150, 0);	//황토색
		colors[6] = new Color(210, 0, 240);	//보라색
		colors[7] = new Color(40, 0, 240);	//파란색
		colors[8] = new Color(238, 238, 238); //배경색
		colors[9] = new Color(80, 80, 80);	//검은회색
		
		data.clear();
		repaint();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		for(int i = 0; i < TetrisData.ROW; i++) {
			for(int k = 0; k < TetrisData.COL; k++) {
				if(data.getAt(i, k) == 0) {
					g.setColor(colors[data.getAt(i, k)]);
					g.draw3DRect(margin/2 + w * k, margin/2 + w * i + w, w, w, true);
				} else {
					g.setColor(colors[data.getAt(i, k)]);
					g.fill3DRect(margin/2 + w * k, margin/2 + w * i + w, w, w, true);
				}
			}
		}
		
		if(current != null) {
			for(int i = 0; i < 4; i++) {
				g.setColor(colors[current.getType()]);
				g.fill3DRect(margin / 2 + w * (current.getX() + current.c[i]), margin/2 + w * (current.getY() + current.r[i]) + w, w, w, true);
			}
		}
	}
	
	public Dimension getPreferredSize() {
		int tw = w * TetrisData.COL + margin;
		int th = w * TetrisData.ROW + margin;
		return new Dimension(tw, th);
	}
}