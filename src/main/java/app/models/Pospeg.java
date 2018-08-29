package app.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.google.common.base.Strings;
import core.io.model.PagingParams;

@Table("pospeg")
@IdName("kdposisi")
public class Pospeg extends Model {
	public static Pospeg findByUsername(String nmposisi) {		
		return Pospeg.findFirst("nmposisi = ?", nmposisi);
	}
	
	@SuppressWarnings("rawtypes")
	public static List<Map> search(String nmposisi, PagingParams pagingParams) {	
		List<Object> params = new ArrayList<Object>();		
		StringBuilder query = new StringBuilder();		

		query.append("select * from pospeg");
		
		if (!Strings.isNullOrEmpty(nmposisi)) {
			query.append(" where username like ?");
			params.add("%"+ nmposisi +"%");
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
	
	public static Long count(String nmposisi, PagingParams pagingParams) {	
		List<Object> params = new ArrayList<Object>();	
		StringBuilder query = new StringBuilder();		

		if (!Strings.isNullOrEmpty(nmposisi)) {
			query.append(" nmposisi like ?");
			params.add("%"+ nmposisi +"%");
		}
		
		if (!Strings.isNullOrEmpty(pagingParams.filterQuery())) {
			query.append(" AND ");
			query.append(pagingParams.filterQuery());
			params.addAll(Arrays.asList(pagingParams.filterParams()));
		}

		if (!Strings.isNullOrEmpty(query.toString())) {
			return Pospeg.count(query.toString(), new Object[params.size()]);
		} else {
			return Pospeg.count();
		}
	}
}
