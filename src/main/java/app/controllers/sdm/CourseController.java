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
		public int norut;
		public String nama;
		public String course_title;
		public String course_provider;
		public String course_place;
		public String course_duration;
		public String course_certificates;
		public String course_date;
	}
	
	@Override
	public CorePage customOnReadAll(PagingParams params) throws Exception {
		List<Map<String, Object>> listMapCourse = new ArrayList<Map<String, Object>>();
		params.setOrderBy("course_id");
		LazyList<Course> listCourse = (LazyList<Course>) this.getItems(params);

		
		Long totalItems = this.getTotalItems(params);
			int number = 1;
			for (Course course : listCourse) {
				Sdm sdm = course.parent(Sdm.class);
				CourseDTO dto = new CourseDTO();
				dto.fromModelMap(course.toMap());
				dto.norut = number;
				number++;
				dto.nama = Convert.toString(sdm.get("sdm_name"));
				dto.course_date = Convert.toString(course.get("course_date"));
				dto.course_certificates = Convert.toString(course.get("course_certificates"));
				dto.course_provider = Convert.toString(course.get("course_provider"));
				dto.course_title = Convert.toString(course.get("course_title"));
				dto.course_duration = Convert.toString(course.get("course_duration"));
				dto.course_place = Convert.toString(course.get("course_place"));
				listMapCourse.add(dto.toModelMap());
			}
		
		return new CorePage(listMapCourse, totalItems);		
	}
}
