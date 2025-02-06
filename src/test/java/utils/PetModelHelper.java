package utils;

import models.Pet;
import models.PetStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PetModelHelper {
    private static final String MOCK_PET_IMAGE_URL_1 = "https://img.freepik.com/premium-psd/adorable-dog-wearing-clothing-mockup_23-2151225174.jpg";
    private static final String MOCK_PET_IMAGE_URL_2 = "https://img.freepik.com/premium-psd/adorable-dog-wearing-clothing-mockup_23-2151225030.jpg";

    private static final List<String> petImageList = Collections.singletonList(MOCK_PET_IMAGE_URL_1);

    public static Pet availablePetWithImage(int id, String name) {
        return new Pet(id, name, PetStatus.AVAILABLE, petImageList);
    }

    public static Pet soldPet(int id, String name) {
        return new Pet(id, name, PetStatus.SOLD, petImageList);
    }

    public static Pet pendingPet(int id, String name) {
        return new Pet(id, name, PetStatus.PENDING, petImageList);
    }

    public static Pet petWithoutName(int id) {
        return new Pet(id, "", PetStatus.AVAILABLE, petImageList);
    }

    public static Pet petWithInvalidId(String name) {
        return new Pet(-1, name, PetStatus.AVAILABLE, petImageList);
    }

    public static Pet petWithMultipleImages(int id, String name) {
        List<String> multipleImages = Arrays.asList(
                MOCK_PET_IMAGE_URL_1,
                MOCK_PET_IMAGE_URL_2
        );
        return new Pet(id, name, PetStatus.AVAILABLE, multipleImages);
    }

    public static Pet petWithEmptyPhotoUrls(int id, String name) {
        List<String> emptyImages = Collections.emptyList();
        return new Pet(id, name, PetStatus.AVAILABLE, emptyImages);
    }

    public static Pet petWithoutStatus(int id, String name) {
        return new Pet(id, name, null, petImageList);
    }

    public static Pet petWithLargeId(long id, String name) {
        return new Pet((int) id, name, PetStatus.AVAILABLE, petImageList);
    }

    public static Pet petWithSpecialCharactersInName(int id, String name) {
        return new Pet(id, name, PetStatus.AVAILABLE, petImageList);
    }
}