package org.ednovo.gooru.client.uc;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.InsertPanel;
import com.google.gwt.user.client.ui.Widget;

public class StrongPanel extends ComplexPanel implements InsertPanel {
	String text;
	public StrongPanel() {
		setElement(Document.get().createElement("strong"));
    }
	
	@Override
	public void add(Widget w){
	add(w,getElement());
	}

	@Override
	public void insert(Widget w, int beforeIndex) {
		insert(w, getElement(), beforeIndex, true);
	}
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		getElement().setInnerText(text);
	}



}
