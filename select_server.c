#include<stdio.h>
#include<stdlib.h>
#include<errno.h>
#include<sys/types.h>
#include<unistd.h>
#include<netinet/in.h>

#define port 9898
#define MAX 512
void main()
{

int sock,new_sock,res,nread;
char buffer[MAX];
struct sockaddr_in ser_addr,cli_addr;

sock=socket(AF_INET,SOCK_STREAM,0);
printf("socket successful\n");

ser_addr.sin_family=AF_INET;
ser_addr.sin_port=htons(port);
ser_addr.sin_addr.s_addr=INADDR_ANY;

socklen_t len=sizeof(ser_addr);

bind(sock,(struct sockaddr*)&ser_addr,len);
printf("bind successful\n");

listen(sock,5);
printf("listen 5 backlogs\n");
fd_set rset,tset;
FD_ZERO(&rset);
FD_SET(sock,&rset);
int x;
while(1)
{
tset=rset;
res=select(FD_SETSIZE,&tset,NULL,NULL,NULL);
printf("select\n");

for(x=0;x<FD_SETSIZE;x++)
{
if(FD_ISSET(x,&tset))
{
if(x==sock)
{
new_sock=accept(sock,(struct sockaddr*)&cli_addr,&len);
printf("Accepted new conn from %s:%d %d\n",new_sock,cli_addr.sin_addr,cli_addr.sin_port);
FD_SET(new_sock,&rset);
}
else
{
nread=recv(x,buffer,25,0);
printf("no of bytes read %d\t%d\n",nread,x);
if(nread<=0)
{
close(new_sock);
FD_CLR(new_sock,&rset);
printf("client disconnected\n");
}
else

{
buffer[nread]='\0';
printf("msg is %s\n",buffer);
}
}
}
}
}
}
