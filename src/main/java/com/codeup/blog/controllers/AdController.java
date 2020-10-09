package com.codeup.blog.controllers;

import com.codeup.blog.models.Ad;

import com.codeup.blog.models.AdCategory;
import com.codeup.blog.repositories.AdRepository;
import com.codeup.blog.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdController {
    private final AdRepository adDao;
    private final UserRepository userDao;

    public AdController(AdRepository adDao, UserRepository userDao) {
        this.adDao = adDao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/ads", method = RequestMethod.GET)
    public String showAllAds(Model model) {
        model.addAttribute("ads", adDao.findAll());
        return "ads/index";
    }

    @RequestMapping(path = "/ads/{id}", method = RequestMethod.GET)
    public String showSingleAd(@PathVariable long id, Model model) {
        model.addAttribute("ad", adDao.getOne(id));
        return "ads/show";
    }

    @GetMapping("ads/hardcoded/create")
    public String createHardcodedAd() {
        Ad ad = new Ad();
        ad.setTitle("Title to Chevy Silverado, Title only.");
        ad.setDescription("Selling the title to Daddy's Silverado. His car was lost at sea.");
        ad.setCategories(new ArrayList<AdCategory>());
        ad.setOwner(userDao.getOne(1L));
        adDao.save(ad);
        return "redirect:/ads";
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
        adDao.save(ad);
        return "redirect:/ads/" + ad.getId();
    }

    @GetMapping("/ads/delete/{id}")
    public String deleteAd(@PathVariable long id, Model model) {
        Ad ad = adDao.getOne(id);
        adDao.delete(ad);
        return "redirect:/ads";
    }

    @GetMapping("/ads/edit/{id}")
    public String editAd(@PathVariable long id, Model model) {
        Ad ad = adDao.getOne(id);
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
        adDao.save(ad);
        return "redirect:/ads/" + ad.getId();
    }
}
