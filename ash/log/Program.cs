using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;
using System.Net.Sockets;

namespace log
{
    class Program
    {
        static void Main(string[] args)
        {
            int port = 8081;
            IPEndPoint sender = new IPEndPoint(IPAddress.Any, port);
            UdpClient client = new UdpClient(port);
            byte[] bytes;
            while (true)
            {
                bytes = client.Receive(ref sender);
                Console.Write(Encoding.Default.GetString(bytes));
            }
        }
    }
}
