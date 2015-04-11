package so.glad.channel.edrive.entity;

import com.google.common.base.Objects;

/**
 * Description: 
 * @author Palmtale
 * @change 
 *	 2015-03-27 created
 */
public class Icon {

	private String smallUrl;

	private String mediumUrl;

	private String largeUrl;

	private String max600;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Icon)) return false;
		Icon icon = (Icon) o;
		return Objects.equal(smallUrl, icon.smallUrl) &&
				Objects.equal(mediumUrl, icon.mediumUrl) &&
				Objects.equal(largeUrl, icon.largeUrl) &&
				Objects.equal(max600, icon.max600);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(smallUrl, mediumUrl, largeUrl, max600);
	}

	public String getSmallUrl() {

		return smallUrl;
	}

	public void setSmallUrl(String smallUrl) {
		this.smallUrl = smallUrl;
	}

	public String getMediumUrl() {
		return mediumUrl;
	}

	public void setMediumUrl(String mediumUrl) {
		this.mediumUrl = mediumUrl;
	}

	public String getLargeUrl() {
		return largeUrl;
	}

	public void setLargeUrl(String largeUrl) {
		this.largeUrl = largeUrl;
	}

	public String getMax600() {
		return max600;
	}

	public void setMax600(String max600) {
		this.max600 = max600;
	}
}
