package de.gedoplan.showcase.jee11.jp.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Embeddable
public record Address(
    @NotNull String street,
    @NotNull String city,
    @NotNull @Pattern(regexp = "\\d{5}") String postcode) {
}
