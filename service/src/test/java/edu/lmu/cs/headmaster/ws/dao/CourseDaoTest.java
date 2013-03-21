package edu.lmu.cs.headmaster.ws.dao;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.junit.Before;
import org.junit.Test;

import edu.lmu.cs.headmaster.ws.domain.Course;
import edu.lmu.cs.headmaster.ws.domain.Student;
import edu.lmu.cs.headmaster.ws.domain.sbg.Assignment;
import edu.lmu.cs.headmaster.ws.domain.sbg.Objective;
import edu.lmu.cs.headmaster.ws.domain.sbg.Outcome;
import edu.lmu.cs.headmaster.ws.domain.sbg.Rubric;
import edu.lmu.cs.headmaster.ws.types.Term;
import edu.lmu.cs.headmaster.ws.util.ApplicationContextTest;

public class CourseDaoTest extends ApplicationContextTest {

    private CourseDao courseDao;

    @Before
    public void getRequiredBeans() {
        courseDao = (CourseDao) applicationContext.getBean("courseDao");
    }

    @Test
    public void testGetCourseById() {
        Course c = courseDao.getCourseById(100001L);
        Assert.assertEquals(Long.valueOf(100001L), c.getId());
        Assert.assertEquals("Dr. Dondi", c.getInstructor());

        Assert.assertEquals(3, c.getClassTimes().size());
        Assert.assertEquals(DateTimeConstants.MONDAY, c.getClassTimes().get(0).getDayOfWeek());
        Assert.assertEquals(11, c.getClassTimes().get(0).getHourOfDay());
        Assert.assertEquals(DateTimeConstants.WEDNESDAY, c.getClassTimes().get(1).getDayOfWeek());
        Assert.assertEquals(11, c.getClassTimes().get(1).getHourOfDay());
        Assert.assertEquals(DateTimeConstants.MONDAY, c.getClassTimes().get(0).getDayOfWeek());
        Assert.assertEquals(11, c.getClassTimes().get(0).getHourOfDay());
        Assert.assertEquals(DateTimeConstants.WEDNESDAY, c.getClassTimes().get(1).getDayOfWeek());
        Assert.assertEquals(11, c.getClassTimes().get(1).getHourOfDay());
        Assert.assertEquals(DateTimeConstants.FRIDAY, c.getClassTimes().get(2).getDayOfWeek());
        Assert.assertEquals(11, c.getClassTimes().get(2).getHourOfDay());

        Assert.assertEquals(2, c.getEnrolledStudents().size());
        Assert.assertEquals(Long.valueOf(1000000L), c.getEnrolledStudents().get(0).getId());
        Assert.assertEquals(Long.valueOf(1000001L), c.getEnrolledStudents().get(1).getId());

        Assert.assertEquals(c.getClassLength().getMillis(), 3000000L);
    }

    @Test
    public void testUpdatedCourseFieldsCanBeRead() {
        Course c = courseDao.getCourseById(100002L);
        Assert.assertEquals(Long.valueOf(100002L), c.getId());
        Assert.assertEquals("Prof. X", c.getInstructor());

        c.setInstructor("Dolores Umbridge");
        courseDao.createOrUpdateCourse(c);

        c = courseDao.getCourseById(100002L);
        Assert.assertEquals(Long.valueOf(100002L), c.getId());
        Assert.assertEquals("Dolores Umbridge", c.getInstructor());
    }

    @Test
    public void testEnrollingNewStudentToCourse() {
        Course beforeCourse = courseDao.getCourseById(100001L);
        Assert.assertEquals(2, beforeCourse.getEnrolledStudents().size());
        Student student = new Student();
        student.setId(1000002L);
        beforeCourse.getEnrolledStudents().add(student);
        courseDao.createOrUpdateCourse(beforeCourse);
        Course afterCourse = courseDao.getCourseById(100001L);
        Assert.assertEquals(3, afterCourse.getEnrolledStudents().size());
        Assert.assertEquals(Long.valueOf(1000002L), afterCourse.getEnrolledStudents().get(2).getId());
    }

    @Test
    public void testGetCoursesByDiscipline() {
        List<Course> courses = courseDao.getCourses("Computer Science", null, null, null, null, null, null, 0, 10);
        Assert.assertEquals(1, courses.size());
        Assert.assertEquals(Long.valueOf(100003L), courses.get(0).getId());
    }

    @Test
    public void testGetCoursesByTerm() {
        List<Course> courses = courseDao.getCourses(null, null, null, null, null, Term.SUMMER, null, 0, 10);
        Assert.assertEquals(2, courses.size());
        Assert.assertEquals(Long.valueOf(100001L), courses.get(0).getId());
        Assert.assertEquals(Long.valueOf(100003L), courses.get(1).getId());
    }

    public void testGetCoursesByYear() {
        List<Course> courses = courseDao.getCourses(null, null, null, null, null, null, 2013, 0, 10);
        Assert.assertEquals(2, courses.size());
        Assert.assertEquals(Long.valueOf(100002L), courses.get(0).getId());
        Assert.assertEquals(Long.valueOf(100003L), courses.get(1).getId());
    }

    @Test
    public void testGetCoursesByTermAndYear() {
        List<Course> courses = courseDao.getCourses(null, null, null, null, null, Term.SUMMER, 2013, 0, 10);
        Assert.assertEquals(1, courses.size());
        Assert.assertEquals(Long.valueOf(100003L), courses.get(0).getId());
    }

    @Test
    public void testGetCoursesByClassTime() {
        List<DateTime> times = new ArrayList<DateTime>();
        times.add(new DateTime(2013, 2, 18, 11, 0, 0, 0));
        times.add(new DateTime(2013, 2, 20, 11, 0, 0, 0));
        List<Course> courses = courseDao.getCourses(null, times, null, null, null, null, null, 0, 10);
        Assert.assertEquals(1, courses.size());

        times.clear();
        times.add(new DateTime(2013, 2, 20, 12, 0, 0, 0));
        courses = courseDao.getCourses(null, times, null, null, null, null, null, 0, 10);
        Assert.assertEquals(0, courses.size());

        times.add(new DateTime(2013, 2, 20, 12, 0, 0, 0));
        times.add(new DateTime(2013, 2, 18, 11, 0, 0, 0));
        courses = courseDao.getCourses(null, times, null, null, null, null, null, 0, 10);
        Assert.assertEquals(1, courses.size());
    }

    @Test
    public void testGetCoursesByMaxClassSize() {
        List<Course> courses = courseDao.getCourses(null, null, null, 1, null, null, null, 0, 10);
        Assert.assertEquals(0, courses.size());

        courses = courseDao.getCourses(null, null, null, 10, null, null, null, 0, 10);
        Assert.assertEquals(1, courses.size());
        Assert.assertEquals(Long.valueOf(100003L), courses.get(0).getId());

        courses = courseDao.getCourses(null, null, null, 10, null, null, null, 0, 10);
        Assert.assertEquals(1, courses.size());
        Assert.assertEquals(Long.valueOf(100003L), courses.get(0).getId());
    }

    @Test
    public void testGetCoursesByMinClassSize() {
        List<Course> courses = courseDao.getCourses(null, null, null, null, 10, null, null, 0, 10);
        Assert.assertEquals(3, courses.size());
        Assert.assertEquals(Long.valueOf(100001L), courses.get(0).getId());
        Assert.assertEquals(Long.valueOf(100002L), courses.get(1).getId());
        Assert.assertEquals(Long.valueOf(100003L), courses.get(2).getId());

        courses = courseDao.getCourses(null, null, null, null, 20, null, null, 0, 10);
        Assert.assertEquals(2, courses.size());
        Assert.assertEquals(Long.valueOf(100001L), courses.get(0).getId());
        Assert.assertEquals(Long.valueOf(100002L), courses.get(1).getId());

        courses = courseDao.getCourses(null, null, null, null, 30, null, null, 0, 10);
        Assert.assertEquals(0, courses.size());
    }

    @Test
    public void testGetCoursesByMinAndMaxClassSize() {
        List<Course> courses = courseDao.getCourses(null, null, null, 15, 10, null, null, 0, 10);
        Assert.assertEquals(1, courses.size());

        courses = courseDao.getCourses(null, null, null, 30, 25, null, null, 0, 10);
        Assert.assertEquals(0, courses.size());
    }

    @Test
    public void testGetCoursesByInstructor() {
        List<Course> courses = courseDao.getCourses(null, null, "Prof. X", null, null, null, null, 0, 10);
        Assert.assertEquals(1, courses.size());
        Assert.assertEquals(Long.valueOf(100002L), courses.get(0).getId());
        Assert.assertEquals("Prof. X", courses.get(0).getInstructor());

        courses = courseDao.getCourses(null, null, "prof. x", null, null, null, null, 0, 10);
        Assert.assertEquals(1, courses.size());
        Assert.assertEquals(Long.valueOf(100002L), courses.get(0).getId());
        Assert.assertEquals("Prof. X", courses.get(0).getInstructor());

        courses = courseDao.getCourses(null, null, "asgsad", null, null, null, null, 0, 10);
        Assert.assertEquals(0, courses.size());
    }

    /*
     * *********************
     * Testing for SBGRubric*********************
     */

    @Test
    public void testCreateNewSBGRubricForExistingCourse() {
        Course course = courseDao.getCourseById(100002L);
        Rubric rubric = new Rubric();
        Outcome proficiency = new Outcome("Herk a derr");
        Objective outcome = new Objective("Derk a herr", proficiency);
        rubric.addOutcome(outcome);
        course.setRubric(rubric);
        courseDao.createOrUpdateCourse(course);
        Course after = courseDao.getCourseById(100002L);
        Rubric r = after.getRubric();
        Assert.assertEquals(1, r.getObjectives().size());
        Assert.assertEquals("Derk a herr", r.getObjectives().get(0).getDescription());
        Assert.assertEquals("Herk a derr", r.getObjectives().get(0).getOutcomes().get(0).getDescription());
    }

    @Test
    public void testUpdateSBGRubricWithNewOutcome() {
        Course course = courseDao.getCourseById(100001L);
        Rubric before = course.getRubric();
        List<Objective> o = before.getObjectives();
        Assert.assertEquals(1, o.size());
        Assert.assertEquals("Become adept at using a CLI", o.get(0).getDescription());
        Assert.assertEquals(3, o.get(0).getOutcomes().size());
        Assert.assertEquals("Successfully SSH tunnel into a machine", o.get(0).getOutcomes().get(0).getDescription());
        Assert.assertEquals("Pipeline outputs between various programs", o.get(0).getOutcomes().get(1).getDescription());
        Assert.assertEquals("Use grep to the output of another program", o.get(0).getOutcomes().get(2).getDescription());

        o.get(0).addProficiency(new Outcome("Sexy shell"));
        before.setObjectives(o);
        course.setRubric(before);
        courseDao.createOrUpdateCourse(course);
        
        course = courseDao.getCourseById(100001L);
        Rubric after = course.getRubric();
        o = after.getObjectives();
        Assert.assertEquals(1, o.size());
        Assert.assertEquals("Become adept at using a CLI", o.get(0).getDescription());
        Assert.assertEquals(4, o.get(0).getOutcomes().size());
        Assert.assertEquals("Sexy shell", o.get(0).getOutcomes().get(0).getDescription());
        Assert.assertEquals("Successfully SSH tunnel into a machine", o.get(0).getOutcomes().get(1).getDescription());
        Assert.assertEquals("Pipeline outputs between various programs", o.get(0).getOutcomes().get(2).getDescription());
        Assert.assertEquals("Use grep to the output of another program", o.get(0).getOutcomes().get(3).getDescription());
    }
    
    @Test
    public void testCreateAssignmentForRubric() {
        Course course = courseDao.getCourseById(100001L);
        Rubric r = course.getRubric();
        Assert.assertEquals(0, r.getAssignments().size());

        Assignment a = new Assignment();
        Outcome o = new Outcome();
        o.setId(100001L);
        a.addOutcome(o);
        r.addAssignment(a);
        course.setRubric(r);
        courseDao.createOrUpdateCourse(course);
        
        course = courseDao.getCourseById(100001L);
        r = course.getRubric();
        Assert.assertEquals(1, r.getAssignments().size());
        Assert.assertEquals("Successfully SSH tunnel into a machine", r.getAssignments().get(0).getOutcomes().get(0).getDescription());
    }
}
