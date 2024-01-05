#include <stdlib.h>     //exit()
#include <signal.h>     //signal()
#include <time.h>
#include "test.h"

int Version;

void Handler(int signo)
{
    //System Exit
    printf("\r\nHandler:Goto Sleep mode\r\n");
    DEV_ModuleExit();
    exit(0);
}

int main(int argc, char *argv[])
{
    // Exception handling:ctrl + c
    signal(SIGINT, Handler);
	EPD_12in48B_V2_test(argv[1]);
    DEV_ModuleExit();
    return 0;
}
