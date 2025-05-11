package de.gedoplan.showcase.jee11.jp.model;

import java.time.LocalDate;
import java.time.Year;
import java.util.Objects;

import jakarta.persistence.CheckConstraint;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PrePersist;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
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
