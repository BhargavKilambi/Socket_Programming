#include<stdio.h>
#include<stdlib.h>
#include<errno.h>
#include<netinet/in.h>
#include<sys/types.h>
#include<unistd.h>

int main()
{

        int sockfd;
        int stype;
        sockfd = socket(AF_INET,SOCK_DGRAM,0);
        int optval = sizeof(stype);
        getsockopt(sockfd,SOL_SOCKET,SO_TYPE,&stype,&optval);
        printf("\nsocket type = %d",stype);
}
// returns socket type
// stype = 1 if TCP and stype = 2 if UDP
