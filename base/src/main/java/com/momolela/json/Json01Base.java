package com.momolela.json;

import com.alibaba.fastjson.JSONObject;

public class Json01Base {
    public static void main(String[] args) {
        // System.out.println(JSONObject.parseObject("nihao"));


        System.out.println(JSONObject.parseObject(
                "    {\n" +
                "        \"age\": \"27\",\n" +
                "        \"bod\": 749836800000,\n" +
                "        \"cdBed\": \"305+01B\",\n" +
                "        \"cdPi\": \"00000454\",\n" +
                "        \"depMedordCd\": \"KS_340\",\n" +
                "        \"depPiCd\": \"KS_340\",\n" +
                "        \"dtMedordsign\": 1628320163000,\n" +
                "        \"emergencyFlag\": 0,\n" +
                "        \"empMedordCd\": \"0080\",\n" +
                "        \"idMedord\": \"610e319f20f7533a18d12602\",\n" +
                "        \"idOrg\": \"60c1dd519cc46a735b612105\",\n" +
                "        \"idno\": \"330327199310061719\",\n" +
                "        \"inspectSrvIndexList\": [],\n" +
                "        \"inspectSrvList\": [\n" +
                "            {\n" +
                "                \"cdSrvMed\": \"f21010301902\",\n" +
                "                \"idSrvMed\": \"60fc1f3969927619fcf793c2\",\n" +
                "                \"quanPkgunit\": 1\n" +
                "            }\n" +
                "        ],\n" +
                "        \"naDepMedord\": \"产一科\",\n" +
                "        \"naDepPi\": \"产一科\",\n" +
                "        \"naEmpMedord\": \"伊胜月\",\n" +
                "        \"naPi\": \"黄医技\",\n" +
                "        \"noPivcard\": \"202108031022298910\",\n" +
                "        \"numVismedIp\": \"00000103\",\n" +
                "        \"sdSexCd\": \"1\",\n" +
                "        \"sdVistpCd\": \"114\",\n" +
                "        \"zfpb\": \"0\"\n" +
                "    }"));
    }
}
