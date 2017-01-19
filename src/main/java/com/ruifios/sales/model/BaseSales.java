package com.ruifios.sales.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import com.ruifios.commons.BaseTimedObject;

/**
 * 基础销售信息
 */
@MappedSuperclass
public class BaseSales extends BaseTimedObject {

	private static final long serialVersionUID = -8281280437325993085L;

	/**
	 * 发票编码	invoiceno	Varchar	必须	发票号
	 */
	@Column(name="invoiceno")
	protected String invoiceno = "发票编码";

	/**
	 * 消费者姓名	consumername	Varchar	必须	消费者姓名
	 */
	@NotNull
	@Column(name="consumername")
	protected String consumername;

	/**
	 * 消费者身份证号	consumercard	Varchar	必须	消费者身份证号
	 */
	@NotNull
	@Column(name="consumercard", length=20)
	protected String consumercard;

	/**
	 * 消费者手机号	consumerphone	Varchar	--	消费者手机号
	 */
	@Column(name="consumerphone")
	protected String consumerphone;

	/**
	 * 商家名称	merchantname	Varchar	必须	商家名称
	 */
	@NotNull
	@Column(name="merchantname")
	protected String merchantname;

	public BaseSales() {
		super();
	}

	public BaseSales(String invoiceno, String consumername, String consumercard, String consumerphone, String merchantname) {
		super();
		this.invoiceno = invoiceno;
		this.consumername = consumername;
		this.consumercard = consumercard;
		this.consumerphone = consumerphone;
		this.merchantname = merchantname;
	}

	public String getInvoiceno() {
		return invoiceno;
	}

	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}

	public String getConsumername() {
		return consumername;
	}

	public void setConsumername(String consumername) {
		this.consumername = consumername;
	}

	public String getConsumercard() {
		return consumercard;
	}

	public void setConsumercard(String consumercard) {
		this.consumercard = consumercard;
	}

	public String getConsumerphone() {
		return consumerphone;
	}

	public void setConsumerphone(String consumerphone) {
		this.consumerphone = consumerphone;
	}

	public String getMerchantname() {
		return merchantname;
	}

	public void setMerchantname(String merchantname) {
		this.merchantname = merchantname;
	}

}
