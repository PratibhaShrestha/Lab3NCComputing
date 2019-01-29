import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class EchoUcaseServerTCP {
    public static void main(String[] args) throws IOException {
        int portNumber = 5555; // Default port to use

        if (args.length > 0) {
            if (args.length == 1)
                portNumber = Integer.parseInt(args[0]);
            else {
                System.err.println("Usage: java EchoUcaseServerTCP [<port number>]");
                System.exit(1);
            }
        }

        System.out.println("Hi, I am EchoUCase TCP server");

        // try() with resource makes sure that all the resources are automatically
        // closed whether there is any exception or not!!!
        try (
                // Create server socket with the given port number
                ServerSocket serverSocket =
                        new ServerSocket(portNumber);
                // create connection socket, server begins listening
                // for incoming TCP requests
                Socket connectSocket = serverSocket.accept();

                // Stream writer to the connection socket
                PrintWriter out =
                        new PrintWriter(connectSocket.getOutputStream(), true);
                // Stream reader from the connection socket
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connectSocket.getInputStream()));
        ) {
            InetAddress clientAddr = connectSocket.getInetAddress();

            int clientPort = connectSocket.getPort();

            String receivedText;

            // read from the connection socket
            while ((receivedText = in.readLine()) != null) {
                System.out.println("Client [" + clientAddr.getHostAddress() + ":" + clientPort + "] > " + receivedText);

                final String USER_AGENT =
                        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2";

                String url="http://www.google.com/search";
                String searchUrl = url + "?q=" + receivedText + "get time" ;

                Connection connection = Jsoup.connect(searchUrl).userAgent(USER_AGENT);

                Document document = connection.get();

                String title = document.title();

            //    System.out.println(title);

                Elements result = document.select("h3.r > a");

                String linkText = result.text();

                System.out.println(linkText);


                //Trenger ikke?
              /*  for (Element results : result) {
                    String linkHref = result.attr("href");
                    String linkText = result.text();

                    System.out.println(linkText);

                }
*/

/*
//Gjør ingenting??
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in)) ;

                String inputLine;
                while ((inputLine = bufferedReader.readLine()) != null){
                    System.out.println(inputLine);
                }
                bufferedReader.close();
*/


          /*      String charset="UTF-8";
                String query = String.format("%s+time",URLEncoder.encode(receivedText, charset));
                URLConnection con = new URL(url+ query).openConnection();
                System.out.println(query);
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                BufferedReader buffIn = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                while ((inputLine = buffIn.readLine()) != null){
                    System.out.println(inputLine);
                }


                buffIn.close();
*/
             //   String outText = "will find you the time, Sir !";
                // Write the converted uppercase string to the connection socket
             //   out.println(outText);
               System.out.println("I (Server) [" + connectSocket.getLocalAddress().getHostAddress() + ":" + portNumber + "] > ");
            }
            System.out.println("I am done, Bye!");
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }

    }
}
