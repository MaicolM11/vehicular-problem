package vehicular.problem.models;

import java.awt.Color;

public class Vehicle {

    public Color color;

    protected int length;
    protected double speed, initial_speed;
    protected int x, y;
    protected int direction;

    private Board board;
    private boolean isOvertaking, overtake;
    private int time_overtaking;

    protected int move_i;

    public Vehicle(int length, double speed, int direction, Board b) {
        this.length = length;
        this.speed = initial_speed = speed;
        this.direction = direction;
        this.board = b;
        this.overtake = isOvertaking = false;
        this.color = new Color((int) (Math.random() * 0x1000000));
        move_i = -1;
    }

    public double getLength() { return length; }

    public int getX() { return x; }

    public int getY() { return y; }

    public int getDirection() { return direction; }

    public int getOtherLane() { return 0 == y ? 1 : 0; }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(int i) {
        this.move_i = i;
        // moveWithSpeed(speed); // 1 version
        checkOvertake(); // tercera version
        verifyVelocity(); // 1, y 2 version
    }

    // 2 version
    private void verifyVelocity() {
        double toMove = speed;
        Vehicle other = board.getNearestVehicle(this, false, this.direction);
        if (other != null) {
            double distance = Utils.getSpaceBetweenVehicles(this, other);
            if (this.move_i > other.move_i)
                distance += other.speed;
            if (distance < speed) {
                speed = other.speed;
                toMove = distance > Board.space_between_vehicles ? distance - Board.space_between_vehicles : 0;
                overtake = true;
            }
        }
        moveWithSpeed(toMove);
    }

    private void moveWithSpeed(double s) {
        board.via[y][x] = null;
        this.x += s * direction;
        if (x < 0 || x > Board.via_length - 1) {
            board.vehicles.remove(this);
            return;
        }
        board.via[y][x] = this;
    }

    private void checkOvertake() {
        if (!overtake) return;
        if (isOvertaking) {
            if (time_overtaking == 0) { // termina de rebasar
                changeLine();
                overtake = false;
            }
            time_overtaking--;
            return;
        }
        doOvertake();
    }

    private void doOvertake() {
        time_overtaking = board.getTimeToOvertake(this, board.getNearestVehicle(this, false, this.direction));
        
        if (time_overtaking == -1) return; // no puede
        
        Vehicle otherLaneCarBehind = board.getNearestVehicle(this, true, this.direction * -1);
        
        if(otherLaneCarBehind != null && Utils.checkIfLastCarPassed(this, otherLaneCarBehind)) 
            return;
        Vehicle otherLaneCar = board.getNearestVehicle(this, true, this.direction);

        if (otherLaneCar == null 
            || (!otherLaneCar.isOvertaking && Utils.checkIfNotCrash(this, otherLaneCar, time_overtaking))){
            changeLine();
        } 
    }

    private void changeLine() {
        board.via[y][x] = null;
        this.y = getOtherLane();
        isOvertaking = !isOvertaking;
        speed = initial_speed;
    }

}