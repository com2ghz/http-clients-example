package nl.orhun;

import nl.orhun.rest.RestApplication;
import nl.orhun.rest.Vehicle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {RestApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestTemplateClientTest {

    @LocalServerPort
    int randomServerPort;

    private final RestTemplate restTemplate = new RestTemplate();
    private final Vehicle vehiclePayload = Vehicle.builder().brand("BMW").build();

    @Test
    void doGetForEntity() {
        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost")
                .path("/vehicles")
                .port(randomServerPort)
                .queryParam("foo", "bar")
                .build()
                .toUri();

        ResponseEntity<Vehicle[]> response = restTemplate.getForEntity(uri, Vehicle[].class);

        assertEquals(response.getStatusCode().value(), 200);
        System.out.println(Arrays.toString(response.getBody()));
    }

    @Test
    void doGetObject() {
        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost")
                .path("/vehicles")
                .port(randomServerPort)
                .queryParam("foo", "bar")
                .build()
                .toUri();

        Vehicle[] vehicles = restTemplate.getForObject(uri, Vehicle[].class);

        assertEquals(vehicles.length, 3);
        System.out.println(Arrays.toString(vehicles));
    }

    @Test
    void doPostEntity() {
        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost")
                .path("/vehicles")
                .port(randomServerPort)
                .queryParam("foo", "bar")
                .build()
                .toUri();

        ResponseEntity<Vehicle> response = restTemplate.postForEntity(uri, vehiclePayload, Vehicle.class);

        assertEquals(response.getStatusCode().value(), 200);
        System.out.println(response.getBody());
    }

    @Test
    void doPostObject() {
        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost")
                .path("/vehicles")
                .port(randomServerPort)
                .queryParam("foo", "bar")
                .build()
                .toUri();

        Vehicle vehicle = restTemplate.postForObject(uri, vehiclePayload, Vehicle.class);

        assertEquals(vehiclePayload, vehicle);
        System.out.println(vehicle);
    }

    @Test
    void doPut() {
        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost")
                .path("/vehicles")
                .port(randomServerPort)
                .queryParam("foo", "bar")
                .build()
                .toUri();

        restTemplate.put(uri, vehiclePayload);
    }

    @Test
    void doPutExchange() {
        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost")
                .path("/vehicles")
                .port(randomServerPort)
                .queryParam("foo", "bar")
                .build()
                .toUri();

        ResponseEntity<Vehicle> response = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(vehiclePayload), Vehicle.class);
        assertEquals(response.getStatusCode().value(), 200);
        System.out.println(response.getBody());
    }

}
