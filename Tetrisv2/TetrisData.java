package Tetris;

//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.io.Serializable;

public class TetrisData
//	implements Serializable 
	{
	public static final int ROW = 20,
							COL = 10,
							MAX_SPEED = 31,
							BASE_SPEED = 2;

	public static int SPEED = BASE_SPEED;
	
	private int data[][];
	private
//	transient
	int line = 0;
	private int score = 0;
	
	private boolean isAttack = false;
	
	public TetrisData() {
		data = new int[ROW][COL];
	}
	
	public int getAt(int x, int y) {
		if(x < 0 || x >= ROW || y < 0 || y >= COL) return 0;
		return data[x][y];
	}
	
	public void setAt(int x, int y, int v) {
		data[x][y] = v;
	}
	
	public int getLine() {
		return line;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int num) {
		score += num;
	}
	
	public synchronized void removeLiness() {
		int chain = 0;
		NEXT:
			for(int i = ROW - 1; i >= 0; i--) {
				boolean done = true;
				for(int k = 0; k < COL; k++) {
					if(data[i][k] == 0) {
						done = false;
						continue NEXT;
					}
				}
				if(done) {
					line++;
					chain++;
					for(int x = i; x > 0; x--) {
						for(int y = 0; y < COL; y++) {
							data[x][y] = data[x-1][y];
						}
					}
					if(i != 0) {
						for(int y = 0; y < COL; y++) {
							data[0][y] = 0;
						}
					}
					i++;
				}
			}
		switch(chain) {
			case 1:
				score += 175 * MyTetris.getTetrisCanvas().level * chain;
				break;
			case 2:
				score += 175 * MyTetris.getTetrisCanvas().level * chain * 1.2;
				break;
			case 3:
				score += 175 * MyTetris.getTetrisCanvas().level * chain * 1.5;
				break;
			case 4:
				score += 175 * MyTetris.getTetrisCanvas().level * chain * 2;
				break;
			case 5:
				score += 175 * MyTetris.getTetrisCanvas().level * chain * 5;
			case 0:
			default:
				break;
		}
		MyTetris.getLblScoreLabel().setText("Score: " + score);
		MyTetris.getLblLineLabel().setText("Line: " + line);
		if(MyTetris.getTetrisCanvas().lineTmp != line) {
			if((line / 10 + BASE_SPEED) >= MAX_SPEED) {
				SPEED = MAX_SPEED - 1;
				MyTetris.getTetrisCanvas().level = MAX_SPEED;
			} else {
				SPEED = line / 10 + BASE_SPEED - 1;
				MyTetris.getTetrisCanvas().level = line / 10 + BASE_SPEED;
			}
			MyTetris.getLblLevelLabel().setText("Level: " + SPEED);
		}
	}
	
	public synchronized void attackLiness() throws Exception {
		if(isAttack)
		while(MyTetris.getTetrisCanvas().aLine > 0) {
			for(int i = 1; i < ROW; i++) {
				for(int k = 0; k < COL; k++) {
					data[i-1][k] = data[i][k];
					if(i == ROW - 1) data[ROW - 1][k] = 9;
				}
			}
			data[ROW - 1][(int)(Math.random() * Integer.MAX_VALUE % COL)] = 0;
			MyTetris.getTetrisCanvas().aLine--;
		}
	}
	
	public synchronized void itemRemoveLine() {
		for(int i = ROW - 1; i >= 0; i--) {
			for(int k = 0; k < COL; k++) {
				if(i != 0) data[i][k] = data[i-1][k];
				else data[0][k] = 0;
			}
		}
	}
	
	public void clear() {
		line = 0;
		score = 0;
		MyTetris.getTetrisCanvas().level = BASE_SPEED;
		for(int i = 0; i < ROW; i++) {
			for(int k = 0; k < COL; k++) {
				data[i][k] = 0;
			}
		}
	}
	
	public void dump() {
		for(int i = 0; i < ROW; i++) {
			for(int k = 0; k < COL; k++) {
				System.out.print(data[i][k] + " ");
			}
			System.out.println();
		}
	}
	
	public int[][] getData() {
		return data;
	}
	
	public void setAttack(boolean attack) {
		isAttack = attack;
	}
	
	public void setData(int[][] data) {
		this.data = data;
	}
	
//	private void writeObject(ObjectOutputStream oos) {
//		try {
//			oos.defaultWriteObject();
//			oos.writeObject(this.data);
//			oos.writeObject(this.line);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
//	}
//	
//	private void readObject(ObjectInputStream oos) {
//		try {
//			oos.defaultReadObject();
//			this.data = (int[][]) oos.readObject();
//			this.line = (int) oos.readObject();
//		} catch (IOException | ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
//	}
}
