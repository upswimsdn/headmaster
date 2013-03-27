package edu.lmu.cs.headmaster.ws.domain.sbg;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@XmlRootElement
public class AssignmentFeedback {
    private Long id;
    private Assignment assignment;
    private DateTime submissionDate;
    private List<OutcomeEvaluation> grades = new ArrayList<OutcomeEvaluation>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlAttribute
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToOne
    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    public DateTime getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(DateTime submissionDate) {
        this.submissionDate = submissionDate;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(joinColumns = @JoinColumn(name = "feedback_id"), inverseJoinColumns = @JoinColumn(name = "outcomeevaluation_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    @XmlTransient
    public List<OutcomeEvaluation> getGrades() {
        return grades;
    }

    public void setGrades(List<OutcomeEvaluation> grades) {
        this.grades = grades;
    }
}
