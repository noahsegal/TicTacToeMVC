import java.util.List;

public class GreedyMove implements MoveStrategy {

    public Move selectMove(BoardGame game) {
        List<Move> allMoves = game.generateMoves();
        
        if (allMoves.isEmpty()) return null;
        
        int bestMoveScore = 0;
        Move bestMove = null; 
  
        for (Move tempMove : allMoves) {
            int score = game.evaluateMove(tempMove);
            
            if (score >= bestMoveScore) {
                bestMove = tempMove;
                bestMoveScore = score;
            }
        }
        return bestMove;
    }
}
