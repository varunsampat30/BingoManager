public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();

        manager.printTickets();

        if(manager.makeTickets){
            manager.printNumberDistribution();
        }

        manager.playGame();
    }
}
