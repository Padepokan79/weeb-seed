package app.controllers.allocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.javalite.common.Convert;

import app.models.Languages;
import app.models.Sdm;
import app.models.SdmLanguage;
import core.io.helper.Validation;
import core.io.model.CorePage;
import core.io.model.DTOModel;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;

public class MengelolaSDMLanguagesController extends CRUDController<SdmLanguage>{
	public class SdmLanguageDTO extends DTOModel{
        public int sdmlanguageId;
        public String sdmName;
        public String languageName;
    }

    public CorePage customOnReadAll(PagingParams params) throws Exception {        
        List<Map<String, Object>> listMapSdmLanguage = new ArrayList<Map<String, Object>>();
        params.setOrderBy("sdm_id");
        LazyList<SdmLanguage> listSdmLanguage = this.getItems(params);
        
//      LazyList<? extends Model> items = this.getItems(params);
        Long totalItems = this.getTotalItems(params);
            for (SdmLanguage lang : listSdmLanguage) {
                Sdm sdm = lang.parent(Sdm.class);
                Languages languages = lang.parent(Languages.class);

                SdmLanguageDTO dto = new SdmLanguageDTO();
                dto.fromModelMap(lang.toMap());
                dto.languageName = Convert.toString(languages.get("language_name"));
                dto.sdmName = Convert.toString(sdm.get("sdm_name"));
                listMapSdmLanguage.add(dto.toModelMap());
            }
        return new CorePage(listMapSdmLanguage, totalItems);
    }
    
	@Override
	public SdmLanguage customInsertValidation(SdmLanguage item) throws Exception {
		String sdmId = item.getString("sdm_id");
		String languageId = item.getString("language_id");
		
		// Contoh Validasi untuk variable yang harus memiliki nilai / tidak boleh null
		Validation.required(sdmId, "SDM ID tidak boleh kosong!");
		Validation.required(languageId, "Language ID tidak boleh kosong!");

		return super.customInsertValidation(item);
	}
}
