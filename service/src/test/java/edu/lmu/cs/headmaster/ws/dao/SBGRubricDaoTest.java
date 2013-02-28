package edu.lmu.cs.headmaster.ws.dao;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import edu.lmu.cs.headmaster.ws.domain.SBGOutcome;
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
    
    @Test
    public void testUpdateSBGRubric() {
        SBGRubric r = rubricDao.getRubricById(100001L);
        String newCourseName = "brand new";
        r.setCourseName(newCourseName);
        rubricDao.createOrUpdateRubric(r);
        r = rubricDao.getRubricById(100001L);
        Assert.assertEquals(newCourseName, r.getCourseName());
    }

    @Test
    public void testCreateNewOutcomeForSBGRubric() {
        SBGRubric before = rubricDao.getRubricById(100001L);
        List<SBGOutcome> outcomes = before.getOutcomes();
        SBGOutcome newOutcome = new SBGOutcome();
        newOutcome.setDescription("oink oink");
        outcomes.add(newOutcome);
        before.setOutcomes(outcomes);
        rubricDao.createOrUpdateRubric(before);

        SBGRubric after = rubricDao.getRubricById(100001L);
        Assert.assertEquals("oink oink", after.getOutcomes().get(0).getDescription());
    }
}
