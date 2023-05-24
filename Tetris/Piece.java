package Tetris;

import java.awt.Point;

public abstract class Piece {
	final int DOWN = 0, LEFT = 1, RIGHT = 2;
	protected int r[], c[];
	protected TetrisData data;
	protected Point center;
	protected boolean save = true;
	
	public Piece(TetrisData data) {
		r = new int[4];
		c = new int[4];
		this.data = data;
		center = new Point(5,0);
	}

	public abstract int getType();
	public abstract int roteType();
	
	public int getX() { return center.x; }
	public int getY() { return center.y; }
	
	public boolean copy() {
		boolean value = false;
		int x = getX();
		int y = getY();
		if(getMinY() + y <= 0) {
			value = true;
		}
		
		for(int i = 0; i < 4; i++) {
			data.setAt(y + r[i], x + c[i], getType());
		}
		return value;
	}
	
	public boolean isOverlap(int dir) {
		int x = getX();
		int y = getY();
		switch(dir) {
			case 0:
				for(int i = 0; i < r.length; i++) {
					if(data.getAt(y + r[i] + 1, x + c[i]) != 0) {
						return true;
					}
				}
				break;
			case 1:
				for(int i = 0; i < r.length; i++) {
					if(data.getAt(y + r[i], x + c[i] - 1) != 0) {
						return true;
					}
				}
				break;
			case 2:
				for(int i = 0; i < r.length; i++) {
					if(data.getAt(y + r[i], x + c[i] + 1) != 0) {
						return true;
					}
				}
				break;
		}
		return false;
	}
	
	public int getMinX() {
		int min = c[0];
		for(int i = 1 ; i < c.length; i++) {
			if(c[i] < min) {
				min = c[i];
			}
		}
		return min;
	}
	
	public int getMaxX() {
		int max = c[0];
		for(int i = 1; i < c.length; i++) {
			if(c[i] > max) {
				max = c[i];
			}
		}
		return max;
	}
	
	public int getMinY() {
		int min = r[0];
		for(int i = 1; i < r.length; i++) {
			if(r[i] < min) {
				min = r[i];
			}
		}
		return min;
	}
	
	public int getMaxY() {
		int max = r[0];
		for(int i = 1; i < r.length; i++) {
			if(r[i] > max) {
				max = r[i];
			}
		}
		return max;
	}
	
	public boolean moveDown() {
		if(center.y + getMaxY() + 1 < TetrisData.ROW) {
			if(isOverlap(DOWN) != true) {
				center.y++;
			} else {
				return true;
			}
		} else { return true; }
		return false;
	}
	
	public void moveLeft() {
		if(center.x + getMinX() - 1 >= 0)
			if(isOverlap(LEFT) != true) { center.x--; }
			else return;
	}
	
	public void moveRight() {
		if(center.x + getMaxX() + 1 < TetrisData.COL)
			if(isOverlap(RIGHT) != true) { center.x++; }
			else return;
	}
	
	public void move(int dx, int dy) {
		center.x += dx;
		center.y += dy;
	}
	
	public void rotate() {
		int rc = roteType();
		if(rc <= 1) return;
		
		int[] tempR = r.clone();
		int[] tempC = c.clone();
		if(rc == 2) {
			rotate4();
			rotate4();
			rotate4();
		} else {
			rotate4();
		}
		
	    if (isOutOfBounds()) {
	        int maxOffset = getMaxOffset();
	        move(maxOffset, 0);

	        if (isOutOfBounds()) {
	            move(-maxOffset, 0);
	            
	            int minOffset = getMinOffset();
	            move(minOffset, 0);
	        }
	    }
	}
	
	public void rotate4() {
		for(int i = 0; i < 4; i++) {
			int temp = c[i];
			c[i] = -r[i];
			r[i] = temp;
		}
	}
	
	private boolean isOutOfBounds() {
	    for (int i = 0; i < 4; i++) {
	        int newRow = getY() + r[i];
	        int newCol = getX() + c[i];
	        if (newRow < 0 || newRow >= TetrisData.ROW || newCol < 0 || newCol >= TetrisData.COL) {
	            return true;
	        }
	    }
	    return false;
	}
	
	private int getMaxOffset() {
	    int maxOffset = 0;
	    for (int i = 0; i < 4; i++) {
	        int newCol = getX() + c[i];
	        int offset = 0;
	        if (newCol < 0) {
	            offset = -newCol;
	        } else if (newCol >= TetrisData.COL) {
	            offset = TetrisData.COL - newCol - 1;
	        }
	        maxOffset = Math.max(maxOffset, offset);
	    }
	    return maxOffset;
	}
	
	private int getMinOffset() {
	    int minOffset = 0;
	    for (int i = 0; i < 4; i++) {
	        int newCol = getX() + c[i];
	        int offset = 0;
	        if (newCol >= TetrisData.COL) {
	            offset = TetrisData.COL - newCol - 1;
	        }
	        minOffset = Math.min(minOffset, offset);
	    }
	    return minOffset;
	}
	
	public void resetPosition() {
		center.x = 5;
		center.y = 0;
	}
}
