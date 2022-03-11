package vehicular.problem.models;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Board {

    public static final int via_length = 1000; // m
    public static final int scope = 50; // m
    public static final int space_between_vehicles = 1;

    protected Vehicle[][] via;
    protected Queue<Vehicle> vehicles;

    public Board() {
        this.via = new Vehicle[2][via_length];
        vehicles = new ConcurrentLinkedQueue<>();
    }

    public void addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
        int x = 0, y = 0;
        if (vehicle.direction == -1) {
            y = 1;
            x = via_length - 1;
        }
        via[y][x] = vehicle;
        vehicle.setPosition(x, y);
    }

    public Vehicle getNearestVehicle(Vehicle vehicle, boolean otherLane, int direction) {
        int lane = otherLane ? vehicle.getOtherLane() : vehicle.y;
        for (int i = vehicle.x, s = 0; i < via_length && i > 0; i += direction, s++) {
            if (i == vehicle.x)
                continue;
            if (s == scope)
                break;
            if (via[lane][i] != null)
                return via[lane][i];
        }
        return null;
    }

    public int getTimeToOvertake(Vehicle initial, Vehicle actual) {
        
        int distance = Utils.getDistanceBetweenVehicles(actual, initial);
        
        if (distance > scope) return -1;
        
        int space = Utils.getSpaceOfVehicle(initial);
        int time = Utils.getTimeToOvertake(initial, actual);
        Vehicle nearestVehicle = getNearestVehicle(actual, false, actual.direction);

        // a b
        if (nearestVehicle == null)
            return time;
            // a b c d
        int spaceBetweenCars = Utils.getSpaceBetweenVehicles(actual, nearestVehicle);
        double dt = nearestVehicle.speed * time - actual.speed * time;
        if (spaceBetweenCars + dt >= space) {
            return time;
        }

        return getTimeToOvertake(initial, nearestVehicle);
    }

}
