import data.Sql2odonatorDao;
import data.Sql2ovolunteerDao;
import models.volunteer1;
import models.donator1;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;
import static spark.Spark.*;
public class App1 {
    public static void main(String[] args){
        Sql2ovolunteerDao volunteer1;
        Sql2odonatorDao donation;

        staticFileLocation("/public");
        String connectionString = "jdbc:postgresql://localhost:5432/gracious";
        Sql2o sql2o = new Sql2o(connectionString, "wecode", "123456");

        volunteer1=new Sql2ovolunteerDao(sql2o);
        donation=new Sql2odonatorDao(sql2o);



        get("/",(request, response) ->{
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine() );

        get("/area",(request, response) ->{
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "area.hbs");
        }, new HandlebarsTemplateEngine() );


        //getting the volunter form
        get("/volunteer/new",((request, response) -> {
            Map<String,Object> model=new HashMap<>();
            return new ModelAndView(model, "volunteer.hbs");
        }), new HandlebarsTemplateEngine());

        //posting the infos in the database
        post("/volunteer/new",((request, response) -> {
            Map<String,Object> model=new HashMap<>();
            String name=request.queryParams("name");
            String email=request.queryParams("email");
            String phone=request.queryParams("phone");
            String message=request.queryParams("message");
            models.volunteer1 volunteer = new volunteer1(name,email,phone,message);
            volunteer1.add(volunteer);
            return new ModelAndView(model, "voluntersuccess.hbs");
        }), new HandlebarsTemplateEngine());

        get("/donate/new",((request, response) -> {
            Map<String,Object> model=new HashMap<>();
            return new ModelAndView(model, "donate.hbs");
        }), new HandlebarsTemplateEngine());


        //posting the donations in the database
        post("/donate/new",((request, response) -> {
            Map<String,Object> model=new HashMap<>();
            String name=request.queryParams("name");
            String email=request.queryParams("email");
            String phone=request.queryParams("phone");
            String donations=request.queryParams("donations");
            donator1 donate = new donator1(name,email,phone,donations);
            donation.add(donate);
            System.out.println(name);
            System.out.println(email);
            System.out.println(phone);
            System.out.println(donations);
            return new ModelAndView(model, "donatesuccess.hbs");
        }), new HandlebarsTemplateEngine());

        get("/About",((request, response) -> {
            Map<String,Object> model=new HashMap<>();
            return new ModelAndView(model, "about.hbs");
        }), new HandlebarsTemplateEngine());
    }
}
