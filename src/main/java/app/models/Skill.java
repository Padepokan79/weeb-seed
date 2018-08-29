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

@Table("skills")
@IdName("skill_id")
@BelongsToParents({
	@BelongsTo(foreignKeyName = "skilltype_id", parent = SkillType.class)
})

public class Skill extends Model {

	@SuppressWarnings("rawtypes")
	public static List<Map> search(String skill_id, PagingParams pagingParams) {	
		List<Object> params = new ArrayList<Object>();		
		StringBuilder query = new StringBuilder();		

		query.append("select * from skills");
		
		if (!Strings.isNullOrEmpty(skill_id)) {
			query.append(" where skill_id like ?");
			params.add("%"+ skill_id +"%");
		}
		
		if (!Strings.isNullOrEmpty(pagingParams.filterQuery())) {
			query.append(" AND ");
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
