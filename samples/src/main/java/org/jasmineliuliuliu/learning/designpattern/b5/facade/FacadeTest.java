package org.jasmineliuliuliu.learning.designpattern.b5.facade;

public class FacadeTest {

  static class Drawer {

    void draw() {
      System.out.println("Draw a circle!");
    }
  }

  static class Color {

    void color() {
      System.out.println("Color it red!");
    }
  }

  static class Mounter {

    void mount() {
      System.out.println("Mount it!");
    }
  }

  static class Painting {

    Drawer drawer = new Drawer();
    Color color = new Color();
    Mounter mounter = new Mounter();

    public void paint() {
      System.out.println("Let me paint a picture!");
      drawer.draw();
      color.color();
      mounter.mount();
      System.out.println("How nice!");
    }
  }

  public static void main(String[] args) {
    System.out.println();
    Painting painting = new Painting();
    painting.paint();
  }
}
