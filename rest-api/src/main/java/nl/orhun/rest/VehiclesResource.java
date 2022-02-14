package nl.orhun.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class VehiclesResource {
    private final List<Vehicle> vehicles = new ArrayList<>();

    public VehiclesResource() {
        vehicles.add(Vehicle.builder().brand("BMW").model("320i").year("2012").build());
        vehicles.add(Vehicle.builder().brand("Mercedes").model("CLA").year("2013").build());
        vehicles.add(Vehicle.builder().brand("Volkswagen").model("Golf").year("2009").build());
    }

    @GetMapping("/vehicles")
    public ResponseEntity<List<Vehicle>> get() {
        return ResponseEntity.ok(vehicles);
    }

    @PostMapping("/vehicles")
    public ResponseEntity<Vehicle> post(@RequestBody Vehicle vehicle) {
        return ResponseEntity.ok(vehicle);
    }

    @PutMapping("/vehicles")
    public ResponseEntity<Vehicle> put(@RequestBody Vehicle vehicle) {
        return ResponseEntity.ok(vehicle);
    }

}
