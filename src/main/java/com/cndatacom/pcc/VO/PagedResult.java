package com.cndatacom.pcc.VO;

import java.io.Serializable;
import java.util.List;
import com.cndatacom.pcc.domain.Travels;

import lombok.Data;

@Data
public class PagedResult implements Serializable{
	private int Num;
    private List<?> rows; //查询出来的list集合
    private int totalPage;
    private int nowPage;
}
