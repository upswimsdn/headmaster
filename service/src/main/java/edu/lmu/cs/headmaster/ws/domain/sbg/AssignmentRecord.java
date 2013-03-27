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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@XmlRootElement
public class AssignmentRecord {
    private Long id;
    private List<AssignmentFeedback> feedback = new ArrayList<AssignmentFeedback>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlAttribute
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(joinColumns = @JoinColumn(name = "record_id"), inverseJoinColumns = @JoinColumn(name = "feedback_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<AssignmentFeedback> getFeedback() {
        return feedback;
    }

    public void setFeedback(List<AssignmentFeedback> gradedAssignments) {
        this.feedback = gradedAssignments;
    }

    public void addAssignmentReport(AssignmentFeedback a) {
        this.feedback.add(a);
    }

}
