package edu.lmu.cs.headmaster.ws.resource;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;

import edu.lmu.cs.headmaster.ws.domain.Course;
import edu.lmu.cs.headmaster.ws.domain.Event;
import edu.lmu.cs.headmaster.ws.domain.GPA;
import edu.lmu.cs.headmaster.ws.domain.Grant;
import edu.lmu.cs.headmaster.ws.domain.Major;
import edu.lmu.cs.headmaster.ws.domain.Student;
import edu.lmu.cs.headmaster.ws.domain.StudentRecord;
import edu.lmu.cs.headmaster.ws.domain.User;
import edu.lmu.cs.headmaster.ws.domain.UserRole;
import edu.lmu.cs.headmaster.ws.domain.sbg.Assignment;
import edu.lmu.cs.headmaster.ws.domain.sbg.AssignmentFeedback;
import edu.lmu.cs.headmaster.ws.domain.sbg.AssignmentRecord;
import edu.lmu.cs.headmaster.ws.domain.sbg.Objective;
import edu.lmu.cs.headmaster.ws.domain.sbg.Outcome;
import edu.lmu.cs.headmaster.ws.domain.sbg.OutcomeEvaluation;
import edu.lmu.cs.headmaster.ws.domain.sbg.GradingSchema;
import edu.lmu.cs.headmaster.ws.types.Role;
import edu.lmu.cs.headmaster.ws.types.Term;

/**
 * A provider which ensures that JSON content is generated using the <em>natural</em> style,
 * rather than the default <em>mapped</em> style.
 */
@Provider
public class NaturalJsonContextResolver implements ContextResolver<JAXBContext> {

    private final JAXBContext context;

    private final Class<?>[] types = {

        // Domain classes requiring JSON serialization.
        Event.class,
        GPA.class,
        Grant.class,
        Major.class,
        Role.class,
        Student.class,
        StudentRecord.class,
        Course.class,
        Assignment.class,
        AssignmentFeedback.class,
        AssignmentRecord.class,
        Objective.class,
        Outcome.class,
        OutcomeEvaluation.class,
        GradingSchema.class,
        Term.class,
        User.class,
        UserRole.class
    };

    private final List<Class<?>> typeList = Arrays.asList(types);

    /**
     * Creates the context to use the natural JSON style.
     */
    public NaturalJsonContextResolver() throws Exception {
        context = new JSONJAXBContext(JSONConfiguration.natural().build(), types);
    }

    @Override
    public JAXBContext getContext(Class<?> objectType) {
        return (typeList.contains(objectType)) ? context : null;
    }
}
