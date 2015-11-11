import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


public class TicTacToeController implements ActionListener {

	private TicTacToe model;
	private TicTacToeView view;
	private int modelState;
	
	public TicTacToeController() {
		model = new TicTacToe(3);
		view = new TicTacToeView(this);
		model.addObserver(view);	
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
//		if (e.getSource() == view.newGameButton) {
//			model = new TicTacToe(3);
//			view = new TicTacToeView(this);
//			view.newGame();
//			if (model.getTurn() == 'o') model.toggleTurn();
//		}
//		else {
		JButton buttonSelected = (JButton) e.getSource();

		modelState = model.play( view.getButtonLocation(buttonSelected) );
		if (modelState == BoardGame.ONGOING) {
			model.toggleTurn();
			modelState = model.machinePlay();
		}
		model.toggleTurn();
		isGameOver(modelState);
	}
//	}
	
	/**
	 * Determine whether the game is over/ongoing
	 * @param modelState
	 */
	private void isGameOver(int modelState) {
		switch (modelState) {
		case BoardGame.X_WON:
			view.endGame("X WON");
			break;
		case BoardGame.O_WON:
			view.endGame("O WON");
			break;
		case BoardGame.TIE:
			view.endGame("TIE");
			break;
		default:
			break;
		}
	}
	
	public static void main( String[] args ) {
		TicTacToeController controller = new TicTacToeController();
	}

}
