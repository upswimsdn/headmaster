package edu.lmu.cs.headmaster.ws.resource;

import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import edu.lmu.cs.headmaster.ws.dao.UserDao;
import edu.lmu.cs.headmaster.ws.domain.Course;
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
            Integer minClassSize, Term term, Integer year, int skip, int max) {
        logServiceCall();

        // Check that we have atleast on query parameter
        validate(!(discipline == null && classTimes == null && instructor == null && maxClassSize == null
                && minClassSize == null && term == null && year == null), Response.Status.BAD_REQUEST, QUERY_REQUIRED);

        // Check that both term and year and either present or not present
        validate(checkMutualInclusionOfParameters(term, year), Response.Status.BAD_REQUEST, ARGUMENT_CONFLICT);
        return null;
    }

    @Override
    public Response createCourse(Course course) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response createOrUpdateCourse(Long id, Course course) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Course getCourseById(Long id) {
        logServiceCall();

        Course course = courseService.getCourseById(id);
        validate(course != null, Response.Status.NOT_FOUND, COURSE_NOT_FOUND);
        return course;
    }

}
