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

package org.ednovo.gooru.client.mvp.gsearch.resource;

import org.ednovo.gooru.client.mvp.gsearch.SearchAbstractView;
import org.ednovo.gooru.client.mvp.search.util.CollectionResourceWidget;
import org.ednovo.gooru.client.mvp.search.util.CollectionSearchWidget;
import org.ednovo.gooru.shared.model.search.CollectionSearchResultDo;
import org.ednovo.gooru.shared.model.search.ResourceSearchResultDo;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

/**
 * @fileName : SearchResourceView.java
 * 
 * @description :
 * 
 * @version : 1.3
 * 
 * @date: 10-04-2015
 * 
 * @Author Gooru Team
 * 
 * @Reviewer:
 */
public class SearchResourceView extends
		SearchAbstractView<ResourceSearchResultDo> implements
		IsSearchResourceView {


	public SearchResourceView() {
		super(false);
	}
	/**
	 * To render Collection search results.
	 * 
	 * @return collectionSearchWidget{@link Widget}
	 */
	@Override
	public Widget renderSearchResult(final ResourceSearchResultDo resourceSearchResultDo) {
		CollectionResourceWidget collectionResourceWidget=new CollectionResourceWidget(resourceSearchResultDo);
		collectionResourceWidget.getAddResoruce().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Element element = Document.get().getElementById("fixedFilterSearchID");
				if(element!=null)
				{
				element.setAttribute("style", "opacity:0.1;");
				}
				Window.enableScrolling(false);
				getUiHandlers().displayAddResourcePoup(resourceSearchResultDo);
			}
		});
		return collectionResourceWidget;
	}
}