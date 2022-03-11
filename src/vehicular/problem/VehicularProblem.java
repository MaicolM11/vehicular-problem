package vehicular.problem;

import java.security.SecureRandom;
import vehicular.problem.models.Vehicle;
import vehicular.problem.views.BoardView;
import vehicular.problem.views.Window;

public class VehicularProblem {

    static int minimun_speed = 5, maximun_speed = 20;
    static int minimun_length = 5, maximun_length = 20;
    static SecureRandom r = new SecureRandom();

    public static void main(String[] args) {
        BoardView board = new BoardView();
        Window view = new Window(board);
        new Thread(view).start();
        addVehicles(board);
    }

    private static void addVehicles(BoardView board) {
        int vehicles = 100;
        try {
            for (int i = 0; i < vehicles; i++) {
                int tll = 2000 + r.nextInt(5000);
                int length = minimun_length + r.nextInt(maximun_length);
                int speed = minimun_speed + r.nextInt(maximun_speed);
                int direction = r.nextBoolean() ? 1 : -1;
                Vehicle v = new Vehicle(length, speed, direction, board);
                board.addVehicle(v);
                Thread.sleep(tll);
            }
        } catch (InterruptedException ex) { }
    }

}
