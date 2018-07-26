package app.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import app.models.ContractType;
import app.models.Gender;
import app.models.Health;
import app.models.Religion;
import app.models.SdmLvl;

@Table("sdm")
@IdName("sdm_id")
@BelongsToParents({
	@BelongsTo(foreignKeyName="sdmlvl_id", parent = SdmLvl.class),
	@BelongsTo(foreignKeyName="contracttype_id", parent = ContractType.class),
	@BelongsTo(foreignKeyName="gender_id", parent = Gender.class),
	@BelongsTo(foreignKeyName="religion_id", parent = Religion.class),
	@BelongsTo(foreignKeyName="health_id", parent = Health.class),
})
public class Sdm extends Model{

	@SuppressWarnings("rawtypes")
	public static List<Map> getDataCV(int sdmId) {	
		List<Object> params = new ArrayList<Object>();
		System.out.println("masuk query");
		StringBuilder query = new StringBuilder();		
		query.append("SELECT sdm.SDM_NAME, gender.GENDER_NAME, \r\n" + 
				"religion.RELIGION_NAME, sdm.SDM_PLACEBIRTH,\r\n" + 
				"sdm.SDM_DATEBIRTH, health.HEALTH_STATUS, sdm.SDM_OBJECTIVE\r\n" + 
				"FROM sdm, religion, health, gender\r\n" + 
				"WHERE sdm.RELIGION_ID = religion.RELIGION_ID \r\n" + 
				"AND sdm.HEALTH_ID = health.HEALTH_ID\r\n" + 
				"AND sdm.GENDER_ID = gender.GENDER_ID \r\n" + 
				"AND sdm.SDM_ID = ?");
		System.out.println("query : "+query.toString());
		params.add(sdmId);
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		
		return lisdata;
	}
	
	@SuppressWarnings("rawtypes")
	public static List<Map> getDataLanguage(int sdmId) {	
		List<Object> params = new ArrayList<Object>();		
		StringBuilder query = new StringBuilder();		
		query.append("SELECT GROUP_CONCAT(l.LANGUAGE_NAME SEPARATOR ', ') AS LANGUAGE_NAME\r\n" + 
				"FROM sdmlanguage AS sl, languages AS l\r\n" + 
				"WHERE sl.LANGUAGE_ID = l.LANGUAGE_ID\r\n" + 
				"AND sl.SDM_ID = ? ");
		System.out.println("query : "+query.toString());
		params.add(sdmId);
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		return lisdata;
	}
	
	@SuppressWarnings("rawtypes")
	public static List<Map> getDataProfiling(int sdmId) {	
		List<Object> params = new ArrayList<Object>();		
		StringBuilder query = new StringBuilder();	
		query.append("SELECT PROFILING_NAME\r\n" + 
				"FROM profiling\r\n" + 
				"WHERE SDM_ID = ? ");
		System.out.println("query : "+query.toString());
		params.add(sdmId);
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		return lisdata;
	}
	
	@SuppressWarnings("rawtypes")
	public static List<Map> getDataSkillSdm(int sdmId) {	
		List<Object> params = new ArrayList<Object>();		
		StringBuilder query = new StringBuilder();	
		query.append("SELECT st.SKILLTYPE_NAME, s.SKILL_NAME,\r\n" + 
				"IF(sk.SKILLTYPE_ID=5,'',\r\n" + 
				"	IF(sk.SKILLTYPE_ID=6,'',\r\n" + 
				"	  IF(sk.SDMSKILL_VALUE<=6,'Beginner',\r\n" + 
				"			IF(sk.SDMSKILL_VALUE<=8,'Intermediate',\r\n" + 
				"				IF(sk.SDMSKILL_VALUE<=10,'Expert', ''))))) AS SDMSKILL_VALUE\r\n" + 
				"FROM sdmskill AS sk, skilltype AS st, skills AS s\r\n" + 
				"WHERE sk.SKILLTYPE_ID = st.SKILLTYPE_ID\r\n" + 
				"AND sk.SKILL_ID = s.SKILL_ID \r\n" + 
				"AND sk.SKILLTYPE_ID <> 1\r\n" + 
				"AND sk.SDM_ID = ? ");
		System.out.println("query : "+query.toString());
		params.add(sdmId);
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		return lisdata;
	}
	
	@SuppressWarnings("rawtypes")
	public static List<Map> getDataEducation(int sdmId) {	
		List<Object> params = new ArrayList<Object>();		
		StringBuilder query = new StringBuilder();		
		query.append("SELECT education.EDU_NAME, education.EDU_SUBJECT, degree.DEGREE_NAME,\r\n" + 
				"education.EDU_STARTDATE, education.EDU_ENDDATE \r\n" + 
				"FROM sdm, education, degree\r\n" + 
				"WHERE education.SDM_ID = sdm.SDM_ID AND education.DEGREE_ID = degree.DEGREE_ID AND sdm.SDM_ID = ? ");
		System.out.println("query : "+query.toString());
		params.add(sdmId);
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		return lisdata;
	}
	
	@SuppressWarnings("rawtypes")
	public static List<Map> getDataCourse(int sdmId) {	
		List<Object> params = new ArrayList<Object>();		
		StringBuilder query = new StringBuilder();		
		query.append("SELECT c.COURSE_TITLE, c.COURSE_PROVIDER, \r\n" + 
				"c.COURSE_PLACE, \r\n" + 
				"CONCAT(IF(MONTH(c.COURSE_DATE)=1,'Januari',\r\n" + 
				"			  IF(MONTH(c.COURSE_DATE)=2,'Februari',\r\n" + 
				"				  IF(MONTH(c.COURSE_DATE)=3,'Maret',\r\n" + 
				"					  IF(MONTH(c.COURSE_DATE)=4,'April',\r\n" + 
				"						  IF(MONTH(c.COURSE_DATE)=5,'Mei',\r\n" + 
				"							  IF(MONTH(c.COURSE_DATE)=6,'Juni',\r\n" + 
				"								  IF(MONTH(c.COURSE_DATE)=7,'Juli',\r\n" + 
				"									  IF(MONTH(c.COURSE_DATE)=8,'Agustus',\r\n" + 
				"										  IF(MONTH(c.COURSE_DATE)=9,'September',\r\n" + 
				"											  IF(MONTH(c.COURSE_DATE)=10,'Oktober',\r\n" + 
				"												  IF(MONTH(c.COURSE_DATE)=11,'Nopember',\r\n" + 
				"													  IF(MONTH(c.COURSE_DATE)=12,'Desember','')))))))))))),\r\n" + 
				"			  ' ',\r\n" + 
				"				IF(DAY(c.COURSE_DATE)=0,'',CONCAT(DAY(c.COURSE_DATE), ', ')),\r\n" + 
				"			  '',\r\n" + 
				"			  YEAR(c.COURSE_DATE)) as COURSE_DATE,\r\n" + 
				"c.COURSE_DURATION, IF(c.COURSE_CERIFICATE = 1, 'YES', '-') AS COURSE_CERIFICATE\r\n" + 
				"FROM course AS c\r\n" + 
				"WHERE c.SDM_ID = ? ");
		System.out.println("query : "+query.toString());
		params.add(sdmId);
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		return lisdata;
	}
	
	@SuppressWarnings("rawtypes")
	public static List<Map> getDataEmployment(int sdmId) {	
		List<Object> params = new ArrayList<Object>();		
		StringBuilder query = new StringBuilder();	
		query.append("SELECT e.EMPLOYMENT_CORPNAME, \r\n" + 
				"CONCAT(IF(MONTH(e.EMPLOYMENT_STARTDATE)=1,'Januari',\r\n" + 
				"			  IF(MONTH(e.EMPLOYMENT_STARTDATE)=2,'Februari',\r\n" + 
				"				  IF(MONTH(e.EMPLOYMENT_STARTDATE)=3,'Maret',\r\n" + 
				"					  IF(MONTH(e.EMPLOYMENT_STARTDATE)=4,'April',\r\n" + 
				"						  IF(MONTH(e.EMPLOYMENT_STARTDATE)=5,'Mei',\r\n" + 
				"							  IF(MONTH(e.EMPLOYMENT_STARTDATE)=6,'Juni',\r\n" + 
				"								  IF(MONTH(e.EMPLOYMENT_STARTDATE)=7,'Juli',\r\n" + 
				"									  IF(MONTH(e.EMPLOYMENT_STARTDATE)=8,'Agustus',\r\n" + 
				"										  IF(MONTH(e.EMPLOYMENT_STARTDATE)=9,'September',\r\n" + 
				"											  IF(MONTH(e.EMPLOYMENT_STARTDATE)=10,'Oktober',\r\n" + 
				"												  IF(MONTH(e.EMPLOYMENT_STARTDATE)=11,'Nopember',\r\n" + 
				"													  IF(MONTH(e.EMPLOYMENT_STARTDATE)=12,'Desember','')))))))))))),\r\n" + 
				"			  ' ',\r\n" + 
				"				IF(DAY(e.EMPLOYMENT_STARTDATE)=0,'',CONCAT(DAY(e.EMPLOYMENT_STARTDATE), ',')),\r\n" + 
				"			  ' ',\r\n" + 
				"			  YEAR(e.EMPLOYMENT_STARTDATE)) as EMPLOYMENT_STARTDATE,\r\n" + 
				"CONCAT(IF(MONTH(e.EMPLOYMENT_ENDDATE)=1,'Januari',\r\n" + 
				"			  IF(MONTH(e.EMPLOYMENT_ENDDATE)=2,'Februari',\r\n" + 
				"				  IF(MONTH(e.EMPLOYMENT_ENDDATE)=3,'Maret',\r\n" + 
				"					  IF(MONTH(e.EMPLOYMENT_ENDDATE)=4,'April',\r\n" + 
				"						  IF(MONTH(e.EMPLOYMENT_ENDDATE)=5,'Mei',\r\n" + 
				"							  IF(MONTH(e.EMPLOYMENT_ENDDATE)=6,'Juni',\r\n" + 
				"								  IF(MONTH(e.EMPLOYMENT_ENDDATE)=7,'Juli',\r\n" + 
				"									  IF(MONTH(e.EMPLOYMENT_ENDDATE)=8,'Agustus',\r\n" + 
				"										  IF(MONTH(e.EMPLOYMENT_ENDDATE)=9,'September',\r\n" + 
				"											  IF(MONTH(e.EMPLOYMENT_ENDDATE)=10,'Oktober',\r\n" + 
				"												  IF(MONTH(e.EMPLOYMENT_ENDDATE)=11,'Nopember',\r\n" + 
				"													  IF(MONTH(e.EMPLOYMENT_ENDDATE)=12,'Desember','')))))))))))),\r\n" + 
				"			  ' ',\r\n" + 
				"				IF(DAY(e.EMPLOYMENT_ENDDATE)=0,'',CONCAT(DAY(e.EMPLOYMENT_ENDDATE), ',')),\r\n" + 
				"			  ' ',\r\n" + 
				"			  YEAR(e.EMPLOYMENT_ENDDATE)) as EMPLOYMENT_ENDDATE,\r\n" + 
				"e.EMPLOYMENT_ROLEJOB\r\n" + 
				"FROM sdm AS s, employment AS e\r\n" + 
				"WHERE s.SDM_ID = e.SDM_ID\r\n" + 
				"AND s.SDM_ID = ? ");
		System.out.println("query : "+query.toString());
		params.add(sdmId);
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		return lisdata;
	}
	
	@SuppressWarnings("rawtypes")
	public static List<Map> getDataProject(int sdmId) {	
		List<Object> params = new ArrayList<Object>();		
		StringBuilder query = new StringBuilder();	
		query.append("SELECT p.PROJECT_NAME,  \r\n" + 
				"p.PROJECT_DESC, p.PROJECT_ROLE, \r\n" + 
				"CONCAT(IF(MONTH(p.PROJECT_STARTDATE)=1,'Januari',\r\n" + 
				"			  IF(MONTH(p.PROJECT_STARTDATE)=2,'Februari',\r\n" + 
				"				  IF(MONTH(p.PROJECT_STARTDATE)=3,'Maret',\r\n" + 
				"					  IF(MONTH(p.PROJECT_STARTDATE)=4,'April',\r\n" + 
				"						  IF(MONTH(p.PROJECT_STARTDATE)=5,'Mei',\r\n" + 
				"							  IF(MONTH(p.PROJECT_STARTDATE)=6,'Juni',\r\n" + 
				"								  IF(MONTH(p.PROJECT_STARTDATE)=7,'Juli',\r\n" + 
				"									  IF(MONTH(p.PROJECT_STARTDATE)=8,'Agustus',\r\n" + 
				"										  IF(MONTH(p.PROJECT_STARTDATE)=9,'September',\r\n" + 
				"											  IF(MONTH(p.PROJECT_STARTDATE)=10,'Oktober',\r\n" + 
				"												  IF(MONTH(p.PROJECT_STARTDATE)=11,'Nopember',\r\n" + 
				"													  IF(MONTH(p.PROJECT_STARTDATE)=12,'Desember','')))))))))))),\r\n" + 
				"			  ' ',\r\n" + 
				"				IF(DAY(p.PROJECT_STARTDATE)=0,'',CONCAT(DAY(p.PROJECT_STARTDATE), ',')),\r\n" + 
				"			  ' ',\r\n" + 
				"			  YEAR(p.PROJECT_STARTDATE)) as PROJECT_STARTDATE,\r\n" + 
				"CONCAT(IF(MONTH(p.PROJECT_ENDDATE)=1,'Januari',\r\n" + 
				"			  IF(MONTH(p.PROJECT_ENDDATE)=2,'Februari',\r\n" + 
				"				  IF(MONTH(p.PROJECT_ENDDATE)=3,'Maret',\r\n" + 
				"					  IF(MONTH(p.PROJECT_ENDDATE)=4,'April',\r\n" + 
				"						  IF(MONTH(p.PROJECT_ENDDATE)=5,'Mei',\r\n" + 
				"							  IF(MONTH(p.PROJECT_ENDDATE)=6,'Juni',\r\n" + 
				"								  IF(MONTH(p.PROJECT_ENDDATE)=7,'Juli',\r\n" + 
				"									  IF(MONTH(p.PROJECT_ENDDATE)=8,'Agustus',\r\n" + 
				"										  IF(MONTH(p.PROJECT_ENDDATE)=9,'September',\r\n" + 
				"											  IF(MONTH(p.PROJECT_ENDDATE)=10,'Oktober',\r\n" + 
				"												  IF(MONTH(p.PROJECT_ENDDATE)=11,'Nopember',\r\n" + 
				"													  IF(MONTH(p.PROJECT_ENDDATE)=12,'Desember','')))))))))))),\r\n" + 
				"			  ' ',\r\n" + 
				"				IF(DAY(p.PROJECT_ENDDATE)=0,'',CONCAT(DAY(p.PROJECT_ENDDATE), ',')),\r\n" + 
				"			  ' ',\r\n" + 
				"			  IF(YEAR(p.PROJECT_ENDDATE)=0,'',YEAR(p.PROJECT_ENDDATE))) as PROJECT_ENDDATE,\r\n" + 
				"p.PROJECT_PROJECTSITE, p.PROJECT_CUSTOMER,  \r\n" + 
				"p.PROJECT_APPTYPE, p.PROJECT_SERVEROS,  \r\n" + 
				"p.PROJECT_DEVLANGUAGE, p.PROJECT_FRAMEWORK,  \r\n" + 
				"p.PROJECT_DATABASE, p.PROJECT_APPSERVER,  \r\n" + 
				"p.PROJECT_DEVTOOL, p.PROJECT_TECHNICALINFO, p.PROJECT_OTHERINFO \r\n" + 
				"FROM sdm AS s, project AS p \r\n" + 
				"WHERE s.SDM_ID = p.SDM_ID  \r\n" + 
				"AND s.SDM_ID = ? ");
		System.out.println("query : "+query.toString());
		params.add(sdmId);
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		return lisdata;
	}
	
	@SuppressWarnings("rawtypes")
	public static List<Map> getLastSdmId() {	
		List<Object> params = new ArrayList<Object>();		
		StringBuilder query = new StringBuilder();		
		query.append("select sdm.SDM_ID\r\n" + 
				"from sdm\r\n" + 
				"ORDER BY sdm.SDM_ID DESC\r\n" + 
				"LIMIT 1;");
//		System.out.println("query : "+query.toString());
//		params.add(sdmId);
		System.out.println("query : "+query.toString());
		List<Map> lisdata = Base.findAll(query.toString());
		return lisdata;
	}
}
