package Tetris;

import java.awt.Point;

public abstract class Piece implements Cloneable {
	final int DOWN = 0, LEFT = 1, RIGHT = 2;
	protected int r[], c[];
	protected TetrisData data;
	protected Point center;
	protected boolean save = true;
	protected int type, roteType;
	
	public Piece deepCopy() {
		Piece copy = null;
		try {
	        copy = (Piece) super.clone();
	        copy.r = r.clone();
	        copy.c = c.clone();
	        copy.center = new Point(center);
	    } catch (CloneNotSupportedException e) {
	        e.printStackTrace();
	    }
	    return copy;
	}
	
	public Piece(TetrisData data, int type, int roteType) {
		r = new int[4];
		c = new int[4];
		this.data = data;
		this.type = type;
		this.roteType = roteType;
		center = new Point(5,-1);
	}

	public int getX() { return center.x; }
	public int getY() { return center.y; }
	public int getType() { return type; }
	public void setType(int type) { this.type = type; }
	
	public boolean copy(boolean downsetting) {
		int x = getX();
		int y = getY();
		
		if(downsetting != true) {
			for(int i = 0; i < 4; i++) {
				if(data.getAt(y + r[i], x + c[i]) != 0) return true;
			}
		} else {
			if(getMinY() + y < 0) return true;
			
			for(int i = 0; i < 4; i++) {
				data.setAt(y + r[i], x + c[i], getType());
			}
		}
		return false;
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
			case 3:
				for(int i = 0; i < r.length; i++) {
					if(data.getAt(y + r[i], x + c[i]) != 0) {
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
		int rc = roteType;
		if(rc <= 1) return;
		
		int[] tempR = r.clone();
		int[] tempC = c.clone();
		
		if(rc == 2) {
			rotate4();
			rotate4();
			rotate4();
			roteType = 3;
		} else if (rc == 3){
			rotate4();
			roteType = 2;
		} else {
			rotate4();
			if(roteType == 7) roteType = 4;
			else roteType++;
		}
		
	    if(isOutOfBounds()) {
	        int maxXOffset = getMaxXOffset();
	        move(maxXOffset, 0);

	        if(isOutOfBounds()) {
	            move(-maxXOffset, 0);

	            int minXOffset = getMinXOffset();
	            move(minXOffset, 0);
	            if(isOutOfBounds()) {
	            	move(-minXOffset, 0);
	            	
	            	r = tempR;
		        	c = tempC;
		        	return;
	            }
	        }
	    }
	    
	    checkDisplacement(tempR, tempC);
	}
	
	public void rotate4() {
		for(int i = 0; i < 4; i++) {
			int temp = c[i];
			c[i] = -r[i];
			r[i] = temp;
		}
	}
	
	private boolean isOutOfBounds() {
		int tmpR; int tmpC;
	    for (int i = 0; i < 4; i++) {
	        tmpR = getY() + r[i];
	        tmpC = getX() + c[i];
	        if(tmpR >= TetrisData.ROW || tmpC < 0 || tmpC >= TetrisData.COL) return true;
	    }
	    return false;
	}
	
	private void checkDisplacement(int[] tempR, int[] tempC) {
	    int minX = getMinX();
	    int maxX = getMaxX();
	    int minY = getMinY();
	    int maxY = getMaxY();

	    for (int i = minX; i <= maxX; i++) {
	        for (int j = minY; j <= maxY; j++) {
	            if (!isOverlap(0)) {
	            	return;
	            }
	        }
	    }
	    
		r = tempR;
		c = tempC;
	}
	
	private int getMaxXOffset() {
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
	
	private int getMinXOffset() {
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
		center.y = -1;
	}
}