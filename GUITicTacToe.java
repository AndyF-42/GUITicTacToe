/*
 * Standard game of Tic Tac Toe, this time
 * with a GUI. Players also have the option
 * to add a name. Program keeps track of how
 * many wins each player has.
 * Author: Andy Fleischer
 * Date: 9/20/2019
 */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUITicTacToe implements ActionListener {

	//Variables
	JFrame frame = new JFrame();
	JButton[][] buttons = new JButton[3][3];
	int[][] board = new int[3][3];
	final int BLANK = 0;
	final int X_MOVE = 1;
	final int O_MOVE = 2;
	final int X_TURN = 0;
	final int O_TURN = 1;
	int turn = 0;
	Container center = new Container();
	JLabel xLabel = new JLabel("X's wins: 0");
	JLabel oLabel = new JLabel("O's wins: 0");
	JButton xChangeName = new JButton("Change X's Name");
	JButton oChangeName = new JButton("Change O's Name");
	JTextField xChangeField = new JTextField();
	JTextField oChangeField = new JTextField();
	Container north = new Container();
	String xPlayerName = "X";
	String oPlayerName = "O";
	int xWins = 0;
	int oWins = 0;
	
	//Constructor for GUITicTacToe
	public GUITicTacToe() {
		//Set fonts of text and make the frame
		xLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		oLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		xChangeName.setFont(new Font("Arial", Font.PLAIN, 20));
		oChangeName.setFont(new Font("Arial", Font.PLAIN, 20));
		frame.setSize(800, 800);
		//Center grid container
		frame.setLayout(new BorderLayout());
		center.setLayout(new GridLayout(3, 3));
		//Create fonts and add action listeners to the buttons, also makes them clickable
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[0].length; j++) {
				buttons[i][j] = new JButton();
				buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 100));
				center.add(buttons[i][j]);
				buttons[i][j].addActionListener(this);
			}
		}
		frame.add(center, BorderLayout.CENTER);
		//North container (name stuff
		north.setLayout(new GridLayout(3, 2));
		north.add(xLabel);
		north.add(oLabel);
		north.add(xChangeName);
		xChangeName.addActionListener(this);
		north.add(oChangeName);
		oChangeName.addActionListener(this);
		north.add(xChangeField);
		north.add(oChangeField);
		frame.add(north, BorderLayout.NORTH);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	//Main method to start the program
	public static void main(String[] args) {
		new GUITicTacToe();
	}

	//Action listener event (change name button or grid button)
	@Override
	public void actionPerformed(ActionEvent event) {
		JButton current;
		boolean wasGridButton = false;
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[0].length; j++) {
				//Checks to see if event was a grid button
				if (event.getSource().equals(buttons[i][j])) {
					wasGridButton = true;
					current = buttons[i][j];
					current.setEnabled(false);
					//Write X or O on the button depending on which turn it is
					if (board[i][j] == BLANK) {
						if (turn == X_TURN) {
							current.setText("X");
							board[i][j] = X_MOVE;
							turn = O_TURN;
						}
						else {
							current.setText("O");
							board[i][j] = O_MOVE;
							turn = X_TURN;
						}
						//Check for win on either side or tie
						if (checkWin(X_MOVE)) {
							xWins++;
							xLabel.setText(xPlayerName + "'s wins: " + xWins);
							clearBoard();
						}
						else if (checkWin(O_MOVE)) {
							oWins++;
							oLabel.setText(oPlayerName + "'s wins: " + oWins);
							clearBoard();
						}
						else if (checkTie()) {
							clearBoard();
						}
					}
				}
			}
		}
		//Change name buttons (get the text that's in the change name field and change the name)
		if (!wasGridButton) {
			if (event.getSource().equals(xChangeName)) {
				xPlayerName = xChangeField.getText();
				if (!xPlayerName.equals("")) {
					xLabel.setText(xPlayerName + "'s wins: " + xWins);
				}
			}
			else if (event.getSource().equals(oChangeName)) {
				oPlayerName = oChangeField.getText();
				if (!oPlayerName.equals("")) {
					oLabel.setText(oPlayerName + "'s wins: " + oWins);
				}
			}
		}
	}

	//Checks all wins
	public boolean checkWin(int player)
	{
		if (board[0][0] == player && board[0][1] == player && board[0][2] == player)
		{
			return true;
		}
		else if (board[1][0] == player && board[1][1] == player && board[1][2] == player)
		{
			return true;
		}
		else if (board[2][0] == player && board[2][1] == player && board[2][2] == player)
		{
			return true;
		}
		else if (board[0][0] == player && board[1][0] == player && board[2][0] == player)
		{
			return true;
		}
		else if (board[0][1] == player && board[1][1] == player && board[2][1] == player)
		{
			return true;
		}
		else if (board[0][2] == player && board[1][2] == player && board[2][2] == player)
		{
			return true;
		}
		else if (board[0][0] == player && board[1][1] == player && board[2][2] == player)
		{
			return true;
		}
		else if (board[0][2] == player && board[1][1] == player && board[2][0] == player)
		{
			return true;
		}
		
		return false;
	}
	
	//Checks for tie, returns boolean true or false
	public boolean checkTie()
	{
		for (int row = 0; row < board.length; row++)
		{
			for (int col = 0; col < board[0].length; col++)
			{
				//If any blanks are found, it's not a tie
				if (board[row][col] == BLANK)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	//Clears board of any X's or O's, sets move to X
	public void clearBoard() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				board[i][j] = BLANK;
				buttons[i][j].setText("");
				buttons[i][j].setEnabled(true);
			}
		}
		turn = X_TURN;
	}
}