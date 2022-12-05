package utils;

import java.util.Random;

public class Helper {

  public static String generateRandomChar(int len) {
    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    Random rnd = new Random();
    StringBuilder sb = new StringBuilder(len);
    for (int i = 0; i < len; i++) {
      sb.append(chars.charAt(rnd.nextInt(chars.length())));
    }
    return sb.toString();
  }

  public static String getRandomName() {
    return generateRandomChar(8);
  }

  public static String getRandomNumber() {
    Random random = new Random();
    int number = random.nextInt(9999999);
    return Integer.toString(number);
  }
}
