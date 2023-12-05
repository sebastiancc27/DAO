package mx.uv;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Hello world!
 *
 */
public class App 
{
    static Gson gson=new Gson();
    static HashMap<String, Usuario> usuarios=new HashMap<>();
    public static void main( String[] args )
    {
        // System.out.println( "Hello World!" );
        // port(80);

        
        package mx.uv;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.UUID;
import com.google.gson.*;

/**
 * Hello world!
 *
 */
public class App 
{
    static Gson gson = new Gson();
    static HashMap<String, Usuario> usuarios = new HashMap<String, Usuario>();
    
    public static void main( String[] args )
    {
        
        // System.out.println( "Hello World!" );

        //port(80);
        port(getHerokuAssignedPort());

        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });
        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

        get("/usuarios", (request,response)->{
            // return "get";
            // response.type("application/json");
            // return gson.toJson(usuarios);
            return gson.toJson(DAO.dameUsuarios());
        });

        post("/usuarios", (request, response)->{
            response.type("application/json");
            String payload = request.body();

            Usuario usuario = gson.fromJson(payload, Usuario.class);
            System.out.println("payload "+payload);
            String id = UUID.randomUUID().toString();
            usuario.setID(id);
            usuarios.put(id, usuario);
            DAO.crearUsuario(usuario);
            // System.out.println("n "+usuario.getNombre());
            // System.out.println("p "+usuario.getPassword());
            // System.out.println("i "+usuario.getId());

            JsonObject respuesta = new JsonObject();
            respuesta.addProperty("msj", "Se creo el usuario");
            respuesta.addProperty("id", id);
            return gson.toJson(respuesta);
        });


    }
}
