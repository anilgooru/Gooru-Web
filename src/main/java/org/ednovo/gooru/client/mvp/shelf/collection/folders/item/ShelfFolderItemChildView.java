package org.ednovo.gooru.client.mvp.shelf.collection.folders.item;

import java.util.HashMap;
import java.util.List;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.child.ChildView;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.application.shared.model.folder.FolderItemDo;
import org.ednovo.gooru.client.effects.FadeInAndOut;
import org.ednovo.gooru.client.mvp.dnd.IsDraggableMirage;
import org.ednovo.gooru.client.mvp.shelf.FolderStyleBundle;
import org.ednovo.gooru.client.mvp.shelf.collection.folders.events.ChangeShelfPanelActiveStyleEvent;
import org.ednovo.gooru.client.mvp.shelf.collection.folders.events.SetFolderCollectionStyleEvent;
import org.ednovo.gooru.client.mvp.shelf.collection.folders.events.SetFolderMetaDataEvent;
import org.ednovo.gooru.client.mvp.shelf.collection.folders.events.SetFolderParentNameEvent;
import org.ednovo.gooru.client.mvp.shelf.collection.folders.events.UpdateAssmntUrlOnShelfListEvent;
import org.ednovo.gooru.client.uc.UcCBundle;
import org.ednovo.gooru.client.uc.tooltip.GlobalToolTip;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ShelfFolderItemChildView extends ChildView<ShelfFolderItemChildPresenter> implements
		IsShelfFolderItemView {

	@UiField FolderStyleBundle folderStyle;

	@UiField FlowPanel contentBlock,contents,reorderPanel;
	@UiField HTMLEventPanel folderImage;
	@UiField Image collectionImage;
	@UiField Label itemTitle,itemNumber;
	
	@UiField TextBox reorderTxtBox;
	@UiField Button moveUpBtn,moveDownBtn,moveTopBtn,moveBottomBtn;
	

	private static final String DEFULT_IMAGE_PREFIX = "images/default-collection-image-160x120.png";
	
	private static final String DEFULT_ASSESSMENT = "images/default-assessment-image -160x120.png";
	
	private static final String SMALL = "Small";
	
	private static final String SMALL_NEW = "SmallNew";
	
	private static final String FOLDER = "folder";
	
	private static final String COLLECTION = "collection";

	private static final String SCOLLECTION = "scollection";
	
	private static final String ASSESSMENT_URL = "assessment/url";
	
	private static final String ASSESSMENT = "assessment";

	private static final String RESOURCE = "resource";
	
	private static final String O1_LEVEL = "o1";
	
	private static final String O2_LEVEL = "o2";
	
	private static final String O3_LEVEL = "o3";
	
	private static final String RIGHT="right";
	
	private static final String TOP="top";
	
	private static final String BOTTOM="bottom";
	
	private static final String ID = "id";
	
	private String itemGooruOId,collectionItemId;
	
	private FolderDo folderDo;
	
	private int itemNo;
	
	private PopupPanel toolTipPopupPanel=new PopupPanel(true);
	
	public PopupPanel toolTipPosPopupPanel=new PopupPanel();
	
	final String o1 = AppClientFactory.getPlaceManager().getRequestParameter(O1_LEVEL);
	
	final String o2 = AppClientFactory.getPlaceManager().getRequestParameter(O2_LEVEL);
	
	final String o3 = AppClientFactory.getPlaceManager().getRequestParameter(O3_LEVEL);

	private static ShelfFolderItemChildViewUiBinder uiBinder = GWT.create(ShelfFolderItemChildViewUiBinder.class);
	
	MessageProperties i18n = GWT.create(MessageProperties.class);

	EditAssessmentPopup editAssessmentPopup=null;
	
	Button folderItemDelete=new Button();
	interface ShelfFolderItemChildViewUiBinder extends UiBinder<Widget, ShelfFolderItemChildView> {}
	
	public ShelfFolderItemChildView(FolderDo folderDo, int folderNumber) { 
		initWidget(uiBinder.createAndBindUi(this));
		this.folderDo = folderDo;
		setFolderDo(folderDo);
		setFolderData(folderDo);
		reorderTxtBox.addMouseOverHandler(new OnMouseOver());
		reorderTxtBox.addMouseOutHandler(new OnMouseOut());
		moveTopBtn.addMouseOverHandler(new OnMouseOver());
		moveTopBtn.addMouseOutHandler(new OnMouseOut());
		moveBottomBtn.addMouseOverHandler(new OnMouseOver());
		moveBottomBtn.addMouseOutHandler(new OnMouseOut());
		
		contentBlock.getElement().setId("fpnlContentBlock");
		folderImage.getElement().setId("epnlFolderImage");
		collectionImage.getElement().setId("imgCollectionImage");
		itemTitle.getElement().setId("lblItemTitle");
		contents.getElement().setId("fpnlContents");
		moveUpBtn.getElement().setId("moveUpBtn");
		moveDownBtn.getElement().setId("moveDownBtn");
		moveTopBtn.getElement().setId("moveTopBtn");
		moveBottomBtn.getElement().setId("moveBottomBtn");
		reorderTxtBox.getElement().setId("reorderTxtBox");
		moveDownBtn.getElement().getStyle().setVisibility(Visibility.VISIBLE);
		moveUpBtn.getElement().getStyle().setVisibility(Visibility.VISIBLE);
		moveBottomBtn.getElement().getStyle().setVisibility(Visibility.VISIBLE);
		moveTopBtn.getElement().getStyle().setVisibility(Visibility.VISIBLE);
		
		moveUpBtn.getElement().setAttribute("style", "padding: 5px 8px;");
		moveDownBtn.getElement().setAttribute("style", "padding: 5px 8px;");
		moveTopBtn.getElement().setAttribute("style", "padding: 5px 8px;");
		moveBottomBtn.getElement().setAttribute("style", "padding: 5px 8px;");
		
	}
	
	public void setFolderData(final FolderDo folderDo) {
		itemTitle.addStyleName(folderStyle.folderTitleElipses());
		final String folderType = folderDo.getType();
		if(folderType.equals(FOLDER)) {
			folderImage.setVisible(true);
			collectionImage.setVisible(false);
			contentBlock.addStyleName(folderStyle.folder());
		} else if (folderType.equals(SCOLLECTION) || folderType.equals(COLLECTION)){
			folderImage.setVisible(false);
			collectionImage.setUrl(DEFULT_IMAGE_PREFIX);
			collectionImage.setVisible(true);
			if(folderDo.getThumbnails()!=null && !StringUtil.isEmpty(folderDo.getThumbnails().getUrl())) 
			{
				setDefaultImage(folderDo.getCollectionType(), folderDo.getThumbnails().getUrl());
			} 
			else {
				setDefaultImage(folderDo.getCollectionType(), null);
			}
			collectionImage.addErrorHandler(new ErrorHandler() {
				@Override
				public void onError(ErrorEvent event) {
					setDefaultImage(folderDo.getCollectionType(),null);
				}
			});
			collectionImage.setHeight("90px");
			collectionImage.setWidth("120px");
						
		}
		List<FolderItemDo> folderItemDo = folderDo.getCollectionItems();
		if(!ASSESSMENT_URL.equals(folderDo.getCollectionType()) && folderItemDo!=null&&folderItemDo.size()>0) {
			for(int i=0;i<folderItemDo.size();i++) {
				final FolderItemDo folderItem = folderItemDo.get(i);
				Label folderItemLbl = new Label(folderItem.getTitle());

				if(folderItem.getType().equals(FOLDER)) {
					folderItemLbl.addStyleName(folderStyle.folder());
					folderItemLbl.addClickHandler(new OpenChildFolderInContent(FOLDER, folderDo.getGooruOid(), folderItem.getGooruOid(), folderItem.getTitle()));
					contents.add(folderItemLbl);
				} else if(folderItem.getType().equals(SCOLLECTION)){
					if(folderItem.getCollectionType().contains(ASSESSMENT)){
						folderItemLbl.addStyleName(folderStyle.assessment());
					}else{
						folderItemLbl.addStyleName(folderStyle.collection());
					}
					if(folderItem.getCollectionType().equals(ASSESSMENT_URL)){
						folderItemLbl.addClickHandler(new ClickHandler() {
							@Override
							public void onClick(ClickEvent event) {
								Window.open(folderItem.getUrl(), "", "");
							}
						});
					}else{
						folderItemLbl.addClickHandler(new OpenChildFolderInContent(SCOLLECTION, folderDo.getGooruOid(), folderItem.getGooruOid(), folderItem.getTitle()));
					}
					
					contents.add(folderItemLbl);
				} else {
					HTMLEventPanel resourcePanel = new HTMLEventPanel("");
					resourcePanel.addStyleName(folderStyle.resource());
					if(folderItem.getResourceFormat()!=null) {
						HTMLPanel resourceCategoryPanel = new HTMLPanel("");
						resourceCategoryPanel.addStyleName(UcCBundle.INSTANCE.css().resourceNameNew());
						resourceCategoryPanel.addStyleName(folderItem.getResourceFormat().getDisplayName().toLowerCase() + SMALL_NEW);
						resourceCategoryPanel.getElement().getStyle().setPosition(Position.STATIC);
						resourceCategoryPanel.getElement().getStyle().setFloat(Float.LEFT);
						resourcePanel.add(resourceCategoryPanel);
					}
					HTML resourceTitle = new HTML(StringUtil.getRefinedQuestionText(folderItem.getTitle()));
					resourceTitle.setStyleName("shelfFolderItemTitle");
					resourcePanel.add(resourceTitle);
					contents.add(resourcePanel);
					resourcePanel.addClickHandler(new OpenChildFolderInContent(RESOURCE, folderDo.getGooruOid(), folderItem.getGooruOid(), null));
				}
			}
			if(folderDo.getItemCount()-folderItemDo.size()>0) {
				Anchor seeMoreLbl = new Anchor(i18n.GL_SPL_PLUS()+" "+ (folderDo.getItemCount()-folderItemDo.size())+" "+i18n.GL1152());
				seeMoreLbl.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						if(folderType.equals(FOLDER)) {
							AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.SHELF, urlParams(FOLDER, folderDo.getGooruOid()));
						} else {
							AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.SHELF, urlParams(COLLECTION, folderDo.getGooruOid()));
						}
						AppClientFactory.fireEvent(new ChangeShelfPanelActiveStyleEvent());
					}
				});
				contents.add(seeMoreLbl);
			}
		} else {
			if(ASSESSMENT_URL.equals(folderDo.getCollectionType())){
				Button folderItemLbl = new Button(i18n.GL1428());
				folderItemLbl.getElement().getStyle().setMarginRight(10, Unit.PCT);
				folderItemLbl.getElement().getStyle().setMarginTop(72, Unit.PX);
				Button folderItemEdit = new Button(i18n.GL2285());
				folderItemEdit.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						getEditAssessmentPoupOrPlayCollection();
					}
				});
				folderItemEdit.getElement().getStyle().setMarginRight(10, Unit.PCT);
				folderItemEdit.getElement().getStyle().setMarginTop(72, Unit.PX);
				folderItemDelete.setText(i18n.GL0558());
				folderItemEdit.getElement().getStyle().setMarginRight(10, Unit.PCT);
				folderItemDelete.getElement().getStyle().setMarginTop(72, Unit.PX);
				
				folderItemLbl.addStyleName("secondary");
				folderItemEdit.addStyleName("secondary");
				folderItemDelete.addStyleName("secondary");
				
				folderItemLbl.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						Window.open(folderDo.getUrl(), "", "");
					}
				});
				contents.addStyleName(folderStyle.assessmentContainer());
				contents.add(folderItemLbl);
				contents.add(folderItemEdit);
				contents.add(folderItemDelete);
			}else{
				contents.addStyleName(folderStyle.empty());
			}
		}
		itemTitle.setText(folderDo.getTitle());	
		itemTitle.getElement().setAttribute("alt",folderDo.getTitle());
		itemTitle.getElement().setAttribute("title",folderDo.getTitle());
	}
	
	
	/**
	 * To set the default image of collection/assessment
	 * @param collectionType {@link String}
	 * @param url 
	 */
	private void setDefaultImage(String collectionType, String url) {
		if(collectionType.equals(ASSESSMENT) || collectionType.equals(ASSESSMENT_URL)){
			contentBlock.addStyleName(folderStyle.assessment());
			collectionImage.setUrl(url==null?DEFULT_ASSESSMENT:url);
		}else{
			contentBlock.addStyleName(folderStyle.collection());
			collectionImage.setUrl(url==null?DEFULT_IMAGE_PREFIX:url);
		}
	}

	@UiHandler("folderImage")
	public void clickOnFolderImage(ClickEvent event) {
		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.SHELF, urlParams(FOLDER, folderDo.getGooruOid()));
		AppClientFactory.fireEvent(new ChangeShelfPanelActiveStyleEvent());
	}
	public void getEditAssessmentPoupOrPlayCollection(){
		if(ASSESSMENT_URL.equals(folderDo.getCollectionType())){
			Window.enableScrolling(false);
			editAssessmentPopup=new EditAssessmentPopup(folderDo) {
				@Override
				public
				void clickEventOnSaveAssessmentHandler(FolderDo result) {
					if(result!=null){
						showAssessmentUrlInfo(result);
						triggerUpdateAssessmentUrl(result);
					}
					editAssessmentPopup.hide();
					Window.enableScrolling(true);
				}
				@Override
				public void clickEventOnCancelAssessmentHandler(ClickEvent event) {
					editAssessmentPopup.hide();
					Window.enableScrolling(true);
				}
			};
			editAssessmentPopup.setGlassEnabled(true);
			editAssessmentPopup.show();
			editAssessmentPopup.center();
		}else{
			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.SHELF, urlParams(COLLECTION, folderDo.getGooruOid())); 
			AppClientFactory.fireEvent(new ChangeShelfPanelActiveStyleEvent()); 
		}
	}
	
	/**
	 * Displays the updated info.
	 * @param result {@link FolderDo}
	 */
	public void showAssessmentUrlInfo(FolderDo result) {
		folderDo.setTitle(result.getTitle());
		folderDo.setUrl(result.getUrl());
		folderDo.setGoals(result.getGoals());
		folderDo.setSharing(result.getSharing());
		folderDo.getSettings().setIsLoginRequired(result.getSettings().getIsLoginRequired());
		itemTitle.setText(folderDo.getTitle());
	}

	protected void triggerUpdateAssessmentUrl(FolderDo result) {
		HashMap<String,String> params = new HashMap<String,String>();
		if(AppClientFactory.getPlaceManager().getRequestParameter("o3")!=null){
			params.put("o1",AppClientFactory.getPlaceManager().getRequestParameter("o1"));  
			params.put("o2",AppClientFactory.getPlaceManager().getRequestParameter("o2"));
			params.put("o3",AppClientFactory.getPlaceManager().getRequestParameter("o3"));
			AppClientFactory.fireEvent(new UpdateAssmntUrlOnShelfListEvent(result, params));
		}else if(AppClientFactory.getPlaceManager().getRequestParameter("o2")!=null){
			params.put("o1",AppClientFactory.getPlaceManager().getRequestParameter("o1"));  
			params.put("o2",AppClientFactory.getPlaceManager().getRequestParameter("o2"));
			AppClientFactory.fireEvent(new UpdateAssmntUrlOnShelfListEvent(result, params));
		}else if(AppClientFactory.getPlaceManager().getRequestParameter("o1")!=null){
			params.put("o1",AppClientFactory.getPlaceManager().getRequestParameter("o1"));
			AppClientFactory.fireEvent(new UpdateAssmntUrlOnShelfListEvent(result, params));
		}else{
			AppClientFactory.fireEvent(new UpdateAssmntUrlOnShelfListEvent(result, params));
		}
	}

	@UiHandler("collectionImage")
	public void clickOnCollectionImage(ClickEvent event) {
		getEditAssessmentPoupOrPlayCollection();
	}
	
	@UiHandler("itemTitle")
	public void clickOnTitle(ClickEvent event) {
		if(!ASSESSMENT_URL.equals(folderDo.getCollectionType())){
			 	if(folderDo.getType().equals("folder")){
					AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.SHELF, urlParams(FOLDER, folderDo.getGooruOid()));
				}else{
					AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.SHELF, urlParams(COLLECTION, folderDo.getGooruOid())); 
				}
				AppClientFactory.fireEvent(new ChangeShelfPanelActiveStyleEvent()); 
		}
	}
	
	@Override
	public Widget getDragHandle() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public IsDraggableMirage initDraggableMirage() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void onDragBlur() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public String getDragId() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public DRAG_TYPE getDragType() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public int getDragTopCorrection() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public int getDragLeftCorrection() {
		throw new RuntimeException("Not implemented");
	}
	
	private HashMap<String,String> urlParams(String assetType, String folderId) {
		HashMap<String,String> params = new HashMap<String,String>();
		
		if(assetType.equals(FOLDER)) {
			if(o3!=null) {
				
			} else if(o2!=null) {
				params.put(O1_LEVEL, o1);
				params.put(O2_LEVEL, o2);
				params.put(O3_LEVEL, folderId);
			} else if(o1!=null) {
				params.put(O1_LEVEL, o1);
				params.put(O2_LEVEL, folderId);
			} else {
				params.put(O1_LEVEL, folderId);
			}
			AppClientFactory.fireEvent(new SetFolderMetaDataEvent(StringUtil.getFolderMetaData(folderDo)));
		} else {
			if(o3!=null) {
				params.put(O1_LEVEL, o1);
				params.put(O2_LEVEL, o2);
				params.put(O3_LEVEL, o3);
				params.put(ID, folderId);
			} else if(o2!=null) {
				params.put(O1_LEVEL, o1);
				params.put(O2_LEVEL, o2);
				params.put(ID, folderId);
			} else if(o1!=null){
				params.put(O1_LEVEL, o1);
				params.put(ID, folderId);
			} else {
				params.put(ID, folderId);
			}
		}
		AppClientFactory.fireEvent(new SetFolderParentNameEvent(folderDo.getTitle()));
		
		return params;
	}
	
	public class OpenChildFolderInContent implements ClickHandler{
		String parentId = null, folderId = null, folderTitle = null, folderType = null;
		public OpenChildFolderInContent(String folderType, String parentId, String folderId, String folderTitle) {
			this.folderType = folderType;
			this.parentId = parentId;
			this.folderId = folderId;
			this.folderTitle = folderTitle;
		}
		
		@Override
		public void onClick(ClickEvent event) {
			HashMap<String,String> params = new HashMap<String,String>();
			if(folderType.equals(FOLDER)) {
				if(o1!=null) {
					params.put(O1_LEVEL, o1);
					params.put(O2_LEVEL, parentId);
					params.put(O3_LEVEL, folderId);
				} else if (o1==null&&o2==null&&o3==null){
					params.put(O1_LEVEL, parentId);
					params.put(O2_LEVEL, folderId);
				}
				AppClientFactory.fireEvent(new SetFolderCollectionStyleEvent(params,FOLDER)); 
				AppClientFactory.fireEvent(new SetFolderParentNameEvent(folderTitle));
				AppClientFactory.fireEvent(new SetFolderMetaDataEvent(StringUtil.getFolderMetaData(folderDo)));
				AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.SHELF, params);
			} else if (folderType.equals(SCOLLECTION)){
				if(o1!=null) {
					params.put(O1_LEVEL, o1);
					params.put(O2_LEVEL, parentId);
					params.put(ID, folderId);
				} else if (o2!=null) {
					params.put(O1_LEVEL, o1);
					params.put(O2_LEVEL, o2);
					params.put(o3, parentId);
					params.put(ID, folderId);
				} else if (o1==null&&o2==null&&o3==null){
					params.put(O1_LEVEL, parentId);
					params.put(ID, folderId);
				}
				AppClientFactory.fireEvent(new SetFolderCollectionStyleEvent(params,SCOLLECTION));  
				AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.SHELF, params);
			}  else if (folderType.equals(RESOURCE)) {
				AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.SHELF, urlParams(COLLECTION, folderDo.getGooruOid()));
				AppClientFactory.fireEvent(new SetFolderCollectionStyleEvent(params,COLLECTION));  
				AppClientFactory.fireEvent(new ChangeShelfPanelActiveStyleEvent());
			}
		}
	}
	
	
	/**
	 * Shows alert tool tip message while doing reorder.
	 * @param validationMsg {@link String}
	 */
	public void showReorderValidationToolTip(String validationMsg){
		toolTipPopupPanel.clear();
		toolTipPopupPanel.setWidget(new GlobalToolTip(validationMsg));
		toolTipPopupPanel.setStyleName("");
		toolTipPopupPanel.setPopupPosition(reorderTxtBox.getElement().getAbsoluteLeft()+110, reorderTxtBox.getElement().getAbsoluteTop()-40);
		toolTipPopupPanel.getElement().getStyle().setZIndex(9999);
		toolTipPopupPanel.show();
		new FadeInAndOut(toolTipPopupPanel.getElement(), 10200);
	}
	
	
	/**
	 * Decides Up and Down button visibility
	 * @param totalCount {@link Integer}
	 */
	public void setUpDownArrowVisibility(int totalCount) { 
		if(getItemNo() == totalCount){
			downButtonIsVisible(false);
		}
		if(getItemNo() == 1){
			upButtonIsVisible(false);
		}
	}
	
	
	
	/**
	 * Sets the re-order Up button visibility
	 * @param isvisible {@link Boolean}
	 */
	public void upButtonIsVisible(boolean isvisible) {
		if(isvisible){
			moveUpBtn.getElement().getStyle().setVisibility(Visibility.VISIBLE);
			moveTopBtn.getElement().getStyle().setVisibility(Visibility.VISIBLE);
		}else{
			moveUpBtn.getElement().getStyle().setVisibility(Visibility.HIDDEN);
			moveTopBtn.getElement().getStyle().setVisibility(Visibility.HIDDEN);
		}
	}


	/**
	 * Sets the re-order Down button visibility
	 * @param isvisible {@link Boolean}
	 */
	public void downButtonIsVisible(boolean isvisible) {
		if(isvisible){
			moveDownBtn.getElement().getStyle().setVisibility(Visibility.VISIBLE);
			moveBottomBtn.getElement().getStyle().setVisibility(Visibility.VISIBLE);
		}else{
			moveDownBtn.getElement().getStyle().setVisibility(Visibility.HIDDEN);
			moveBottomBtn.getElement().getStyle().setVisibility(Visibility.HIDDEN);
		}
	}
	
	/**
	 * Inner class to implement mouse over functionality for a text field, top most button and bottom most button.
	 * On mouse over shows alert tool tip.
	 *
	 */
	
	private class OnMouseOver implements MouseOverHandler {

		@Override
		public void onMouseOver(MouseOverEvent event) {
			if (event.getSource() == reorderTxtBox){
				toolTipPosPopupPanel.clear();
				toolTipPosPopupPanel.setWidget(new GlobalToolTip(i18n.GL3002(),RIGHT));
				toolTipPosPopupPanel.setStyleName("");
				toolTipPosPopupPanel.setPopupPosition(reorderTxtBox.getElement().getAbsoluteLeft()-110, reorderTxtBox.getElement().getAbsoluteTop()-40);
				toolTipPosPopupPanel.getElement().getStyle().setZIndex(9999);
				toolTipPosPopupPanel.show();
			}else if(event.getSource() == moveTopBtn){
				toolTipPosPopupPanel.clear();
				toolTipPosPopupPanel.setWidget(new GlobalToolTip(i18n.GL3000(),TOP));
				toolTipPosPopupPanel.setStyleName("");
				toolTipPosPopupPanel.setPopupPosition(moveTopBtn.getElement().getAbsoluteLeft()-59, moveTopBtn.getElement().getAbsoluteTop()-34);
				toolTipPosPopupPanel.getElement().getStyle().setZIndex(9999);
				toolTipPosPopupPanel.show();
			}else if(event.getSource() == moveBottomBtn){
				toolTipPosPopupPanel.clear();
				toolTipPosPopupPanel.setWidget(new GlobalToolTip(i18n.GL3001(),BOTTOM));
				toolTipPosPopupPanel.setStyleName("");
				toolTipPosPopupPanel.setPopupPosition(moveBottomBtn.getElement().getAbsoluteLeft()-70, moveBottomBtn.getElement().getAbsoluteTop()-40);
				toolTipPosPopupPanel.getElement().getStyle().setZIndex(9999);
				toolTipPosPopupPanel.show();
			}
		}
	}
	
	/**
	 * Inner class to implement mouse out functionality for a text field, top most button and bottom most button.
	 * On mouse out hides alert tool tip.
	 *
	 */
	
	private class OnMouseOut implements MouseOutHandler {

		@Override
		public void onMouseOut(MouseOutEvent event) {
			toolTipPosPopupPanel.hide();
		}

	}

	/**
	 * @return the itemNo
	 */
	public int getItemNo() {
		return itemNo;
	}

	/**
	 * @param itemNo the itemNo to set
	 */
	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}

	
	/**
	 * @return the itemNumber
	 */
	public Label getItemNumber() {
		return itemNumber;
	}

	/**
	 * @param itemNumber the itemNumber to set
	 */
	public void setItemNumber(Label itemNumber) {
		this.itemNumber = itemNumber;
	}

	/**
	 * @return the reorderTxtBox
	 */
	public TextBox getReorderTxtBox() {
		return reorderTxtBox;
	}

	/**
	 * @param reorderTxtBox the reorderTxtBox to set
	 */
	public void setReorderTxtBox(TextBox reorderTxtBox) {
		this.reorderTxtBox = reorderTxtBox;
	}
	

	/**
	 * @return the moveUpBtn
	 */
	public Button getMoveUpBtn() {
		return moveUpBtn;
	}

	/**
	 * @param moveUpBtn the moveUpBtn to set
	 */
	public void setMoveUpBtn(Button moveUpBtn) {
		this.moveUpBtn = moveUpBtn;
	}

	/**
	 * @return the moveDownBtn
	 */
	public Button getMoveDownBtn() {
		return moveDownBtn;
	}

	/**
	 * @param moveDownBtn the moveDownBtn to set
	 */
	public void setMoveDownBtn(Button moveDownBtn) {
		this.moveDownBtn = moveDownBtn;
	}
	
	/**
	 * @return the itemGooruOId
	 */
	public String getItemGooruOId() {
		return itemGooruOId;
	}

	/**
	 * @param itemGooruOId the itemGooruOId to set
	 */
	public void setItemGooruOId(String itemGooruOId) {
		this.itemGooruOId = itemGooruOId;
	}

	/**
	 * @return the collectionItemId
	 */
	public String getCollectionItemId() {
		return collectionItemId;
	}

	/**
	 * @param collectionItemId the collectionItemId to set
	 */
	public void setCollectionItemId(String collectionItemId) {
		this.collectionItemId = collectionItemId;
	}

	/**
	 * @return the folderDo
	 */
	public FolderDo getFolderDo() {
		return folderDo;
	}

	/**
	 * @param folderDo the folderDo to set
	 */
	public void setFolderDo(FolderDo folderDo) {
		this.folderDo = folderDo;
	}

	/**
	 * @return the moveTopBtn
	 */
	public Button getMoveTopBtn() {
		return moveTopBtn;
	}

	/**
	 * @param moveTopBtn the moveTopBtn to set
	 */
	public void setMoveTopBtn(Button moveTopBtn) {
		this.moveTopBtn = moveTopBtn;
	}

	/**
	 * @return the moveBottomBtn
	 */
	public Button getMoveBottomBtn() {
		return moveBottomBtn;
	}

	/**
	 * @return the delete button
	 */
	public Button getDeleteBtn() {
		return folderItemDelete;
	}
	/**
	 * @param moveBottomBtn the moveBottomBtn to set
	 */
	public void setMoveBottomBtn(Button moveBottomBtn) {
		this.moveBottomBtn = moveBottomBtn;
	}

	/**
	 * @return the reorderPanel
	 */
	public FlowPanel getReorderPanel() {
		return reorderPanel;
	}

	/**
	 * @param reorderPanel the reorderPanel to set
	 */
	public void setReorderPanel(FlowPanel reorderPanel) {
		this.reorderPanel = reorderPanel;
	}
}