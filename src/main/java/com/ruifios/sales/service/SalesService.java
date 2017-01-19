package com.ruifios.sales.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ruifios.auth.model.User;
import com.ruifios.commons.Pager;
import com.ruifios.dao.IdRecordHelper;
import com.ruifios.sales.model.BaseSales;
import com.ruifios.sales.model.SalesRecord;
import com.ruifios.sales.model.ShopsInfo;
import com.ruifios.server.RuifiosEnv;

/**
 * 销售记录业务处理
 * @author ch
 *
 */
@Service(SalesService.IOC_NAME)
public class SalesService {

	public final static String IOC_NAME = "SalesService";
	
	public final static float discount = Float.parseFloat(RuifiosEnv.getMessage("discount"));

	public final static double totalrebate = Double.parseDouble(RuifiosEnv.getMessage("rebate.total"));
	
	private static Logger logger = Logger.getLogger(SalesService.class);
	
	/**
	 * 保存销售记录，并筛选出商家商品信息保存
	 * @param record
	 */
	public void addSalesRecord(BaseSales base, List<SalesRecord> records){
		String levelcode = "/";
		int size = records.size();
		long merchantid = 0l;
		ShopsInfo shop = getShopsInfo(base.getMerchantname());
		if(shop != null){// 商家存在
			//merchantid = shop.getId();
			levelcode = shop.getLevelcode();
		} else {// 添加商家信息
			merchantid = IdRecordHelper.getTblMaxIdWithUpdate(ShopsInfo.class, size);
			levelcode += merchantid;
			ShopsInfo merchant = new ShopsInfo();
			merchant.setId(merchantid);
			merchant.setName(base.getMerchantname());
			merchant.setLevelcode(levelcode);
			RuifiosEnv.dao.insert(merchant);
		}
	
		// 商品信息
		List<ShopsInfo> goods = new ArrayList<ShopsInfo>();
		// 添加商品销售信息
		List<SalesRecord> list = new ArrayList<SalesRecord>();
		// 个人消费总金额
		double consum = getSalesSum(base)*(1/discount);
		for(SalesRecord record: records){
			record.newSalesRecord(base);
			// 计算返利
			double total = record.getSellingprice()*record.getNumber();
			double transferprice = 0;// 让利额度
			double subsidyprice = 0;// 政府补贴
			double actualpayment = total;// 实际支付
			if(consum<totalrebate){// 之前总金额小于500
				if((consum + total)<totalrebate){
					transferprice = subsidyprice = total*discount;
				}else{
					transferprice = subsidyprice = (totalrebate - consum)*discount;
				}
				consum += total;
			}
			actualpayment = total - transferprice;
			record.setTransferprice(transferprice);
			record.setSubsidyprice(subsidyprice);
			record.setActualpayment(actualpayment);
			
			list.add(record);
			
			String name = record.getCommodityname();
			if(merchantid > 0){
				long goodid = ++merchantid;
				ShopsInfo good = new ShopsInfo();
				good.setId(goodid);
				good.setName(name);
				good.setLevelcode(levelcode+"/"+goodid);
				goods.add(good);
			}else{
				ShopsInfo good = getShopsInfo(name);
				if(good == null){
					long goodid = IdRecordHelper.getTblMaxIdWithUpdate(ShopsInfo.class);
					good = new ShopsInfo();
					good.setId(goodid);
					good.setName(name);
					good.setLevelcode(levelcode+"/"+goodid);
					goods.add(good);
				}
			}
		}
		RuifiosEnv.dao.batchInsert(list);
		RuifiosEnv.dao.batchInsert(goods);
	}
	
	/**
	 * 获取销售记录
	 * @param pager
	 * @param condition
	 * @return
	 */
	public Pager<SalesRecord> getSalesRecord(Pager<SalesRecord> pager, String condition){
		return RuifiosEnv.dao.query(SalesRecord.class, pager, condition);
	}

	/**
	 * 获取用户消费总金额
	 *  若用户不存在，添加用户信息
	 *  若用户存在，计算消费总金额
	 * @param base
	 */
	public double getSalesSum(BaseSales base){
		double consum = 0.0;
		try {
			List<User> users = RuifiosEnv.dao.query(User.class, " where name = '"+base.getConsumercard()+"'");
			if(users == null || users.size()==0){
				User user = new User();
				user.setName(base.getConsumercard());
				user.setRealName(base.getConsumername());
				user.setPhone(base.getConsumerphone());
				user.setRole("weixin");
				
				RuifiosEnv.dao.insert(user);
			}else{
				consum = getSalesSum(base.getConsumercard());
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return consum;
	}
	
	/**
	 * 获取用户消费总金额
	 * @param consumercard
	 * @return
	 */
	public double getSalesSum(String consumercard){
		double consum = 0.0;
		try {
			String sql = "select sum(actualpayment) from t_salesrecord where consumercard = '"+consumercard+"'"; 
			List<Object> list = RuifiosEnv.dao.SQLQuery(sql);
			if(list != null && list.size()>0){
				if(list.get(0) != null)
					consum = Double.parseDouble(String.valueOf(list.get(0)));
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return consum;
	}
	
	/**
	 * 获取商品信息
	 * @param goodname
	 * @return
	 */
	public ShopsInfo getShopsInfo(String name){
		List<ShopsInfo> shopsinfos = RuifiosEnv.dao.query(ShopsInfo.class, " where name = '"+name+"'");
		if(shopsinfos != null && shopsinfos.size()>0){
			return shopsinfos.get(0);
		}
		return null;
	}
	
}
