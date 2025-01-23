package de.gedoplan.showcase.util;

import java.lang.reflect.Member;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.Bean;
import jakarta.enterprise.inject.spi.InjectionPoint;

import org.jboss.logging.Logger;

/**
 * Producer für JBoss Logger.
 *
 * @author dw
 */
@ApplicationScoped
public class LoggerProducer {

  private static final Map<String, Logger> LOG_MAP = new ConcurrentHashMap<>();

  /**
   * Logger liefern.
   *
   * @param injectionPoint
   *          Injection-Point
   * @return Logger
   */
  @Produces
  Logger getLogger(InjectionPoint injectionPoint) {
    Bean<?> targetBean = null;
    if (injectionPoint != null) {
      targetBean = injectionPoint.getBean();
    }

    Class<?> targetClass = null;
    if (targetBean != null) {
      targetClass = targetBean.getBeanClass();
    }

    if (targetClass == null && injectionPoint != null) {
      Member member = injectionPoint.getMember();
      if (member != null) {
        targetClass = ClassUtil.getProxiedClass(member.getDeclaringClass());
      }
    }

    return getLog(targetClass);
  }

  public static Logger getLog(Class<?> targetClass) {
    String name = targetClass != null ? targetClass.getName() : "UnnamedLogger";
    return LOG_MAP.computeIfAbsent(name, k -> Logger.getLogger(k));
  }
}
