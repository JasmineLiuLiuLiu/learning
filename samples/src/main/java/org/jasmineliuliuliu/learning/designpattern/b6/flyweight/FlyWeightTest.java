package org.jasmineliuliuliu.learning.designpattern.b6.flyweight;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

public class FlyWeightTest {

  @Data
  @AllArgsConstructor
  static class UnsharedFlyWeight {

    private String specificInfo;

  }

  interface FlyWeight {

    void operation(UnsharedFlyWeight unsharedFlyWeight);
  }

  static class ConcreteFlyWeight implements FlyWeight {

    private final String key;

    ConcreteFlyWeight(String key) {
      this.key = key;
      System.out.println("Fly Weight " + key + " is constructed!");
    }

    @Override
    public void operation(UnsharedFlyWeight unsharedFlyWeight) {
      System.out.print("This is fly weight: " + key + ", it's unshared info is: ");
      System.out.println(unsharedFlyWeight.getSpecificInfo() + ".");
    }
  }

  static class FlyWeightFactory {

    private Map<String, FlyWeight> flyWeights = new HashMap<>();

    public FlyWeight getFlyWeight(String key) {
      FlyWeight flyWeight = flyWeights.get(key);
      if (flyWeight == null) {
        flyWeight = new ConcreteFlyWeight(key);
        flyWeights.put(key, flyWeight);
      }
      return flyWeight;
    }
  }

  public static void main(String[] args) {
    UnsharedFlyWeight jasmine = new UnsharedFlyWeight("Jasmine");
    UnsharedFlyWeight justin = new UnsharedFlyWeight("Justin");
    UnsharedFlyWeight joyce = new UnsharedFlyWeight("Joyce");

    FlyWeightFactory factory = new FlyWeightFactory();
    System.out.println();
    factory.getFlyWeight("a").operation(jasmine);
    System.out.println();
    factory.getFlyWeight("b").operation(justin);
    System.out.println();
    factory.getFlyWeight("c").operation(joyce);
    System.out.println();
    factory.getFlyWeight("a").operation(justin);
    System.out.println();
    factory.getFlyWeight("b").operation(joyce);
  }
}
