package edu.lmu.cs.headmaster.ws.domain;

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

@Entity
@XmlRootElement
public class SBGOutcome {
    private Long id;
    private String description;
    private List<SBGProficiency> proficiencies;

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
    @JoinTable(joinColumns = @JoinColumn(name = "outcome_id"), inverseJoinColumns = @JoinColumn(name = "proficiency_id"))
    @XmlTransient
    public List<SBGProficiency> getProficiencies() {
        return proficiencies;
    }

    public void setProficiencies(List<SBGProficiency> proficiencies) {
        this.proficiencies = proficiencies;
    }
}
