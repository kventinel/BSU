#include <iostream>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <string>
#include <netdb.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>


#define DEFAULT_PORT 5005

int main() {
  int conn_socket;
  int retval;
  std::string server_name;
  std::string message;
  sockaddr_in server;
  hostent *host;
  in_addr_t addr;
  char buffer[256];

  std::cout << "Input server IP-address\n";
  std::cin >> server_name;

  if (std::isalpha(server_name[0])) {  /* server address is a name */
    host = gethostbyname(server_name.c_str());
  }
  else {  /* Convert nnn.nnn address to a usable one */
    addr = inet_addr(server_name.c_str());
    host = gethostbyaddr(reinterpret_cast<char *>(&addr), sizeof(in_addr_t), AF_INET);
  }

  if (host == nullptr) {
    std::cerr << "Client: Cannot resolve address [" << server_name << "]\n";
    return 1;
  }

  // Create a socket
  conn_socket = socket(AF_INET, SOCK_STREAM, 0);
  if (conn_socket == -1) {
    std::cerr << "Client: Error Opening socket\n";
    return 1;
  }

  // Copy into the sockaddr_in structure
  memcpy(&(server.sin_addr), host->h_addr, host->h_length);
  server.sin_family = host->h_addrtype;
  server.sin_port = htons(DEFAULT_PORT);

  std::cout << "Client connecting to: " << host->h_name << "\n";
  if (connect(conn_socket, reinterpret_cast<sockaddr *>(&server), sizeof(server)) == -1) {
    std::cerr << "connect() failed\n";
    return 1;
  }

  std::cout << "Input message" << std::endl;
  std::getline(std::cin, message);
  std::getline(std::cin, message);

  retval = send(conn_socket, message.c_str(), message.size(), 0);
  if (retval == -1) {
    std::cerr << "send() failed\n";
    return 1;
  }
  std::cout << "Sent Data [" << message.c_str() << "]\n";

  retval = recv(conn_socket, buffer, sizeof(buffer), 0);
  if (retval == -1) {
    std::cerr << "recv() failed\n";
    close(conn_socket);
    return 1;
  }

  if (retval == 0) {
    std::cout << "Server closed connection\n";
    close(conn_socket);
    return 1;
  }
  buffer[retval] = '\0';
  std::cout << "Received " << retval << " bytes, data [" << buffer << "] from server\n";

  std::cout << "Press Enter\n";
  std::cin.get();

  shutdown(conn_socket, 2);
  close(conn_socket);

  return 0;
}
