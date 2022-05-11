package cloud.huel.crm.workbench.web.service;

import cloud.huel.crm.workbench.web.domain.ClueActivityRelation;

import java.util.List;

/**
 * @author 张晓华
 * @version 1.0
 */
public interface ClueActivityRelationService {

	Integer addClueActivityAssociation(List<ClueActivityRelation> clueActivityRelationList);


	Integer deleteClueActivityAssociation(ClueActivityRelation clueActivityRelation);


}
