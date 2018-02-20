package me.jrandom.core;

import java.lang.reflect.Field;

public class ReflectionAction {

  public static void accessibleSafeAction(Field field, Runnable action) {
    boolean isFieldAccessible = field.isAccessible();
    if (!isFieldAccessible) {
      field.setAccessible(true);
    }
    action.run();
    if (!isFieldAccessible) {
      field.setAccessible(false);
    }
  }
}
