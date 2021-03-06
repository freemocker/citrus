package com.github.yiuman.citrus.workflow.model.impl;

import com.github.yiuman.citrus.workflow.model.WorkflowContext;
import lombok.Builder;
import lombok.Data;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

/**
 * 流程上下文实现
 *
 * @author yiuman
 * @date 2020/12/29
 */
@Builder
@Data
public class WorkflowContextImpl implements WorkflowContext {

    private ProcessInstance processInstance;

    private Task task;

    private String currentUserId;

    private FlowElement flowElement;

}
