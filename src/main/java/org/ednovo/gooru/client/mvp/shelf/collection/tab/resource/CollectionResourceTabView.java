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
package org.ednovo.gooru.client.mvp.shelf.collection.tab.resource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.code.CodeDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.CollectionQuestionItemDo;
import org.ednovo.gooru.application.shared.model.user.MediaUploadDo;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.item.CollectionEditResourceCBundle;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.item.CollectionEditResourceCBundle.CollectionEditResourceCss;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.item.EditQuestionPopupVc;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.item.EditResourcePopupVc;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.item.EditUserOwnResourcePopupVc;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.item.ShelfCollectionResourceChildView;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.item.UpdateQuestionImageView;
import org.ednovo.gooru.client.mvp.shelf.event.GetEditPageHeightEvent;
import org.ednovo.gooru.client.mvp.shelf.event.InsertCollectionItemInAddResourceEvent;
import org.ednovo.gooru.client.mvp.shelf.event.RefreshCollectionItemInShelfListEvent;
import org.ednovo.gooru.client.mvp.shelf.event.RefreshType;
import org.ednovo.gooru.client.mvp.shelf.event.UpdateResourceCountEvent;
import org.ednovo.gooru.client.uc.AlertContentUc;
import org.ednovo.gooru.client.uc.ConfirmationPopupVc;
import org.ednovo.gooru.client.uc.tooltip.AddResourceToolTip;
import org.ednovo.gooru.client.util.MixpanelUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *  
 */
public class CollectionResourceTabView extends
		BaseViewWithHandlers<CollectionResourceTabUiHandlers> implements
		IsCollectionResourceTabView {

	private static CollectionResourceTabViewUiBinder uiBinder = GWT
			.create(CollectionResourceTabViewUiBinder.class);
	
	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	interface CollectionResourceTabViewUiBinder extends
			UiBinder<Widget, CollectionResourceTabView> {
	}

	@UiField
	VerticalPanel sequenceVerPanel/*,collectionResourcePanelVc*/;

	@UiField
	CollectionResourcePanelVc collectionResourcePanelVc; 

	@UiField
	Label dragAndDropLabel;

	@UiField
	Label noResourceLineOneLabel, noResourceLineTwoLabel,
			noResourceLineThreeLabel, noResourceLineFourLabel,noResourceLineFiveLabel,noResourceLineSixLabel;

	@UiField
	HTMLPanel panelNoResourceContainer,panelLoading,contentPanel;

	@UiField
	Button buttonContainer, buttonContainerForQuestion, buttonContainerAddGray,buttonContainerForQuestionGreay ;

	private CollectionDo collectionDo;

	private CollectionEditResourceCss css;
	
	ShelfCollectionResourceChildView shelfCollectionResourceVc;

	private EditQuestionPopupWidget editQuestionPopupWidget;

	private EditResourcePopupVc editResoruce;
	
	private EditUserOwnResourcePopupVc ownResourcePopupVc;
	
	ConfirmationPopupVc deleteConfirmationPopupVc = null;

	private String refreshType = "insert";
	
	String title,description,category,thumbnailUrl;
	private CollectionItemDo collectionItemDo;
	
	private boolean isFlag=false;
	
	private String clickType;
	private PopupPanel toolTipPopupPanel = new PopupPanel();

	private static final String MESSAGE_HEADER = i18n.GL0748();
	private static final String MESSAGE_CONTENT = i18n.GL0891();
	private HandlerRegistration handlerRegistration=null;
	
	private int totalCount;
	
	private static final String UP_ARROW = "MoveUp";
	
	private static final String DOWN_ARROW = "MoveDown";
	
	private static final String REORDER_VALIDATION_MSG = "Success";

	/**
	 * Class constructor
	 */
	public CollectionResourceTabView() {
		setWidget(uiBinder.createAndBindUi(this));
		buttonContainer.setText(i18n.GL0851());
		buttonContainer.getElement().setId("btnNewResource");
		buttonContainer.getElement().setAttribute("alt",i18n.GL0851());
		buttonContainer.getElement().setAttribute("title",i18n.GL0851());
		
		buttonContainerAddGray.setText(i18n.GL0851());
		buttonContainerAddGray.getElement().setId("btnButtonContainerAddGray");
		buttonContainerAddGray.getElement().setAttribute("alt",i18n.GL0851());
		buttonContainerAddGray.getElement().setAttribute("title",i18n.GL0851());
		
		buttonContainerForQuestion.setText(i18n.GL0852());
		buttonContainerForQuestion.getElement().setId("btnNewQuestion");
		buttonContainerForQuestion.getElement().setAttribute("alt",i18n.GL0852());
		buttonContainerForQuestion.getElement().setAttribute("title",i18n.GL0852());
		
		buttonContainerForQuestionGreay.setText(i18n.GL0852());
		buttonContainerForQuestionGreay.getElement().setId("btnButtonContainerForQuestionGreay");
		buttonContainerForQuestionGreay.getElement().setAttribute("alt",i18n.GL0852());
		buttonContainerForQuestionGreay.getElement().setAttribute("title",i18n.GL0852());
		
		dragAndDropLabel.setText(i18n.GL0853());
		dragAndDropLabel.getElement().setId("lblDragAndDropLabel");
		dragAndDropLabel.getElement().setAttribute("alt",i18n.GL0853());
		dragAndDropLabel.getElement().setAttribute("title",i18n.GL0853());
		
		noResourceLineOneLabel.getElement().setId("lblNoResourceLineOneLabel");
		noResourceLineThreeLabel.getElement().setId("lblNoResourceLineThreeLabel");
		noResourceLineTwoLabel.getElement().setId("lblNoResourceLineTwoLabel");
		noResourceLineSixLabel.getElement().setId("lblNoResourceLineSixLabel");
		noResourceLineFiveLabel.getElement().setId("lblNoResourceLineFiveLabel");
		
		
		noResourceLineFourLabel.getElement().setId("lblNoResourceLineFourLabel");
		
		CollectionEditResourceCBundle.INSTANCE.css().ensureInjected();
		css = CollectionEditResourceCBundle.INSTANCE.css();
		showOrHideNoCollectionItemsMessage(false);
		buttonContainer.addClickHandler(new NewResourceClickEvent());
		buttonContainerForQuestion.addClickHandler(new NewQuestionClickEvent());
		dragAndDropLabel.setVisible(true);
		buttonContainerAddGray.setVisible(false);
		contentPanel.setVisible(false);
		panelLoading.getElement().getStyle().setDisplay(Display.BLOCK);
		collectionResourcePanelVc.getElement().setId("editPanelShelf");
		panelLoading.getElement().setId("pnlPanelLoading");
		contentPanel.getElement().setId("pnlContentPanel");
		sequenceVerPanel.getElement().setId("vpnlSequenceVerPanel");
		panelNoResourceContainer.getElement().setId("pnlPanelNoResourceContainer");
	}

	@Override
	public void reset() {
		super.reset();
		collectionDo = null;
		sequenceVerPanel.clear();
		collectionResourcePanelVc.clear();
	}

	@Override
	public void setData(CollectionDo collectionDo) {
		if (this.collectionDo == null) {
			this.collectionDo = collectionDo;
			setTotalCount(collectionDo.getCollectionItems().size());
			Label label = new Label("");
			label.setStyleName(getCss().shelfResourceDragdropSpacer());
			collectionResourcePanelVc.superAdd(label);
			Label toplabel = new Label("");
			toplabel.setStyleName(getCss().shelfResourceDragdropSpacer());
			collectionResourcePanelVc.add(toplabel);
			Label prelabel = new Label("");
			prelabel.setStyleName(getCss().shelfResourceSequenceSpacer());
			sequenceVerPanel.add(prelabel);
			Label postlabel = new Label("");
			postlabel.setStyleName(getCss().shelfResourceSequenceSpacer());
			sequenceVerPanel.add(postlabel);
			if (collectionDo.getCollectionItems().size() <= 0) {
				showNoCollectionsItemsMessage(collectionDo.getCollectionType());
				showOrHideNoCollectionItemsMessage(true);
				// dragAndDropLabel.setVisible(false);
			}else{
				
			}
			for (CollectionItemDo collectionItem : collectionDo.getCollectionItems()) {
				insertColectionItem(collectionItem, false);
			}
			hideNoResourceMsg();
			
			if (collectionDo.getCollectionItems().size() >= 25) {
				buttonContainerForQuestionGreay.setVisible(true);
				buttonContainerAddGray.setVisible(true);
				buttonContainer.setVisible(false);
				buttonContainerForQuestion.setVisible(false);
				buttonContainerAddGray.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
					}
				});
				buttonContainerForQuestionGreay.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
					}
				});
				buttonContainerAddGray.addMouseOverHandler(new MouseOverHandler() {

					@Override
					public void onMouseOver(MouseOverEvent event) {
						toolTipPopupPanel.clear();
						toolTipPopupPanel.setWidget(new AddResourceToolTip());
						toolTipPopupPanel.setPopupPosition(	event.getRelativeElement().getAbsoluteLeft() - 1, event.getRelativeElement().getAbsoluteTop() + 22);
						toolTipPopupPanel.removeStyleName("gwt-PopupPanel");
						toolTipPopupPanel.show();
					}

				});
				buttonContainerAddGray.addMouseOutHandler(new MouseOutHandler() {

					@Override
					public void onMouseOut(MouseOutEvent event) {
						toolTipPopupPanel.hide();

					}
				});
				buttonContainerForQuestionGreay.addMouseOverHandler(new MouseOverHandler() {

					@Override
					public void onMouseOver(MouseOverEvent event) {
						toolTipPopupPanel.clear();
						toolTipPopupPanel.setWidget(new AddResourceToolTip());
						toolTipPopupPanel.setPopupPosition(event.getRelativeElement().getAbsoluteLeft() - 1, event.getRelativeElement().getAbsoluteTop() + 22);
						toolTipPopupPanel.removeStyleName("gwt-PopupPanel");
						toolTipPopupPanel.show();

					}
				});
				buttonContainerForQuestionGreay.addMouseOutHandler(new MouseOutHandler() {

					@Override
					public void onMouseOut(MouseOutEvent event) {
						toolTipPopupPanel.hide();

					}
				});

			} else {
				toolTipPopupPanel.clear();
				buttonContainerForQuestionGreay.setVisible(false);
				buttonContainerAddGray.setVisible(false);
				if(collectionDo.getCollectionType()!=null&&collectionDo.getCollectionType().equals("assessment")){
					buttonContainer.setVisible(false);
				}else{
					buttonContainer.setVisible(true);
				}
				buttonContainerForQuestion.setVisible(true);
			}
		}
		panelLoading.getElement().getStyle().setDisplay(Display.NONE);
		contentPanel.setVisible(true);
	}
	public void modifyExistingCollectionItemWidget(final CollectionItemDo collectionItemDo){
		collectionItemDo.setCollection(collectionDo);
		shelfCollectionResourceVc = new ShelfCollectionResourceChildView(this, collectionItemDo);
		shelfCollectionResourceVc.getEditInfoLbl().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						shelfCollectionResourceVc.getResourceEditButtonContainer().setVisible(false);

						shelfCollectionResourceVc.getEditButton().setVisible(false);
						AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99,false));
						if (collectionItemDo.getResource().getCategory().equalsIgnoreCase("Question")) {
							getUiHandlers().showEditQuestionResourcePopup(collectionItemDo);
						}else if(collectionItemDo.getResource().getResourceType().getName().equals("resource/url") || collectionItemDo.getResource().getResourceType().getName().equals("video/youtube")
								|| collectionItemDo.getResource().getResourceType().getName().equals("vimeo/video")){
							editResoruce = new EditResourcePopupVc(collectionItemDo) {

								@Override
								public void updateResource(CollectionItemDo collectionItemDo,List<String> tagList) {
									getUiHandlers().updateResourceInfo(collectionItemDo,tagList);
								}

								@Override
								public void resourceImageUpload() {
									getUiHandlers().imageEditResourceUpload();
								}

								@Override
								public void onSelection(
										SelectionEvent<Suggestion> event) {
									super.onSelection(event);		
								}

								@Override
								public void closeStandardsPopup() {
									getUiHandlers().closeBrowseStandardsPopup();
								}

								@Override
								public void browseStandardsInfo(boolean val,
										boolean userResource) {
									getUiHandlers().getBrowseStandardsInfo(val, userResource);
								}
							};
						}
						else {
							MixpanelUtil.Resource_Action_Edit_Info();
							ownResourcePopupVc = new EditUserOwnResourcePopupVc(collectionItemDo) {
								@Override
								public void resourceImageUpload() {
									getUiHandlers().imageEditUserOwnResourceUpload();
								}
								@Override
								public void updateUserOwnResource(String resourceFilePath,String resMediaFileName,String resOriginalFileName,String titleStr, String desc,String categoryStr,String thumbnailUrlStr,CollectionItemDo collectionItemDo, List<String> tagList) {
									title=titleStr;
									description = desc;
									category = categoryStr;
	 								thumbnailUrl = thumbnailUrlStr;
									JSONObject jsonObject = setEditUserResourceJsonObject(resOriginalFileName,resMediaFileName, title, desc, category, thumbnailUrlStr,collectionItemDo,tagList);
									getUiHandlers().editUserOwnResource(jsonObject.toString(),collectionItemDo.getCollectionItemId(),collectionItemDo.getParentGooruOid());
								}
								@Override
								public void browseStandardsInfo(boolean val, boolean userResource) {
									getUiHandlers().getBrowseStandardsInfo(val,userResource);
								}
								@Override
								public void closeStandardsPopup() {
									getUiHandlers().closeBrowseStandardsPopup();
								}
								
								@Override
								public void onSelection(SelectionEvent<Suggestion> event) {
									super.onSelection(event);		
								}
							};
						} 
					}
				});
		collectionResourcePanelVc.remove(collectionItemDo.getItemSequence());
		collectionResourcePanelVc.addDraggable(shelfCollectionResourceVc,collectionItemDo.getItemSequence());	
		collectionResourcePanelVc.insert(shelfCollectionResourceVc, collectionItemDo.getItemSequence());
	}

	@Override
	public void insertColectionItem(final CollectionItemDo collectionItemDo,boolean newFlag) {
		
		this.collectionItemDo = collectionItemDo;
		if (collectionDo.getCollectionItems().size() >= 25) {
			buttonContainerForQuestionGreay.setVisible(true);
			buttonContainerAddGray.setVisible(true);
			buttonContainer.setVisible(false);
			buttonContainerForQuestion.setVisible(false);
			buttonContainerAddGray.addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					toolTipPopupPanel.clear();
					toolTipPopupPanel.setWidget(new AddResourceToolTip());
					toolTipPopupPanel.setPopupPosition(event.getRelativeElement().getAbsoluteLeft() - 1, event.getRelativeElement().getAbsoluteTop() + 22);
					toolTipPopupPanel.removeStyleName("gwt-PopupPanel");
					toolTipPopupPanel.show();
				}

			});
			
			buttonContainerAddGray.addMouseOutHandler(new MouseOutHandler() {
				@Override
				public void onMouseOut(MouseOutEvent event) {
					toolTipPopupPanel.hide();
				}
			});
			
			
			buttonContainerForQuestionGreay.addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					toolTipPopupPanel.clear();
					toolTipPopupPanel.setWidget(new AddResourceToolTip());
					toolTipPopupPanel.setPopupPosition(event.getRelativeElement().getAbsoluteLeft() - 1, event.getRelativeElement().getAbsoluteTop() + 22);
					toolTipPopupPanel.removeStyleName("gwt-PopupPanel");
					toolTipPopupPanel.show();

				}
			});
			buttonContainerForQuestionGreay.addMouseOutHandler(new MouseOutHandler() {

				@Override
				public void onMouseOut(MouseOutEvent event) {
					toolTipPopupPanel.hide();

				}
			});

		}else{
			toolTipPopupPanel.clear();
			buttonContainerForQuestionGreay.setVisible(false);
			buttonContainerAddGray.setVisible(false);
			if(collectionDo.getCollectionType()!=null&&collectionDo.getCollectionType().equals("assessment")){
				buttonContainer.setVisible(false);
			}else{
				buttonContainer.setVisible(true);
			}
			buttonContainerForQuestion.setVisible(true);
		}
		if (newFlag) {
			if (collectionDo.getCollectionItems() == null) {
				collectionDo
						.setCollectionItems(new ArrayList<CollectionItemDo>());
			}
			if (!collectionDo.getCollectionItems().contains(collectionItemDo)) {
				collectionDo.getCollectionItems().add(collectionItemDo);
			}

			AppClientFactory.fireEvent(new RefreshCollectionItemInShelfListEvent(collectionItemDo, RefreshType.INSERT));
			showOrHideNoCollectionItemsMessage(false);
			dragAndDropLabel.setVisible(true);

		} else {
			collectionItemDo.setCollection(collectionDo);
			collectionItemDo.setGooruUId(collectionDo.getUser().getGooruUId());
			Label sequenceLbl = new Label(collectionItemDo.getItemSequence()+ "");
			sequenceLbl.setStyleName(getCss().shelfResourceSequenceNumber());
			int sequencePostion = collectionItemDo.getItemSequence();
			sequencePostion = sequencePostion >= sequenceVerPanel.getWidgetCount() ? sequenceVerPanel.getWidgetCount() - 1 : sequencePostion;
			sequenceVerPanel.insert(sequenceLbl, sequencePostion);
			shelfCollectionResourceVc = new ShelfCollectionResourceChildView(this, collectionItemDo);
			resetSequence();
			if(isFlag){
				isFlag=false;
				shelfCollectionResourceVc.addNewResource();
			}
			shelfCollectionResourceVc.getEditInfoLbl().addClickHandler(	new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					shelfCollectionResourceVc.getResourceEditButtonContainer().setVisible(false);
					shelfCollectionResourceVc.getEditButton().setVisible(false);
					AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99,false));
					if (collectionItemDo.getResource().getCategory().equalsIgnoreCase("Question")) {
						getUiHandlers().showEditQuestionResourcePopup(collectionItemDo);
					} else if(collectionItemDo.getResource().getResourceType().getName().equals("resource/url") || collectionItemDo.getResource().getResourceType().getName().equals("video/youtube") || 
							collectionItemDo.getResource().getResourceType().getName().equals("vimeo/video")){
						editResoruce = new EditResourcePopupVc(collectionItemDo) {

							@Override
							public void updateResource(CollectionItemDo collectionItemDo,List<String> tagList) {
								getUiHandlers().updateResourceInfo(collectionItemDo,tagList);
							}

							@Override
							public void resourceImageUpload() {
								getUiHandlers().imageEditResourceUpload();
							}

							@Override
							public void onSelection(
									SelectionEvent<Suggestion> event) {
								super.onSelection(event);		
							}

							@Override
							public void browseStandardsInfo(boolean val,boolean userResource) {
								getUiHandlers().getBrowseStandardsInfo(val,userResource);
							}

							@Override
							public void closeStandardsPopup() {
								getUiHandlers().closeBrowseStandardsPopup();
							}
						};
					}
					else {
						MixpanelUtil.Resource_Action_Edit_Info();
						ownResourcePopupVc = new EditUserOwnResourcePopupVc(collectionItemDo) {
							@Override
							public void resourceImageUpload() {
								getUiHandlers().imageEditUserOwnResourceUpload();
							}
							@Override
							public void updateUserOwnResource(String resourceFilePath,String resMediaFileName,String resOriginalFileName,String titleStr, String desc,String categoryStr,String thumbnailUrlStr,CollectionItemDo collectionItemDo, List<String> tagList) {
								title=titleStr;
								description = desc;
								category = categoryStr;
 								thumbnailUrl = thumbnailUrlStr;
								JSONObject jsonObject = setEditUserResourceJsonObject(resOriginalFileName,resMediaFileName, title, desc, category, thumbnailUrlStr,collectionItemDo,tagList);
								getUiHandlers().editUserOwnResource(jsonObject.toString(),collectionItemDo.getCollectionItemId(),collectionItemDo.getParentGooruOid());
							}
							@Override
							public void browseStandardsInfo(boolean val, boolean userResource) {
								getUiHandlers().getBrowseStandardsInfo(val,userResource);
							}
							@Override
							public void closeStandardsPopup() {
								getUiHandlers().closeBrowseStandardsPopup();
							}
							
							@Override
							public void onSelection(SelectionEvent<Suggestion> event) {
								super.onSelection(event);		
							}
						};
					}

				}
			});
			collectionResourcePanelVc.addDraggable(shelfCollectionResourceVc,collectionItemDo.getItemSequence());
			AppClientFactory.fireEvent(new UpdateResourceCountEvent(collectionDo.getCollectionItems().size()));
		}
		hideNoResourceMsg();
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
	}

	public void setNewResourcePanel(final CollectionItemDo collectionItemDo) {
		this.collectionItemDo = collectionItemDo;
		// Refresh on adding : Shelf refreshes but the code below throws an
		// index out of bouds exception. If this is fixed. New resources will
		// get added
		collectionItemDo.setCollection(this.collectionDo);
		Label sequenceLbl = new Label(collectionItemDo.getItemSequence() + "");
		sequenceLbl.setStyleName(getCss().shelfResourceSequenceNumber());
		int sequencePostion = collectionItemDo.getItemSequence();
		sequencePostion = sequencePostion >= sequenceVerPanel.getWidgetCount() ? sequenceVerPanel.getWidgetCount() - 1 : sequencePostion;
		sequenceVerPanel.insert(sequenceLbl, sequencePostion);
		final ShelfCollectionResourceChildView shelfCollectionResourceVc = new ShelfCollectionResourceChildView(this, collectionItemDo);
		resetSequence();
		Window.Location.reload();
		shelfCollectionResourceVc.getEditInfoLbl().addClickHandler(	new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						shelfCollectionResourceVc.getResourceEditButtonContainer().setVisible(false);
						shelfCollectionResourceVc.getEditButton().setVisible(false);
						AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99,false));
						if (collectionItemDo.getResource().getCategory().equalsIgnoreCase("Question")) {
							editQuestionPopupWidget = new EditQuestionPopupWidget(collectionItemDo);
							editQuestionPopupWidget.getAddQuestion().getElement().getStyle().setDisplay(Display.NONE);
							editQuestionPopupWidget.getUpdateQuestionImageView().getUploadImage().addClickHandler(new ClickHandler() {
										@Override
										public void onClick(ClickEvent event) {
											getUiHandlers().updateQustionImage(collectionItemDo.getCollectionItemId());
										}
									});
							editQuestionPopupWidget
									.getUpdateQuestionImageView()
									.getRemoveImage()
									.addClickHandler(new ClickHandler() {

										@Override
										public void onClick(ClickEvent event) {
											editQuestionPopupWidget.hide();
											deleteConfirmationPopupVc = new ConfirmationPopupVc(
													MESSAGE_HEADER,
													MESSAGE_CONTENT) {

												@Override
												public void onDelete(
														ClickEvent clickEvent) {

													getUiHandlers()
															.removeQuestionImage(
																	collectionItemDo
																			.getResource()
																			.getGooruOid());
												}

												public void hide() {
													super.hide();

													Window.enableScrolling(true);
													AppClientFactory
															.fireEvent(new SetHeaderZIndexEvent(
																	0, true));

													editQuestionPopupWidget
															.show();
												}
											};
										}
									});

							editQuestionPopupWidget.getAddQuestion()
									.addClickHandler(new ClickHandler() {

										@Override
										public void onClick(ClickEvent event) {
											getUiHandlers().updateQustionImage(
													collectionItemDo
															.getCollectionItemId());
										}
									});
						} else if(collectionItemDo.getResource().getResourceType().getName().equals("resource/url") || collectionItemDo.getResource().getResourceType().getName().equals("video/youtube")
								|| collectionItemDo.getResource().getResourceType().getName().equals("vimeo/video")){
							editResoruce = new EditResourcePopupVc(collectionItemDo) {

							@Override
							public void updateResource(CollectionItemDo collectionItemDo,List<String> tagList) {
								getUiHandlers().updateResourceInfo(collectionItemDo,tagList);
							}

							@Override
							public void resourceImageUpload() {
								getUiHandlers().imageEditResourceUpload();
								}

							@Override
							public void onSelection(
									SelectionEvent<Suggestion> event) {
								super.onSelection(event);
								
							}

							@Override
							public void browseStandardsInfo(boolean val,boolean userResource) {
								getUiHandlers().getBrowseStandardsInfo(val,userResource);
							}

							@Override
							public void closeStandardsPopup() {
								getUiHandlers().closeBrowseStandardsPopup();
							}
							};
						}

						else {
							MixpanelUtil.Resource_Action_Edit_Info();
							ownResourcePopupVc = new EditUserOwnResourcePopupVc(collectionItemDo) {
								@Override
								public void resourceImageUpload() {
									getUiHandlers().imageEditUserOwnResourceUpload();
								}

								@Override
								public void updateUserOwnResource(String resourceFilePath,String resMediaFileName,String resOriginalFileName,String titleStr, String desc,String categoryStr,String thumbnailUrlStr,CollectionItemDo collectionItemDo, List<String> tagList) {
									title=titleStr;
									description = desc;
									category = categoryStr;
									thumbnailUrl = thumbnailUrlStr;
									JSONObject jsonObject = setEditUserResourceJsonObject(resOriginalFileName,resMediaFileName, title, desc, category, thumbnailUrlStr,collectionItemDo,tagList);
									getUiHandlers().editUserOwnResource(jsonObject.toString(),collectionItemDo.getCollectionItemId(),collectionItemDo.getParentGooruOid());
								}
								@Override
								public void browseStandardsInfo(boolean val, boolean userResource) {
									getUiHandlers().getBrowseStandardsInfo(val,userResource);
								}
								@Override
								public void closeStandardsPopup() {
									getUiHandlers().closeBrowseStandardsPopup();
								}
								@Override
								public void onSelection(SelectionEvent<Suggestion> event) {
									super.onSelection(event);		
								}
							};
						}
					}
				});
		collectionResourcePanelVc.addDraggable(shelfCollectionResourceVc,collectionItemDo.getItemSequence());
	}

	public class EditQuestionPopupWidget extends EditQuestionPopupVc {
		private String collectionItemId;

		public EditQuestionPopupWidget(CollectionItemDo collectionItemDo) {
			super(collectionItemDo);
			this.collectionItemId = collectionItemDo.getCollectionItemId();
			AppClientFactory.fireEvent(new GetEditPageHeightEvent(this, false));
		}

		public void show() {
			super.show();
			this.center();
		}

		@Override
		public void updateQuestionResource(String collectionItemId,
				CollectionQuestionItemDo collectionQuestionItemDo) {
			
			if (editQuestionPopupWidget.getQuestionImageContainer()
					.getElement().hasChildNodes()) {
				UpdateQuestionImageView updateQuestionImage = (UpdateQuestionImageView) editQuestionPopupWidget
						.getQuestionImageContainer().getWidget(0);
				String thumbnailUrl = updateQuestionImage
						.getThumbnailImageUrl();
				if (thumbnailUrl != null) {
					getUiHandlers().updateQuestionResource(collectionItemId,
							collectionQuestionItemDo,
							"asset-question_" + thumbnailUrl);

				} else {
					getUiHandlers().updateQuestionResource(collectionItemId,
							collectionQuestionItemDo, null);
				}

			} else {
				getUiHandlers().updateQuestionResource(collectionItemId,
						collectionQuestionItemDo, null);
			}

		}

		@Override
		public void callBrowseStandardsInfo(boolean val,boolean userResource) {
			getUiHandlers().getBrowseStandardsInfo(val,userResource);
		}

		public void setUpdatedBrowseStandardsVal(String setStandardsVal,Integer codeId, String setStandardDesc) {
			super.setUpdatedBrowseStandards(setStandardsVal,codeId,setStandardDesc);
			
		}

		@Override
		public void closeBrowseStandardsPopup() {
		getUiHandlers().closeBrowseStandardsPopup();
		}

	}

	@Override
	public void removeCollectionItem(CollectionItemDo collectionItemDo,	ShelfCollectionResourceChildView resourceChildView) {
		int sequence = collectionResourcePanelVc.getWidgetIndex(resourceChildView.getParent());
		collectionResourcePanelVc.remove(sequence);
		sequenceVerPanel.remove(sequence);
		resetSequence();
		collectionDo.getCollectionItems().remove(collectionItemDo);
		if (collectionDo.getCollectionItems().size() >= 25) {

			buttonContainerForQuestionGreay.setVisible(true);
			buttonContainerAddGray.setVisible(true);
			buttonContainer.setVisible(false);
			buttonContainerForQuestion.setVisible(false);
			buttonContainerAddGray.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {

				}
			});
			buttonContainerForQuestionGreay.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {

				}
			});
			buttonContainerAddGray.addMouseOverHandler(new MouseOverHandler() {

				@Override
				public void onMouseOver(MouseOverEvent event) {
					toolTipPopupPanel.clear();
					toolTipPopupPanel.setWidget(new AddResourceToolTip());
					toolTipPopupPanel.setPopupPosition(event
							.getRelativeElement().getAbsoluteLeft() - 1, event
							.getRelativeElement().getAbsoluteTop() + 22);
					toolTipPopupPanel.removeStyleName("gwt-PopupPanel");
					toolTipPopupPanel.show();
				}

			});
			buttonContainerAddGray.addMouseOutHandler(new MouseOutHandler() {

				@Override
				public void onMouseOut(MouseOutEvent event) {
					toolTipPopupPanel.hide();

				}
			});
			buttonContainerForQuestionGreay
					.addMouseOverHandler(new MouseOverHandler() {

						@Override
						public void onMouseOver(MouseOverEvent event) {
							toolTipPopupPanel.clear();
							toolTipPopupPanel
									.setWidget(new AddResourceToolTip());
							toolTipPopupPanel.setPopupPosition(
									event.getRelativeElement()
											.getAbsoluteLeft() - 1, event
											.getRelativeElement()
											.getAbsoluteTop() + 22);
							toolTipPopupPanel.removeStyleName("gwt-PopupPanel");
							toolTipPopupPanel.show();

						}
					});
			buttonContainerForQuestionGreay
					.addMouseOutHandler(new MouseOutHandler() {

						@Override
						public void onMouseOut(MouseOutEvent event) {
							toolTipPopupPanel.hide();

						}
					});
		} else {

			toolTipPopupPanel.clear();
			buttonContainerForQuestionGreay.setVisible(false);
			buttonContainerAddGray.setVisible(false);
			if(collectionDo.getCollectionType()!=null&&collectionDo.getCollectionType().equals("assessment")){
				buttonContainer.setVisible(false);
			}else{
				buttonContainer.setVisible(true);
			}
			buttonContainerForQuestion.setVisible(true);
		}
		if (collectionDo.getCollectionItems().size() <= 0) {
			showNoCollectionsItemsMessage(collectionDo.getCollectionType());
			showOrHideNoCollectionItemsMessage(true);
		}
		AppClientFactory.fireEvent(new UpdateResourceCountEvent(collectionDo.getCollectionItems().size()));

	}
	
	public void showNoCollectionsItemsMessage(String collectionType){

		if(collectionType!=null&&collectionType.equals("assessment")){
			setAttributeToWidget(noResourceLineOneLabel,i18n.GL3014());
			setAttributeToWidget(noResourceLineTwoLabel,i18n.GL3276());
			setAttributeToWidget(noResourceLineThreeLabel,"");
			setAttributeToWidget(noResourceLineFourLabel," "+i18n.GL3015());
			setAttributeToWidget(noResourceLineSixLabel,"");
			setAttributeToWidget(noResourceLineFiveLabel," "+i18n.GL0857());
		}else{
			setAttributeToWidget(noResourceLineOneLabel,i18n.GL0854());
			setAttributeToWidget(noResourceLineTwoLabel,i18n.GL0855());
			setAttributeToWidget(noResourceLineThreeLabel," "+i18n.GL0856());
			setAttributeToWidget(noResourceLineFourLabel," "+i18n.GL0858());
			setAttributeToWidget(noResourceLineSixLabel," "+i18n.GL0209()+" ");
			setAttributeToWidget(noResourceLineFiveLabel," "+i18n.GL0857());
		}
	}
	
	public void setAttributeToWidget(Label textLabel,String text){
		textLabel.setText(text);
		textLabel.getElement().setAttribute("alt",text);
		textLabel.getElement().setAttribute("title",text);
	}
	
	public void showOrHideNoCollectionItemsMessage(boolean isShow){
		noResourceLineOneLabel.setVisible(isShow);
		panelNoResourceContainer.setVisible(isShow);
		noResourceLineTwoLabel.setVisible(isShow);
		noResourceLineThreeLabel.setVisible(isShow);
		noResourceLineFourLabel.setVisible(isShow);
	}

	/**
	 * Reorder the collection item , and change collection item order view
	 */
	private void resetSequence() {
		Iterator<Widget> widgets = sequenceVerPanel.iterator();
		int sequence = 1;
		while (widgets.hasNext()) {
			Widget widget = widgets.next();
			if (widget instanceof Label	&& ((Label) widget).getText().length() > 0) {
				((Label) widget).setText(sequence++ + "");
			}
		}
	}

	@Override
	public void setEditMode(boolean editMode, Widget resourceWidget) {
		Widget sequenceWidget = sequenceVerPanel.getWidget(collectionResourcePanelVc.getWidgetIndex(resourceWidget.getParent()));
		collectionResourcePanelVc.makeChildrenDraggable(!editMode);
		sequenceWidget.setHeight((resourceWidget.getOffsetHeight() - 7) + "px");
	}

	/**
	 * @return css instance of {@link CollectionEditResourceCss}
	 */
	public CollectionEditResourceCss getCss() {
		return css;
	}

	/**
	 * @param css
	 *            name the styles to set
	 */
	public void setCss(CollectionEditResourceCss css) {
		this.css = css;
	}

	/*
	 * To handle click event for New Resource
	 */
	private class NewResourceClickEvent implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			MixpanelUtil.Click_On_AddResource();
			clickType = "Url";
			hidePopup();
			if (collectionDo.getCollectionItems().size() >= 25) {
			} else {
				MixpanelUtil.Click_Add_NewResource();
				Window.enableScrolling(false);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
				displayNewResourcePopup();
			}

		}
	}

	/*
	 * To handle click event for New Resource
	 */
	private class NewQuestionClickEvent implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			try{ 
			MixpanelUtil.Click_On_AddQuestion();
			clickType = "Question";
			hidePopup();
			if (collectionDo.getCollectionItems().size() >= 25) {

			} else {
				Window.enableScrolling(false);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
				displayNewResourcePopup();
			}
		}catch(Exception e){
			AppClientFactory.printSevereLogger("CollectionResourceTabView collectionitems size:::"+e);
		}
			

		}

	}
	
	/*
	 * To hide the Popup
	 */
	private void hidePopup() {
	}

	public void showMaximumCollectionItemsPopup() {

		AlertContentUc alertContentUc = new AlertContentUc(
				i18n.GL0061(),
				i18n.GL0302());

	}
	@Override
	public void displayNewResourcePopup() {
		getUiHandlers().addResourcePopup(collectionDo, clickType);
	}


	@Override
	public void insertData(CollectionItemDo collectionItem) {
		AppClientFactory.fireEvent(new RefreshCollectionItemInShelfListEvent(
				collectionItem, RefreshType.INSERT));
		AppClientFactory.fireEvent(new InsertCollectionItemInAddResourceEvent(
				collectionItem, RefreshType.INSERT));
	}
	@Override
	public void updateCollectionItem(CollectionItemDo collectionItem) {
		if(collectionItem != null)
		{
		AppClientFactory.fireEvent(new InsertCollectionItemInAddResourceEvent(collectionItem, RefreshType.UPDATE));
		}
	}
	

	native void redirect(String url)
	/*-{
		$wnd.location.reload();
	}-*/;


	@Override
	public void hideUpdateResourcePopup() {
		
			editResoruce.hide();

			Window.enableScrolling(true);
			AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
	
		

	}
	
	@Override
	public void hideUpdateOwnResourcePopup() {
		
			ownResourcePopupVc.hide();

			Window.enableScrolling(true);
			AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
	
		

	}

	@Override
	public void hideUpdateResourceQuestionPopup() {
			AppClientFactory.fireEvent(new GetEditPageHeightEvent(editQuestionPopupWidget, true));
			editQuestionPopupWidget.hide();

			Window.enableScrolling(true);
			AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
		
		

	}

	@Override
	public void updateCollectionItemImage(String imageUrl,
			String fileNameWithOutRepository) {

		if (editQuestionPopupWidget.getQuestionImageContainer().getElement()
				.hasChildNodes()) {
			UpdateQuestionImageView updateQuestionImage = (UpdateQuestionImageView) editQuestionPopupWidget
					.getQuestionImageContainer().getWidget(0);
			updateQuestionImage.getUpdateQuestionImage().setUrl(imageUrl);
			updateQuestionImage.setThumbnailImageUrl(fileNameWithOutRepository);
		} else {
			displayUpdateQuestionView(imageUrl, fileNameWithOutRepository);
		}

	}

	public void displayUpdateQuestionView(String imageUrl,
			String fileNameWithOutRepository) {
		UpdateQuestionImageView updateQuestionImage = new UpdateQuestionImageView();
		updateQuestionImage.getUpdateQuestionImage().setUrl(imageUrl);
		updateQuestionImage.setThumbnailImageUrl(fileNameWithOutRepository);
		editQuestionPopupWidget.getQuestionImageContainer().clear();
		editQuestionPopupWidget.getQuestionImageContainer().add(
				updateQuestionImage);
		updateQuestionImage.getUploadImage().addClickHandler(
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						getUiHandlers().updateQustionImage(
								editQuestionPopupWidget.collectionItemId);
					}
				});
		updateQuestionImage.getRemoveImage().addClickHandler(
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						editQuestionPopupWidget.hide();
						deleteConfirmationPopupVc = new ConfirmationPopupVc(
								MESSAGE_HEADER, MESSAGE_CONTENT) {

							@Override
							public void onDelete(ClickEvent clickEvent) {

								getUiHandlers()
										.removeQuestionImage(
												editQuestionPopupWidget.collectionItemId);
							}

							public void hide() {
								super.hide();

								Window.enableScrolling(true);
								AppClientFactory
										.fireEvent(new SetHeaderZIndexEvent(0,
												true));

								editQuestionPopupWidget.show();
							}
						};
					}
				});
	}

	@Override
	public void removeUpdateQuestionView() {
		deleteConfirmationPopupVc.hide();

		Window.enableScrolling(true);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));

		editQuestionPopupWidget.show();
		editQuestionPopupWidget.getUpdateQuestionImageView().removeFromParent();
		editQuestionPopupWidget.getAddQuestion().getElement().getStyle()
				.setDisplay(Display.BLOCK);
	}

	@Override
	public void updateResouceItemImage(String imageUrl,String fileNameWithOutRespUrl, boolean isEditUserOwnResourceImage) {
		if(isEditUserOwnResourceImage){
			ownResourcePopupVc.getSetThumbnailImage().setUrl(imageUrl + "?" + Math.random());
			ownResourcePopupVc.setThumbnailUrlStr(imageUrl);
			ownResourcePopupVc.setFileNameWithOutRespUrl(fileNameWithOutRespUrl);
		}
		else{
			editResoruce.getSetThumbnailImage().setUrl(imageUrl + "?" + Math.random());
			editResoruce.setThumbnailUrlStr(imageUrl);
			editResoruce.setFileNameWithOutRespUrl(fileNameWithOutRespUrl);
		}
		
	}

	@Override
	public void hideNoResourceMsg() {
		if (this.collectionDo.getCollectionItems().size() > 0) {
			showOrHideNoCollectionItemsMessage(false);
		}
	}

	@Override
	public void insertCollectionItemInAddResource(
			CollectionItemDo collectionItem, RefreshType refreshType) {
		this.refreshType = refreshType.toString();
		if (this.refreshType.equalsIgnoreCase("insert")) {
			collectionDo.getCollectionItems().add(collectionItem);
			setTotalCount(collectionDo.getCollectionItems().size());
			isFlag=true;
			insertColectionItem(collectionItem, false);
		} else if(refreshType.toString().equalsIgnoreCase("update")){
			collectionDo.getCollectionItems().set(collectionItem.getItemSequence()-1, collectionItem);
			modifyExistingCollectionItemWidget(collectionItem);
		}
		else {
			insertColectionItem(collectionItem, false);
		}
	}

	@Override
	public void closeAllOpenedPopUp() {
		if(editResoruce!=null && editResoruce.isShowing()){
			editResoruce.hide();

		}
		if(editQuestionPopupWidget!=null && editQuestionPopupWidget.isShowing()){
			editQuestionPopupWidget.hide();
		}
	}

	@Override
	public void uploadResource(MediaUploadDo result) {
	}

	private JSONObject setEditUserResourceJsonObject(String originalFilename,String mediaFileName, String editedTitle, String editedDescription, String editedCategory,String editedThumbnailUrl, CollectionItemDo collectionItemDo, List<String> tagList) {
		JSONObject file = new JSONObject();
		 if(originalFilename!=null && mediaFileName!=null){
			 file.put("filename", new JSONString(originalFilename));
			 file.put("mediaFilename", new JSONString(mediaFileName));
		 }
		
		     
		JSONObject attach = new JSONObject();
        attach.put("title", new JSONString(editedTitle));
        attach.put("description", new JSONString(editedDescription));
        JSONObject resourceFormat = new JSONObject();
        resourceFormat.put("value", new JSONString(editedCategory));
        attach.put("resourceFormat", resourceFormat);
        if(editedThumbnailUrl!=null){
        	 attach.put("thumbnail", new JSONString(editedThumbnailUrl));
        }
        if(originalFilename!=null && mediaFileName!=null){
        	 attach.put("attach", file);
             
        }
        
        List<CodeDo> codeDoList = new ArrayList<CodeDo>(collectionItemDo.getResource().getTaxonomySet());
      
        JSONArray standardsJsonArray = new JSONArray();
        JSONArray momentsOfLearningArrValue = new JSONArray();
        JSONArray educatUseArrValue = new JSONArray();
        JSONArray tagsArrValue = new JSONArray();
        
        for(int i=0;i<codeDoList.size();i++){
        	JSONObject code = new JSONObject();
        	code.put("code",new JSONString(codeDoList.get(i).getCode()));
        	code.put("codeId",new JSONNumber(codeDoList.get(i).getCodeId()));
        	standardsJsonArray.set(i,code);
        }
        attach.put("taxonomySet", standardsJsonArray);
        
        for(int i=0;i<collectionItemDo.getResource().getMomentsOfLearning().size();i++){
        	JSONObject momentsOfLearningJsonObj = new JSONObject();
        	momentsOfLearningJsonObj.put("selected",JSONBoolean.getInstance(collectionItemDo.getResource().getMomentsOfLearning().get(i).isSelected()));        
        	momentsOfLearningJsonObj.put("value",new JSONString(collectionItemDo.getResource().getMomentsOfLearning().get(i).getValue()));
            momentsOfLearningArrValue.set(i, momentsOfLearningJsonObj);
        }
        attach.put("momentsOfLearning", momentsOfLearningArrValue);
        
        for(int i=0;i<collectionItemDo.getResource().getEducationalUse().size();i++){
        	JSONObject educatUseJsonObj = new JSONObject();
        	educatUseJsonObj.put("selected",JSONBoolean.getInstance(collectionItemDo.getResource().getEducationalUse().get(i).isSelected()));
        	educatUseJsonObj.put("value", new JSONString(collectionItemDo.getResource().getEducationalUse().get(i).getValue()));
        	educatUseArrValue.set(i, educatUseJsonObj);
        }
        attach.put("educationalUse", educatUseArrValue);
        
        for(int i=0;i<tagList.size();i++){
        	tagsArrValue.set(i, new JSONString(tagList.get(i))); 
        }
        
        JSONObject resource = new JSONObject();
        resource.put("resourceTags",tagsArrValue);
        resource.put("resource", attach);
        
		return resource;
	}

	@Override
	public void OnBrowseStandardsClickEvent(Button addStandardsBtn) {
		// TODO Auto-generated method stub
		if(handlerRegistration!=null){
			handlerRegistration.removeHandler();
		}
		handlerRegistration=addStandardsBtn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				getUiHandlers().addUpdatedBrowseStandards();
			}
		});
	}

	@Override
	public void setUpdatedStandardsCode(String setStandardsVal, Integer codeId,String setStandardDesc, boolean value, boolean userResource) {
		if(value == false){
			if(userResource){
				ownResourcePopupVc.setUpdatedBrowseStandardsVal(setStandardsVal,codeId,setStandardDesc);
			}else{
				editResoruce.setUpdatedBrowseStandardsVal(setStandardsVal,codeId,setStandardDesc);
			}
			
		}else{
			editQuestionPopupWidget.setUpdatedBrowseStandardsVal(setStandardsVal,codeId,setStandardDesc);
		}
		
	}
	
/*
 * Removed move option
 * 
	private void setResourceSequence() { 
		Iterator<Widget> widgets = collectionResourcePanelVc.iterator(); 
		int seqNum=1;
		while (widgets.hasNext()) {
			Widget widget = widgets.next();
			if (widget instanceof ShelfCollectionResourceChildView) {
				if(getTotalCount()==1){
					((ShelfCollectionResourceChildView) widget).setReorderContainerVisibility(false);
				}else{
					((ShelfCollectionResourceChildView) widget).setReorderContainerVisibility(true);
				}
				if(seqNum==1){
					((ShelfCollectionResourceChildView) widget).upButtonIsVisible(false); 
					((ShelfCollectionResourceChildView) widget).downButtonIsVisible(true); 
				}else{
					*//**
					 * If user moved folder to last position, based on total count down arrow will be invisible and 
					 * vice versa in case of reordering last folder or collection to the first position, up arrow should be in visible.
					 *//*
					if(seqNum==getTotalCount()){
						((ShelfCollectionResourceChildView) widget).upButtonIsVisible(true); 
						((ShelfCollectionResourceChildView) widget).downButtonIsVisible(false);  
					}else{
						((ShelfCollectionResourceChildView) widget).upButtonIsVisible(true); 
						((ShelfCollectionResourceChildView) widget).downButtonIsVisible(true); 
					}
				}
				
				((ShelfCollectionResourceChildView) widget).getReorderTxtBox().setText(seqNum+"");
				((ShelfCollectionResourceChildView) widget).getElement().setAttribute("widgetNumb",seqNum+"");
			}
			seqNum++;
		}
	}
	

	*//**
	 * This inner class used for to restrict text box values to have only numbers
	 *
	 *//*

	public class HasNumbersOnly implements KeyPressHandler {

		@Override
		public void onKeyPress(KeyPressEvent event) {

			if (!Character.isDigit(event.getCharCode()) 
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_TAB 
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_BACKSPACE
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_SHIFT
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_ENTER
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_LEFT
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_RIGHT
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_DELETE){
				((TextBox) event.getSource()).cancelKey();
			}
			if (event.getNativeEvent().getKeyCode() == 46
					|| event.getNativeEvent().getKeyCode() == 37) {
				((TextBox) event.getSource()).cancelKey();
			}
		}
	}
	
	
	*//**
	 * This inner class used for disabling up and down arrow based on user entered reorder value.
	 *
	 *//*

	public class ReorderText implements KeyUpHandler {
		String itemGooruOid;
		int itemPosSeqNumb,itemToBeMovedPosSeqNumb;
		
		public ReorderText(String itemGooruOid) {
			this.itemGooruOid=itemGooruOid;
		}

		@Override
		public void onKeyUp(KeyUpEvent event) {

			ShelfCollectionResourceChildView shelfCollectionResourceChildView = getFolderOrCollectionWidget(itemGooruOid);

			itemPosSeqNumb = shelfCollectionResourceChildView != null ?(Integer.parseInt(shelfCollectionResourceChildView.getElement().getAttribute("widgetNumb"))):0;
			itemToBeMovedPosSeqNumb = (shelfCollectionResourceChildView != null && shelfCollectionResourceChildView.getReorderTxtBox().getText().trim() !=null && !shelfCollectionResourceChildView.getReorderTxtBox().getText().trim().equals(""))?(Integer.parseInt(shelfCollectionResourceChildView.getReorderTxtBox().getText().trim())):0;

			if(itemToBeMovedPosSeqNumb==0 && itemPosSeqNumb!=1 && itemPosSeqNumb!=getTotalCount()){
				shelfCollectionResourceChildView.downButtonIsVisible(true);
				shelfCollectionResourceChildView.upButtonIsVisible(true);
			}else if(itemToBeMovedPosSeqNumb>itemPosSeqNumb && itemPosSeqNumb!=getTotalCount()){
				//disable up arrow
				shelfCollectionResourceChildView.upButtonIsVisible(false); 
				shelfCollectionResourceChildView.downButtonIsVisible(true); 
			}else if(itemToBeMovedPosSeqNumb<itemPosSeqNumb && itemPosSeqNumb!=1){
				//disable down arrow
				shelfCollectionResourceChildView.downButtonIsVisible(false);
				shelfCollectionResourceChildView.upButtonIsVisible(true);
			}
		}
	}
	
	
	*//**
	 * 
	 * Inner class for reorder Up button, which implements click handler {@link ClickHandler}
	 *
	 *//*
	
	public class OnClickReorderUpButton implements ClickHandler{
		private String itemGooruOid;
		int itemPosSeqNumb,itemToBeMovedPosSeqNumb,itemSeqToAPI;
		private String reorderValidationMsg;
		*//**
		 * Class constructor
		 * @param itemGooruOid {@link String}
		 *//*
		public OnClickReorderUpButton(String itemGooruOid) {
			this.itemGooruOid = itemGooruOid;
		}

		@Override
		public void onClick(ClickEvent event) {
			
			ShelfCollectionResourceChildView shelfCollectionResourceChildView = getFolderOrCollectionWidget(itemGooruOid);
			
			itemPosSeqNumb = shelfCollectionResourceChildView != null ?Integer.parseInt(shelfCollectionResourceChildView.getElement().getAttribute("widgetNumb")):0;
			itemToBeMovedPosSeqNumb = (shelfCollectionResourceChildView != null && shelfCollectionResourceChildView.getReorderTxtBox().getText().trim() !=null && !shelfCollectionResourceChildView.getReorderTxtBox().getText().trim().equals(""))?(Integer.parseInt(shelfCollectionResourceChildView.getReorderTxtBox().getText().trim())):0;
			if(shelfCollectionResourceChildView!=null){
				reorderValidationMsg = reorderValidations(itemToBeMovedPosSeqNumb,itemPosSeqNumb,UP_ARROW);
				if(reorderValidationMsg.equalsIgnoreCase(REORDER_VALIDATION_MSG)){
					
					if(itemToBeMovedPosSeqNumb==itemPosSeqNumb){
						itemToBeMovedPosSeqNumb-=1;
					}
					
					shelfCollectionResourceChildView.reorderCollectionResources(shelfCollectionResourceChildView,itemToBeMovedPosSeqNumb,UP_ARROW);
//					reorderItemToNewPosition(shelfCollectionResourceChildView,(itemToBeMovedPosSeqNumb-1),UP_ARROW);
				}else{
					shelfCollectionResourceChildView.showReorderValidationToolTip(reorderValidationMsg);
				}
			}
			
		}
	}
	
	
	
	*//**
	 * 
	 * Inner class for reorder down button, which implements click handler {@link ClickHandler}
	 *
	 *//*
	
	public class OnClickReorderDownButton implements ClickHandler{
		
		private String itemGooruOid;
		int itemPosSeqNumb,itemToBeMovedPosSeqNumb,itemSeqToAPI;
		private String reorderValidationMsg;
		
		
		*//**
		 * Class constructor
		 * @param itemGooruOid {@link String}
		 *//*
		public OnClickReorderDownButton(String itemGooruOid) {
			this.itemGooruOid = itemGooruOid;
		}

		@Override
		public void onClick(ClickEvent event) {
			
			ShelfCollectionResourceChildView shelfCollectionResourceChildView = getFolderOrCollectionWidget(itemGooruOid);

			itemPosSeqNumb = shelfCollectionResourceChildView != null ?(Integer.parseInt(shelfCollectionResourceChildView.getElement().getAttribute("widgetNumb"))):0;
			itemToBeMovedPosSeqNumb = shelfCollectionResourceChildView != null && shelfCollectionResourceChildView.getReorderTxtBox().getText().trim() !=null && !shelfCollectionResourceChildView.getReorderTxtBox().getText().trim().equals("")?(Integer.parseInt(shelfCollectionResourceChildView.getReorderTxtBox().getText().trim())):0;
			if(shelfCollectionResourceChildView!=null){
				reorderValidationMsg = reorderValidations(itemToBeMovedPosSeqNumb,itemPosSeqNumb,DOWN_ARROW);
				
				if(reorderValidationMsg.equalsIgnoreCase(REORDER_VALIDATION_MSG)){
					if(itemToBeMovedPosSeqNumb==itemPosSeqNumb){
						itemToBeMovedPosSeqNumb+=1;
					}
					shelfCollectionResourceChildView.reorderCollectionResources(shelfCollectionResourceChildView,itemToBeMovedPosSeqNumb,DOWN_ARROW);
				}else{
					shelfCollectionResourceChildView.showReorderValidationToolTip(reorderValidationMsg);
				}
			}
		}
	}
	
	
	
	*//**
	 * Before reorder will return with valid message.
	 * @param itemToBeMovedPosSeqNumb {@link Integer}
	 * @param itemPosSeqNumb {@link Integer}
	 * @param arrow {@link String}
	 * @return validationStaus {@link String} 
	 *//*
	public String reorderValidations(int itemToBeMovedPosSeqNumb,int itemPosSeqNumb,String arrow) {
		String validationStaus=REORDER_VALIDATION_MSG; 
		if(itemToBeMovedPosSeqNumb==0){
			validationStaus = i18n.GL3003();
		}else if(itemToBeMovedPosSeqNumb>getTotalCount()){
			validationStaus = StringUtil.generateMessage(i18n.GL3005(),itemToBeMovedPosSeqNumb+"");
		}else if(itemToBeMovedPosSeqNumb==0){
			validationStaus = "Please specify the reorder sequence.";
		}
		return validationStaus;
	}
	

	*//**
	 * Gets the respective folder or collection widget for reorder.
	 * @param itemGooruOid {@link String}
	 * 
	 * @return widget {@link ShelfCollectionResourceChildView}
	 *//*
	public ShelfCollectionResourceChildView getFolderOrCollectionWidget(String itemGooruOid) { 
		Iterator<Widget> widgets = collectionResourcePanelVc.iterator();
		while (widgets.hasNext()) {
			Widget widget = widgets.next();
			if (widget instanceof ShelfCollectionResourceChildView && ((ShelfCollectionResourceChildView) widget).getCollectionItemDo().getGooruOid().equals(itemGooruOid)) {
				return (ShelfCollectionResourceChildView) widget;
			}
		}
		return null;
	}
	

	
	@Override
	public void reorderItemToNewPosition(ShelfCollectionResourceChildView shelfCollectionResourceChildView,Integer newSequence, String arrow) {
		if(arrow.equals(UP_ARROW)){
			collectionResourcePanelVc.insert(shelfCollectionResourceChildView, (newSequence-1));
		}else{
			collectionResourcePanelVc.insert(shelfCollectionResourceChildView, newSequence);
		}
		shelfCollectionResourceChildView.getResourceEditButtonContainer().setVisible(false);
		shelfCollectionResourceChildView.getEditButton().setVisible(false);
		shelfCollectionResourceChildView.getReorderContainer().setVisible(false);
		resetSequence();
		setResourceSequence();
	}
*/	
	
	/**
	 * This not commented for hot fix, not required.
	 */
	/*private void isReorderButtonEnabled(boolean isEnable, ShelfCollectionResourceChildView shelfCollectionResourceChildView) { 
		if(isEnable){
			shelfCollectionResourceChildView.setReorderContainerVisibility(isEnable); 
		}else{
			shelfCollectionResourceChildView.setReorderContainerVisibility(isEnable);
		}
	}*/
	

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

}
