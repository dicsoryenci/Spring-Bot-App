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

import com.umg.aplicacion.Exception.CustomeFieldValidationException;
import com.umg.aplicacion.Exception.SoftwareNameOrIdNotFound;
import com.umg.aplicacion.entity.Software;
import com.umg.aplicacion.repository.EquipmentRepository;
import com.umg.aplicacion.repository.SoftwareRepository;
import com.umg.aplicacion.service.SoftwareService;

@Controller
public class SoftwareController {
	
	@Autowired
	SoftwareService softwareService;
	
	@Autowired
	SoftwareRepository softwareRepository;
	
	@Autowired
	EquipmentRepository equipmentRepository;
	
	@GetMapping("/softwareForm")
	public String softwareForm(Model model) {
		model.addAttribute("softwareForm", new Software());
		model.addAttribute("softwareList", softwareService.getAllSoftwares());
		model.addAttribute("equipments", equipmentRepository.findAll());	
		model.addAttribute("listTab","active");
		return "software-form/software-view";
	}
	
	@PostMapping("/softwareForm")
	public String createSoftware(@Valid @ModelAttribute("softwareForm")Software software, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("softwareForm", software);
			model.addAttribute("formTab","active");
		} else {
			try {
				softwareService.createSoftware(software);
				model.addAttribute("softwareForm", new Software());
				model.addAttribute("listTab","active");
			} catch (CustomeFieldValidationException cfve) {
			result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
			model.addAttribute("softwareForm", software);
			model.addAttribute("formTab","active");
			model.addAttribute("softwareList", softwareService.getAllSoftwares());
			model.addAttribute("equipments", equipmentRepository.findAll());
		}
			catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				model.addAttribute("softwareForm", software);
				model.addAttribute("formTab","active");
				model.addAttribute("softwareList", softwareService.getAllSoftwares());
				model.addAttribute("equipments", equipmentRepository.findAll());
			}
		}
		
		model.addAttribute("softwareList", softwareService.getAllSoftwares());
		model.addAttribute("equipments", equipmentRepository.findAll());
		return "software-form/software-view";
	}
	
	@GetMapping("/editSoftware/{id}")
	public String getSoftwareForm(Model model, @PathVariable(name = "id")Long id)throws Exception {
		Software softwareToEdit = softwareService.getSoftwareById(id);
		
		model.addAttribute("softwareForm", softwareToEdit);
		model.addAttribute("softwareList", softwareService.getAllSoftwares());
		model.addAttribute("equipments", equipmentRepository.findAll());
		model.addAttribute("formTab","active");
		model.addAttribute("editMode","true");
		
		return "software-form/software-view";
	}
	
	@PostMapping("/editSoftware")
	public String postEditSoftwareForm(@Valid @ModelAttribute("equipmentForm")Software software, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("softwareForm", software);
			model.addAttribute("formTab","active");
			model.addAttribute("editMode","true");
		} else {
			try {
				softwareService.updateSoftware(software);
				model.addAttribute("softwareForm", new Software());
				model.addAttribute("listTab","active");
			} catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				model.addAttribute("softwareForm", software);
				model.addAttribute("formTab","active");
				model.addAttribute("softwareList", softwareService.getAllSoftwares());
				model.addAttribute("equipments", equipmentRepository.findAll());
				model.addAttribute("editMode","true");
			}
		}
		
		model.addAttribute("softwareList", softwareService.getAllSoftwares());
		model.addAttribute("equipments", equipmentRepository.findAll());
		return "software-form/software-view";
	}
	
	@GetMapping("/softwareForm/cancel")
	public String cancelEditSoftware(ModelMap model) {
		return "redirect:/softwareForm";
	}
	
	@GetMapping("/deleteSoftware/{id}")
	public String deleteSoftware(Model model, @PathVariable(name="id")Long id) {
		try {
			softwareService.deleteSoftware(id);
		} catch (SoftwareNameOrIdNotFound soin) {
			model.addAttribute("listErrorMessage", soin.getMessage());
		}
		return softwareForm(model);
	}
}
