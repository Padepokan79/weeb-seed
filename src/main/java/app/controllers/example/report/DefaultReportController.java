package app.controllers.example.report;

import java.util.Map;

import org.javalite.common.JsonHelper;
import org.javalite.common.Util;

import core.javalite.controllers.ReportController;

public class DefaultReportController extends ReportController {
	
	public DefaultReportController() {
		super("report-design/TestReport.jrxml");
	}

	/*
	 *  API :
	 *  localhost:7979/example/report/DefaultReport/generateAsXLSX
	 *  localhost:7979/example/report/DefaultReport/generateAsPDF
	 * 
	 *  Body :
		{
			"content": [
				{
					"deptId": 1,
					"name": "Engineering",
					"budget": 1936760,
					"q1": 445455,
					"q2": 522925,
					"q3": 426087,
					"q4": 542293,
					"deptCode": "Eng",
					"location": "San Francisco",
					"tenantId": 1
				},
				{
					"deptId": 2,
					"name": "Marketing",
					"budget": 1129777,
					"q1": 225955,
					"q2": 271146,
					"q3": 327635,
					"q4": 305040,
					"deptCode": "Mktg",
					"location": "New York",
					"tenantId": 1
				},
				{
					"deptId": 3,
					"name": "General and Admin",
					"budget": 1452570,
					"q1": 435771,
					"q2": 290514,
					"q3": 348617,
					"q4": 377668,
					"deptCode": "G&A",
					"location": "San Francisco",
					"tenantId": 1
				},
				{
					"deptId": 4,
					"name": "Sales",
					"budget": 2743744,
					"q1": 493874,
					"q2": 658499,
					"q3": 713373,
					"q4": 877998,
					"deptCode": "Sales",
					"location": "Austin",
					"tenantId": 1
				},
				{
					"deptId": 5,
					"name": "Professional Services",
					"budget": 806984,
					"q1": 201746,
					"q2": 201746,
					"q3": 177536,
					"q4": 225955,
					"deptCode": "PS",
					"location": "San Francisco",
					"tenantId": 2
				}
			]
		}
	 * 
	 */	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getData() throws Exception {
		String data = Util.read(getRequestInputStream());
		
		return JsonHelper.toMap(data);
	}
}