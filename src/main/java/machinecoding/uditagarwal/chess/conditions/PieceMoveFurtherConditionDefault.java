package machinecoding.uditagarwal.chess.conditions;


import machinecoding.uditagarwal.chess.model.Board;
import machinecoding.uditagarwal.chess.model.Cell;
import machinecoding.uditagarwal.chess.model.Piece;

/**
 * Default condition for moving further. By default, a piece is allowed to move from a cell only if the cell was free
 * when it came there.
 */
public class PieceMoveFurtherConditionDefault implements PieceMoveFurtherCondition {

    @Override
    public boolean canPieceMoveFurtherFromCell(Piece piece, Cell cell, Board board) {
        return cell.isFree();
    }
}
