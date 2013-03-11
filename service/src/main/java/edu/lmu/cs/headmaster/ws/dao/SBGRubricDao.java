package edu.lmu.cs.headmaster.ws.dao;

import java.util.List;

import edu.lmu.cs.headmaster.ws.domain.SBGRubric;

public interface SBGRubricDao {
    SBGRubric getRubricById(Long id);
    
    List<SBGRubric> getRubrics(String query);
    
    SBGRubric createRubric(SBGRubric rubric);
    
    void createOrUpdateRubric(SBGRubric rubric);
}
