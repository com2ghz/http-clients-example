package nl.orhun;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitter.finagle.Http;
import com.twitter.finagle.Service;
import com.twitter.finagle.http.Method;
import com.twitter.finagle.http.Request;
import com.twitter.finagle.http.Response;
import com.twitter.util.Future;
import nl.orhun.rest.RestApplication;
import nl.orhun.rest.Vehicle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.CompletableFuture;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {RestApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FinagleClientTest {

    @LocalServerPort
    int randomServerPort;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Vehicle vehiclePayload = Vehicle.builder().brand("BMW").build();


    @Test
    void finagleGet() {
        Service<Request, Response> httpService = Http.newService("localhost:" + randomServerPort);

        Request request = Request.apply(Method.Get(), "/vehicles?foo=bar");
        request.headerMap().put("host", "localhost");

        Future<Response> responseFuture = httpService.apply(request);
        CompletableFuture<Response> completableFuture = responseFuture.toCompletableFuture();
        Response response = completableFuture.join();

        Assertions.assertEquals(200, response.statusCode());
        System.out.println(request);
        System.out.println(response.getContentString());
    }

    @Test
    void finaglePost() throws JsonProcessingException {
        Service<Request, Response> httpService = Http.newService("localhost:" + randomServerPort);

        Request request = Request.apply(Method.Post(), "/vehicles?foo=bar");
        request.headerMap().put("host", "localhost");
        request.setContentTypeJson();
        request.setContentString(objectMapper.writeValueAsString(vehiclePayload));

        Future<Response> responseFuture = httpService.apply(request);
        CompletableFuture<Response> completableFuture = responseFuture.toCompletableFuture();
        Response response = completableFuture.join();

        Assertions.assertEquals(200, response.statusCode());
        System.out.println(request);
        System.out.println(response.getContentString());
    }
}
