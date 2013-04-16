package edu.lmu.cs.headmaster.ws.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.Duration;

public class DurationXmlAdapter extends XmlAdapter<Long, Duration> {

    @Override
    public Duration unmarshal(Long millis) throws Exception {
        return new Duration(millis);
    }

    @Override
    public Long marshal(Duration duration) throws Exception {
        return new Long(duration.getMillis());
    }

}
