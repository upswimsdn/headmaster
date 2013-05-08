package edu.lmu.cs.headmaster.ws.resource;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;

import edu.lmu.cs.headmaster.ws.dao.UserDao;
import edu.lmu.cs.headmaster.ws.domain.Course;
import edu.lmu.cs.headmaster.ws.domain.Student;
import edu.lmu.cs.headmaster.ws.service.CourseService;
import edu.lmu.cs.headmaster.ws.types.Term;

@Path("/courses")
public class CourseResourceImpl extends AbstractResource implements CourseResource {

    private CourseService courseService;

    public CourseResourceImpl(UserDao userDao, CourseService courseService) {
        super(userDao);
        this.courseService = courseService;
    }

    @Override
    public List<Course> getCourses(String discipline, String classTimes, String instructor, Integer maxClassSize,
            Integer minClassSize, Term term, Integer year, String title, int skip, int max) {
        logServiceCall();

        // Error check all the query parameters
        validateQueryParameters(discipline, classTimes, instructor, maxClassSize, minClassSize, term, year, title, skip, max);

        // Process and validate the class time query string
        List<DateTime> schedule = processQueryByClassTime(classTimes);

        return courseService.getCourses(discipline, schedule, instructor, maxClassSize, minClassSize, term, year,
                title, skip, max);
    }

    @Override
    public Response createCourse(Course course) {
        logServiceCall();

        // Only faculty/staff are able to create new courses.
        validatePrivilegedUserCredentials();

        validate(course.getId() == null, Response.Status.BAD_REQUEST, CourseResource.COURSE_OVERSPECIFIED);

        courseService.createCourse(course, retrieveCurrentUsername());
        return Response.created(URI.create(Long.toString(course.getId()))).build();
    }

    @Override
    public Response createOrUpdateCourse(Long id, Course course) {
        logServiceCall();

        // Only faculty/staff are able to update any course information.
        validatePrivilegedUserCredentials();

        // The course IDs should match.
        validate(id.equals(course.getId()), Response.Status.BAD_REQUEST, ID_INCONSISTENT);

        // Dao problems will filter up as exceptions.
        courseService.createOrUpdateCourse(id, course);
        return Response.noContent().build();
    }

    @Override
    public Course getCourseById(Long id) {
        logServiceCall();

        Course course = courseService.getCourseById(id);
        validate(course != null, Response.Status.NOT_FOUND, COURSE_NOT_FOUND);
        return course;
    }

    @Override
    public List<Student> getEnrolledStudentsById(Long id) {
        validatePrivilegedUserCredentials();
        return courseService.getEnrolledStudentsById(id);
    }

    /**
     * Helper method to verify the query by class time is correctly formatted
     */
    public Boolean verifyDateTimeIsWithinRange(DateTime date) {
        return LEGAL_DATE_RANGE.contains(date);
    }

    /**
     * Helper method to convert timestamps to Java DateTimes for querying courses by classTime.
     */
    public List<DateTime> processQueryByClassTime(String times) {
        List<DateTime> schedule = null;
        if (times != null) {
            String[] dateTimes = times.split(",");
            schedule = new ArrayList<DateTime>();
            for (String s : dateTimes) {
                DateTime d = toDateTime(s);
                validate(verifyDateTimeIsWithinRange(d), Response.Status.BAD_REQUEST,
                        COURSE_CLASSTIME_QUERY_OUT_OF_RANGE);
                schedule.add(d);
            }
        }
        return schedule;
    }

    /**
     * Method to check validate parameters and throw service errors when requesting courses by any query.
     */
    public void validateQueryParameters(String discipline, String classTimes, String instructor, Integer maxClassSize,
            Integer minClassSize, Term term, Integer year, String title, int skip, int max) {
        // Check that we have at least on query parameter
        validate(!(discipline == null && classTimes == null && instructor == null && maxClassSize == null
                && minClassSize == null && term == null && year == null && title == null), Response.Status.BAD_REQUEST, QUERY_REQUIRED);

        // Check that both term and year are either present or not present
        validate(checkMutualInclusionOfParameters(term, year), Response.Status.BAD_REQUEST, ARGUMENT_CONFLICT);

        // Check that pagination are within reasonable bounds
        validatePagination(skip, max, 0, 50);
    }
}
