#include <iostream>
#include <cstring>
#include <string>
#include <stdio.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <sys/ioctl.h>
#include <net/if_arp.h>
#include <linux/sockios.h>


std::string ethernet_mactoa(struct sockaddr *addr) {
  static char buff[256];

  unsigned char *ptr = (unsigned char *)addr->sa_data;

  sprintf(buff, "%02X:%02X:%02X:%02X:%02X:%02X",
          (ptr[0] & 0xff), (ptr[1] & 0xff), (ptr[2] & 0xff),
          (ptr[3] & 0xff), (ptr[4] & 0xff), (ptr[5] & 0xff));

  return std::string(buff);
}

int main() {
  std::string address;
  std::cout << "Enter host name/address: ";
  std::cin >> address;

  in_addr_t addr = inet_addr(address.c_str());
  in_addr addr_struct;

  if (addr == INADDR_NONE) {
    std::cerr << "Invalid IP address\n";
    return 1;
  } else {
    addr_struct.s_addr = addr;
  }

  int sock;

  if ((sock = socket(AF_INET, SOCK_DGRAM, 0)) == -1) {
    std::cerr << "Couldn't receive internet socket\n";
    return 1;
  }

  arpreq areq;
  sockaddr_in *sin;

  sin = reinterpret_cast<sockaddr_in *>(&areq.arp_pa);
  sin->sin_family = AF_INET;
  sin->sin_addr = addr_struct;

  sin = reinterpret_cast<sockaddr_in *>(&areq.arp_ha);
  sin->sin_family = ARPHRD_ETHER;

  strncpy(areq.arp_dev, "eth1", 15);

  if (ioctl(sock, SIOCGARP, reinterpret_cast<caddr_t>(&areq)) == -1) {
    std::cerr << "Unable to make ARP request\n";
    return 1;
  }

  std::cout << std::string(
    inet_ntoa(reinterpret_cast<sockaddr_in *>(&areq.arp_pa)->sin_addr)
  ) << "\t" << ethernet_mactoa(&areq.arp_ha) << "\n";

  return 0;
}