package de.gedoplan.showcase.jee11.jp.model;

import jakarta.persistence.EnumeratedValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Status {
  NEW(0), ACTIVE(1), DELETED(-1);

  @Getter
  @EnumeratedValue
  final int shortcut;
}
