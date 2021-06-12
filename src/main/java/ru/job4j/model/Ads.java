package ru.job4j.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "ads")
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    private boolean status;

    private String photo;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "body_type_id")
    private BodyType bodyType;

    public static Ads of(String description, boolean status, String photo) {
        Ads ads = new Ads();
        ads.description = description;
        ads.status = status;
        ads.photo = photo;
        ads.created = new Date(System.currentTimeMillis());
        return ads;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ads ads = (Ads) o;
        return status == ads.status && Objects.equals(id, ads.id)
                && Objects.equals(description, ads.description)
                && Objects.equals(created, ads.created) && Objects.equals(photo, ads.photo)
                && Objects.equals(car, ads.car) && Objects.equals(author, ads.author)
                && Objects.equals(bodyType, ads.bodyType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, created, status, photo, car, author, bodyType);
    }
}




