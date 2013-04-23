package edu.lmu.cs.headmaster.client.web;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

public class CourseWriterPage extends ClientPage {
    
    public CourseWriterPage(final PageParameters pageParameters) {
        super(pageParameters);

        // Relay the requested course ID to the rendered web page.
        add(new Label(
            "course-id",
            pageParameters.containsKey("id") ?
                new Model<Long>(pageParameters.getLong("id")) :
                new Model<String>() {
                    public String getObject() {
                        return "";
                    }
                }
        ));
    }

}
