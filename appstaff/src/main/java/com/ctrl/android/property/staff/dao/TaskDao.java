package com.ctrl.android.property.staff.dao;

import android.util.Log;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.staff.base.Constant;
import com.ctrl.android.property.staff.entity.Img;
import com.ctrl.android.property.staff.entity.Task;
import com.ctrl.android.property.staff.entity.TaskDetail;
import com.ctrl.android.property.staff.http.AopUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务 dao
 * Created by Eric on 2015/10/28.
 */
public class TaskDao extends IDao {

    private List<Task> listTask = new ArrayList<>();

    private TaskDetail taskDetail;

    private List<Img> listImgTask;
    private List<Img> listImgTaskAdd;

    public TaskDao(INetResult activity){
        super(activity);
    }

    /**
     * 1.30、获取任务指派列表
     * @param taskType 任务执行状态（0：执行中、1：已完成）
     * @param communityId 社区id
     * @param staffId 员工id
     * @param currentPage 当前页码
     * @param rowCountPerPage 每页条数
     * */
    public void requestTaskList(String taskType,String communityId,
                             String staffId,String currentPage,String rowCountPerPage){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.taskAssignment.queryTaskAssignmentList");//方法名称

        map.put("taskType",taskType);
        map.put("communityId",communityId);
        map.put("staffId",staffId);
        map.put("currentPage",currentPage);
        map.put("rowCountPerPage",rowCountPerPage);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 0);
    }

    /**
     * 新增任务指派
     * @param communityId 社区id
     * @param staffId 员工id
     * @param taskName 任务名称
     * @param content 任务详情
     * @param adminId 任务下达者id
     * */
    public void requestAddTask(String communityId,String staffId,String taskName
                                ,String content,String adminId){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.taskAssignment.addTaskAssignment");//方法名称

        map.put("communityId",communityId);
        map.put("staffId",staffId);
        map.put("taskName",taskName);
        map.put("content",content);
        map.put("adminId",adminId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 1);
    }

    /**
     * 任务详细
     * @param taskAssignmentId 任务id
     * @param adminId 任务下达者id
     * @param newStaffId 新增任务执行人id（员工id）
     * @param staffId 员工id
     * */
    public void requestTaskDetail(String taskAssignmentId,String adminId,String newStaffId,String staffId){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.taskAssignment.queryTaskAssignmentInfo");//方法名称

        map.put("taskAssignmentId",taskAssignmentId);
        map.put("adminId",adminId);
        map.put("newStaffId",newStaffId);
        map.put("staffId",staffId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 2);
    }

    /**
     * 新增任务指派
     * @param taskAssignmentId 社区id
     * @param newTaskContent 员工id
     * @param newStaffId 任务名称
     * */
    public void requestAddTask(String taskAssignmentId,String newTaskContent,String newStaffId){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.taskAssignment.addNewTaskAssignment");//方法名称

        map.put("taskAssignmentId",taskAssignmentId);
        map.put("newTaskContent",newTaskContent);
        map.put("newStaffId",newStaffId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 3);
    }

    /**
     * 提交反馈
     * @param taskAssignmentId 任务id
     * @param taskFeedback 反馈内容
     * @param taskAssignmentPicStr1 任务指派反馈图片urlString串1（原图、缩略图，url间用逗号分开）
     * @param taskAssignmentPicStr2 任务指派反馈图片urlString串2（原图、缩略图，url间用逗号分开）
     * @param taskAssignmentPicStr3 任务指派反馈图片urlString串3（原图、缩略图，url间用逗号分开）
     * */
    public void requestSubmitFeedback(String taskAssignmentId,String taskFeedback
            ,String taskAssignmentPicStr1,String taskAssignmentPicStr2,String taskAssignmentPicStr3){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.taskAssignment.updateTaskFeedback");//方法名称

        map.put("taskAssignmentId",taskAssignmentId);
        map.put("taskFeedback",taskFeedback);
        map.put("taskAssignmentPicStr1",taskAssignmentPicStr1);
        map.put("taskAssignmentPicStr2",taskAssignmentPicStr2);
        map.put("taskAssignmentPicStr3",taskAssignmentPicStr3);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 4);
    }

    /**
     * 追加任务 提交反馈
     * @param taskAssignmentId 任务id
     * @param newTaskFeedback 反馈内容
     * @param taskAssignmentPicStr1 任务指派反馈图片urlString串1（原图、缩略图，url间用逗号分开）
     * @param taskAssignmentPicStr2 任务指派反馈图片urlString串2（原图、缩略图，url间用逗号分开）
     * @param taskAssignmentPicStr3 任务指派反馈图片urlString串3（原图、缩略图，url间用逗号分开）
     * */
    public void requestSubmitAddFeedback(String taskAssignmentId,String newTaskFeedback
            ,String taskAssignmentPicStr1,String taskAssignmentPicStr2,String taskAssignmentPicStr3){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.taskAssignment.updateNewTaskFeedback");//方法名称

        map.put("taskAssignmentId",taskAssignmentId);
        map.put("newTaskFeedback",newTaskFeedback);
        map.put("taskAssignmentPicStr1",taskAssignmentPicStr1);
        map.put("taskAssignmentPicStr2",taskAssignmentPicStr2);
        map.put("taskAssignmentPicStr3",taskAssignmentPicStr3);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 5);
    }

    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 0){
            Log.d("demo","dao中结果集(任务列表): " + result);
            //staffInfo = JsonUtil.node2pojo(result.findValue("staffInfo"), StaffInfo.class);
            List<Task> list = new ArrayList<>();
            list = JsonUtil.node2pojoList(result.findValue("taskAssignmentList"), Task.class);
            //Log.d("demo","size: " + list.size());
            listTask.addAll(list);
        }

        if(requestCode == 1){
            Log.d("demo","dao中结果集(新增任务): " + result);
            //staffInfo = JsonUtil.node2pojo(result.findValue("staffInfo"), StaffInfo.class);
            listTask = JsonUtil.node2pojoList(result.findValue("taskAssignmentList"), Task.class);
        }

        if(requestCode == 2){
            Log.d("demo", "dao中结果集(任务详细): " + result);
            List<TaskDetail> list = JsonUtil.node2pojoList(result.findValue("taskAssignmentInfo"), TaskDetail.class);
            taskDetail = list.get(0);
            //taskDetail = JsonUtil.node2pojo(result.findValue("taskAssignmentInfo"), TaskDetail.class);
            //staffInfo = JsonUtil.node2pojo(result.findValue("staffInfo"), StaffInfo.class);
            listImgTask = JsonUtil.node2pojoList(result.findValue("communityVisitPictureList"), Img.class);
            listImgTaskAdd = JsonUtil.node2pojoList(result.findValue("newCommunityVisitPictureList"), Img.class);
        }

        if(requestCode == 3){
            Log.d("demo","dao中结果集(新增任务2): " + result);
            //staffInfo = JsonUtil.node2pojo(result.findValue("staffInfo"), StaffInfo.class);
            //listTask = JsonUtil.node2pojoList(result.findValue("taskAssignmentList"), Task.class);
        }

        if(requestCode == 4){
            Log.d("demo","dao中结果集(提交反馈): " + result);
            //staffInfo = JsonUtil.node2pojo(result.findValue("staffInfo"), StaffInfo.class);
            //listTask = JsonUtil.node2pojoList(result.findValue("taskAssignmentList"), Task.class);
        }

        if(requestCode == 5){
            Log.d("demo","dao中结果集(追加反馈): " + result);
            //staffInfo = JsonUtil.node2pojo(result.findValue("staffInfo"), StaffInfo.class);
            //listTask = JsonUtil.node2pojoList(result.findValue("taskAssignmentList"), Task.class);
        }



    }

    public List<Task> getListTask() {
        return listTask;
    }

    public TaskDetail getTaskDetail() {
        return taskDetail;
    }

    public List<Img> getListImgTask() {
        return listImgTask;
    }

    public List<Img> getListImgTaskAdd() {
        return listImgTaskAdd;
    }
}
