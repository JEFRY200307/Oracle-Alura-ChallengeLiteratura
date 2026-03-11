package oracle.alura.literaturachallenge.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import oracle.alura.literaturachallenge.dto.GutendexSearchResponse;
import oracle.alura.literaturachallenge.exception.RemoteApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Component
public class GutendexClient {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String baseUrl;

    public GutendexClient(ObjectMapper objectMapper,
                          @Value("${app.gutendex.base-url:https://gutendex.com/books}") String baseUrl) {
        this.httpClient = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();
        this.objectMapper = objectMapper;
        this.baseUrl = baseUrl;
    }

    public GutendexSearchResponse searchBooksByTitle(String title) {
        var safeQuery = URLEncoder.encode(title, StandardCharsets.UTF_8);
        var target = URI.create(baseUrl + "?search=" + safeQuery);
        System.out.println("URL solicitada a Gutendex: " + target); // LOG URL
        var request = HttpRequest.newBuilder(target)
                .header("Accept", "application/json")
                .GET()
                .build();
        try {
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Código HTTP: " + response.statusCode()); // LOG HTTP
            System.out.println("Respuesta Gutendex: " + response.body());
            if (response.statusCode() >= 400) {
                throw new RemoteApiException("La API Gutendex respondió con estado " + response.statusCode());
            }
            return objectMapper.readValue(response.body(), GutendexSearchResponse.class);
        } catch (IOException e) {
            throw new RemoteApiException("No fue posible interpretar la respuesta JSON", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RemoteApiException("La solicitud a Gutendex fue interrumpida", e);
        }
    }
}
