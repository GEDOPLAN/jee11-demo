package de.gedoplan.showcase.jee11.jp.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@NamedQuery(name = "Person.findAll", query = "FROM Person")
public class Person {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(comment = "The name of the person.")
  @NotNull
  @Size(min = 2)
  String name;

//  @Enumerated(EnumType.STRING)
  Status status;

  @NotNull
  String number;

  @NotNull
  @PastOrPresent
  LocalDate birthday;

  @Column(check = @CheckConstraint(name = "check_year", constraint = "\"YEAROFBIRTH\" >= 1900"))
  Year yearOfBirth;

  @Valid
  Address address;

  @OneToMany(mappedBy = Note_.PERSON)
  List<Note> notes;

  @PrePersist
  void prePersist() {
    yearOfBirth = Year.from(birthday);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Person other = (Person) obj;
    return Objects.equals(id, other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
