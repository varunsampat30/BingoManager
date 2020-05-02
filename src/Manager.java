import java.util.*;

public class Manager {
    private NumberCountManager numberCountManager;
    private BoardGameManager boardGameManager;
    private Ticket ticket[];
    private FileManager fileManager;
    boolean makeTickets;

    public Manager() {
        makeTickets = true; // either generate tickets or load them from tickets.txt
        ticket = new Ticket[Constants.number_of_tickets]; // array of tickets
        fileManager = new FileManager(); // manages file I/O


        if(makeTickets){

            // making data struct that keeps track of which numbers must not be generated on ticket
            HashMap<Integer, Integer> numbers_to_avoid = new HashMap<>();
            String[] numbers_to_avoid_on_tickets = Constants.numbers_to_avoid.split(" ");

            for (int i = 0; i < numbers_to_avoid_on_tickets.length; i++){
                numbers_to_avoid.put(Integer.parseInt(numbers_to_avoid_on_tickets[i]), 1);
            }

            for (int i = 0; i < Constants.number_of_tickets; i++){
                ticket[i] = new Ticket(i+1);
            }
            numberCountManager = new NumberCountManager(); // this distribution can be done while generation the tickets, not otherwise
            // can be adjusted however, to scan tickets file. not implemented yet

            for (int i = 0; i < Constants.num_cols; i++) {
                for (int k = 0; k < Constants.number_of_tickets; k++) {
                    // make each ticket
                    HashMap<Integer, Integer> numbers_to_add = new HashMap<>(); // this data struct ensures the same ticket is not generated in the same col

                    for (int j = 0; j < Constants.num_rows; j++){
                        int number_to_add = numberCountManager.getNumberToAdd(i);

                        while(numbers_to_add.containsKey(number_to_add) || numbers_to_avoid.containsKey(number_to_add)){
                            numberCountManager.removeNumber(number_to_add);
                            number_to_add = numberCountManager.getNumberToAdd(i);
                        }

                        numbers_to_add.put(number_to_add, 1);
                    }

                    List<Integer> numbers_list = new ArrayList<>(numbers_to_add.keySet());

                    Collections.sort(numbers_list); // in housie, numbers are sorted in ascending order down the col

                    for (int j = 0; j < Constants.num_rows; j++){
                        ticket[k].add_number(numbers_list.get(j), j, i);
                    }
                }
            }

            fileManager.makeFile(Constants.distribution_file);
            fileManager.writeToFile(Constants.distribution_file, numberCountManager.getDistribution());

        }

        boolean loadTickets = !makeTickets; // either load or upload

        if(!loadTickets){
            System.out.println("UPLOADING TICKETS");
            fileManager.makeFile(Constants.tickets_file);
            String toPush = "1" + "\n" + ticket[0].getTicket();
            for (int i = 1; i < Constants.number_of_tickets; i++){
                toPush += "\n" + (i+1) + "\n" + ticket[i].getTicket();
                if(i == Constants.number_of_tickets-1){
                    toPush += "\n";
                }
            } fileManager.writeToFile_Single(Constants.tickets_file, toPush);
            System.out.println("DONE UPLOADING TICKETS");
        } else{
            // load file
            if(fileManager.doesFileExist(Constants.tickets_file)){
                List<String> file_data = fileManager.readFromFile(Constants.tickets_file);
                System.out.println("LOADING TICKETS");

                for (int i = 0; i < Constants.number_of_tickets; i++){
                    String[] s = new String[Constants.num_rows+1];
                    for (int j = 0; j < Constants.num_rows + 2; j++){
                        if(j != Constants.num_rows + 2 - 1){
                            s[j] = file_data.get(i*5 + j);
                        }

                    } ticket[i] = new Ticket(s);
                }
                System.out.println("DONE LOADING TICKETS");
            }
        }


        // once tickets are made, make board game manager. This is what is used to manage the rest of the game
        boardGameManager = new BoardGameManager();


    }

    private void saveBoard(){
        if(!fileManager.doesFileExist(Constants.board_file)){
            // if it does not exist
            fileManager.makeFile(Constants.board_file);
        }

        // file exists,
        fileManager.writeToFile(Constants.board_file, boardGameManager.getBoard());

    }

    public void printTickets(){
        for (int i = 0; i < Constants.number_of_tickets; i++){
            ticket[i].print_ticket();
        }
    }

    public void printNumberDistribution(){
        numberCountManager.printNumberDistribution();
    }

    public void playGame(){
        boolean gameOver = false;
        Scanner scanner = new Scanner(System.in);

        int board_store_counter = 0;

        while (!gameOver){

            System.out.println("Which number(s) is next? (to enter multiple numbers, enter numbers with spaces in between.)");
            String s = scanner.nextLine();
            String[] numbers_input = s.split(" ");

            for (int i = 0; i < numbers_input.length; i++){
                int input = Integer.parseInt(numbers_input[i]);
                if(!boardGameManager.movePlayed(input)){
                    boardGameManager.updateBoard(input);
                }
            }

            boardGameManager.printBoard();

            if(board_store_counter == Constants.board_store_frequency){
                saveBoard();
                board_store_counter = 0;
            } else{
                board_store_counter += 1;
            }


            // board is updated.. any claims?
            boolean claims = true;
            while (claims){
                System.out.println("Any takers? If so, enter (ticket number, ROW/COL/FULL, #). If none, then enter -1 to play the next number");
                String s1 = scanner.nextLine();
                String[] inputs = s1.split(" ");

                int ticketNumber = Integer.parseInt(inputs[0]);
                if(ticketNumber == -1){
                    claims = false;
                } else if (inputs.length != 3){
                    System.out.println("Invalid input! please try again");
                } else if(ticketNumber < 1 || ticketNumber > Constants.number_of_tickets ){
                    System.out.println("Ticket number is out of bounds!");
                } else {
                    ticketNumber = ticketNumber-1;
                    String input = inputs[1].toLowerCase();
                    int numberToRemove = Integer.parseInt(inputs[2]) - 1;
                    int numbersToCheck[];
                    int class_input = 0;


                    if(input.equals(Constants.row)){
                        numbersToCheck = ticket[ticketNumber].getRow(numberToRemove);
                        class_input = Constants.input_row;
                    } else if(input.equals(Constants.col)){
                        numbersToCheck = ticket[ticketNumber].getCol(numberToRemove);
                        class_input = Constants.input_col;
                    } else if(input.equals(Constants.full_house)){
                        numbersToCheck = ticket[ticketNumber].get_compressed_tickets();
                        class_input = Constants.input_full_house;
                    } else {
                        System.out.println("Error! incorrect input");
                        numbersToCheck = new int[1];
                        numbersToCheck[0] = -1;
                    }

                    if(numbersToCheck[0] != -1){
                        boolean correctClaim = boardGameManager.areNumbersPlayed(numbersToCheck);
                        ticketNumber += 1;
                        numberToRemove += 1;
                        if(correctClaim){
                            if (class_input == Constants.input_full_house){
                                System.out.println("Correct claim! Ticket #" + ticketNumber + " has claimed a full house");
                            } else{
                                String lol = (class_input == Constants.input_row) ? Constants.row: Constants.col;
                                System.out.println("Correct claim! Ticket #" + ticketNumber + " does have numbers crossed out at " + lol + numberToRemove);
                            }

                        } else {
                            if (class_input == Constants.input_full_house){
                                System.out.println("Incorrect claim! Ticket #" + ticketNumber + " has claimed a full house");
                            } else{
                                String lol = (class_input == Constants.input_row) ? Constants.row: Constants.col;
                                System.out.println("Incorrect claim! Ticket #" + (ticketNumber) + " does not have numbers crossed out at " + lol + numberToRemove);
                            }


                        }
                    }

                }

            }
        }
    }

}

