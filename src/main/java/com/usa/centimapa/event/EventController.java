package com.usa.centimapa.event;


import com.usa.centimapa.user.User;
import com.usa.centimapa.utils.DateTimeUtil;
import com.usa.centimapa.utils.EmailUtil;
import com.usa.centimapa.utils.SignInUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/page/event")
public class EventController {


    @Value("${user.email}") private String userEmail;
    @Value("${user.password}")  private String password;
    @Value("${emal.body.template}")  private String emailBodyTemplate;
    @Autowired private EventService service;

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
        service.remove(id);
        redir.addFlashAttribute("success", "Event deleted!");
        return "redirect:/page/event/";
    }


    @PostMapping("/save")
    public String saveEvent(@ModelAttribute Event event, RedirectAttributes redir) {
        event.setUserId(SignInUtils.getInstance().getCurrentUser().getId());
        try {

            sendMail(event);
            service.save(event);
            redir.addFlashAttribute("success", event.getName()+" booked successfully on "+ DateTimeUtil.dateLongToString(event.getDate(), "MMMM dd, yyyy")+".");
            return "redirect:/page/event/";
        } catch (Exception e) {
            redir.addFlashAttribute("error",e.getMessage());
            return event.getId()==null?"redirect:/page/event/create":"redirect:/page/event/edit?id="+event.getId();
        }
    }

    private void sendMail(@ModelAttribute Event event) {
        try {
            EmailUtil.sendEmail(userEmail, password, event.getClientEmail(), event.getName(), emailBodyTemplate+" "+DateTimeUtil.dateLongToString(event.getDate(),"MMMM dd, yyyy"));
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
