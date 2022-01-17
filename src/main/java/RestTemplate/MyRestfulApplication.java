package RestTemplate;

import RestTemplate.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class MyRestfulApplication {

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://91.241.64.178:7081/api/users";

        SpringApplication.run(MyRestfulApplication.class, args);

        //получение первой записи
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        System.out.println("1) " + response.getBody() + " Status: " + response.getStatusCode());

        // получение ключа
        // получение второй записи [Set-Cookie:"JSESSIONID=F101D4601E1E2410601C1E24F7CAF0EA; Path=/; HttpOnly", X-Content-Type-Options:"nosniff",
        String sessionId = response.getHeaders().get("Set-Cookie").get(0);
        System.out.println(sessionId);


        HttpHeaders myHeaders = new HttpHeaders();

        myHeaders.add("Cookie",sessionId);
        myHeaders.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("my new HttpHeaders:   " + myHeaders);

        //стадия 2
        User tri = new User((long) 3, "James", "Brown", (byte) 15);
        HttpEntity<String> requestBody = new HttpEntity<>(tri.toJson(), myHeaders);
        ResponseEntity<String> response2 = restTemplate.exchange(url, HttpMethod.POST, requestBody, String.class);
        System.out.println("2) " + response2.getBody() + " Status: " + response2.getStatusCode());

        //стадия 3  поменять name на Thomas, а lastName на Shelby
        tri.setName("Thomas");
        tri.setLastName("Shelby");
        requestBody = new HttpEntity<>(tri.toJson(), myHeaders);
        ResponseEntity<String> response3 = restTemplate.exchange(url, HttpMethod.PUT, requestBody, String.class);
        System.out.println("3) " + response3.getBody() + " Status: " + response3.getStatusCode());


        // стадия 4  Удалить пользователя с id = 3
        requestBody = new HttpEntity<>("3", myHeaders);
        ResponseEntity<String> response4 = restTemplate.exchange(url + "/{id}", HttpMethod.DELETE, requestBody, String.class, 3);
        System.out.println("4) " + response4.getBody() + " Status: " + response4.getStatusCode());

        //стадия 5 конкатенация
        System.out.println("Итог " + response2.getBody() + response3.getBody() + response4.getBody());



    }

}
