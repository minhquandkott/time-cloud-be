package com.ces.intern.apitimecloud.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@SqlResultSetMapping(
        name = "TaskUserTimeMapping",
        classes = {
                @ConstructorResult(
                        targetClass = TaskUserTimeDTO.class,
                        columns = {
                                @ColumnResult(name="task_id", type=Integer.class),
                                @ColumnResult(name="user_id", type=Integer.class),
                                @ColumnResult(name="total_time", type=Float.class)
                        }
                )
        }
)
@NamedNativeQuery(
        name="getTotalTimeOfTaskUserInProject",
        query = "select task_id,user_id, extract (epoch from sum(end_time- start_time)) as total_time from time where user_id =?1 and task_id in (select task_id from task where project_id =?2) group by task_id, user_id",
        resultSetMapping = "TaskUserTimeMapping"
)
public class TaskUserTimeDTO {
    private Integer taskId;
    private Integer userId;
    private Float totalTime;
}
