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
import com.umg.aplicacion.Exception.EquipmentIpOrIdNotFound;
import com.umg.aplicacion.entity.Equipment;
import com.umg.aplicacion.repository.AreaRepository;
import com.umg.aplicacion.repository.EquipmentRepository;
import com.umg.aplicacion.service.EquipmentService;

@Controller
public class EquipmentController {
	
	@Autowired
	EquipmentService equipmentService;
	
	@Autowired
	EquipmentRepository equipmentRepository;
	
	@Autowired
	AreaRepository areaRepository;
	
	@GetMapping("/equipmentForm")
	public String equipmentForm(Model model) {
		model.addAttribute("equipmentForm", new Equipment());
		model.addAttribute("equipmentList", equipmentService.getAllEquipments());
		model.addAttribute("areas", areaRepository.findAll());	
		model.addAttribute("listTab","active");
		return "equipment-form/equipment-view";
	}
	
	@PostMapping("/equipmentForm")
	public String createEquipment(@Valid @ModelAttribute("equipmentForm")Equipment equipment, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("equipmentForm", equipment);
			model.addAttribute("formTab","active");
		} else {
			try {
				equipmentService.createEquipment(equipment);
				model.addAttribute("equipmentForm", new Equipment());
				model.addAttribute("listTab","active");
			} catch (CustomeFieldValidationException cfve) {
			result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
			model.addAttribute("equipmentForm", equipment);
			model.addAttribute("formTab","active");
			model.addAttribute("equipmentList", equipmentService.getAllEquipments());
			model.addAttribute("areas", areaRepository.findAll());
		}
			catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				model.addAttribute("equipmentForm", equipment);
				model.addAttribute("formTab","active");
				model.addAttribute("equipmentList", equipmentService.getAllEquipments());
				model.addAttribute("areas", areaRepository.findAll());
			}
		}
		
		model.addAttribute("equipmentList", equipmentService.getAllEquipments());
		model.addAttribute("areas", areaRepository.findAll());
		return "equipment-form/equipment-view";
	}
	
	@GetMapping("/editEquipment/{id}")
	public String getEquipmentForm(Model model, @PathVariable(name = "id")Long id)throws Exception {
		Equipment equipmentToEdit = equipmentService.getEquipmentById(id);
		
		model.addAttribute("equipmentForm", equipmentToEdit);
		model.addAttribute("equipmentList", equipmentService.getAllEquipments());
		model.addAttribute("areas", areaRepository.findAll());
		model.addAttribute("formTab","active");
		model.addAttribute("editMode","true");
		
		return "equipment-form/equipment-view";
	}
	
	@PostMapping("/editEquipment")
	public String postEditEquipmentForm(@Valid @ModelAttribute("equipmentForm")Equipment equipment, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("equipmentForm", equipment);
			model.addAttribute("formTab","active");
			model.addAttribute("editMode","true");
		} else {
			try {
				equipmentService.updateEquipment(equipment);
				model.addAttribute("equipmentForm", new Equipment());
				model.addAttribute("listTab","active");
			} catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				model.addAttribute("equipmentForm", equipment);
				model.addAttribute("formTab","active");
				model.addAttribute("equipmentList", equipmentService.getAllEquipments());
				model.addAttribute("areas", areaRepository.findAll());
				model.addAttribute("editMode","true");
			}
		}
		
		model.addAttribute("equipmentList", equipmentService.getAllEquipments());
		model.addAttribute("areas", areaRepository.findAll());
		return "equipment-form/equipment-view";
	}
	
	@GetMapping("/equipmentForm/cancel")
	public String cancelEditEquipment(ModelMap model) {
		return "redirect:/equipmentForm";
	}
	
	@GetMapping("/deleteEquipment/{id}")
	public String deleteEquipment(Model model, @PathVariable(name="id")Long id) {
		try {
			equipmentService.deleteEquipment(id);
		} catch (EquipmentIpOrIdNotFound eoin) {
			model.addAttribute("listErrorMessage", eoin.getMessage());
		}
		return equipmentForm(model);
	}
}
