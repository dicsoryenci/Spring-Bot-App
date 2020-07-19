package com.umg.aplicacion.controller;

import java.util.Arrays;
import java.util.List;
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

import com.umg.aplicacion.Exception.CustomeFieldValidationException;
import com.umg.aplicacion.entity.Area;
import com.umg.aplicacion.repository.AreaRepository;
import com.umg.aplicacion.service.AreaService;
import com.umg.aplicacion.Exception.AreaNameOrIdNotFound;

@Controller
public class AreaController {
	
	@Autowired
	AreaService areaService;
	
	@Autowired
	AreaRepository areaRepository;
	
	@GetMapping("/areaForm")
	public String areaForm(Model model) {
		model.addAttribute("areaForm", new Area());
		model.addAttribute("areaList", areaService.getAllAreas());
		model.addAttribute("listTab","active");
		return "area-form/area-view";
	}
	
	@PostMapping("/areaForm")
	public String createArea(@Valid @ModelAttribute("areaForm")Area area, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("areaForm", area);
			model.addAttribute("formTab","active");
		} else {
			try {
				areaService.createArea(area);
				model.addAttribute("areaForm", new Area());
				model.addAttribute("listTab","active");
			} catch (CustomeFieldValidationException cfve) {
			result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
			model.addAttribute("areaForm", area);
			model.addAttribute("formTab","active");
			model.addAttribute("areaList", areaService.getAllAreas());
		}
			catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				model.addAttribute("areaForm", area);
				model.addAttribute("formTab","active");
				model.addAttribute("areaList", areaService.getAllAreas());
			}
		}
		
		model.addAttribute("areaList", areaService.getAllAreas());	
		return "area-form/area-view";
	}
	
	@GetMapping("/editArea/{id}")
	public String getEditAreaForm(Model model, @PathVariable(name = "id")Long id)throws Exception {
		Area areaToEdit = areaService.getAreaById(id);
		
		model.addAttribute("areaForm", areaToEdit);
		model.addAttribute("areaList", areaService.getAllAreas());	
		model.addAttribute("formTab","active");
		model.addAttribute("editMode","true");
		
		return "area-form/area-view";
	}
	
	@PostMapping("/editArea")
	public String postEditAreaForm(@Valid @ModelAttribute("areaForm")Area area, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("areaForm", area);
			model.addAttribute("formTab","active");
			model.addAttribute("editMode","true");
		} else {
			try {
				areaService.updateArea(area);
				model.addAttribute("areaForm", new Area());
				model.addAttribute("listTab","active");
			} catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				model.addAttribute("areaForm", area);
				model.addAttribute("formTab","active");
				model.addAttribute("areaList", areaService.getAllAreas());
				model.addAttribute("editMode","true");
			}
		}
		
		model.addAttribute("areaList", areaService.getAllAreas());	
		return "area-form/area-view";
	}
	
	@GetMapping("/areaForm/cancel")
	public String cancelEditArea(ModelMap model) {
		return "redirect:/areaForm";
	}
	
	@GetMapping("/deleteArea/{id}")
	public String deleteArea(Model model, @PathVariable(name="id")Long id) {
		try {
			areaService.deleteArea(id);
		} catch (AreaNameOrIdNotFound aoin) {
			model.addAttribute("listErrorMessage", aoin.getMessage());
		}
		return areaForm(model);
	}
}
