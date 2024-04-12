package SJCE.xgui.JPanel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MoveTest {
    private int[] board;
    private Move move;

    @BeforeEach
    public void setup() {
        board = new int[64];
    }

    @Test
    public void testDoMoveNormal() {
        board[12] = PiecesUI.WHITE_PAWN;
        move = new Move(12, 20, PiecesUI.WHITE_PAWN);
        int result = move.doMove(board);
        assertEquals(Move.NORMAL_MOVE, result);
        assertEquals(PiecesUI.NO_PIECE, board[12]);
        assertEquals(PiecesUI.WHITE_PAWN, board[20]);
    }

    @Test
    public void testDoMoveCastle() {
        board[4] = PiecesUI.WHITE_KING;
        board[0] = PiecesUI.WHITE_ROOK;
        move = new Move(4, 2, PiecesUI.WHITE_KING);
        int result = move.doMove(board);
        assertEquals(Move.CASTLE_MOVE, result);
        assertEquals(PiecesUI.NO_PIECE, board[4]);
        assertEquals(PiecesUI.WHITE_KING, board[2]);
    }

    @Test
    public void testUndoMoveNormal() {
        board[12] = PiecesUI.NO_PIECE;
        board[20] = PiecesUI.WHITE_PAWN;
        move = new Move(12, 20, PiecesUI.WHITE_PAWN);
        move.doMove(board);
        move.undoMove(board, Move.NORMAL_MOVE);
        assertEquals(PiecesUI.WHITE_PAWN, board[12]);
        assertEquals(PiecesUI.NO_PIECE, board[20]);
    }

    @Test
    public void testIsCastleMove() {
        move = new Move(4, 6, PiecesUI.WHITE_KING);
        assertTrue(move.isCastleMove());
    }

    @Test
    public void testIsEnPassant() {
        move = new Move(48, 32, PiecesUI.BLACK_PAWN);
        move.setCaptured(ENPASSANT_CAPTURE + PiecesUI.WHITE_PAWN);
        assertTrue(move.isEnPassant());
    }

    @Test
    public void testCastleMove() {
        move = new Move(4, 6, PiecesUI.WHITE_KING);
        assertNotNull(move.castleMove());
    }

    @Test
    public void testCastleRookMove() {
        move = new Move(4, 6, PiecesUI.WHITE_KING);
        int[] rookMove = move.castleRookMove();
        assertNotNull(rookMove);
        assertEquals(7, rookMove[0]);
        assertEquals(5, rookMove[1]);
    }

    @Test
    public void testEnPassant() {
        board[48] = PiecesUI.BLACK_PAWN;
        board[32] = PiecesUI.NO_PIECE;
        move = new Move(48, 32, PiecesUI.BLACK_PAWN);
        int result = move.enPassant(board);
        assertEquals(32, result);
    }
}
