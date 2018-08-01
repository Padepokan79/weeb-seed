/**
 * 
 */
package app.controllers.sdm;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.common.Convert;

import com.ibm.icu.util.Calendar;

import app.models.Course;
import app.models.Education;
import app.models.Employment;
import app.models.Profiling;
import app.models.Sdm;
import app.models.SdmLanguage;
import app.models.core.master.MasterUser;
import app.models.core.master.MasterUserActivity;
import core.io.model.CorePage;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;

/**
 *web-seed
 * MengelolaSdmController.java
 ----------------------------
 * @author Ryan Ahmad Nuriana
 * 13.51.48 24 Jul 2018
 */
public class MengelolaSdmController extends CRUDController<Sdm> {
//		@Override
//		public CorePage customOnReadAll(PagingParams params) throws Exception {
//			params.setOrderBy("sdm_id");
//			LazyList<? extends Model> items = this.getItems(params);
//			Long totalItems = this.getTotalItems(params);
//			return new CorePage(items.toMaps(), totalItems);			
//		}
		
		@Override
		public Map<String, Object> customOnInsert(Sdm item, Map<String, Object> mapRequest) throws Exception{		
			Map<String, Object> result = super.customOnInsert(item, mapRequest);
			Sdm sdm = Sdm.findFirst("Order by sdm_id desc");
			
			//input education
			Education edu = new Education();
			String nama = Convert.toString(mapRequest.get("edu_name"));
			Integer degree = Convert.toInteger(mapRequest.get("degree_id"));
			String jurusan = Convert.toString(mapRequest.get("edu_subject"));
			Date masuk = Convert.toSqlDate(mapRequest.get("edu_startdate"));
			Date keluar = Convert.toSqlDate(mapRequest.get("edu_startdate"));
			edu.set("EDU_NAME", nama);
			edu.set("DEGREE_ID", degree);
			edu.set("EDU_SUBJECT", jurusan);
			edu.set("EDU_STARTDATE", masuk);
			edu.set("EDU_ENDDATE", keluar);
			edu.set("sdm_id", sdm.getId());
			edu.save();
			
			//input COURSE
			Course course = new Course();
			String kursus = Convert.toString(mapRequest.get("course_title"));
			String penyelenggara = Convert.toString(mapRequest.get("course_provider"));
			String tempat = Convert.toString(mapRequest.get("course_place"));
			Date waktu = Convert.toSqlDate(mapRequest.get("course_date")); 
			String durasi = Convert.toString(mapRequest.get("course_duration"));
			int sertifikat = Convert.toInteger(mapRequest.get("course_cerificate"));
			course.set("COURSE_TITLE", kursus);
			course.set("COURSE_PROVIDER", penyelenggara);
			course.set("COURSE_PLACE", tempat);
			course.set("COURSE_DATE", waktu);
			course.set("COURSE_DURATION", durasi);
			course.set("COURSE_CERIFICATE", sertifikat);
			course.set("sdm_id", sdm.getId());
			course.save();
			
			//input employment
			Employment employment = new Employment();
			String perusahaan = Convert.toString(mapRequest.get("employment_corpname"));
			Date start = Convert.toSqlDate(mapRequest.get("employment_startdate"));
			Date end = Convert.toSqlDate(mapRequest.get("employment_enddate"));
			String job = Convert.toString(mapRequest.get("employment_rolejob"));
			employment.set("EMPLOYMENT_CORPNAME", perusahaan);
			employment.set("EMPLOYMENT_STARTDATE", start);
			employment.set("EMPLOYMENT_ENDDATE", end);
			employment.set("EMPLOYMENT_ROLEJOB", job);
			employment.set("sdm_id", sdm.getId());
			employment.save();
			
			//input profiling
			Profiling profiling = new Profiling();
			String profile = Convert.toString(mapRequest.get("profiling_name"));
			profiling.set("PROFILING_NAME", profile);
			profiling.set("sdm_id", sdm.getId());
			
			//input SDMLANGUAGE
			SdmLanguage sdml = new SdmLanguage();
			int languageId = Convert.toInteger(mapRequest.get("language_id"));
			sdml.set("language_id", languageId);
			sdml.set("sdm_id", sdm.getId());
			sdml.save();
			
			return result;
		}
}
