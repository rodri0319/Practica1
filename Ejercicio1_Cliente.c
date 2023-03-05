#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>

int main()
{
    int sock = socket(AF_INET, SOCK_STREAM, 0);

    struct sockaddr_in server_addr;
    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(1234);
    server_addr.sin_addr.s_addr = INADDR_ANY;
    // inet_aton("192.168.0.1", &server_addr.sin_addr);

    connect(sock, (struct sockaddr *)&server_addr, sizeof(server_addr));
    printf("ConexiÃ³n establecida\n");
    char message[100], server_resp[100];

    while (1)
    {
        printf("Ingrese el mensaje: ");
        fgets(message, 100, stdin);

        if(send(sock, message, strlen(message), 0)<0)
        {
            printf("Error al enviar\n");
            exit(1);
        }

        if(recv(sock, server_resp, 100, 0)<0)
        {
            printf("Error al recibir\n");
            exit(1);
        }
        printf("Respuesta del servidor: %s\n", server_resp);
        if (strcmp(server_resp, "Bye.") == 0)
            break;
    }
    close(sock);
    return 0;
}
