package pieces;

import java.util.HashSet;

import chess.Board;
import chess.Square;

/**
 * @author Naman Bajaj, Sharad Prasad
 */

public abstract class Piece {

    /**
     * Whether piece has been captured by another piece
     */
    public boolean isTaken;

    /**
     * Whether piece is white
     */
    public boolean isWhite;

    /**
     * Square that piece resides on
     */
    public Square square;

    /**
     * 2-arg constructor for Piece
     * 
     * @param isWhite whether color of piece is white
     * @param square square that piece is on
     */
    public Piece(boolean isWhite, Square square) {
        this.isTaken = false;
        this.square = square;
        this.isWhite = isWhite;
    }

    /**
     * Default constructor for Piece
     */
    public Piece() {
        this(false, null);
    }

    /**
     * Checks if move made by user is valid according to rules of Piece being moved
     * 
     * @param start Starting square
     * @param end Ending square
     * @param b Board being played on
     * @return True if valid move, false otherwise
     */
    public abstract boolean isValidMove(Square start, Square end, Board b);

    /**
     * Returns set of squares in between 2 specific squares
     * 
     * @param start Starting square
     * @param end Ending square
     * @param b Board being played on
     * @return HashSet of squares in between start and
     */
    public abstract HashSet<Square> squaresBetween(Square start, Square end, Board b);

    /**
     * Returns piece in String format
     * 
     * @return String representation of Piece
     */
    public abstract String toString();
}