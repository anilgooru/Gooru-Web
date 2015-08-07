package org.ednovo.gooru.client.mvp.analytics.util;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public abstract class AnalyticsTabContainer extends Composite implements ClientConstants{

	private static AnalyticsTabContainerUiBinder uiBinder = GWT
			.create(AnalyticsTabContainerUiBinder.class);

	interface AnalyticsTabContainerUiBinder extends
			UiBinder<Widget, AnalyticsTabContainer> {
	}
	
	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField Button btnScoredQuestions,btnOpenEndedQuestions,btnCollectionBreakDown,btnPtint,btnSave,btnEmail;
	
	/**
	 * Constructor
	 */
	public AnalyticsTabContainer() {
		initWidget(uiBinder.createAndBindUi(this));
		btnScoredQuestions.addClickHandler(new ClickImplemntation(SCORED));
		btnOpenEndedQuestions.addClickHandler(new ClickImplemntation(OPENENDED));
		btnCollectionBreakDown.addClickHandler(new ClickImplemntation(BREAKDOWN));
		btnPtint.addClickHandler(new ClickImplemntation(PRINT));
		btnEmail.addClickHandler(new ClickImplemntation(EMAIL));
		btnSave.addClickHandler(new ClickImplemntation(SAVEBTN));
		StringUtil.setAttributes(btnPtint.getElement(), "btnPtint", i18n.GL3284(), i18n.GL3284());
		StringUtil.setAttributes(btnEmail.getElement(), "btnPtint", "print", "Email");
		StringUtil.setAttributes(btnSave.getElement(), "btnSave", i18n.GL0141(), i18n.GL0141());
	}
	/**
	 * This method is used to clear the highlight styles
	 */
	public void clearStyles(){
		btnScoredQuestions.removeStyleName("addButonStyleActive");
		btnOpenEndedQuestions.removeStyleName("addButonStyleActive");
		btnCollectionBreakDown.removeStyleName("addButonStyleActive");
	}
	/**
	 * This method is used to highlight scored questions tab.
	 */
	public void setScoredQuestionsHilight(){
		btnCollectionBreakDown.addStyleName("addButonStyleActive");
	}
	public class ClickImplemntation implements ClickHandler{
		private String tabClicked;
		ClickImplemntation(String tabClicked){
			this.tabClicked=tabClicked;
		}
		@Override
		public void onClick(ClickEvent event) {
			if(tabClicked.equalsIgnoreCase(SCORED)){
				clearStyles();
				btnScoredQuestions.addStyleName("addButonStyleActive");
			}else if(tabClicked.equalsIgnoreCase(OPENENDED)){
				clearStyles();
				btnOpenEndedQuestions.addStyleName("addButonStyleActive");
			}else if(tabClicked.equalsIgnoreCase(BREAKDOWN)){
				clearStyles();
				btnCollectionBreakDown.addStyleName("addButonStyleActive");
			}
			onTabClick(tabClicked);
		}
	}
	/**
	 * This will return the email Button
	 * @return
	 */
	public Button getEmailButton(){
		return btnEmail;
	}
	/**
	 * This abastact method is used to handle the click event on clicked button
	 * @param tabClicked
	 */
	public abstract void onTabClick(String tabClicked);
}
