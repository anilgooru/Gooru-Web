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
package org.ednovo.gooru.client.mvp.gshelf.collectioncontent;

import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.service.ResourceServiceAsync;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.CollectionQuestionItemDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.application.shared.model.search.ResourceSearchResultDo;
import org.ednovo.gooru.application.shared.model.user.ProfileDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.gsearch.addResourcePopup.SearchAddResourceToCollectionPresenter;
import org.ednovo.gooru.client.mvp.gshelf.righttabs.MyCollectionsRightClusterPresenter;
import org.ednovo.gooru.client.mvp.home.library.events.StandardPreferenceSettingEvent;
import org.ednovo.gooru.client.mvp.image.upload.ImageUploadPresenter;
import org.ednovo.gooru.client.mvp.search.standards.AddStandardsPresenter;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.AddResourcePresenter;
import org.ednovo.gooru.client.mvp.shelf.event.InsertCollectionItemInAddResourceEvent;
import org.ednovo.gooru.client.mvp.shelf.event.InsertCollectionItemInAddResourceHandler;
import org.ednovo.gooru.client.mvp.shelf.event.RefreshType;
import org.ednovo.gooru.client.mvp.shelf.event.UpdateEditResourceImageEvent;
import org.ednovo.gooru.client.util.MixpanelUtil;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.Proxy;

/**
 * @author Search Team
 *
 */
public class CollectionContentPresenter extends PresenterWidget<IsCollectionContentView> implements CollectionContentUiHandlers {

	final String SUBJECT="subject";

	final String COURSE="course";
	private static final String USER_META_ACTIVE_FLAG = "0";
	private boolean isCCSSAvailable =false;
	private boolean isNGSSAvailable =false;
	private boolean isTEKSAvailable =false;
	private boolean isCAAvailable =false;
	private boolean isQuestionResource=false;
	private boolean isUserOwnResource = false;
	
	private Integer courseList;

	AddResourcePresenter addResourcePresenter=null;
	ImageUploadPresenter imgUploadPresenter=null;
	AddStandardsPresenter addStandardsPresenter = null;
	
	MyCollectionsRightClusterPresenter myCollectionsRightClusterPresenter;
	
	private SimpleAsyncCallback<Void> removeImageAsyncCallback;

	private SimpleAsyncCallback<CollectionItemDo> updateResourceItemAsyncCallback;
	
	SearchAddResourceToCollectionPresenter searchAddResourceToCollectionPresenter = null;
	

	@Inject
	private ResourceServiceAsync resourceService;

	/**
	 * Class constructor
	 * @param view {@link View}
	 * @param proxy {@link Proxy}
	 */
	@Inject
	public CollectionContentPresenter( EventBus eventBus,IsCollectionContentView view, AddResourcePresenter addResourcePresenter, ImageUploadPresenter imgUploadPresenter,AddStandardsPresenter addStandardsPresenter,SearchAddResourceToCollectionPresenter searchAddResourceToCollectionPresenter) {
		super(eventBus,view);
		getView().setUiHandlers(this);
		this.addResourcePresenter = addResourcePresenter;
		this.imgUploadPresenter = imgUploadPresenter;
		this.addStandardsPresenter = addStandardsPresenter;
		this.searchAddResourceToCollectionPresenter = searchAddResourceToCollectionPresenter;
		addRegisteredHandler(UpdateEditResourceImageEvent.TYPE, this);
		getView().setCollectionContentPresenter(this);
		addRegisteredHandler(InsertCollectionItemInAddResourceEvent.TYPE, new InsertCollectionItemInAddResourceHandler() {
			@Override
			public void insertCollectionItemInAddResource(CollectionItemDo collectionItem, RefreshType refreshType) {
				getView().setDisplayResourceItem(collectionItem, refreshType, -1);
				updateWidgetCount(collectionItem,false);
			}
		});
	}

	@Override
	public void onBind() {
		super.onBind();
	}

	@Override
	protected void onReveal(){
		super.onReveal();
		getView().onLoad();
	}

	@Override
	public void setData(final FolderDo folderDo) {
		if(folderDo!=null){
			AppClientFactory.getInjector().getResourceService().getCollection(folderDo.getGooruOid(),false, new SimpleAsyncCallback<CollectionDo>() {
				@Override
				public void onSuccess(CollectionDo result) {
					getView().setData(result,folderDo, RefreshType.INSERT);
				}
			});
		}
	}
	@Override
	public void reorderWidgetPositions(String idToMove,int itemSeqToAPI,String moveGooruOid) {
		String view=AppClientFactory.getPlaceManager().getRequestParameter("view", null);
		AppClientFactory.getInjector().getfolderService().reorderFoldersOrCollections("","","",moveGooruOid,itemSeqToAPI,idToMove,view,new SimpleAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				getView().resetWidgetPositions();
			}
		});
	}
	@Override
	public void updateNarrationItem(final CollectionItemDo collectionItem, String narration){
		AppClientFactory.getInjector().getResourceService().updateNarrationMetadata(collectionItem.getCollectionItemId(), narration, null, new SimpleAsyncCallback<CollectionItemDo>() {
			@Override
			public void onSuccess(CollectionItemDo result) {
				collectionItem.setNarration(result.getNarration());
				/*if("video/youtube".equalsIgnoreCase(collectionItem.getResource().getResourceType().getName())){*/
					collectionItem.setStart(result.getStart());
					collectionItem.setStop(result.getStop());
					getView().setCollectionDetails(result);
					/*}*/
			}
		});
	}
	@Override
	public void updateNarrationItemMetaData(String collectionId,final CollectionItemDo collectionItem, String narration,String start, String stop){
		AppClientFactory.getInjector().getResourceService().updateNarrationItemMetadata(collectionId,collectionItem.getCollectionItemId(), narration, null,start,stop, new SimpleAsyncCallback<CollectionItemDo>() {
			@Override
			public void onSuccess(CollectionItemDo result) {
				collectionItem.setNarration(result.getNarration());
				collectionItem.setStart(result.getStart());
				collectionItem.setStop(result.getStop());
			}
		});
	}
	@Override
	public void updateVideoTimeUpdate(final CollectionItemDo collectionItemDo){
		
		AppClientFactory.getInjector().getResourceService().updateTimeMetadata(collectionItemDo.getCollectionItemId(),collectionItemDo.getStart(), collectionItemDo.getStop(), new AsyncCallback<CollectionItemDo>() {
			@Override
			public void onSuccess(CollectionItemDo result) {
				// TODO Auto-generated method stub
				collectionItemDo.setStart(result.getStart());
				collectionItemDo.setStop(result.getStop());
				getView().setCollectionDetails(collectionItemDo);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
			}
		});
		
	}
	
	@Override
	public void deleteCollectionItem(String collectionId,final String collectionItemId, final int itemSequence) {
		AppClientFactory.getInjector().getResourceService().deleteCollectionItem(collectionId,collectionItemId, new SimpleAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				getView().updateDeleteItem(collectionItemId, itemSequence);
			}
		});
	};
	@Override
	public void updateCollectionItem(final CollectionItemDo collectionItem, String narration, String start, String stop) {
		AppClientFactory.getInjector().getResourceService().updateCollectionItemMetadata(collectionItem.getCollectionItemId(), narration, null, start, stop,new SimpleAsyncCallback<CollectionItemDo>() {
			@Override
			public void onSuccess(CollectionItemDo result) {
				collectionItem.setNarration(result.getNarration());
				collectionItem.setStart(result.getStart());
				collectionItem.setStop(result.getStop());
			}
		});
	}

	@Override
    public void addResourcePopup(CollectionDo collectionDo, String clickType) {
    	addResourcePresenter.setCollectionDo(collectionDo);
    	addResourcePresenter.setCollectionDoAndType(collectionDo, clickType);
        addToPopupSlot(addResourcePresenter);
	}

	@Override
	public void updateQustionImage(String collectionItemId) {
		addToPopupSlot(imgUploadPresenter);
        imgUploadPresenter.setCollectionImage(false);
        imgUploadPresenter.setUpdateQuestionImage(true);
        imgUploadPresenter.setCollectionItemId(collectionItemId);
        imgUploadPresenter.setEditResourceImage(false);
        imgUploadPresenter.setAnswerImage(false);

	}
	@Override
	public void removeQuestionImage(String collectionQuestionId) {
		getResourceService().removeQuestionImage(collectionQuestionId, getRemoveImageAsyncCallback());
	}

	public ResourceServiceAsync getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceServiceAsync resourceService) {
		this.resourceService = resourceService;
	}

	public SimpleAsyncCallback<Void> getRemoveImageAsyncCallback() {
		if (removeImageAsyncCallback == null) {
			removeImageAsyncCallback = new SimpleAsyncCallback<Void>() {
				@Override
				public void onSuccess(Void result) {
//					getView().removeUpdateQuestionView();
				}
			};
		}
		return removeImageAsyncCallback;
	}

	@Override
	public void updateResourceInfo(CollectionItemDo collectionItemDo,List<String> tagList) {
		getResourceService().updateResourceInfo(collectionItemDo, tagList,getUpdateResourceItemAsyncCallback());
	}

	/**
	 * This method is to get the updateResourceItemAsyncCallback
	 */
	public SimpleAsyncCallback<CollectionItemDo> getUpdateResourceItemAsyncCallback() {
		if (updateResourceItemAsyncCallback == null) {
			updateResourceItemAsyncCallback = new SimpleAsyncCallback<CollectionItemDo>() {
				@Override
				public void onSuccess(CollectionItemDo result) {
					getView().hideUpdateResourcePopup();

					getView().updateCollectionItem(result);
				}
			};
		}
		return updateResourceItemAsyncCallback;
	}

	@Override
	public void imageEditResourceUpload() {
		 addToPopupSlot(imgUploadPresenter);
         imgUploadPresenter.setEditResourceImage(true);
         imgUploadPresenter.setCollectionImage(false);
         imgUploadPresenter.setQuestionImage(false);
         imgUploadPresenter.setAnswerImage(false);
	}

	@Override
	public void getBrowseStandardsInfo(final boolean val,final boolean userResource) {
		AppClientFactory.getInjector().getUserService().getUserProfileV2Details(AppClientFactory.getLoggedInUser().getGooruUId(),
				USER_META_ACTIVE_FLAG,
				new SimpleAsyncCallback<ProfileDo>() {
					@Override
					public void onSuccess(final ProfileDo profileObj) {
					if(profileObj.getUser().getMeta() != null && profileObj.getUser().getMeta().getTaxonomyPreference() != null && profileObj.getUser().getMeta().getTaxonomyPreference().getCode() != null){
						AppClientFactory.fireEvent(new StandardPreferenceSettingEvent(profileObj.getUser().getMeta().getTaxonomyPreference().getCode()));
						checkStandarsList(profileObj.getUser().getMeta().getTaxonomyPreference().getCode());
					}
					}
					public void checkStandarsList(List<String> standarsPreferencesList) {

					if(standarsPreferencesList!=null){
							if(standarsPreferencesList.contains("CCSS")){
								isCCSSAvailable = true;
							}else{
								isCCSSAvailable = false;
							}
							if(standarsPreferencesList.contains("NGSS")){
								isNGSSAvailable = true;
							}else{
								isNGSSAvailable = false;
							}
							if(standarsPreferencesList.contains("TEKS")){
								isTEKSAvailable = true;
							}else{
								isTEKSAvailable = false;
							}
							if(standarsPreferencesList.contains("CA")){
								isCAAvailable = true;
							}else{
								isCAAvailable = false;
							}
								if(isCCSSAvailable || isNGSSAvailable || isTEKSAvailable || isCAAvailable){
									isQuestionResource = val;
									isUserOwnResource = userResource;
									addStandardsPresenter.enableStandardsData(isCCSSAvailable,isTEKSAvailable,isNGSSAvailable,isCAAvailable);
									addToPopupSlot(addStandardsPresenter);
									getView().OnBrowseStandardsClickEvent(addStandardsPresenter.getAddBtn());
								}
					}

					}

				});
	}

	@Override
	public void addUpdatedBrowseStandards() {
		List<Map<String,String>> selectedStandList=addStandardsPresenter.getStandardListArray();
		if(selectedStandList.size()!=0){
			for(int i=0;i<selectedStandList.size();i++){
				getView().setUpdatedStandardsCode(selectedStandList.get(i).get("selectedCodeVal"), Integer.parseInt(selectedStandList.get(i).get("selectedCodeId")),selectedStandList.get(i).get("selectedCodeDesc"),this.isQuestionResource, this.isUserOwnResource);
			}
		}
	}

	@Override
	public void closeBrowseStandardsPopup() {
		addStandardsPresenter.hidePopup();
	}

	@Override
	public void imageEditUserOwnResourceUpload() {
		 addToPopupSlot(imgUploadPresenter);
		 imgUploadPresenter.setEditUserOwnResourceImage(true);
         imgUploadPresenter.setEditResourceImage(false);
         imgUploadPresenter.setCollectionImage(false);
         imgUploadPresenter.setQuestionImage(false);
         imgUploadPresenter.setAnswerImage(false);
         imgUploadPresenter.getView().isFromEditQuestion(true);
	}

	@Override
	public void editUserOwnResource(String jsonString, String gooruOid, String collectionId) {
		MixpanelUtil.Resource_Edit_Info_Success();
		AppClientFactory.getInjector().getResourceService().updateUserOwnResource(jsonString,gooruOid,collectionId,new SimpleAsyncCallback<CollectionItemDo>(){
			@Override
			public void onSuccess(CollectionItemDo result) {
				getView().hideUpdateOwnResourcePopup();
				getView().updateCollectionItem(result);
			}
		});
	}

	@Override
	public void updateQuestionResource(String questionItemId,CollectionQuestionItemDo collectionQuestionItemDo,String thumbnailUrl) {
		
	}
	@Override
	public void showEditQuestionResourcePopup(CollectionItemDo collectionItemDo) {
		 addResourcePresenter.setCollectionItemDo(collectionItemDo);
		 addResourcePresenter.setCollectionDoAndType(null, "QuestionEdit");
		 addToPopupSlot(addResourcePresenter);
	}

	public void setMyCollectionRightClusterPresenter(MyCollectionsRightClusterPresenter myCollectionsRightClusterPresenter) {
		this.myCollectionsRightClusterPresenter=myCollectionsRightClusterPresenter;
	}
	
	@Override
	public void updateWidgetCount(CollectionItemDo collectionItem,boolean isDelete){
		myCollectionsRightClusterPresenter.getShelfMainPresenter().updateWidgetsCount(collectionItem,isDelete);
	}

	@Override

	public void showResourcePopup(CollectionItemDo collectionItem) {
		myCollectionsRightClusterPresenter.getShelfMainPresenter().SetDefaultTypeAndVersion();
		ResourceSearchResultDo resourceSearchResultDo = new ResourceSearchResultDo();
		searchAddResourceToCollectionPresenter.DisableMyCollectionsPanelData(false);
		searchAddResourceToCollectionPresenter.getLoadingImage();
		resourceSearchResultDo.setGooruOid(collectionItem.getResource().getGooruOid());
		resourceSearchResultDo.setQuestionType(collectionItem.getResource().getTypeName());
		resourceSearchResultDo.setResourceFormat(collectionItem.getNewResourceFormat());
		searchAddResourceToCollectionPresenter.getUserShelfData(resourceSearchResultDo,"coursebuilder",null);
		searchAddResourceToCollectionPresenter.setCollectionsData(true);
		addToPopupSlot(searchAddResourceToCollectionPresenter);
		Window.enableScrolling(false);
	}

	public void disableCollabaratorOptions(boolean isHide) {
		myCollectionsRightClusterPresenter.disableCollabaratorOptions(isHide);
	}

	@Override
	public void showLastEditCollaborater(String lastEditedBy,boolean hasLastModifiedUser) {
		myCollectionsRightClusterPresenter.getShelfMainPresenter().showLastEditCollaborater(lastEditedBy,hasLastModifiedUser);
	}

	@Override
	public void setUpdateResourceImageUrl(String fileName,	String fileNameWithOutRespUrl, boolean isEditUserOwnResourceImage) {
		 getView().updateResouceItemImage(fileName, fileNameWithOutRespUrl,isEditUserOwnResourceImage);
	}
}

