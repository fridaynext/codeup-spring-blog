package com.codeup.blog.controllers;

import com.codeup.blog.models.Ad;
import com.codeup.blog.models.AdCategory;
import com.codeup.blog.repositories.AdRepository;
import com.codeup.blog.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
}
