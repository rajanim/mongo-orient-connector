package com.bio.cip.constants;

public class QueryConstants {

	// Orient DB Queries
	//select out('posted_by')[0].@rid as userRid, out('posted_by')[0].userName as userName, out('posted_by')[0].profileUrl as profileUrl, out('posted_by')[0].profileImageUrl as profileImageUrl, @rid as contentRid, content_id, content, time, source, contentURL from Content where @rid >
	public static final String QRY_CONTENT = "select out('posted_by')[0].@rid as userRid, out('posted_by')[0].userName as userName,out('posted_by')[0].profileName as profileName, out('posted_by')[0].profileUrl as profileUrl, out('posted_by')[0].profileImageUrl as profileImageUrl, @rid as contentRid, content_id, content, time, source, contentURL from Content where @rid > ?";

	public static final String QRY_EVENT = "select from ExtractedEvent where @rid > ?";

	public static final String QRY_PERSON = "select out('lives_at')[0].address as address, @rid, source, profileName, userId, userName,profileImageUrl from Person where @rid > ?";

	public static final String QRY_LOCATION = "select from ExtractedLocation where @rid > ?";

	public static final String QRY_ORGANIZATION = "select from ExtractedOrganization where @rid > ?";

}
