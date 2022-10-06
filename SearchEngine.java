import java.io.IOException;
import java.net.URI;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    String str = "";

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return "String: " + str;
        } 
        else if (url.getPath().contains("/search")){
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                    if (str.contains(parameters[1])) {
                        return str;
                    }
                    else {
                        return "There is no result found!";
                    }
            }
            return "404 Not Found!";
        }
        else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    str += parameters[1];
                    str += "\n";
                    return "String is added by " + parameters[1] + "!" + "It's now " + str;
                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}