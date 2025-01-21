package de.gedoplan.showcase.service.concurrency;

import jakarta.enterprise.concurrent.ManagedExecutorDefinition;
import jakarta.enterprise.context.Dependent;

@ManagedExecutorDefinition(name = "java:comp/UseVirtualIfSupportedExecutor", qualifiers = UseVirtualIfSupported.class, virtual = true)
@Dependent
public class ExecutorDefinitions {
}
