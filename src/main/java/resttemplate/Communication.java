package resttemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import resttemplate.dto.User;

import java.util.List;

@Component
public class Communication {

    private static final String URL_API = "http://94.198.50.185:7081/api/users/";

    private final RestTemplate restTemplate;
    private final User user;
    private final HttpHeaders requestHeaders;
    private String key;

    @Autowired
    public Communication(RestTemplate restTemplate, User user, HttpHeaders requestHeaders) {
        this.restTemplate = restTemplate;
        this.user = user;
        this.requestHeaders = requestHeaders;
    }

    public void work() {
        findAll();
        createUser();
        updateUser();
        deleteUser();
    }

    private void findAll() {
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(URL_API, HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {
                });

        HttpHeaders httpHeaders = responseEntity.getHeaders();
        String sessionId = httpHeaders.getFirst("Set-Cookie");

        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add("Cookie", sessionId);
    }

    private void createUser() {
        HttpEntity<User> entityPost = new HttpEntity<>(user, requestHeaders);
        ResponseEntity<String> response = restTemplate.exchange(URL_API, HttpMethod.POST, entityPost, String.class);
        key = response.getBody();
    }

    private void updateUser() {
        user.setName("Thomas");
        user.setLastName("Shelby");
        HttpEntity<User> entityPut = new HttpEntity<>(user, requestHeaders);
        ResponseEntity<String> response = restTemplate.exchange(URL_API, HttpMethod.PUT, entityPut, String.class);

        key += response.getBody();
    }

    private void deleteUser() {
        HttpEntity<Object> entityDelete = new HttpEntity<>(requestHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                URL_API + "/" + user.getId(), HttpMethod.DELETE, entityDelete, String.class);
        key += response.getBody();
    }

    public String getKey() {
        return key;
    }
}
