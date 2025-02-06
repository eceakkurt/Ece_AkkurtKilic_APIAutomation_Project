package tests;

import models.Pet;
import org.testng.annotations.Test;
import utils.PetModelHelper;

import static utils.TestConstants.*;

public class DeletePetTest extends BaseAPITest {

    @Test
    public void shouldDeletePetSuccessfully() {
        Pet pet = PetModelHelper.availablePetWithImage(1010, "Rex");

        getRequestSpec()
                .body(pet)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_OK);

        getRequestSpecWithAuth()
                .pathParam(PET_ID, 1010)
                .when()
                .delete(PET_WITH_PATH_ID_PARAM)
                .then()
                .statusCode(RESPONSE_STATUS_OK);
    }

    @Test
    public void shouldReturnErrorForNonExistingPetId() throws Exception {
        try {
            getRequestSpecWithAuth()
                    .pathParam(PET_ID, 9999)
                    .when()
                    .delete(PET_WITH_PATH_ID_PARAM)
                    .then()
                    .statusCode(RESPONSE_STATUS_NOT_FOUND);
        } catch (Exception e) {
            validate404ExceptionAsResponse(e);
        }

    }

    @Test
    public void shouldReturnNotAllowedWhenPetIdIsMissing() throws Exception {
        try {
            getRequestSpec()
                    .when()
                    .delete(PET_ENDPOINT)
                    .then()
                    .statusCode(RESPONSE_STATUS_BAD_REQUEST);
        } catch (Exception e) {
            validateExceptionAsResponse(e, RESPONSE_STATUS_METHOD_NOT_ALLOWED);
        }
    }

    @Test
    public void shouldReturnNotFoundWhenGettingDeletedPet() throws Exception {
        Pet pet = PetModelHelper.availablePetWithImage(1011, "Charlie");

        getRequestSpec()
                .body(pet)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_OK);

        getRequestSpecWithAuth()
                .pathParam(PET_ID, 1011)
                .when()
                .delete(PET_WITH_PATH_ID_PARAM)
                .then()
                .statusCode(RESPONSE_STATUS_OK);

        try {
            getRequestSpec()
                    .pathParam(PET_ID, 1011)
                    .when()
                    .get(PET_WITH_PATH_ID_PARAM)
                    .then()
                    .statusCode(RESPONSE_STATUS_NOT_FOUND);
        } catch (Exception e) {
            validate404ExceptionAsResponse(e);
        }
    }

    @Test
    public void shouldNotDeletePetWhenIncorrectPetIdIsUsed() throws Exception {
        Pet pet = PetModelHelper.availablePetWithImage(1013, "Sam");

        getRequestSpec()
                .body(pet)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_OK);

        try {
            getRequestSpecWithAuth()
                    .pathParam(PET_ID, 1014)
                    .when()
                    .delete(PET_WITH_PATH_ID_PARAM)
                    .then()
                    .statusCode(RESPONSE_STATUS_NOT_FOUND);
        } catch (Exception e) {
            validate404ExceptionAsResponse(e);
        }
    }

    @Test
    public void shouldNotFindDeletedPet() throws Exception {
        Pet pet = PetModelHelper.availablePetWithImage(1016, "Tommy");

        getRequestSpec()
                .body(pet)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_OK);

        getRequestSpecWithAuth()
                .pathParam(PET_ID, 1016)
                .when()
                .delete(PET_WITH_PATH_ID_PARAM)
                .then()
                .statusCode(RESPONSE_STATUS_OK);

        try {
            getRequestSpec()
                    .pathParam(PET_ID, 1016)
                    .when()
                    .get(PET_WITH_PATH_ID_PARAM)
                    .then()
                    .statusCode(RESPONSE_STATUS_NOT_FOUND);
        } catch (Exception e) {
            validate404ExceptionAsResponse(e);
        }
    }

    @Test
    public void shouldReturnUnauthorizedWhenDeletingWithoutAuth() {
        Pet pet = PetModelHelper.availablePetWithImage(1017, "Oscar");

        getRequestSpec()
                .body(pet)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_OK);

        getRequestSpec()
                .pathParam(PET_ID, 1017)
                .when()
                .delete(PET_WITH_PATH_ID_PARAM)
                .then()
                .statusCode(RESPONSE_STATUS_UNAUTHORIZED);
    }
}
