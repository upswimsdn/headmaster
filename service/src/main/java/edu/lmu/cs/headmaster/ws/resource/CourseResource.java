package edu.lmu.cs.headmaster.ws.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.lmu.cs.headmaster.ws.domain.Course;
import edu.lmu.cs.headmaster.ws.types.Term;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface CourseResource {
    
    static final String COURSE_NOT_FOUND = "course.not.found";
    
    @GET
    List<Course> getCourses(@QueryParam("discipline") String discipline, @QueryParam("classTimes") String classTimes,
            @QueryParam("instructor") String instructor, @QueryParam("maxClassSize") Integer maxClassSize,
            @QueryParam("minClassSize") Integer minClassSize, @QueryParam("term") Term term,
            @QueryParam("year") Integer year, @QueryParam("skip") @DefaultValue("0") int skip,
            @QueryParam("max") @DefaultValue("50") int max);

    @POST
    Response createCourse(Course course);

    @PUT
    @Path("{id}")
    Response createOrUpdateCourse(@PathParam("id") Long id, Course course);

    @GET
    @Path("{id}")
    Course getCourseById(@PathParam("id") Long id);

}
