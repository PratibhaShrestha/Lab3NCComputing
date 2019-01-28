import java.io.*;
import java.net.*;
import java.util.Hashtable;

public class Client
{

	public static void main(String []args)
	{
		// Declarations to get input from keyboard
		// ---------------------------------------
		int port=555;			// server port
		String strPort="",		// server port
		ip="",			// IP of server
		strUserName="";	// User name of client

		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		// Get ip, port & user name from the client
		try
		{
			System.out.print("\033[H\033[2J");
			System.out.flush();

			// get IP from the user
			ip = "127.0.0.1";	// default IP

			// get port from user
			port = 555;			// default port



			// Declarations to manage connection
			// ---------------------------------
			String strAnother = "z",	// looping variable
			strInt1 = "10",		// First integer
			strInt2 = "10",		// Second integer
			strOp = "-",			// Operator
			strResult="";



			// get user name from the client
			strUserName = "temp";
			do
			{
				System.out.print("Enter User Name (mandatory): ");
				strUserName = input.readLine();
			}
			while (strUserName.equals(""));	// repeat until valid user name is given


			// get first number
			System.out.print("Enter First Number: ");
			strInt1 = input.readLine();
			if (strInt1.equals(""))
				strInt1 = "10";

			// get second number
			System.out.print("Enter Second Number: ");
			strInt2 = input.readLine();
			if (strInt2.equals(""))
				strInt2 = "10";

			// get operator
			System.out.print("Enter Operator: ");
			strOp = input.readLine();
			if (strOp.equals(""))
				strOp = "-";


			// create a new socket
			Socket socket = new Socket(ip, port);

			// Connection successfull at this point, so inform user about this
			System.out.print("\n\n\t\tConnection was successful.\n\t\t----------------------\n\n");

			// to get data to and from server
			InputStream in = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			OutputStream out = socket.getOutputStream();
			PrintWriter pr = new PrintWriter(out, true);

			// send user name to the server
			pr.println(strUserName);

//			Socket socket = new Socket(ip, port);
//			pr.println("z");

			// write 2 integers and operator to the server
			pr.println(strInt1);
			pr.println(strInt2);
			pr.println(strOp);

			// get result from the server
			strResult = br.readLine();
			System.out.println(strResult);

			// At this point client wants to disconnect from the server,
			// so close the connection
			System.out.print("\nConnection Closing.......\n");

			socket.close();

		}	// End of try
		catch(Exception e)
		{
			System.out.println("Some kind of error has occurred.");
			System.exit(0);
		}	// End of exception

	}	// End of main()
}	// End of class