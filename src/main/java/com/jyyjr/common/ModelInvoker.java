package com.jyyjr.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.dmg.pmml.FieldName;
import org.dmg.pmml.PMML;
import org.jpmml.evaluator.ModelEvaluator;
import org.jpmml.evaluator.ModelEvaluatorFactory;
import org.jpmml.model.PMMLUtil;
import org.xml.sax.SAXException;

/**
 * 读取pmml 获取模型
 * 
 * @author liaotuo
 *
 */
public class ModelInvoker {
	
	private static ModelInvoker modelInvoker = new ModelInvoker();
	
	@SuppressWarnings("rawtypes")
	private static ModelEvaluator modelEvaluator;
	
	public static ModelInvoker getInstance() {
		return modelInvoker;
	}
	
	private ModelInvoker() {
		PMML pmml = null;
		InputStream is = null;
		try {	
			is = ModelInvoker.class.getClassLoader().getResourceAsStream("pmml/randomForest_Model_50tree.pmml");
			long time1 = System.currentTimeMillis();
			pmml = PMMLUtil.unmarshal(is);
			long time2 = System.currentTimeMillis();
			System.out.println("流耗时："+(time2-time1));
			
			modelEvaluator = ModelEvaluatorFactory.newInstance().newModelEvaluator(pmml);
		} catch (SAXException e) {
			pmml = null;
		} catch (JAXBException e) {
			pmml = null;
		} finally {
			try {
				is.close();
			} catch (IOException localIOException3) {
			}
		}
		modelEvaluator.verify();
	}
	
	@SuppressWarnings("unchecked")
	public Map<FieldName, ?> invoke(Map<FieldName, Object> paramsMap) {
		return modelEvaluator.evaluate(paramsMap);
	}
}
