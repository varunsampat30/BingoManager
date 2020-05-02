public final class Constants {
    private Constants(){
        // not allowed
    }


    public static final int number_of_tickets = 20;
    public static final int num_rows = 3;
    public static final int num_cols = 10;
    public static final int num_digits = 10;
    public static final int total_digits = 100;

    public static final String row = "row";
    public static final String col = "col";
    public static final String full_house = "full";
    public static final String numbers_to_avoid = "43 47 48 62 87 88 93";
    // these are numbers you do not have clues for, which is why you want to avoid them on the tickets
    // can be changed as per Clue data set

    public static final int input_row = 1;
    public static final int input_col = 2;
    public static final int input_full_house = 3;


    public static final String tickets_file = "tickets.txt";
    public static final String distribution_file = "distribution.txt";
    public static final String board_file = "board.txt";
    public static final int board_store_frequency = 2;

}
