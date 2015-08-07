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
package org.ednovo.gooru.client.mvp.home;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.uc.AppPopUp;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 */
public class ResetTimeoutVc extends PopupPanel{
	
	private AppPopUp appPopUp;
	
	@UiField
	Label forgotPwdLbl,newPwdLbl,timeOutText,requestNewText;
	
	private static ResetTimeoutVcUiBinder uiBinder = GWT
			.create(ResetTimeoutVcUiBinder.class);

	interface ResetTimeoutVcUiBinder extends UiBinder<Widget, ResetTimeoutVc> {
	}
	
	 private MessageProperties i18n = GWT.create(MessageProperties.class);

	/**
	 * Class constructor , create password reset token expired popup 
	 */
	public ResetTimeoutVc() {
		setWidget(uiBinder.createAndBindUi(this));
		appPopUp = new AppPopUp();
		appPopUp.setStyleName("removeResourcePopup");
		appPopUp.setContent(i18n.GL0062(), uiBinder.createAndBindUi(this));
		appPopUp.show();
		appPopUp.center();
		newPwdLbl.setText(i18n.GL1254());
		newPwdLbl.getElement().setId("lblNewPwdLbl");
		newPwdLbl.getElement().setAttribute("alt",i18n.GL1254());
		newPwdLbl.getElement().setAttribute("title",i18n.GL1254());
		
		timeOutText.setText(i18n.GL1257());
		timeOutText.getElement().setId("lblTimeOutText");
		timeOutText.getElement().setAttribute("alt",i18n.GL1257());
		timeOutText.getElement().setAttribute("title",i18n.GL1257());
		
		requestNewText.setText(i18n.GL1258());
		requestNewText.getElement().setId("lblRequestNewText");
		requestNewText.getElement().setAttribute("alt",i18n.GL1258());
		requestNewText.getElement().setAttribute("title",i18n.GL1258());
		
		forgotPwdLbl.setText(i18n.GL1259());
		forgotPwdLbl.getElement().setId("lblForgotPwdLbl");
		forgotPwdLbl.getElement().setAttribute("alt",i18n.GL1259());
		forgotPwdLbl.getElement().setAttribute("title",i18n.GL1259());
		
		forgotPwdLbl.addClickHandler(new OnResetPasswordVc());
	}
	
	/**
	 * @author Search Team
	 * Takes to forgot password popup if ok is clicked in {@link ResetTimeoutVc} popup
	 *
	 */
	public class OnResetPasswordVc implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			appPopUp.hide();
			new ForgotPasswordVc();
		}
		
	}

}
