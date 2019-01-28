import java.io.*;
import java.net.*;
import java.util.Hashtable;
//import java.io.IOException;

//public class CLS {
    //public static void main(String... arg) throws IOException, InterruptedException {
    //		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    //}
//}

public class Server
{
	public static void main(String []args)
	{
		try
		{
			// Create a socket on server
			ServerSocket ss = new ServerSocket(555);

			// hashtable to manage list of online and offline users
			Hashtable tOnlineUsers = new Hashtable(10);
			Hashtable tOfflineUsers = new Hashtable(10);

			// ---------------------------------------------------------------
			// Now start accepting connections from clients in a while loop
			// The server should run in an infinite loop
			System.out.print("\033[H\033[2J");
			System.out.flush();
			while(true)
			{
				Socket socket = ss.accept();	// accept connection from client
				System.out.println("\n \nA new client is connected.");

				// to get data to and from server
				InputStream in = socket.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				OutputStream out = socket.getOutputStream();
				PrintWriter pr = new PrintWriter(out, true);

				// read user name from the client and store in table
				// in the format username + socket
				String strUserName = br.readLine();
				System.out.println("Username: " + strUserName + "\n");
				//String  = br.readLine();
				//System.out.println("IP: " + strUserName + "\n");
                                System.out.println("Calculating result and sending answer....\n");
                                String strInt1 = br.readLine();
                                String strInt2 = br.readLine();
                                String strOp = br.readLine();
                                int int1 = Integer.parseInt(strInt1);
                                int int2 = Integer.parseInt(strInt2);
                                char chOp = strOp.charAt(0);
                                String strCalcResult = "";
                                switch(chOp)
                                {
					case '+':
						strCalcResult = "The result is "+(int1+int2); break;
                                        case '-':
                                                strCalcResult = "The result is "+(int1-int2); break;
                                        case '*':
                                                strCalcResult = "The result is "+(int1*int2); break;
                                        case '/':
                                                strCalcResult = "The result is "+(int1/int2); break;
                                        default:
                                                //strCalcResult = "The Operator is invalid."; break;
						strCalcResult = "It is not a valid binary expression, so I can't evaluate it. I have rather reversed your text for fun. Your first number was: "+strInt1+", Your second number was: "+strInt2+" and  Your selected operator was: "+strOp; break;
				}
                                pr.println(strCalcResult);



				//tOnlineUsers.put(strUserName, socket);

				// create a thread to allow simultaneous connections
				//Worker w = new Worker(socket, tOnlineUsers, tOfflineUsers, strUserName);
				//w.start();
			}	// End of while

		}	// End of try
		catch(Exception e)
		{
			System.out.println("Some kind of error has occurred.\n\n");
		}	// End of exception

	}	// End of main()
}	// End of class