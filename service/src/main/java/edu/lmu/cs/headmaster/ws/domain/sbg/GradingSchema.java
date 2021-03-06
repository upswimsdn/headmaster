package edu.lmu.cs.headmaster.ws.domain.sbg;

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
public class GradingSchema {
    private List<Objective> objectives = new ArrayList<Objective>();
    private List<Assignment> assignments = new ArrayList<Assignment>();
    private GradingStrategy gradingMethod;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<Objective> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<Objective> objectives) {
        this.objectives = objectives;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public GradingStrategy getGradingMethod() {
        return gradingMethod;
    }

    public void setGradingMethod(GradingStrategy gradingMethod) {
        this.gradingMethod = gradingMethod;
    }
    
    public void addObjective(Objective objective) {
        this.objectives.add(objective);
    }
    
    public void addAssignment(Assignment assignment) {
        this.assignments.add(assignment);
    }

}
