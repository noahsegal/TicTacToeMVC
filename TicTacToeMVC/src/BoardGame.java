import java.util.*;

public abstract class BoardGame extends Observable {
	
    public static final int ILLEGAL = 0;
    public static final int ONGOING = 1;
    public static final int TIE = 2;
    public static final int X_WON = 3;
    public static final int O_WON = 4;
    public static final int NO_MOVE = 5;
    public static final int GAME_OVER = 6;
    
    public static final int GOOD_MOVE = 1;
    public static final int BAD_MOVE = -1;
    public static final int NEUTRAL = 0;

    private int size;
    protected int status;
    protected char[][] grid;
    protected char turn;
    private MoveStrategy strategy;
    
    public BoardGame(int size) {
        status = ONGOING;
        this.size = size;
        grid = new char[size][size];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = '_';
            }
        }
        //strategy = new FirstAvailableMove();
        strategy = new GreedyMove();
        turn = 'x';
    }
    
    public BoardGame(int size, MoveStrategy gameStrategy) {
        this(size);
        strategy = gameStrategy;
    }
    
    public BoardGame(BoardGame copyGame)
    {
    	size = copyGame.size;
    	status = copyGame.status;
    	turn = copyGame.turn;
    	strategy = copyGame.strategy;
    	
    	grid = new char[size][size];
    	for (int i = 0; i < size; i ++) {
    		for (int j = 0; j < size; j++) {
    			grid[i][j] = copyGame.grid[i][j];
    		}
    	}
    }
    
    public void toggleTurn() {
        if (turn == 'x') turn = 'o';
        else turn = 'x';
    }
    
    
    //Abstract method to set the Move Strategy
    public void setMoveStrategy(MoveStrategy newStrategy) {strategy = newStrategy;}
    
    //Abstract method to evaluate Moves
    protected abstract int evaluateMove(Move possibleMove);
    
    protected abstract boolean canPlay(Move m);
    
    public abstract int play(Move m);
    
    protected abstract void determineWinner();
    
    
    public char getDisc(int row, int col) {
    	return grid[row][col];
    }
    
    public int getSize() {
    	return size;
    }
    
    public char getTurn() {
    	return turn;
    }
    
    protected List<Move> generateMoves() {
        List<Move> moves = new ArrayList<Move>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Move m = new Move(i,j);
                if (canPlay(m)) {
                    moves.add(m);
                }
            }
        }
        return moves;
    }
    
    protected void displayStatus(int s) {
        switch (s) {
            case X_WON: System.out.println("X won!"); print(); break;
            case O_WON: System.out.println("O won!"); print(); break;
            case TIE: System.out.println("It's a tie!"); print(); break;
            case ILLEGAL: System.out.println("Illegal move!"); break;
            case ONGOING: break;
            default: break;
        }
    }
    
    protected int machinePlay() {
    	//Move move = machineFirstAvailableMove();
        //Move move = machineRandomMove();
    	
    	Move move = strategy.selectMove(this);
        if (move == null) return NO_MOVE; //game loop should prevent this from happening
        else return play(move);
    }

    
    protected Move getMove(String input) {
        int row;
        int col;
        String[] smove = input.split("[, ]+");
        if (smove.length != 2)  return null;
        try {
            row = Integer.parseInt(smove[0]);
            col = Integer.parseInt(smove[1]);
        }
        catch (Exception e) {return null;}
        return new Move(row,col);
    }
    
    
    /**
     * Clear the board and start a new game (starting with X)
     */
    protected void newGame() {
    	 for (int i = 0; i < grid.length; i++) {
             for (int j = 0; j < grid.length; j++) {
                 grid[i][j] = '_';
             }
         }
    	 turn = 'x';
    }
    
    public String toString() {
        String s = "   ";
        for (int i = 0; i < size; i++) {
        	s += (i + " ");
        }
        s += "\n";
        for (int i = 0; i < grid.length; i++) {
        	s += (i + "| ");
            for (int j = 0; j < grid.length; j++) {
                s += grid[i][j] + " ";
            }
            s += "\n";
        }
        return s;
    }
    
    
    public void print() {
        System.out.println(this);
    }
}
