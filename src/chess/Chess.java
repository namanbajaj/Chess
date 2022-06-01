package chess;

import java.util.Scanner;

import pieces.Bishop;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

/**
 * @author Naman Bajaj, Sharad Prasad
 */

/**
 * Main class
 * <p>
 * Runs a Chess game until a user wins, draws, or resigns
 */
public class Chess {

    /**
     * Main chess runner that runs until a user wins, draws, or resigns
     * 
     * @param args main arguments (unused)
     */
    public static void main(String[] args) {
        Board board = new Board();
        board.printBoard();

        Scanner scanner = new Scanner(System.in);
        String input = "";

        Boolean isWhiteTurn = true;
        Boolean drawOffered = false;

        while (!input.equals("exit")) {
            if (isWhiteTurn)
                System.out.print("\nWhite's Move: ");
            else
                System.out.print("\nBlack's Move: ");

            input = scanner.nextLine();

            // if one player resigns
            if (input.equals("resign")) {
                if (isWhiteTurn)
                    System.out.println("Black wins");
                else
                    System.out.println("White wins");
                break;
            }

            String[] array = input.split(" ");

            // check for draw offered
            if (array.length > 2 && array[2].equals("draw?")) {
                drawOffered = true;
            }

            Square start = board.board[8 - Integer.parseInt(array[0].substring(1))][(array[0].charAt(0) - 97)];
            Square end = board.board[8 - Integer.parseInt(array[1].substring(1))][(array[1].charAt(0) - 97)];
            Piece piece = start.piece;

            if (piece == null || (isWhiteTurn && !piece.isWhite) || (!isWhiteTurn && piece.isWhite)) {
                System.out.print("Illegal move, try again");
                continue;
            }

            if (piece.isValidMove(start, end, board)) {
                Piece capturedPiece = end.piece;

                // for en passant
                if (piece instanceof Pawn) {
                    if (Math.abs(end.rowpos - start.rowpos) == 1 && Math.abs(end.colpos -
                            start.colpos) == 1
                            && end.piece == null) {
                        if (piece.isWhite) {
                            capturedPiece = board.board[end.rowpos + 1][end.colpos].piece;
                            board.board[end.rowpos + 1][end.colpos].piece = null;
                        } else {
                            capturedPiece = board.board[end.rowpos - 1][end.colpos].piece;
                            board.board[end.rowpos - 1][end.colpos].piece = null;
                        }
                        // board.printBoard();

                    }
                }

                // pawn promotion
                if (piece instanceof Pawn) {
                    Pawn p = (Pawn) piece;
                    if (end.rowpos == 7 || end.rowpos == 0) { // pawn reached end
                        int convertPiece = 0;
                        if (array.length > 2)
                            convertPiece = array[2].charAt(0);

                        boolean iW = piece.isWhite;

                        if (p.isWhite)
                            board.whitePieces.remove(piece);
                        else
                            board.blackPieces.remove(piece);

                        switch (convertPiece) {
                            // convert to knight
                            case 78:
                                piece = new Knight(iW, end);
                                break;

                            // convert to rook
                            case 82:
                                piece = new Rook(iW, end);
                                break;

                            // convert to bishop
                            case 66:
                                piece = new Bishop(iW, end);
                                break;

                            // convert to queen
                            default:
                                piece = new Queen(iW, end);
                                break;
                        }

                        if (p.isWhite)
                            board.whitePieces.add(piece);
                        else
                            board.blackPieces.add(piece);

                    }

                }

                if (capturedPiece != null) {
                    if (capturedPiece.isWhite) {
                        board.whitePieces.remove(capturedPiece);
                    } else {
                        board.blackPieces.remove(capturedPiece);
                    }
                }

                start.piece = null;
                end.piece = piece;
                piece.square = end;
                System.out.println();
                board.printBoard();
            } else {
                System.out.println("Illegal move, try again");
                continue;
            }

            if (board.inCheckmate(!isWhiteTurn)) {
                System.out.println("\nCheckmate");
                if (isWhiteTurn) {
                    System.out.println("White wins");
                } else {
                    System.out.println("Black wins");
                }
                break;
            }

            if (board.inCheck(!isWhiteTurn)) {
                System.out.print("\nCheck");
            }

            // for en passant
            for (Piece pawn : board.blackPieces) {
                if (pawn instanceof Pawn) {
                    ((Pawn) pawn).justMoved = false;
                }
            }

            for (Piece pawn : board.whitePieces) {
                if (pawn instanceof Pawn) {
                    ((Pawn) pawn).justMoved = false;
                }
            }

            if (piece instanceof Pawn && Math.abs(start.rowpos - end.rowpos) == 2) {
                ((Pawn) piece).justMoved = true;
            }

            if(drawOffered){
                if (isWhiteTurn)
                    System.out.print("\nBlack's Move: ");
                else
                    System.out.print("\nWhite's Move: ");

                while (!scanner.nextLine().equals("draw"))
                    System.out.println("Illegal move, try again");
                break;
            }

            isWhiteTurn = !isWhiteTurn;
        }

        scanner.close();
    }
}
