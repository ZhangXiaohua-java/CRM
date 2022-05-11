package cloud.huel.crm.workbench.web.service.impl;

import cloud.huel.crm.workbench.web.dao.ClueActivityRelationMapper;
import cloud.huel.crm.workbench.web.domain.ClueActivityRelation;
import cloud.huel.crm.workbench.web.service.ClueActivityRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 张晓华
 * @version 1.0
 */
@Service
public class ClueActivityRelationServiceImpl implements ClueActivityRelationService {

	@Autowired
	private ClueActivityRelationMapper clueActivityRelationMapper;

	@Override
	public Integer addClueActivityAssociation(List<ClueActivityRelation> clueActivityRelationList) {
		return clueActivityRelationMapper.addAssociation(clueActivityRelationList);
	}

	@Override
	public Integer deleteClueActivityAssociation(ClueActivityRelation clueActivityRelation) {
		return clueActivityRelationMapper.deleteClueActivityAssociation(clueActivityRelation);
	}

}
