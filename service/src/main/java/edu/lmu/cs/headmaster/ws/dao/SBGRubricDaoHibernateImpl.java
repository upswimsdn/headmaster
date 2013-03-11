package edu.lmu.cs.headmaster.ws.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.lmu.cs.headmaster.ws.domain.SBGRubric;

public class SBGRubricDaoHibernateImpl extends HibernateDaoSupport implements SBGRubricDao {

    @Override
    public SBGRubric getRubricById(Long id) {
        return getHibernateTemplate().get(SBGRubric.class, id);
    }

    @Override
    public List<SBGRubric> getRubrics(String query) {
        return null;
    }

    @Override
    public SBGRubric createRubric(SBGRubric rubric) {
        getHibernateTemplate().save(rubric);
        return rubric;
    }

    @Override
    public void createOrUpdateRubric(SBGRubric rubric) {
        getHibernateTemplate().saveOrUpdate(rubric);
    }

}
