package chess;

import pieces.Piece;

/**
 * @author Naman Bajaj, Sharad Prasad
 */

/**
 * Square class that represents a square on the board
 */
public class Square {
    /**
     * Row position of square
     */
    public int rowpos;

    /**
     * Column position of square
     */
    public int colpos;

    /**
     * Piece that is on square
     */
    public Piece piece;

    /**
     * Whether square is white or black
     */
    boolean isLightSquare;

    /**
     * Square 4-arg constructor
     * 
     * @param rowpos        row position of square
     * @param colpos        column position of square
     * @param piece         piece on square
     * @param isLightSquare if square is white or black
     */
    public Square(int rowpos, int colpos, Piece piece, boolean isLightSquare) {
        this.rowpos = rowpos;
        this.colpos = colpos;
        this.piece = piece;
        this.isLightSquare = isLightSquare;
    }

    public String toString() {
        int row = 8 - rowpos;
        Character col = (char) (97 + colpos);
        return "" + col + "" + row;
    }
}
