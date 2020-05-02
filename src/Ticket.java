import java.util.ArrayList;

public class Ticket {
    private int ticket_number;
    private int A[][];

    public Ticket(int input_ticket_number) {
        ticket_number = input_ticket_number;
        this.A = new int[Constants.num_rows][Constants.num_cols];
        for (int i = 0; i < Constants.num_rows; i ++){
            for (int j = 0; j < Constants.num_cols; j++){
                this.A[i][j] = -1; // not initialized!
            }
        }
    }

    public Ticket(String[] file_data){
        this.A = new int[Constants.num_rows][Constants.num_cols];
        // string s is passed from the file (by each row)
        ticket_number = Integer.valueOf(file_data[0]);

        for (int i = 0; i < Constants.num_rows; i ++){
            String[] row_numbers = file_data[i+1].split(" "); // " " is the delimiter
            for (int j = 0; j < Constants.num_cols; j++){
                this.A[i][j] = Integer.parseInt(row_numbers[j]);
            }
        }
    }

    public void add_number(int number, int row, int col){
        if(A[row][col] == -1){
            // valid
            A[row][col] = number;
        }
    }

    public void print_ticket(){
        System.out.println("Printing ticket number: " + this.ticket_number);
        for (int i = 0; i < Constants.num_rows; i ++){
            for (int j = 0; j < Constants.num_cols; j++){
                System.out.print(this.A[i][j] + " ");
            }
            System.out.println( );
        }
        System.out.println();
    }

    public int[] getRow(int rowNumber){

        int row[];
        if(rowNumber >= Constants.num_rows || rowNumber < 0){
            System.out.println("Row out of bounds. Please try again!");
            row = new int[1];
            row[0] = -1;
        } else{
            row = new int[Constants.num_cols];
            for (int col = 0; col < Constants.num_cols; col++){
                row[col] = this.A[rowNumber][col];
            }
        }


        return row;
    }

    public int[] getCol(int colNumber){
        int col[];
        if(colNumber >= Constants.num_cols || colNumber < 0){
            System.out.println("Column out of bounds. Please try again!");
            col = new int[1];
            col[0] = -1;
        } else{
            col = new int[Constants.num_rows];
            for (int row = 0; row < Constants.num_rows; row++){
                col[row] = this.A[row][colNumber];
            }
        }
        return col;
    }

    public String getTicket(){
        String returnString = new String();

        for (int i = 0; i < Constants.num_rows; i++){
            for (int j = 0; j < Constants.num_cols; j++){
                returnString += (this.A[i][j] + " ");
            }
            returnString += ("\n");
        }

        return returnString;
    }

    public int[] get_compressed_tickets(){
        int return_ticket[] = new int[Constants.num_rows*Constants.num_cols];
        for (int i = 0; i < Constants.num_rows; i++){
            for (int j = 0; j < Constants.num_cols; j++){
                return_ticket[(i*Constants.num_cols) + j] = this.A[i][j];
            }
        }

        return return_ticket;
    }
}
