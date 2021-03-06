package edu.lmu.cs.headmaster.ws.resource;

import java.util.List;

import junit.framework.Assert;

import org.joda.time.DateTimeConstants;
import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

import edu.lmu.cs.headmaster.ws.domain.Course;
import edu.lmu.cs.headmaster.ws.domain.sbg.Assignment;
import edu.lmu.cs.headmaster.ws.domain.sbg.Objective;
import edu.lmu.cs.headmaster.ws.domain.sbg.Outcome;
import edu.lmu.cs.headmaster.ws.domain.sbg.GradingSchema;
import edu.lmu.cs.headmaster.ws.types.Term;

public class CourseResourceTest extends ResourceTest {

    @Test
    public void testGetCourseById() {
        Course c = wr.path("/courses/100001").get(Course.class);

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

        Assert.assertEquals(c.getClassLength().getMillis(), 3000000L);
    }

    @Test
    public void testGetNonExistantCourseByIdResponds404() {
        ClientResponse response = wr.path("courses/8008135").get(ClientResponse.class);
        Assert.assertEquals(404, response.getStatus());
        Assert.assertEquals("404 " + CourseResource.COURSE_NOT_FOUND, response.getEntity(String.class));
    }

    @Test
    public void testGetCourseWithNoQueryResponds400() {
        ClientResponse response = wr.path("courses").get(ClientResponse.class);
        Assert.assertEquals(400, response.getStatus());
        Assert.assertEquals("400 " + AbstractResource.QUERY_REQUIRED, response.getEntity(String.class));
    }

    @Test
    public void testMutualInclusionOfTermAndYearQueryParameters() {
        ClientResponse response = wr.path("courses").queryParam("term", "FALL").get(ClientResponse.class);
        Assert.assertEquals(400, response.getStatus());
        Assert.assertEquals("400 " + AbstractResource.ARGUMENT_CONFLICT, response.getEntity(String.class));

        response = wr.path("courses").queryParam("year", "2012").get(ClientResponse.class);
        Assert.assertEquals(400, response.getStatus());
        Assert.assertEquals("400 " + AbstractResource.ARGUMENT_CONFLICT, response.getEntity(String.class));

        response = wr.path("courses").queryParam("term", "SUMMER").queryParam("year", "2014").get(ClientResponse.class);
        Assert.assertTrue(response.getStatus() >= 200);
    }

    @Test
    public void testGetCoursesByDiscipline() {
        List<Course> courses = wr.path("courses")
                .queryParam("discipline", "Computer Science")
                .get(new GenericType<List<Course>>() {
                });
        Assert.assertEquals(1, courses.size());
        Assert.assertEquals(Long.valueOf(100003L), courses.get(0).getId());
        Assert.assertEquals("Computer Science", courses.get(0).getDiscipline());
    }

    @Test
    public void testGetCoursesByTermAndYear() {
        List<Course> courses = wr.path("courses")
                .queryParam("term", "SUMMER")
                .queryParam("year", "2013")
                .get(new GenericType<List<Course>>() {
                });
        Assert.assertEquals(1, courses.size());
        Assert.assertEquals(Long.valueOf(100003L), courses.get(0).getId());
        Assert.assertEquals(Term.SUMMER, courses.get(0).getTerm());
        Assert.assertEquals(Integer.valueOf(2013), courses.get(0).getYear());
    }

    @Test
    public void testGetCoursesByMaxClassSize() {
        List<Course> courses = wr.path("courses").queryParam("maxClassSize", "1").get(new GenericType<List<Course>>() {
        });
        Assert.assertEquals(0, courses.size());

        courses = wr.path("courses").queryParam("maxClassSize", "10").get(new GenericType<List<Course>>() {
        });
        Assert.assertEquals(1, courses.size());
        Assert.assertEquals(Long.valueOf(100003L), courses.get(0).getId());

        courses = wr.path("courses").queryParam("maxClassSize", "20").get(new GenericType<List<Course>>() {
        });
        Assert.assertEquals(3, courses.size());
        Assert.assertEquals(Long.valueOf(100001L), courses.get(0).getId());
        Assert.assertEquals(Long.valueOf(100002L), courses.get(1).getId());
        Assert.assertEquals(Long.valueOf(100003L), courses.get(2).getId());
    }

    @Test
    public void testGetCoursesByMinClassSize() {
        List<Course> courses = wr.path("courses").queryParam("minClassSize", "10").get(new GenericType<List<Course>>() {
        });
        Assert.assertEquals(3, courses.size());
        Assert.assertEquals(Long.valueOf(100001L), courses.get(0).getId());
        Assert.assertEquals(Long.valueOf(100002L), courses.get(1).getId());
        Assert.assertEquals(Long.valueOf(100003L), courses.get(2).getId());

        courses = wr.path("courses").queryParam("minClassSize", "20").get(new GenericType<List<Course>>() {
        });
        Assert.assertEquals(2, courses.size());
        Assert.assertEquals(Long.valueOf(100001L), courses.get(0).getId());
        Assert.assertEquals(Long.valueOf(100002L), courses.get(1).getId());

        courses = wr.path("courses").queryParam("minClassSize", "30").get(new GenericType<List<Course>>() {
        });
        Assert.assertEquals(0, courses.size());
    }

    @Test
    public void testGetCoursesByMinAndMaxClassSize() {
        List<Course> courses = wr.path("courses")
                .queryParam("minClassSize", "10")
                .queryParam("maxClassSize", "15")
                .get(new GenericType<List<Course>>() {
                });
        Assert.assertEquals(1, courses.size());

        courses = wr.path("courses")
                .queryParam("minClassSize", "30")
                .queryParam("maxClassSize", "25")
                .get(new GenericType<List<Course>>() {
                });
        Assert.assertEquals(0, courses.size());
    }

    @Test
    public void testGetCoursesByInstructor() {
        List<Course> courses = wr.path("courses")
                .queryParam("instructor", "Prof. X")
                .get(new GenericType<List<Course>>() {
                });
        Assert.assertEquals(1, courses.size());
        Assert.assertEquals(Long.valueOf(100002L), courses.get(0).getId());
        Assert.assertEquals("Prof. X", courses.get(0).getInstructor());

        courses = wr.path("courses").queryParam("instructor", "prof. x").get(new GenericType<List<Course>>() {
        });
        Assert.assertEquals(1, courses.size());
        Assert.assertEquals(Long.valueOf(100002L), courses.get(0).getId());
        Assert.assertEquals("Prof. X", courses.get(0).getInstructor());

        courses = wr.path("courses").queryParam("instructor", "asgsad").get(new GenericType<List<Course>>() {
        });
        Assert.assertEquals(0, courses.size());
    }

    @Test
    public void testCreateNewSBGRubricForExistingCourse() {
        Course course = wr.path("courses/100002").get(Course.class);
        GradingSchema rubric = new GradingSchema();
        Outcome outcome = new Outcome("Herk a derr");
        Objective objective = new Objective("Derk a herr", outcome);
        rubric.addObjective(objective);
        course.setGradingSchema(rubric);
        Assert.assertEquals(1, objective.getOutcomes().size());
        wr.path("courses/100002").put(course);
        Course after = wr.path("courses/100002").get(Course.class);
        GradingSchema r = after.getGradingSchema();
        Assert.assertEquals(1, r.getObjectives().size());
        Assert.assertEquals("Derk a herr", r.getObjectives().get(0).getDescription());
        Assert.assertEquals("Herk a derr", r.getObjectives().get(0).getOutcomes().get(0).getDescription());
    }

    @Test
    public void testUpdateSBGRubricWithNewOutcome() {
        Course course = wr.path("courses/100001").get(Course.class);
        GradingSchema before = course.getGradingSchema();
        List<Objective> o = before.getObjectives();
        Assert.assertEquals(1, o.size());
        Assert.assertEquals("Become adept at using a CLI", o.get(0).getDescription());
        Assert.assertEquals(3, o.get(0).getOutcomes().size());
        Assert.assertEquals("Successfully SSH tunnel into a machine", o.get(0).getOutcomes().get(0).getDescription());
        Assert.assertEquals("Pipeline outputs between various programs", o.get(0).getOutcomes().get(1).getDescription());
        Assert.assertEquals("Use grep to the output of another program", o.get(0).getOutcomes().get(2).getDescription());

        o.get(0).addProficiency(new Outcome("Sexy shell"));
        before.setObjectives(o);
        course.setGradingSchema(before);
        wr.path("courses/100001").put(course);

        course = wr.path("courses/100001").get(Course.class);
        GradingSchema after = course.getGradingSchema();
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
        Course course = wr.path("courses/100001").get(Course.class);
        GradingSchema r = course.getGradingSchema();
        Assert.assertEquals(0, r.getAssignments().size());

        Assignment a = new Assignment();
        Outcome o = new Outcome();
        o.setId(100001L);
        a.addOutcome(o);
        r.addAssignment(a);
        course.setGradingSchema(r);
        wr.path("courses/100001").put(course);

        Course course2 = wr.path("courses/100001").get(Course.class);
        r = course2.getGradingSchema();
        Assert.assertEquals(1, r.getAssignments().size());
        Assert.assertEquals("Successfully SSH tunnel into a machine", r.getAssignments()
                .get(0)
                .getOutcomes()
                .get(0)
                .getDescription());
    }

    @Test
    public void testDeleteRubricFromCourse() {
        Course course = wr.path("courses/100001").get(Course.class);
        List<Objective> o = course.getGradingSchema().getObjectives();
        Assert.assertEquals(1, o.size());
        Assert.assertEquals("Become adept at using a CLI", o.get(0).getDescription());
        Assert.assertEquals(3, o.get(0).getOutcomes().size());
        Assert.assertEquals("Successfully SSH tunnel into a machine", o.get(0).getOutcomes().get(0).getDescription());
        Assert.assertEquals("Pipeline outputs between various programs", o.get(0).getOutcomes().get(1).getDescription());
        Assert.assertEquals("Use grep to the output of another program", o.get(0).getOutcomes().get(2).getDescription());
        course.setGradingSchema(null);
        wr.path("courses/100001").put(course);
        Course course2 = wr.path("courses/100001").get(Course.class);
        Assert.assertEquals(0, course2.getGradingSchema().getObjectives().size());
        Assert.assertEquals(0, course2.getGradingSchema().getAssignments().size());
    }

    @Test
    public void testGetCoursesByClassTime() {
        List<Course> courses = wr.path("courses")
                .queryParam("classTimes", "2013-02-18T11:00,2013-02-20T11:00:00")
                .get(new GenericType<List<Course>>() {
                });
        Assert.assertEquals(2, courses.size());
        Assert.assertEquals(Long.valueOf(100001), courses.get(0).getId());
        Assert.assertEquals(Long.valueOf(100004), courses.get(1).getId());

        courses = wr.path("courses")
                .queryParam("classTimes", "2013-02-22T12:00:00")
                .get(new GenericType<List<Course>>() {
                });
        Assert.assertEquals(1, courses.size());
        Assert.assertEquals(Long.valueOf(100004), courses.get(0).getId());

        courses = wr.path("courses")
                .queryParam("classTimes", "2013-02-22T11:00:00")
                .get(new GenericType<List<Course>>() {
                });
        Assert.assertEquals(1, courses.size());
        Assert.assertEquals(Long.valueOf(100001), courses.get(0).getId());

        courses = wr.path("courses")
                .queryParam("classTimes", "2013-02-22T12:00:00")
                .get(new GenericType<List<Course>>() {
                });
        Assert.assertEquals(1, courses.size());
        Assert.assertEquals(Long.valueOf(100004), courses.get(0).getId());
    }

    @Test
    public void testGetCoursesByClasstimeWithMalformedTimestampResponds400() {
        ClientResponse response = wr.path("courses")
                .queryParam("classTimes", "NotATimestamp")
                .get(ClientResponse.class);
        Assert.assertEquals(400, response.getStatus());
        Assert.assertEquals("400 " + AbstractResource.MALFORMED_ARGUMENT_DATE, response.getEntity(String.class));

        response = wr.path("courses").queryParam("classTimes", "2013-02-22 12:00:00").get(ClientResponse.class);
        Assert.assertEquals(400, response.getStatus());
        Assert.assertEquals("400 " + AbstractResource.MALFORMED_ARGUMENT_DATE, response.getEntity(String.class));

        response = wr.path("courses").queryParam("classTimes", "2013-02-22T12:89:00").get(ClientResponse.class);
        Assert.assertEquals(400, response.getStatus());
        Assert.assertEquals("400 " + AbstractResource.MALFORMED_ARGUMENT_DATE, response.getEntity(String.class));
    }

    @Test
    public void testGetCoursesByClassTimeWithTimestampOutOfRangeResponds400() {
        ClientResponse response = wr.path("courses")
                .queryParam("classTimes", "2013-02-25T00:00:00")
                .get(ClientResponse.class);
        Assert.assertEquals(400, response.getStatus());
        Assert.assertEquals("400 " + CourseResource.COURSE_CLASSTIME_QUERY_OUT_OF_RANGE,
                response.getEntity(String.class));

        response = wr.path("courses").queryParam("classTimes", "2014-02-18T00:00:00").get(ClientResponse.class);
        Assert.assertEquals(400, response.getStatus());
        Assert.assertEquals("400 " + CourseResource.COURSE_CLASSTIME_QUERY_OUT_OF_RANGE,
                response.getEntity(String.class));
    }

    @Test
    public void testGetCoursesWithTooLargeSkipAndMaxResponds400() {
        List<Course> courses = wr.path("courses")
                .queryParam("discipline", "Computer Science")
                .get(new GenericType<List<Course>>() {
                });
        Assert.assertEquals(1, courses.size());
        Assert.assertEquals(Long.valueOf(100003L), courses.get(0).getId());
        Assert.assertEquals("Computer Science", courses.get(0).getDiscipline());

        ClientResponse response = wr.path("courses")
                .queryParam("discipline", "Computer Science")
                .queryParam("max", "51")
                .get(ClientResponse.class);
        Assert.assertEquals(400, response.getStatus());
        Assert.assertEquals("400 " + AbstractResource.MAX_TOO_LARGE, response.getEntity(String.class));

        response = wr.path("courses")
                .queryParam("discipline", "Computer Science")
                .queryParam("skip", "-1")
                .get(ClientResponse.class);
        Assert.assertEquals(400, response.getStatus());
        Assert.assertEquals("400 " + AbstractResource.SKIP_TOO_SMALL, response.getEntity(String.class));
    }

    @Test
    public void testCreateNewCourseWithIdResponds400() {
        Course courseToAdd = new Course();
        courseToAdd.setId(25L);
        ClientResponse response = wr.path("courses").post(ClientResponse.class, courseToAdd);
        Assert.assertEquals(400, response.getStatus());
        Assert.assertEquals("400 " + CourseResource.COURSE_OVERSPECIFIED, response.getEntity(String.class));
    }

    @Test
    public void testCreateNewCourse() {
        Course courseToAdd = new Course();
        courseToAdd.setTitle("New Beginnings");
        ClientResponse response = wr.path("courses").post(ClientResponse.class, courseToAdd);
        Assert.assertEquals(201, response.getStatus());
        Assert.assertEquals(wr.getURI() + "/courses/1", response.getHeaders().getFirst("Location"));

        Course newCourse = wr.path("courses/1").get(Course.class);
        Assert.assertEquals(Long.valueOf(1L), newCourse.getId());
        Assert.assertEquals("New Beginnings", newCourse.getTitle());
    }
}
