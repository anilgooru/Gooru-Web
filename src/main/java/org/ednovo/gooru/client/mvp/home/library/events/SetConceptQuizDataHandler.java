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
package org.ednovo.gooru.client.mvp.home.library.events;

import java.util.ArrayList;

import org.ednovo.gooru.application.shared.model.library.ConceptDo;

import com.google.gwt.event.shared.EventHandler;
/**
 * 
 * @fileName : SetConceptQuizDataHandler.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: Dec 4, 2013
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */

public interface SetConceptQuizDataHandler extends EventHandler {

	/**
	 * @param lessonCode 
	 * @param lessonLabel 
	 * @param lessonId 
	 * @param topicId 
	 * @param conceptId 
	 * @function setConceptTitleStyleHandler 
	 * 
	 * @created_date : 12-Dec-2013
	 * 
	 * @description
	 * 
	 * @parm(s) : @param ArrayList<ConceptDo> conceptDoList
	 * @return : void
	 * @throws : <Mentioned if any exceptions>
	 */
	void setConceptQuizDataHandler(ArrayList<ConceptDo> conceptDoList, Integer topicId, String lessonId, String lessonLabel, String lessonCode, String conceptId,String libraryGooruOid);
}



