package ru.stqa.pft.sandbox;

public class Point {
    public double x;
    public double y;



    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Point secondPoint) {
        return Math.sqrt(Math.pow((secondPoint.x - this.x), 2) + Math.pow((secondPoint.y - this.y), 2));
    }
}
