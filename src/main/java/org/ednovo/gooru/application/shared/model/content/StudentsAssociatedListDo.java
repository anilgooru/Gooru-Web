/*******************************************************************************
 * Copyright 2013 Ednovo d/b/a Gooru. All rights reserved.
 * 
 *  http://www.goorulearning.org/
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining
 *  a copy of this software and associated documentation files (the
 *  "Software"), to deal in the Software without restriction, including
 *  without limitation the rights to use, copy, modify, merge, publish,
 *  distribute, sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so, subject to
 *  the following conditions:
 * 
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 * 
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *  OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 *  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/
package org.ednovo.gooru.application.shared.model.content;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;

@JsonInclude(Include.NON_NULL)
public class StudentsAssociatedListDo implements IsSerializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3223243025838735212L;
	private List<CollaboratorsDo> searchResults;
	private Integer totalHitCount;
	private List<CollaboratorsDo> searchResult;

	
	public StudentsAssociatedListDo(){}




	public List<CollaboratorsDo> getSearchResults() {
		return searchResults;
	}




	public void setSearchResults(List<CollaboratorsDo> searchResults) {
		this.searchResults = searchResults;
	}




	public Integer getTotalHitCount() {
		return totalHitCount;
	}


	public void setTotalHitCount(Integer totalHitCount) {
		this.totalHitCount = totalHitCount;
	}




	/** 
	 * This method is to get the searchResult
	 */
	public List<CollaboratorsDo> getSearchResult() {
		return searchResult;
	}




	/** 
	 * This method is to set the searchResult
	 */
	public void setSearchResult(List<CollaboratorsDo> searchResult) {
		this.searchResult = searchResult;
	}
	
	
}
