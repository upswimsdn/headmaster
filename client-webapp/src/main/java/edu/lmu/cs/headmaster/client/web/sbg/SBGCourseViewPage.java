package edu.lmu.cs.headmaster.client.web.sbg;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

public class SBGCourseViewPage extends SBGPage {

    public SBGCourseViewPage(PageParameters pageParameters) {
        super(pageParameters);

        add(new Label("course-id", new Model<Long>(pageParameters.getLong("id"))));
    }

}
