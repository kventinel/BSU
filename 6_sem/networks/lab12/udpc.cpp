#include <iostream>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <sys/types.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <string>
#include <cerrno>
#include <iomanip>
#include <sstream>


#define SERVER_PORT_UDP 5001
#define CLIENT_PORT_UDP 5002
#define SERVER_ADDRESS_UDP "127.0.0.1"

#define TIMEOUT_SEC 10

int main(int argc, char *argv[]) {
  const int PACKETS_NUM = std::atoi(argv[1]);
  const int PACKET_SIZE = std::atoi(argv[2]);

  int listen_socket;
  sockaddr_in local, server_addr;
  int so_broadcast = 1;
  int retval;
  char buffer[256];
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

  in_addr_t server_ip_addr = inet_addr(SERVER_ADDRESS_UDP);
  if (server_ip_addr == INADDR_NONE) {
    std::cerr << "Could not get IP Address\n";
    return 1;
  }
  socklen_t server_addr_len = sizeof(server_addr);
  server_addr.sin_addr.s_addr = server_ip_addr;
  server_addr.sin_family = AF_INET;
  server_addr.sin_port = htons(SERVER_PORT_UDP);

  for (int i = 0; i < PACKETS_NUM; i++) {
    std::ostringstream ss;
    ss << std::setw(PACKET_SIZE) << std::setfill('0') << i;
    message = ss.str();
    if (sendto(listen_socket, message.c_str(), PACKET_SIZE, 0,
               reinterpret_cast<sockaddr *>(&server_addr), server_addr_len) ==
        -1) {
      std::cerr << "Client: Error occurred during sendto()\n";
    }
  }

  for (int i = 0; i < PACKETS_NUM; i++) {
    retval = recvfrom(listen_socket, buffer, sizeof(buffer), 0,
                      reinterpret_cast<sockaddr *>(&server_addr), &server_addr_len);

    if (!retval || retval == -1) {
      if (errno == EAGAIN || errno == EWOULDBLOCK) {
        std::cout << "Received overall " << i << " packets from server.\n";
        break;
      }
      std::cerr << "Client: error occurred during recvfrom()\n";
      return 1;
    }
    std::cout << "Received " << retval << " bytes, data ["
              << buffer << "] from server\n";
  }

  close(listen_socket);
  return 0;
}


