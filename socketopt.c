#include<stdio.h>
#include<stdlib.h>
#include<errno.h>
#include<sys/types.h>
#include<unistd.h>
#include<netinet/in.h>
#include<netinet/tcp.h>
#include<netinet/udp.h>
int main()
{
//FOR TCP
        int sockfd,maxseg,sendbuff;
        sockfd = socket(AF_INET,SOCK_STREAM,0);
        socklen_t optlen = sizeof(maxseg);
        getsockopt(sockfd,IPPROTO_TCP,TCP_MAXSEG,&maxseg,&optlen);
        printf("\ntcp maxseg = %d",maxseg);

        setsockopt(sockfd,SOL_SOCKET,SO_SNDBUF,&sendbuff,optlen);
        optlen = sizeof(sendbuff);

        getsockopt(sockfd,SOL_SOCKET,SO_SNDBUF,&sendbuff,&optlen);
        printf("\n send buff = %d\n",sendbuff);


        getsockopt(sockfd,SOL_SOCKET,SO_RCVBUF,&sendbuff,&optlen);
        printf("\nRCVBUF = %d\n",sendbuff);

//FOR UDP
        int sockfd1,maxseg1;
        sockfd1 = socket(AF_INET,SOCK_DGRAM,0);
        optlen = sizeof(maxseg1);
        getsockopt(sockfd1,IPPROTO_UDP,TCP_MAXSEG,&maxseg1,&optlen);
        printf("\nudp maxseg = %d\n",maxseg1);

// tcp_maxseg 536
// snd_buf 65534
// rcv_buff 87380
// udp_maxseg 4195520 -- doesn't exist , it's garbage value

}