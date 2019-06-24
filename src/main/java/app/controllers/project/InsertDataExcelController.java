package app.controllers.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.javalite.activejdbc.Model;
import org.javalite.activeweb.annotations.GET;
import org.javalite.common.Convert;
import org.javalite.http.Get;

import app.adapters.MapAdapter;
import app.models.Course;
import app.models.Education;
import app.models.Employment;
import app.models.Profiling;
import app.models.Sdm;
import app.models.SdmAssignment;
import app.models.SdmLanguage;
import core.javalite.controllers.CommonController;

public class InsertDataExcelController extends CommonController {
	
	MapAdapter lib = new MapAdapter();
	

//	public void insertDataProsesExcel() throws FileNotFoundException {
//		try {
//			String filePath = "D:\\FikryHandsome\\sdm\\web-seed\\assets\\Data_Talent.xlsx" ;// + Convert.toString(param("$filePath")); // menghindari backslash pada param
//				
//			int data = insertData(filePath);
//			
//			response().setResponseBody(HttpResponses.ON_SUCCESS);
//		} catch (Exception e) {
//			response().setResponseBody(e);
//			e.printStackTrace();
//		}
//	}
	
	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	
	@GET
	public void insertDataProsesExcel() throws FileNotFoundException,Exception {
		int data = 0;
		try {
			String filePath = "D:\\FikryHandsome\\sdm\\web-seed\\assets\\Test.xls" ;// + Convert.toString(param("$filePath")); // menghindari backslash pada param
			int sdm_id = 0;
			Workbook workbook = WorkbookFactory.create(new File(filePath));
			Sheet sheet = workbook.getSheetAt(0);
			String key[]= {"no","nama","generasi","nip","tanggalkontrak","dAwal","mAwal","yAwal","endContractThisMonth","tanggalAkhirKontrak",
					"dAkhir","mAkhir","yAkhir","tempatLahir","tanggalLahir","bornOnThisMonth","dLahir","Mlahir","yLahir","noKtp","email","noTelp",
					"alamat","noTelpSaudara","penempatan","status","kodePos","asalSekolah","pendidikan","jurusan","nilaiAptitude","complex","logic","pattern",
					"tanggalValidasiNoTelp","odoo"};
			Iterator<Row> rowIterator = sheet.iterator();
			
			int index = 0;
			while(rowIterator.hasNext()) {
				index=0;
				Row row = rowIterator.next();
				if(row.getRowNum()==0) {
					continue;
				}
				
				Iterator<Cell> cellIterator = row.cellIterator();
				Map<String,Object> map = new HashMap<>();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						if(HSSFDateUtil.isCellDateFormatted(cell)) {
							map.put(key[index], Convert.toSqlDate(cell.getBooleanCellValue()));
						}else {
                        	map.put(key[index], Convert.toInteger(cell.getNumericCellValue()));
						}
						break;
					case Cell.CELL_TYPE_STRING:
                    	map.put(key[index], Convert.toString(cell.getRichStringCellValue()));
                    	break;
					}
					index++;
				}
				
				//insert sdm
				Sdm sdm = new Sdm();
				sdm.set("SDMLVL_ID",1);
				sdm.set("CONTRACTTYPE_ID",1);
				sdm.set("GENDER_ID",1);
				sdm.set("RELIGION_ID",1);
				sdm.set("HEALTH_ID",1);
				sdm.set("SDM_NIK",map.get("nip"));
				sdm.set("SDM_NAME",map.get("nama"));
				sdm.set("SDM_KTP",map.get("noKtp"));
				sdm.set("SDM_ADDRESS",map.get("alamat"));
				sdm.set("SDM_EMAIL",map.get("email"));
				sdm.set("SDM_PLACEBIRTH",map.get("tempatLahir"));
				sdm.set("SDM_DATEBIRTH",map.get("yLahir")+"-"+map.get("Mlahir")+"-"+map.get("dLahir"));
				sdm.set("SDM_POSTCODE",map.get("kodePos"));
				sdm.set("SDM_PHONE",map.get("noTelp"));
				sdm.set("SDM_IMAGE",null);
				sdm.set("SDM_LINKEDIN",null);
				sdm.set("SDM_STARTCONTRACT",map.get("yAwal")+"-"+map.get("mAwal")+"-"+map.get("dAwal"));
				sdm.set("SDM_ENDCONTRACT",map.get("yAkhir")+"-"+map.get("mAkhir")+"-"+map.get("dAkhir"));
				sdm.set("SDM_CONTRACTLOC",map.get("penempatan"));
				sdm.set("SDM_OBJECTIVE",null);
				sdm.set("SDM_STATUS",map.get("status"));
				sdm.set("SDM_BANK",null);
				sdm.insert();
				
				Sdm sdmm = Sdm.findFirst(" sdm_nik = ? ", map.get("nip").toString());

					sdm_id = Convert.toInteger(sdmm.get("sdm_id"));
				
				
				//insert sdmAssigment
			/*	SdmAssignment sdmAssigment = new SdmAssignment();
				sdmAssigment.set("SDMASSIGN_ID");
				sdmAssigment.set("METHOD_ID");
				sdmAssigment.set("SDMHIRING_ID");
				sdmAssigment.set("SDMASSIGN_STARTDATE");
				sdmAssigment.set("SDMASSIGN_ENDDATE");
				sdmAssigment.set("SDMASSIGN_LOC");
				sdmAssigment.set("SDMASSIGN_PICCLIENT");
				sdmAssigment.set("SDMASSIGN_PICCLIENTPHONE");
				sdmAssigment.set("CLIENT_ID");
				sdmAssigment.insert();*/
				
				//insert education
				Education education = new Education();
				education.set("SDM_ID",sdm_id);
				//education.set("DEGREE_ID","?");
				education.set("EDU_NAME",map.get("asalSekolah"));
				education.set("EDU_SUBJECT","?");
				education.set("EDU_STARTDATE",1990);
				education.set("EDU_ENDDATE",1990);
				education.insert();
				
				//insert course
				Course course = new Course();
				course.set("SDM_ID",sdm_id);
				course.set("COURSE_TITLE","insert by system");
				/*course.set("COURSE_PROVIDER","?");
				course.set("COURSE_PLACE","?");
				course.set("COURSE_DATE","?");
				course.set("COURSE_DURATION","?");
				course.set("COURSE_CERTIFICATES","?");*/
				course.insert();
				
				//insert employment
				Employment employment = new Employment();
				employment.set("SDM_ID",sdm_id);
				employment.set("EMPLOYMENT_CORPNAME","insert by system");
				employment.set("EMPLOYMENT_STARTDATE","1990-01-01");
				employment.set("EMPLOYMENT_ENDDATE","1990-01-01");
				employment.set("EMPLOYMENT_ROLEJOB","insert by system");
				employment.insert();
				
				//insert profiling
				Profiling profiling = new Profiling();
				profiling.set("SDM_ID",sdm_id);
				profiling.set("PROFILING_NAME","insert by system");
				profiling.insert();
				
				//insert sdmlanguage
				SdmLanguage sdmlanguage = new SdmLanguage();
				sdmlanguage.set("LANGUAGE_ID",1);
				sdmlanguage.set("SDM_ID",sdm_id);
				sdmlanguage.insert();
				
				data++;

			}
		} catch (Exception e) {
			System.out.println("-=-=-=");
			response().setResponseBody(e);
			e.printStackTrace();
		}
		sendResponse();
	}
}
