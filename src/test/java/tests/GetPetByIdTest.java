package tests;

import models.Pet;
import models.PetStatus;
import org.testng.annotations.Test;
import utils.PetModelHelper;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static utils.TestConstants.*;

public class GetPetByIdTest extends BaseAPITest {

    @Test
    public void shouldGetPetByIdSuccessfully() {
        Pet pet = PetModelHelper.availablePetWithImage(1001, "Lucky");

        getRequestSpec()
                .body(pet)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_OK);

        getRequestSpec()
                .pathParam(PET_ID, 1001)
                .when()
                .get(PET_WITH_PATH_ID_PARAM)
                .then()
                .statusCode(RESPONSE_STATUS_OK)
                .body(PET_FIELD_ID, equalTo(1001))
                .body(PET_FIELD_NAME, equalTo("Lucky"))
                .body(PET_FIELD_STATUS, equalToIgnoringCase(PetStatus.AVAILABLE.getValue()));
    }

    @Test
    public void shouldReturnNotFoundForNonExistingPetIdOld() throws Exception {
        try {
            getRequestSpec()
                    .pathParam(PET_ID, 99999)
                    .when()
                    .get(PET_WITH_PATH_ID_PARAM)
                    .then()
                    .statusCode(RESPONSE_STATUS_NOT_FOUND);
        } catch (Exception e) {
            validate404ExceptionAsResponse(e);
        }
    }

    @Test
    public void shouldReturnNotFoundForNonExistingPetId() throws Exception {
        try {
            getRequestSpec()
                    .pathParam(PET_ID, 99999)
                    .when()
                    .get(PET_WITH_PATH_ID_PARAM)
                    .then()
                    .statusCode(RESPONSE_STATUS_NOT_FOUND);
        } catch (Exception e) {
            validate404ExceptionAsResponse(e);
        }
    }

    @Test
    public void shouldReturnBadRequestForInvalidPetId() throws Exception {
        try {
            getRequestSpec()
                    .pathParam(PET_ID, "invalid_id")
                    .when()
                    .get(PET_WITH_PATH_ID_PARAM)
                    .then()
                    .statusCode(RESPONSE_STATUS_NOT_FOUND);
        } catch (Exception e) {
            validate404ExceptionAsResponse(e);
        }
    }

    @Test
    public void shouldReturnMethodNotAllowedForPostRequest() {
        getRequestSpec()
                .pathParam(PET_ID, 1001)
                .when()
                .post(PET_WITH_PATH_ID_PARAM)
                .then()
                .statusCode(RESPONSE_STATUS_UNSUPPORTED_MEDIA_TYPE);
    }
}
