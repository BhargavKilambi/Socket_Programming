#include<stdio.h>
#include<stdlib.h>
#include<errno.h>
#include<sys/types.h>
#include<unistd.h>
#include<netinet/in.h>
#define port 9898

void fun(int pid)
{
int sock=socket(AF_INET,SOCK_STREAM,0);

struct sockaddr_in ser_addr;
ser_addr.sin_family=AF_INET;
ser_addr.sin_port=htons(port);
ser_addr.sin_addr.s_addr=inet_addr("172.20.0.7");
int len=sizeof(ser_addr);
connect(sock,(struct sockaddr*)&ser_addr,len);

char message[200];
printf("enter the message\n");
scanf("%s",message);
send(sock,message,200,0);
printf("msg sent\n");
close(sock);
}

int main()
{

int pid;
pid=fork();
if(pid==0)
{
printf("child process\n");
fun(pid);
exit(0);
}
else
{
wait(3);
printf("Parent process\n");
fun(1);
exit(0);
}
return 0;
}