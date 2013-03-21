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
public class Objective {
    private Long id;
    private String description;
    private List<Outcome> outcomes = new ArrayList<Outcome>();
    private Integer orderIndex;

    public Objective() {

    }

    public Objective(String description, Outcome proficiency) {
        this.description = description;
        this.outcomes.add(proficiency);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlAttribute
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(joinColumns = @JoinColumn(name = "objective_id"), inverseJoinColumns = @JoinColumn(name = "outcome_id"))
    @XmlTransient
    public List<Outcome> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<Outcome> outcomes) {
        this.outcomes = outcomes;
    }

    public void addProficiency(Outcome p) {
        this.outcomes.add(p);
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }
    
    
}
