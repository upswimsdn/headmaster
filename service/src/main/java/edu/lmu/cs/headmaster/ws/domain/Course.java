package edu.lmu.cs.headmaster.ws.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import edu.lmu.cs.headmaster.ws.domain.sbg.Rubric;
import edu.lmu.cs.headmaster.ws.types.Term;
import edu.lmu.cs.headmaster.ws.util.DateTimeXmlAdapter;
import edu.lmu.cs.headmaster.ws.util.DurationXmlAdapter;

@Entity
@XmlRootElement
public class Course {
    private Long id;
    private String classTitle;
    private String description;
    private String discipline;
    private Integer credits;
    private String room;
    private Term term;
    private Integer year;
    private List<DateTime> classTimes = new ArrayList<DateTime>();
    private Duration classLength;
    private Integer classSize;
    private String instructor;
    private List<Student> enrolledStudents = new ArrayList<Student>();
    private Rubric rubric;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlAttribute
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassTitle() {
        return classTitle;
    }

    public void setClassTitle(String classTitle) {
        this.classTitle = classTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getClassSize() {
        return classSize;
    }

    public void setClassSize(Integer classSize) {
        this.classSize = classSize;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @OrderBy("lastName")
    @JoinTable(joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(List<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    /*
     * For class times, we'll just arbitrarily pick the week of Monday, February 18, 2013 to Sunday, February 24, 2013
     * to determine what days a class takes place. joda-time does not have a feature to just store an arbitrary day of
     * the week and hour. Note to future Eric: should probably have checks against any dates that are passed in outside
     * of this range and to shave also off seconds or minutes.
     */
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    @XmlJavaTypeAdapter(value = DateTimeXmlAdapter.class)
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<DateTime> getClassTimes() {
        return classTimes;
    }

    public void setClassTimes(List<DateTime> classTimes) {
        this.classTimes = classTimes;
    }

    @Type(type = "org.joda.time.contrib.hibernate.PersistentDuration")
    @XmlJavaTypeAdapter(value = DurationXmlAdapter.class)
    public Duration getClassLength() {
        return classLength;
    }

    public void setClassLength(Duration classLength) {
        this.classLength = classLength;
    }

    public Rubric getRubric() {
        return rubric;
    }

    public void setRubric(Rubric rubric) {
        this.rubric = rubric;
    }
}
