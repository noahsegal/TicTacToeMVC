import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class TicTacToeView implements Observer {

	private int size = 3;
	private JFrame gameFrame;
	private JPanel gamePanel;
	private JLabel gameLabel;
	private JButton[][] gameButtons;
//	protected JButton newGameButton;
	
	
	/**
	 * Set up the GUI
	 * @param controller
	 */
	public TicTacToeView(TicTacToeController controller) {
		gameFrame = new JFrame();
		gamePanel = new JPanel();
		gameButtons = new JButton[size][size];
		
//		newGameButton = new JButton("New Game");
//		newGameButton.addActionListener(controller);
		
		gameLabel = new JLabel("TicTacToe: X vs. O");
		
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setSize(250, 250);
		
		gamePanel.setLayout(new GridLayout(size, size));
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				gameButtons[i][j] = new JButton();
				gamePanel.add( gameButtons[i][j] );
				gameButtons[i][j].addActionListener(controller);
			}
		}
		gameFrame.getContentPane().setLayout(new BorderLayout());
		gameFrame.getContentPane().add(gamePanel);
//		gameFrame.getContentPane().add(newGameButton, BorderLayout.NORTH);
		gameFrame.getContentPane().add(gameLabel, BorderLayout.SOUTH);
		
		gameFrame.setVisible(true);
	}
	
	/**
	 * Get a Move object with coordinates of the selected button
	 * @param buttonSelect
	 * @return
	 */
	public Move getButtonLocation(JButton buttonSelect) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if ( gameButtons[i][j] == buttonSelect ) return new Move(i, j);
			}
		}
		return null;
	}
	
	/**
	 * Disable buttons on the board and display the winner
	 * @param textResult
	 */
	public void endGame(String textResult) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				gameButtons[i][j].setEnabled(false);
			}
		}
		gameLabel.setText("Game Over: " + textResult);;
	}
	
//	public void newGame() {
//		for (int i = 0; i < size; i++) {
//			for (int j = 0; j < size; j++) {
//				gameButtons[i][j].setEnabled(true);
//				gameButtons[i][j].setText("");
//			}
//		}
//	}
	
	
	@Override
	public void update(Observable o, Object arg) {
		char [][] grid = ( char[][] ) arg;
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if ( grid[i][j] == '_') gameButtons[i][j].setText("");
				else gameButtons[i][j].setText( grid[i][j] + "" );
			}
		}
	}
	
}
