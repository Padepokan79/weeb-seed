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
	
	private static Object sdmskill_value;

	@SuppressWarnings("rawtypes")
	public static List<Map> search(String skill_id, PagingParams pagingParams) {	
		List<Object> params = new ArrayList<Object>();		
		StringBuilder query = new StringBuilder();		

		query.append("SELECT sdm.SDM_NIK, sdm.SDM_NAME, skilltype.SKILLTYPE_NAME, skills.SKILL_NAME, sdmskill.SDMSKILL_VALUE"
					+ "FROM sdmskill "
					+ "INNER JOIN sdm ON sdm.SDM_ID = sdmskill.SDM_ID"
					+ "INNER JOIN skilltype ON skilltype.SKILLTYPE_ID = sdmskill.SKILLTYPE_ID"
					+ "INNER JOIN skills ON skills.SKILL_ID = sdmskill.SKILL_ID");
		
		if (!Strings.isNullOrEmpty(skill_id)) {
			query.append(" where sdmskill.SKILL_ID = ?");
			params.add(skill_id);
			query.append(" AND sdmskill.SDMSKILL_VALUE >= ?");
			params.add(sdmskill_value);
		}
		
		if (!Strings.isNullOrEmpty(pagingParams.filterQuery())) {
			query.append(" OR ");
			query.append(pagingParams.filterQuery());
			params.addAll(Arrays.asList(pagingParams.filterParams()));
		}
		
		if (!Strings.isNullOrEmpty(pagingParams.orderBy())) {
			query.append(" order by ");
			query.append(pagingParams.orderBy());
			
			if (pagingParams.limit() != null && pagingParams.offset() != null) {
				query.append(" OFFSET ");
				query.append(pagingParams.offset());
				query.append(" ROWS");

				query.append(" FETCH NEXT ");
				query.append(pagingParams.limit());
				query.append(" ROWS ONLY");
			}
		}
				
		return Base.findAll(query.toString(), new Object[params.size()]);
	}	

}
