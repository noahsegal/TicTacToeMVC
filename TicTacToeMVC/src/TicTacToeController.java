import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


public class TicTacToeController implements ActionListener {

	private TicTacToe model;
	private TicTacToView view;
	private int gameState;
	
	public TicTacToeController() {
		model = new TicTacToe(3);
		view = new TicTacToView(this);
		model.addObserver(view);	
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton buttonSelected = (JButton) e.getSource();
		gameState = model.play( view.getButtonLocation(buttonSelected) );
		
		switch (gameState) {
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
