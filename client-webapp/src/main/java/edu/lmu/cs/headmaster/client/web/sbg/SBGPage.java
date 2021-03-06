package edu.lmu.cs.headmaster.client.web.sbg;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.lmu.cs.headmaster.client.web.Headmaster;


/**
 * SBGHome is the superclass for all of Headmaster's Standards Based Grading Views
 */
@AuthorizeInstantiation(Roles.USER)
public class SBGPage extends WebPage {

    public SBGPage(final PageParameters pageParameters) {
        super(pageParameters);
    }

    /**
     * Relay call to the application. Mainly a shortcut, so wedon't have to code out
     * ((Headmaster)getApplication()).getServiceRoot() all the time.
     */
    protected String getServiceRoot() {
        return ((Headmaster) getApplication()).getServiceRoot();
    }

    /**
     * Convenience method for grabbing the logger. This is not stored as an instance variable because Wicket serializes
     * web page classes and this logger is not serializable.
     */
    protected Logger getLogger() {
        return LoggerFactory.getLogger(getClass());
    }

}
