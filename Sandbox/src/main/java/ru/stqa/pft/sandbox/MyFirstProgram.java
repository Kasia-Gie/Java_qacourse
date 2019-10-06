package ru.stqa.pft.sandbox;

public class MyFirstProgram {

  public static void main(String[] args) {
    System.out.println("Hello, world!");
    Square s = new Square(5);
    System.out.println("Pole kwadratu o boku " + s.l + " = " + s.area());

    Rectangle r = new Rectangle(4, 6);

    System.out.println("Pole prostokąta o bokach " + r.a + " i " + r.b + " = " + r.area());
  }

}
