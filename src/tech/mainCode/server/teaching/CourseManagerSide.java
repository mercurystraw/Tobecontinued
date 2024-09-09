package tech.mainCode.server.teaching;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import tech.mainCode.dao.IClassMapper;
import tech.mainCode.dao.INoticeMapper;
import tech.mainCode.dao.ITeacherMapper;
import tech.mainCode.server.main.App;
import tech.mainCode.entity.*;

/**
 * 教务老师相关后端
 * 

 */
public class CourseManagerSide {

	public static String getTeacherNameById(String academy, String idInAcademy) {
		String result = null;
		SqlSession sqlSession = null;
		try {
			sqlSession = App.sqlSessionFactory.openSession();
			ITeacherMapper teacherMapper = sqlSession.getMapper(ITeacherMapper.class);
			Map<String, String> map = new HashMap<>();
			map.put("academy", academy);
			map.put("idInAcademy", idInAcademy);
			result = teacherMapper.getTeacherNameById(map);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Boolean insertNewCourse(ClassInfo classInfo, String idInAca) {
		SqlSession sqlSession = null;
		Boolean result = null;
		try {
			sqlSession = App.sqlSessionFactory.openSession();
			IClassMapper classMapper = sqlSession.getMapper(IClassMapper.class);
			ITeacherMapper teacherMapper = sqlSession.getMapper(ITeacherMapper.class);
			Map<String, String> map = new HashMap<>();
			map.put("academy", classInfo.getId().substring(0,2));
			map.put("idInAcademy", idInAca);
			classInfo.setTeacherCard(teacherMapper.getTeacherCardById(map));
			result = classMapper.insertNewCourse(classInfo);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result == null ? false : result;
	}

	public static Boolean deleteCourse(String id) {
		SqlSession sqlSession = null;
		Boolean result = null;
		try {
			sqlSession = App.sqlSessionFactory.openSession();
			IClassMapper classMapper = sqlSession.getMapper(IClassMapper.class);
			result = classMapper.deleteCourse(id);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result == null ? false : result;
	}

	public static Boolean addNotice(NoticeInfo notice) {
		SqlSession sqlSession = null;
		Boolean result = null;
		try {
			sqlSession = App.sqlSessionFactory.openSession();
			INoticeMapper noticeMapper = sqlSession.getMapper(INoticeMapper.class);

			Integer nextId = (noticeMapper.getMaxId() != null ? noticeMapper.getMaxId() : -1) + 1;
			Map<String, String> map = new HashMap<>();
			map.put("id", String.valueOf(nextId));
			map.put("title", notice.getTitle());
			map.put("date", notice.getDate());
			map.put("url", notice.getUrl());
			result = noticeMapper.insertNewNotice(map);

			sqlSession.commit();
			sqlSession.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result == null ? false : result;
	}

}
