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
package org.ednovo.gooru.client.mvp.play.collection.preview.metadata;


import java.util.ArrayList;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.service.LibraryServiceAsync;
import org.ednovo.gooru.application.client.service.PlayerAppServiceAsync;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.ContentReportDo;
import org.ednovo.gooru.application.shared.model.library.ConceptDo;
import org.ednovo.gooru.application.shared.model.player.CommentsDo;
import org.ednovo.gooru.application.shared.model.player.CommentsListDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.play.collection.event.EditCommentChildViewEvent;
import org.ednovo.gooru.client.mvp.play.collection.event.SetPlayerLoginStatusEvent;
import org.ednovo.gooru.client.mvp.play.collection.event.UpdateCommentChildViewEvent;
import org.ednovo.gooru.client.mvp.play.collection.preview.PreviewPlayerPresenter;
import org.ednovo.gooru.client.mvp.play.collection.preview.end.PreviewEndPresenter;
import org.ednovo.gooru.client.mvp.play.collection.preview.home.PreviewHomePresenter;
import org.ednovo.gooru.client.uc.PlayerBundle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;

public class PreviewPlayerMetadataPresenter extends PresenterWidget<IsPreviewPlayerMetadataView> implements PreviewPlayerMetadataUiHandlers{
	
	@Inject
	private PlayerAppServiceAsync playerAppService;
	
	@Inject
	private LibraryServiceAsync libraryService;

    private SimpleAsyncCallback<CommentsListDo> commentsListDoAsync;
    
	private CollectionDo collectionDo=null;
	
	private PreviewHomePresenter previewHomePresenter;
	
	private PreviewEndPresenter previewEndPresenter;
	
	private PreviewPlayerPresenter previewPlayerPresenter=null;
	
	public static final  Object METADATA_PRESENTER_SLOT = new Object();
	
	private static final String CREATE = "CREATE";
	
	private static final String EDIT = "EDIT";
	
	private static final String FEATCHINGCOMMENT = "FEATCHINGCOMMENT";
	
	private static final String INITIAL_COMMENT_LIMIT = "10";
	
	private static final String INITIAL_OFFSET = "0";
	
	private static final String PAGE = "course-page";
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);
	
	@Inject
	public PreviewPlayerMetadataPresenter(EventBus eventBus, IsPreviewPlayerMetadataView view,PreviewHomePresenter previewHomePresenter,
			PreviewEndPresenter previewEndPresenter) {
		super(eventBus, view);
		getView().setUiHandlers(this);
		this.previewHomePresenter=previewHomePresenter;
		this.previewEndPresenter=previewEndPresenter;
		addRegisteredHandler(SetPlayerLoginStatusEvent.TYPE, this);
		addRegisteredHandler(UpdateCommentChildViewEvent.TYPE, this);
		addRegisteredHandler(EditCommentChildViewEvent.TYPE, this);
	}
	
	public void setCollectionMetadata(final CollectionDo collectionDo){
		this.collectionDo=collectionDo;
		getView().clearCommentContainer(true);
		getView().setCollectionMetadata(collectionDo);
		if(AppClientFactory.isAnonymous()) {
			getView().setPlayerLoginStatus(false);
		} else {
			getView().setPlayerLoginStatus(true);
		}
		getCollectionCommentsList(collectionDo.getGooruOid(), INITIAL_OFFSET, INITIAL_COMMENT_LIMIT);
		setRelatedConcepts(collectionDo);
	}
	
	private void getCollectionCommentsList(String gooruOid, final String offset, String limit) {
		this.playerAppService.getCollectionCommentsList(gooruOid, offset, limit, new SimpleAsyncCallback<CommentsListDo>() {
			@Override
			public void onSuccess(CommentsListDo commentDoList) {
				getView().setCommentsData(commentDoList,collectionDo, Integer.parseInt(offset) >0 ? false : true);
			}
		});
	}
	
	public void displayAuthorDetails(boolean isDisplayDetails){
		getView().displayAuthorDetails(isDisplayDetails);
	}
	public void setPreviewHomePresenter(){
		previewHomePresenter.setCollectionMetadata(collectionDo);
		setInSlot(METADATA_PRESENTER_SLOT, previewHomePresenter,false);
	}
	public void setPreviewEndPresenter(){
		previewEndPresenter.setCollectionMetadata(collectionDo);
		setInSlot(METADATA_PRESENTER_SLOT, previewEndPresenter,false);
	}
	
	public void setViewCount(String viewCount){
		getView().setViewCount(viewCount);
	}
	
	public void updateLikesCount(int likesCount){
		getView().setLikesCount(likesCount);
	}
	
	public void setUserProfileName(String gooruUid){
		getView().setUserProfileName(gooruUid);
	}
	
	public void resetMetadataFields(){
		getView().resetMetadataFields();
	}

	@Override
	public void setPlayerLoginStatusHandler(boolean isLoggedIn) {
		getView().setPlayerLoginStatus(isLoggedIn);
	}
	
	public PlayerAppServiceAsync getPlayerAppService() {
		return playerAppService;
	}

	public void setPlayerAppService(PlayerAppServiceAsync playerAppService) {
		this.playerAppService = playerAppService;
	}
	
	public SimpleAsyncCallback<CommentsListDo>  getCommentsListDoAsync() {
		return commentsListDoAsync;
	}

	public void setCommentsListDoAsync(SimpleAsyncCallback<CommentsListDo> commentsListDoAsync) {
		this.commentsListDoAsync = commentsListDoAsync;
	}

	@Override
	public void createCommentForCollection(String gooruOid, String comment) {
		this.playerAppService.createCommentForCollection(gooruOid, comment, new SimpleAsyncCallback<CommentsDo>() {
			@Override
			public void onSuccess(CommentsDo commentsDo) {
				getView().setCommentsWidget(commentsDo, CREATE);
				getView().displaySuccessMsg(false);
			}
		});
	}

	@Override
	public void updateCommentChildView(String commentUid, String action) {
		getView().updateCommentChildView(commentUid, action);
	}

	@Override
	public void deleteCommentFromCollection(final String gooruOid,String commentUid,final String offset, final String limit) {
		this.playerAppService.deleteCollectionCommentbyCommentUid(commentUid, new SimpleAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void noResult) {
				if(Integer.parseInt(offset)>=9)
				insertCommentAfterDeletion(gooruOid, offset, limit);
			}
		});
	}
	public void insertCommentAfterDeletion(final String gooruOid,final String offset, String limit){
		this.playerAppService.getCollectionCommentsList(gooruOid, offset, limit, new SimpleAsyncCallback<CommentsListDo>() {
			@Override
			public void onSuccess(CommentsListDo commentDoList) {
				getView().setCommentsWidget(commentDoList.getSearchResults().get(0), FEATCHINGCOMMENT);
			}
		});
	}
	@Override
	public void editCommentChildView(String commentUid, String commentText, String action) {
		this.playerAppService.updateCollectionCommentbyCommentUid(commentUid, commentText, new SimpleAsyncCallback<CommentsDo>() {
			@Override
			public void onSuccess(CommentsDo result) {
				getView().updateCommentChildView("", EDIT);
			}
		});
	}

	@Override
	public void getPaginationResults(String gooruOid, String offset, String limit) {
		getCollectionCommentsList(gooruOid, offset, limit);
	}

	
	public void getFlagedReport(String gooruOid) {
		playerAppService.getContentReport(collectionDo.getGooruOid(), AppClientFactory.getGooruUid(), new SimpleAsyncCallback<ArrayList<ContentReportDo>>() {
			@Override
			public void onSuccess(ArrayList<ContentReportDo> result) {
					String gooruFlagId="";
					if(result!=null && result.size()==0){
							getView().getFlagButton().setText(i18n.GL0556());
							getView().getFlagButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().previewCoverFlagImageOrange());
							getView().getFlagButton().setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().playerPreviewCoverFlagImage());
					}else{
						if(result!=null&&result.size()>0){
							for(int i=0;i<result.size();i++){
								if(result.get(i).getDeleteContentGooruOid()!=null){
									gooruFlagId = gooruFlagId+result.get(i).getDeleteContentGooruOid();
								}
								getView().getFlagButton().setText(i18n.GL0557());
								getView().getFlagButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().playerPreviewCoverFlagImage());
								getView().getFlagButton().setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().previewCoverFlagImageOrange());
							}
						}
					}
				}
			});
	}

	public void setRelatedConcepts(CollectionDo collectionDo) {
		final String subject = AppClientFactory.getPlaceManager().getRequestParameter("subject");
		final String lessonId = AppClientFactory.getPlaceManager().getRequestParameter("lessonId", "123");
		final String libraryType = AppClientFactory.getPlaceManager().getRequestParameter("library", PlaceTokens.DISCOVER);
		if(subject!=null) {
			this.libraryService.getLibraryCollections(subject, lessonId, libraryType, new SimpleAsyncCallback<ArrayList<ConceptDo>>() {
				@Override
				public void onSuccess(ArrayList<ConceptDo> conceptDoList) {
					getView().isConceptsContainerVisible(true);
					getView().setRelatedConceptsContent(conceptDoList, PAGE, subject, lessonId, libraryType);
				}
			});
		} else {
			getView().isConceptsContainerVisible(false);
		}
	}

	public PreviewPlayerPresenter getPreviewPlayerPresenter() {
		return previewPlayerPresenter;
	}

	public void setPreviewPlayerPresenter(PreviewPlayerPresenter previewPlayerPresenter) {
		this.previewPlayerPresenter = previewPlayerPresenter;
		if(previewEndPresenter!=null){
			previewEndPresenter.setPreviewPlayerPresenter(previewPlayerPresenter);
		}
		if(previewHomePresenter!=null){
			previewHomePresenter.setPreviewPlayerPresenter(previewPlayerPresenter);
		}
	}
}