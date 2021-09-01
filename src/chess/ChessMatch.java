package chess;

import boardgame.Board;
import boardgame.Position;
import chess.piece.Rook;
import chess.piece.king;

public class ChessMatch {
	
	private static Board board;
	
	
	public ChessMatch() {
		board = new Board(8, 8);
		initialSetup();
	}
	
	public static ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumn()];
		for (int i=0;i<board.getRows();i++) {
			for(int j=0; j<board.getColumn();j++) {
				mat[i][j] = (ChessPiece) board.piece(i , j);
			}
		}
		return mat;
		
	}
	
	private void initialSetup() {
		board.PlacePiece(new Rook(board, Color.WHITE), new Position(2, 1));
		board.PlacePiece(new king(board, Color.BLACK), new Position(0, 4));
		board.PlacePiece(new king(board, Color.WHITE), new Position(7, 4));
	}
	
	

}
