package edu.lmu.cs.headmaster.ws.resource;

import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;

import edu.lmu.cs.headmaster.ws.domain.Course;

@Path("/courses")
public class CourseResourceImpl implements CourseResource {

    @Override
    public List<Course> getCourses(String discipline, List<DateTime> classTimes, String instructor,
            Integer maxClassSize, Integer minClassSize, String term, Integer year, int skip, int max) {
        // TODO Auto-generated method stub
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
        // TODO Auto-generated method stub
        return null;
    }

}
