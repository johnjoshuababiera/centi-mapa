package com.usa.centimapa.packages;

import com.usa.centimapa.user.User;
import com.usa.centimapa.utils.DateTimeUtil;
import com.usa.centimapa.utils.SignInUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/page/package")
public class PackageController {

    @Autowired
    private PackageService service;

    @RequestMapping("/")
    public String packageList(Model model, RedirectAttributes redir) {
        User user = SignInUtils.getInstance().getCurrentUser();
        if (user == null || !user.isAdmin()) {
            redir.addFlashAttribute("error", "Invalid access!");
            return "redirect:/";
        }
        model.addAttribute("packages",service.findAll());
        return "/package/package_list.html";
    }

    @RequestMapping("/create")
    public String create(Model model, RedirectAttributes redir) {
        if (SignInUtils.getInstance().getCurrentUser() == null) {
            return "redirect:/";
        }
        Package pkg = new Package();
        model.addAttribute("package", pkg);
        return "/package/package_form.html";
    }


    @RequestMapping("/edit")
    public String edit(@RequestParam long id, Model model, RedirectAttributes redir) {
        if (SignInUtils.getInstance().getCurrentUser() == null) {
            return "redirect:/";
        }
        Package pkg = service.findOne(id);
        model.addAttribute("package", pkg);
        return "/package/package_form";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam long id, RedirectAttributes redir) {
        if (SignInUtils.getInstance().getCurrentUser() == null) {
            redir.addFlashAttribute("error", "Please login!");
            return "redirect:/";
        }
        service.remove(id);
        redir.addFlashAttribute("success", "Package deleted!");
        return "redirect:/page/package/";
    }


    @PostMapping("/save")
    public String savePackage(@ModelAttribute Package pkg, RedirectAttributes redir) {

        try {
            service.save(pkg);
            redir.addFlashAttribute("success", pkg.getName() +" package saved!");
            return "redirect:/page/package/";
        } catch (Exception e) {
            redir.addFlashAttribute("error",e.getMessage());
            return pkg.getId()==null?"redirect:/page/package/create":"redirect:/page/package/edit?id="+pkg.getId();
        }
    }
//
//    @RequestParam("/print")
//    public void print()


}
