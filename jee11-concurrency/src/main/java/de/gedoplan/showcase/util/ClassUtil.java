package de.gedoplan.showcase.util;

import java.lang.reflect.Proxy;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility-Klasse mit diversen Hilfsmethoden für Klassen.
 *
 * @author dw
 */
public final class ClassUtil {
  private static Set<Class<?>> primitiveWrappers = new HashSet<Class<?>>();
  static {
    primitiveWrappers.add(Byte.class);
    primitiveWrappers.add(Short.class);
    primitiveWrappers.add(Integer.class);
    primitiveWrappers.add(Long.class);
    primitiveWrappers.add(Float.class);
    primitiveWrappers.add(Double.class);
    primitiveWrappers.add(Character.class);
    primitiveWrappers.add(Boolean.class);
  }

  private static Set<Class<?>> temporals17 = new HashSet<Class<?>>();
  static {
    temporals17.add(Date.class);
    temporals17.add(java.sql.Date.class);
    temporals17.add(Timestamp.class);
    temporals17.add(Time.class);
    temporals17.add(Calendar.class);
  }

  private static Set<Class<?>> proxyBases = new HashSet<>();
  static {
    try {
      proxyBases.add(Class.forName("org.jboss.weld.proxy.WeldConstruct"));
    } catch (ClassNotFoundException e) {
    }
    try {
      proxyBases.add(Class.forName("org.apache.webbeans.proxy.OwbNormalScopeProxy"));
    } catch (ClassNotFoundException e) {
    }
    try {
      proxyBases.add(Class.forName("javassist.util.proxy.Proxy"));
    } catch (ClassNotFoundException e) {
    }
    try {
      proxyBases.add(Class.forName("io.quarkus.arc.Subclass"));
    } catch (ClassNotFoundException e) {
    }
  }

  /**
   * Ist die übergebene Klasse eine Wrapperklasse eines primitiven Typs?
   *
   * @param clazz
   *          Klasse
   * @return <code>true</code>, wenn die Klasse eine Wrapper-Klasse ist
   */
  public static boolean isPrimitiveWrapper(Class<?> clazz) {
    return primitiveWrappers.contains(clazz);
  }

  /**
   * Ist die übergebene Klasse eine Datum/Uhrzeit-Klasse?
   *
   * @param clazz
   *          Klasse
   * @return <code>true</code>, wenn die Klasse eine Datum/Uhrzeit-Klasse ist
   */
  public static boolean isTemporal(Class<?> clazz) {
    return temporals17.contains(clazz)
        || TemporalAmount.class.isAssignableFrom(clazz)
        || Temporal.class.isAssignableFrom(clazz)
        || TemporalAccessor.class.isAssignableFrom(clazz);
  }

  /**
   * Ist die übergebene Klasse eine Proxy-Klasse?
   *
   * @param clazz
   *          Klasse
   * @return <code>true</code>, wenn die Klasse eine eine Proxy-Klasse ist
   */
  public static boolean isProxy(Class<?> clazz) {
    if (Proxy.isProxyClass(clazz)) {
      return true;
    }

    for (Class<?> base : proxyBases) {
      if (base.isAssignableFrom(clazz)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Nicht-Proxy-Klasse zur angegebenen Klasse liefern.
   *
   * @param clazz
   *          Klasse
   * @return Nicht-Proxy-Klasse zur angegeben Klasse
   */
  public static Class<?> getProxiedClass(Class<?> clazz) {
    if (!isProxy(clazz)) {
      return clazz;
    }

    Class<?> superclass = clazz.getSuperclass();
    if (superclass == null) {
      return clazz;
    }

    return getProxiedClass(superclass);
  }

  private ClassUtil() {
  }

}
