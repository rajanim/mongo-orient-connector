/**
 * 
 */
package com.bio.cip.feed.model;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Piyush.Sharma
 * 
 */
public class CIPUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5882141303893998614L;

	private String userId = null;
	private String userName = null;
	private String firstName = null;
	private String middleName = null;
	private String lastName = null;
	private String profileName = null;
	private String language = null;
	private String profileUrl = null;

	private String description = null;
	private String profileImageUrl = null;
	private Integer friendsCount = null;
	private Integer followersCount = null;
	private String profileCreatedAt = null;
	private String profileCoverImageUrl = null;
	private String kloutScore = null;
	private CIPLocation location = null;
	private Set<CIPUser> followers = null;
	private Set<CIPUser> friends = null;
	private Boolean isVerified = null;

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		if (StringUtils.isBlank(userName)) {
			StringBuilder nameBuff = new StringBuilder();
			if (StringUtils.isNotBlank(firstName)) {
				nameBuff.append(firstName);
				if (StringUtils.isNotBlank(lastName)) {
					nameBuff.append(" ").append(lastName);
				}
				String name = nameBuff.toString();
				if (StringUtils.isNotBlank(name)) {
					userName = name;
				}
			}
		}
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName
	 *            the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the profileName
	 */
	public String getProfileName() {
		return profileName;
	}

	/**
	 * @param profileName
	 *            the profileName to set
	 */
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the profileImageUrl
	 */
	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	/**
	 * @param profileImageUrl
	 *            the profileImageUrl to set
	 */
	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	/**
	 * @return the friendsCount
	 */
	public Integer getFriendsCount() {
		return friendsCount;
	}

	/**
	 * @param friendsCount
	 *            the friendsCount to set
	 */
	public void setFriendsCount(Integer friendsCount) {
		this.friendsCount = friendsCount;
	}

	/**
	 * @return the followersCount
	 */
	public Integer getFollowersCount() {
		return followersCount;
	}

	/**
	 * @param followersCount
	 *            the followersCount to set
	 */
	public void setFollowersCount(Integer followersCount) {
		this.followersCount = followersCount;
	}

	/**
	 * @return the profileCreatedAt
	 */
	public String getProfileCreatedAt() {
		return profileCreatedAt;
	}

	/**
	 * @param profileCreatedAt
	 *            the profileCreatedAt to set
	 */
	public void setProfileCreatedAt(String profileCreatedAt) {
		this.profileCreatedAt = profileCreatedAt;
	}

	/**
	 * @return the profileCoverImageUrl
	 */
	public String getProfileCoverImageUrl() {
		return profileCoverImageUrl;
	}

	/**
	 * @param profileCoverImageUrl
	 *            the profileCoverImageUrl to set
	 */
	public void setProfileCoverImageUrl(String profileCoverImageUrl) {
		this.profileCoverImageUrl = profileCoverImageUrl;
	}

	/**
	 * @return the location
	 */
	public CIPLocation getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(CIPLocation location) {
		this.location = location;
	}

	/**
	 * 
	 * @param geoLoc
	 *            the location to set
	 */
	public void setGeoLocation(String geoLoc) {
		CIPLocation loc = new CIPLocation();
		loc.setAddress(geoLoc);
		location = loc;
	}

	/**
	 * @return the followers
	 */
	public Set<CIPUser> getFollowers() {
		return followers;
	}

	/**
	 * @param followers
	 *            the followers to set
	 */
	public void setFollowers(Set<CIPUser> followers) {
		this.followers = followers;
	}

	/**
	 * @return the friends
	 */
	public Set<CIPUser> getFriends() {
		return friends;
	}

	/**
	 * @param friends
	 *            the friends to set
	 */
	public void setFriends(Set<CIPUser> friends) {
		this.friends = friends;
	}

	public String getKloutScore() {
		return kloutScore;
	}

	public void setKloutScore(String kloutScore) {
		this.kloutScore = kloutScore;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new Double(userId).intValue();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		boolean equal = false;
		if ((obj instanceof CIPUser) && (obj.hashCode() == hashCode())) {
			equal = true;
		}
		return equal;
	}

	/**
	 * @return the isVerified
	 */
	public Boolean isVerified() {
		return isVerified;
	}

	/**
	 * @param isVerified
	 *            the isVerified to set
	 */
	public void setVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CIPUser [userId=" + userId + ", userName=" + userName + ", firstName=" + firstName + ", middleName="
				+ middleName + ", lastName=" + lastName + ", profileName=" + profileName + ", language=" + language
				+ ", profileUrl=" + profileUrl + ", description=" + description + ", profileImageUrl="
				+ profileImageUrl + ", friendsCount=" + friendsCount + ", followersCount=" + followersCount
				+ ", profileCreatedAt=" + profileCreatedAt + ", profileCoverImageUrl=" + profileCoverImageUrl
				+ ", kloutScore=" + kloutScore + ", location=" + location + ", followers=" + followers + ", friends="
				+ friends + ", isVerified=" + isVerified + "]";
	}

}
