package de.gedoplan.showcase.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class ThreadUtil {
  public static boolean isVirtualThread() {
    try {
      Method isVirtual = Thread.class.getMethod("isVirtual");
      return (Boolean) isVirtual.invoke(Thread.currentThread());
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      return false;
    }
  }

  public static String getKindOfThread() {
    return isVirtualThread() ? "virtual" : "platform";
  }

}
