package com.xuie.androiddemo.bean;

import com.google.gson.annotations.Expose;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Shot extends RealmObject {

    @Expose @PrimaryKey private Integer id;
    @Expose String title;
    @Expose String description;
    @Expose Integer width;
    @Expose Integer height;
    @Expose Images images;
    @Expose Integer viewsCount;
    @Expose Integer likesCount;
    @Expose Integer commentsCount;
    @Expose Integer attachmentsCount;
    @Expose Integer reboundsCount;
    @Expose Integer bucketsCount;
    @Expose String createdAt;
    @Expose String updatedAt;
    @Expose String htmlUrl;
    @Expose String attachmentsUrl;
    @Expose String bucketsUrl;
    @Expose String commentsUrl;
    @Expose String likesUrl;
    @Expose String projectsUrl;
    @Expose String reboundsUrl;
    @Expose Boolean animated;
    @Expose User user;
    @Expose Team team = new Team();

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The width
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * @param width The width
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * @return The height
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * @param height The height
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * @return The images
     */
    public Images getImages() {
        return images;
    }

    /**
     * @param images The images
     */
    public void setImages(Images images) {
        this.images = images;
    }

    /**
     * @return The viewsCount
     */
    public Integer getViewsCount() {
        return viewsCount;
    }

    /**
     * @param viewsCount The views_count
     */
    public void setViewsCount(Integer viewsCount) {
        this.viewsCount = viewsCount;
    }

    /**
     * @return The likesCount
     */
    public Integer getLikesCount() {
        return likesCount;
    }

    /**
     * @param likesCount The likes_count
     */
    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    /**
     * @return The commentsCount
     */
    public Integer getCommentsCount() {
        return commentsCount;
    }

    /**
     * @param commentsCount The comments_count
     */
    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    /**
     * @return The attachmentsCount
     */
    public Integer getAttachmentsCount() {
        return attachmentsCount;
    }

    /**
     * @param attachmentsCount The attachments_count
     */
    public void setAttachmentsCount(Integer attachmentsCount) {
        this.attachmentsCount = attachmentsCount;
    }

    /**
     * @return The reboundsCount
     */
    public Integer getReboundsCount() {
        return reboundsCount;
    }

    /**
     * @param reboundsCount The rebounds_count
     */
    public void setReboundsCount(Integer reboundsCount) {
        this.reboundsCount = reboundsCount;
    }

    /**
     * @return The bucketsCount
     */
    public Integer getBucketsCount() {
        return bucketsCount;
    }

    /**
     * @param bucketsCount The buckets_count
     */
    public void setBucketsCount(Integer bucketsCount) {
        this.bucketsCount = bucketsCount;
    }

    /**
     * @return The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @return The htmlUrl
     */
    public String getHtmlUrl() {
        return htmlUrl;
    }

    /**
     * @param htmlUrl The html_url
     */
    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    /**
     * @return The attachmentsUrl
     */
    public String getAttachmentsUrl() {
        return attachmentsUrl;
    }

    /**
     * @param attachmentsUrl The attachments_url
     */
    public void setAttachmentsUrl(String attachmentsUrl) {
        this.attachmentsUrl = attachmentsUrl;
    }

    /**
     * @return The bucketsUrl
     */
    public String getBucketsUrl() {
        return bucketsUrl;
    }

    /**
     * @param bucketsUrl The buckets_url
     */
    public void setBucketsUrl(String bucketsUrl) {
        this.bucketsUrl = bucketsUrl;
    }

    /**
     * @return The commentsUrl
     */
    public String getCommentsUrl() {
        return commentsUrl;
    }

    /**
     * @param commentsUrl The comments_url
     */
    public void setCommentsUrl(String commentsUrl) {
        this.commentsUrl = commentsUrl;
    }

    /**
     * @return The likesUrl
     */
    public String getLikesUrl() {
        return likesUrl;
    }

    /**
     * @param likesUrl The likes_url
     */
    public void setLikesUrl(String likesUrl) {
        this.likesUrl = likesUrl;
    }

    /**
     * @return The projectsUrl
     */
    public String getProjectsUrl() {
        return projectsUrl;
    }

    /**
     * @param projectsUrl The projects_url
     */
    public void setProjectsUrl(String projectsUrl) {
        this.projectsUrl = projectsUrl;
    }

    /**
     * @return The reboundsUrl
     */
    public String getReboundsUrl() {
        return reboundsUrl;
    }

    /**
     * @param reboundsUrl The rebounds_url
     */
    public void setReboundsUrl(String reboundsUrl) {
        this.reboundsUrl = reboundsUrl;
    }

    /**
     * @return The animated
     */
    public Boolean getAnimated() {
        return animated;
    }

    /**
     * @param animated The animated
     */
    public void setAnimated(Boolean animated) {
        this.animated = animated;
    }

 /*   *//**
     *
     * @return
     *     The tags
     *//*
    public List<String> getTags() {
        return tags;
    }

    *//**
     *
     * @param tags
     *     The tags
     *//*
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
*/

    /**
     * @return The user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return The team
     */
    public Team getTeam() {
        return team;
    }

    /**
     * @param team The team
     */
    public void setTeam(Team team) {
        this.team = team;
    }

    @Override public String toString() {
        return "Shot{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", images=" + images +
                ", viewsCount=" + viewsCount +
                ", likesCount=" + likesCount +
                ", commentsCount=" + commentsCount +
                ", attachmentsCount=" + attachmentsCount +
                ", reboundsCount=" + reboundsCount +
                ", bucketsCount=" + bucketsCount +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", htmlUrl='" + htmlUrl + '\'' +
                ", attachmentsUrl='" + attachmentsUrl + '\'' +
                ", bucketsUrl='" + bucketsUrl + '\'' +
                ", commentsUrl='" + commentsUrl + '\'' +
                ", likesUrl='" + likesUrl + '\'' +
                ", projectsUrl='" + projectsUrl + '\'' +
                ", reboundsUrl='" + reboundsUrl + '\'' +
                ", animated=" + animated +
                ", user=" + user +
                ", team=" + team +
                '}';
    }
}
