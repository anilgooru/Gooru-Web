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
package org.ednovo.gooru.client.mvp.search.standards;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.service.ResourceServiceAsync;
import org.ednovo.gooru.application.client.service.ShelfServiceAsync;
import org.ednovo.gooru.application.client.service.TaxonomyServiceAsync;
import org.ednovo.gooru.application.shared.model.code.LibraryCodeDo;
import org.ednovo.gooru.application.shared.model.code.StandardsLevel1DO;
import org.ednovo.gooru.application.shared.model.code.StandardsLevel2DO;
import org.ednovo.gooru.application.shared.model.code.StandardsLevel3DO;
import org.ednovo.gooru.application.shared.model.code.StandardsLevel4DO;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Button;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.Proxy;

/**
 * @author Search Team
 *
 */
public class AddStandardsPresenter extends PresenterWidget<IsAddStandardsView> implements AddStandardsUiHandlers {

	@Inject
	private ShelfServiceAsync shelfService;

	@Inject
	private ResourceServiceAsync resourceService;

	@Inject
	private TaxonomyServiceAsync taxonomyService;

	private SimpleAsyncCallback<CollectionDo> saveCollectionAsyncCallback;

	private SimpleAsyncCallback<CollectionDo> collectionAsyncCallback;

	private SimpleAsyncCallback<List<LibraryCodeDo>> courseAsyncCallback;

	private String resourceOid;
	
	private String playerType=null;
	
	private boolean isCCSSAvailable =false;
	private boolean isNGSSAvailable =false;
	private boolean isTEKSAvailable =false;
	private boolean isCAAvailable =false;
	
	private String standardVal="";

	public String getResourceUid() {
		return resourceOid;
	}
	
	public String setPlayerType(String playerType){
		return playerType;
	}

	public void setResourceUid(String resourceUid) {
		this.resourceOid = resourceUid;
	}

	/**
	 * Class constructor
	 * @param view {@link View}
	 * @param proxy {@link Proxy}
	 */
	@Inject
	public AddStandardsPresenter( EventBus eventBus,IsAddStandardsView view) {
		super(eventBus,view);
		getView().setUiHandlers(this);
	}

	@Override
	public void onBind() {
		super.onBind();
	
	}
	
	public void callDefaultStandardsLoad()
	{
		getView().setEnableStandardButtons(isCCSSAvailable,isNGSSAvailable,isTEKSAvailable,isCAAvailable);
		if(isCCSSAvailable){
			standardVal = "CCSS";
			getView().setStandardsStyles(standardVal);
		}else if(isTEKSAvailable){
			standardVal = "TEKS";
			getView().setStandardsStyles(standardVal);
		}else if(isNGSSAvailable){
			standardVal = "NGSS";
			getView().setStandardsStyles(standardVal);
		}else if(isCAAvailable){
			standardVal = "CA";
			getView().setStandardsStyles(standardVal);
		}
		getView().loadData();
		getView().reset();
		AppClientFactory.getInjector().getSearchService().getFirstLevelStandards("0", standardVal, new SimpleAsyncCallback<ArrayList<StandardsLevel1DO>>() {
			@Override
			public void onSuccess(ArrayList<StandardsLevel1DO> result) {
				for(int i=0;i<result.size();i++) {
					getView().SetData(result.get(i),i);
				}
			}
		});
	}
	
	public void loadStateStandards(String stateCode)
	{
		getView().reset();
		AppClientFactory.getInjector().getSearchService().getFirstLevelStandards("0", stateCode, new SimpleAsyncCallback<ArrayList<StandardsLevel1DO>>() {
			@Override
			public void onSuccess(ArrayList<StandardsLevel1DO> result) {
				for(int i=0;i<result.size();i++) {
					getView().SetData(result.get(i),i);
				}
			}
		});
	}
	
	public void getFirstLevelObjects(String levelOrder, final String standardCode)
	{
		AppClientFactory.getInjector().getSearchService().getSecondLevelStandards(levelOrder, standardCode, new SimpleAsyncCallback<ArrayList<StandardsLevel2DO>>() {
			@Override
			public void onSuccess(ArrayList<StandardsLevel2DO> result) {
			
					getView().loadSecondLevelContianerObjects(result);
				
			}
		});
	}

	@Override
	protected void onReveal(){
		callDefaultStandardsLoad();
	}

	@Override
	public String setStandardsVal() {
		return getView().setStandardsVal();
	}
	
	@Override
	public Integer setStandardsIdVal() {
		return getView().setStandardsIdVal();
	}
	
	@Override
	public String setStandardDesc() {
		return getView().setStandardsDesc();
	}
	
	@Override
	public List<Map<String, String>> getStandardListArray() {
		return getView().getStandListArray();
	}
	
	@Override
	public Button getAddBtn() {
		return getView().getAddBtn();
	}
	
	@Override
	public void hidePopup() {
		getView().hidePopup();
	}

	@Override
	public void getSecondLevelObjects(String levelOrder,
			String standardCodeSelected) {
		AppClientFactory.getInjector().getSearchService().getThirdLevelStandards(levelOrder, standardCodeSelected, new SimpleAsyncCallback<ArrayList<StandardsLevel3DO>>() {
			@Override
			public void onSuccess(ArrayList<StandardsLevel3DO> result) {
			
					getView().loadThirdLevelContianerObjects(result);
				
			}
		});
		
	}

	@Override
	public void getThirdLevelObjects(String levelOrder,
			String standardCodeSelected) {
		AppClientFactory.getInjector().getSearchService().getFourthLevelStandards(levelOrder, standardCodeSelected, new SimpleAsyncCallback<ArrayList<StandardsLevel4DO>>() {
			@Override
			public void onSuccess(ArrayList<StandardsLevel4DO> result) {
			
					getView().loadFourthLevelContianerObjects(result);
				
			}
		});
		
	}

	public void enableStandardsData(boolean isCCSSAvailable,
			boolean isTEKSAvailable, boolean isNGSSAvailable,
			boolean isCAAvailable) {
		// TODO Auto-generated method stub
		this.isCCSSAvailable= isCCSSAvailable;
		this.isNGSSAvailable= isNGSSAvailable;
		this.isTEKSAvailable= isTEKSAvailable;
		this.isCAAvailable= isCAAvailable;
	}

}
