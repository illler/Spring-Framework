package org.crud.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int person_id;
    @NotEmpty(message = "Full name should not be empty")
    @Size(min = 2, max = 255, message = "Full name should be between 2 and 255 characters")
    @Pattern(regexp = "[А-ЯЁA-Z][а-яёa-z]+ [А-ЯЁA-Z][а-яёa-z]+ [А-ЯЁA-Z][а-яёa-z]+", message = "Необходимый формат имени: Иванов Иван Иванович")
    private String fullname;
    @Min(value = 1900, message = "Year should be greater than 1900")
    @Max(value = 2023, message = "Age should be less than 2023")
    private int year_of_birth;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Book> items;



    public Person(String fullname, int year_of_birth) {
        this.fullname = fullname;
        this.year_of_birth = year_of_birth;
    }

    public Person(){}

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getYear_of_birth() {
        return year_of_birth;
    }

    public void setYear_of_birth(int year_of_birth) {
        this.year_of_birth = year_of_birth;
    }

    public List<Book> getItems() {
        return items;
    }

    public void setItems(List<Book> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return person_id == person.person_id && year_of_birth == person.year_of_birth && Objects.equals(fullname, person.fullname) && Objects.equals(items, person.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person_id, fullname, year_of_birth, items);
    }
}
