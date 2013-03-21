package edu.lmu.cs.headmaster.ws.domain.sbg;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@XmlRootElement
public class Assignment {
    private Long id;
    private String title;
    private String description;
    private DateTime assignedDate;
    private DateTime dueDate;
    private List<Outcome> proficiencies;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlAttribute
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    public DateTime getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(DateTime assignedDate) {
        this.assignedDate = assignedDate;
    }

    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    public DateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(DateTime dueDate) {
        this.dueDate = dueDate;
    }

    @ManyToMany
    @LazyCollection(LazyCollectionOption.TRUE)
    @XmlTransient
    public List<Outcome> getProficiencies() {
        return proficiencies;
    }

    public void setProficiencies(List<Outcome> proficiencies) {
        this.proficiencies = proficiencies;
    }
}
