/*
 Creted by 	: Yana
 Time		:
 Update by	: Yana
 Time 		: 27-07-2018
 */

package app.controllers.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.common.Convert;

import app.models.Clients;
import app.models.ProjectMethod;
import app.models.Sdm;
import app.models.SdmAssignment;
import app.models.SdmHiring;
import core.io.model.CorePage;
import core.io.model.DTOModel;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;

public class SdmAssignmentController extends CRUDController<SdmAssignment>{
	public class SdmAssignmentDTO extends DTOModel{
 		public int sdmassignId;
		public String sdmassignLoc;
		public String sdmassignPicclient;
		public String sdmassignPicclientphone;
		public String methodName;
		public int sdmhiringId;
		public String sdmassignStartdate;
 		public String sdmassignEnddate;
	}
	
	@Override
	public CorePage customOnReadAll(PagingParams params) throws Exception{
		
		List<Map<String, Object>> listMapSdmAssignment 	= new ArrayList<Map<String, Object>>();
		LazyList<SdmAssignment> listSdmAssignment 		= SdmAssignment.findAll();	
		List<Map<String, Object>> listMapSdmHiring 		= new ArrayList<Map<String, Object>>();
		LazyList<SdmHiring> listSdmHiring		= SdmHiring.findAll();	

		params.setOrderBy("sdmassign_id");
		Long totalItems = this.getTotalItems(params);
		
		for(SdmAssignment sdmassign : listSdmAssignment) {
			ProjectMethod method 	= sdmassign.parent(ProjectMethod.class);
			SdmHiring hiring 		= sdmassign.parent(SdmHiring.class);
			SdmAssignmentDTO dto = new SdmAssignmentDTO();
			dto.fromModelMap(sdmassign.toMap());
			dto.methodName 	= Convert.toString(method.get("method_name"));
			dto.sdmassignStartdate = Convert.toString(sdmassign.get("sdmassign_startdate"));
			dto.sdmassignEnddate = Convert.toString(sdmassign.get("sdmassign_enddate"));
			listMapSdmAssignment.add(dto.toModelMap());
		}
		
		return new CorePage(listMapSdmAssignment, totalItems);
	}
}

