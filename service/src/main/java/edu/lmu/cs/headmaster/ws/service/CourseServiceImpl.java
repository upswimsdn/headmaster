package edu.lmu.cs.headmaster.ws.service;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import edu.lmu.cs.headmaster.ws.dao.CourseDao;
import edu.lmu.cs.headmaster.ws.domain.Course;
import edu.lmu.cs.headmaster.ws.types.DayOfWeek;
import edu.lmu.cs.headmaster.ws.types.Term;

public class CourseServiceImpl implements CourseService {

    private CourseDao courseDao;

    public CourseServiceImpl(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    public List<Course> getCourses(String discipline, String classTimes, String instructor, Integer maxClassSize,
            Integer minClassSize, Term term, Integer year, int skip, int max) {
        List<DateTime> requestedSchedule = classTimes == null ? null : processClassTimeQuery(classTimes);
        return courseDao.getCourses(discipline, requestedSchedule, instructor, maxClassSize, minClassSize, term, year, skip, max);
    }

    @Override
    public void createCourse(Course course) {
        courseDao.createOrUpdateCourse(course);
    }

    @Override
    public void createOrUpdateCourse(Long id, Course course) {
        courseDao.createOrUpdateCourse(course);
    }

    @Override
    public Course getCourseById(Long id) {
        return courseDao.getCourseById(id);
    }

    public List<DateTime> processClassTimeQuery(String query) {
        // schedule[1] holds the string that is to be parsed for hours and minutes
        String[] schedule = query.split(",");
        String[] requestedDays = schedule[0].split("-");
        List<DateTime> requestedDateTimes = new ArrayList<DateTime>();

        for (String day : requestedDays) {
            requestedDateTimes.add(createDateTime(day, schedule[1]));
        }

        return requestedDateTimes;
    }

    public DateTime createDateTime(String day, String timeOfDay) {
        // timeOfDay should be a string of four characters going from 0000 to 2359
        int date = retrieveDayInteger(day);
        int hours = Integer.parseInt(timeOfDay.substring(0, 2));
        int minutes = Integer.parseInt(timeOfDay.substring(2));
        return new DateTime(DEFAULT_YEAR, DEFAULT_MONTH, date, hours, minutes, 0, 0);
    }

    public int retrieveDayInteger(String dayString) {
        int dayOffset = DayOfWeek.valueOf(dayString).ordinal();
        return DEFAULT_MONDAY + dayOffset;
    }

}
