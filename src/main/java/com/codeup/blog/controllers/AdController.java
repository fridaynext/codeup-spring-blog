package com.codeup.blog.controllers;

import com.codeup.blog.models.Ad;
import com.codeup.blog.repository.AdRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdController {
    private final AdRepository adRepo;

    public AdController(AdRepository adRepo) {
        this.adRepo = adRepo;
    }

    @GetMapping("/ads")
    public String index(Model model) {
        model.addAttribute("ads", adRepo.findAll());
        return "ads/index";
    }

    @GetMapping("/ads/{id}")
    public String showAd(@PathVariable long id, Model model) {
        Ad ad = adRepo.getAdById(id);
        model.addAttribute("ad", ad);
        return "ads/show";
    }

    @GetMapping("/ads/create")
    public String showCreateView() {
        return "ads/create";
    }

    @PostMapping("/ads/create")
    public String createAd(@RequestParam(name = "title") String title,
                           @RequestParam(name = "description") String description,
                           Model model) {
        Ad ad = new Ad(title, description);
        adRepo.save(ad);
        return "redirect:/ads/" + ad.getId();
    }

    @GetMapping("/ads/delete/{id}")
    public String deleteAd(@PathVariable long id, Model model) {
        Ad ad = adRepo.getAdById(id);
        adRepo.delete(ad);
        return "redirect:/ads";
    }

    @GetMapping("/ads/edit/{id}")
    public String editAd(@PathVariable long id, Model model) {
        Ad ad = adRepo.getAdById(id);
        model.addAttribute("ad", ad);
        return "ads/edit";
    }

    @PostMapping("/ads/edit")
    public String updateAd(@RequestParam(name = "id") long id,
                           @RequestParam(name = "title") String title,
                           @RequestParam(name = "description") String description) {
        Ad ad = new Ad();
        ad.setId(id);
        ad.setTitle(title);
        ad.setDescription(description);
        adRepo.save(ad);
        return "redirect:/ads/" + ad.getId();
    }
}
