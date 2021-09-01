package chess.piece;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class king extends ChessPiece{

	public king(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}
   
	@Override
	public String toString() {
		return "K";
	}
	
}
