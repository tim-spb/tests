package tim.testprj.reqtest.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tim.testprj.reqtest.entity.Task;
import tim.testprj.reqtest.repository.*;

@RestController
public class TasksController {

	@Autowired
	TasksRepository tasksRepository ;
	
	@PostMapping( value = "/tasks" )
	public ResponseEntity<Task> createTask( @RequestBody String description ) {
		try {
			Task newTask = new Task( description ) ;
			tasksRepository.save( newTask ) ;
			return ResponseEntity.ok( newTask ) ;
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping( value = "/tasks" )
	public ResponseEntity< List<Task> > getAllTasks() {
		try {
			ArrayList< Task > tasks = new ArrayList<Task>( (Collection<? extends Task>) tasksRepository.findAll() ) ;
			return ResponseEntity.ok( tasks ) ;
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping( value = "/tasks/{id}" ) 
	public ResponseEntity< Task > getTask( @PathVariable Long id )  {
		try {
			Optional< Task > task = tasksRepository.findById( id ) ;
			if( task.isPresent() )
				return ResponseEntity.ok( task.get() ) ;
			else
				return ResponseEntity.notFound().build() ;
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping( value = "/tasks/{id}/comments" )
	public ResponseEntity<?> addComment( @PathVariable Long id, @RequestBody String comment ) {
		try {
			Optional< Task > task = tasksRepository.findById( id ) ;
			if( task.isEmpty() )
				return ResponseEntity.notFound().build() ;
			task.get().addComment(comment);
			tasksRepository.save( task.get() ) ;
			return ResponseEntity.ok().build() ;
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping( value="/tasks/{id}/status" )
	public ResponseEntity<?> updateStatus( @PathVariable Long id, @RequestBody Task.Status status ) {
		try {
			Optional< Task > task = tasksRepository.findById( id ) ;
			if( task.isEmpty() )
				return ResponseEntity.notFound().build() ;
			task.get().setStatus(status) ;
			tasksRepository.save( task.get() ) ;
			return ResponseEntity.ok().build() ;
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@DeleteMapping( value = "/tasks/{id}" )
	public ResponseEntity<?> deleteTask( @PathVariable Long id ) {
		try {
			if( tasksRepository.existsById(id) ) {
				tasksRepository.deleteById(id);
				return ResponseEntity.ok().build() ;
			} else
				return ResponseEntity.notFound().build() ;
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
