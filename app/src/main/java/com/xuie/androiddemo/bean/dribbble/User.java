package com.xuie.androiddemo.bean.dribbble;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {

    @PrimaryKey Integer id;
    String name;
    String username;
    String htmlUrl;
    String avatarUrl;
    String bio;
    String location;
    Links links;
    Integer bucketsCount;
    Integer commentsReceivedCount;
    Integer followersCount;
    Integer followingsCount;
    Integer likesCount;
    Integer likesReceivedCount;
    Integer projectsCount;
    Integer reboundsReceivedCount;
    Integer shotsCount;
    Integer teamsCount;
    Boolean canUploadShot;
    String type;
    Boolean pro;
    String bucketsUrl;
    String followersUrl;
    String followingUrl;
    String likesUrl;
    String projectsUrl;
    String shotsUrl;
    String teamsUrl;
    String createdAt;
    String updatedAt;

    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

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
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username The username
     */
    public void setUsername(String username) {
        this.username = username;
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
     * @return The avatarUrl
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * @param avatarUrl The avatar_url
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    /**
     * @return The bio
     */
    public String getBio() {
        return bio;
    }

    /**
     * @param bio The bio
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * @return The location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location The location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return The links
     */
    public Links getLinks() {
        return links;
    }

    /**
     * @param links The links
     */
    public void setLinks(Links links) {
        this.links = links;
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
     * @return The commentsReceivedCount
     */
    public Integer getCommentsReceivedCount() {
        return commentsReceivedCount;
    }

    /**
     * @param commentsReceivedCount The comments_received_count
     */
    public void setCommentsReceivedCount(Integer commentsReceivedCount) {
        this.commentsReceivedCount = commentsReceivedCount;
    }

    /**
     * @return The followersCount
     */
    public Integer getFollowersCount() {
        return followersCount;
    }

    /**
     * @param followersCount The followers_count
     */
    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }

    /**
     * @return The followingsCount
     */
    public Integer getFollowingsCount() {
        return followingsCount;
    }

    /**
     * @param followingsCount The followings_count
     */
    public void setFollowingsCount(Integer followingsCount) {
        this.followingsCount = followingsCount;
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
     * @return The likesReceivedCount
     */
    public Integer getLikesReceivedCount() {
        return likesReceivedCount;
    }

    /**
     * @param likesReceivedCount The likes_received_count
     */
    public void setLikesReceivedCount(Integer likesReceivedCount) {
        this.likesReceivedCount = likesReceivedCount;
    }

    /**
     * @return The projectsCount
     */
    public Integer getProjectsCount() {
        return projectsCount;
    }

    /**
     * @param projectsCount The projects_count
     */
    public void setProjectsCount(Integer projectsCount) {
        this.projectsCount = projectsCount;
    }

    /**
     * @return The reboundsReceivedCount
     */
    public Integer getReboundsReceivedCount() {
        return reboundsReceivedCount;
    }

    /**
     * @param reboundsReceivedCount The rebounds_received_count
     */
    public void setReboundsReceivedCount(Integer reboundsReceivedCount) {
        this.reboundsReceivedCount = reboundsReceivedCount;
    }

    /**
     * @return The shotsCount
     */
    public Integer getShotsCount() {
        return shotsCount;
    }

    /**
     * @param shotsCount The shots_count
     */
    public void setShotsCount(Integer shotsCount) {
        this.shotsCount = shotsCount;
    }

    /**
     * @return The teamsCount
     */
    public Integer getTeamsCount() {
        return teamsCount;
    }

    /**
     * @param teamsCount The teams_count
     */
    public void setTeamsCount(Integer teamsCount) {
        this.teamsCount = teamsCount;
    }

    /**
     * @return The canUploadShot
     */
    public Boolean getCanUploadShot() {
        return canUploadShot;
    }

    /**
     * @param canUploadShot The can_upload_shot
     */
    public void setCanUploadShot(Boolean canUploadShot) {
        this.canUploadShot = canUploadShot;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The pro
     */
    public Boolean getPro() {
        return pro;
    }

    /**
     * @param pro The pro
     */
    public void setPro(Boolean pro) {
        this.pro = pro;
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
     * @return The followersUrl
     */
    public String getFollowersUrl() {
        return followersUrl;
    }

    /**
     * @param followersUrl The followers_url
     */
    public void setFollowersUrl(String followersUrl) {
        this.followersUrl = followersUrl;
    }

    /**
     * @return The followingUrl
     */
    public String getFollowingUrl() {
        return followingUrl;
    }

    /**
     * @param followingUrl The following_url
     */
    public void setFollowingUrl(String followingUrl) {
        this.followingUrl = followingUrl;
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
     * @return The shotsUrl
     */
    public String getShotsUrl() {
        return shotsUrl;
    }

    /**
     * @param shotsUrl The shots_url
     */
    public void setShotsUrl(String shotsUrl) {
        this.shotsUrl = shotsUrl;
    }

    /**
     * @return The teamsUrl
     */
    public String getTeamsUrl() {
        return teamsUrl;
    }

    /**
     * @param teamsUrl The teams_url
     */
    public void setTeamsUrl(String teamsUrl) {
        this.teamsUrl = teamsUrl;
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

    @Override public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", htmlUrl='" + htmlUrl + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", bio='" + bio + '\'' +
                ", location='" + location + '\'' +
                ", links=" + links +
                ", bucketsCount=" + bucketsCount +
                ", commentsReceivedCount=" + commentsReceivedCount +
                ", followersCount=" + followersCount +
                ", followingsCount=" + followingsCount +
                ", likesCount=" + likesCount +
                ", likesReceivedCount=" + likesReceivedCount +
                ", projectsCount=" + projectsCount +
                ", reboundsReceivedCount=" + reboundsReceivedCount +
                ", shotsCount=" + shotsCount +
                ", teamsCount=" + teamsCount +
                ", canUploadShot=" + canUploadShot +
                ", type='" + type + '\'' +
                ", pro=" + pro +
                ", bucketsUrl='" + bucketsUrl + '\'' +
                ", followersUrl='" + followersUrl + '\'' +
                ", followingUrl='" + followingUrl + '\'' +
                ", likesUrl='" + likesUrl + '\'' +
                ", projectsUrl='" + projectsUrl + '\'' +
                ", shotsUrl='" + shotsUrl + '\'' +
                ", teamsUrl='" + teamsUrl + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}