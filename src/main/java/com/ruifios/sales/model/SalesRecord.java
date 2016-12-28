package com.ruifios.sales.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ruifios.server.RuifiosEnv;

/**
 * 销售记录
 * @author dysec
 *
 */
@Entity
@Table(name="t_salesrecord", catalog=RuifiosEnv.DB_NAME)
public class SalesRecord extends BaseSales implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	/**
	 * 商品名称	commodityname	Varchar	必须	商品名称
	 */
	@Column(name="commodityname")
	private String commodityname;

	/**
	 * 购买数量	number	Int	必须	购买数量
	 */
	@Column(name="number")
	private Integer number;
	
	/**
	 * 消费日期	consumptiontime	Long	必须	商品卖出日期
	 */
	@Column(name="consumptiontime")
	private Long consumptiontime;
	
	/**
	 * 原价	originalcost	Double	必须	商品定价
	 */
	@Column(name="originalcost")
	private Double originalcost;
	
	/**
	 * 商家售价	sellingprice	Double	必须	商家售价
	 */
	@Column(name="sellingprice")
	private Double sellingprice;
	
	/**
	 * 让利额度	transferprice	Double	必须	让利额度
	 */
	@Column(name="transferprice")
	private Double transferprice;
	
	/**
	 * 政府补贴	subsidyprice	Double	必须	政府补贴
	 */
	@Column(name="subsidyprice")
	private Double subsidyprice;
	
	/**
	 * 实际支付	actualpayment	Double	必须	实际支付
	 */
	@Column(name="actualpayment")
	private Double actualpayment;
	
	public SalesRecord() {
		super();
	}

	public SalesRecord newSalesRecord(BaseSales basesales) {
		this.invoiceno = basesales.getInvoiceno();
		this.consumername = basesales.getConsumername();
		this.consumercard = basesales.getConsumercard();
		this.consumerphone = basesales.getConsumerphone();
		this.merchantname = basesales.getMerchantname();
		
		return this;
	}

	public SalesRecord(long id, String commodityname, Integer number, Long consumptiontime, Double originalcost, Double sellingprice, Double transferprice, Double subsidyprice, Double actualpayment) {
		super();
		this.id = id;
		this.commodityname = commodityname;
		this.number = number;
		this.consumptiontime = consumptiontime;
		this.originalcost = originalcost;
		this.sellingprice = sellingprice;
		this.transferprice = transferprice;
		this.subsidyprice = subsidyprice;
		this.actualpayment = actualpayment;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCommodityname() {
		return commodityname;
	}

	public void setCommodityname(String commodityname) {
		this.commodityname = commodityname;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Long getConsumptiontime() {
		return consumptiontime;
	}

	public void setConsumptiontime(Long consumptiontime) {
		this.consumptiontime = consumptiontime;
	}

	public Double getOriginalcost() {
		return originalcost;
	}

	public void setOriginalcost(Double originalcost) {
		this.originalcost = originalcost;
	}

	public Double getSellingprice() {
		return sellingprice;
	}

	public void setSellingprice(Double sellingprice) {
		this.sellingprice = sellingprice;
	}

	public Double getTransferprice() {
		return transferprice;
	}

	public void setTransferprice(Double transferprice) {
		this.transferprice = transferprice;
	}

	public Double getSubsidyprice() {
		return subsidyprice;
	}

	public void setSubsidyprice(Double subsidyprice) {
		this.subsidyprice = subsidyprice;
	}

	public Double getActualpayment() {
		return actualpayment;
	}

	public void setActualpayment(Double actualpayment) {
		this.actualpayment = actualpayment;
	}

}


