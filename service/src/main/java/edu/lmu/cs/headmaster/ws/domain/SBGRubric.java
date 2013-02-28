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
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import edu.lmu.cs.headmaster.ws.types.GradingStrategy;

@Entity
@XmlRootElement
public class SBGRubric {
    private Long id;
    private String courseName;
    private List<SBGOutcome> outcomes = new ArrayList<SBGOutcome>();
    private List<SBGAssignment> assignments = new ArrayList<SBGAssignment>();
    private GradingStrategy gradingMethod;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlAttribute
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(joinColumns = @JoinColumn(name = "rubric_id"), inverseJoinColumns = @JoinColumn(name = "outcome_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    @XmlTransient
    public List<SBGOutcome> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<SBGOutcome> outcomes) {
        this.outcomes = outcomes;
    }
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(joinColumns = @JoinColumn(name = "rubric_id"), inverseJoinColumns = @JoinColumn(name = "assignment_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    @XmlTransient
    public List<SBGAssignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<SBGAssignment> assignments) {
        this.assignments = assignments;
    }

    public GradingStrategy getGradingMethod() {
        return gradingMethod;
    }

    public void setGradingMethod(GradingStrategy gradingMethod) {
        this.gradingMethod = gradingMethod;
    }
}
