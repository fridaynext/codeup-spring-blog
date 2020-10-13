package com.codeup.blog.controllers;

import com.codeup.blog.models.Ad;

import com.codeup.blog.models.User;
import com.codeup.blog.repositories.AdRepository;
import com.codeup.blog.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class AdController {
    private final AdRepository adRepo;
    private final UserRepository userRepo;

    public AdController(AdRepository adRepo, UserRepository userRepo) {
        this.adRepo = adRepo;
        this.userRepo = userRepo;
    }

    @RequestMapping(path = "/ads", method = RequestMethod.GET)
    public String showAllAds(Model model) {
        model.addAttribute("ads", adRepo.findAll());
        return "ads/index";
    }

    @RequestMapping(path = "/ads/{id}", method = RequestMethod.GET)
    public String showSingleAd(@PathVariable long id, Model model) {
        User user = userRepo.findAll().get(0);
        Ad ad = adRepo.getOne(id);
        ad.setOwner(user);
        model.addAttribute("ad", adRepo.getOne(id));
        return "ads/show";
    }

    @GetMapping("/ads/create")
    public String showCreateView(Model model) {
        Ad ad = new Ad();
        model.addAttribute("ad", ad);
        return "ads/create";
    }

    @PostMapping("/ads/create")
    public String createAd(@ModelAttribute Ad ad) {
        adRepo.save(ad);
        return "redirect:/ads/" + ad.getId();
    }

    @GetMapping("/ads/delete/{id}")
    public String deleteAd(@PathVariable long id, Model model) {
        Ad ad = adRepo.getOne(id);
        adRepo.delete(ad);
        return "redirect:/ads";
    }

    @GetMapping("/ads/edit/{id}")
    public String editAd(@PathVariable long id, Model model) {
        Ad ad = adRepo.getOne(id);
        model.addAttribute("ad", ad);
        return "ads/edit";
    }

    @PostMapping("/ads/edit")
    public String updateAd(@ModelAttribute Ad ad) {
        adRepo.save(ad);
        return "redirect:/ads/" + ad.getId();
    }
}
