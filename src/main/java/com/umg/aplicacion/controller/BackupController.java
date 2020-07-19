package com.umg.aplicacion.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.umg.aplicacion.Exception.BackupDescriptionOrIdNotFound;
import com.umg.aplicacion.Exception.CustomeFieldValidationException;
import com.umg.aplicacion.entity.Backup;
import com.umg.aplicacion.repository.BackupRepository;
import com.umg.aplicacion.service.BackupService;

@Controller
public class BackupController {

	
	@Autowired
	BackupService backupService;
	
	@Autowired
	BackupRepository backupRepository;
	
	@GetMapping("/backupForm")
	public String backupForm(Model model) {
		model.addAttribute("backupForm", new Backup());
		model.addAttribute("backupList", backupService.getAllBackups());
		model.addAttribute("listTab","active");
		return "backup-form/backup-view";
	}
	
	@PostMapping("/backupForm")
	public String createBackup(@Valid @ModelAttribute("backupForm")Backup backup, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("backupForm", backup);
			model.addAttribute("formTab","active");
		} else {
			try {
				backupService.createBackup(backup);
				model.addAttribute("backupForm", new Backup());
				model.addAttribute("listTab","active");
			} catch (CustomeFieldValidationException cfve) {
			result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
			model.addAttribute("backupForm", backup);
			model.addAttribute("formTab","active");
			model.addAttribute("backupList", backupService.getAllBackups());
		}
			catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				model.addAttribute("backupForm", backup);
				model.addAttribute("formTab","active");
				model.addAttribute("backupList", backupService.getAllBackups());
			}
		}
		
		model.addAttribute("backupList", backupService.getAllBackups());	
		return "backup-form/backup-view";
	}
	
	@GetMapping("/editBackup/{id}")
	public String getEditBackupForm(Model model, @PathVariable(name = "id")Long id)throws Exception {
		Backup backupToEdit = backupService.getBackupById(id);
		
		model.addAttribute("backupForm", backupToEdit);
		model.addAttribute("backupList", backupService.getAllBackups());	
		model.addAttribute("formTab","active");
		model.addAttribute("editMode","true");
		
		return "backup-form/backup-view";
	}
	
	@PostMapping("/editBackup")
	public String postEditBackupForm(@Valid @ModelAttribute("backupForm")Backup backup, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("backupForm", backup);
			model.addAttribute("formTab","active");
			model.addAttribute("editMode","true");
		} else {
			try {
				backupService.updateBackup(backup);
				model.addAttribute("backupForm", new Backup());
				model.addAttribute("listTab","active");
			} catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				model.addAttribute("backupForm", backup);
				model.addAttribute("formTab","active");
				model.addAttribute("backupList", backupService.getAllBackups());
				model.addAttribute("editMode","true");
			}
		}
		
		model.addAttribute("backupList", backupService.getAllBackups());	
		return "backup-form/backup-view";
	}
	
	@GetMapping("/backupForm/cancel")
	public String cancelEditBackup(ModelMap model) {
		return "redirect:/backupForm";
	}
	
	@GetMapping("/deleteBackup/{id}")
	public String deleteBackup(Model model, @PathVariable(name="id")Long id) {
		try {
			backupService.deleteBackup(id);
		} catch (BackupDescriptionOrIdNotFound boin) {
			model.addAttribute("listErrorMessage", boin.getMessage());
		}
		return backupForm(model);
	}
}
