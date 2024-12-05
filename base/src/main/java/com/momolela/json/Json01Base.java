package com.momolela.json;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

public class Json01Base {
    public static void main(String[] args) throws DocumentException {
        // System.out.println(JSONObject.parseObject("nihao"));

        Document document = DocumentHelper.parseText("<?xml version=\"1.0\"?>\n" +
                "<BSXml>\n" +
                "  <MsgHeader>\n" +
                "    <Sender>MRC</Sender>\n" +
                "    <Organization>JG000001</Organization>\n" +
                "    <ServiceType>service</ServiceType>\n" +
                "    <MsgType>ODS_2210</MsgType>\n" +
                "    <MsgVersion>3.3</MsgVersion>\n" +
                "  </MsgHeader>\n" +
                "  <MsgBody>\n" +
                "    <ClpInfos>\n" +
                "      <ClpInfo>\n" +
                "        <BusinessID>JC20241111201753</BusinessID>\n" +
                "        <BusinessCircleID>0</BusinessCircleID>\n" +
                "        <OperatorCode>1356</OperatorCode>\n" +
                "        <OperatorName>吴友义</OperatorName>\n" +
                "        <OperateDeptCode>287</OperateDeptCode>\n" +
                "        <OperateDeptName>(瑞祥)肿瘤放疗科</OperateDeptName>\n" +
                "        <OperateDateTime>20241111T102850</OperateDateTime>\n" +
                "        <FlowCode>0104</FlowCode>\n" +
                "        <FlowName>检查闭环(门诊)</FlowName>\n" +
                "        <OperateTypeCode>01040201</OperateTypeCode>\n" +
                "        <OperateTypeName>检查预约</OperateTypeName>\n" +
                "        <BusinessCompsiteID/>\n" +
                "        <GroupId/>\n" +
                "        <GroupName/>\n" +
                "        <OperateComments/>\n" +
                "      </ClpInfo>\n" +
                "    </ClpInfos>\n" +
                "    <Appointment>\n" +
                "      <Sender>1</Sender>\n" +
                "      <RequestId>JC20241111201753</RequestId>\n" +
                "      <AuthorOrganization DisplayName=\"瑞安市人民医院\">JG000001</AuthorOrganization>\n" +
                "      <Name>蔡兰松</Name>\n" +
                "      <PatientType DisplayName=\"门诊\">1</PatientType>\n" +
                "      <ClinicId>7659713</ClinicId>\n" +
                "      <HospizationId>7659713</HospizationId>\n" +
                "      <DomainID/>\n" +
                "      <SourcePatientId>7659713</SourcePatientId>\n" +
                "      <AppointsHospital>1</AppointsHospital>\n" +
                "      <VisitTimes>null</VisitTimes>\n" +
                "      <VisitId>7659713</VisitId>\n" +
                "      <DeptCode>287</DeptCode>\n" +
                "      <DeptName>(瑞祥)肿瘤放疗科</DeptName>\n" +
                "      <EquipmentBookingId/>\n" +
                "      <EquipmentBookingName/>\n" +
                "      <AdviceId/>\n" +
                "      <ItemCode>141105</ItemCode>\n" +
                "      <ItemName>鼻咽部MR平扫+增强(3T)</ItemName>\n" +
                "      <ExamPart>鼻咽部MR平扫+增强(3T)</ExamPart>\n" +
                "      <ExamMethod/>\n" +
                "      <IsAppointment>1</IsAppointment>\n" +
                "      <AppointsId>94350900</AppointsId>\n" +
                "      <ExamId>JC20241111201753</ExamId>\n" +
                "      <AppointsDateTime>20241114T160600</AppointsDateTime>\n" +
                "      <TimePeriod>2024-11-14 16:00-16:30</TimePeriod>\n" +
                "      <AppointsOrganization DisplayName=\"瑞安市人民医院\">JG000001</AppointsOrganization>\n" +
                "      <ExecutiveDeptCode>307</ExecutiveDeptCode>\n" +
                "      <ExecutiveDeptName>CT MRI</ExecutiveDeptName>\n" +
                "      <AppointsQueue>273</AppointsQueue>\n" +
                "      <AppointsQueueName>瑞祥二号磁共振机房</AppointsQueueName>\n" +
                "      <AppointsGroupName/>\n" +
                "      <AppointsDoctor/>\n" +
                "      <AppointsDoctorName/>\n" +
                "      <AppointsNo>64</AppointsNo>\n" +
                "      <NumberSourceId>43586138</NumberSourceId>\n" +
                "      <ExamIdMerge>0</ExamIdMerge>\n" +
                "      <AppointAddress>瑞祥院区综合楼一楼放射科二号磁共振机房</AppointAddress>\n" +
                "      <AppointAttention>家属陪同，先到MR护士站签字、打留置针，再把单子交给医生。装有心脏起搏器、神经刺激器、胰岛素泵、人工耳蜗、孕妇不能进行3T磁共振检查！</AppointAttention>\n" +
                "      <EmptyStomach>F</EmptyStomach>\n" +
                "      <HoldUrine>F</HoldUrine>\n" +
                "      <IsEmergency>0</IsEmergency>\n" +
                "      <IsSpecialist>0</IsSpecialist>\n" +
                "      <SpecialistNum/>\n" +
                "      <SpecialistCode/>\n" +
                "      <FeeStatus>1</FeeStatus>\n" +
                "      <ReportAddress>瑞祥院区综合楼一楼放射科</ReportAddress>\n" +
                "      <AppointmentEmployeeCode>8598</AppointmentEmployeeCode>\n" +
                "      <AppointmentEmployeeName>金晓芳</AppointmentEmployeeName>\n" +
                "      <AppointChannel>中心预约</AppointChannel>\n" +
                "    </Appointment>\n" +
                "    <Patient>\n" +
                "      <DiagnoseCode>C11.900</DiagnoseCode>\n" +
                "      <Pcard/>\n" +
                "      <DiagnoseName>鼻咽恶性肿瘤</DiagnoseName>\n" +
                "      <SourcePatientId>7659713</SourcePatientId>\n" +
                "      <Name>蔡兰松</Name>\n" +
                "      <SourceVisitId>7659713</SourceVisitId>\n" +
                "      <SourcePatientIdType>OV</SourcePatientIdType>\n" +
                "      <ClinicId>7659713</ClinicId>\n" +
                "      <HospizationId/>\n" +
                "      <AuthorOrganization>JG000001</AuthorOrganization>\n" +
                "    </Patient>\n" +
                "  </MsgBody>\n" +
                "</BSXml>\n");
        System.out.println(Boolean.parseBoolean(document.selectSingleNode("//BSXml/MsgHeader/Sender").getText()));


        // System.out.println(JSONObject.parseObject(
        //         "    {\n" +
        //         "        \"age\": \"27\",\n" +
        //         "        \"bod\": 749836800000,\n" +
        //         "        \"cdBed\": \"305+01B\",\n" +
        //         "        \"cdPi\": \"00000454\",\n" +
        //         "        \"depMedordCd\": \"KS_340\",\n" +
        //         "        \"depPiCd\": \"KS_340\",\n" +
        //         "        \"dtMedordsign\": 1628320163000,\n" +
        //         "        \"emergencyFlag\": 0,\n" +
        //         "        \"empMedordCd\": \"0080\",\n" +
        //         "        \"idMedord\": \"610e319f20f7533a18d12602\",\n" +
        //         "        \"idOrg\": \"60c1dd519cc46a735b612105\",\n" +
        //         "        \"idno\": \"330327199310061719\",\n" +
        //         "        \"inspectSrvIndexList\": [],\n" +
        //         "        \"inspectSrvList\": [\n" +
        //         "            {\n" +
        //         "                \"cdSrvMed\": \"f21010301902\",\n" +
        //         "                \"idSrvMed\": \"60fc1f3969927619fcf793c2\",\n" +
        //         "                \"quanPkgunit\": 1\n" +
        //         "            }\n" +
        //         "        ],\n" +
        //         "        \"naDepMedord\": \"产一科\",\n" +
        //         "        \"naDepPi\": \"产一科\",\n" +
        //         "        \"naEmpMedord\": \"伊胜月\",\n" +
        //         "        \"naPi\": \"黄医技\",\n" +
        //         "        \"noPivcard\": \"202108031022298910\",\n" +
        //         "        \"numVismedIp\": \"00000103\",\n" +
        //         "        \"sdSexCd\": \"1\",\n" +
        //         "        \"sdVistpCd\": \"114\",\n" +
        //         "        \"zfpb\": \"0\"\n" +
        //         "    }"));
    }
}
