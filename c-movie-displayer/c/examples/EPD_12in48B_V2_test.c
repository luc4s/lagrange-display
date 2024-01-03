#include "test.h"
#include "EPD_12in48b.h"

extern int Version;

int EPD_12in48B_V2_test(char* filename)
{
	// Specify the version
	Version = 2;
	
	printf("12.48inch e-Paper B demo\r\n");
    DEV_ModuleInit();

    if(EPD_12in48B_Init() != 0) {
        printf("e-Paper init failed\r\n");
    } else {
        printf("e-Paper init...\r\n");
    }
    //printf("e-Paper clear...\r\n");
    //EPD_12in48B_Clear();
    //DEV_Delay_ms(3000);
    // Create a new image cache
    UBYTE  *BlackImage, *RedImage;

    UDOUBLE Imagesize = (((EPD_12in48B_MAX_WIDTH%8==0)?(EPD_12in48B_MAX_WIDTH/8):(EPD_12in48B_MAX_WIDTH/8+1)) * EPD_12in48B_MAX_HEIGHT);
    if((BlackImage = (UBYTE *)malloc(Imagesize)) == NULL) {
        printf("Failed to apply for black memory...\r\n");
        exit(0);
    }
    if((RedImage = (UBYTE *)malloc(Imagesize)) == NULL) {
        printf("Failed to apply for red memory...\r\n");
        exit(0);
    }

    Paint_NewImage(BlackImage, EPD_12in48B_MAX_WIDTH, EPD_12in48B_MAX_HEIGHT, 0, 0);
    Paint_SelectImage(BlackImage);
    Paint_Clear(WHITE);

    Paint_NewImage(RedImage, EPD_12in48B_MAX_WIDTH, EPD_12in48B_MAX_HEIGHT, 0, 0);
    Paint_SelectImage(RedImage);
    Paint_Clear(WHITE);


    printf("**************\r\n");
    printf("Read BMP file:\r\n");
    Paint_SelectImage(BlackImage);
    GUI_ReadBmp(filename, 0, 0);
    EPD_12in48B_Display(BlackImage, RedImage);
    DEV_Delay_ms(2000);
    printf("**************\r\n");

    return 0;
}

