package chess;

import boardgame.Board;
import chess.piece.Rook;
import chess.piece.king;

public class ChessMatch {
	
	private  Board board;
	
	
	public ChessMatch() {
		board = new Board(8,8);
		initialSetup();
	}
	
	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumn()];
		for (int i=0;i<board.getRows();i++) {
			for(int j=0; j<board.getColumn();j++) {
				mat[i][j] = (ChessPiece) board.piece(i , j);
			}
		}
		return mat;
		
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.PlacePiece(piece, new ChessPosition(column, row).toPosition());
	}
	
	private void initialSetup() {
		placeNewPiece('b', 6, new Rook(board, Color.WHITE));
	    placeNewPiece('e',8, new king(board, Color.BLACK));
		placeNewPiece('e',1, new king(board, Color.WHITE));
	}
	
	

}
