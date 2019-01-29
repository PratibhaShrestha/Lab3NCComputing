import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTimeServerTCP {
    public static void main(String[] args) throws IOException {
        int portNumber = 5555; // Default port to use

        if (args.length > 0) {
            if (args.length == 1)
                portNumber = Integer.parseInt(args[0]);
            else {
                System.err.println("Usage: java DateTimeServerTCP [<port number>]");
                System.exit(1);
            }
        }

        System.out.println("Hi, I am DateTime TCP server");

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


                String outText = getDateTimeString(receivedText);
                // Write the converted uppercase string to the connection socket
                out.println(outText);
                System.out.println("I (Server) [" + connectSocket.getLocalAddress().getHostAddress() + ":" + portNumber + "] > " + outText);
            }
            System.out.println("I am done, Bye!");
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }

    }

    // GETS the Time and Date in needed format , for a searchQuery !!!
    public static String getDateTimeString(String searchQuery) {
        String url = "https://www.worldtimeserver.com/search.aspx?searchfor=";
        String charset = "UTF-8";
        String query = null;
        String returnString = null;
        try {
            query = String.format("%s", URLEncoder.encode(searchQuery, charset));
            URLConnection con = new URL(url + query).openConnection();
            con.setRequestProperty("User-Agent", "javaTestClient 1.0");
            BufferedReader buffIn = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine, outputTime = null, outputDate = "";
            while ((inputLine = buffIn.readLine()) != null) {
                //12:45:25
                Matcher mTime = Pattern.compile("(Server Time: {3}\\d{1,2}:\\d{1,2}:\\d{1,2} (AM|PM))", Pattern.CASE_INSENSITIVE).matcher(inputLine);
                //Wednesday, January 30, 2019
                Matcher mDate = Pattern.compile("(\\w*, \\w* \\d{2}, \\d{4}</span>)", Pattern.CASE_INSENSITIVE).matcher(inputLine);
                while (mTime.find()) {
                    outputTime = mTime.group();
                }

                while (mDate.find()) {
                    outputDate = mDate.group();
                }
                //System.out.println(inputLine);
            }

            returnString = outputDate + outputTime;
            if (returnString != null) {
                returnString = returnString.replace("</span>Server Time:   ", " ");
            }

            return returnString;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }
}
