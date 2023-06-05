package Tetris;

public class CP extends Piece {
    public CP(TetrisData data) {
        super(data, 10, 1);
        c = new int[5]; r = new int[5];
        c[0] = 0;   r[0] = 0;
        c[1] = 1;   r[1] = 1;
        c[2] = 1;   r[2] = -1;
        c[3] = -1;   r[3] = 1;
        c[4] = -1;	r[4] = -1;
    }
}
