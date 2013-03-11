package edu.lmu.cs.headmaster.ws.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@XmlRootElement
public class SBGAssignmentReport {
    private Long id;
    private SBGAssignment assignment;
    private DateTime submissionDate;
    private List<SBGGrade> grades = new ArrayList<SBGGrade>();

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
    public SBGAssignment getAssignment() {
        return assignment;
    }

    public void setAssignment(SBGAssignment assignment) {
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
    @JoinTable(joinColumns = @JoinColumn(name = "sbgassignmentreport_id"), inverseJoinColumns = @JoinColumn(name = "sbggrade_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    @XmlTransient
    public List<SBGGrade> getGrades() {
        return grades;
    }

    public void setGrades(List<SBGGrade> grades) {
        this.grades = grades;
    }
}
