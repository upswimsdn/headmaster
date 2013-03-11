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

@Entity
@XmlRootElement
public class SBGRecord {
    private Long id;
    private List<SBGAssignmentReport> gradedAssignments = new ArrayList<SBGAssignmentReport>();

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
    @JoinTable(joinColumns = @JoinColumn(name = "sbgrecord_id"), inverseJoinColumns = @JoinColumn(name = "sbgassignmentreport_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    @XmlTransient
    public List<SBGAssignmentReport> getGrades() {
        return gradedAssignments;
    }

    public void setGrades(List<SBGAssignmentReport> gradedAssignments) {
        this.gradedAssignments = gradedAssignments;
    }

    public void addAssignmentReport(SBGAssignmentReport a) {
        this.gradedAssignments.add(a);
    }

}
