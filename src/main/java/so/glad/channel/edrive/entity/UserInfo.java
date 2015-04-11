package so.glad.channel.edrive.entity;

import com.google.common.base.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class UserInfo extends Root{

	private String loginName;

	private Long capacity;

	private Long available;

	private Long maxFilesize;

	private Long mailUsedSize;

	private Long orderAmount;

	private Long provinceCode;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public long getCapacity() {
		return capacity;
	}

	public void setCapacity(long capacity) {
		this.capacity = capacity;
	}

	public long getAvailable() {
		return available;
	}

	public void setAvailable(long available) {
		this.available = available;
	}

	public Long getMaxFilesize() {
		return maxFilesize;
	}

	public void setMaxFilesize(Long maxFilesize) {
		this.maxFilesize = maxFilesize;
	}

	public Long getMailUsedSize() {
		return mailUsedSize;
	}

	public void setMailUsedSize(Long mailUsedSize) {
		this.mailUsedSize = mailUsedSize;
	}

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Long getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(Long provinceCode) {
		this.provinceCode = provinceCode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof UserInfo)) return false;
		UserInfo userInfo = (UserInfo) o;
		return Objects.equal(capacity, userInfo.capacity) &&
				Objects.equal(available, userInfo.available) &&
				Objects.equal(maxFilesize, userInfo.maxFilesize) &&
				Objects.equal(mailUsedSize, userInfo.mailUsedSize) &&
				Objects.equal(orderAmount, userInfo.orderAmount) &&
				Objects.equal(provinceCode, userInfo.provinceCode) &&
				Objects.equal(loginName, userInfo.loginName);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(loginName, capacity, available, maxFilesize, mailUsedSize, orderAmount, provinceCode);
	}
}
