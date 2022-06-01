package pieces;

import java.util.HashSet;

import chess.Board;
import chess.Square;

/**
 * @author Naman Bajaj, Sharad Prasad
 */

public class King extends Piece {
    /**
     * True if King has moved, false otherwise
     */
    boolean isFirstMove;

    /**
     * 2-arg constructor for King class
     * 
     * @param isWhite If piece is white or black
     * @param square  Square piece resides on
     */
    public King(boolean isWhite, Square square) {
        super(isWhite, square);
        isFirstMove = true;
    }

    public boolean isValidMove(Square start, Square end, Board b) {
        if (start.equals(end)) {
            return false;
        }

        // castling attempt found
        if (Math.abs(start.colpos - end.colpos) == 2 && Math.abs(start.rowpos - end.rowpos) == 0) {
            // king has been moved before
            if (!this.isFirstMove)
                return false;

            // check if king is in check (can't castle then)
            if (b.inCheck(this.isWhite))
                return false;

            // white castling
            if (this.isWhite) {

                // castling king side (right)
                if ((start.colpos - end.colpos) == -2) {
                    Piece p = b.board[7][7].piece;
                    // piece at end is not rook
                    if (p == null || !(p instanceof Rook))
                        return false;

                    // rook being castled has moved before
                    Rook r = (Rook) p;
                    if (!r.isFirstMove)
                        return false;

                    // piece in between king and rook
                    for (int i = 5; i < 7; i++)
                        if (b.board[7][i].piece != null)
                            return false;

                    // moves rook to appropriate spot, king still moved in Chess class
                    ((Rook) b.board[7][7].piece).isFirstMove = false;
                    b.board[7][5].piece = b.board[7][7].piece;
                    b.board[7][7].piece = null;
                    this.isFirstMove = false;
                    b.board[7][5].piece.square = b.board[7][5];

                    return true;

                }

                // castling queen side (left)
                if ((start.colpos - end.colpos) == 2) {
                    Piece p = b.board[7][0].piece;
                    if (p == null || !(p instanceof Rook))
                        return false;

                    Rook r = (Rook) p;
                    if (!r.isFirstMove)
                        return false;

                    for (int i = 1; i < 4; i++)
                        if (b.board[7][i].piece != null)
                            return false;

                    ((Rook) b.board[7][0].piece).isFirstMove = false;
                    b.board[7][3].piece = b.board[7][0].piece;
                    b.board[7][0].piece = null;
                    this.isFirstMove = false;
                    b.board[7][3].piece.square = b.board[7][3];

                    return true;

                }
            }

            // black castling
            else {
                // castling king side (right)
                if ((start.colpos - end.colpos) == -2) {
                    Piece p = b.board[0][7].piece;
                    if (p == null || !(p instanceof Rook))
                        return false;

                    Rook r = (Rook) p;
                    if (!r.isFirstMove)
                        return false;

                    for (int i = 5; i < 7; i++)
                        if (b.board[0][i].piece != null)
                            return false;

                    ((Rook) b.board[0][7].piece).isFirstMove = false;
                    b.board[0][5].piece = b.board[0][7].piece;
                    b.board[0][7].piece = null;
                    this.isFirstMove = false;
                    b.board[0][5].piece.square = b.board[0][5];

                    return true;
                }

                // castling queen side (left)
                if ((start.colpos - end.colpos) == 2) {
                    Piece p = b.board[0][0].piece;
                    if (p == null || !(p instanceof Rook))
                        return false;

                    Rook r = (Rook) p;
                    if (!r.isFirstMove)
                        return false;

                    for (int i = 1; i < 4; i++)
                        if (b.board[0][i].piece != null)
                            return false;

                    ((Rook) b.board[0][0].piece).isFirstMove = false;
                    b.board[0][3].piece = b.board[0][0].piece;
                    b.board[0][0].piece = null;
                    this.isFirstMove = false;
                    b.board[0][3].piece.square = b.board[0][3];

                    return true;

                }
            }
        }

        if (Math.abs(start.rowpos - end.rowpos) > 1 || Math.abs(start.colpos - end.colpos) > 1) {
            return false;
        }

        if (end.piece != null && (end.piece.isWhite == start.piece.isWhite)) {
            return false;
        }

        HashSet<Piece> oppPieces;

        if (start.piece.isWhite) {
            oppPieces = b.blackPieces;
        } else {
            oppPieces = b.whitePieces;
        }

        for (Piece piece : oppPieces) {
            Piece temp = end.piece;
            end.piece = this;
            this.square = end;
            start.piece = null;
            if (piece.isValidMove(piece.square, end, b)) {
                start.piece = this;
                this.square = start;
                end.piece = temp;
                return false;
            }
            start.piece = this;
            this.square = start;
            end.piece = temp;
        }

        this.isFirstMove = false;
        return true;
    }

    public HashSet<Square> squaresBetween(Square start, Square end, Board b) {
        return new HashSet<>();
    }

    public String toString() {
        return isWhite ? "wK " : "bK ";
    }
}
