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
/**
 * 
 */
package org.ednovo.gooru.client.mvp.classpage.teach.reports.lesson;

import java.util.ArrayList;

import org.ednovo.gooru.application.client.child.ChildPresenter;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.classpages.MasterReportDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;

/**
 * @author Search Team
 * 
 */
public class TeachLessonReportChildPresenter extends ChildPresenter<TeachLessonReportChildPresenter, IsTeachLessonReportView> implements TeachLessonReportPresenterUiHandlers{

	public TeachLessonReportChildPresenter(IsTeachLessonReportView childView) {
		super(childView);
	}
	
	@Override
	public void getLessonMasteryData(String classId, String courseId, String unitId, String lessonId, String assessmentId, String collectionType) {
		AppClientFactory.getInjector().getClasspageService().getCollectionMasteryReport(classId, courseId, unitId, lessonId, assessmentId, collectionType, new SimpleAsyncCallback<ArrayList<MasterReportDo>>() {
			@Override
			public void onSuccess(ArrayList<MasterReportDo> result) {
				getView().setCollectionMasterytData(result);
			}
		});
	}


}