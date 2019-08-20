package com.cndatacom.pcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cndatacom.pcc.VO.ResponseVO;
import com.cndatacom.pcc.domain.Evaluation;
import com.cndatacom.pcc.service.EvaluationService;

@RestController
public class EvaluationController {

	@Autowired
	private EvaluationService evaluationService;
	
	@RequestMapping("/evaluation/addEvaluation")
	public ResponseVO addEvaluation(String texts ,Integer pjScore,Long oid) {
		String[] strs = texts.split("-");
		Evaluation evaluation = new Evaluation();
		evaluation.setOid(oid);
		evaluation.setPjScore(pjScore);
		for (String text : strs) {
			switch (text) {
			case "导航准确":
				evaluation.setPjNavigation(1);
				break;
			case "举止文明礼貌":
				evaluation.setPjPolite(1);
				break;
			case "行驶安全平稳":
				evaluation.setPjSecurity(1);
				break;
			case "车内整洁无异味":
				evaluation.setPjClean(1);
				break;
			default:
				break;
			}
		}

		int isSuccess = evaluationService.addEvaluation(evaluation);
		if(isSuccess > 0) {
			return ResponseVO.success("评价成功！");
		}else {
			return ResponseVO.serviceFail("业务异常，请稍后再试");
		}
	}
}
