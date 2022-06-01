package chess;

public class Move {
    public Square start;
    public Square end;
    public Piece movedPiece;
    public Piece capturedPiece;

    public Move(Square start, Square end, Piece movedPiece, Piece capturedPiece) {
        this.start = start;
        this.end = end;
        this.movedPiece = movedPiece;
        this.capturedPiece = capturedPiece;
    }
}
