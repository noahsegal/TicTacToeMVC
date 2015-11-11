/**
 * One-player version of Tic Tac Toe, against terrible AI
 * 
 * @author babak
 * @version 0.0
 */
 
import java.util.*;

public class TicTacToe extends BoardGame {
    public static int SIZE = 3;


    public TicTacToe(int size) {
        super(size, new GreedyMove());
    }
    
    public TicTacToe(TicTacToe copyGame) {
    	super(copyGame);
    }
    
    
    protected boolean canPlay(Move m) {
        int row = m.getRow(); 
        int col = m.getCol();
        
        if ((row < 0) || (row >= SIZE) || (col < 0) || (col >= SIZE)) return false;
        if (grid[row][col] != '_') return false;
        
        return true;
    }
    
    public int play(Move m) {
        if (!canPlay(m)) return ILLEGAL; 
        
        int row = m.getRow(); 
        int col = m.getCol();
        
        grid[row][col] = turn;
        
        // Redraw and Inform Observer 
        this.setChanged();
        this.notifyObservers(grid);
        
        if (checkRow(row) || checkCol(col) || checkFirstDiag() || checkSecondDiag()) {
            if (turn == 'x') return X_WON;
            else return O_WON;
        }
        
        if (checkTie()) return TIE;
        
        toggleTurn();
        return ONGOING;
    }
    
    protected int evaluateMove(Move possibleMove)
    {
    	 TicTacToe gameCopy = new TicTacToe(this);
    	 switch (gameCopy.play(possibleMove))
 		{
 			case X_WON:
 				if (turn == 'x')
 					return GOOD_MOVE;
 				else
 					return BAD_MOVE;
 			case O_WON:
 				if (turn == 'o')
 					return GOOD_MOVE;
 				else
 					return BAD_MOVE;
 				
 			default: return NEUTRAL;
 		}	
    	
    }
        
    public void loop() {
        //int status = ONGOING;
        Scanner sc = new Scanner(System.in);
        Move move;
        
        System.out.println("Enter 'x' if you want to start");
        if (!(sc.nextLine().equals("x"))) {
            System.out.println("OK, I'll start then");
            status = machinePlay();
            toggleTurn();
        }
        
        while (status == ONGOING) {
           print();
           do {
               System.out.println("Enter your move: <row, col> or quit <q>");
               move = getMove(sc.nextLine());
               if (move == null) {
                   System.out.println("Bye bye!");
                   return;
               }
            }
           while (!(canPlay(move)));
           status = play(move);
           
           if (status == ONGOING) {
               print();
               System.out.println("Computer's turn: ");
               toggleTurn();
               status = machinePlay();
           }           
           toggleTurn();
        }
        
        determineWinner();
        displayStatus(status);
        System.out.println("Bye bye!");
           
    }
    
    private boolean checkRow(int i) {
        for (int j = 0; j < SIZE; j++) {
            if (grid[i][j] != turn) return false;
        }
        return true;
    }
    
    private boolean checkCol(int i) {
        for (int j = 0; j < SIZE; j++) {
            if (grid[j][i] != turn) return false;
        }
        return true;
    }
    
    private boolean checkFirstDiag() {
        for (int i = 0; i < SIZE; i++) {
            if (grid[i][i] != turn) return false;
        }
        return true;
    }
    
    private boolean checkSecondDiag() {
        for (int i = 0; i < SIZE; i++) {
            if (grid[i][SIZE - i - 1] != turn) return false;
        }
        return true;
    }
    
    private boolean checkTie() {
    	for (int i = 0; i < SIZE; i++) {
    		for (int j = 0; j < SIZE; j++) {
    			if (grid[i][j] == '_') return false;
    		}
    	}
    	return true;
    }
    
    protected void determineWinner() {
        if (status == NO_MOVE) status = TIE;
        //otherwise status is already clear, no need to do anything
    }
    
    public static void main(String[] args) {
        (new TicTacToe(SIZE)).loop();
    }
}
