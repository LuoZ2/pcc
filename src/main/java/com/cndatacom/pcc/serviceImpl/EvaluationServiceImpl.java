package com.cndatacom.pcc.serviceImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cndatacom.pcc.dao.EvaluationMapper;
import com.cndatacom.pcc.domain.Evaluation;
import com.cndatacom.pcc.service.EvaluationService;
@Service
public class EvaluationServiceImpl implements EvaluationService {

	@Autowired
	private EvaluationMapper evaluationMapper;
	@Override
	public int addEvaluation(Evaluation evaluation) {
		evaluation.setPjId(new Date().getTime());
		evaluation.setCreated(new Date());
		if(evaluation.getPjScore() == null) {
			evaluation.setPjScore(5);
		}
		int isSuccess = evaluationMapper.insert(evaluation);
		return isSuccess;
	}

}
