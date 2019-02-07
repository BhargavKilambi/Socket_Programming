#include<stdio.h>
#include<unistd.h>
#include<stdlib.h>
#include<sys/types.h>
#include<netinet/in.h>
#include<sys/socket.h>
#include<string.h>

#define PORT 8808

//for TCP
//display peer ip and port after receiving a connection and accepting it
int main()
{

struct sockaddr_in saddr;
int sockfd=0,new_sock;
struct sockaddr_storage s1;
socklen_t len;
char ipstr[INET6_ADDRSTRLEN];

len = sizeof(saddr);

if((sockfd = socket(AF_INET,SOCK_STREAM,0))==0){

printf("socket failed\n");

}

saddr.sin_family = AF_INET;
saddr.sin_addr.s_addr = htonl(INADDR_ANY);
saddr.sin_port = htons(PORT);

if(bind(sockfd,(struct sockaddr *)&saddr,len)<0)
{printf("Bind Failed\n");
}

if(listen(sockfd,3)<0){

printf("Listen Error\n");

}

if((new_sock = accept(sockfd,(struct sockaddr *)&saddr,(socklen_t*)&len))<0)
{
printf("Accept Failed\n");
}
else{
len = sizeof(s1);
getpeername(sockfd,(struct sockaddr*)&s1,&len);

struct sockaddr_in *s = (struct sockaddr_in *) &s1;
int port = ntohs(s->sin_port);
inet_ntop(AF_INET,&s->sin_addr,ipstr,sizeof(ipstr));

printf("Peer IP Address: %s\n",ipstr);
printf("Peer Port      : %d\n",port);
}
