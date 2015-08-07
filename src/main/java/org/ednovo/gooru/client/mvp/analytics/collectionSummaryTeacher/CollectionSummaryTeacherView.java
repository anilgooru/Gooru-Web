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
package org.ednovo.gooru.client.mvp.analytics.collectionSummaryTeacher;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.analytics.CollectionSummaryMetaDataDo;
import org.ednovo.gooru.application.shared.model.analytics.MetaDataDo;
import org.ednovo.gooru.application.shared.model.analytics.OetextDataDO;
import org.ednovo.gooru.application.shared.model.analytics.UserDataDo;
import org.ednovo.gooru.application.shared.model.content.ClasspageItemDo;
import org.ednovo.gooru.client.mvp.analytics.HCBarChart;
import org.ednovo.gooru.client.mvp.analytics.collectionSummary.CollectionSummaryWidget;
import org.ednovo.gooru.client.mvp.analytics.collectionSummaryIndividual.CollectionOverViewWidget;
import org.ednovo.gooru.client.mvp.analytics.util.AnalyticsReactionWidget;
import org.ednovo.gooru.client.mvp.analytics.util.AnalyticsTabContainer;
import org.ednovo.gooru.client.mvp.analytics.util.AnalyticsUtil;
import org.ednovo.gooru.client.mvp.analytics.util.DataView;
import org.ednovo.gooru.client.mvp.analytics.util.Print;
import org.ednovo.gooru.client.mvp.analytics.util.SortTable;
import org.ednovo.gooru.client.mvp.analytics.util.ViewResponsesPopup;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.ajaxloader.client.Properties;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Clear;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.visualizations.Table.Options;
//newly added
public class CollectionSummaryTeacherView  extends BaseViewWithHandlers<CollectionSummaryTeacherUiHandlers> implements IsCollectionSummaryTeacherView,ClientConstants  {

	private static CollectionSummaryTeacherViewUiBinder uiBinder = GWT
			.create(CollectionSummaryTeacherViewUiBinder.class);

	interface CollectionSummaryTeacherViewUiBinder extends
			UiBinder<Widget, CollectionSummaryTeacherView> {
	}
	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField HTMLPanel scoredPrintWidget,printWidget,totalAvgReactionlbl,hiddenChartPnl,tabContainer,teacherScoredData,teacherScoredDatapnl,teacherOpenendedData,teacherResourceBreakdownData,teacherResourceBreakdownDatapnl;
	@UiField ListBox filterDropDown;
	@UiField Label totalTimeSpentlbl,totalViewlbl;
	@UiField Frame downloadFile;

	AnalyticsTabContainer teacherTabContainer;

	CollectionSummaryTeacherCBundle res;
	CollectionSummaryMetaDataDo collectionMetaData;

	final String SCORED="scoredTab",OPENENDED="openendedTab",BREAKDOWN="breakdownTab",PRINT="print";;
	private int collectionProgressCount=1;


	ArrayList<UserDataDo> questionsData=new ArrayList<UserDataDo>();
	ArrayList<UserDataDo> openendedData=new ArrayList<UserDataDo>();

	final List<Integer> questionRowIndex=new ArrayList<Integer>();
	final List<Integer> resourceRowIndex=new ArrayList<Integer>();
	DataView operationsView;
	ViewResponsesPopup popupPanel=null;

	//Used for printing
	Label collectionSummaryText=new Label();
  	Label scoredQuestionHeading=new Label();
	HTMLPanel printScoredData=new HTMLPanel("");
	Label opendedQuestionHeading=new Label();
	HTMLPanel printOpendedData=new HTMLPanel("");
	HTMLPanel printResourceData=new HTMLPanel("");

	CollectionSummaryWidget collectionSummaryWidget=new CollectionSummaryWidget();
	CollectionOverViewWidget collectionOverViewWidget=new CollectionOverViewWidget();

	 String style="";
	/**
	 * Costructor
	 */
	public CollectionSummaryTeacherView() {
		this.res = CollectionSummaryTeacherCBundle.INSTANCE;
		res.css().ensureInjected();
		setWidget(uiBinder.createAndBindUi(this));
		String urlDomain=Window.Location.getProtocol()+"//"+Window.Location.getHost();
		style="<link rel='styleSheet' type='text/css' href='"+urlDomain+"/css/googleVisualization.css'><link href='"+urlDomain+"/css/printAnalytics.css' rel='stylesheet' type='text/css'>";
		setData();
		printWidget.setVisible(false);
		downloadFile.setVisible(false);
	}

	/**
	 * This method is used to hide all the panels
	 */
	void hideAllPanels(){
		teacherScoredDatapnl.setVisible(false);
		teacherOpenendedData.setVisible(false);
		teacherResourceBreakdownDatapnl.setVisible(false);
	}
	/**
	 * This method is used to set default data.
	 */
	void setData(){
		collectionSummaryText.setText(i18n.GL1587());
		collectionSummaryText.getElement().getStyle().setPaddingBottom(15, Unit.PX);
		collectionSummaryText.addStyleName("collectionSummaryText");
		printWidget.add(collectionSummaryText);
		teacherTabContainer=new AnalyticsTabContainer() {
			@Override
			public void onTabClick(String tabClicked) {
				if(SCORED.equalsIgnoreCase(tabClicked)){
					hideAllPanels();
					teacherScoredDatapnl.setVisible(true);
				}else if(OPENENDED.equalsIgnoreCase(tabClicked)){
					hideAllPanels();
					teacherOpenendedData.setVisible(true);
				}else if(BREAKDOWN.equalsIgnoreCase(tabClicked)){
					hideAllPanels();
					teacherResourceBreakdownDatapnl.setVisible(true);
				}else if(PRINT.equalsIgnoreCase(tabClicked)){
					Element printElement=collectionSummaryText.getElement();
					printElement.appendChild(collectionSummaryWidget.getElement());
					printElement.appendChild(scoredQuestionHeading.getElement());
					printElement.appendChild(scoredPrintWidget.getElement());
					printElement.appendChild(opendedQuestionHeading.getElement());
					printElement.appendChild(printOpendedData.getElement());
					printElement.appendChild(collectionOverViewWidget.getElement());
					printElement.appendChild(printResourceData.getElement());
					scoredPrintWidget.setVisible(true);
					Print.it(style,printElement);
					scoredPrintWidget.setVisible(false);
				}else{
					scoredPrintWidget.setVisible(true);
					Element printElement=collectionSummaryText.getElement();
					printElement.appendChild(collectionSummaryWidget.getElement());
					printElement.appendChild(scoredQuestionHeading.getElement());
					printElement.appendChild(scoredPrintWidget.getElement());
					printElement.appendChild(opendedQuestionHeading.getElement());
					printElement.appendChild(printOpendedData.getElement());
					printElement.appendChild(collectionOverViewWidget.getElement());
					printElement.appendChild(printResourceData.getElement());
					getUiHandlers().setHtmltopdf(style.toString().replaceAll("'", "\\\\\"")+printElement.getInnerHTML().toString().replaceAll("\"", "\\\\\""),collectionMetaData.getTitle()!=null?collectionMetaData.getTitle():"");
					scoredPrintWidget.setVisible(false);
				}
			}
		};
		teacherTabContainer.getEmailButton().setVisible(false);
		tabContainer.add(teacherTabContainer);
		filterDropDown.clear();
        filterDropDown.addItem("All", "All");
        filterDropDown.addItem("Questions", "Questions");
        filterDropDown.addItem("Resources", "Resources");
	}
	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.analytics.collectionSummaryTeacher.IsCollectionSummaryTeacherView#setTeacherResourceData(java.util.ArrayList, org.ednovo.gooru.shared.model.analytics.CollectionSummaryMetaDataDo, com.google.gwt.user.client.ui.HTMLPanel)
	 */
	@Override
	public void setTeacherResourceData(ArrayList<UserDataDo> resourcesData,CollectionSummaryMetaDataDo collectionMetaData,HTMLPanel loadingImage) {
		this.collectionMetaData=collectionMetaData;


		teacherTabContainer.clearStyles();
		teacherTabContainer.setScoredQuestionsHilight();
		hideAllPanels();
	    teacherResourceBreakdownDatapnl.setVisible(true);


	    teacherScoredData.clear();
		teacherOpenendedData.clear();
		teacherResourceBreakdownData.clear();
		questionsData.clear();
		openendedData.clear();

		collectionProgressCount=0;
		questionRowIndex.clear();
		resourceRowIndex.clear();
		//Set collection meta data
		if(collectionMetaData != null)
		{
			collectionOverViewWidget.setData(collectionMetaData,true);
			collectionSummaryWidget.setDataAnalyticsData(collectionMetaData, null);
			printWidget.add(collectionSummaryWidget);
			printWidget.add(collectionOverViewWidget);
			totalTimeSpentlbl.setText(getTimeSpent(collectionMetaData.getAvgTimeSpent()));
			totalViewlbl.setText(Integer.toString(collectionMetaData.getViews()));
			totalAvgReactionlbl.clear();
			totalAvgReactionlbl.add(new AnalyticsReactionWidget(collectionMetaData.getAvgReaction()));
		}
		Collections.sort(resourcesData,new Comparator<UserDataDo>() {
        	public int compare(UserDataDo o1, UserDataDo o2) {
        		 Integer obj1 = new Integer(o1.getItemSequence());
				 Integer obj2 = new Integer(o2.getItemSequence());
        	     return obj1.compareTo(obj2);
        	}
        });
        //This is used for segrate data based on the category
        for (UserDataDo userDataDo : resourcesData) {
				if(QUESTION.equalsIgnoreCase( userDataDo.getResourceFormat())){
					if(OE.equalsIgnoreCase(userDataDo.getType())){
						openendedData.add(userDataDo);
					}else{
						questionsData.add(userDataDo);
					}
					questionRowIndex.add(collectionProgressCount);
				}else{
					resourceRowIndex.add(collectionProgressCount);
				}
				collectionProgressCount++;
        }
    	setScoredQuestionsData(questionsData);
    	setQuestionsPrintData(questionsData);
    	setOpenendedQuestionsData(openendedData);
    	setOpenendedQuestionsPrintData(openendedData);
    	setCollectionBreakDown(resourcesData,loadingImage);
    	setCollectionBreakDownPrintData(resourcesData);
	}
	/**
	 * This is used to print opened questions data
	 * @param result
	 */
	void setOpenendedQuestionsPrintData(ArrayList<UserDataDo> result){
        try{
        	printOpendedData.clear();
		 	int totalUserCount=collectionMetaData.getUserCount();
		    DataTable data = DataTable.create();
		    data.addColumn(ColumnType.NUMBER, i18n.GL3259());
	        data.addColumn(ColumnType.STRING, i18n.GL0308());
	        data.addColumn(ColumnType.STRING, i18n.GL3260());
	        data.addColumn(ColumnType.STRING, i18n.GL2084());
	        data.addColumn(ColumnType.STRING, i18n.GL3261());
	        data.addColumn(ColumnType.STRING, i18n.GL3273());
	        data.addRows(result.size());
	        if(result.size()!=0){
	        	   for(int i=0;i<result.size();i++) {
	   	        	data.setCell(i, 0, result.get(i).getItemSequence(), null, getPropertiesCell());

	   	            //Set Question Title
	   	            Label questionTitle=new Label( AnalyticsUtil.html2text(result.get(i).getTitle()!=null?result.get(i).getTitle():""));
	   	            questionTitle.setStyleName(res.css().alignCenterAndBackground());
	   	            questionTitle.addStyleName(res.css().alignLeft());
	   	            data.setValue(i, 1, questionTitle.toString());

	   	            //Set completion
	   	            HTMLPanel completionpnl=new HTMLPanel("");
	   	            Label progressBar=new Label();
	   	            progressBar.setStyleName(res.css().setProgressBar());
	   	            completionpnl.add(progressBar);
	   	            Label incompleteProgressBar=new Label();
	   	            incompleteProgressBar.setStyleName(res.css().setIncorrectProgressBar());
	   	            completionpnl.add(incompleteProgressBar);
	   	            int attemptedCount=result.get(i).getTotalAttemptUserCount();
	   	            float maxAvgVal = ((float) attemptedCount)/((float) totalUserCount);
	   	            progressBar.getElement().getStyle().setWidth(maxAvgVal*100, Unit.PX);
	   	            incompleteProgressBar.getElement().getStyle().setWidth((100-maxAvgVal*100), Unit.PX);
	   	            Label studentTextlbl=new Label(attemptedCount+"/"+totalUserCount+" Students");
	   	            studentTextlbl.setStyleName(res.css().alignCenterAndBackground());
	   	            studentTextlbl.getElement().getStyle().setWidth(100, Unit.PX);
	   	            completionpnl.add(studentTextlbl);
	   	            data.setValue(i, 2, completionpnl.toString());

	   	            //Set time spent
	   	            data.setValue(i, 3,AnalyticsUtil.getTimeStampLabel(result.get(i).getAvgTimeSpent()).toString());

	   	            //Set reactions
	   	            int reaction=result.get(i).getAvgReaction();
	   	            HTMLPanel reactionpnl=new HTMLPanel("");
	   	            reactionpnl.add(new AnalyticsReactionWidget(reaction));
	   	            Label reactioncount=new Label();
	   	            reactionpnl.add(reactioncount);
	   	            reactioncount.setText(reaction+"/5");
	   	            reactioncount.setStyleName(res.css().alignCenterAndBackground());
	   	            data.setValue(i, 4, reactionpnl.toString());

	   	            //set View response label
	   	            Label viewResponselbl=new Label("");
	   	            viewResponselbl.setStyleName(res.css().viewResponseTextOpended());
	   	            data.setValue(i, 5, viewResponselbl.toString());
	   	        }
	        }
	        Options options = Options.create();
	        options.setAllowHtml(true);
	        final Table table = new Table(data, options);
	        table.getElement().setId("opendedData");
	        printOpendedData.add(table);
	        if(result.size()==0){
	        	Label erroeMsg=new Label();
	        	erroeMsg.setStyleName(res.css().displayMessageTextForOEQuestions());
	        	erroeMsg.setText(i18n.GL3264());
	        	printOpendedData.add(erroeMsg);
	        }
	    	//To add OE questions
			opendedQuestionHeading.setText(i18n.GL3266());
			opendedQuestionHeading.getElement().getStyle().setPaddingTop(15, Unit.PX);
			opendedQuestionHeading.getElement().getStyle().setPaddingBottom(20, Unit.PX);
			opendedQuestionHeading.getElement().getStyle().setTextAlign(TextAlign.LEFT);
			printWidget.add(opendedQuestionHeading);
	        printWidget.add(printOpendedData);
	    	printOpendedData.getElement().getStyle().setPaddingBottom(20, Unit.PX);
        }catch(Exception e){
        	AppClientFactory.printSevereLogger("CollectionSummaryTeacherView : setOpenendedQuestionsPrintData : "+e.getMessage());
        }
	}
	/**
	 * This method is used to set opended question data.
	 * @param result
	 */
	void setOpenendedQuestionsData(ArrayList<UserDataDo> result){

	 	int totalUserCount=collectionMetaData.getUserCount();
	    DataTable data = DataTable.create();
	    data.addColumn(ColumnType.NUMBER, i18n.GL3259());
        data.addColumn(ColumnType.STRING, i18n.GL0308());
        data.addColumn(ColumnType.STRING, i18n.GL3260());
        data.addColumn(ColumnType.STRING, i18n.GL2084());
        data.addColumn(ColumnType.STRING, i18n.GL3261());
        data.addColumn(ColumnType.STRING, i18n.GL3273());
        data.addRows(result.size());
        if(result.size()!=0){

        	   for(int i=0;i<result.size();i++) {
   	        	data.setCell(i, 0,result.get(i).getItemSequence(), null, getPropertiesCell());

   	            //Set Question Title
   	            Label questionTitle=new Label( AnalyticsUtil.html2text(result.get(i).getTitle()));
   	            questionTitle.setStyleName(res.css().alignCenterAndBackground());
   	            questionTitle.addStyleName(res.css().alignLeft());
   	            data.setValue(i, 1, questionTitle.toString());

   	            //Set completion
   	            HTMLPanel completionpnl=new HTMLPanel("");
   	            Label progressBar=new Label();
   	            progressBar.setStyleName(res.css().setProgressBar());
   	            completionpnl.add(progressBar);
   	            Label incompleteProgressBar=new Label();
   	            incompleteProgressBar.setStyleName(res.css().setIncorrectProgressBar());
   	            completionpnl.add(incompleteProgressBar);
   	            int attemptedCount=result.get(i).getTotalAttemptUserCount();
   	            float maxAvgVal = ((float) attemptedCount)/((float) totalUserCount);
   	            progressBar.getElement().getStyle().setWidth(maxAvgVal*100, Unit.PX);
   	            incompleteProgressBar.getElement().getStyle().setWidth((100-maxAvgVal*100), Unit.PX);
   	            Label studentTextlbl=new Label(attemptedCount+"/"+totalUserCount+" Students");
   	            studentTextlbl.setStyleName(res.css().alignCenterAndBackground());
   	            studentTextlbl.getElement().getStyle().setWidth(100, Unit.PX);
   	            completionpnl.add(studentTextlbl);
   	            data.setValue(i, 2, completionpnl.toString());

   	            //Set time spent
   	            data.setValue(i, 3, AnalyticsUtil.getTimeStampLabel(result.get(i).getAvgTimeSpent()).toString());

   	            //Set reactions
   	            int reaction=result.get(i).getAvgReaction();
   	            HTMLPanel reactionpnl=new HTMLPanel("");
   	            reactionpnl.add(new AnalyticsReactionWidget(reaction));
   	            Label reactioncount=new Label();
   	            reactionpnl.add(reactioncount);
   	            reactioncount.setText(reaction+"/5");
   	            reactioncount.setStyleName(res.css().alignCenterAndBackground());
   	            data.setValue(i, 4, reactionpnl.toString());

   	            //set View response label
   	            Label viewResponselbl=new Label(i18n.GL2286());
   	            viewResponselbl.setStyleName(res.css().viewResponseTextOpended());
   	            viewResponselbl.getElement().setAttribute("resourceGooruId", result.get(i).getResourceGooruOId());
   	            viewResponselbl.getElement().setAttribute("questionType", result.get(i).getType());
   	            viewResponselbl.getElement().setAttribute("labelType", "View Response");
   	            data.setValue(i, 5, viewResponselbl.toString());
   	        }
        }
        Options options = Options.create();
        options.setAllowHtml(true);
        final Table table = new Table(data, options);
        table.getElement().setId("opendedData");
        teacherOpenendedData.add(table);
        if(result.size()==0){
        	Label erroeMsg=new Label();
        	erroeMsg.setStyleName(res.css().displayMessageTextForOEQuestions());
        	erroeMsg.setText(i18n.GL3118());
        	teacherOpenendedData.add(erroeMsg);
        }
        table.addDomHandler(new ClickOnTableCell(), ClickEvent.getType());
        table.getElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().getStyle().setProperty("width", "98% !important");
	}
	/**
	 * This class is used to handle the click event on the table cell
	 */
	class ClickOnTableCell implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			Element ele=event.getNativeEvent().getEventTarget().cast();
			if(ele.getAttribute("labelType").equalsIgnoreCase("View Response") && !StringUtil.isEmpty(ele.getAttribute("resourceGooruId"))){
				getUiHandlers().setOEtextData(ele.getAttribute("resourceGooruId"),ele.getAttribute("questionType"));
			}
		}
	}
	/**
	 * This method is used to print collection breakdown data
	 * @param result
	 */
	void setCollectionBreakDownPrintData(ArrayList<UserDataDo> result){
		try{
			printResourceData.clear();
			UserDataDo maxAvgValue=Collections.max(result,new Comparator<UserDataDo>() {
	        	public int compare(UserDataDo o1, UserDataDo o2) {
	        		 Long obj1 = new Long(o1.getTimeSpent());
	        	     Long obj2 = new Long(o2.getTimeSpent());
	        	     return obj1.compareTo(obj2);
	        	}
	        });

		    final DataTable data = DataTable.create();
		    data.addColumn(ColumnType.NUMBER, i18n.GL3259());
	        data.addColumn(ColumnType.STRING, i18n.GL3267());
	        data.addColumn(ColumnType.STRING, i18n.GL3182());
	        data.addColumn(ColumnType.STRING, i18n.GL3268());
	        data.addColumn(ColumnType.STRING, i18n.GL0934());
	        data.addColumn(ColumnType.STRING, i18n.GL3261());
	        int rowCount=0,rowVal=0;
	        for(int i=0;i<result.size();i++) {
	        		rowCount=rowCount+1;
	        }
	        data.addRows(rowCount);

	        for(int i=0;i<result.size();i++) {
	        	data.setCell(rowVal, 0, result.get(i).getItemSequence(), null, getPropertiesCell());
	            //set Format
	        	 String  resourceCategory =result.get(i).getResourceFormat()!=null?result.get(i).getResourceFormat().trim():"";
	              String categoryStyle="";
	              if(resourceCategory.equalsIgnoreCase("website") || resourceCategory.equalsIgnoreCase("webpage")){
				      resourceCategory = "webpage";
				      categoryStyle=res.css().category_new_type_webpage();
				  } else if(resourceCategory.equalsIgnoreCase("slide") || resourceCategory.equalsIgnoreCase("image")){
				      resourceCategory = "image";
				      categoryStyle=res.css().category_new_type_image();
				  } else if(resourceCategory.equalsIgnoreCase("handout") || resourceCategory.equalsIgnoreCase("lesson") || resourceCategory.equalsIgnoreCase("textbook")|| resourceCategory.equalsIgnoreCase("text")) {
				      resourceCategory = "text";
				      categoryStyle=res.css().category_new_type_text();
				  }  else if(resourceCategory.equalsIgnoreCase("exam")) {
				      resourceCategory = "webpage";
				      categoryStyle=res.css().category_new_type_webpage();
				  } else if(resourceCategory.equalsIgnoreCase("video")) {
				      resourceCategory = "video";
				      categoryStyle=res.css().category_new_type_video();
				  } else if(resourceCategory.equalsIgnoreCase("interactive")) {
				      resourceCategory = "webpage";
				      categoryStyle=res.css().category_new_type_interactive();
				  }else if(resourceCategory.equalsIgnoreCase("audio")) {
				      resourceCategory = "audio";
				      categoryStyle=res.css().category_new_type_audio();
				  }else{
					  categoryStyle=res.css().category_new_type_other();
				  }
	            Label categorylbl=new Label();
	            categorylbl.addStyleName(categoryStyle);
	            categorylbl.addStyleName(res.css().setMarginAuto());
	            data.setValue(rowVal, 1,categorylbl.toString());

	            //Set Question Title
	            Label questionTitle=new Label(AnalyticsUtil.html2text(result.get(i).getTitle()));
	            questionTitle.setStyleName(res.css().alignCenterAndBackground());
	            questionTitle.addStyleName(res.css().alignLeft());
	            data.setValue(rowVal, 2, questionTitle.toString());

	           //Set time spent
	            HorizontalPanel timeSpentpnl=new HorizontalPanel();
	            timeSpentpnl.add(AnalyticsUtil.getTimeStampLabel(result.get(i).getAvgTimeSpent()));
	            Label progressBar=new Label();
	            progressBar.setStyleName(res.css().setProgressBar());
	            timeSpentpnl.add(progressBar);
	            double maxAvgVal = ((double) result.get(i).getAvgTimeSpent())/((double) maxAvgValue.getTimeSpent());
	            progressBar.getElement().getStyle().setWidth(maxAvgVal*100, Unit.PX);
	            data.setValue(rowVal, 3, timeSpentpnl.toString());

	            //set Views label
	            //HorizontalPanel viewpnl=new HorizontalPanel();
	            Label viewlbl=new Label(Integer.toString(result.get(i).getViews()));
	            viewlbl.setStyleName(res.css().alignCenterAndBackground());
	           /* viewpnl.add(viewlbl);
	            Label viewProgressBar=new Label();
	            viewProgressBar.setStyleName(res.css().setProgressBar());
	            viewpnl.add(viewProgressBar);
	            float maxViewVal = ((float) result.get(i).getViews())/((float) maxViews.getViews());
	            viewProgressBar.getElement().getStyle().setWidth(maxViewVal*100, Unit.PX);*/
	            data.setValue(rowVal, 4, viewlbl.toString());

	            //Set reactions
	            int reaction=result.get(i).getAvgReaction();
	            HTMLPanel reactionpnl=new HTMLPanel("");
	            reactionpnl.add( new AnalyticsReactionWidget(reaction));
	            Label reactioncount=new Label();
	            reactionpnl.add(reactioncount);
	            reactioncount.setText(reaction+"/5");
	            reactioncount.setStyleName(res.css().alignCenterAndBackground());
	            data.setValue(rowVal, 5, reactionpnl.toString());
	            rowVal++;
	        }
	        final Options options = Options.create();
	        options.setAllowHtml(true);
	        Table table = new Table(data, options);
	        printResourceData.add(table);
	        printWidget.add(printResourceData);
		}catch(Exception e){
			AppClientFactory.printSevereLogger("CollectionSummaryTeacherView : setCollectionBreakDownPrintData : "+e.getMessage());
		}
	}
	/**
	 * This method is used to display collection break down data.
	 * @param result
	 * @param loadingImage
	 */
	void setCollectionBreakDown(ArrayList<UserDataDo> result,HTMLPanel loadingImage){


		final int[] primitivesQuestions = AnalyticsUtil.toIntArray(questionRowIndex);
		final int[] primitivesResources = AnalyticsUtil.toIntArray(resourceRowIndex);

			UserDataDo maxAvgValue=Collections.max(result,new Comparator<UserDataDo>() {
	        	public int compare(UserDataDo o1, UserDataDo o2) {
	        		 Long obj1 = new Long(o1.getTimeSpent());
	        	     Long obj2 = new Long(o2.getTimeSpent());
	        	     return obj1.compareTo(obj2);
	        	}
	        });

		    final DataTable data = DataTable.create();
		    data.addColumn(ColumnType.NUMBER, i18n.GL3259());
	        data.addColumn(ColumnType.STRING, i18n.GL3267());
	        data.addColumn(ColumnType.STRING, i18n.GL3182());
	        data.addColumn(ColumnType.STRING, i18n.GL3268());
	        data.addColumn(ColumnType.STRING, i18n.GL0934());
	        data.addColumn(ColumnType.STRING, i18n.GL3261());
	        int rowCount=0,rowVal=0;
	        for(int i=0;i<result.size();i++) {
	        		rowCount=rowCount+1;
	        }
	        data.addRows(rowCount);
	        for(int i=0;i<result.size();i++) {
	        	data.setCell(rowVal, 0,result.get(i).getItemSequence(), null, getPropertiesCell());
	            //set Format
	              String  resourceCategory =result.get(i).getResourceFormat()!=null?result.get(i).getResourceFormat().trim():"";
	              String categoryStyle="";
	              if(resourceCategory.equalsIgnoreCase("website") || resourceCategory.equalsIgnoreCase("webpage")){
				      resourceCategory = "webpage";
				      categoryStyle=res.css().category_new_type_webpage();
				  } else if(resourceCategory.equalsIgnoreCase("slide") || resourceCategory.equalsIgnoreCase("image")){
				      resourceCategory = "image";
				      categoryStyle=res.css().category_new_type_image();
				  } else if(resourceCategory.equalsIgnoreCase("handout") || resourceCategory.equalsIgnoreCase("lesson") || resourceCategory.equalsIgnoreCase("textbook")|| resourceCategory.equalsIgnoreCase("text")) {
				      resourceCategory = "text";
				      categoryStyle=res.css().category_new_type_text();
				  }  else if(resourceCategory.equalsIgnoreCase("exam")) {
				      resourceCategory = "webpage";
				      categoryStyle=res.css().category_new_type_webpage();
				  } else if(resourceCategory.equalsIgnoreCase("video")) {
				      resourceCategory = "video";
				      categoryStyle=res.css().category_new_type_video();
				  } else if(resourceCategory.equalsIgnoreCase("interactive")) {
				      resourceCategory = "webpage";
				      categoryStyle=res.css().category_new_type_interactive();
				  }else if(resourceCategory.equalsIgnoreCase("audio")) {
				      resourceCategory = "audio";
				      categoryStyle=res.css().category_new_type_audio();
				  } else{
					  categoryStyle=res.css().category_new_type_other();
				  }
	            Label categorylbl=new Label();
	            categorylbl.addStyleName(categoryStyle);
	            categorylbl.addStyleName(res.css().setMarginAuto());
	            data.setValue(rowVal, 1,categorylbl.toString());

	            //Set Question Title
	            Label questionTitle=new Label(AnalyticsUtil.html2text(result.get(i).getTitle()!=null?result.get(i).getTitle():""));
	            questionTitle.setStyleName(res.css().alignCenterAndBackground());
	            questionTitle.addStyleName(res.css().alignLeft());
	            data.setValue(rowVal, 2, questionTitle.toString());

	           //Set time spent
	            HorizontalPanel timeSpentpnl=new HorizontalPanel();
	            timeSpentpnl.add(AnalyticsUtil.getTimeStampLabel(result.get(i).getAvgTimeSpent()));
	            Label progressBar=new Label();
	            progressBar.setStyleName(res.css().setProgressBar());
	            timeSpentpnl.add(progressBar);
	            double maxAvgVal = ((double) result.get(i).getAvgTimeSpent())/((double) maxAvgValue.getTimeSpent());
	            progressBar.getElement().getStyle().setWidth(maxAvgVal*100, Unit.PX);
	            data.setValue(rowVal, 3, timeSpentpnl.toString());

	            //set Views label
	            Label viewlbl=new Label(Integer.toString(result.get(i).getViews()));
	            viewlbl.setStyleName(res.css().alignCenterAndBackground());

	            data.setValue(rowVal, 4, viewlbl.toString());

	            //Set reactions
	            int reaction=result.get(i).getAvgReaction();
	            HTMLPanel reactionpnl=new HTMLPanel("");
	            reactionpnl.add( new AnalyticsReactionWidget(reaction));
	            Label reactioncount=new Label();
	            reactionpnl.add(reactioncount);
	            reactioncount.setText(reaction+"/5");
	            reactioncount.setStyleName(res.css().alignCenterAndBackground());
	            data.setValue(rowVal, 5, reactionpnl.toString());
	            rowVal++;
	        }
	        final Options options = Options.create();
	        options.setAllowHtml(true);
	        Table table = new Table(data, options);
	        table.getElement().setId("collectionBreakDown");
	        teacherResourceBreakdownData.add(table);
	        table.getElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().getStyle().setProperty("width", "98% !important");
	        filterDropDown.addChangeHandler(new ChangeHandler() {

				@Override
				public void onChange(ChangeEvent event) {
				teacherResourceBreakdownData.clear();
				int selectedIndex = filterDropDown.getSelectedIndex();
				operationsView = DataView.create(data);
				Table table = new Table(operationsView, options);
				table.setStyleName("collectionProgressTable");
				table.getElement().setId("collectionBreakDown");
				if (selectedIndex == 1) {
					operationsView.hideRows(primitivesResources);
					teacherResourceBreakdownData.add(table);
					if (primitivesQuestions.length == 0) {
						Label erroeMsg = new Label();
						erroeMsg.setStyleName(res.css()
								.displayMessageTextForOEQuestions());
						erroeMsg.setText(i18n.GL3274());
						teacherResourceBreakdownData.add(erroeMsg);
					}
				} else if (selectedIndex == 2) {
					operationsView.hideRows(primitivesQuestions);
					teacherResourceBreakdownData.add(table);
					if (primitivesResources.length == 0) {
						Label erroeMsg = new Label();
						erroeMsg.setStyleName(res.css()
								.displayMessageTextForOEQuestions());
						erroeMsg.setText(i18n.GL3275());
						teacherResourceBreakdownData.add(erroeMsg);
					}
				} else {
					teacherResourceBreakdownData.add(table);
				}
				table.addDomHandler(new ClickOnTableCell(),ClickEvent.getType());
				}
			});
	        loadingImage.setVisible(false);

	}
	/**
	 * This method is used to print scored questions data
	 * @param scoredQuestionsData
	 */
	void setQuestionsPrintData(ArrayList<UserDataDo> scoredQuestionsData){
		scoredPrintWidget.clear();
        final SortTable sortableTable = new SortTable();
        sortableTable.setStyleName(res.css().tableMain());
        sortableTable.getElement().setId("results");
        sortableTable.setBorderWidth(1);
        sortableTable.setCellPadding(4);
        sortableTable.setCellSpacing(1);
        sortableTable.addColumnHeader(i18n.GL3259(), 0);
        sortableTable.addColumnHeader(i18n.GL0308(), 1);
        sortableTable.addColumnHeader(i18n.GL2229(), 2);
        sortableTable.addColumnHeader(i18n.GL3269(), 3);
        sortableTable.addColumnHeader(i18n.GL2084(), 4);
        sortableTable.addColumnHeader(i18n.GL3261(), 5);
        sortableTable.getRowFormatter().addStyleName(0, res.css().tableHeader());
        //To add scored questions
      	scoredQuestionHeading.setText(i18n.GL2282());
      	scoredQuestionHeading.getElement().getStyle().setClear(Clear.BOTH);
      	scoredQuestionHeading.getElement().getStyle().setPaddingTop(15, Unit.PX);
      	scoredQuestionHeading.getElement().getStyle().setPaddingBottom(20, Unit.PX);
      	scoredQuestionHeading.getElement().getStyle().setTextAlign(TextAlign.LEFT);
      	printWidget.add(scoredQuestionHeading);
        scoredPrintWidget.add(sortableTable);
        scoredPrintWidget.getElement().getStyle().setPaddingTop(15, Unit.PX);
        if(scoredQuestionsData.size()!=0){
        	 setSortedData(scoredQuestionsData,sortableTable,true);
        }else{
        	Label erroeMsg=new Label();
        	erroeMsg.setStyleName(res.css().displayMessageTextForScoredQuestions());
        	erroeMsg.setText(i18n.GL3265());
        	scoredPrintWidget.add(erroeMsg);
        }
        scoredPrintWidget.setVisible(false);
	}
	/**
	 * This method is used to display scored data
	 * @param scoredQuestionsData
	 */
	void setScoredQuestionsData(ArrayList<UserDataDo> scoredQuestionsData){
		teacherScoredData.clear();
        final SortTable sortableTable = new SortTable();
        sortableTable.setStyleName(res.css().tableMain());
        sortableTable.getElement().setId("results");
        sortableTable.setBorderWidth(1);
        sortableTable.setCellPadding(4);
        sortableTable.setCellSpacing(1);
        sortableTable.setWidth("800");
        sortableTable.addColumnHeader(i18n.GL3259(),  0);
        sortableTable.addColumnHeader(i18n.GL0308(), 1);
        sortableTable.addColumnHeader(i18n.GL3276(), 2);
        sortableTable.addColumnHeader(i18n.GL3277(), 3);
        sortableTable.addColumnHeader(i18n.GL2084(), 4);
        sortableTable.addColumnHeader(i18n.GL3261(), 5);
        sortableTable.getRowFormatter().addStyleName(0, res.css().tableHeader());
        teacherScoredData.add(sortableTable);
        if(scoredQuestionsData.size()!=0){
        	 setSortedData(scoredQuestionsData,sortableTable,false);
        }else{
        	Label erroeMsg=new Label();
        	erroeMsg.setStyleName(res.css().displayMessageTextForScoredQuestions());
        	erroeMsg.setText(i18n.GL3265());
        	teacherScoredData.add(erroeMsg);
        }
	}
	/**
	 * This method is used to set scored questions data.
	 * @param scoredQuestionsData
	 * @param sortableTable
	 * @param isPrint
	 */
	void setSortedData(ArrayList<UserDataDo> scoredQuestionsData,SortTable sortableTable,boolean isPrint){
		 for(int i=1;i<=scoredQuestionsData.size();i++){
        	 sortableTable.setValue(i, 0,scoredQuestionsData.get(i-1).getItemSequence());
        	 Label questionTitle=new Label(AnalyticsUtil.html2text(scoredQuestionsData.get(i-1).getTitle()!=null?scoredQuestionsData.get(i-1).getTitle():""));
	         questionTitle.setStyleName(res.css().alignCenterAndBackground());
	         questionTitle.addStyleName(res.css().alignLeft());
	         sortableTable.setWidget(i, 1, questionTitle);
             VerticalPanel answerBreakDownpnl=new VerticalPanel();
             if(scoredQuestionsData.get(i-1).getType()!=null){
            	  String getQuestionType=scoredQuestionsData.get(i-1).getType();
                  if(MC.equalsIgnoreCase(getQuestionType) || TF.equalsIgnoreCase(getQuestionType) || TSLASHF.equalsIgnoreCase(getQuestionType)){
                 		if((scoredQuestionsData.get(i-1).getMetaData() !=null) && (scoredQuestionsData.get(i-1).getMetaData().size() != 0)) {
                 			int metaDataSize=scoredQuestionsData.get(i-1).getMetaData().size();
                 			int totalcount=scoredQuestionsData.get(i-1).getAttempts();
                 			for(int j=0;j<metaDataSize;j++){
                 				     MetaDataDo metaData=scoredQuestionsData.get(i-1).getMetaData().get(j);
                 	            	 HorizontalPanel datagrap=new HorizontalPanel();
                 	            	 Label tickmarklbl=new Label();
                 	            	 tickmarklbl.setStyleName(res.css().tickMarkImgCss());
                 	            	 datagrap.add(tickmarklbl);
                 	            	 String questionSequence= AnalyticsUtil.getCharForNumber(metaData.getSequence()-1);
                 	            	 int attemptCount=0;
                 	            	 Label sequenceCharlbl=new Label(questionSequence+")");
                 	            	 sequenceCharlbl.setStyleName(res.css().barGraphCharacter());
                 	            	 datagrap.add(sequenceCharlbl);
                 	            	 if(scoredQuestionsData.get(i-1).getOptions()!=null){
                 	            		 Map<String, Integer> authorObject = scoredQuestionsData.get(i-1).getOptions();
                  	        			 /*JSONValue value = JSONParser.parseStrict(scoredQuestionsData.get(i-1).getOptions().toString());
                  	        			 JSONObject authorObject = value.isObject();*/
   	               	        			 if(authorObject.keySet().size()!=0 && authorObject.get(questionSequence)!=null){
   	               	        				attemptCount = (int)authorObject.get(questionSequence).doubleValue();
   	               	         			 }
                    	             }

                 	            	 Label progressBarlbl=new Label("");
                 	            	 if(metaData.getIsCorrect()==1){
                 	            		tickmarklbl.addStyleName(res.css().tickMarkImg());
                 	            		progressBarlbl.addStyleName(res.css().assignment_quesiton_ans_bar());
                 	            	 }else{
                 	            		progressBarlbl.addStyleName(res.css().wrongSelectStyle());
                 	            	 }
                 	            	 float setWidth=((float)attemptCount/(float)totalcount)*100;
                 	            	 setWidth=(setWidth==0.0?1:setWidth);
                 	            	 progressBarlbl.getElement().getStyle().setWidth(setWidth, Unit.PX);
                 	            	 datagrap.add(progressBarlbl);

                 	            	 Label countlbl=new Label("("+attemptCount+")");
                 	            	 datagrap.add(countlbl);
                 	            	 answerBreakDownpnl.add(datagrap);
                 		}
                  }
             }else if((OE.equalsIgnoreCase(getQuestionType)|| FIB.equalsIgnoreCase(getQuestionType) ||MA.equalsIgnoreCase(getQuestionType)) && !isPrint){
            	 Label viewResponselbl=new Label(i18n.GL2286());
            	 viewResponselbl.getElement().setAttribute("labelType", "View Response");
            	 viewResponselbl.getElement().setAttribute("resourceGooruId", scoredQuestionsData.get(i-1).getResourceGooruOId());
	   	         viewResponselbl.getElement().setAttribute("questionType", scoredQuestionsData.get(i-1).getType());
 	             viewResponselbl.setStyleName(res.css().viewResponseTextOpended());
 	             viewResponselbl.addClickHandler(new ClickOnTableCell());
            	 answerBreakDownpnl.add(viewResponselbl);
             }

             sortableTable.setWidget(i, 3, answerBreakDownpnl);
             sortableTable.setValue(i, 4,AnalyticsUtil.getTimeStampLabel(scoredQuestionsData.get(i-1).getAvgTimeSpent()).getText());
             sortableTable.setWidget(i, 5,new AnalyticsReactionWidget(scoredQuestionsData.get(i-1).getAvgReaction()));

                int[] pieChatValues=new int[4];
	            pieChatValues[0]=scoredQuestionsData.get(i-1).getTotalInCorrectCount();
	            pieChatValues[1]=scoredQuestionsData.get(i-1).getTotalCorrectCount();
	            pieChatValues[2]=scoredQuestionsData.get(i-1).getSkip();
	            pieChatValues[3]=pieChatValues[0]+pieChatValues[1]+pieChatValues[2];

             //set row style
             if ( i % 2 == 0 ){
            	 sortableTable.getRowFormatter().addStyleName(i, res.css().tableRowOdd());
            	 sortableTable.setWidget(i, 2, new HCBarChart().pieChart("#fafafa",pieChatValues,isPrint));
             }else{
            	 sortableTable.getRowFormatter().addStyleName(i, res.css().tableRowEven());
            	 sortableTable.setWidget(i, 2, new HCBarChart().pieChart("#fff",pieChatValues,isPrint));
	            }
	        }
		}
	}
	/**
	 * This method is used to set the cell properties
	 * @return
	 */
	com.google.gwt.visualization.client.Properties getPropertiesCell(){
		  Properties properties=Properties.create();
		  properties.set("style", "text-align:center;");
		  com.google.gwt.visualization.client.Properties p=properties.cast();
		  return p;
	}
	/**
	 * This is used to convert long time format
	 * @param commentCreatedTime
	 * @return
	 */
	private String getTimeSpent(Long commentCreatedTime) {
		String createdTime = null;
		int seconds = (int) (commentCreatedTime / 1000) % 60 ;
		int minutes = (int) ((commentCreatedTime / (1000*60)) % 60);
		int hours   = (int) ((commentCreatedTime / (1000*60*60)) % 24);
		int days = (int) (commentCreatedTime / (1000*60*60*24));
		if(days!=0){
			createdTime=days+":";
		}
		if(hours!=0){
			if(createdTime!=null){
				createdTime=createdTime+hours+"hr ";
			}else{
				createdTime=hours+"hr ";
			}
		}
		if(minutes!=0){
			if(createdTime!=null){
				createdTime=createdTime+((minutes<10)?"0"+minutes+"min":minutes+"min")+" ";
			}else{
				createdTime=((minutes<10)?"0"+minutes+"min":minutes+"min")+" ";
			}
		}
		if(seconds!=0){
			if(createdTime!=null){
				createdTime=createdTime+((seconds<10)?"0"+seconds+"sec":seconds+"sec")+"";
			}else{
				createdTime="0min"+" "+((seconds<10)?"0"+seconds+"sec":seconds+"sec")+"";
			}
		}
		return createdTime;
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.analytics.collectionSummaryTeacher.IsCollectionSummaryTeacherView#setViewResponseData(java.util.ArrayList, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void setViewResponseData(ArrayList<OetextDataDO> result,String resourceGooruId, String collectionId, String classpageId, String pathwayId,String questionType,String session) {
		popupPanel=new ViewResponsesPopup(result,resourceGooruId,collectionId,classpageId,pathwayId,questionType,true,session,new ClasspageItemDo());
		popupPanel.setStyleName(res.css().setOETextPopupCenter());
	     if(popupPanel.isShowing()){
	    	 popupPanel.hide();
	    	 Window.enableScrolling(true);
	     }else{
	    	 Window.enableScrolling(false);
	    	 popupPanel.setGlassEnabled(true);
	    	 popupPanel.setAutoHideEnabled(true);
	    	 popupPanel.show();
	    	 popupPanel.center();
	     }
	}
	@Override
	public Frame getFrame() {
		return downloadFile;
	}
}
