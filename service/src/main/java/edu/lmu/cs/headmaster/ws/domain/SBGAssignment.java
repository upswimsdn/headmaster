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
public class SBGAssignment {
    private Long id;
    private String title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SBGProficiency> getProficiencies() {
        return proficiencies;
    }

    public void setProficiencies(List<SBGProficiency> proficiencies) {
        this.proficiencies = proficiencies;
    }
}
