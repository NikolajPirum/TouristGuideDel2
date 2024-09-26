package gruppe7.turistguide2.Controller;

import gruppe7.turistguide2.Model.Tourist;
import gruppe7.turistguide2.Service.TouristService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/attractions")
public class TouristController {

    public final TouristService touristService;

    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    @GetMapping("")
    public String getAllAttractions(Model model) {
        List<Tourist> allAttractions = touristService.getAllAttractions();
        model.addAttribute("attractions", allAttractions);
        return "attractionList";
    }

    @GetMapping("/{name}")
    public String getAttraction(@PathVariable("name") String name, Model model) {
        Tourist attraction = touristService.getAttractionbyName(name);
        model.addAttribute("attraction", attraction);
        return "attractionNames";
    }

    @GetMapping("{name}/tags")
    public String getTag(@PathVariable("name") String name, Model model) {
        List<String> tags = touristService.getTagsByName(name);
        model.addAttribute("tags", tags);
        return "tags";
    }

    @GetMapping("/add")
    public String addAttraction(Model model) {
        //adding list with tags options
        List<String> attractionTags = touristService.getTagsList();
        //makes a new tourist that will get values from the html
        Tourist attraction = new Tourist();
        model.addAttribute("attractionTags", attractionTags);
        model.addAttribute("attraction", attraction);
        return "addAttraction";
    }
    @PostMapping("/save")
    public String saveAttraction(@ModelAttribute Tourist attraction){
        touristService.AddAttractionsList(attraction);
        return "redirect:/attractions";
        }


    @PostMapping("/{name}/delete")
    public String deleteAttraction(@PathVariable("name") String name) {
        Tourist attraction = touristService.getAttractionbyName(name);
        if(attraction != null) {
            touristService.deleteAttraction(attraction);
        }
        return "redirect:/attractions";
    }

    // Viser formular til at opdatere en attraktion
    @GetMapping("/{name}/edit")
    public String showUpdateForm(@PathVariable("name") String name, Model model) {
        Tourist attraction = touristService.getAttractionbyName(name);
        model.addAttribute("attraction", attraction);
        model.addAttribute("tags",touristService.getTagsList());
        model.addAttribute("towns",touristService.getTownList());
        return "updateAttractions"; // navnet på din HTML-skabelon
    }

    // Håndterer formularindsendelse for opdatering
    @PostMapping("/update")
    public String updateAttraction(@ModelAttribute Tourist attraction) {
        touristService.updateAttraction(attraction);
        return "redirect:/attractions"; // Omdiriger til listen over attraktioner
    }

}
