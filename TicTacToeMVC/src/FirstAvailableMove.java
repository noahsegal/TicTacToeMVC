import java.util.List;

public class FirstAvailableMove implements MoveStrategy {

	@Override
	public Move selectMove(BoardGame game) {
		List<Move> moves = game.generateMoves();
		if (moves.isEmpty()) return null;
		else return moves.get(0);
	}
	
}
