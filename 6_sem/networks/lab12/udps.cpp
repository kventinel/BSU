#include <iostream>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <sys/types.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <cerrno>
#include <iomanip>
#include <sstream>


#define SERVER_PORT_UDP 5001
#define TIMEOUT_SEC 5

int main(int argc, char *argv[]) {
  const int PACKETS_NUM = std::atoi(argv[1]);
  const int PACKET_SIZE = std::atoi(argv[2]);

  int listen_socket;
  sockaddr_in local, client_addr;
  char buffer[256];
  int so_broadcast = 1;  // true
  int retval;
  std::string message;

  // Create a socket
  listen_socket = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP); // UDP socket
  if (listen_socket == -1) {
    std::cerr << "Server: Error Opening socket\n";
    return 1;
  }

  retval = setsockopt(listen_socket, SOL_SOCKET, SO_BROADCAST, &so_broadcast, sizeof(so_broadcast));
  if (retval == -1) {
    std::cerr << "Server: couldn't specify broadcast option";
  }

  struct timeval tv;
  tv.tv_sec = TIMEOUT_SEC;
  tv.tv_usec = 0;
  retval = setsockopt(listen_socket, SOL_SOCKET, SO_RCVTIMEO, &tv, sizeof(tv));
  if (retval == -1) {
    std::cerr << "Server: couldn't specify receive timeout";
  }

  // bind() associates a local address and port combination with the
  // socket just created.
  local.sin_family = AF_INET;
  local.sin_addr.s_addr = INADDR_ANY;
  local.sin_port = htons(SERVER_PORT_UDP);  // Port MUST be in Network Byte Order

  if (bind(listen_socket, reinterpret_cast<sockaddr *>(&local), sizeof(local)) == -1) {
    std::cerr << "bind() failed\n";
    return 1;
  }

  socklen_t client_addr_len = sizeof(client_addr);
  for (int i = 0; i < PACKETS_NUM; i++) {
    retval = recvfrom(listen_socket, buffer, sizeof(buffer), 0,
                      reinterpret_cast<sockaddr *>(&client_addr),
                      &client_addr_len);

    if (!retval || retval == -1) {
      if (errno == EAGAIN || errno == EWOULDBLOCK) {
        std::cout << "Received overall " << i << " packets from client.\n";
        std::cout << "Sending data back\n";
        break;
      }
      std::cerr << "Server: error occurred during recvfrom()\n";
      return 1;
    }
    std::cout << "Received " << retval << " bytes, data ["
              << buffer << "] from client\n";
  }

  for (int i = 0; i < PACKETS_NUM; i++) {
    std::ostringstream ss;
    ss << std::setw(PACKET_SIZE) << std::setfill('0') << i;
    message = ss.str();

    if (sendto(listen_socket, message.c_str(), message.size(), 0,
               reinterpret_cast<sockaddr *>(&client_addr), client_addr_len) == -1) {
      std::cerr << "Server: Error occurred during sendto()\n";
      return 1;
    }
  }

  close(listen_socket);
  return 0;
}


