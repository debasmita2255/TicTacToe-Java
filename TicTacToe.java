import java.util.Scanner;
import java.util.InputMismatchException;

public class TicTacToe{

    // this class handles all the game logic and manages the game board using an object of Board class
    private static Board board;
    private static Scanner sc = new Scanner(System.in);

    // starts the game by printing the welcome banner and taking the game mode input
    public static void main(String[] args){
        clearScreen();
        System.out.println("Welcome to " + Board.ANSI_RED + "TIC " + Board.ANSI_GREEN + "TAC " + Board.ANSI_BLUE + "TOE" + Board.ANSI_RESET);

        System.out.println("1. Computer (Unbeatable) vs Human\n2. Human vs Human\n");
        int choice = getValidIntInput(1, 2);

        playGame(choice);
    }

    // handles all the function calls needed to play the game, in appropriate sequence
    private static void playGame(int mode){
        board = new Board();
        int moveCount = 0;

        while (true){
            clearScreen();
            board.display();
            
            // check win or draw
            char winner = board.checkWin();
            if(winner != 'N'){
                System.out.println("WINNER: Player " + winner + "!");
                break;
            }
            if (board.isFull()){
                System.out.println("It's a Draw!");
                break;
            }

            // turns based on value of moveCount: even = X, odd = O 
            char currentSymbol = (moveCount%2 == 0) ? 'X' : 'O';
            
            if (mode==1 && currentSymbol=='X')
                makeBestComputerMove();
            else
                makeHumanMove(currentSymbol);

            moveCount++;
        }
    }

    // lets human player enter a valid move and updates board using respective symbol
    private static void makeHumanMove(char symbol){
        System.out.print("Player " + symbol + "'s turn. Enter an unoccupied position (1-9): ");
        while(true){
            String input = sc.nextLine();
            if (input.length()==1 && board.update(input.charAt(0), symbol)) 
                break;
            else
                System.out.print("Invalid input or occupied position. Try again: ");
        }
    }
    
    // this function makes each possible next move of computer and uses minimax function to find out the best move
    private static void makeBestComputerMove(){
        int bestScore = Integer.MIN_VALUE;
        char bestMove = ' ';

        for (int i = 1; i <= 9; i++){
            char move = (char)('0' + i);
            if (board.isAvailable(move)){
                board.update(move, 'X'); // make move
                int score = minimax(board, 0, false); // calculate score
                board.undoMove(move); // undo move

                if (score > bestScore){
                    bestScore = score;
                    bestMove = move;
                }
            }
        }
        board.update(bestMove, 'X');
    }

    // recursive function to calculate score of a move using minimax algorithm
    private static int minimax(Board board, int depth, boolean isMaximizing){ 
        // depth is like the efficiency factor which decides how fast/slow the computer wins/looses

        char result = board.checkWin();
        if(result == 'X') return 10 - depth; // computer wins
        if(result == 'O') return -10 + depth; // human wins
        if(board.isFull()) return 0; // draw

        if(isMaximizing){ // computer's turn (maximize score)
            int bestScore = Integer.MIN_VALUE;
            for (int i=1; i<=9; i++){
                char move = (char)('0' + i);
                if (board.isAvailable(move)){

                    board.update(move, 'X'); // make move
                    int score = minimax(board, depth + 1, false); // calculate score
                    board.undoMove(move); // undo move

                    bestScore = Math.max(score, bestScore);
                }
            }
            return bestScore;
        }
        else{ // human's turn (minimize score)
            int bestScore = Integer.MAX_VALUE;
            for (int i=1; i<=9; i++){
                char move = (char)('0' + i);
                if (board.isAvailable(move)){

                    board.update(move, 'O'); // make move
                    int score = minimax(board, depth + 1, true); // calculate score
                    board.undoMove(move); // undo move

                    bestScore = Math.min(score, bestScore);
                }
            }
            return bestScore;
        }
    }


    // utility functions:

    private static int getValidIntInput(int c1, int c2){
        int val;
        while(true){
            System.out.print("Enter choice (" + c1 + " or " + c2 + "): ");
            try{
                val = sc.nextInt(); sc.nextLine();
                if (val==c1 || val==c2) return val;
            }
            catch(InputMismatchException e){
                sc.nextLine(); 
            }
        }
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

