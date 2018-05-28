#include <errno.h>
#include <stdio.h>
#include <sys/socket.h>
#include <sys/ioctl.h>
#include <net/if_arp.h>
#include <netinet/in.h>
#include <linux/sockios.h>

static char *ethernet_mactoa(struct sockaddr *addr) 
{ 
	static char buff[256]; 
	unsigned char *ptr = (unsigned char *) addr->sa_data;

	sprintf(buff, "%02X:%02X:%02X:%02X:%02X:%02X", 
		(ptr[0] & 0377), (ptr[1] & 0377), (ptr[2] & 0377), 
		(ptr[3] & 0377), (ptr[4] & 0377), (ptr[5] & 0377)); 

return (buff); 

} 


int main(int argc, char *argv[]) {
	int                 s;
	struct arpreq       areq;
	struct sockaddr_in *sin;
	struct in_addr      ipaddr;

	if (argc < 2 || argc > 2) {
		fprintf(stderr, "-- Usage: %s ipaddress\n", argv[0]);
		exit(1);
	}
	
	/* Get an internet domain socket. */
	if ((s = socket(AF_INET, SOCK_DGRAM, 0)) == -1) {
		perror("socket");
		exit(1);
	}
	
	/* Make the ARP request. */
	memset(&areq, 0, sizeof(areq));
	sin = (struct sockaddr_in *) &areq.arp_pa;
	sin->sin_family = AF_INET;

	if (inet_aton(argv[1], &ipaddr) == 0) {
		fprintf(stderr, "-- Error: invalid numbers-and-dots IP address %s.\n",
				argv[1]);
		exit(1);
	}
	
	sin->sin_addr = ipaddr;
	sin = (struct sockaddr_in *) &areq.arp_ha;
	sin->sin_family = ARPHRD_ETHER;
	
	strncpy(areq.arp_dev, "eth0", 15);
	
	if (ioctl(s, SIOCGARP, (caddr_t) &areq) == -1) {
		perror("-- Error: unable to make ARP request, error");
		exit(1);
	}
	printf("%s (%s) -> %s\n", argv[1], 
			inet_ntoa(&((struct sockaddr_in *) &areq.arp_pa)->sin_addr), 
			ethernet_mactoa(&areq.arp_ha));
	return 0;
}
