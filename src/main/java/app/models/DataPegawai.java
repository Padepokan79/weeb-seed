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
import org.junit.FixMethodOrder;

import com.google.common.base.Strings;

import core.io.model.PagingParams;

@Table("datapegawai")
@IdName("nik")
@BelongsToParents({
	@BelongsTo(foreignKeyName="posisi", parent = Pospeg.class),
	@BelongsTo(foreignKeyName="agama" , parent = TabelAgama.class)
})
public class DataPegawai extends Model {
	public static Pospeg findByUsername(String nik) {		
		return Pospeg.findFirst("nik = ?", nik);
	}
	
	@SuppressWarnings("rawtypes")
	public static List<Map> search(String nik, PagingParams pagingParams) {	
		List<Object> params = new ArrayList<Object>();		
		StringBuilder query = new StringBuilder();		

		query.append("select * from datapegawai");
		
		if (!Strings.isNullOrEmpty(nik)) {
			query.append(" where nik like ?");
			params.add("%"+ nik +"%");
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
	
	public static Long count(String nik, PagingParams pagingParams) {	
		List<Object> params = new ArrayList<Object>();	
		StringBuilder query = new StringBuilder();		

		if (!Strings.isNullOrEmpty(nik)) {
			query.append(" nik like ?");
			params.add("%"+ nik +"%");
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
