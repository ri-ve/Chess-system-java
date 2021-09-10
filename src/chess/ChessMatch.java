package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.piece.Rook;
import chess.piece.king;

public class ChessMatch {
	
	private int turn;
	private Color currentPlayer;
	private  Board board;
	private boolean check;
	
	private List<Piece> pieceOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	
	public ChessMatch() {
		board = new Board(8,8);
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
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
	
	public boolean [][] possibleMoves(ChessPosition sourcePosition){
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition(); 
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPice = makeMove(source, target);
		if(testCheck(currentPlayer)) {
			undoMove(source, target, capturedPice);
			throw new ChessException("You can´t put yourself in check");
		}
		
		check = (testCheck(opponent(currentPlayer)))? true : false;
		nextTurn();
		return (ChessPiece)capturedPice;
	}
	
	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.PlacePiece(p, target);
		
		if(capturedPiece != null) {
			pieceOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target,Piece capturedPiece) {
		Piece p = board.removePiece(target);
		board.PlacePiece(p, source);
		
		if(capturedPiece != null) {
			board.PlacePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			pieceOnTheBoard.add(capturedPiece);
		}
	}
	
	private void validateSourcePosition (Position position) {
		if(!board.thereIsApiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
			throw new ChessException("The chosen piese is not yours");
		}
		if(!board.piece(position).isTheAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piese");
		}
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	
	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("The chosen piece can't move to target position");
		}
		
	}
	
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private ChessPiece King(Color color) {
		List<Piece> list = pieceOnTheBoard.stream().filter(x ->((ChessPiece)x).getColor()== color).collect(Collectors.toList());
		for (Piece p : list) {
			if(p instanceof king) {
				return (ChessPiece)p;
			}
		}
		
		throw new IllegalStateException("There is no "+ color + "King on the borad");
	}
	
	private boolean testCheck(Color color) {
		Position KingPosition = King(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = pieceOnTheBoard.stream().filter(x ->((ChessPiece)x).getColor()== opponent (color)).collect(Collectors.toList());
		for(Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[KingPosition.getRow()][KingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.PlacePiece(piece, new ChessPosition(column, row).toPosition());
		pieceOnTheBoard.add(piece);
	}
	
	private void initialSetup() {
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new king(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new king(board, Color.BLACK));
	}
	
	

}
