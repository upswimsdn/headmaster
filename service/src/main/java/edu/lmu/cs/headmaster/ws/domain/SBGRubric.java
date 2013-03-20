package edu.lmu.cs.headmaster.ws.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import edu.lmu.cs.headmaster.ws.types.GradingStrategy;

@Embeddable
@XmlRootElement
public class SBGRubric {
    private List<SBGOutcome> outcomes = new ArrayList<SBGOutcome>();
    private List<SBGAssignment> assignments = new ArrayList<SBGAssignment>();
    private GradingStrategy gradingMethod;

    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<SBGOutcome> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<SBGOutcome> outcomes) {
        this.outcomes = outcomes;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
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
    
    public void addOutcome(SBGOutcome outcome) {
        this.outcomes.add(outcome);
    }

}
