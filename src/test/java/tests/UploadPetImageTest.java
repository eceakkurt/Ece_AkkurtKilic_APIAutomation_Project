package tests;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.io.File;

import static org.hamcrest.Matchers.containsString;
import static utils.TestConstants.*;

public class UploadPetImageTest extends BaseAPITest {

    private static final int TEST_PET_ID = 9999;
    private static final String MULTIPART_FILE = "file";

    @Test
    public void shouldUploadValidPetImageSuccessfully() {
        File imageFile = new File("src/test/resources/pet_image.jpg");

        getRequestSpec()
                .pathParam(PET_ID, TEST_PET_ID)
                .contentType(ContentType.MULTIPART)
                .multiPart(MULTIPART_FILE, imageFile)
                .when()
                .post(PET_UPLOAD_IMAGE_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_OK)
                .body("message", containsString("File uploaded"));
    }

    @Test
    public void shouldUploadJpegImageSuccessfully() {
        File jpegFile = new File("src/test/resources/pet_image.jpeg");

        getRequestSpec()
                .contentType(ContentType.MULTIPART)
                .multiPart(MULTIPART_FILE, jpegFile)
                .pathParam(PET_ID, TEST_PET_ID)
                .when()
                .post(PET_UPLOAD_IMAGE_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_OK)
                .body("message", containsString("File uploaded"));
    }

    @Test
    public void shouldReturnUnsupportedMediaTypeForInvalidFileType() {
        File invalidFile = new File("src/test/resources/sample.txt");

        getRequestSpec()
                .contentType(ContentType.MULTIPART)
                .multiPart(MULTIPART_FILE, invalidFile)
                .pathParam(PET_ID, TEST_PET_ID)
                .when()
                .post(PET_UPLOAD_IMAGE_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_UNSUPPORTED_MEDIA_TYPE);
    }

    @Test
    public void shouldUploadLargeImageSuccessfully() {
        File largeFile = new File("src/test/resources/8k.jpg");

        getRequestSpec()
                .contentType(ContentType.MULTIPART)
                .multiPart(MULTIPART_FILE, largeFile)
                .pathParam(PET_ID, TEST_PET_ID)
                .when()
                .post(PET_UPLOAD_IMAGE_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_OK)
                .body("message", containsString("File uploaded"));
    }

    @Test
    public void shouldReturnNotFoundForInvalidPetId() throws Exception {
        File imageFile = new File("src/test/resources/pet_image.jpg");

        try {
            getRequestSpec()
                    .contentType(ContentType.MULTIPART)
                    .multiPart(MULTIPART_FILE, imageFile)
                    .pathParam(PET_ID, -1)
                    .when()
                    .post(PET_UPLOAD_IMAGE_ENDPOINT)
                    .then()
                    .statusCode(RESPONSE_STATUS_NOT_FOUND);
        } catch (Exception e) {
            validate404ExceptionAsResponse(e);
        }
    }

    @Test
    public void shouldReturnBadRequestWhenNoFileIsProvided() {
        getRequestSpec()
                .contentType(ContentType.MULTIPART)
                .pathParam(PET_ID, TEST_PET_ID)
                .when()
                .post(PET_UPLOAD_IMAGE_ENDPOINT)
                .then()
                .statusCode(RESPONSE_STATUS_BAD_REQUEST);
    }
}

