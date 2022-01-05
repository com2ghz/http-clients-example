package nl.orhun.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@EqualsAndHashCode
@ToString
@Getter
public class Vehicle {
    private final String brand;
    private final String model;
    private final String year;

    public Vehicle(@JsonProperty("brand") String brand, @JsonProperty String model, String year) {
        this.brand = brand;
        this.model = model;
        this.year = year;
    }
}
