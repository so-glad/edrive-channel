package so.glad.channel.edrive.entity;

import com.google.common.base.Objects;

public class MediaAttr {

	private String streamKind;

	private String name;

	private String value;

	public String getStreamKind() {
		return streamKind;
	}

	public void setStreamKind(String streamKind) {
		this.streamKind = streamKind;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof MediaAttr)) return false;
		MediaAttr mediaAttr = (MediaAttr) o;
		return Objects.equal(streamKind, mediaAttr.streamKind) &&
				Objects.equal(name, mediaAttr.name) &&
				Objects.equal(value, mediaAttr.value);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(streamKind, name, value);
	}
}
