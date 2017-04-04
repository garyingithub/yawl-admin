package org.yawlfoundation.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.yawlfoundation.admin.Constant;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by gary on 28/02/2017.
 */
@Controller
@RequestMapping(value = {"/yawl/ib","/yawl/ia"})
public class Dispatcher {

    private Set<String> specificationCommands=new HashSet<>();
    private  Set<String> customServiceCommands=new HashSet<>();
    private  Set<String> userCommands=new HashSet<>();
    private  Set<String> caseCommands=new HashSet<>();
    private Set<String> yawlCommands=new HashSet<>();


    private  String[] specificationCommandsArray={"upload","getList","getSpecificationPrototypesList",
            "getSpecification","taskInformation"};
    private  String[] customServiceCommandsArray={"newYAWLService","getYAWLServices"};
    private String[] userCommandsArray={"connect","getPassword","getAccounts","checkConnection"};
    private String[] yawlCommandsArray={"getBuildProperties"};

    private String[] caseCommandsArray={"launchCase","getAllRunningCases"};


    private Set[] commandSets= new Set[]{specificationCommands, customServiceCommands, userCommands, caseCommands,yawlCommands};
    private  String[] controllersPlaceHolders={Constant.FORWARD_TO_SPECIFICATION,
            Constant.FORWARD_TO_CUSTOMSERVICE,
            Constant.FORWARD_TO_USER,
            Constant.FORWARD_TO_CASE,
            Constant.FORWARD_TO_YAWL};


    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    private void init(){
        specificationCommands.addAll(Arrays.asList(specificationCommandsArray));
        customServiceCommands.addAll(Arrays.asList(customServiceCommandsArray));
        userCommands.addAll(Arrays.asList(userCommandsArray));
        yawlCommands.addAll(Arrays.asList(yawlCommandsArray));
        caseCommands.addAll(Arrays.asList(caseCommandsArray));

    }


    @RequestMapping(method = {RequestMethod.POST,RequestMethod.GET})
    public String processRequest(@RequestParam(value = "action",defaultValue = "")String action){


        for(int i=0;i<commandSets.length;i++){
            Set commandSet=commandSets[i];
            if(commandSet.contains(action)){
//                logger.info(controllersPlaceHolders[i]+"/"+action);
                return controllersPlaceHolders[i]+"/"+action;
            }
        }
        return "plain";
    }

    @RequestMapping(method = RequestMethod.HEAD)
    public void processHead(HttpServletResponse response) {
        response.setContentLength(0);
        response.setStatus(HttpServletResponse.SC_OK);
    }


}
