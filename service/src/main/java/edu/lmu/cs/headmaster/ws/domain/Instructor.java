package edu.lmu.cs.headmaster.ws.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * An Instructor is someone who is professing a class.
 */
@Entity
@XmlRootElement
public class Instructor {
    private Long id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String primaryEmail;
    private String secondaryEmail;
    private List<Course> instructedCourses = new ArrayList<Course>();
    private String login;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlAttribute
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Lob
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Lob
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Lob
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Lob
    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    @Lob
    public String getSecondaryEmail() {
        return secondaryEmail;
    }

    public void setSecondaryEmail(String secondaryEmail) {
        this.secondaryEmail = secondaryEmail;
    }

    @OneToMany(orphanRemoval = true)
    @XmlTransient
    public List<Course> getInstructedCourses() {
        return instructedCourses;
    }

    public void setInstructedCourses(List<Course> instructedCourses) {
        this.instructedCourses = instructedCourses;
    }

    @Lob
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}
