package de.gedoplan.showcase.jee11.jp.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NamedQuery(name = "Person.findAll", query = "FROM Person")
public class Person {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @NotNull
  @Size(min = 2)
  String name;

  Status status;

//  @NotNull
  @PastOrPresent
  LocalDate birthday;

  @Valid
  Address address;
}
