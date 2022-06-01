package chess;

import java.util.HashSet;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

/**
 * @author Naman Bajaj, Sharad Prasad
 */

/**
 * Class that represents the board that the game is being played on
 * <p>
 * Board is an 8x8 2D array of type Square that contains all present pieces on
 * the board
 */
public class Board {
    /**
     * 2D square array that represents the board
     */
    public Square[][] board;

    /**
     * Set of all black Pieces that are in play (not captured)
     */
    public HashSet<Piece> blackPieces;

    /**
     * Set of all white Pieces that are in play (not captured)
     */
    public HashSet<Piece> whitePieces;

    /**
     * Default board constructor
     */
    public Board() {
        board = new Square[8][8];
        blackPieces = new HashSet<>();
        whitePieces = new HashSet<>();

        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new Square(i, j, null, count % 2 == 1 ? false : true);
                count++;
            }
            count--;
        }

        initializeBoard();
    }

    /**
     * Initializes board and sets pieces to their default positions
     */
    public void initializeBoard() {
        for (int i = 0; i < 8; i++) {
            board[1][i].piece = new Pawn(false, board[1][i]);

            board[6][i].piece = new Pawn(true, board[6][i]);
        }

        board[0][0].piece = new Rook(false, board[0][0]);
        board[0][7].piece = new Rook(false, board[0][7]);
        board[0][1].piece = new Knight(false, board[0][1]);
        board[0][6].piece = new Knight(false, board[0][6]);
        board[0][2].piece = new Bishop(false, board[0][2]);
        board[0][5].piece = new Bishop(false, board[0][5]);
        board[0][3].piece = new Queen(false, board[0][3]);
        board[0][4].piece = new King(false, board[0][4]);

        board[7][0].piece = new Rook(true, board[7][0]);
        board[7][7].piece = new Rook(true, board[7][7]);
        board[7][1].piece = new Knight(true, board[7][1]);
        board[7][6].piece = new Knight(true, board[7][6]);
        board[7][2].piece = new Bishop(true, board[7][2]);
        board[7][5].piece = new Bishop(true, board[7][5]);
        board[7][3].piece = new Queen(true, board[7][3]);
        board[7][4].piece = new King(true, board[7][4]);

        for (int i = 0; i < 8; i++) {
            blackPieces.add(board[0][i].piece);
            blackPieces.add(board[1][i].piece);
            whitePieces.add(board[7][i].piece);
            whitePieces.add(board[6][i].piece);
        }
    }

    /**
     * Prints the board as it currently is
     */
    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].piece != null) {
                    System.out.print(board[i][j].piece);
                } else if (board[i][j].isLightSquare) {
                    System.out.print("   ");
                } else {
                    System.out.print("## ");
                }
            }
            System.out.println(8 - i);
        }

        System.out.print(" ");
        for (int i = 0; i < board.length; i++) {
            char letter = (char) (i + 97);
            System.out.print(letter + "  ");
        }
        System.out.println();
    }

    /**
     * Checks if black or white king is in check
     * 
     * @param isWhite if king being checked is white
     * @return true if king of a specific color is in check, false otherwise
     */
    public boolean inCheck(boolean isWhite) {
        HashSet<Piece> oppPieces = null;
        Square kingPosition = null;

        if (isWhite) {
            oppPieces = blackPieces;
            for (Piece piece : whitePieces) {
                if (piece instanceof King) {
                    kingPosition = piece.square;
                }
            }
        } else {
            oppPieces = whitePieces;
            for (Piece piece : blackPieces) {
                if (piece instanceof King) {
                    kingPosition = piece.square;
                }
            }
        }

        for (Piece piece : oppPieces) {
            if (piece.isValidMove(piece.square, kingPosition, this)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if black or white king is in checkmate
     * 
     * @param isWhite if king being checkmated is white
     * @return true if specific colored king is in checkmate, false otherwise
     */
    public boolean inCheckmate(boolean isWhite) {
        // check if king is in check
        if (!inCheck(isWhite)) {
            return false;
        }

        HashSet<Piece> oppPieces = null;
        HashSet<Piece> ownPieces = null;
        Square kingPosition = null;

        // assign opponent & own pieces and king position
        if (isWhite) {
            oppPieces = blackPieces;
            ownPieces = whitePieces;
            for (Piece piece : whitePieces) {
                if (piece instanceof King) {
                    kingPosition = piece.square;
                }
            }
        } else {
            oppPieces = whitePieces;
            ownPieces = blackPieces;
            for (Piece piece : blackPieces) {
                if (piece instanceof King) {
                    kingPosition = piece.square;
                }
            }
        }

        // check if king has valid squares (wip)
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (kingPosition.rowpos + i > -1 && kingPosition.rowpos + i < 8 && kingPosition.colpos + j > -1
                        && kingPosition.colpos + j < 8) {
                    Square end = board[kingPosition.rowpos + i][kingPosition.colpos + j];
                    if (kingPosition.piece.isValidMove(kingPosition, end, this)) {
                        return false;
                    }
                }
            }
        }

        HashSet<Piece> attackPieces = new HashSet<>();

        for (Piece piece : oppPieces) {
            if (piece.isValidMove(piece.square, kingPosition, this)) {
                attackPieces.add(piece);
            }
        }

        // if double check, checkmate since possible king moves are already checked
        if (attackPieces.size() > 1) {
            return true;
        }

        // check if block attacking piece
        for (Piece attackPiece : attackPieces) {
            HashSet<Square> squaresBetween = attackPiece.squaresBetween(attackPiece.square, kingPosition, this);
            for (Square square : squaresBetween) {
                for (Piece ownPiece : ownPieces) {
                    if (!(ownPiece instanceof King) && ownPiece.isValidMove(ownPiece.square, square, this)) {
                        return false;
                    }
                }
            }
        }

        // check if capture attacking piece
        for (Piece attackPiece : attackPieces) {
            for (Piece ownPiece : ownPieces) {
                if (ownPiece instanceof King) {
                    continue;
                }
                if (ownPiece.isValidMove(ownPiece.square, attackPiece.square, this)) {
                    System.out.println("piece that can capture: " + ownPiece);
                    return false;
                }
            }

            Piece king = kingPosition.piece;
            if (king.isValidMove(king.square, attackPiece.square, this)) {
                System.out.println("piece that can capture: " + king);
                return false;
            }
        }

        return true;
    }
}
