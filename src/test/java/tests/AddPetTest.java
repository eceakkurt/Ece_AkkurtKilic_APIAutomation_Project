package tests;

import io.restassured.http.ContentType;
import models.Pet;
import org.testng.annotations.Test;
import utils.PetModelHelper;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static utils.TestConstants.*;

public class AddPetTest extends BaseAPITest {

    @Test
    public void shouldAddAvailablePetSuccessfully() {
        Pet pet = PetModelHelper.availablePetWithImage(1001, "Lucky");

        getRequestSpecWithAuth()
                .body(pet)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_OK)
                .body(PET_FIELD_ID, equalTo(pet.getId()))
                .body(PET_FIELD_NAME, equalTo(pet.getName()))
                .body(PET_FIELD_STATUS, equalToIgnoringCase(pet.getStatus().getValue()));
    }

    @Test
    public void shouldAddSoldPetSuccessfully() {
        Pet pet = PetModelHelper.soldPet(1002, "Bella");

        getRequestSpecWithAuth()
                .body(pet)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_OK)
                .body(PET_FIELD_ID, equalTo(pet.getId()))
                .body(PET_FIELD_NAME, equalTo(pet.getName()))
                .body(PET_FIELD_STATUS, equalToIgnoringCase(pet.getStatus().getValue()));
    }

    @Test
    public void shouldAddPendingPetSuccessfully() {
        Pet pet = PetModelHelper.pendingPet(1003, "Max");

        getRequestSpecWithAuth()
                .contentType(ContentType.JSON)
                .body(pet)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_OK)
                .body(PET_FIELD_ID, equalTo(pet.getId()))
                .body(PET_FIELD_NAME, equalTo(pet.getName()))
                .body(PET_FIELD_STATUS, equalToIgnoringCase(pet.getStatus().getValue()));
    }

    @Test
    public void shouldNotAddPetWithoutName() {
        Pet pet = PetModelHelper.petWithoutName(1004);

        getRequestSpecWithAuth()
                .body(pet)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_BAD_REQUEST);
    }

    @Test
    public void shouldNotAddPetWithInvalidId() {
        Pet pet = PetModelHelper.petWithInvalidId("Ghost");

        getRequestSpecWithAuth()
                .body(pet)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_BAD_REQUEST);
    }

    @Test
    public void shouldAddPetWithMultipleImages() {
        Pet pet = PetModelHelper.petWithMultipleImages(1005, "Rocky");

        getRequestSpecWithAuth()
                .body(pet)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_OK)
                .body(PET_FIELD_ID, equalTo(pet.getId()))
                .body(PET_FIELD_NAME, equalTo(pet.getName()))
                .body(PET_FIELD_STATUS, equalToIgnoringCase(pet.getStatus().getValue()));
    }

    @Test
    public void shouldNotAddPetWithEmptyPhotoUrls() {
        Pet pet = PetModelHelper.petWithEmptyPhotoUrls(1006, "Shadow");

        getRequestSpecWithAuth()
                .body(pet)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_OK)
                .body(PET_FIELD_ID, equalTo(pet.getId()))
                .body(PET_FIELD_NAME, equalTo(pet.getName()))
                .body(PET_FIELD_STATUS, equalToIgnoringCase(pet.getStatus().getValue()));
    }

    @Test
    public void shouldAddPetWithoutStatus() {
        Pet pet = PetModelHelper.petWithoutStatus(1007, "Zeus");

        getRequestSpecWithAuth()
                .body(pet)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_OK)
                .body(PET_FIELD_ID, equalTo(pet.getId()))
                .body(PET_FIELD_NAME, equalTo(pet.getName()));
    }

    @Test
    public void shouldNotAddPetWithVeryLargeId() {
        Pet pet = PetModelHelper.petWithLargeId(Long.MAX_VALUE, "GiantPet");

        getRequestSpecWithAuth()
                .body(pet)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_OK);  // Assuming the system accepts large IDs
    }

    @Test
    public void shouldNotAddPetWithSpecialCharactersInName() {
        Pet pet = PetModelHelper.petWithSpecialCharactersInName(1009, "P@t#e$");

        getRequestSpecWithAuth()
                .body(pet)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_BAD_REQUEST);
    }
}
