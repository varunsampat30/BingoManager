public class BoardGameManager {
    private int board[];

    public BoardGameManager() {
//        System.out.print(Constants.total_digits);
        board = new int[Constants.total_digits];
        for (int i = 0; i < Constants.total_digits; i++){
            board[i] = 0;
        }
    }

    public boolean updateBoard(int index){
        if(index < 0 || index >= Constants.total_digits){
            System.out.println("Index to update out of bounds!");
            return false;
        }
        if (board[index] == 0){
            // valid move
            System.out.println("Valid move at index " + index + ". Updating board!");
            board[index] = 1;
            return true;
        } else{
            System.out.println("Invalid move at index " + index + ". Not updating board!");
            return false;
        }
    }

    public boolean movePlayed(int index){
        if(index < 0 || index >= Constants.total_digits){
            System.out.println("Index to check out of bounds!");
            return false;
        }
        if (board[index] == 0){
            // valid move
            System.out.println("Move has not been played at " + index);
            return false;
        } else{
            System.out.println("Move has been played at " + index);
            return true;
        }
    }

    public boolean areNumbersPlayed(int[] numbers){
        int size = numbers.length;
        for (int i = 0; i < size; i++){
            if (!movePlayed(numbers[i])){
                return false;
            }
        }
        return true;
    }

    public void printBoard(){
        for(int i = 0; i < Constants.num_digits; i++){
            if (i == 0){
                System.out.print("    ");
            }
            System.out.print( (i) + " ");
        }
        for (int i = 0; i < Constants.total_digits; i++){
            if( i%Constants.num_digits == 0){
                System.out.println();
                System.out.print( (i/Constants.num_digits) + "   " + board[i] + " ");
            } else{
                System.out.print(board[i] + " ");
            }
        }
        System.out.println();
    }

    public String[] getBoard(){
        String[] s = new String[Constants.num_digits]; // assuming a square grid (10 digits in TENs, 10 digits in ONEs)

        int index = 0;
        for (int i = 0; i < Constants.total_digits; i++){
            if((i%Constants.num_digits == 0)){
                if(i != 0){
                    index += 1; // store in mem for next line
                    s[index] = "\n" + board[i] + " ";
                } else {
                    s[index] = board[i] + " ";
                }

            } else{
                s[index] += board[i] + " ";
            }
        }

        return s;
    }
}
