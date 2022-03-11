package vehicular.problem.models;

public class Utils {

    // tiempo para alcanzar y rebasar
    public static int getTimeToOvertake(Vehicle a, Vehicle b) {
        double time = Math.abs(b.x - a.x) / (a.initial_speed - b.speed); // alcanzar
        time += (a.length + Board.space_between_vehicles * 2) / (a.initial_speed - b.speed);
        return (int) Math.ceil(Math.abs(time));
    }

    // distancia entre dos posiciones
    public static int getDistanceBetweenVehicles(Vehicle a, Vehicle b) {
        return Math.abs(a.x - b.x);
    }

    // espacio entre dos vehiculos
    public static int getSpaceBetweenVehicles(Vehicle a, Vehicle b) {
        return getDistanceBetweenVehicles(a, b) - b.length;
    }

    // espacio que ocupa un vehiculo
    public static int getSpaceOfVehicle(Vehicle v) {
        return v.length + Board.space_between_vehicles * 2;
    }

    // Posicion x, depues de t tiempo
    public static int getPositionAfterTime(Vehicle v, int time) {
        return (int) (v.x + v.speed * time * v.direction);
    }

    public static boolean checkIfNotCrash(Vehicle a, Vehicle b, int time) {

        int a_position = getPositionAfterTime(a, time);
        int b_position = getPositionAfterTime(b, time);
        int diference_before = a.x - b.x;
        int diference_after = a_position - b_position;

        return (diference_after < 0 && diference_before < 0) || diference_after > 0 && diference_before > 0;
    }

    public static boolean checkIfLastCarPassed(Vehicle vehicle, Vehicle otherLaneCarBehind) {
        int final_vehicle_x = otherLaneCarBehind.x + otherLaneCarBehind.length * otherLaneCarBehind.direction * -1;
    
        return (vehicle.x < otherLaneCarBehind.x && vehicle.x < final_vehicle_x) || 
        (vehicle.x > otherLaneCarBehind.x && vehicle.x > final_vehicle_x);
    }
}
