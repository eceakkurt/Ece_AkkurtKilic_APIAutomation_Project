package models;

import com.google.gson.Gson;

import java.util.List;

public class Pet {
    private int id;
    private String name;
    private PetStatus status;
    private List<String> photoUrls;

    public Pet(int id, String name, PetStatus status, List<String> photoUrls) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.photoUrls = photoUrls;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PetStatus getStatus() {
        return status;
    }

    public void setStatus(PetStatus status) {
        this.status = status;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
