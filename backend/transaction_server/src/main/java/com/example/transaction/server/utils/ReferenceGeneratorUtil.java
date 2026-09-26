package com.example.transaction.server.utils;

import java.security.SecureRandom;

public class ReferenceGeneratorUtil {

  private ReferenceGeneratorUtil() {

  }

  private static final SecureRandom secureRandom = new SecureRandom();

  public static long generateReferenceLong(int length) {
    if (length <= 0) {
      throw new IllegalArgumentException("Length must be greater than 0.");
    }

    long min = (long) Math.pow(10, length - 1);
    long max = (long) Math.pow(10, length) - 1;
    long range = max - min + 1;
    long random = secureRandom.nextLong();

    return min + Math.abs(random) % range;
  }

  public static String generateReferenceString(int length) {
    return String.valueOf(generateReferenceLong(length));
  }


}
