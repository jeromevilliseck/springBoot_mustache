package app;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith((SpringRunner.class))
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class MustacheApplicationTest {

    @Autowired
    private TestRestTemplate restTemplate; //L'autowired permet l'injection de dépendance automatique
    //L'objet restTemplate est initialisé dès la construction lancement de la classe test pas de risque NPE

    /*
    Teste si le mapping sur l'url de base / fonctionne (doit retourner un code 200)
    l'objet entity contient le retour du template, on peut controler les variable , le code de retour etc
     */
    @Test
    public void testMainPage() throws Exception{
        ResponseEntity<String> entity = this.restTemplate.getForEntity("/", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).contains("Hello Mkyong");
    }

    @Test
    public void test404Page() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("/uri-not-exist", HttpMethod.GET,
                requestEntity, String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).contains("Something went wrong: 404 Not Found");
    }

    @Test
    public void test5xxPage() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("/5xx", HttpMethod.GET, requestEntity,
                String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(responseEntity.getBody()).contains("I'm a 5xx");

    }
}