package Tetris;

public class Kr extends Piece {
    public Kr(TetrisData data) {
        super(data, 7, 2);
        c[0] = 0;   r[0] = 0;
        c[1] = 0;  r[1] = -1;
        c[2] = -1;  r[2] = -1;
        c[3] = 1;   r[3] = 0;
    }
}
