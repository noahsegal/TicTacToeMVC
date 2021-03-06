import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


public class TicController implements ActionListener {

	private TicModel model;
	private TicView view;
	private int modelState;
	
	public TicController() {
		model = new TicModel(TicModel.SIZE);
		view = new TicView(this);
		model.addObserver(view);	
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// Handle Button: Start a new game
		if (e.getSource() == view.newGameButton) {
			model.newGame();
			view.newGame();
			if (model.getTurn() == 'o') model.toggleTurn();
		}

		// Handle Button: Change the CPU strategy
		else if (e.getSource() == view.greedy) {
			model.setMoveStrategy(new GreedyMove());
			view.strategyName.setText("Greedy");
		}
		else if (e.getSource() == view.firstAvailable){
			model.setMoveStrategy(new FirstAvailableMove());
			view.strategyName.setText("First Available");
		}

		// Handle Button: Take a turn
		else {
			JButton buttonSelected = (JButton) e.getSource();

			modelState = model.play( view.getButtonLocation(buttonSelected) );
			if (modelState == BoardGame.ONGOING) {
				model.toggleTurn();
				modelState = model.machinePlay();
				model.toggleTurn();
			}
			isGameOver(modelState);
		}
	}
	
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
		TicController controller = new TicController();
	}

}
