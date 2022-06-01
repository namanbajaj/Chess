package pieces;

import java.util.HashSet;

import chess.Board;
import chess.Square;

/**
 * @author Naman Bajaj, Sharad Prasad
 */

public class Bishop extends Piece {

    /**
     * 2-arg constructor for Bishop class
     * 
     * @param isWhite If piece is white or black
     * @param square  Square piece resides on
     */
    public Bishop(boolean isWhite, Square square) {
        super(isWhite, square);
    }

    public boolean isValidMove(Square start, Square end, Board b) {
        if (start.equals(end))
            return false;

        if (Math.abs(start.rowpos - end.rowpos) != Math.abs(start.colpos - end.colpos)) {
            return false;
        }

        // check if any pieces are in between start and end square
        if (start.rowpos < end.rowpos) {
            if (start.colpos < end.colpos) {
                // down and right
                for (int i = 1; i < Math.abs(end.rowpos - start.rowpos); i++) {
                    if (b.board[start.rowpos + i][start.colpos + i].piece != null)
                        return false;
                }
            } else {
                // down and left
                for (int i = 1; i < Math.abs(end.rowpos - start.rowpos); i++) {
                    if (b.board[start.rowpos + i][start.colpos - i].piece != null)
                        return false;
                }
            }
        } else {
            if (start.colpos < end.colpos) {
                // up and right
                for (int i = 1; i < Math.abs(end.rowpos - start.rowpos); i++) {
                    if (b.board[start.rowpos - i][start.colpos + i].piece != null)
                        return false;
                }
            } else {
                // up and left
                for (int i = 1; i < Math.abs(end.rowpos - start.rowpos); i++) {
                    if (b.board[start.rowpos - i][start.colpos - i].piece != null)
                        return false;
                }
            }
        }

        // checks if end square has a same color piece
        if (end.piece != null && (end.piece.isWhite == start.piece.isWhite)) {
            return false;
        }

        // check if piece is pinned (perform the move and check if the king is in check
        // and revert back)
        boolean output;
        Piece piece = start.piece;
        Piece capturedPiece = end.piece;
        if (capturedPiece != null) {
            if (capturedPiece.isWhite) {
                b.whitePieces.remove(capturedPiece);
            } else {
                b.blackPieces.remove(capturedPiece);
            }
        }
        start.piece = null;
        end.piece = piece;
        piece.square = end;
        // capturedPiece.square = null;
        if (b.inCheck(piece.isWhite)) {
            output = false;
        } else {
            output = true;
        }

        start.piece = piece;
        piece.square = start;
        end.piece = capturedPiece;
        // capturedPiece.square = end;
        if (capturedPiece != null) {
            if (capturedPiece.isWhite) {
                b.whitePieces.add(capturedPiece);
            } else {
                b.blackPieces.add(capturedPiece);
            }
        }

        return output;
    }

    public HashSet<Square> squaresBetween(Square start, Square end, Board b) {
        HashSet<Square> squares = new HashSet<>();

        if (start.rowpos < end.rowpos) {
            if (start.colpos < end.colpos) {
                // down and right
                for (int i = 1; i < Math.abs(end.rowpos - start.rowpos); i++) {
                    squares.add(b.board[start.rowpos + i][start.colpos + i]);

                }
            } else {
                // down and left
                for (int i = 1; i < Math.abs(end.rowpos - start.rowpos); i++) {
                    squares.add(b.board[start.rowpos + i][start.colpos - i]);

                }
            }
        } else {
            if (start.colpos < end.colpos) {
                // up and right
                for (int i = 1; i < Math.abs(end.rowpos - start.rowpos); i++) {
                    squares.add(b.board[start.rowpos - i][start.colpos + i]);
                }
            } else {
                // up and left
                for (int i = 1; i < Math.abs(end.rowpos - start.rowpos); i++) {
                    squares.add(b.board[start.rowpos - i][start.colpos - i]);
                }
            }
        }

        return squares;
    }

    public String toString() {
        return isWhite ? "wB " : "bB ";
    }

}