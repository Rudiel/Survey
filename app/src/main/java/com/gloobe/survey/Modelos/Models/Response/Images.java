package com.gloobe.survey.Modelos.Models.Response;

/**
 * Created by rudielavilaperaza on 12/9/16.
 */

public class Images {

    private int id;
    private File file;
    private int question_id;
    private String created_at;
    private String updated_at;
    private String name;
    private String reference_path;

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getReference_path() {
        return reference_path;
    }

    public void setReference_path(String reference_path) {
        this.reference_path = reference_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
