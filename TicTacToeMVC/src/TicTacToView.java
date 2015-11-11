import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class TicTacToView implements Observer {

	private int size = 3;
	private JFrame gameFrame;
	private JPanel gamePanel;
	private JLabel gameLabel;
	private JButton[][] gameButtons;
	
	public TicTacToView(TicTacToeController controller) {
		gameFrame = new JFrame();
		gamePanel = new JPanel();
		gameButtons = new JButton[size][size];
		
		gameLabel = new JLabel("TicTacToe: X vs. O");
		
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setSize(200, 200);
		
		gamePanel.setLayout(new GridLayout(size, size));
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				gameButtons[i][j] = new JButton();
				gamePanel.add( gameButtons[i][j] );
				gameButtons[i][j].addActionListener( controller );
			}
		}
		gameFrame.getContentPane().setLayout(new BorderLayout());
		gameFrame.getContentPane().add(gamePanel);
		gameFrame.getContentPane().add(gameLabel, BorderLayout.SOUTH);
		
		gameFrame.setVisible(true);
	}
	
	public Move getButtonLocation(JButton buttonSelect) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if ( gameButtons[i][j] == buttonSelect ) return new Move(i, j);
			}
		}
		return null;
	}
	
	public void endGame(String textResult) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				gameButtons[i][j].setEnabled(false);
			}
		}
		gameLabel.setText("Game Over: " + textResult);;
	}
	
	
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
