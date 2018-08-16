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

import com.google.common.base.Strings;

@Table("sdmskill")
@IdName("sdmskill_id")
@BelongsToParents({
	@BelongsTo(foreignKeyName = "skilltype_id", parent = SkillType.class),
	@BelongsTo(foreignKeyName = "skill_id", parent = Skill.class),
	@BelongsTo(foreignKeyName = "sdm_id", parent = Sdm.class)
})



public class SdmSkill extends Model {
	
//	AUTHOR 	: Malik Chaudhary
//	UPDATE  : 09-08-2018 16:00
	
	static int limit = 2;
    @SuppressWarnings("rawtypes")
	public static List<Map> getMultifiltering() {	
    	int array[] = {5,7,6,7};
		List<Object> params = new ArrayList<Object>();
		System.out.println("masuk query");
		StringBuilder query = new StringBuilder();		
		query.append("SELECT sdmskill.SDMSKILL_ID, sdm.SDM_ID, sdm.SDM_NIK, sdm.SDM_NAME, skilltype.SKILLTYPE_NAME, skills.SKILL_NAME, sdmskill.SDMSKILL_VALUE \r\n" + 
				"FROM sdmskill\r\n" + 
				"INNER JOIN sdm ON sdm.SDM_ID = sdmskill.SDM_ID\r\n" + 
				"INNER JOIN skills ON skills.SKILL_ID = sdmskill.SKILL_ID\r\n" + 
				"INNER JOIN skilltype ON skilltype.SKILLTYPE_ID = sdmskill.SKILLTYPE_ID\r\n" + 
				"WHERE skills.SKILL_ID = "+array[0]+" AND sdmskill.SDMSKILL_VALUE >= "+array[1]+"");
		if (limit > 1) {
			for(int inc = 1; inc < limit; inc++) {
				query.append(" OR skills.SKILL_ID = "+array[inc+1]+" AND sdmskill.SDMSKILL_VALUE >= "+array[inc+2]+"");
//				params.add("skills.SKILL_ID");
//				params.add("sdmskill.SDMSKILL_VALUE");
			}
			query.append(" ORDER BY sdm.SDM_NAME");
		}
		System.out.println("query : "+query.toString());
//		params.add(skillId);
//		params.add(sdmskillValue);
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		
		return lisdata;
	}
    
//	AUTHOR 	: Hendra Kurniawan
//	UPDATE  : 15-08-2018 16:00  
    public static List<Map> getEndContract(int sdmId) {
    	List<Object> params = new ArrayList<Object>();
    	StringBuilder query = new StringBuilder();
    	query.append("SELECT  CONCAT(IF(DAY(p.PROJECT_ENDDATE)=0,'',CONCAT(DAY(p.PROJECT_ENDDATE), '')), \r\n" + 
    			"						' ', \r\n" + 
    			"								IF(MONTH(p.PROJECT_ENDDATE)=1,'Januari',\r\n" + 
    			"							  IF(MONTH(p.PROJECT_ENDDATE)=2,'Februari', \r\n" + 
    			"								  IF(MONTH(p.PROJECT_ENDDATE)=3,'Maret', \r\n" + 
    			"									  IF(MONTH(p.PROJECT_ENDDATE)=4,'April', \r\n" + 
    			"										  IF(MONTH(p.PROJECT_ENDDATE)=5,'Mei', \r\n" + 
    			"											  IF(MONTH(p.PROJECT_ENDDATE)=6,'Juni', \r\n" + 
    			"												  IF(MONTH(p.PROJECT_ENDDATE)=7,'Juli', \r\n" + 
    			"												  IF(MONTH(p.PROJECT_ENDDATE)=8,'Agustus', \r\n" + 
    			"														  IF(MONTH(p.PROJECT_ENDDATE)=9,'September', \r\n" + 
    			"															  IF(MONTH(p.PROJECT_ENDDATE)=10,'Oktober',\r\n" + 
    			"																  IF(MONTH(p.PROJECT_ENDDATE)=11,'November', \r\n" + 
    			"																	  IF(MONTH(p.PROJECT_ENDDATE)=12,'Desember','')))))))))))), \r\n" + 
    			"							  ' ', \r\n" + 
    			"							  IF(YEAR(p.PROJECT_ENDDATE)=0,'',YEAR(p.PROJECT_ENDDATE))) as PROJECT_ENDDATE \r\n" + 
    			"				FROM sdm AS s, project AS p \r\n" + 
    			"				WHERE s.SDM_ID = p.SDM_ID  \r\n" + 
    			"				AND s.SDM_ID = ? ORDER BY p.PROJECT_ENDDATE DESC LIMIT 1");
    	
    	System.out.println("query : " + query.toString());
    	params.add(sdmId);
    	List<Map> listdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
    	return listdata;
    }
}
