package tests;

import models.Pet;
import models.PetStatus;
import org.testng.annotations.Test;
import utils.PetModelHelper;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static utils.TestConstants.*;

public class UpdatePetTest extends BaseAPITest {

    @Test
    public void shouldUpdatePetSuccessfully() {
        Pet pet = PetModelHelper.availablePetWithImage(1001, "Lucky");

        getRequestSpec()
                .body(pet)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_OK);

        pet.setName("UpdatedLucky");
        pet.setStatus(PetStatus.SOLD);

        getRequestSpec()
                .body(pet)
                .when()
                .put(PET_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_OK)
                .body(PET_FIELD_ID, equalTo(pet.getId()))
                .body(PET_FIELD_NAME, equalTo(pet.getName()))
                .body(PET_FIELD_STATUS, equalToIgnoringCase(pet.getStatus().getValue()));
    }

    @Test
    public void shouldNotUpdatePetWithInvalidId() {
        Pet pet = PetModelHelper.availablePetWithImage(1002, "Bella");

        getRequestSpec()
                .body(pet)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_OK);

        pet.setId(-1);
        pet.setName("InvalidPet");

        getRequestSpec()
                .body(pet)
                .when()
                .put(PET_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_BAD_REQUEST);
    }

    @Test
    public void shouldUpdatePetStatusSuccessfully() {
        Pet pet = PetModelHelper.availablePetWithImage(1003, "Max");

        getRequestSpec()
                .body(pet)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_OK);

        pet.setStatus(PetStatus.PENDING);

        getRequestSpec()
                .body(pet)
                .when()
                .put(PET_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_OK)
                .body(PET_FIELD_ID, equalTo(pet.getId()))
                .body(PET_FIELD_NAME, equalTo(pet.getName()))
                .body(PET_FIELD_STATUS, equalToIgnoringCase(pet.getStatus().getValue()));
    }

    @Test
    public void shouldNotUpdatePetWithEmptyName() {
        Pet pet = PetModelHelper.availablePetWithImage(1004, "Charlie");

        getRequestSpec()
                .body(pet)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_OK);

        pet.setName("");

        getRequestSpec()
                .body(pet)
                .when()
                .put(PET_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_BAD_REQUEST);
    }
}
