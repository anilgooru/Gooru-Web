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

package org.ednovo.gooru.application.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.AppInjector;
import org.ednovo.gooru.application.shared.model.user.UserDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.user.client.Window;
import com.gwtplatform.mvp.client.DelayedBindRegistry;
/**
 *
 * @fileName : GooruEntry.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 06-Dec-2013
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class AppEntry implements EntryPoint {

	private final AppInjector appInjector = GWT.create(AppInjector.class);

	public AppEntry(){

	}


	public void onModuleLoad() {

		Logger logger=Logger.getLogger(GWT.getModuleName());

		/**
		 * Capturing all uncaught exception on client side.
		 */
/*		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {

			@Override
			public void onUncaughtException(Throwable e) {
//				Throwable unwrapped = getExceptionToDisplay(e);
//				AppClientFactory.printSevereLogger("Exception Caught !! "+unwrapped.getMessage());
			}
		});
*/

		try {
			DelayedBindRegistry.bind(appInjector);
			AppClientFactory.setAppGinjector(appInjector);

			appInjector.getAppService().getLoggedInUser(new SimpleAsyncCallback<UserDo>() {
				@Override
				public void onSuccess(UserDo loggedInUser) {
					AppClientFactory.setLoggedInUser(loggedInUser);
					appInjector.getPlaceManager().revealCurrentPlace();
					AppClientFactory.setProtocol(getHttpOrHttpsProtocol());
					appInjector.getWrapPresenter().get().setLoginData(loggedInUser);
				}
			});

		} catch (Exception e) {
			logger.log(Level.SEVERE,"onModuleLoad : "+e.getMessage(),e);
		}
	}

	public String getHttpOrHttpsProtocol() {
		return Window.Location.getProtocol();
	}

	/**
	 *  Method used to unwrap GWT umbrella exception.
	 *
	 * @param e {@link Throwable}
	 * @return {@link Throwable}
	 */
	public Throwable getExceptionToDisplay(Throwable e) {
	    if(e instanceof UmbrellaException) {
	      UmbrellaException ue = (UmbrellaException) e;
	      if(ue.getCauses().size() == 1) {
	        return getExceptionToDisplay(ue.getCauses().iterator().next());
	      }
	    }
	    return e;
	  }
}
