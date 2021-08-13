// 1 - Pacote
package petstore;

// 2 - Bibliotecas

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

// 3 - Classe
public class Pet {
    // 3.1 - Atributos
    String uri = "https://petstore.swagger.io/v2/pet"; // endereço da entidade Pet

    // 3.2 - Métodos e Funções
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    // Incluir - Create - Post
    @Test (priority = 1) // identifica o metodo ou função como um teste para o TestNG
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
                .body("category.name", is ("14721"))
                .body("tags.name", contains("data")) // [] colchetes dentro do pet1.json permite o uso de contains, pois colchetes significa que tem uma lista de tags
        ;

    }
// consultar o pet
    @Test (priority = 2) // mostrar que isso é um teste, aparecer "play" para rodar
    public void consultarPet(){
        String petId = "4999182513";

        String token=
        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri +"/"+ petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Morcego"))
                .body("category.name", is ("14721"))
                .body("status", is("available"))

            .extract()
                .path("category.name")

                ;
        System.out.println("O token é " + token);

    }
@Test (priority = 3)
    public void alterarPet() throws IOException {
        String jsonBody = lerJson("db/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Morcego"))
                .body("status",is("sold"))
        ;

    }

    @Test (priority = 4)
    public void excluirPet(){
        String petId = "4999182513";

        given()
                .contentType("application/json")
                .log().all()
        .when() // comandos (get,put, post,delete)
                .delete(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is(petId))

        ;
    }

}
