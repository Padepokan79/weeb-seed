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
import org.javalite.common.Convert;

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
	
	static int limit = 2;
	@SuppressWarnings("rawtypes")
	public static List<Map> getGroupSdmSkill(String filter) {
		List<Object> params = new ArrayList<Object>();
		System.out.println("masuk query");
		StringBuilder query = new StringBuilder();
		query.append("SELECT sdmskill.SDMSKILL_ID as SDMSKILL_ID, sdm.SDM_NIK as SDM_NIK, sdm.SDM_NAME as SDM_NAME, \r\n" + 
				"GROUP_CONCAT(skilltype.SKILLTYPE_NAME SEPARATOR '-'), \r\n" + 
				"GROUP_CONCAT(skills.SKILL_NAME SEPARATOR '-'), \r\n" + 
				"GROUP_CONCAT(sdmskill.SDMSKILL_VALUE SEPARATOR ',') \r\n" + 
				"FROM sdmskill\r\n" + 
				"INNER JOIN sdm ON sdm.SDM_ID = sdmskill.SDM_ID\r\n" + 
				"INNER JOIN skilltype ON skilltype.SKILLTYPE_ID = sdmskill.SKILLTYPE_ID\r\n" + 
				"INNER JOIN skills ON skills.SKILL_ID = sdmskill.SKILL_ID\r\n");
		
		if(!Strings.isNullOrEmpty(filter)) {
			query.append("WHERE " + filter + "\r\n");
		}
				
		query.append("GROUP BY sdm.SDM_NIK, sdm.SDM_NAME");

		System.out.println("query : "+query.toString());
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		
		return lisdata;		
	}

//	AUTHOR 	: Malik Chaudhary
//	UPDATE  : 09-08-2018 16:00
	
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
    
    public static List<Map> getDataSdmSkillConcat(int sdmId) {
    	List<Object> params = new ArrayList<Object>();
    	StringBuilder query = new StringBuilder();
    	query.append("SELECT SDMSKILL.SDM_ID, SDMSKILL.SDMSKILL_ID, SDMSKILL.SDM_NIK,  SDMSKILL.SDM_NAME, SDMSKILL.SKILLTYPE_NAME, SDMSKILL.SDMSKILL, SDMSKILL.SDMSKILLVALUE FROM (\r\n" + 
    			"				SELECT sdm.SDM_ID as SDM_ID, sdmskill.SDMSKILL_ID as SDMSKILL_ID, sdm.SDM_NIK as SDM_NIK, sdm.SDM_NAME as SDM_NAME,\r\n" + 
    			"				skilltype.SKILLTYPE_NAME, \r\n" + 
    			"				GROUP_CONCAT(skills.SKILL_NAME SEPARATOR ',') AS SDMSKILL,\r\n" + 
    			"				GROUP_CONCAT(sdmskill.SDMSKILL_VALUE SEPARATOR ',') AS SDMSKILLVALUE \r\n" + 
    			"				FROM sdmskill \r\n" + 
    			"				INNER JOIN sdm ON sdm.SDM_ID = sdmskill.SDM_ID\r\n" + 
    			"				INNER JOIN skilltype ON skilltype.SKILLTYPE_ID = sdmskill.SKILLTYPE_ID \r\n" + 
    			"				INNER JOIN skills ON skills.SKILL_ID = sdmskill.SKILL_ID\r\n" + 
    			"				GROUP BY sdm.SDM_NAME, skilltype.SKILLTYPE_NAME \r\n" + 
    			"				) as SDMSKILL WHERE SDMSKILL.SDM_ID = ? ");
    	params.add(sdmId);
    	List<Map> listdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
    	return listdata;
    }
    
    public static List<Map> getEndContract(int sdmId) {
    	List<Object> params = new ArrayList<Object>();
    	StringBuilder query = new StringBuilder();
    	query.append("Select project.project_enddate from sdm INNER JOIN project ON sdm.sdm_id = project.sdm_id WHERE sdm.sdm_id = ? ORDER BY project.PROJECT_ENDDATE DESC LIMIT 1\r\n" + 
    			"");
    	params.add(sdmId);
    	List<Map> listdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
    	return listdata;
    }
    
    
  //Method Filter
    public static List<Map> getbySdm(int sdmId){
    	List<Object> params = new ArrayList<Object>();
		StringBuilder query = new StringBuilder();
		query.append("SELECT sdmskill.SDM_ID, sdm.SDM_NAME,  sdm.SDM_NIK, sdm.SDM_ENDCONTRACT, sdmskill.SDMSKILL_iD, sdm.SDM_NAME, skilltype.SKILLTYPE_NAME, skills.SKILL_NAME, sdmskill.SDMSKILL_VALUE, sdm.SDM_STATUS \r\n" + 
				"FROM sdmskill\r\n" + 
				"INNER JOIN sdm ON sdm.SDM_ID = sdmskill.SDM_ID \r\n" + 
				"INNER JOIN skills ON skills.SKILL_ID = sdmskill.SKILL_ID \r\n" + 
				"INNER JOIN skilltype ON skilltype.SKILLTYPE_ID = sdmskill.SKILLTYPE_ID \r\n" +
				" where sdmskill.SDM_ID = ?");
		System.out.println("query : "+query.toString());
		
		params.add(sdmId);
		query.append(" ORDER BY sdm.SDM_NAME");
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		
    	return lisdata;
		
    }
    
    public static List<Map> getbyCategoryOR(List<Map<String, Object>> listParams)
    {
    	List<Object> params = new ArrayList<Object>();
		StringBuilder query = new StringBuilder();
		query.append("SELECT sdmskill.SDM_ID, sdm.SDM_NAME, sdm.SDM_NIK,  sdm.SDM_ENDCONTRACT,sdmskill.SDMSKILL_iD, sdm.SDM_NAME, skilltype.SKILLTYPE_NAME, skills.SKILL_NAME, sdmskill.SDMSKILL_VALUE, sdm.SDM_STATUS\r\n" + 
				"FROM sdmskill\r\n" + 
				"INNER JOIN sdm ON sdm.SDM_ID = sdmskill.SDM_ID \r\n" + 
				"INNER JOIN skills ON skills.SKILL_ID = sdmskill.SKILL_ID \r\n" + 
				"INNER JOIN skilltype ON skilltype.SKILLTYPE_ID = sdmskill.SKILLTYPE_ID \r\n" +
				"WHERE ");
		
		int index=1;
		int jumlahData=listParams.size();
		
		for (Map<String, Object> sdm : listParams) {
			int skilltypeId = Convert.toInteger(sdm.get("skilltype_id"));
			if(index == 1) {
				query.append(" sdmskill.SKILLTYPE_ID = " + skilltypeId);
			}
			else if(index <= jumlahData) {
				query.append(" OR sdmskill.SKILLTYPE_ID = " + skilltypeId);
			}
			index++;
		}
		
		query.append(" ORDER BY sdm.SDM_NAME");
		System.out.println("query : "+query.toString());
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		
    	return lisdata;
		
    }
    
    public static List<Map> getbyCategoryAND(List<Map<String, Object>> listParams)
    {
    	List<Object> params = new ArrayList<Object>();
		StringBuilder query = new StringBuilder();
		query.append("SELECT sdmskill.SDM_ID, sdm.SDM_NAME, sdm.SDM_NIK,  sdm.SDM_ENDCONTRACT,sdmskill.SDMSKILL_iD, sdm.SDM_NAME, skilltype.SKILLTYPE_NAME, skills.SKILL_NAME, sdmskill.SDMSKILL_VALUE, sdm.SDM_STATUS\r\n" + 
				"FROM sdmskill\r\n" + 
				"INNER JOIN sdm ON sdm.SDM_ID = sdmskill.SDM_ID \r\n" + 
				"INNER JOIN skills ON skills.SKILL_ID = sdmskill.SKILL_ID \r\n" + 
				"INNER JOIN skilltype ON skilltype.SKILLTYPE_ID = sdmskill.SKILLTYPE_ID \r\n" +
				"WHERE sdmskill.SDM_ID IN ( SELECT sdm_id FROM sdmskill\r\n" + 
				"WHERE SKILLTYPE_ID IN ( " );
		int index=1;
		int jumlahData=0;
		int skilltypeId=0;
		for (Map<String, Object> sdm : listParams) {
			skilltypeId = Convert.toInteger(sdm.get("skilltype_id"));
			jumlahData = listParams.size();
			query.append(" " + skilltypeId + " ");
			if(index < jumlahData)
			{	query.append(", ");
			}
			else if(index == jumlahData)
			{ }
			index++;
		}

		query.append(") GROUP BY SDM_ID HAVING COUNT(DISTINCT SKILLTYPE_ID)=" + jumlahData);
		query.append(" ORDER BY SDM_ID) AND sdmskill.SKILLTYPE_ID IN (" );
		
		index=1;
		for (Map<String, Object> sdm : listParams) {
			skilltypeId = Convert.toInteger(sdm.get("skilltype_id"));
			jumlahData = listParams.size();
			query.append(" " + skilltypeId + " ");
			if(index < jumlahData)
			{	query.append(", ");
			}
			else if(index == jumlahData)
			{ 
				query.append(" ) ");
			}
			index++;
		}
		
		System.out.println("query : "+query.toString());
		query.append(" ORDER BY sdm.SDM_NAME");
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		
    	return lisdata;
		
    }
    
    public static List<Map> getbySkillValueOR(List<Map<String, Object>> listParams)
    {
    	List<Object> params = new ArrayList<Object>();
		StringBuilder query = new StringBuilder();
		query.append("SELECT sdmskill.SDM_ID, sdm.SDM_NAME,  sdm.SDM_NIK, sdm.SDM_ENDCONTRACT,sdmskill.SDMSKILL_iD, sdm.SDM_NAME, skilltype.SKILLTYPE_NAME, skills.SKILL_NAME, sdmskill.SDMSKILL_VALUE, sdm.SDM_STATUS\r\n" + 
				"FROM sdmskill\r\n" + 
				"INNER JOIN sdm ON sdm.SDM_ID = sdmskill.SDM_ID \r\n" + 
				"INNER JOIN skills ON skills.SKILL_ID = sdmskill.SKILL_ID \r\n" + 
				"INNER JOIN skilltype ON skilltype.SKILLTYPE_ID = sdmskill.SKILLTYPE_ID \r\n" +
				"WHERE ");
		
		int index=1;
		int jumlahData=listParams.size();
		
		for (Map<String, Object> sdm : listParams) {
			int skilltypeId = Convert.toInteger(sdm.get("skilltype_id"));
			int skillId = Convert.toInteger(sdm.get("skill_id"));
			int sdmskillValue = Convert.toInteger(sdm.get("sdmskill_value"));
			if(index == 1) {
				query.append(" (sdmskill.SKILLTYPE_ID = " + skilltypeId + " AND sdmskill.SKILL_ID = " + skillId + " AND sdmskill.SDMSKILL_VALUE >= " + sdmskillValue + " )");
			}
			else if(index <= jumlahData) {
				query.append(" OR (sdmskill.SKILLTYPE_ID = " + skilltypeId + " AND sdmskill.SKILL_ID = " + skillId + " AND sdmskill.SDMSKILL_VALUE >= " + sdmskillValue + " )");
			}
			index++;
		}
		
		query.append(" ORDER BY sdm.SDM_NAME");
		System.out.println("query : "+query.toString());
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		
    	return lisdata;
    }
    
    public static List<Map> getbySkillValueAND(List<Map<String, Object>> listParams)
    {
    	List<Object> params = new ArrayList<Object>();
		StringBuilder query = new StringBuilder();
		query.append(" SELECT sdmskill.SDM_ID, sdm.SDM_NAME,  sdm.SDM_NIK, sdm.SDM_ENDCONTRACT,sdmskill.SDMSKILL_iD, sdm.SDM_NAME, skilltype.SKILLTYPE_NAME, skills.SKILL_NAME, sdmskill.SDMSKILL_VALUE, sdm.SDM_STATUS\r\n" + 
				"				FROM sdmskill \r\n" + 
				"				INNER JOIN sdm ON sdm.SDM_ID = sdmskill.SDM_ID \r\n" + 
				"				INNER JOIN skills ON skills.SKILL_ID = sdmskill.SKILL_ID  \r\n" + 
				"				INNER JOIN skilltype ON skilltype.SKILLTYPE_ID = sdmskill.SKILLTYPE_ID \r\n" + 
				"			  WHERE sdmskill.SDM_ID IN (  SELECT tabel1.sdm_id FROM (SELECT SDM_ID,SDMSKILL_ID, SKILLTYPE_ID, SKILL_ID, SDMSKILL_VALUE \r\n" + 
				"				FROM sdmskill \r\n" + 
				"				WHERE ");
		
		
		int jumlahData=listParams.size();
		int skilltypeId=0;
		int skillId=0;
		int sdmskillValue=0;
		int index=1;
		for (Map<String, Object> sdm : listParams) {
			skilltypeId = Convert.toInteger(sdm.get("skilltype_id"));
			skillId = Convert.toInteger(sdm.get("skill_id"));
			sdmskillValue = Convert.toInteger(sdm.get("sdmskill_value"));
			if(index == 1) {
				query.append(" (sdmskill.SKILLTYPE_ID = " + skilltypeId + " AND sdmskill.SKILL_ID = " + skillId + " AND sdmskill.SDMSKILL_VALUE >= " + sdmskillValue + " )");
			}
			else if(index <= jumlahData) {
				query.append(" OR (sdmskill.SKILLTYPE_ID = " + skilltypeId + " AND sdmskill.SKILL_ID = " + skillId + " AND sdmskill.SDMSKILL_VALUE >= " + sdmskillValue + " )");
			}
			index++;
		}
		
		query.append(") as tabel1\r\n" + 
				"	WHERE tabel1.SKILL_ID IN (" );
			index=1;
			for (Map<String, Object> sdm : listParams) {
				skillId = Convert.toInteger(sdm.get("skill_id"));
				jumlahData = listParams.size();
				query.append(" " + skillId + " ");
				if(index < jumlahData)
				{	query.append(", ");
				}
				else if(index == jumlahData)
				{ 
					query.append(" ) ");
				}
				index++;
			}
		query.append(" GROUP BY tabel1.SDM_ID HAVING COUNT(DISTINCT tabel1.SKILL_ID)= " + jumlahData);
		query.append(" ORDER BY tabel1.SDM_ID) ");
		query.append(" AND sdmskill.SKILL_ID IN ( ");
			index=1;
			for (Map<String, Object> sdm : listParams) {
				skillId = Convert.toInteger(sdm.get("skill_id"));
				jumlahData = listParams.size();
				query.append(" " + skillId + " ");
				if(index < jumlahData)
				{	query.append(", ");
				}
				else if(index == jumlahData)
				{ 
					query.append(" ) ");
				}
				index++;
			}
//		query.append(" ORDER BY sdm.SDM_NAME");
		System.out.println("query : "+query.toString());
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		System.out.println(lisdata);
    	return lisdata;
    }
    
    public static List<Map> getbySdmCategoryOR(List<Map<String, Object>> listParams){
    	List<Object> params = new ArrayList<Object>();
		StringBuilder query = new StringBuilder();
		int sdmId=0;
		for (Map<String, Object> sdm : listParams) {
			sdmId = Convert.toInteger(sdm.get("sdm_id"));
		}
		query.append("SELECT sdmskill.SDM_ID, sdm.SDM_NAME,  sdm.SDM_NIK, sdm.SDM_ENDCONTRACT,sdmskill.SDMSKILL_iD, sdm.SDM_NAME, skilltype.SKILLTYPE_NAME, skills.SKILL_NAME, sdmskill.SDMSKILL_VALUE, sdm.SDM_STATUS\r\n" + 
				"FROM sdmskill\r\n" + 
				"INNER JOIN sdm ON sdm.SDM_ID = sdmskill.SDM_ID \r\n" + 
				"INNER JOIN skills ON skills.SKILL_ID = sdmskill.SKILL_ID \r\n" + 
				"INNER JOIN skilltype ON skilltype.SKILLTYPE_ID = sdmskill.SKILLTYPE_ID \r\n" +
				" where sdmskill.SDM_ID = " + sdmId + " AND  ");
		int index=1;
		int jumlahData=listParams.size();
		
		for (Map<String, Object> sdm : listParams) {
			int skilltypeId = Convert.toInteger(sdm.get("skilltype_id"));
			if(index == 1) {
				query.append(" sdmskill.SKILLTYPE_ID = " + skilltypeId);
			}
			else if(index <= jumlahData) {
				query.append(" OR sdmskill.SKILLTYPE_ID = " + skilltypeId);
			}
			index++;
		}
		
		
		query.append(" ORDER BY sdm.SDM_NAME");
		System.out.println("query : "+query.toString());
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		
    	return lisdata;
    }
    
    public static List<Map> getbySdmCategoryAND(List<Map<String, Object>> listParams){
    	List<Object> params = new ArrayList<Object>();
		StringBuilder query = new StringBuilder();
		int sdmId=0;
		for (Map<String, Object> sdm : listParams) {
			sdmId = Convert.toInteger(sdm.get("sdm_id"));
		}
		query.append("SELECT sdmskill.SDM_ID, sdm.SDM_NAME, sdm.SDM_NIK, sdm.SDM_ENDCONTRACT,sdmskill.SDMSKILL_iD, sdm.SDM_NAME, skilltype.SKILLTYPE_NAME, skills.SKILL_NAME, sdmskill.SDMSKILL_VALUE, sdm.SDM_STATUS\r\n" + 
				"FROM sdmskill\r\n" + 
				"INNER JOIN sdm ON sdm.SDM_ID = sdmskill.SDM_ID \r\n" + 
				"INNER JOIN skills ON skills.SKILL_ID = sdmskill.SKILL_ID \r\n" + 
				"INNER JOIN skilltype ON skilltype.SKILLTYPE_ID = sdmskill.SKILLTYPE_ID \r\n" +
				"where sdmskill.SDM_ID = " + sdmId + " AND \r\n" +
				"sdmskill.SDM_ID IN ( SELECT sdm_id FROM sdmskill\r\n" + 
				"WHERE SKILLTYPE_ID IN ( " );
		int index=1;
		int jumlahData=0;
		int skilltypeId=0;
		for (Map<String, Object> sdm : listParams) {
			skilltypeId = Convert.toInteger(sdm.get("skilltype_id"));
			jumlahData = listParams.size();
			query.append(" " + skilltypeId + " ");
			if(index < jumlahData)
			{	query.append(", ");
			}
			else if(index == jumlahData)
			{ }
			index++;
		}

		query.append(") GROUP BY SDM_ID HAVING COUNT(DISTINCT SKILLTYPE_ID)=" + jumlahData);
		query.append(" ORDER BY SDM_ID) AND sdmskill.SKILLTYPE_ID IN (" );
		
		index=1;
		for (Map<String, Object> sdm : listParams) {
			skilltypeId = Convert.toInteger(sdm.get("skilltype_id"));
			jumlahData = listParams.size();
			query.append(" " + skilltypeId + " ");
			if(index < jumlahData)
			{	query.append(", ");
			}
			else if(index == jumlahData)
			{ 
				query.append(" ) ");
			}
			index++;
		}
		
		
		query.append(" ORDER BY sdm.SDM_NAME");
		System.out.println("query : "+query.toString());
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		
    	return lisdata;
    }    
    
    
    public static List<Map> getbySdmCategoryValueOR(List<Map<String, Object>> listParams){
    	List<Object> params = new ArrayList<Object>();
		StringBuilder query = new StringBuilder();
		int sdmId=0;
		for (Map<String, Object> sdm : listParams) {
			sdmId = Convert.toInteger(sdm.get("sdm_id"));
		}
		query.append("SELECT sdmskill.SDM_ID, sdm.SDM_NAME,  sdm.SDM_NIK, sdm.SDM_ENDCONTRACT,sdmskill.SDMSKILL_iD, sdm.SDM_NAME, skilltype.SKILLTYPE_NAME, skills.SKILL_NAME, sdmskill.SDMSKILL_VALUE, sdm.SDM_STATUS\r\n" + 
				"FROM sdmskill\r\n" + 
				"INNER JOIN sdm ON sdm.SDM_ID = sdmskill.SDM_ID \r\n" + 
				"INNER JOIN skills ON skills.SKILL_ID = sdmskill.SKILL_ID \r\n" + 
				"INNER JOIN skilltype ON skilltype.SKILLTYPE_ID = sdmskill.SKILLTYPE_ID \r\n" +
				" where sdmskill.SDM_ID = " + sdmId + " AND  ");
		int index=1;
		int jumlahData=listParams.size();
		for (Map<String, Object> sdm : listParams) {
			int skilltypeId = Convert.toInteger(sdm.get("skilltype_id"));
			int skillId = Convert.toInteger(sdm.get("skill_id"));
			int sdmskillValue = Convert.toInteger(sdm.get("sdmskill_value"));
			if(index == 1) {
				query.append(" (sdmskill.SKILLTYPE_ID = " + skilltypeId + " AND sdmskill.SKILL_ID = " + skillId + " AND sdmskill.SDMSKILL_VALUE >= " + sdmskillValue + " )");
			}
			else if(index <= jumlahData) {
				query.append(" OR (sdmskill.SKILLTYPE_ID = " + skilltypeId + " AND sdmskill.SKILL_ID = " + skillId + " AND sdmskill.SDMSKILL_VALUE >= " + sdmskillValue + " )");
			}
			index++;
		}
		
		query.append(" ORDER BY sdm.SDM_NAME");
		System.out.println("query : "+query.toString());
		
    	List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		
    	return lisdata;
    }
    
    
    public static List<Map> getbySdmCategoryValueAND(List<Map<String, Object>> listParams){
    	List<Object> params = new ArrayList<Object>();
		StringBuilder query = new StringBuilder();
		int sdmId=0;
		for (Map<String, Object> sdm : listParams) {
			sdmId = Convert.toInteger(sdm.get("sdm_id"));
		}
		query.append(" SELECT sdmskill.SDM_ID, sdm.SDM_NAME,  sdm.SDM_NIK, sdm.SDM_ENDCONTRACT,sdmskill.SDMSKILL_iD, sdmskill.SKILLTYPE_ID, sdm.SDM_NAME, skilltype.SKILLTYPE_NAME, skills.SKILL_NAME, sdmskill.SDMSKILL_VALUE, sdm.SDM_STATUS\r\n" + 
				"				FROM sdmskill \r\n" + 
				"				INNER JOIN sdm ON sdm.SDM_ID = sdmskill.SDM_ID \r\n" + 
				"				INNER JOIN skills ON skills.SKILL_ID = sdmskill.SKILL_ID  \r\n" + 
				"				INNER JOIN skilltype ON skilltype.SKILLTYPE_ID = sdmskill.SKILLTYPE_ID \r\n" + 
				"			  WHERE sdmskill.SDM_ID IN (  SELECT tabel1.sdm_id FROM (SELECT SDM_ID,SDMSKILL_ID, SKILLTYPE_ID, SKILL_ID, SDMSKILL_VALUE \r\n" + 
				"				FROM sdmskill \r\n" + 
				"				WHERE sdmskill.SDM_ID =  "+ sdmId +"  AND  ");
		
		
		int jumlahData=listParams.size();
		int skilltypeId=0;
		int skillId=0;
		int sdmskillValue=0;
		int index=1;
		for (Map<String, Object> sdm : listParams) {
			skilltypeId = Convert.toInteger(sdm.get("skilltype_id"));
			skillId = Convert.toInteger(sdm.get("skill_id"));
			sdmskillValue = Convert.toInteger(sdm.get("sdmskill_value"));
			if(index == 1) {
				query.append(" (sdmskill.SKILLTYPE_ID = " + skilltypeId + " AND sdmskill.SKILL_ID = " + skillId + " AND sdmskill.SDMSKILL_VALUE >= " + sdmskillValue + " )");
			}
			else if(index <= jumlahData) {
				query.append(" OR (sdmskill.SKILLTYPE_ID = " + skilltypeId + " AND sdmskill.SKILL_ID = " + skillId + " AND sdmskill.SDMSKILL_VALUE >= " + sdmskillValue + " )");
			}
			index++;
		}
		
		query.append(") as tabel1\r\n" + 
				"	WHERE tabel1.SKILL_ID IN (" );
			index=1;
			for (Map<String, Object> sdm : listParams) {
				skillId = Convert.toInteger(sdm.get("skill_id"));
				jumlahData = listParams.size();
				query.append(" " + skillId + " ");
				if(index < jumlahData)
				{	query.append(", ");
				}
				else if(index == jumlahData)
				{ 
					query.append(" ) ");
				}
				index++;
			}
		query.append(" GROUP BY tabel1.SDM_ID HAVING COUNT(DISTINCT tabel1.SKILL_ID)= " + jumlahData);
		query.append(" ORDER BY tabel1.SDM_ID) ");
		query.append(" AND sdmskill.SKILL_ID IN ( ");
			index=1;
			for (Map<String, Object> sdm : listParams) {
				skillId = Convert.toInteger(sdm.get("skill_id"));
				jumlahData = listParams.size();
				query.append(" " + skillId + " ");
				if(index < jumlahData)
				{	query.append(", ");
				}
				else if(index == jumlahData)
				{ 
					query.append(" ) ");
				}
				index++;
			}
//		query.append(" ORDER BY sdm.SDM_NAME");
		System.out.println("query : "+query.toString());
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		System.out.println(lisdata);
    	return lisdata;
    }
    
    public static List<Map> getAllSdmSkill(){
    	List<Object> params = new ArrayList<Object>();
		StringBuilder query = new StringBuilder();
		query.append("SELECT sdmskill.SDM_ID, sdm.SDM_NAME,  sdm.SDM_NIK, sdm.SDM_ENDCONTRACT, sdmskill.SDMSKILL_iD, sdm.SDM_NAME, skilltype.SKILLTYPE_NAME, skills.SKILL_NAME, sdmskill.SDMSKILL_VALUE, sdm.SDM_STATUS\r\n" + 
				"FROM sdmskill\r\n" + 
				"INNER JOIN sdm ON sdm.SDM_ID = sdmskill.SDM_ID \r\n" + 
				"INNER JOIN skills ON skills.SKILL_ID = sdmskill.SKILL_ID \r\n" + 
				"INNER JOIN skilltype ON skilltype.SKILLTYPE_ID = sdmskill.SKILLTYPE_ID \r\n");
		System.out.println("query : "+query.toString());
		
		
		query.append(" ORDER BY sdm.SDM_NAME");
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		
    	return lisdata;
		
    }
    
    //filter dengan kriteria category saja + categoryskillvalue dengan operator OR 
    public static List<Map> getbyCategoryCategorySkillValueOR(List<Map<String, Object>> listParams){
    	List<Object> params = new ArrayList<Object>();
		StringBuilder query = new StringBuilder();
		System.out.println("masuk qurey");
		query.append("SELECT sdmskill.SDM_ID, sdm.SDM_NAME,  sdm.SDM_NIK, sdm.SDM_ENDCONTRACT,sdmskill.SDMSKILL_iD, sdm.SDM_NAME, skilltype.SKILLTYPE_NAME, skills.SKILL_NAME, sdmskill.SDMSKILL_VALUE, sdm.SDM_STATUS\r\n" + 
				"FROM sdmskill\r\n" + 
				"INNER JOIN sdm ON sdm.SDM_ID = sdmskill.SDM_ID \r\n" + 
				"INNER JOIN skills ON skills.SKILL_ID = sdmskill.SKILL_ID \r\n" + 
				"INNER JOIN skilltype ON skilltype.SKILLTYPE_ID = sdmskill.SKILLTYPE_ID \r\n" +
				" where ");
		int index=1;
		int jumlahData=listParams.size();
		for (Map<String, Object> sdm : listParams) {
			String  skilltypeId = Convert.toString(sdm.get("skilltype_id"));
			String skillId = Convert.toString(sdm.get("skill_id"));
			String sdmskillValue = Convert.toString(sdm.get("sdmskill_value"));
			System.out.println(" data " + skilltypeId + " " + skillId + " " +  sdmskillValue);
			
			if(skilltypeId != null && skillId != null && sdmskillValue != null) {
				if(index == 1) {
					query.append(" (sdmskill.SKILLTYPE_ID = " + skilltypeId + " AND sdmskill.SKILL_ID = " + skillId + " AND sdmskill.SDMSKILL_VALUE >= " + sdmskillValue + " )");
				}
				else if(index <= jumlahData) {
					query.append(" OR (sdmskill.SKILLTYPE_ID = " + skilltypeId + " AND sdmskill.SKILL_ID = " + skillId + " AND sdmskill.SDMSKILL_VALUE >= " + sdmskillValue + " )");
				}	
			}
			else {
				if(index == 1) {
					query.append(" (sdmskill.SKILLTYPE_ID = " + skilltypeId + " ) ");
				}
				else if(index <= jumlahData) {
					query.append(" OR (sdmskill.SKILLTYPE_ID = " + skilltypeId + " ) ");
				}
			}
			index++;
		}
		query.append(" ORDER BY sdm.SDM_NAME");
		System.out.println("query : "+query.toString());
		
    	List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		
    	return lisdata;
    }
    
  //filter dengan kriteria category saja + categoryskillvalue dengan operator AND
    public static List<Map> getbyCategoryCategorySkillValueAND(List<Map<String, Object>> listParams){
    	List<Object> params = new ArrayList<Object>();
		StringBuilder query = new StringBuilder();
		query.append(" SELECT sdms.SDM_ID,  sdms.SDM_NIK, sdms.SDM_ENDCONTRACT,sdms.SDMSKILL_iD, sdms.SDM_NAME, sdms.SKILLTYPE_NAME, sdms.SKILL_NAME, sdms.SDMSKILL_VALUE, sdms.SKILLTYPE_ID, sdms.SKILL_ID, sdms.SDM_STATUS \r\n" + 
				     "FROM ( ");
		query.append(" SELECT sdmskill.SDM_ID, sdm.SDM_NIK, sdm.SDM_ENDCONTRACT,sdmskill.SDMSKILL_iD, sdm.SDM_NAME, skilltype.SKILLTYPE_NAME, skills.SKILL_NAME, sdmskill.SDMSKILL_VALUE, sdmskill.SKILLTYPE_ID, sdmskill.SKILL_ID, sdm.SDM_STATUS\r\n" + 
				"				FROM sdmskill \r\n" + 
				"				INNER JOIN sdm ON sdm.SDM_ID = sdmskill.SDM_ID \r\n" + 
				"				INNER JOIN skills ON skills.SKILL_ID = sdmskill.SKILL_ID  \r\n" + 
				"				INNER JOIN skilltype ON skilltype.SKILLTYPE_ID = sdmskill.SKILLTYPE_ID \r\n" + 
				"			  WHERE sdmskill.SDM_ID IN (  SELECT tabel1.sdm_id FROM (SELECT SDM_ID,SDMSKILL_ID, SKILLTYPE_ID, SKILL_ID, SDMSKILL_VALUE \r\n" + 
				"				FROM sdmskill \r\n" + 
				"				WHERE ");
		
		
		int jumlahData=listParams.size();
		String skilltypeId="";
		String skillId="";
		String sdmskillValue="";
		int jmlhDataskill = 0;
		int jmlhDataCategory = 0;
		int index=1;
		for (Map<String, Object> sdm : listParams) {
			skilltypeId = Convert.toString(sdm.get("skilltype_id"));
			skillId = Convert.toString(sdm.get("skill_id"));
			sdmskillValue = Convert.toString(sdm.get("sdmskill_value"));
			if(skilltypeId != null && skillId != null && sdmskillValue != null) {
				if(index == 1) {
					query.append(" (sdmskill.SKILLTYPE_ID = " + skilltypeId + " AND sdmskill.SKILL_ID = " + skillId + " AND sdmskill.SDMSKILL_VALUE >= " + sdmskillValue + " )");
				}
				else if(index <= jumlahData) {
					query.append(" OR (sdmskill.SKILLTYPE_ID = " + skilltypeId + " AND sdmskill.SKILL_ID = " + skillId + " AND sdmskill.SDMSKILL_VALUE >= " + sdmskillValue + " )");
				}
				jmlhDataskill++;
			}
			else {
				if(index == 1) {
					query.append(" (sdmskill.SKILLTYPE_ID = " + skilltypeId + " )");
				}
				else if(index <= jumlahData) {
					query.append(" OR (sdmskill.SKILLTYPE_ID = " + skilltypeId + "  )");
				}
				jmlhDataCategory++;
			}
			index++;
		}
		
		query.append(") as tabel1\r\n" + 
				"	WHERE tabel1.SKILLTYPE_ID IN (" );
			index=1;
			for (Map<String, Object> sdm : listParams) {
				skilltypeId = Convert.toString(sdm.get("skilltype_id"));
				jumlahData = listParams.size();
				query.append(" " + skilltypeId + " ");
				if(index < jumlahData)
				{	query.append(", ");
				}
				else if(index == jumlahData)
				{ 
					query.append(" ) ");
				}
				index++;
			}
		query.append(" GROUP BY tabel1.SDM_ID HAVING COUNT(DISTINCT tabel1.SKILLTYPE_ID)= " + jumlahData);
		query.append(" ORDER BY tabel1.SDM_ID) ");
		query.append(" AND sdmskill.SKILLTYPE_ID IN ( ");
			index=1;
			for (Map<String, Object> sdm : listParams) {
				skilltypeId = Convert.toString(sdm.get("skilltype_id"));
				jumlahData = listParams.size();
				query.append(" " + skilltypeId + " ");
				if(index < jumlahData)
				{	query.append(", ");
				}
				else if(index == jumlahData)
				{ 
					query.append(" ) ");
				}
				index++;
			}
			
		query.append(" ) as sdms WHERE ");
		index=1;
		for (Map<String, Object> sdm : listParams) {
			skilltypeId = Convert.toString(sdm.get("skilltype_id"));
			skillId = Convert.toString(sdm.get("skill_id"));
			sdmskillValue = Convert.toString(sdm.get("sdmskill_value"));
			if(skilltypeId != null && skillId != null && sdmskillValue != null) {
				if(index == 1) {
					query.append(" (sdms.SKILLTYPE_ID = " + skilltypeId + " AND sdms.SKILL_ID = " + skillId + " AND sdms.SDMSKILL_VALUE >= " + sdmskillValue + " )");
				}
				else if(index <= jumlahData) {
					query.append(" OR (sdms.SKILLTYPE_ID = " + skilltypeId + " AND sdms.SKILL_ID = " + skillId + " AND sdms.SDMSKILL_VALUE >= " + sdmskillValue + " )");
				}
				jmlhDataskill++;
			}
			else {
				if(index == 1) {
					query.append(" (sdms.SKILLTYPE_ID = " + skilltypeId + " )");
				}
				else if(index <= jumlahData) {
					query.append(" OR (sdms.SKILLTYPE_ID = " + skilltypeId + "  )");
				}
				jmlhDataCategory++;
			}
			index++;
		}
//		query.append(" ORDER BY sdm.SDM_NAME");
		System.out.println("query : "+query.toString());
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		System.out.println(lisdata);
    	return lisdata;
    }
    
    public static List<Map> getbySkillsOR(List<Map<String, Object>> listParams)
    {
    	List<Object> params = new ArrayList<Object>();
		StringBuilder query = new StringBuilder();
		query.append("SELECT sdmskill.SDM_ID, sdm.SDM_NAME, sdm.SDM_NIK,  sdm.SDM_ENDCONTRACT,sdmskill.SDMSKILL_iD, sdm.SDM_NAME, skilltype.SKILLTYPE_NAME, skills.SKILL_NAME, sdmskill.SDMSKILL_VALUE, sdm.SDM_STATUS\r\n" + 
				"FROM sdmskill\r\n" + 
				"INNER JOIN sdm ON sdm.SDM_ID = sdmskill.SDM_ID \r\n" + 
				"INNER JOIN skills ON skills.SKILL_ID = sdmskill.SKILL_ID \r\n" + 
				"INNER JOIN skilltype ON skilltype.SKILLTYPE_ID = sdmskill.SKILLTYPE_ID \r\n" +
				"WHERE ");
		
		int index=1;
		int jumlahData=listParams.size();
		
		for (Map<String, Object> sdm : listParams) {
			int skillId = Convert.toInteger(sdm.get("skill_id"));
			if(index == 1) {
				query.append(" sdmskill.SKILL_ID = " + skillId);
			}
			else if(index <= jumlahData) {
				query.append(" OR sdmskill.SKILL_ID = " + skillId);
			}
			index++;
		}
		
		query.append(" ORDER BY sdm.SDM_NAME");
		System.out.println("query : "+query.toString());
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		
    	return lisdata;
		
    }
    
    public static List<Map> getbySkillsAND(List<Map<String, Object>> listParams)
    {
    	List<Object> params = new ArrayList<Object>();
		StringBuilder query = new StringBuilder();
		query.append("SELECT sdmskill.SDM_ID, sdm.SDM_NAME, sdm.SDM_NIK,  sdm.SDM_ENDCONTRACT,sdmskill.SDMSKILL_iD, sdm.SDM_NAME, skilltype.SKILLTYPE_NAME, skills.SKILL_NAME, sdmskill.SDMSKILL_VALUE, sdm.SDM_STATUS\r\n" + 
				"FROM sdmskill\r\n" + 
				"INNER JOIN sdm ON sdm.SDM_ID = sdmskill.SDM_ID \r\n" + 
				"INNER JOIN skills ON skills.SKILL_ID = sdmskill.SKILL_ID \r\n" + 
				"INNER JOIN skilltype ON skilltype.SKILLTYPE_ID = sdmskill.SKILLTYPE_ID \r\n" +
				"WHERE sdmskill.SDM_ID IN ( SELECT sdm_id FROM sdmskill\r\n" + 
				"WHERE SKILL_ID IN ( " );
		int index=1;
		int jumlahData=0;
		int skillId=0;
		for (Map<String, Object> sdm : listParams) {
			skillId = Convert.toInteger(sdm.get("skill_id"));
			jumlahData = listParams.size();
			query.append(" " + skillId + " ");
			if(index < jumlahData)
			{	query.append(", ");
			}
			else if(index == jumlahData)
			{ }
			index++;
		}

		query.append(") GROUP BY SDM_ID HAVING COUNT(DISTINCT SKILL_ID)=" + jumlahData);
		query.append(" ORDER BY SDM_ID) AND sdmskill.SKILL_ID IN (" );
		
		index=1;
		for (Map<String, Object> sdm : listParams) {
			skillId = Convert.toInteger(sdm.get("skill_id"));
			jumlahData = listParams.size();
			query.append(" " + skillId + " ");
			if(index < jumlahData)
			{	query.append(", ");
			}
			else if(index == jumlahData)
			{ 
				query.append(" ) ");
			}
			index++;
		}
		
		System.out.println("query : "+query.toString());
		query.append(" ORDER BY sdm.SDM_NAME");
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		
    	return lisdata;
		
    }
    
    
    
}
