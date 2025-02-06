package tests;

import io.restassured.http.ContentType;
import models.Pet;
import models.PetStatus;
import org.testng.annotations.Test;
import utils.PetModelHelper;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static utils.TestConstants.*;

public class UpdatePetWithFormTest extends BaseAPITest {

    @Test
    public void shouldUpdatePetSuccessfully() {
        Pet pet = PetModelHelper.availablePetWithImage(9, "Lucky");

        getRequestSpec()
                .body(pet)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_OK);

        getRequestSpecWithAuth()
                .pathParam(PET_ID, 1001)
                .contentType(ContentType.URLENC)
                .formParam(PET_FIELD_NAME, "LuckyUpdated")
                .when()
                .post(PET_WITH_PATH_ID_PARAM)
                .then()
                .statusCode(RESPONSE_STATUS_OK);
    }

    @Test
    public void shouldUpdatePetStatusSuccessfully() {
        Pet pet = PetModelHelper.availablePetWithImage(1002, "Bella");

        getRequestSpec()
                .body(pet)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_OK);

        getRequestSpecWithAuth()
                .pathParam(PET_ID, 1002)
                .contentType(ContentType.URLENC)
                .formParam(PET_FIELD_STATUS, PetStatus.SOLD.getValue())
                .when()
                .post(PET_WITH_PATH_ID_PARAM)
                .then()
                .statusCode(RESPONSE_STATUS_OK);
    }

    @Test
    public void shouldReturnErrorWhenNameIsEmpty() {
        getRequestSpec()
                .pathParam(PET_ID, 1003)
                .contentType(ContentType.URLENC)
                .formParam(PET_FIELD_NAME, "")
                .formParam(PET_FIELD_STATUS, PetStatus.AVAILABLE.getValue())
                .when()
                .post(PET_WITH_PATH_ID_PARAM)
                .then()
                .statusCode(RESPONSE_STATUS_NOT_FOUND);
    }

    @Test
    public void shouldReturnNotFoundForInvalidStatus() {
        getRequestSpec()
                .pathParam(PET_ID, 1004)
                .contentType(ContentType.URLENC)
                .formParam(PET_FIELD_NAME, "Charlie")
                .formParam(PET_FIELD_STATUS, "invalid-status")
                .when()
                .post(PET_WITH_PATH_ID_PARAM)
                .then()
                .statusCode(RESPONSE_STATUS_NOT_FOUND);
    }

    @Test
    public void shouldReturnNotFoundForInvalidPetId() {
        getRequestSpec()
                .pathParam(PET_ID, 9999)
                .contentType(ContentType.URLENC)
                .formParam(PET_FIELD_NAME, "NonExistentPet")
                .formParam(PET_FIELD_STATUS, PetStatus.SOLD.getValue())
                .when()
                .post(PET_WITH_PATH_ID_PARAM)
                .then()
                .statusCode(RESPONSE_STATUS_NOT_FOUND);
    }

    @Test
    public void shouldReturnErrorWhenPetIdIsMissing() {
        getRequestSpec()
                .contentType(ContentType.URLENC)
                .formParam(PET_FIELD_NAME, "NoPetIdPet")
                .formParam(PET_FIELD_STATUS, PetStatus.PENDING.getValue())
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_UNSUPPORTED_MEDIA_TYPE);
    }

    @Test
    public void shouldUpdatePetWhenSameDetailsAreSent() {
        Pet pet = PetModelHelper.availablePetWithImage(1005, "Rocky");

        getRequestSpec()
                .body(pet)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_OK);

        getRequestSpecWithAuth()
                .pathParam(PET_ID, 1005)
                .contentType(ContentType.URLENC)
                .formParam(PET_FIELD_NAME, "Rocky")
                .formParam(PET_FIELD_STATUS, PetStatus.AVAILABLE.getValue())
                .when()
                .post(PET_WITH_PATH_ID_PARAM)
                .then()
                .statusCode(RESPONSE_STATUS_OK);
    }

    @Test
    public void shouldGetUpdatedPetDetails() {
        Pet pet = PetModelHelper.availablePetWithImage(1006, "Benny");

        getRequestSpec()
                .body(pet)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_OK);

        getRequestSpec()
                .pathParam(PET_ID, 1006)
                .when()
                .get(PET_WITH_PATH_ID_PARAM)
                .then()
                .statusCode(RESPONSE_STATUS_OK)
                .body(PET_FIELD_NAME, equalTo("Benny"))
                .body(PET_FIELD_STATUS, equalToIgnoringCase(PetStatus.AVAILABLE.getValue()));
    }

    @Test
    public void shouldReturnUnsupportedMediaTypeForInvalidContentType() {
        Pet pet = PetModelHelper.availablePetWithImage(1007, "Bella");

        getRequestSpec()
                .body(pet)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_OK);

        getRequestSpecWithAuth()
                .pathParam(PET_ID, 1001)
                .contentType(ContentType.JSON)
                .formParam(PET_FIELD_NAME, "BellaUpdated")
                .when()
                .post(PET_WITH_PATH_ID_PARAM)
                .then()
                .statusCode(RESPONSE_STATUS_UNSUPPORTED_MEDIA_TYPE);
    }

    @Test
    public void shouldReturnErrorWhenEmptyDataIsSent() {
        getRequestSpec()
                .pathParam(PET_ID, 1008)
                .contentType(ContentType.URLENC)
                .formParam(PET_FIELD_NAME, "")
                .formParam(PET_FIELD_STATUS, "")
                .when()
                .post(PET_WITH_PATH_ID_PARAM)
                .then()
                .statusCode(RESPONSE_STATUS_NOT_FOUND);
    }

    @Test
    public void shouldReturnNotFoundForNonExistingPetId() {
        getRequestSpec()
                .pathParam(PET_ID, -1)
                .contentType(ContentType.URLENC)
                .formParam(PET_FIELD_NAME, "NonExistentPet")
                .formParam(PET_FIELD_STATUS, PetStatus.SOLD.getValue())
                .when()
                .post(PET_WITH_PATH_ID_PARAM)
                .then()
                .statusCode(RESPONSE_STATUS_NOT_FOUND);
    }
}
