package edu.lmu.cs.headmaster.ws.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class SBGRubric {
    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlAttribute
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private List<SBGOutcome> outcomes;
    private List<SBGAssignment> assignments;

    public List<SBGOutcome> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<SBGOutcome> outcomes) {
        this.outcomes = outcomes;
    }

    public List<SBGAssignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<SBGAssignment> assignments) {
        this.assignments = assignments;
    }
}
