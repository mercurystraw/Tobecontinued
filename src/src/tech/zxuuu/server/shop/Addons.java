package src.tech.zxuuu.server.shop;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import tech.zxuuu.dao.IProductMapper;
import tech.zxuuu.dao.IStudentMapper;
import tech.zxuuu.entity.Product;
import tech.zxuuu.server.main.App;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class Addons {
	private static final Logger logger = LoggerFactory.getLogger(Addons.class);

	public static Boolean insertNewProduct(Product product) {
		Boolean result = false; // 初始值设为 false

		try (SqlSession sqlSession = App.sqlSessionFactory.openSession()) {
			IProductMapper productMapper = sqlSession.getMapper(IProductMapper.class);
			result = productMapper.insertNewProcut(product);
			if (result) {
				sqlSession.commit();
				System.out.println("Product inserted successfully: " + product);
			} else {
				sqlSession.rollback();
				System.out.println("Product insertion failed: " + product);
			}
		} catch (Exception e) {
			System.out.println("Error inserting product: " + e.getMessage());
			result = false; // 确保 result 在异常情况下为 false
		}

		return result;
	}


	public static Boolean deleteProduct(Product product) {
		Boolean result = null;

		SqlSession sqlSession = null;
		try {
			sqlSession = App.sqlSessionFactory.openSession();
			IProductMapper productMapper = sqlSession.getMapper(IProductMapper.class);
			result = productMapper.deleteProduct(product);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			sqlSession.rollback();
			e.printStackTrace();
		}
		return result;
	}

	public static Integer checkout(String cardNumber, String se) {
		if (se.length() < 3) {
			return -1; // invalid request
		}
		Integer result = 0;
		// tokenize and parse
		Float totalMoney = (float) 0;
		Map<String, Integer> remainChecker = new HashMap<>();
		String[] L1 = se.split("\\$");
		for (String ele : L1) {
			String[] L2 = ele.split("@");
			remainChecker.put(L2[0], Integer.parseInt(L2[1]));
			totalMoney += (Integer.parseInt(L2[1]) * Float.parseFloat(L2[2]));
		}
		SqlSession sqlSession = null;
		try {
			sqlSession = App.sqlSessionFactory.openSession();
			// balance check
			IStudentMapper studentMapper = sqlSession.getMapper(IStudentMapper.class);
			Float currentBalance = Float.parseFloat(studentMapper.getBalance(cardNumber));
			if (currentBalance < totalMoney) {
				return 1; // balance not sufficient
			}
			// storage check
			IProductMapper productMapper = sqlSession.getMapper(IProductMapper.class);
			for (String key : remainChecker.keySet()) {
				Integer currentRemain = Integer.parseInt(productMapper.getProductStorage(key));
				if (currentRemain < remainChecker.get(key)) {
					return 2; // storage not sufficient
				}
			}
			// buy, update `tb_product`
			for (String key : remainChecker.keySet()) {
				Map<String, String> map = new HashMap<>();
				map.put("howmany", String.valueOf(remainChecker.get(key)));
				map.put("name", key);
				productMapper.buySomething(map);
			}
			// buy, update student balance
			Map<String, String> map = new HashMap<>();
			map.put("money", String.valueOf(-1 * totalMoney));
			map.put("cardNumber", cardNumber);
			studentMapper.chargeCard(map);
			sqlSession.commit();
			sqlSession.close();
			return 0;
		} catch (Exception e) {
			sqlSession.rollback();
			e.printStackTrace();
		}
		return result;
	}

}
