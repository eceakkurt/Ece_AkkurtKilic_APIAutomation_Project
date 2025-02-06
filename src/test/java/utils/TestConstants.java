package utils;

public class TestConstants {
    public static final String PET_ID = "petId";
    public static final String PET_FIELD_ID = "id";
    public static final String PET_FIELD_NAME = "name";
    public static final String PET_FIELD_STATUS = "status";

    public static final int RESPONSE_STATUS_OK = 200;
    public static final int RESPONSE_STATUS_BAD_REQUEST = 400;
    public static final int RESPONSE_STATUS_UNAUTHORIZED = 401;
    public static final int RESPONSE_STATUS_NOT_FOUND = 404;
    public static final int RESPONSE_STATUS_METHOD_NOT_ALLOWED = 405;
    public static final int RESPONSE_STATUS_UNSUPPORTED_MEDIA_TYPE = 415;

    public static final String PET_ENDPOINT = "/pet";
    public static final String PET_STATUS_ENDPOINT = PET_ENDPOINT + "/findByStatus";
    public static final String PET_WITH_PATH_ID_PARAM = PET_ENDPOINT + "/{petId}";
    public static final String PET_UPLOAD_IMAGE_ENDPOINT = PET_WITH_PATH_ID_PARAM + "/uploadImage";

    public static final String BASE_URL = "https://petstore.swagger.io/v2";
    public static final String API_KEY = "special-key";
}
