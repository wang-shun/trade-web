/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.centaline.trans.taskList.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * submit variables and use method of ActivitiRest, then claim the last other candidate tasks
 * @author natsucheng
 * @version $Id: TaskCompleteController.java, v 0.1 2015年8月7日 上午10:16:38 natsucheng Exp $
 */


@Controller
@RequestMapping(value={"/task","/rest/task"})


public class TaskCompleteController {
    @ResponseBody
    @RequestMapping(value="/TaskComplete/{taskId}")   
    public String Submitresponse(@PathVariable String taskId){
        return taskId;

        
    }
    
   

}






/*

        public List<TaskInfoSamp> currentTaskList(Model model, ServletRequest request,@PathVariable String assignee) throws ClientProtocolException, IOException{
            //query list in Activiti runtime and return json code
            
            List<ValidTaskQueryModel> tasks =  RestRequestByHttpClient.queryCurrentTask(assignee);
            
            List<TaskInfoSamp> taskInfoList = new ArrayList();
            
            for(ValidTaskQueryModel task : tasks){
                TaskInfoSamp samp = new TaskInfoSamp(); 
                samp.setTaskModel(task);
                samp.setCaseName("aaaa");
                samp.setManager("xiac");
                taskInfoList.add(samp);
            }
            
             
            //String json=JSONArray.toJSONString(currentTask);
            return taskInfoList;
      
        }*/

    
    