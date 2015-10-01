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
package org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add;
/**
* @fileName : AddResourceUiHandlers.java 
*
* @description :This file is used to handle Ui Click events.
*
* @version :5.1
*
* @date: Apr 6 2013
   	
* @Author  Gooru Team
* 
* @Reviewer 
*
*/
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;
import org.ednovo.gooru.application.shared.model.code.CodeDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.CollectionQuestionItemDo;
import org.ednovo.gooru.application.shared.model.content.StandardFo;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
import org.ednovo.gooru.client.mvp.shelf.event.AddResourceImageHandler;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public interface AddResourceUiHandlers extends BaseUiHandlers, AddResourceImageHandler, EventHandler{

	void addResource(String idStr, String urlStr,String titleStr, String descriptionStr, String categoryStr, String thumbnailImgSrcStr,Integer endTime,String edcuationalUse,String momentsOfLearning,List<CodeDo> standards,List<StandardFo> centurySkills,String hostName, List<String> tagList,Map<String,List<Integer>> mediafeaturesAndHazards, String mediaType);
	
	void getResourceMetaInfo(String url);
	
	void resourceImageUpload();
	
//	void updateShare(String shareType);
	
	void getResourceExists(String url);
	
	void questionImageUpload();
	
	void questionImageUpload(String resourceGooruOid);
	
	void addQeustionResource(String mediaFileName, CollectionQuestionItemDo collectionQuestionItemDo);
	
	CollectionDo getParentCollectionDetails();

	void isShortenUrl(String userUrlStr);

	void userOwnResourceImageUpload();

	void userOwnResourceUpload();   
	
	void removeQuestionImage(String collectionItemId);
	
	void addUserOwnResource(String jsonString);

	void saveUserResource(String filePath);
	public void showDriveResoureView(HTMLPanel tabContainer);
	
	void v2UpdateQuestionResource(CollectionItemDo collectionItemDo,CollectionQuestionItemDo collectionQuestionItemDo, String thumbnailUrl);

	void getResourceImageInfo(String userUrlStr);
	
	void addSelectedQuestionType(String type,CollectionQuestionItemDo collectionQuestionItemDo);
	
	void setEditQuestionData(CollectionItemDo collectionItemDo);
	
	void setHSEditData();
	
	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_QUESTION_TYPE = new Type<RevealContentHandler<?>>();
	
	public void getDepthOfKnowledges();

	void showStandardsPopup(String standardVal, String standardsDesc,
			List<LiPanelWithClose> collectionLiPanelWithCloseArray);

	

}
