package com.umg.aplicacion.controller;

import java.util.Date;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.umg.aplicacion.Exception.CustomeFieldValidationException;
import com.umg.aplicacion.dto.AssignUserForm;
import com.umg.aplicacion.dto.ChangePasswordForm;
import com.umg.aplicacion.entity.Request;
import com.umg.aplicacion.entity.RequestDetail;
import com.umg.aplicacion.entity.User;
import com.umg.aplicacion.repository.RequestDetailRepository;
import com.umg.aplicacion.repository.RoleRepository;
import com.umg.aplicacion.service.BackupService;
import com.umg.aplicacion.service.RequestDetailService;
import com.umg.aplicacion.service.RequestService;
import com.umg.aplicacion.service.SoftwareService;
import com.umg.aplicacion.service.UserService;

@Controller
public class RequestController {

	@Autowired
	RequestService requestService;
	
	@Autowired
	RequestDetailService requestDetailService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	BackupService backupService;
	
	@Autowired
	SoftwareService softwareService;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	RequestDetailRepository requestDetailRepository;
	
	@GetMapping("/requestForm")
	public String requestForm(Model model) throws Exception {
		model.addAttribute("requestDetailForm", new RequestDetail());
		model.addAttribute("requestDetailList", requestDetailService.getAllRequestDetails());
		model.addAttribute("backups", backupService.getAllBackups());
		model.addAttribute("softwares", softwareService.getAllSoftwares());
		model.addAttribute("users", userService.getAllUsersByRoles(roleRepository.findByName("ADMIN")));
		model.addAttribute("listTab","active");
		model.addAttribute("assignUserForm", new AssignUserForm());
		return "request-form/request-view";
	}
	
	@PostMapping("/requestForm")
	public String createRequest(@Valid @ModelAttribute("requestDetailForm")RequestDetail requestDetail, BindingResult result, ModelMap model) throws Exception {
		if(result.hasErrors()) {
			model.addAttribute("requestDetailForm", requestDetail);
			model.addAttribute("assignUserForm", new AssignUserForm());
			model.addAttribute("formTab","active");
		} else {
			try {
				Request request = new Request();
				
				//Get date request
				Date applicationDate = new Date();
				request.setApplicationDate(applicationDate);
				
				//Set staturs I = Ingresada, A = Asignada, E = En Proceso, F = Finalizada
				request.setStatus('I');
				
				//Get User Request
				User userRequest = new User();				
				userRequest = userService.getLoggedUser();
				request.setUserRequest(userRequest);
				Request requestGenerated = requestService.createRequest(request);
				
				//Generate Request Detail
				requestDetail.setRequest(requestGenerated);
				
				requestDetailService.createRequestDetail(requestDetail);
				
				model.addAttribute("requestDetailForm", new RequestDetail());
				model.addAttribute("assignUserForm", new AssignUserForm());
				model.addAttribute("listTab","active");
			} catch (CustomeFieldValidationException cfve) {
			result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
			model.addAttribute("requestDetailForm", new RequestDetail());
			model.addAttribute("formTab","active");
			model.addAttribute("requestDetailList", requestDetailService.getAllRequestDetails());
			model.addAttribute("assignUserForm", new AssignUserForm());
			model.addAttribute("backups", backupService.getAllBackups());
			model.addAttribute("softwares", softwareService.getAllSoftwares());
			model.addAttribute("users", userService.getAllUsersByRoles(roleRepository.findByName("ADMIN")));
		}
			catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				model.addAttribute("requestDetailForm", new RequestDetail());
				model.addAttribute("formTab","active");
				model.addAttribute("requestDetailList", requestDetailService.getAllRequestDetails());
				model.addAttribute("assignUserForm", new AssignUserForm());
				model.addAttribute("backups", backupService.getAllBackups());
				model.addAttribute("softwares", softwareService.getAllSoftwares());
				model.addAttribute("users", userService.getAllUsersByRoles(roleRepository.findByName("ADMIN")));
			}
		}
		
		model.addAttribute("requestDetailList", requestDetailService.getAllRequestDetails());
		model.addAttribute("assignUserForm", new AssignUserForm());
		model.addAttribute("backups", backupService.getAllBackups());
		model.addAttribute("softwares", softwareService.getAllSoftwares());
		model.addAttribute("users", userService.getAllUsersByRoles(roleRepository.findByName("ADMIN")));
		return "request-form/request-view";
	}
	
	@GetMapping("/requestForm/cancel")
	public String cancelEditRequest(ModelMap model) {
		return "redirect:/requestForm";
	}
	
	@PostMapping("/requestForm/user")
	public ResponseEntity user(@Valid @RequestBody AssignUserForm form, ModelMap model, Errors errors) {
		try {
			//If error, just return a 400 bad request, along with the error message
	        if (errors.hasErrors()) {
	            String result = errors.getAllErrors()
	                        .stream().map(x -> x.getDefaultMessage())
	                        .collect(Collectors.joining(""));

	            throw new Exception(result);
	        }
			model.addAttribute("requestDetailForm", new RequestDetail());
			model.addAttribute("requestDetailList", requestDetailService.getAllRequestDetails());
			model.addAttribute("backups", backupService.getAllBackups());
			model.addAttribute("softwares", softwareService.getAllSoftwares());
			model.addAttribute("users", userService.getAllUsersByRoles(roleRepository.findByName("ADMIN")));
			model.addAttribute("listTab","active");
			model.addAttribute("assignUserForm", new AssignUserForm(form.getId()));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok("success");
	}
	
	@PostMapping("/requestForm/assignUser")
	public String assignUser(@Valid @ModelAttribute("assignUserForm")AssignUserForm assignUserForm, BindingResult result, ModelMap model) throws Exception {
		if(result.hasErrors()) {
			model.addAttribute("requestDetailForm", new RequestDetail());
			model.addAttribute("assignUserForm", new AssignUserForm(assignUserForm.getId()));
			model.addAttribute("listTab","active");
		} else {
			try {
				Request userToAssign = new Request();
				
				userToAssign = requestService.getRequestById(assignUserForm.getId());
				
				//Get date assignment
				Date assignmentDate = new Date();
				userToAssign.setAssignmentDate(assignmentDate);
				
				//Set staturs I = Ingresada, A = Asignada, E = En Proceso, F = Finalizada
				userToAssign.setStatus('A');
				
				//Assign User
				userToAssign.setOperatorUser(assignUserForm.getOperatorUser());
				
				requestService.createRequest(userToAssign);
				
				model.addAttribute("requestDetailForm", new RequestDetail());
				model.addAttribute("assignUserForm", new AssignUserForm());
				model.addAttribute("listTab","active");
			} catch (CustomeFieldValidationException cfve) {
			result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
			model.addAttribute("requestDetailForm", new RequestDetail());
			model.addAttribute("assignUserForm", new AssignUserForm(assignUserForm.getId()));
			model.addAttribute("listTab","active");
			model.addAttribute("requestDetailList", requestDetailService.getAllRequestDetails());
			model.addAttribute("backups", backupService.getAllBackups());
			model.addAttribute("softwares", softwareService.getAllSoftwares());
			model.addAttribute("users", userService.getAllUsersByRoles(roleRepository.findByName("ADMIN")));
		}
			catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				model.addAttribute("requestDetailForm", new RequestDetail());
				model.addAttribute("assignUserForm", new AssignUserForm(assignUserForm.getId()));
				model.addAttribute("listTab","active");
				model.addAttribute("requestDetailList", requestDetailService.getAllRequestDetails());
				model.addAttribute("backups", backupService.getAllBackups());
				model.addAttribute("softwares", softwareService.getAllSoftwares());
				model.addAttribute("users", userService.getAllUsersByRoles(roleRepository.findByName("ADMIN")));
			}
		}
		
		model.addAttribute("requestDetailList", requestDetailService.getAllRequestDetails());
		model.addAttribute("assignUserForm", new AssignUserForm(assignUserForm.getId()));
		model.addAttribute("backups", backupService.getAllBackups());
		model.addAttribute("softwares", softwareService.getAllSoftwares());
		model.addAttribute("users", userService.getAllUsersByRoles(roleRepository.findByName("ADMIN")));
		return "redirect:/requestForm";
	}
	
	@GetMapping("/markInProcess/{id}")
	public String markInProcessRequestForm(Model model, @PathVariable(name = "id")Long id)throws Exception {
		Request requestToEdit = requestService.getRequestById(id);
		
		//Set staturs I = Ingresada, A = Asignada, E = En Proceso, F = Finalizada
		requestToEdit.setStatus('E');
		
		requestService.createRequest(requestToEdit);
		
		model.addAttribute("requestDetailForm", new RequestDetail());
		model.addAttribute("assignUserForm", new AssignUserForm(id));
		model.addAttribute("listTab","active");
		model.addAttribute("requestDetailList", requestDetailService.getAllRequestDetails());
		model.addAttribute("backups", backupService.getAllBackups());
		model.addAttribute("softwares", softwareService.getAllSoftwares());
		model.addAttribute("users", userService.getAllUsersByRoles(roleRepository.findByName("ADMIN")));
		
		return "redirect:/requestForm";
	}
	
	@GetMapping("/markAsFinished/{id}")
	public String markAsFinishedRequestForm(Model model, @PathVariable(name = "id")Long id)throws Exception {
		Request requestToEdit = requestService.getRequestById(id);
		
		//Get date delivery
		Date deliveryDate = new Date();
		requestToEdit.setDeliveryDate(deliveryDate);
		
		//Set staturs I = Ingresada, A = Asignada, E = En Proceso, F = Finalizada
		requestToEdit.setStatus('F');
		
		requestService.createRequest(requestToEdit);
		
		model.addAttribute("requestDetailForm", new RequestDetail());
		model.addAttribute("assignUserForm", new AssignUserForm(id));
		model.addAttribute("listTab","active");
		model.addAttribute("requestDetailList", requestDetailService.getAllRequestDetails());
		model.addAttribute("backups", backupService.getAllBackups());
		model.addAttribute("softwares", softwareService.getAllSoftwares());
		model.addAttribute("users", userService.getAllUsersByRoles(roleRepository.findByName("ADMIN")));
		
		return "redirect:/requestForm";
	}
	
}
