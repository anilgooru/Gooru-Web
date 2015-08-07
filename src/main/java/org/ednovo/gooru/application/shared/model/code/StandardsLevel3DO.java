package org.ednovo.gooru.application.shared.model.code;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;

@JsonInclude(Include.NON_NULL)
public class StandardsLevel3DO implements IsSerializable
{
	
	private static final long serialVersionUID = 1L;
	private String label;
	private ArrayList<StandardsLevel4DO> node;
	private Integer parentId;
	private Integer codeId;
	private String code;
	
	public StandardsLevel3DO(){}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}


	public ArrayList<StandardsLevel4DO> getNode() {
		return node;
	}

	public void setNode(ArrayList<StandardsLevel4DO> node) {
		this.node = node;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getCodeId() {
		return codeId;
	}

	public void setCodeId(Integer codeId) {
		this.codeId = codeId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
