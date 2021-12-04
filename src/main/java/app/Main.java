package app;

import app.models.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String response = "";
        String cookie;
        User user = new User(3L, "James", "Brown", (byte) 40);

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity1 = new HttpEntity<>(headers);
        cookie = restTemplate
                .exchange("http://91.241.64.178:7081/api/users", HttpMethod.GET, entity1, String.class)
                .getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        headers.set("Cookie", cookie);
        HttpEntity<User> entity2 = new HttpEntity<>(user, headers);
        response = response + restTemplate
                .postForEntity("http://91.241.64.178:7081/api/users", entity2, String.class)
                .getBody();

        user.setName("Thomas");
        user.setLastName("Shelby");
        entity2 = new HttpEntity<>(user, headers);
        response = response + restTemplate
                .exchange("http://91.241.64.178:7081/api/users", HttpMethod.PUT, entity2, String.class)
                .getBody();

        response = response + restTemplate
                .exchange("http://91.241.64.178:7081/api/users/3", HttpMethod.DELETE, entity1, String.class)
                .getBody();

        System.out.println(response);
    }
}
