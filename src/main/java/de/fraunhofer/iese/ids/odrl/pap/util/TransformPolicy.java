package de.fraunhofer.iese.ids.odrl.pap.util;

import de.fraunhofer.iese.ids.odrl.mydata.translator.util.MydataTranslator;
import de.fraunhofer.iese.ids.odrl.policy.library.model.OdrlPolicy;
import de.fraunhofer.iese.ids.odrl.policy.library.model.interfaces.ITranslator;


public class TransformPolicy {
	
	public static String createTechnologyDependentPolicy(OdrlPolicy odrlPolicy, Boolean providerSide){

		if(null == providerSide)
		{
			providerSide = true;
		}
		ITranslator translator = new MydataTranslator();
		try
		{
			return translator.translateComplexPolicy(odrlPolicy);

		}
		catch (NullPointerException e){
			e.printStackTrace();
			return "Please, be aware that your ODRL policy must comply to the IDS profile. " +
					"Undefined IDS Actions or Left Operands are not accepted. " +
					"Check your ODRL policy and try again!";
			
		}

	}

//	 private static String getLastSplitElement(String url) {
//		String value;
//		String[] bits = url.split(":");
//		value = bits[bits.length-1];
//		return value;
//	}
//
//	static Object[] addElement(Object[] a, Object e) {
//		a  = Arrays.copyOf(a, a.length + 1);
//		a[a.length - 1] = e;
//		return a;
//	}
//
//	private static String createCron(String y, String m, String d, String th, String tm, String ts)
//	{
//		String cron = ts + " " + tm + " " + th + " " + d + " " + m + " ? " + y ;
//		return cron;
//	}
}
