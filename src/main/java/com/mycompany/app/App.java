package com.mycompany.app;

/** Sample application with testable utility method. */
public final class App {
  private App() {}

  public static String getGreeting() {
    return "Hello World 3!";
  }

  public static void main(String[] args) {
    System.out.println(getGreeting());
    System.out.println(getGreeting());
    System.out.println(getGreeting());
  }
}
