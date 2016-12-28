package com.ruifios.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.Id;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

/**
 * 
 * @author dysec
 *
 */
public class Dao extends HibernateDaoSupport {

	private final Logger logger = Logger.getLogger(Dao.class);
	
	private Pattern locked;

	public <T> long getCount(Class<T> t) {
		Session session = null;
		try {
			session = getSessionFactory().openSession();
			String hql = "select count(id) from " + t.getSimpleName();
			Query query = session.createQuery(hql);
			long size = (long) query.list().get(0);
			return size;
		} catch (Exception e) {
			this.logger.warn("", e);
		} finally {
			try {
				session.close();
			} catch (Exception e1) {
				this.logger.warn("", e1);
			}
		}
		return 0L;
	}

	public <T> long getMaxId(Class<T> t, String condition) {
		Session session = null;
		try {
			session = getSessionFactory().openSession();

			String hql = null;

			if ((condition == null) || (condition.equals(""))) {
				hql = "select max(id) from " + t.getSimpleName();
			} else {
				hql = "select max(id) from " + t.getSimpleName() + " " + condition;
			}

			Query query = session.createQuery(hql);
			if (query.list().size() > 0) {
				long re = (long) query.list().get(0);
				
				return re;
			}
			return -1L;
		} catch (Exception e) {
			this.logger.warn("", e);
			return -1L;
		} finally {
			try {
				session.close();
			} catch (Exception e1) {
				this.logger.warn("", e1);
			}
		}
	}

	public <T> long getMinId(Class<T> t, String condition) {
		Session session = null;
		try {
			session = getSessionFactory().openSession();

			String hql = null;

			if ((condition == null) || (condition.equals(""))) {
				hql = "select min(id) from " + t.getSimpleName();
			} else {
				hql = "select min(id) from " + t.getSimpleName() + " " + condition;
			}

			Query query = session.createQuery(hql);
			if (query.list().size() > 0) {
				long re = ((Long) query.list().get(0)).longValue();
				long l1 = re;
				return l1;
			}
			return -1L;
		} catch (Exception e) {
			this.logger.warn("", e);
			return -1L;
		} finally {
			try {
				session.close();
			} catch (Exception e1) {
				this.logger.warn("", e1);
			}
		}
	}

	public <T> long getCount(Class<T> t, String condition) {
		Session session = null;
		try {
			session = getSessionFactory().openSession();

			String hql = "select count(id) from " + t.getSimpleName() + " " + condition;
			Query query = session.createQuery(hql);
			long size = ((Long) query.list().get(0)).longValue();
			return size;
		} catch (Exception e) {
			this.logger.warn("", e);
			return 0L;
		} finally {
			try {
				session.close();
			} catch (Exception e1) {
				this.logger.warn("", e1);
			}
		}
	}

	public <T> List<T> query(String hql) {
		Session session = null;
	
		try {
			session = getSessionFactory().openSession();
			Query query = session.createQuery(hql);
			@SuppressWarnings("unchecked")
			List<T> list = query.list();
			return list;
		} catch (Exception e) {
			this.logger.warn("", e);
			return new ArrayList<T>();
		} finally {
			try {
				session.close();
			} catch (Exception e1) {
				this.logger.warn("", e1);
			}
		}
	}

	public <T> List<T> query(String hql, int currentPage, int pageSize) {
		Session session = null;
		
		try {
			session = getSessionFactory().openSession();
			Query query = session.createQuery(hql);
			query.setFirstResult((currentPage - 1) * pageSize);
			query.setMaxResults(pageSize);
			@SuppressWarnings("unchecked")
			List<T> list = query.list();
			return list;
		} catch (Exception e) {
			this.logger.warn("", e);
			return new ArrayList<T>();
		} finally {
			try {
				session.close();
			} catch (Exception e1) {
				this.logger.warn("", e1);
			}
		}
	}

	public <T> T queryById(Class<T> clazz, Long id) {
		Session session = null;
		try {
			session = getSessionFactory().openSession();

			Query query = session.createQuery("from " + clazz.getSimpleName() + " where id=" + id);
			@SuppressWarnings("unchecked")
			List<T> list = query.list();
			if ((list != null) && (list.size() > 0)) {
				return list.get(0);
			}
			return null;
		} catch (Exception e) {
			this.logger.warn("", e);
			return null;
		} finally {
			try {
				session.close();
			} catch (Exception e1) {
				this.logger.warn("", e1);
			}
		}
	}

	public <T> T queryById(Class<T> clazz, String id) {
		Session session = null;
		try {
			session = getSessionFactory().openSession();

			Query query = session.createQuery("from " + clazz.getSimpleName() + " where id='" + id + "'");
			@SuppressWarnings("unchecked")
			List<T> list = query.list();
			if ((list != null) && (list.size() > 0)) {
				return list.get(0);
			}
		} catch (Exception e) {
			this.logger.warn("", e);
			return null;
		} finally {
			try {
				session.close();
			} catch (Exception e1) {
				this.logger.warn("", e1);
			}
		}
		try {
			session.close();
		} catch (Exception e1) {
			this.logger.warn("", e1);
		}

		return null;
	}

	public <T> List<T> query(Class<T> clazz) {
		Session session = null;
		
		try {
			session = getSessionFactory().openSession();
			Query query = session.createQuery("from " + clazz.getSimpleName());
			@SuppressWarnings("unchecked")
			List<T> list = (List<T>)query.list();
			return list;
		} catch (Exception e) {
			this.logger.warn("", e);
			return new ArrayList<T>();
		} finally {
			try {
				session.close();
			} catch (Exception e1) {
				this.logger.warn("", e1);
			}
		}
	}

	public <T> List<T> queryOrderBy(Class<T> clazz, String orderby) {
		Session session = null;
		
		try {
			session = getSessionFactory().openSession();
			Query query = session.createQuery("from " + clazz.getSimpleName() + " " + orderby);
			@SuppressWarnings("unchecked")
			List<T> list = query.list();
			return list;
		} catch (Exception e) {
			this.logger.warn("", e);
			return new ArrayList<T>();
		} finally {
			try {
				session.close();
			} catch (Exception e1) {
				this.logger.warn("", e1);
			}
		}
	}

	public <T> void insert(T t) {
		Session session = null;
		try {
			session = getSessionFactory().openSession();
			Transaction tran = session.beginTransaction();
			session.save(t);
			session.flush();
			tran.commit();
		} catch (Exception e) {
			this.logger.warn("", e);
		} finally {
			try {
				session.close();
			} catch (Exception e1) {
				this.logger.warn("", e1);
			}
		}
	}

	public <T> void batchInsert(List<T> list) {
		Session session = getSessionFactory().openSession();
		try {
			Transaction tx = session.beginTransaction();

			int i = 0;
			for (Object t : list) {
				session.save(t);
				if (i % 20 == 0) {
					session.flush();
					session.clear();
				}

				++i;
			}

			session.flush();
			session.clear();

			tx.commit();
		} catch (Exception e) {
			this.logger.warn("", e);
		} finally {
			try {
				session.close();
			} catch (Exception e1) {
				this.logger.warn("", e1);
			}
		}
	}

	public <T> void update(T t) {
		Session session = null;
		try {
			session = getSessionFactory().openSession();
			Transaction tran = session.beginTransaction();
			session.update(t);
			session.flush();
			tran.commit();
		} catch (Exception e) {
			this.logger.warn("", e);
		} finally {
			try {
				session.close();
			} catch (Exception e1) {
				this.logger.warn("", e1);
			}
		}
	}

	public <T> void update(T t, String locked) {
		Session session = null;
		try {
			if (locked != null)
	            this.locked = Pattern.compile(locked);
			
			session = getSessionFactory().openSession();
			Transaction tran = session.beginTransaction();
			
			StringBuffer sb = new StringBuffer();
			sb.append("update ").append(t.getClass().getSimpleName()).append(" t ");
			String id = bulidUpdateHql(t, sb);
			String hql = sb.toString() + " where t.id = :id";
			
			Query query = session.createQuery(hql).setString("id", id);  
	        query.executeUpdate();  
	        
			session.flush();
			tran.commit();
		} catch (Exception e) {
			this.logger.warn("", e);
		} finally {
			try {
				session.close();
			} catch (Exception e1) {
				this.logger.warn("", e1);
			}
		}
	}

	public <T> String bulidUpdateHql(T t, StringBuffer sb){
		String id = null;
		try {
			Field[] list = t.getClass().getDeclaredFields();
			boolean first = true;
			for(Field field: list){
				Id hid = field.getAnnotation(Id.class);
				String name = field.getName();
				if(hid != null || "id".equals(name)){
					field.setAccessible(true);
					id = String.valueOf(field.get(t));
				}else if(!isLocked(field)){
					// 私有变量必须先设置Accessible为true
					field.setAccessible(true);
					String value = String.valueOf(field.get(t));
					if(first){
						sb.append(" set t.").append(name).append(" = '").append(value).append("' ");
						first = false;
					}else{
						sb.append(" , t.").append(name).append(" = '").append(value).append("' ");
					}
				}
			}	
			logger.info(sb.toString());
		} catch (Exception e) {
			logger.warn("", e);
		}
		return id;
	}
	
	public boolean isLocked(Field field) {
		try {
			if (null != locked && locked.matcher(field.getName()).find()) {
			    return true;
			}
			if (!((Class<?>)field.get(null)).isPrimitive())
				return true;
		} catch (IllegalArgumentException | IllegalAccessException e) {
			logger.warn("", e);
		}
		return false;
	}
	
	public <T> void delete(Class<T> clazz, long id) {
		Session session = null;
		try {
			session = getSessionFactory().openSession();

			String hql = "delete " + clazz.getSimpleName() + " c where c.id = :id";
			session.createQuery(hql).setLong("id", id).executeUpdate();
			session.flush();
		} catch (Exception e) {
			this.logger.warn("", e);
		} finally {
			try {
				session.close();
			} catch (Exception e1) {
				this.logger.warn("", e1);
			}
		}
	}

	public <T> void delete(Class<T> clazz, String id) {
		Session session = null;
		try {
			session = getSessionFactory().openSession();

			String hql = "delete " + clazz.getSimpleName() + " c where c.id = :id";
			session.createQuery(hql).setString("id", id).executeUpdate();
			session.flush();
		} catch (Exception e) {
			this.logger.warn("", e);
		} finally {
			try {
				session.close();
			} catch (Exception e1) {
				this.logger.warn("", e1);
			}
		}
	}

	public <T> void delete(String hql) {
		Session session = null;
		try {
			session = getSessionFactory().openSession();

			session.createQuery(hql).executeUpdate();
			session.flush();
		} catch (Exception e) {
			this.logger.warn("", e);
		} finally {
			try {
				session.close();
			} catch (Exception e1) {
				this.logger.warn("", e1);
			}
		}
	}

	public <T> void delete(Class<T> clazz) {
		Session session = null;
		try {
			session = getSessionFactory().openSession();
			Transaction tran = session.beginTransaction();
			String hql = "delete " + clazz.getSimpleName();
			session.createQuery(hql).executeUpdate();
			session.flush();
			tran.commit();
		} catch (Exception e) {
			this.logger.warn("", e);
		} finally {
			try {
				session.close();
			} catch (Exception e1) {
				this.logger.warn("", e1);
			}
		}
	}
	
}
