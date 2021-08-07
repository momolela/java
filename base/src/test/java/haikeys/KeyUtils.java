package haikeys;

import org.apache.commons.collections.map.CaseInsensitiveMap;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class KeyUtils {
    public static void main(String[] args) {


//        Map<String,Object> params = new HashMap<>();
//        params.put("SOURCEPATIENTID", "2106021002");
//        params.put("SOURCEPATIENTIDTYPE", "OV");
//        params.put("AUTHORORGANIZATION", "");
//        System.out.println(createPatientId1(params));


//        System.out.println(createPrimaryKeyNew1("2106021002OV47045223-9"));


//        Map<String,Object> params = new HashMap<>();
//        params.put("VISITID", "658770");
//        params.put("PATIENTTYPE", "04");
//        params.put("SOURCEPATIENTID", "658770");
//        params.put("SOURCEPATIENTIDTYPE", "IV");
//        params.put("AUTHORORGANIZATION", "123303004705255657");
//        System.out.println(createVisitID(params));


        Map<String,Object> params = new HashMap<>();
        params.put("SourceId", "");
        params.put("AuthorOrganization", "");
        params.put("RecordClassifying", "TransfusionRecord");
        System.out.println(generateDcId(params));
    }

    public static String createPatientId1(Map<String, Object> head) {
        try {
            String visitStr = "#SOURCEPATIENTID##SOURCEPATIENTIDTYPE##AUTHORORGANIZATION#";
            List<String> strList = new ArrayList<>(Arrays.asList("SOURCEPATIENTID", "SOURCEPATIENTIDTYPE", "AUTHORORGANIZATION"));
            for (String vi : strList) {
                Object vioObject = head.get(vi);
                if (vioObject == null)
                    throw new Exception(vi);
                // 在生成patientId的时候有任何一项是‘-’ 则生成 ‘-’
                if (vioObject.toString().equals("-"))
                    return "-";
                visitStr = visitStr.replace("#" + vi + "#",
                        vioObject.toString());
            }
            return createPrimaryKeyNew(visitStr);
        } catch (Exception e) {
            throw new RuntimeException("生成PATIENTID错误", e);
        }
    }

    public static String createPrimaryKeyNew(String str) {
        String result = str;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String createPrimaryKeyNew1(String str) {
        String result = str;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String createVisitID(Map<String, Object> head) {
        try {
            String visitStr = "#AUTHORORGANIZATION##VISITID##PATIENTTYPE#";
            List<String> visitRuleArg = new ArrayList<>(Arrays.asList("SOURCEPATIENTID", "SOURCEPATIENTIDTYPE", "AUTHORORGANIZATION", "VISITID", "PATIENTTYPE"));
            for (String vi : visitRuleArg) {
                Object vioObject = head.get(vi);
                if (vioObject == null)
                    return null;
                // 在生成visitID的时候有任何一项是‘-’ 则生成 ‘-’
                if (vioObject.toString().equals("-"))
                    return "-";
                visitStr = visitStr.replaceAll("#" + vi + "#",
                        vioObject.toString());
            }
            return createPrimaryKey(visitStr);
        } catch (Exception e) {
            throw new RuntimeException("生成VISITID错误", e);
        }
    }

    public static String createPrimaryKey(String str) {
        String result = str;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {

        }
        return result;
    }

    public static String generateDcId(Map map) {
        String dcId = null;
        try {
            Map<String, Object> map2 = new CaseInsensitiveMap(map);
            dcId = createDCID(map2);
        } catch (Exception e) {
            throw new RuntimeException("生成DcId失败!");
        }
        if (dcId == null || "".equals(dcId))
            throw new RuntimeException("生成的DcId为空!");
        return dcId;
    }

    public static String createDCID(Map<String, Object> head) {
        try {
            String dcidStr = "#AUTHORORGANIZATION##SOURCEID##RECORDCLASSIFYING#";
            List<String> dcidRuleArg = new ArrayList<>(Arrays.asList("AUTHORORGANIZATION", "SOURCEID", "RECORDCLASSIFYING"));
            for (String di : dcidRuleArg) {
                Object dioObject = head.get(di);
                // 生成DCID时不允许任何一项为空
//				if (dioObject == null || dioObject.toString().isEmpty())
//					throw new NullArgumentException(di);

                if (dioObject == null || dioObject.toString().isEmpty()) {
                    dioObject = "";
                }


                dcidStr = dcidStr.replace("#" + di + "#", dioObject.toString());
            }
            return createPrimaryKey(dcidStr);
        } catch (Exception e) {
            throw new RuntimeException("生成DCID错误", e);
        }
    }
}
