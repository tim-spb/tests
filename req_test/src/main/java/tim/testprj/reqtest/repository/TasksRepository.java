package tim.testprj.reqtest.repository;

import org.springframework.data.repository.CrudRepository;
import tim.testprj.reqtest.entity.Task;

public interface TasksRepository extends CrudRepository<Task, Long> {}
