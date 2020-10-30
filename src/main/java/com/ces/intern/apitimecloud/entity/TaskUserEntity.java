package com.ces.intern.apitimecloud.entity;

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
        name = "TaskUserMapping",
        classes = {
                @ConstructorResult(
                        targetClass = TaskUserEntity.class,
                        columns = {
                                @ColumnResult(name="task_id", type=Integer.class),
                                @ColumnResult(name="user_id", type=Integer.class),
                        }
                )
        }
)
@NamedNativeQuery(
        name="getTaskUserInProject",
        query = "select task_id,user_id from time where user_id =?1 and task_id in (select task_id from task where project_id =?2) group by task_id, user_id",
        resultSetMapping = "TaskUserMapping"
)
public class TaskUserEntity {
    private Integer taskId;
    private Integer userId;
}
