package models;

public class Categories_class {
    String id_class, name_class;
    String course_name;

    public Categories_class() {
    }

    public Categories_class(String id_class, String name_class) {
        this.id_class = id_class;
        this.name_class = name_class;
    }

    public String getId_class() {
        return id_class;
    }

    public void setId_class(String id_class) {
        this.id_class = id_class;
    }

    public String getName_class() {
        return name_class;
    }

    public void setName_class(String name_class) {
        this.name_class = name_class;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }
}