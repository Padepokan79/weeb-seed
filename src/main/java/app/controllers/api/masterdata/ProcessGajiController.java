package app.controllers.api.masterdata;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.javalite.activejdbc.LazyList;
import org.javalite.activeweb.annotations.GET;
import org.javalite.common.Convert;
import org.omg.PortableInterceptor.SUCCESSFUL;

import app.models.DataPegawai;
import app.models.Pospeg;
import core.io.enums.HttpResponses;
import core.io.model.DTOModel;
import core.javalite.controllers.ProcessController;

public class ProcessGajiController extends ProcessController {
	
	public class HitungGajiDTO extends DTOModel{
		public String nik;
		public String nama;
		public short posisi;
		public long gapok;
		public int tunjangan;
		public int potongan;
		

		public int gajiBersih;
		
		public double gajiKotor() {
			return gapok + tunjangan;
		}
		public double nilaiPajak() {
			return (gapok + tunjangan) * 25 / 100;
		}
		
	}
	
	@GET
	public void hitungGajiPegawai() {
		
		LazyList<DataPegawai> listDataPegawai = DataPegawai.findAll();
		List<Map<String, Object>> listDataGaji = new ArrayList<Map<String, Object>>();
		
		for (DataPegawai dataPegawai : listDataPegawai) {
			Pospeg posPeg = dataPegawai.parent(Pospeg.class);
			
			
			HitungGajiDTO hitGajiDTO = new HitungGajiDTO();
			hitGajiDTO.fromModelMap(dataPegawai.toMap());
//			hitGajiDTO.posisi = Convert.toString(posPeg.get("nmposisi"));
			hitGajiDTO.gapok = Convert.toLong(posPeg.get("gapok"));
//			long gapok = Convert.toLong(posPeg.get("gapok"));
			
			double gajiKotor = hitGajiDTO.gajiKotor();
			double pajak = hitGajiDTO.gajiKotor() * 25/100;
			double totalPotongan = hitGajiDTO.potongan + pajak;
			double gajiBersih =  gajiKotor - totalPotongan;
			
			try {
				DataPegawai datPeg	= new DataPegawai();
				datPeg.fromMap(hitGajiDTO.toMap());
//				datPeg.set("gapok", hitGajiDTO.gapok);
//				datPeg.set("takehomepay", gajiBersih);
				datPeg.update("gapok = ?", "nik = ?", hitGajiDTO.gapok, hitGajiDTO.nik);
				datPeg.update("takehomepay = ?", "nik = ?", gajiBersih, hitGajiDTO.nik);
//				datPeg.upd
				
				response().setResponseBody(HttpResponses.ON_SUCCESS);
			} catch (Exception e) {
				response().setResponseBody(e, 400);
			}
			
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("nik", hitGajiDTO.nik);
			result.put("Gapok", hitGajiDTO.gapok);
			result.put("nama", hitGajiDTO.nama);
			result.put("Posisi", hitGajiDTO.posisi);
			result.put("Gaji Kotor", gajiKotor);
			result.put("Total Potongan", totalPotongan);
			result.put("Gaji Bersih", gajiBersih);
			
			listDataGaji.add(result);
		}
		response().setResponseBody(HttpResponses.ON_SUCCESS, listDataGaji);
		sendResponse();
	}
}
