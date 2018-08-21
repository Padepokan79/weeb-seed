package app.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.google.common.base.Strings;
import core.io.model.PagingParams;

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
	public static List<Map> getGroupSdmSkill(String filter) {
		List<Object> params = new ArrayList<Object>();
		System.out.println("masuk query");
		StringBuilder query = new StringBuilder();
		query.append("SELECT sdmskill.SDMSKILL_ID as SDMSKILL_ID, sdm.SDM_NIK as SDM_NIK, sdm.SDM_NAME as SDM_NAME, \r\n" + 
				"GROUP_CONCAT(skilltype.SKILLTYPE_NAME SEPARATOR '-') as SKILLTYPE_NAME,\r\n" + 
				"GROUP_CONCAT(skills.SKILL_NAME SEPARATOR '-') as SKILL_NAME, \r\n" + 
				"GROUP_CONCAT(sdmskill.SDMSKILL_VALUE SEPARATOR ',') as SDMSKILL_VALUE\r\n" + 
				"FROM sdmskill\r\n" + 
				"INNER JOIN sdm ON sdm.SDM_ID = sdmskill.SDM_ID\r\n" + 
				"INNER JOIN skilltype ON skilltype.SKILLTYPE_ID = sdmskill.SKILLTYPE_ID\r\n" + 
				"INNER JOIN skills ON skills.SKILL_ID = sdmskill.SKILL_ID\r\n");
		
		if(Strings.isNullOrEmpty(filter)) {
			query.append("WHERE " + filter);
		}
				
		query.append("GROUP BY sdmskill.SDM_NIK, sdmskill.SDM_NAME");

		System.out.println("query : "+query.toString());
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		
		return lisdata;		
	}
	
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
    	query.append("Select project.project_enddate from sdm INNER JOIN project ON sdm.sdm_id = project.sdm_id WHERE sdm.sdm_id = ? ORDER BY project.PROJECT_ENDDATE DESC LIMIT 1\r\n" + 
    			"");
    	params.add(sdmId);
    	List<Map> listdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
    	return listdata;
    }
}
