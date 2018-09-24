package com.usa.centimapa.event;


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
@RequestMapping("/page/event")
public class EventController {

    @Autowired
    private EventService service;

    @RequestMapping("/")
    public String eventList(Model model, RedirectAttributes redir) {
        User user = SignInUtils.getInstance().getCurrentUser();
        if (user == null || !user.isAdmin()) {
            redir.addFlashAttribute("error", "Invalid access!");
            return "redirect:/";
        }
        model.addAttribute("events",service.findAll());
        return "/event/event_list.html";
    }


    @RequestMapping("/create")
    public String create(Model model, RedirectAttributes redir) {
        if (SignInUtils.getInstance().getCurrentUser() == null) {
            return "redirect:/";
        }
        Event event = new Event();
        model.addAttribute("event", event);
        return "/event/event_form.html";
    }

    @RequestMapping("/view")
    public String view(@RequestParam long id, Model model, RedirectAttributes redir) {
        if (SignInUtils.getInstance().getCurrentUser() == null) {
            return "redirect:/";
        }
        Event event = service.findOne(id);
        model.addAttribute("event", event);
        return "/event/event_view";
    }


    @RequestMapping("/edit")
    public String edit(@RequestParam long id, Model model, RedirectAttributes redir) {
        if (SignInUtils.getInstance().getCurrentUser() == null) {
            return "redirect:/";
        }
        Event event = service.findOne(id);
        model.addAttribute("event", event);
        return "/event/event_form";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam long id, RedirectAttributes redir) {
        if (SignInUtils.getInstance().getCurrentUser() == null) {
            redir.addFlashAttribute("error", "Please login!");
            return "redirect:/";
        }
        service.delete(id);
        redir.addFlashAttribute("success", "News deleted!");
        return "redirect:/page/news/";
    }


    @PostMapping("/save")
    public String saveEvent(@ModelAttribute Event event, RedirectAttributes redir) {
        event.setUserId(SignInUtils.getInstance().getCurrentUser().getId());
        service.save(event);
        redir.addFlashAttribute("success", event.getName()+" booked successfully on "+ DateTimeUtil.dateLongToString(event.getDate(), "MMMM dd, yyyy")+".");
        return "redirect:/page/event/";
    }
}
