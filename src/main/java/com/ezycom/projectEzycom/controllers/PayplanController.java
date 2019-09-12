package com.ezycom.projectEzycom.controllers;

import javax.validation.Valid;

import com.ezycom.projectEzycom.entities.Payplan;
import com.ezycom.projectEzycom.repositories.PayplanRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PayplanController {

    @Autowired
    private PayplanRepository payplanRepository;

    @GetMapping("/payplans")
    public String browse(Model model, @RequestParam(defaultValue = "1") int page) {

    page = Math.max(1, page);
    Page<Payplan> payplans = payplanRepository.findAll(PageRequest.of(page-1, 10));
    model.addAttribute("payplans", payplans);
    return "payplans/browse";

    }
   
    @GetMapping("/payplans/create")
    public String create(@ModelAttribute Payplan payplan, Model model) {
        model.addAttribute("payplan", payplan);
        return "/payplans/create";
    }

    @DeleteMapping("/payplans/{id}")
    public String deletePayplan(@PathVariable("id") long id, Model model) {
        payplanRepository.delete(
            payplanRepository.findById(id).get());
        return "redirect:/payplans";
    }

    @PostMapping("/payplans")
    public String validate(@Valid Payplan payplan, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if(bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println(error.toString());
            }
            redirectAttrs.addFlashAttribute("payplan", payplan);
            return "redirect:/payplans/create";
        }
         else {
            payplanRepository.save(payplan);
            return "redirect:/payplans/create";
        }
    }


}