package app.controllers.example.process;

import java.util.HashMap;
import java.util.Map;

import org.javalite.activeweb.annotations.GET;

import core.io.enums.HttpResponses;
import core.io.model.DTOModel;
import core.javalite.controllers.ProcessController;

public class DefaultProcessController extends ProcessController {

	public class GajiDTO extends DTOModel {
		public int userId; 
		public String username;

		// Gunakan tipe data long untuk menghandle nilai yang besar
		public long gajiPokok;

		// Gunakan tipe data int/long untuk menghandle nilai null yang didapat dari table 
		// dan menggantinya dengan 0 secara otomatis
		public int potongan;
				
		// Manfaat lain DTO
		public double nilaiPajak() {
			return gajiPokok * 25 / 100;
		}
	}

	@GET
	public void hitungPajak() throws Exception {
		// Asumsi data didapat dari table
		Map<String, Object> dataGajiMaret = new HashMap<String, Object>();
		dataGajiMaret.put("user_id", 1);
		dataGajiMaret.put("gaji_pokok", 99999999999999L);
		dataGajiMaret.put("potongan", null);
		dataGajiMaret.put("username", "Ichione");
		
		// Asumsi data didapat dari table
		Map<String, Object> dataGajiApril = new HashMap<String, Object>();
		dataGajiApril.put("user_id", 1);
		dataGajiApril.put("gaji_pokok", 100000000000000L);
		dataGajiApril.put("potongan", null);
		dataGajiApril.put("username", "Ichione");
		
		GajiDTO gajiMaretDTO = new GajiDTO();
		gajiMaretDTO.fromModelMap(dataGajiMaret);
		
		GajiDTO gajiAprilDTO = new GajiDTO();
		gajiAprilDTO.fromModelMap(dataGajiApril);
		
		double totalPajak = gajiMaretDTO.nilaiPajak() + gajiAprilDTO.nilaiPajak();
		double totalPotongan = gajiMaretDTO.potongan + gajiAprilDTO.potongan;
		double gajiBersih = (gajiMaretDTO.gajiPokok + gajiAprilDTO.gajiPokok) - (totalPajak + totalPotongan);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total_pajak", totalPajak);
		result.put("total_potongan", totalPotongan);
		result.put("gaji_bersih", gajiBersih);
		
		response().setResponseBody(HttpResponses.ON_SUCCESS, result);
		sendResponse();
	}
	
}
