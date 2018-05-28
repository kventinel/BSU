#include <iostream>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <sys/types.h>
#include <unistd.h>
#include <arpa/inet.h>


#define DEFAULT_PORT 5005

int main() {
  int listen_socket, msgsock;
  int retval, fromlen;
  sockaddr_in local, from;
  char buffer[256];
  std::string message;

  // Create a socket
  listen_socket = socket(AF_INET, SOCK_STREAM, 0); // TCP socket
  if (listen_socket == -1) {
    std::cerr << "Server: Error Opening socket\n";
    return 1;
  }

  // bind() associates a local address and port combination with the
  // socket just created.
  local.sin_family = AF_INET;
  local.sin_addr.s_addr = INADDR_ANY;
  local.sin_port = htons(DEFAULT_PORT);  // Port MUST be in Network Byte Order

  if (bind(listen_socket, reinterpret_cast<sockaddr *>(&local), sizeof(local)) == -1) {
    std::cerr << "bind() failed\n";
    return 1;
  }

  // marks the socket as a passive socket
  if (listen(listen_socket, 5) == -1) {
    std::cerr << "listen() failed\n";
    return 1;
  }
  std::cout << "Server listening on port " << DEFAULT_PORT << " , protocol TCP\n";

  fromlen = sizeof(from);

  msgsock = accept(listen_socket, reinterpret_cast<sockaddr *>(&from),
                   reinterpret_cast<socklen_t *>(&fromlen));
  if (msgsock == -1) {
    std::cerr << "accept() error\n";
    return 1;
  }
  std::cout << "accepted connection from " << inet_ntoa(from.sin_addr)
            << ", port " << htons(from.sin_port) << "\n";

  retval = recv(msgsock, buffer, sizeof(buffer), 0);
  if (retval == -1) {
    std::cout << "Server: recv() failed\n";
    close(msgsock);
    return 1;
  }
  if (retval == 0) {
    printf("Client closed connection\n");
    close(msgsock);
    return 1;
  }
  buffer[retval] = '\0';
  std::cout << "Received " << retval << " bytes, data ["
            << buffer << "] from client\n";

  std::cout << "Input message" << std::endl;
  std::getline(std::cin, message);

  retval = send(msgsock, message.c_str(), message.size(), 0);
  if (retval == -1) {
    std::cerr << "Server: send() failed\n";
  }

  std::cout << "Press Enter\n";
  std::cin.get();

  shutdown(msgsock, 2);
  close(msgsock);
  return 0;
}


