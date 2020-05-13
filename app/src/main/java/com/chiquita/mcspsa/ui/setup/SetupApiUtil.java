package com.chiquita.mcspsa.ui.setup;

import com.chiquita.mcspsa.core.CoreConfigurationManager;
import com.chiquita.mcspsa.data.api.request.CoreApiUtil;
import com.chiquita.mcspsa.data.api.request.CoreCommonParameter;
import com.chiquita.mcspsa.data.api.request.CoreTunnelTransform;
import com.chiquita.mcspsa.data.model.CoreUserEntity;
import com.chiquita.mcspsa.ui.bean.SetupBean;

public class SetupApiUtil {

    public static CoreTunnelTransform getCampaignsTransform(CoreUserEntity user, SetupBean bean) {

        CoreCommonParameter p1, p2, p3;

        p1 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_email")
                .setDirection("IN")
                .setType("VARCHAR")
                .setValue(bean.getUserName()).createCommonRequestParameter();

        p2 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_idioma")
                .setDirection("IN")
                .setType("VARCHAR")
                .setValue(CoreConfigurationManager.getInstance().getLanguage()).createCommonRequestParameter();

        p3 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_lista")
                .setDirection("OUT")
                .setType("CURSOR").createCommonRequestParameter();

        CoreTunnelTransform ir = new CoreTunnelTransform();
        ir.setUser(user);
        ir.setParameters(CoreApiUtil.createSingleRequestObject(user, "MABD0005.CAMPAIGN_LIST", "", p1, p2, p3));

        return ir;
    }

    public static CoreTunnelTransform getStoresTransform(CoreUserEntity user, SetupBean bean) {

        CoreCommonParameter p1, p2, p3, p4, p5;

        p1 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_email")
                .setDirection("IN")
                .setType("VARCHAR")
                .setValue(bean.getUserName()).createCommonRequestParameter();

        p2 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_idioma")
                .setDirection("IN")
                .setType("VARCHAR")
                .setValue(CoreConfigurationManager.getInstance().getLanguage()).createCommonRequestParameter();

        p3 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_evento_num")
                .setDirection("IN")
                .setType("NUMBER")
                .setValue(bean.getCampaign().getEventoNum()).createCommonRequestParameter();

        p4 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_evento_inst")
                .setDirection("IN")
                .setType("NUMBER")
                .setValue(bean.getCampaign().getInstNum()).createCommonRequestParameter();

        p5 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_lista")
                .setDirection("OUT")
                .setType("CURSOR").createCommonRequestParameter();

        CoreTunnelTransform ir = new CoreTunnelTransform();
        ir.setUser(user);
        ir.setParameters(CoreApiUtil.createSingleRequestObject(user, "MABD0005.CAMPAIGN_UPFJ_LIST", "", p1, p2, p3, p4, p5));

        return ir;
    }

    public static CoreTunnelTransform getSurveys(CoreUserEntity user, SetupBean bean) {

        CoreCommonParameter p1, p2, p3, p4, p5;

        p1 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_email")
                .setDirection("IN")
                .setType("VARCHAR")
                .setValue(bean.getUserName()).createCommonRequestParameter();

        p2 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_idioma")
                .setDirection("IN")
                .setType("VARCHAR")
                .setValue(CoreConfigurationManager.getInstance().getLanguage()).createCommonRequestParameter();

        p3 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_evento_num")
                .setDirection("IN")
                .setType("NUMBER")
                .setValue(String.valueOf(bean.getEventNum())).createCommonRequestParameter();

        p4 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_evento_inst")
                .setDirection("IN")
                .setType("NUMBER")
                .setValue(String.valueOf(bean.getEventInst())).createCommonRequestParameter();

        p5 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_lista")
                .setDirection("OUT")
                .setType("CURSOR").createCommonRequestParameter();

        CoreTunnelTransform ir = new CoreTunnelTransform();
        ir.setUser(user);
        ir.setParameters(CoreApiUtil.createSingleRequestObject(user, "MABD0005.GET_SURVEYS", "", p1, p2, p3, p4, p5));

        return ir;
    }

    public static CoreTunnelTransform postSchedule(CoreUserEntity user, SetupBean bean) {

        CoreCommonParameter p1, p2, p3, p4, p5, p6, p7, p8;

        p1 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_email")
                .setDirection("IN")
                .setType("VARCHAR")
                .setValue(bean.getUserName()).createCommonRequestParameter();

        p2 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_idioma")
                .setDirection("IN")
                .setType("VARCHAR")
                .setValue(CoreConfigurationManager.getInstance().getLanguage()).createCommonRequestParameter();

        p3 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_evento_num")
                .setDirection("IN")
                .setType("NUMBER")
                .setValue(String.valueOf(bean.getEventNum())).createCommonRequestParameter();

        p4 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_evento_inst")
                .setDirection("IN")
                .setType("NUMBER")
                .setValue(String.valueOf(bean.getEventInst())).createCommonRequestParameter();

        p5 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_pfj_num")
                .setDirection("IN")
                .setType("NUMBER")
                .setValue(String.valueOf(bean.getPfjNum())).createCommonRequestParameter();

        p6 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_upfj_num")
                .setDirection("IN")
                .setType("NUMBER")
                .setValue(String.valueOf(bean.getUpfjNum())).createCommonRequestParameter();

        p7 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_sch_date")
                .setDirection("IN")
                .setType("DATE")
                .setValue(String.valueOf(bean.getSchDate())).createCommonRequestParameter();

        p8 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_mensagem")
                .setDirection("OUT")
                .setType("VARCHAR").createCommonRequestParameter();

        CoreTunnelTransform ir = new CoreTunnelTransform();
        ir.setUser(user);
        ir.setParameters(CoreApiUtil.createSingleRequestObject(user, "MABD0005.INSERT_AGENDA", "", p1, p2, p3, p4, p5, p6, p7, p8));

        return ir;
    }

}
