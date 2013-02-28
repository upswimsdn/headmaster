package edu.lmu.cs.headmaster.ws.dao;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import edu.lmu.cs.headmaster.ws.domain.SBGRubric;
import edu.lmu.cs.headmaster.ws.util.ApplicationContextTest;

public class SBGRubricDaoTest extends ApplicationContextTest {
    private SBGRubricDao rubricDao;

    @Before
    public void getRequiredBeans() {
        rubricDao = (SBGRubricDao) applicationContext.getBean("rubricDao");
    }
    
    @Test
    public void testGetDaoById() {
        SBGRubric r = rubricDao.getRubricById(100001L);
        Assert.assertEquals(Long.valueOf(100001L), r.getId());
        Assert.assertEquals("Intro to Database Systems", r.getCourseName());
    }
}
