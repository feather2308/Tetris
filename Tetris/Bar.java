package Tetris;

public class Bar extends Piece {
	public Bar(TetrisData data) {
		super(data, 1, 2);
		c[0] = 0;		r[0] = 0;
		c[1] = 0;		r[1] = -1;
		c[2] = 0;		r[2] = 1;
		c[3] = 0;		r[3] = 2;
	}
}
