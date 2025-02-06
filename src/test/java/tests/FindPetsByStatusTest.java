package tests;

import models.PetStatus;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;
import static utils.TestConstants.*;

public class FindPetsByStatusTest extends BaseAPITest {

    @Test
    public void shouldFindAvailablePetsSuccessfully() {
        getRequestSpec()
                .queryParams(PET_FIELD_STATUS, PetStatus.AVAILABLE.getValue())
                .when()
                .get(PET_STATUS_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_OK)
                .body(PET_FIELD_STATUS, everyItem(equalToIgnoringCase(PetStatus.AVAILABLE.getValue())));
    }

    @Test
    public void shouldFindPendingPetsSuccessfully() {
        getRequestSpec()
                .queryParams(PET_FIELD_STATUS, PetStatus.PENDING.getValue())
                .when()
                .get(PET_STATUS_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_OK)
                .body(PET_FIELD_STATUS, everyItem(equalToIgnoringCase(PetStatus.PENDING.getValue())));
    }

    @Test
    public void shouldFindSoldPetsSuccessfully() {
        getRequestSpec()
                .queryParams(PET_FIELD_STATUS, PetStatus.SOLD.getValue())
                .when()
                .get(PET_STATUS_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_OK)
                .body(PET_FIELD_STATUS, everyItem(equalToIgnoringCase(PetStatus.SOLD.getValue())));
    }

    @Test
    public void shouldReturnBadRequestForNonExistingStatus() {
        getRequestSpec()
                .queryParams(PET_FIELD_STATUS, "not-existing-status")
                .when()
                .get(PET_STATUS_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_BAD_REQUEST);
    }

    @Test
    public void shouldReturnBadRequestIfNoStatusIsProvided() {
        getRequestSpec()
                .when()
                .get(PET_STATUS_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_BAD_REQUEST);
    }
}
