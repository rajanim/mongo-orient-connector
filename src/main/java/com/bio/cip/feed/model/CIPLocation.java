/**
 * 
 */
package com.bio.cip.feed.model;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Piyush.Sharma
 * 
 */
public class CIPLocation {

	private String address = null;
	private String city = null;
	private String state = null;
	private String country = null;
	private String latitude = null;
	private String longitude = null;
	private String zip = null;
	private String timezone = null;
	private String activity = null;

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * @param zip
	 *            the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * @return the timezone
	 */
	public String getTimezone() {
		return timezone;
	}

	/**
	 * @param timezone
	 *            the timezone to set
	 */
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	/**
	 * @return the activity
	 */
	public String getActivity() {
		return activity;
	}

	/**
	 * @param activity
	 *            the activity to set
	 */
	public void setActivity(String activity) {
		this.activity = activity;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((activity == null) ? 0 : activity.hashCode());
		result = (prime * result) + ((address == null) ? 0 : address.hashCode());
		result = (prime * result) + ((city == null) ? 0 : city.hashCode());
		result = (prime * result) + ((country == null) ? 0 : country.hashCode());
		result = (prime * result) + ((latitude == null) ? 0 : latitude.hashCode());
		result = (prime * result) + ((longitude == null) ? 0 : longitude.hashCode());
		result = (prime * result) + ((state == null) ? 0 : state.hashCode());
		result = (prime * result) + ((zip == null) ? 0 : zip.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if ((obj == null) || !(obj instanceof CIPLocation)) {
		} else {
			CIPLocation other = (CIPLocation) obj;
			if (StringUtils.isNotBlank(latitude)
					&& StringUtils.isNotBlank(longitude)
					&& !(StringUtils.equalsIgnoreCase(latitude, String.valueOf(0.0)) && StringUtils.equalsIgnoreCase(
							longitude, String.valueOf(0.0))) && StringUtils.equalsIgnoreCase(latitude, other.latitude)
					&& StringUtils.equalsIgnoreCase(longitude, other.longitude)) {
				return true;
			}
			if (StringUtils.equalsIgnoreCase(address, other.address) && StringUtils.equalsIgnoreCase(city, other.city)
					&& StringUtils.equalsIgnoreCase(country, other.country)
					&& StringUtils.equalsIgnoreCase(state, other.state)) {
				return true;
			}
		}
		return false;
	}

	public boolean isNull() {
		boolean isNull = false;
		if (StringUtils.isBlank(activity) && StringUtils.isBlank(address) && StringUtils.isBlank(city)
				&& StringUtils.isBlank(state) && StringUtils.isBlank(country) && StringUtils.isBlank(latitude)
				&& StringUtils.isBlank(longitude) && StringUtils.isBlank(zip) && StringUtils.isBlank(timezone)) {
			isNull = true;
		}
		return isNull;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CIPLocation [address=" + address + ", city=" + city + ", state=" + state + ", country=" + country
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", zip=" + zip + ", timezone=" + timezone
				+ ", activity=" + activity + "]";
	}
}
