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
package org.ednovo.gooru.client.mvp.play.collection.end;

import org.ednovo.gooru.client.gin.BaseUiHandlers;
import org.ednovo.gooru.client.mvp.play.collection.event.SetPlayerLoginStatusHandler;
import org.ednovo.gooru.shared.model.analytics.PrintUserDataDO;

public interface CollectionEndUiHandlers extends BaseUiHandlers, SetPlayerLoginStatusHandler {
	public void createCommentForCollection(String gooruOid, String comment);
	
	public void deleteCommentFromCollection(String gooruOid,String commentUid, String offset, String limit,String commentText);
	
	public void getPaginationResults(String gooruOid, String offset, String limit);
	
	public void resetCollectionActivityEventId();
	
	public void triggerCollectionShareDataEvent(String collectionId,String itemType, String shareType, boolean confirmStatus) ;
	
	public void getAvgReaction();

	public void updateCommentsStatus(String string);
	
	public void getSessionsDataByUser(String collectionId, String classId, String userId);
	
	public void getCollectionMetaDataByUserAndSession(String collectionId, String classId, String userId, String sessionId,PrintUserDataDO printData);
	
	public void setCollectionSummaryData(String collectionId,String classpageId,String userId,String sessionId,PrintUserDataDO printData);
	
	public void setCollectionSummaryBasedOnClasspageIdSessionId();
	
	public void clearslot();

	public void getNextCollectionItem();
}
