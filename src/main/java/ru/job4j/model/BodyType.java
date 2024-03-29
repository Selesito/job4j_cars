package ru.job4j.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "body_type")
public class BodyType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    public static BodyType of(String name) {
        BodyType bodyType = new BodyType();
        bodyType.name = name;
        return bodyType;
    }

    public static BodyType of(int id) {
        BodyType bodyType = new BodyType();
        bodyType.id = id;
        return bodyType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BodyType bodyType = (BodyType) o;
        return id == bodyType.id && Objects.equals(name, bodyType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
