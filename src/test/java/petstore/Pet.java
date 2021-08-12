// 1 - Pacote
package petstore;

// 2 - Bibliotecas

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

// 3 - Classe
public class Pet {
    // 3.1 - Atributos
    String uri = "https://petstore.swagger.io/v2/pet"; // endereço da entidade Pet

    // 3.2 - Métodos e Funções
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    // Incluir - Create - Post
    @Test // identifica o metodo ou função como um teste para o TestNG
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("db/pet1.json");

        // Sintaxe Gherkin
        // Dado - Quando - Então
        // Given - When - Then

        given() // dado
                .contentType("application/json") // mais comum em API REST - Antigas eram "text/xml"
                .log().all()
                .body(jsonBody)

        .when() // quando
                .post(uri)

        .then() // então
                .log().all()
                .statusCode(200)
                .body("name", is("Morcego"))  // resultado esperado ver se esse texto está no teste
                .body("status", is("available"))
        ;

    }


}
