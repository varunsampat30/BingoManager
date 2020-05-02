import java.util.Random;


public class NumberCountManager {
    private int number_count[];

    public NumberCountManager() {
        number_count = new int[Constants.total_digits];

        for (int i = 0; i < Constants.total_digits; i++){
            number_count[i] = 0; // each digit has not been used
        }
    }

    private int getRand(int min, int max){
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }

    public int getNumberToAdd(int rangeID){
        // if rangeID == 0, must scan from 0-9
        int start_index = rangeID * 10; //
        int end_index =  ((rangeID + 1) * 10) -1; // if range == 0, endIndex = 9

//        int return_val = 0;
//        int max_value = Integer.MAX_VALUE;
//
//        for (int i = start_index; i < end_index; i++){
//            if(number_count[i] < max_value){
//                // this is the index to return (least used)
//                return_val = i;
//                max_value = number_count[i];
//            }
//        }

//        alternative to this is to generate a random index between start & end;
        // can randomize between the two
        int return_val = getRand(start_index, end_index);

        // also update number_count as this is the number to add
        number_count[return_val] += 1;
        return return_val;
    }

    public void removeNumber(int number){
        if(number_count[number] != 0){
            number_count[number] = number_count[number] - 1;
        }
    }

    public void printNumberDistribution() {
        for (int i = 0; i < Constants.total_digits; i++){
            System.out.println(i + " appeared " + number_count[i]);
        }

    }

    public String[] getDistribution(){
        String[] s = new String[Constants.total_digits];
        for (int i = 0; i < Constants.total_digits; i++){
            s[i] = new String();
            s[i] = i + " " + number_count[i] + "\n";
        }

        return s;
    }
}
