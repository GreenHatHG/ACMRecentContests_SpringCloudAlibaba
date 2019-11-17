package team.huoguo.acm.remind.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.huoguo.acm.remind.service.QuartzJobService;
import team.huoguo.acm.commons.dto.Result;
import team.huoguo.acm.commons.dto.ResultFactory;
import team.huoguo.acm.commons.exception.CustomException;
import team.huoguo.acm.commons.utils.JWTUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * @author GreenHatHG
 **/
@RestController
@Validated
public class RemindController {

    @Autowired
    private QuartzJobService quartzJobService;

    @PostMapping("/remind_info")
    public Result addRemind(HttpServletRequest request,
                            @RequestParam @NotBlank @Size(max = 500) String contest,
                            @RequestParam @NotBlank @Size(max = 30) String remindDate,
                            @RequestParam int type,
                            @RequestParam @NotBlank @Size(max = 30) String contact){
        String id = JWTUtil.getId(request.getHeader("Authorization"));
        String s = null;
        try{
            JSONObject jsonObject = JSONUtil.parseObj(contest);
            jsonObject.put("name", URLDecoder.decode(jsonObject.getStr("name"), "UTF-8"));
            s = quartzJobService.addJob(contact, jsonObject, remindDate, id);
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomException(410, "设置定时任务失败，请稍后再试");
        }

        if(!"".equals(s)) {
            return ResultFactory.buildFailResult(s);
        }
        return ResultFactory.buildSuccessResult("设置成功");
    }

    @GetMapping("/remind_infos")
    public Result getRemindInfoById(HttpServletRequest request){
        String id = JWTUtil.getId(request.getHeader("Authorization"));
        List<Map<String, String>> list = null;
        try{
            list = quartzJobService.selectAllByJobGroup(id);
        }catch (Exception e){
            return ResultFactory.buildFailResult("查询失败,请稍后再试");
        }
        return ResultFactory.buildSuccessResult(list);
    }

    @DeleteMapping("/name")
    public Result deleteRemindInfoByName(HttpServletRequest request,
                                         @RequestParam @NotBlank @Size(max = 30) String name){
        String id = JWTUtil.getId(request.getHeader("Authorization"));
        String s = quartzJobService.deleteJob(name, id);
        if(!"".equals(s)){
            return ResultFactory.buildFailResult(s);
        }

        return ResultFactory.buildSuccessResult("删除成功");
    }

    @DeleteMapping("/names")
    public Result deleteRemindInfoByNames(HttpServletRequest request,
                                         @RequestBody JSONObject jsonObject){
        String id = JWTUtil.getId(request.getHeader("Authorization"));
        JSONArray names = jsonObject.getJSONArray("names");
        for(Object name : names){
            String s = quartzJobService.deleteJob(name.toString(), id);
            if(!"".equals(s)){
                return ResultFactory.buildFailResult(s);
            }
        }
        return ResultFactory.buildSuccessResult("删除成功");
    }

    @PutMapping("/info")
    public Result updateRemindInfo(HttpServletRequest request,
                    @RequestParam @NotBlank @Size(max = 500) String contest,
                    @RequestParam @NotBlank @Size(max = 30) String remindDate,
                    @RequestParam int type,
                    @RequestParam @NotBlank @Size(max = 30) String contact){
        String id = JWTUtil.getId(request.getHeader("Authorization"));
        String s = quartzJobService.editJob(contact, remindDate, JSONUtil.parseObj(contest), id);
        if(!"".equals(s)){
            return ResultFactory.buildFailResult(s);
        }

        return ResultFactory.buildSuccessResult("更新成功");
    }

    @PostMapping("actions/pause")
    public Result pauseRemind(HttpServletRequest request,
                              @RequestParam @NotBlank @Size(max = 30) String name){
        String id = JWTUtil.getId(request.getHeader("Authorization"));
        String s = quartzJobService.pauseJob(name, id);
        if(!"".equals(s)){
            return ResultFactory.buildFailResult(s);
        }

        return ResultFactory.buildSuccessResult("暂停成功");
    }

    @PostMapping("actions/resume")
    public Result resumeRemind(HttpServletRequest request,
                              @RequestParam @NotBlank @Size(max = 30) String name){
        String id = JWTUtil.getId(request.getHeader("Authorization"));
        String s = quartzJobService.resumeJob(name, id);
        if(!"".equals(s)){
            return ResultFactory.buildFailResult(s);
        }

        return ResultFactory.buildSuccessResult("重新启动成功");
    }
}
