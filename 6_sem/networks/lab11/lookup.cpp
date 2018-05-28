#include <iostream>
#include <string>
#include <unistd.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>

int main() {

  //Task 1

  std::cout << "Task1 : search information about host by hostname and host ip:  " << std::endl;

  const int length = 255;
  char name[length];

  if (gethostname(name, length) == 0)
    std::cout << std::endl
         << "Localhost name: " << name << std::endl << std::endl;
  else
    std::cout << "Can't get localhost name" << std::endl << std::endl;

  std::string address;
  std::cout << "Enter host name/address: ";
  std::cin >> address;

  hostent *host;

  in_addr_t addr = inet_addr(address.c_str());

  if (addr != INADDR_NONE) {
    host = gethostbyaddr((char*)&addr, sizeof(in_addr_t), AF_INET);
  }
  else {
    host = gethostbyname(address.c_str());
  }

  if (host != NULL) {
    std::cout << "Host name: " << host->h_name << std::endl;
    int i = 0;
    while (host->h_aliases[i] != NULL) {
      std::cout << "Alias name: " << host->h_aliases[i] << std::endl;
      i++;
    }

    i = 0;

    while (host->h_addr_list[i] != NULL) {
      std::cout << inet_ntoa(*(in_addr*)host->h_addr_list[i])<<std::endl;
      i++;
    }
  }
  else {
    std::cerr << "No information about host" << std::endl;
  }


  std::cout << std::endl << "_________________________________________________________" << std::endl << std::endl;



  return 0;
}

