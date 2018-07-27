/**
 * 
 */
package app.controllers.sdm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.javalite.common.Convert;

import app.controllers.sdm.EducationController.EducationDTO;
import app.models.Course;
import app.models.Degree;
import app.models.Education;
import app.models.Sdm;
import core.io.model.CorePage;
import core.io.model.DTOModel;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;

/**
 *web-seed
 * CourseController.java
 ----------------------------
 * @author Ryan Ahmad Nuriana
 * 16.36.18 25 Jul 2018
 */
public class CourseController extends CRUDController<Course>{
	public class CourseDTO extends DTOModel {
		public int course_id;
		public String nama;
		public String course_title;
		public String course_provider;
		public String course_place;
		public String course_duration;
		public String course_cerificate;
		public String course_date;
	}
	
	@Override
	public CorePage customOnReadAll(PagingParams params) throws Exception {		
		List<Map<String, Object>> listMapCourse = new ArrayList<Map<String, Object>>();
		LazyList<Course> listCourse = Course.findAll();	
		params.setOrderBy("course_id");
		
		Long totalItems = this.getTotalItems(params);
					
			for (Course course : listCourse) {
				Sdm sdm = course.parent(Sdm.class);
				CourseDTO dto = new CourseDTO();
				dto.fromModelMap(course.toMap());
				dto.nama = Convert.toString(sdm.get("sdm_name"));
				dto.course_date = Convert.toString(course.get("course_date"));
				listMapCourse.add(dto.toModelMap());
			}
		
		return new CorePage(listMapCourse, totalItems);		
	}
}
