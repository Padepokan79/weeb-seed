package app.controllers.project;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.functors.IfClosure;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activeweb.annotations.POST;
import org.javalite.common.Convert;

import app.models.SdmSkill;
import app.models.Sdm;
import app.models.SdmHiring;
import app.models.Skill;
import app.models.SkillType;
import core.io.enums.ActionTypes;
import core.io.enums.HttpResponses;
import core.io.helper.JsonHelper;
import core.io.helper.MapHelper;
import core.io.helper.Validation;
import core.io.model.CorePage;
import core.io.model.DTOModel;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;


/*
 * Created by Khairil Anwar
 * 21 Agustus 2018
 *
 * Updated by Hendra Kurniawan
 * 7 September 2018
 */

public class MultiHiringController extends CRUDController<SdmHiring>{
	
	public class InputHiringDTO extends DTOModel{
		public int client_id;
		public int sdm_id;
		public int hirestat_id;
		
	}

	@POST
	public final void multiCreate() {
		try {
			Base.openTransaction();

			response().setActionType(ActionTypes.CREATE);
		
			Map<String, Object> mapRequest = getRequestBody();
			List<Map<String, Object>> params = MapHelper.castToListMap((List<Map>) mapRequest.get("listhiring"));
			List<Map<String, Object>> listHiring = new ArrayList<>();
			
			System.out.println(params.size());
			//filter datasdm dengan id yang sama
			
			listHiring = validateRedudantInput(params);
			System.out.println("Banyak data" + listHiring.size());
			System.out.println(listHiring);
			Integer cv79 = 1;
			
			for (Map<String, Object> hiring : listHiring) {
//				System.out.println("SDM Hiring : " + JsonHelper.toJson(hiring));
				InputHiringDTO sdmhiringDto = new InputHiringDTO ();
				
				System.out.println("SDM Hiring DTO : " + JsonHelper.toJson(sdmhiringDto.toMap()));

				System.out.println("ini data hiring " + hiring);
				
				Integer sdmId = Convert.toInteger(hiring.get("sdm_id"));
				Integer clientId = Convert.toInteger(hiring.get("client_id"));
				Integer hirestatId = Convert.toInteger(hiring.get("hirestat_id"));
				boolean cekData = true, validatebyClient = true, validatebyHireStat = true;
				SdmHiring sdmModel = new SdmHiring();
				//cek validasi : 1 sdm  hanya bisa 1 kali hire di sebuah perusahaan sebelum 
				List<Map> listdata = new ArrayList<>();
				listdata = SdmHiring.getDataSdmbyClient(clientId);
				List<Map> listdataStatSdm = new ArrayList<>();
				listdataStatSdm = SdmHiring.getDataSdmbyHirestat(sdmId);
				System.out.println("Banyak data stat sdm" + listdataStatSdm.size());
				System.out.println("datahire" + listdata);
				int banyakSdmditerima = listdataStatSdm.size();
				int banyakListdata = listdata.size();
				System.out.println(banyakSdmditerima == 0);
				System.out.println(banyakListdata == 0 );
				if(banyakListdata == 0 ) {
					if(banyakSdmditerima == 0) {
						cekData = false;
						sdmhiringDto.fromMap(hiring);
						sdmModel.fromMap(sdmhiringDto.toModelMap());
						System.out.println("1");
					} else {
						for(Map sdmHirestat : listdataStatSdm) {
							System.out.println(sdmHirestat);
							System.out.println(sdmHirestat.size());
							if(sdmId == sdmHirestat.get("sdm_id") && sdmHirestat.get("client_id") != cv79) {
								validatebyHireStat = false;
								System.out.println("2 xd");
								System.out.println("tidak input (hirestat)" + sdmId);
							} else if ( validatebyHireStat == true ){
								cekData = false;
								System.out.println("3 xc");
								System.out.println("input data Hire" + sdmId);
								sdmhiringDto.fromMap(hiring);
								sdmModel.fromMap(sdmhiringDto.toModelMap());
							}
						}
					}
					
				} else {
					
					for(Map dataHire : listdata) {
						cekData = true;
						if(banyakSdmditerima == 0) {
							cekData = false;
							validatebyClient = false;
							sdmhiringDto.fromMap(hiring);
							sdmModel.fromMap(sdmhiringDto.toModelMap());
							System.out.println("4");
						}
						if(sdmId == dataHire.get("sdm_id")) {
							System.out.println("sdm_id " + sdmId);
							System.out.println(dataHire.get("sdm_id"));
							System.out.println("5");
							validatebyClient = false;
							System.out.println("tidak input (by client)" + sdmId);
						} else if ( validatebyClient == true ){
							System.out.println("6");
							for(Map sdmHirestat : listdataStatSdm) {
								System.out.println(sdmHirestat);
								if(sdmId == sdmHirestat.get("sdm_id") && sdmHirestat.get("client_id") != cv79 ) {
									validatebyHireStat = false;
									System.out.println("7");
									System.out.println("tidak input (hirestat)" + sdmId);
								} else if ( validatebyHireStat == true ){
									cekData = false;
									System.out.println("8");
									System.out.println("input data Hire" + sdmId);
									sdmhiringDto.fromMap(hiring);
									sdmModel.fromMap(sdmhiringDto.toModelMap());
								}
							}	
						}
					}
				}
				 //menambah respon field sdmhiring_id untuk FE
				int sdmhiringId=0;
				if (cekData == false) {
					sdmModel.insert();
					listdata = sdmModel.getDataHiringDesc();
					List<Map<String, Object>> newListHiring = new ArrayList<>();
					
					for(Map list: listdata) {
						sdmhiringId = Convert.toInteger(list.get("sdmhiring_id"));
					}
					for(Map<String, Object> list : listHiring) {
						list.put("sdmhiring_id", sdmhiringId);
						newListHiring.add(list);
					}
					
					System.out.println("Inserted Hiring : " + sdmhiringDto.sdm_id);
					response().setResponseBody(HttpResponses.ON_SUCCESS_CREATE, newListHiring);
				}
				else {
					response().setResponseBody(HttpResponses.ERROR);
				}
			}
			
		
//			response().setResponseBody(HttpResponses.ON_CREATE_REDUNDANT_DATA);

			Base.commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			response().setResponseBody(e);
			Base.rollbackTransaction();
		}

		sendResponse();
	}

	public static List<Map<String, Object>> validateRedudantInput(List<Map<String, Object>> listHiring){
		List<Map<String, Object>> newListHiring = new ArrayList<>();
		
		int banyakData = listHiring.size();
		int [] sdmIdRedudant = new int[banyakData];
		int [] sdmId = new int[banyakData];
		int index=0;
	    
		for (Map<String, Object> list : listHiring) {
			sdmIdRedudant[index] = Convert.toInteger(list.get("sdm_id"));
		index++;	
		}
		
		index=0;
		Set<Integer> store = new HashSet<>(); 
		
		for(Integer sdm : sdmIdRedudant) {
			if(store.add(sdm) == true) {
				sdmId[index] = sdm;
				index++;
			}
		}
		
		int [] sdmIdFilter = new int[index];
		for(index=0; index < sdmIdFilter.length; index++) {
			sdmIdFilter[index] = sdmId[index];
			System.out.println("sdm filter :" + sdmIdFilter[index]);
		}
		index=0;
		for(Map<String, Object> list : listHiring) {
			int sdm = Convert.toInteger(list.get("sdm_id"));
			if(sdmId[index] == sdm) {
				newListHiring.add(list); 
						index++;
						
			}
			
		}
		
		return newListHiring;
	}
	
}
