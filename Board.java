public class Board{

    // the Board class specifically handles the game board and all actions/checks that need to be performed on it or using it
    // it's a helper class for the the TicTacToe class

    private char[][] grid;
    public static final String ANSI_RESET = "\u001B[0m", ANSI_BLUE = "\u001B[34m", ANSI_RED = "\u001B[31m", ANSI_GREEN = "\u001B[32m";

    public Board(){
        grid = new char[][]{{'1', '2', '3'}, {'4', '5', '6'}, {'7', '8', '9'}};
    }

    // displays the game board using appropriate colour
    public void display(){
        System.out.println("\n");
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                String color = "";
                if(Character.isDigit(grid[i][j])) color = ANSI_BLUE;
                else if(grid[i][j] == 'X') color = ANSI_RED;
                else if(grid[i][j] == 'O') color = ANSI_GREEN;

                System.out.print("   " + color + grid[i][j] + ANSI_RESET + "   ");
                if(j<2)
                    System.out.print("|");
            }
            System.out.println();
            if(i<2)
                System.out.println("-----------------------");
        }
        System.out.println("\n");
    }

    // place a move by updating the board
    public boolean update(char position, char symbol){
        int[] indices = getIndices(position);
        if (indices == null) return false; // invalid position
        
        int r = indices[0], c = indices[1];

        if (grid[r][c] != position) return false; // already occupied
        grid[r][c] = symbol;
        return true;
    }

    // used by AI to backtrack (undo a move)
    public void undoMove(char position){
        int[] indices = getIndices(position);
        if (indices != null)
            grid[indices[0]][indices[1]] = position; // put the number back
    }

    // helper function to take position from 1-9 and return corresponding indices in matrix
    private int[] getIndices(char position){
        for (int i=0; i<3; i++)
            for (int j=0; j<3; j++)
                if (position == (char)('1' + (i*3 + j)))
                     return new int[]{i, j};
        
        return null;
    }
    
    // check if a specific cell is available or occupied
    public boolean isAvailable(char position){
         int[] idx = getIndices(position);
         return idx!=null && Character.isDigit(grid[idx[0]][idx[1]]);
    }

    // check if a player has made a combination of 3 in the board, hence has won
    // returns the symbol of the player who has won, otherwise returns 'N'
    public char checkWin(){
        for (int i=0; i<3; i++) {
            if (grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2]) return grid[i][0];
            if (grid[0][i] == grid[1][i] && grid[1][i] == grid[2][i]) return grid[0][i];
        }
        if (grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2]) return grid[0][0];
        if (grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0]) return grid[0][2];
        return 'N';
    }
    
    // check if board is full resulting in a draw
    public boolean isFull() {
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++)
                if(Character.isDigit(grid[i][j])) return false;
            
        return true;
    }
}