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
package org.ednovo.gooru.application.shared.model.analytics;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;
/**
 * @fileName : CollectionSummaryMetaDataDo.java
 *
 *
 * @Author :Gooru Team
 *
 * @Reviewer:
 */
@JsonInclude(Include.NON_NULL)
public class CollectionSummaryMetaDataDo implements IsSerializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String completionStatus;
	private int userCount;
	private long avgTimeSpent;
	private String title;
	private int avgReaction;
	private String thumbnail;
	private long lastModified;
	private long lastAccessed;
	private String gooruOId;
	private int views;
	private int score;
	private int gradeInPercentage;
	private int totalQuestionCount;
	private long timeSpent;
	private int resourceCount;
	private int nonResourceCount;
	private String evidence;
	private int scorableQuestionCount;
	private int goal;
	private String resourceType;
	private String username;
	private String profileUrl;
	private String questionCount;
	private int scoreInPercentage;
	private String oeCount;
	private int selectedSessionScorableQuestionCount;
	private ArrayList<session> session;

	public int getUserCount() {
		return userCount;
	}
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}
	public long getAvgTimeSpent() {
		return avgTimeSpent;
	}
	public void setAvgTimeSpent(long avgTimeSpent) {
		this.avgTimeSpent = avgTimeSpent;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getAvgReaction() {
		return avgReaction;
	}
	public void setAvgReaction(int avgReaction) {
		this.avgReaction = avgReaction;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public long getLastModified() {
		return lastModified;
	}
	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}
	public String getGooruOId() {
		return gooruOId;
	}
	public void setGooruOId(String gooruOId) {
		this.gooruOId = gooruOId;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getGradeInPercentage() {
		return gradeInPercentage;
	}
	public void setGradeInPercentage(int gradeInPercentage) {
		this.gradeInPercentage = gradeInPercentage;
	}
	public int getTotalQuestionCount() {
		return totalQuestionCount;
	}
	public void setTotalQuestionCount(int totalQuestionCount) {
		this.totalQuestionCount = totalQuestionCount;
	}
	public long getTimeSpent() {
		return timeSpent;
	}
	public void setTimeSpent(long timeSpent) {
		this.timeSpent = timeSpent;
	}
	public String getCompletionStatus() {
		return completionStatus;
	}
	public void setCompletionStatus(String completionStatus) {
		this.completionStatus = completionStatus;
	}
	public int getResourceCount() {
		return resourceCount;
	}
	public void setResourceCount(int resourceCount) {
		this.resourceCount = resourceCount;
	}
	public int getNonResourceCount() {
		return nonResourceCount;
	}
	public void setNonResourceCount(int nonResourceCount) {
		this.nonResourceCount = nonResourceCount;
	}
	public long getLastAccessed() {
		return lastAccessed;
	}
	public void setLastAccessed(long lastAccessed) {
		this.lastAccessed = lastAccessed;
	}
	public String getEvidence() {
		return evidence;
	}
	public int getScorableQuestionCount() {
		return scorableQuestionCount;
	}
	public int getGoal() {
		return goal;
	}
	public String getResourceType() {
		return resourceType;
	}
	public String getUsername() {
		return username;
	}
	public String getProfileUrl() {
		return profileUrl;
	}
	public String getQuestionCount() {
		return questionCount;
	}
	public int getScoreInPercentage() {
		return scoreInPercentage;
	}
	public String getOeCount() {
		return oeCount;
	}
	public void setEvidence(String evidence) {
		this.evidence = evidence;
	}
	public void setScorableQuestionCount(int scorableQuestionCount) {
		this.scorableQuestionCount = scorableQuestionCount;
	}
	public void setGoal(int goal) {
		this.goal = goal;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}
	public void setQuestionCount(String questionCount) {
		this.questionCount = questionCount;
	}
	public void setScoreInPercentage(int scoreInPercentage) {
		this.scoreInPercentage = scoreInPercentage;
	}
	public void setOeCount(String oeCount) {
		this.oeCount = oeCount;
	}
	public ArrayList<session> getSession() {
		return session;
	}
	public void setSession(ArrayList<session> session) {
		this.session = session;
	}
	/**
	 * This method is to get the selectedSessionScorableQuestionCount
	 */
	public int getSelectedSessionScorableQuestionCount() {
		return selectedSessionScorableQuestionCount;
	}
	/**
	 * This method is to set the selectedSessionScorableQuestionCount
	 */
	public void setSelectedSessionScorableQuestionCount(
			int selectedSessionScorableQuestionCount) {
		this.selectedSessionScorableQuestionCount = selectedSessionScorableQuestionCount;
	}


}
