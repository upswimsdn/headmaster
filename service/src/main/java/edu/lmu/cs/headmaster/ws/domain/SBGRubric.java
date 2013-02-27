package edu.lmu.cs.headmaster.ws.domain;

import java.util.List;

public class SBGRubric {
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
